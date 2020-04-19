package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.apache.camel.component.jackson.JacksonDataFormat;

@Component
public class LoginRequestRoute extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(LoginRequestRoute.class);

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
            .produces(MediaType.TEXT_PLAIN)
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
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    Map<String, Object> headers = exchange.getIn().getHeaders();
                    if(headers == null){
                        logger.debug("headers were null");
                        headers = new HashMap<String, Object>();
                    }
                    headers.put(Exchange.CONTENT_TYPE, MediaType.TEXT_PLAIN);
                    headers.put(Exchange.HTTP_RESPONSE_CODE, constant(201));
                    exchange.getIn().setHeaders(headers);
                }
            })
            .to(ExchangePattern.InOnly, "rabbitmq://javainuse.exchange?routingKey=loginRequest&autoDelete=false&declare=false");
            //.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
            //.setHeader(Exchange.CONTENT_TYPE, MediaType.TEXT_PLAIN);
    }
}