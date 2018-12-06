/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.alibaba.sentinel.feign;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import feign.Contract;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;

/**
 * {@link Feign.Builder} like {@link HystrixFeign.Builder}
 *
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
public class SentinelFeign {

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder extends Feign.Builder
			implements ApplicationContextAware {

		private Contract contract = new Contract.Default();

		private ApplicationContext applicationContext;

		private FeignContext feignContext;

		@Override
		public Feign.Builder invocationHandlerFactory(
				InvocationHandlerFactory invocationHandlerFactory) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Builder contract(Contract contract) {
			this.contract = contract;
			return this;
		}

		@Override
		public Feign build() {
			super.invocationHandlerFactory(new InvocationHandlerFactory() {
				@Override
				public InvocationHandler create(Target target,
						Map<Method, MethodHandler> dispatch) {
					// using reflect get fallback and fallbackFactory properties from
					// FeignClientFactoryBean because FeignClientFactoryBean is a package
					// level class, we can not use it in our package
					Object feignClientFactoryBean = Builder.this.applicationContext
							.getBean("&" + target.type().getName());

					Class fallback = (Class) getFieldValue(feignClientFactoryBean,
							"fallback");
					Class fallbackFactory = (Class) getFieldValue(feignClientFactoryBean,
							"fallbackFactory");
					String name = (String) getFieldValue(feignClientFactoryBean, "name");

					Object fallbackInstance;
					FallbackFactory fallbackFactoryInstance;
					// check fallback and fallbackFactory properties
					if (void.class != fallback) {
						fallbackInstance = getFromContext(name, "fallback", fallback,
								target);
						return new SentinelInvocationHandler(target, dispatch,
								new FallbackFactory.Default(fallbackInstance));
					}
					if (void.class != fallbackFactory) {
						fallbackFactoryInstance = (FallbackFactory) getFromContext(name,
								"fallbackFactory", fallbackFactory, target);
						return new SentinelInvocationHandler(target, dispatch,
								fallbackFactoryInstance);
					}
					return new SentinelInvocationHandler(target, dispatch);
				}

				private Object getFromContext(String name, String type,
						Class fallbackType, Target target) {
					Object fallbackInstance = feignContext.getInstance(name,
							fallbackType);
					if (fallbackInstance == null) {
						throw new IllegalStateException(String.format(
								"No %s instance of type %s found for feign client %s",
								type, fallbackType, name));
					}

					if (!target.type().isAssignableFrom(fallbackType)) {
						throw new IllegalStateException(String.format(
								"Incompatible %s instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
								type, fallbackType, target.type(), name));
					}
					return fallbackInstance;
				}
			});

			super.contract(new SentinelContractHolder(contract));
			return super.build();
		}

		private Object getFieldValue(Object instance, String fieldName) {
			Field field = ReflectionUtils.findField(instance.getClass(), fieldName);
			field.setAccessible(true);
			try {
				return field.get(instance);
			}
			catch (IllegalAccessException e) {
				// ignore
			}
			return null;
		}

		@Override
		public void setApplicationContext(ApplicationContext applicationContext)
				throws BeansException {
			this.applicationContext = applicationContext;
			feignContext = this.applicationContext.getBean(FeignContext.class);
		}
	}

}
