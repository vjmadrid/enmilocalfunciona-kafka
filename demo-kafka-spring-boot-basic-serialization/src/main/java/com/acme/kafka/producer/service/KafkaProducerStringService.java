package com.acme.kafka.producer.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerStringService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerStringService.class);

    @Value("${app.topic.example-string}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateString;
    
    public void send(String message){
        LOG.info("[KafkaProducerStringService] sending message='{}' to topic='{}'", message, topic);
        kafkaTemplateString.send(topic, message);
    }
    
    public void send(String topic, String message){
        LOG.info("[KafkaProducerStringService] sending message='{}' to topic param='{}'", message, topic);
        kafkaTemplateString.send(topic, message);
    }
    
    public void send(ProducerRecord<String, String> record){
    	LOG.info("[KafkaProducerStringService] sending message='{}' to topic='{}'",record.value(),topic);
    	kafkaTemplateString.send(record);
    }
    
    public void send(String topic, ProducerRecord<String, String> record){
    	LOG.info("[KafkaProducerStringService] sending message='{}' to topic param='{}'",record.value(),topic);
    	kafkaTemplateString.send(record);
    }
    
}
