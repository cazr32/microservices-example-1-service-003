/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.jackson.JacksonDataFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple Camel route that triggers from a timer and routes to RabbitMQ
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
/*@Component
public class SampleCamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:hello?period=1000")
            .transform(simple("Random number ${random(0,100)}"))
            .to("rabbitmq:foo");

        from("rabbitmq:foo")
            .log("From RabbitMQ: ${body}");
    }

}*/

@Component
public class RabbitMQRoute /*extends RouteBuilder*/ {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQRoute.class);

	/*@Override
	public void configure() throws Exception {

        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);
        
        logger.debug("before calling the rabbitMQroute");

		from("direct:startQueuePoint").id("rabbitMQRoute").marshal(jsonDataFormat)
				.to("rabbitmq://46.101.194.224:5672/javainuse.exchange?queue=javainuse.queue&autoDelete=false").end();
	}*/
}