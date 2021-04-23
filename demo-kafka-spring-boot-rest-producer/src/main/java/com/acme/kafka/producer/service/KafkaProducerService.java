package com.acme.kafka.producer.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.kafka.producer.entity.CustomMessage;

@Service
public class KafkaProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${app.topic.example1}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private KafkaTemplate<String, CustomMessage> customMessageKafkaTemplate;

    public void send(String message){
        LOG.info("[KafkaProducerService] sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }
    
    public void send(CustomMessage message){
        LOG.info("[KafkaProducerService] sending message='{}' to topic='{}'", message, topic);
        customMessageKafkaTemplate.send(topic, message);
    }
    
}
