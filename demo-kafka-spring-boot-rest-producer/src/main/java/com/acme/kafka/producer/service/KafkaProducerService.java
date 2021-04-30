package com.acme.kafka.producer.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);

	@Value("${app.topic.example1}")
	private String topic;

	@Value("${app.topic.messages-per-request}")
	private int messagesPerRequest;

	private CountDownLatch latch;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String message) {
		LOG.info("[KafkaProducerService] sending message='{}' to topic='{}'", message, topic);
		kafkaTemplate.send(topic, message);
	}

	public void sendWithLatch(String message) throws InterruptedException {
		latch = new CountDownLatch(messagesPerRequest);
		IntStream.range(0, messagesPerRequest).forEach(i -> {
			this.kafkaTemplate.send(topic, String.valueOf(i), message);
			LOG.info("[KafkaProducerService] sending message='{}' to topic='{}'", message, topic);
		});
		latch.await(60, TimeUnit.SECONDS);
		LOG.info("All {} messages received", messagesPerRequest);
	}

}
