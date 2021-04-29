package com.acme.kafka.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerByteArrayService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerByteArrayService.class);

    @Value("${app.topic.example-bytearray}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplateByteArray;
    
    public void send(byte[] bytearray){
        LOG.info("[KafkaProducerJsonService] sending bytes='{}' to topic='{}'", bytearray, topic);
        kafkaTemplateByteArray.send(topic, bytearray);
    }
    
    public void send(String topic, byte[] bytearray){
        LOG.info("[KafkaProducerJsonService] sending bytes='{}' to topic param='{}'", bytearray, topic);
        kafkaTemplateByteArray.send(topic, bytearray);
    }
   
}
