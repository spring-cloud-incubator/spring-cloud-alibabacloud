/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.alicloud.sms.base;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 获取接收云通信消息的临时token.
 */
public class TokenGetterForAlicom {

	private static final Logger log = LoggerFactory.getLogger(TokenGetterForAlicom.class);

	private String accessKeyId;

	private String accessKeySecret;

	private String endpointNameForPop;

	private String regionIdForPop;

	private String domainForPop;

	private IAcsClient iAcsClient;

	private Long ownerId;

	private final static String PRODUCT_NAME = "Dybaseapi";

	// 过期时间小于2分钟则重新获取，防止服务器时间误差
	private long bufferTime = 1000 * 60 * 2;

	private final Object lock = new Object();

	private ConcurrentMap<String, TokenForAlicom> tokenMap = new ConcurrentHashMap<String, TokenForAlicom>();

	public TokenGetterForAlicom(String accessKeyId, String accessKeySecret,
			String endpointNameForPop, String regionIdForPop, String domainForPop,
			Long ownerId) throws ClientException {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.endpointNameForPop = endpointNameForPop;
		this.regionIdForPop = regionIdForPop;
		this.domainForPop = domainForPop;
		this.ownerId = ownerId;
		init();
	}

	private void init() throws ClientException {
		DefaultProfile.addEndpoint(endpointNameForPop, regionIdForPop, PRODUCT_NAME,
				domainForPop);
		IClientProfile profile = DefaultProfile.getProfile(regionIdForPop, accessKeyId,
				accessKeySecret);
		profile.getHttpClientConfig().setCompatibleMode(true);
		iAcsClient = new DefaultAcsClient(profile);
	}

	private TokenForAlicom getTokenFromRemote(String messageType)
			throws ClientException {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		QueryTokenForMnsQueueRequest request = new QueryTokenForMnsQueueRequest();
		request.setAcceptFormat(FormatType.JSON);
		request.setMessageType(messageType);
		request.setOwnerId(ownerId);
		request.setProtocol(ProtocolType.HTTPS);
		request.setMethod(MethodType.POST);
		QueryTokenForMnsQueueResponse response = iAcsClient.getAcsResponse(request);
		String resultCode = response.getCode();
		if (resultCode != null && "OK".equals(resultCode)) {
			QueryTokenForMnsQueueResponse.MessageTokenDTO dto = response
					.getMessageTokenDTO();
			TokenForAlicom token = new TokenForAlicom();
			String timeStr = dto.getExpireTime();
			TemporalAccessor parse = df.parse(timeStr);
			LocalDateTime date = LocalDateTime.from(parse);
			token.setMessageType(messageType);
			token.setExpireTime(date.toEpochSecond(ZoneOffset.of("+8")));
			token.setToken(dto.getSecurityToken());
			token.setTempAccessKeyId(dto.getAccessKeyId());
			token.setTempAccessKeySecret(dto.getAccessKeySecret());
			return token;
		}
		else {
			log.error("getTokenFromRemote_error,messageType:" + messageType + ",code:"
					+ response.getCode() + ",message:" + response.getMessage());
			throw new ServerException(response.getCode(), response.getMessage());
		}
	}

	public TokenForAlicom getTokenByMessageType(String messageType, String queueName,
			String mnsAccountEndpoint)
			throws ClientException {
		TokenForAlicom token = tokenMap.get(messageType);
		long now = System.currentTimeMillis();
		// 过期时间小于2分钟则重新获取，防止服务器时间误差
		if (token == null || (token.getExpireTime() - now) < bufferTime) {
			synchronized (lock) {
				token = tokenMap.get(messageType);
				if (token == null || (token.getExpireTime() - now) < bufferTime) {
					TokenForAlicom oldToken = null;
					if (token != null) {
						oldToken = token;
					}
					token = getTokenFromRemote(messageType);
					// 因为换token时需要重建client和关闭老的client，所以创建client的代码和创建token放在一起
					CloudAccount account = new CloudAccount(token.getTempAccessKeyId(),
							token.getTempAccessKeySecret(), mnsAccountEndpoint,
							token.getToken());
					MNSClient client = account.getMNSClient();
					CloudQueue queue = client.getQueueRef(queueName);
					token.setClient(client);
					token.setQueue(queue);
					tokenMap.put(messageType, token);
					if (oldToken != null) {
						oldToken.closeClient();
					}
				}
			}
		}
		return token;
	}

}
