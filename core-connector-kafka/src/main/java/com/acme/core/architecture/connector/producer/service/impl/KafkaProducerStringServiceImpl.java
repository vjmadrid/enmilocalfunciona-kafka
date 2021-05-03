package com.acme.core.architecture.connector.producer.service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.core.architecture.connector.producer.service.KafkaProducerStringService;

@Service
public class KafkaProducerStringServiceImpl implements KafkaProducerStringService{

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerStringServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateString;
    
    public void send(String topic, String message){
        LOG.info("Sending message='{}' to topic param='{}'", message, topic);
        kafkaTemplateString.send(topic, message);
    }
    
    public void send(String topic, ProducerRecord<String, String> record){
    	LOG.info("Sending message='{}' to topic param='{}'",record.value(),topic);
    	kafkaTemplateString.send(record);
    }
    
}
