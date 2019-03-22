/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.alibaba.dubbo.http.matcher;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

/**
 * {@link ProduceMediaTypeExpression} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
public class ProduceMediaTypeExpressionTest extends AbstractMediaTypeExpressionTest<ProduceMediaTypeExpression> {

    @Test
    public void testMatch() {
        ProduceMediaTypeExpression expression = createExpression(MediaType.APPLICATION_JSON_VALUE);
        Assert.assertTrue(expression.match(Arrays.asList(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON)));

        expression = createExpression(MediaType.APPLICATION_JSON_VALUE);
        Assert.assertFalse(expression.match(Arrays.asList(MediaType.APPLICATION_XML)));
    }
}
