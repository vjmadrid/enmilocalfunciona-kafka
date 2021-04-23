package com.acme.kafka.producer.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.kafka.producer.controller.constant.KafkaProducerRestControllerConstant;
import com.acme.kafka.producer.entity.CustomMessage;
import com.acme.kafka.producer.service.KafkaProducerService;

@RestController
@RequestMapping(value = KafkaProducerRestControllerConstant.BASE_URL)
public class KafkaProducerRestController {
	
	private final KafkaProducerService kafkaProducerService;

	@Autowired
	public KafkaProducerRestController(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;
	}
	
	@PostMapping(value = KafkaProducerRestControllerConstant.BASE_SEND_MESSAGE_URL)
	public void sendMessageToKafkaTopic(@RequestParam(KafkaProducerRestControllerConstant.MESSAGE_VARIABLE_PARAM) String message) {
		this.kafkaProducerService.send(message);
	}
	
	@PostMapping(value = KafkaProducerRestControllerConstant.BASE_SEND_MESSAGE_ENTITY_URL)
	public void sendMessageEntityToKafkaTopic(@RequestParam(KafkaProducerRestControllerConstant.MESSAGE_VARIABLE_PARAM) String message) {
		
		CustomMessage customMessage = new CustomMessage();
		customMessage.setValue(message);
		customMessage.setCreatedDate(new Date());
		
		this.kafkaProducerService.send(message);
	}
	

}
