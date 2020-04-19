package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import org.apache.camel.model.rest.RestBindingMode;

import org.springframework.beans.factory.annotation.Value;

@Component
public class AllowLoginRoute extends RouteBuilder {

    @Value("${server.port}")
    String serverPort;
    
    @Value("${baeldung.api.path}")
    String contextPath;

    @Override
    public void configure() {

        restConfiguration().contextPath(contextPath) //
        .port(serverPort)
        .enableCORS(true)
        .apiContextPath("/api-doc")
        .apiProperty("api.title", "Test REST API")
        .apiProperty("api.version", "v1")
        .apiProperty("cors", "true") // cross-site
        .apiContextRouteId("doc-api")
        .component("servlet")
        .bindingMode(RestBindingMode.json)
        .dataFormatProperty("prettyPrint", "true");

        rest("/api/").description("REST Allow Login")
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
            .to("rabbitmq://javainuse.exchange?queue=allowedLoginQueue&autoDelete=false");
    }

}