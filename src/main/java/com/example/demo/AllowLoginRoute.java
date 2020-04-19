package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.model.rest.RestBindingMode;

import org.springframework.beans.factory.annotation.Value;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

@Component
public class AllowLoginRoute extends RouteBuilder {

    @Value("${server.port}")
    String serverPort;
    
    @Value("${baeldung.api.path}")
    String contextPath;

    @Override
    public void configure() {

        CamelContext context = new DefaultCamelContext();

        restConfiguration().contextPath(contextPath) //
        .port(serverPort)
        .enableCORS(true)
        .apiContextPath("/api-doc-2")
        .apiProperty("api.title", "Test REST API")
        .apiProperty("api.version", "v1")
        .apiProperty("cors", "true") // cross-site
        .apiContextRouteId("doc-api-2")
        .component("servlet")
        .bindingMode(RestBindingMode.json)
        .dataFormatProperty("prettyPrint", "true");

        rest("/api2/").description("REST Allow Login")
        .id("api-route-2")
        .post("/allowLogin")
        .produces(MediaType.APPLICATION_JSON)
        .consumes(MediaType.APPLICATION_JSON)
//                .get("/hello/{place}")
        .bindingMode(RestBindingMode.auto)
        .type(String.class)
        .enableCORS(true)
//                .outType(OutBean.class)

        .to("direct:allowedLoginQueue");

        from("direct:allowedLoginQueue").convertBodyTo(String.class)
            .to("log:?level=INFO&showBody=true")
            //.to(ExchangePattern.InOnly, "rabbitmq://javainuse.exchange?routingKey=loginGrant&autoDelete=false&declare=false")
            .to(ExchangePattern.InOnly, "rabbitmq://javainuse.exchange?routingKey=loginGrant&autoDelete=false")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
    }

}