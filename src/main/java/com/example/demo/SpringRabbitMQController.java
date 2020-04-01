package com.example.demo;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SpringRabbitMQController {

	@Produce(uri = "direct:startRabbitMQPoint")
	private ProducerTemplate template;

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String createEmployee(@RequestParam int id, @RequestParam String name, @RequestParam String designation) {

		Employee emp = new Employee();
		emp.setName(name);
		emp.setDesignation(designation);
		emp.setEmpId(id);

		template.asyncSendBody(template.getDefaultEndpoint(), emp);
		return "";
	}
}
