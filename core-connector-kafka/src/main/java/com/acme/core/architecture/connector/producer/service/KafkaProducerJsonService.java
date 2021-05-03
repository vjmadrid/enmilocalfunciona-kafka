package com.acme.core.architecture.connector.producer.service;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface KafkaProducerJsonService {

    public void send(String topic, Object object);
    
    public void send(String topic, ProducerRecord<String, Object> record);
    
}
