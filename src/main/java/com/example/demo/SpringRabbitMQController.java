package com.example.demo;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@RestController
public class SpringRabbitMQController {

	/*@EndpointInject(uri = "direct:startQueuePoint")
	private ProducerTemplate template;

	private static final Logger logger = LoggerFactory.getLogger(SpringRabbitMQController.class);

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String createEmployee(@RequestParam int id, @RequestParam String name, @RequestParam String designation) {

		Employee emp = new Employee();
		emp.setName(name);
		emp.setDesignation(designation);
		emp.setEmpId(id);
		logger.debug("before sending request to rabbit {}", template.getDefaultEndpoint());
		template.asyncSendBody(template.getDefaultEndpoint(), emp);
		logger.debug("after sending request to rabbit");
		return "";
	}*/
}
