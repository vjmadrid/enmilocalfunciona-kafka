package com.acme.kafka.testing.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${app.topic.example1}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message){
        LOG.info("[KafkaProducerService] sending message='{}' to topic='{}'", message, this.topic);
        kafkaTemplate.send(topic, message);
    }
    
    public void send(String topic, String message){
        LOG.info("[KafkaProducerService] sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }
}
