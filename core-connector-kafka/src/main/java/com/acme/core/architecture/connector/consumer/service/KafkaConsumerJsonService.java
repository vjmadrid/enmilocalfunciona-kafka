package com.acme.core.architecture.connector.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.handler.annotation.Payload;

public interface KafkaConsumerJsonService {

	public void receive(ConsumerRecord<String, Object> consumerRecord, @Payload Object payload);
	
}