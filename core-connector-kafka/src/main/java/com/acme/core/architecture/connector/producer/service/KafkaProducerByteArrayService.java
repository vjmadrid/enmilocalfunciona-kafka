package com.acme.core.architecture.connector.producer.service;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface KafkaProducerByteArrayService {

	public void send(String topic, byte[] bytearray);
    
    public void send(String topic, ProducerRecord<String, byte[]> record);
    
}
