package com.acme.kafka.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.kafka.producer.controller.constant.KafkaProducerRestControllerConstant;
import com.acme.kafka.producer.service.KafkaProducerService;

@RestController
@RequestMapping(value = KafkaProducerRestControllerConstant.BASE_URL)
public class KafkaProducerRestController {
	
	public static final Logger LOG = LoggerFactory.getLogger(KafkaProducerRestController.class);
	
	private final KafkaProducerService kafkaProducerService;

	@Autowired
	public KafkaProducerRestController(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;
	}
	
	@GetMapping("/")
	public void isAlived() {
		LOG.info("[KafkaProducerRestController] is Alived ...");
	}
	
	@PostMapping(value = KafkaProducerRestControllerConstant.BASE_SEND_MESSAGE_URL)
	public void sendMessageToKafkaTopic(@RequestParam(KafkaProducerRestControllerConstant.MESSAGE_VARIABLE_PARAM) String message) {
		LOG.info("[KafkaProducerRestController] sending message : {}", message);
		this.kafkaProducerService.send(message);
	}

}
