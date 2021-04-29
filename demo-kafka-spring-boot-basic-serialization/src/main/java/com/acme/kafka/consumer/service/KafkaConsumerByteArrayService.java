package com.acme.kafka.consumer.service;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.kafka.constant.KafkaConfigConstant;
import com.acme.kafka.util.KafkaUtil;

@Service
public class KafkaConsumerByteArrayService {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerByteArrayService.class);

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

	@KafkaListener(id = "bytearray-listener",
			clientIdPrefix = "bytearray",
			topics = "${app.topic.example-bytearray}", 
			groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactoryBytesArray"
	)
	public void receiveAsByteArray(ConsumerRecord<String, byte[]> consumerRecord, @Payload byte[] payload) {
		LOG.info("[KafkaConsumerByteArrayService] received key {}: Type [{}] | Payload: {} | Record: {}", consumerRecord.key(), KafkaUtil.typeIdHeader(consumerRecord.headers()), payload, consumerRecord.toString());

		LOG.info("[KafkaConsumerByteArrayService] latch.countDown()...");
		latchTest.countDown();
	}

}