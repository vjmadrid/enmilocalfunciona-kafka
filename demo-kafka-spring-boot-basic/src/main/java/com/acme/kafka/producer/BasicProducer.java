package com.acme.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BasicProducer {

    private static final Logger LOG = LoggerFactory.getLogger(BasicProducer.class);

    @Value("${app.topic.example1}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message){
        LOG.info("[BasicProducer] sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }
}
