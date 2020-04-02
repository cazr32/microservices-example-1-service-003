package com.example.demo;


import javax.annotation.Resource;

import com.rabbitmq.client.ConnectionFactory;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class DemoApplication {

	@Value("${baeldung.api.path}")
    String contextPath;

	@Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), contextPath+"/*");
        servlet.setName("CamelServlet");
        return servlet;
    }


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/*@Resource
	private Environment env;*/

    //@Bean
    //public ConnectionFactory connectionFactory(){
        //ConnectionFactory connectionFactory = new ConnectionFactory();
        //connectionFactory.setHost("hola");
		//connectionFactory.setPort(Integer.valueOf(env.getProperty("spring.rabbitmq.port")));
		//connectionFactory.setUsername(env.getProperty("spring.rabbitmq.username"));
		//connectionFactory.setPassword(env.getProperty("spring.rabbitmq.password"));
        //connectionFactory.setAutomaticRecoveryEnabled(true);
        // more config options here etc
        //return connectionFactory;
	//}
	





}
//go to this URL to test the program
//http://46.101.194.224:7171/demo-0.0.1-SNAPSHOT/employee?id=6&name=emp1&designation=manager
