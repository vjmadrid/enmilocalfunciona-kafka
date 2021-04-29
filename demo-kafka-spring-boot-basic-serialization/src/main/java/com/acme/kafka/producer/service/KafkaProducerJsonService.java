package com.acme.kafka.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.kafka.entity.CustomMessage;

@Service
public class KafkaProducerJsonService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerJsonService.class);

    @Value("${app.topic.example-json}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplateJson;
    
    public void send(CustomMessage customMessage){
        LOG.info("[KafkaProducerJsonService] sending custommessage='{}' to topic='{}'", customMessage, topic);
        kafkaTemplateJson.send(topic, customMessage);
    }
    
    public void send(String topic, CustomMessage customMessage){
        LOG.info("[KafkaProducerJsonService] sending custommessage='{}' to topic param='{}'", customMessage, topic);
        kafkaTemplateJson.send(topic, customMessage);
    }
    
}
