package com.acme.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${app.topic.example1}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateString;

    public void send(String message){
        LOG.info("[KafkaProducerService] sending message='{}' to topic='{}'", message, topic);
        kafkaTemplateString.send(topic, message);
    }
    
    public void send(String topic, String message){
        LOG.info("[KafkaProducerService] sending message='{}' to topic param='{}'", message, topic);
        kafkaTemplateString.send(topic, message);
    }
    
    public void send(ProducerRecord<String, String> record){
    	LOG.info("[KafkaProducerService] sending message='{}' to topic='{}'",record.value(),topic);
    	kafkaTemplateString.send(record);
    }
    
    public void send(String topic, ProducerRecord<String, String> record){
    	LOG.info("[KafkaProducerService] sending message='{}' to topic param='{}'",record.value(),topic);
    	ListenableFuture<SendResult<String, String>> future =kafkaTemplateString.send(record);
    	
    	future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
            	LOG.info("[Callback] Unable to send message=[{}] due to : {}", record.value(), ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
            	LOG.info("[Callback] Sent message=[{}] with offset=[{}]", record.value(), result.getRecordMetadata().offset());
            }
        });
    }
    
}
