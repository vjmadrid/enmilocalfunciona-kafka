package com.acme.core.architecture.connector.producer.service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.core.architecture.connector.producer.service.KafkaProducerJsonService;

@Service
public class KafkaProducerJsonServiceImpl implements KafkaProducerJsonService{

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerJsonServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplateJson;
    
    public void send(String topic, Object object){
        LOG.info("Sending object='{}' to topic param='{}'", object, topic);
        kafkaTemplateJson.send(topic, object);
    }

	@Override
	public void send(String topic, ProducerRecord<String, Object> record) {
		LOG.info("Sending message='{}' to topic param='{}'",record.value(),topic);
		kafkaTemplateJson.send(record);
	}
    
}
