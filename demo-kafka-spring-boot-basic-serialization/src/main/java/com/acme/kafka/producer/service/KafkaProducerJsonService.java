package com.acme.kafka.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerJsonService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerJsonService.class);

    @Value("${app.topic.example-json}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplateJson;
    
    public void send(Object objectValue){
        LOG.info("[KafkaProducerJsonService] sending object='{}' to topic='{}'", objectValue, topic);
        kafkaTemplateJson.send(topic, objectValue);
    }
    
    public void send(String topic, Object objectValue){
        LOG.info("[KafkaProducerJsonService] sending custommessage='{}' to topic param='{}'", objectValue, topic);
        kafkaTemplateJson.send(topic, objectValue);
    }
    
}
