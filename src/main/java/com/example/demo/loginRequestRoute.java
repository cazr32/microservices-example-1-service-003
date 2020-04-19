package com.example.demo;

import javax.ws.rs.core.MediaType;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import org.apache.camel.component.jackson.JacksonDataFormat;

@Component
public class loginRequestRoute extends RouteBuilder {

    @Value("${server.port}")
    String serverPort;
    
    @Value("${baeldung.api.path}")
    String contextPath;

    @Override
    public void configure() {

        CamelContext context = new DefaultCamelContext();

        
        // http://localhost:8080/camel/api-doc
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
/** 
The Rest DSL supports automatic binding json/xml contents to/from 
POJOs using Camels Data Format.
By default the binding mode is off, meaning there is no automatic 
binding happening for incoming and outgoing messages.
You may want to use binding if you develop POJOs that maps to 
your REST services request and response types. 
*/         
        
        rest("/api/").description("REST Send Login Request")
            .id("api-route-1")
            .post("/loginRequest")
            .produces(MediaType.APPLICATION_JSON)
            .consumes(MediaType.APPLICATION_JSON)
//                .get("/hello/{place}")
            .bindingMode(RestBindingMode.auto)
            .type(LoginRequest.class)
            .enableCORS(true)
//                .outType(OutBean.class)

            .to("direct:loginRequest");


        
   
        /*from("direct:remoteService")
            .routeId("direct-route")
            .tracing()
            .log(">>> ${body.id}")
            .log(">>> ${body.name}")
//                .transform().simple("blue ${in.body.name}")                
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    MyBean bodyIn = (MyBean) exchange.getIn().getBody();
                    
                    //ExampleServices.example(bodyIn);

                    exchange.getIn().setBody(bodyIn);
                }
            })
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));*/

        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(LoginRequest.class);

        from("direct:loginRequest").id("direct-route-2").marshal(jsonDataFormat)
            .to("log:?level=INFO&showBody=true")
            .to("rabbitmq://javainuse.exchange?routingKey=loginRequest&autoDelete=false")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)).end();
    }
}