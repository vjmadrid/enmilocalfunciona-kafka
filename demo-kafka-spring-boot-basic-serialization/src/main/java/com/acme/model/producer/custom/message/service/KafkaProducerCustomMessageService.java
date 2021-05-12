package com.acme.model.producer.custom.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.model.custom.message.entity.CustomMessage;

@Service
public class KafkaProducerCustomMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerCustomMessageService.class);

    @Value("${app.topic.example-json}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, CustomMessage> kafkaTemplateCustomMessage;
    
    public void send(CustomMessage customMessage){
        LOG.info("[KafkaProducerCustomMessageService] sending custommessage='{}' to topic='{}'", customMessage, topic);
        kafkaTemplateCustomMessage.send(topic, customMessage);
    }
    
    public void send(String topic, CustomMessage customMessage){
        LOG.info("[KafkaProducerCustomMessageService] sending custommessage='{}' to topic param='{}'", customMessage, topic);
        kafkaTemplateCustomMessage.send(topic, customMessage);
    }
    
}
