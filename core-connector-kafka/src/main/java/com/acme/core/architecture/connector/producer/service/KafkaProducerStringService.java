package com.acme.core.architecture.connector.producer.service;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface KafkaProducerStringService {

	public void send(String topic, String message);
    
    public void send(String topic, ProducerRecord<String, String> record);
	
}
