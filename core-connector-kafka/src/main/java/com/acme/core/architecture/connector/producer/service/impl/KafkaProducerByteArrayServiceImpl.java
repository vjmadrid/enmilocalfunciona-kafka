package com.acme.core.architecture.connector.producer.service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.core.architecture.connector.producer.service.KafkaProducerByteArrayService;

@Service
public class KafkaProducerByteArrayServiceImpl implements KafkaProducerByteArrayService{

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerByteArrayServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplateByteArray;
    
    public void send(String topic, byte[] bytearray){
        LOG.info("Sending bytes='{}' to topic param='{}'", bytearray, topic);
        kafkaTemplateByteArray.send(topic, bytearray);
    }
    
    public void send(String topic, ProducerRecord<String, byte[]> record){
    	LOG.info("Sending message='{}' to topic param='{}'",record.value(),topic);
    	kafkaTemplateByteArray.send(record);
    }
   
}
