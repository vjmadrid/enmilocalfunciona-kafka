package com.acme.core.architecture.connector.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.handler.annotation.Payload;

public interface KafkaConsumerByteArrayService {

	public void receiveAsByteArray(ConsumerRecord<String, byte[]> consumerRecord, @Payload byte[] payload);

}