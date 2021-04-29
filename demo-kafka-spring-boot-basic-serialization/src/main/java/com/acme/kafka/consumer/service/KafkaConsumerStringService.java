package com.acme.kafka.consumer.service;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.kafka.constant.KafkaConfigConstant;
import com.acme.kafka.util.KafkaUtil;

@Service
public class KafkaConsumerStringService {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerStringService.class);

	private CountDownLatch latchTest = new CountDownLatch(KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH);

	// Use in testing for received a message -> No production environment
	public CountDownLatch getLatchTest() {
		return latchTest;
	}

	/**
	 * Create @KafkaListener -> Depends application properties
	 * 
	 * Other options :
	 * 
	 * @KafkaListener(topics = DemoConstant.TOPIC, groupId = DemoConstant.GROUP_ID)
	 * @KafkaListener(id = "basic-listener", topics = "${app.topic.example1}")
	 * @KafkaListener(id = "basic-listener", topics = "${app.topic.example1}",
	 *                   groupId = "${spring.kafka.consumer.group-id}")
	 */

	@KafkaListener(
			id = "string-listener", 
			topics = "${app.topic.example-string}", 
			groupId = "${spring.kafka.consumer.group-id}", 
			errorHandler = "customersErrorHandler", 
			containerFactory = "kafkaListenerContainerFactoryString"
	)
	public void receive(ConsumerRecord<String, String> consumerRecord, @Payload String payload) {
		LOG.info("[KafkaConsumerStringService] received key {}: Type [{}] | Payload: {} | Record: {}", consumerRecord.key(), KafkaUtil.typeIdHeader(consumerRecord.headers()), payload, consumerRecord.toString());

		LOG.info("[KafkaConsumerStringService] latch.countDown()...");
		latchTest.countDown();

		// throw new RuntimeException("Active customersErrorHandler");
	}

	@Bean
	public ConsumerAwareListenerErrorHandler customersErrorHandler() {
		return (m, e, c) -> {
			LOG.error("Error consuming a message", e);
			MessageHeaders headers = m.getHeaders();
			c.seek(new org.apache.kafka.common.TopicPartition(headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
					headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class)),
					Math.max(0, headers.get(KafkaHeaders.OFFSET, Long.class) - 5)); // panic! replay last 5
			return null;
		};
	}

}