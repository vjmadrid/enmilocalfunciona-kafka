package com.acme.kafka.testing.consumer;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class KafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);

    //getLatchTest() -> Use in testing for received a message -> No production environment
    private CountDownLatch latchTest = new CountDownLatch(1);
    
    private String payload = null;
    
    private String topic = null;
    
    private void showMessageHeaders(MessageHeaders headers) {
		if (headers != null) {
			headers.keySet().forEach(key -> 
        		LOG.info("\t{}: {}", key, headers.get(key))
			);
			
			setTopic((String) headers.get("kafka_receivedTopic"));
		}
		
	}
    
    /** Create @KafkaListener -> Depends application properties
     * 
     * 	Other options : 
     * 		@KafkaListener(topics = DemoConstant.TOPIC, groupId = DemoConstant.GROUP_ID)
     * 		@KafkaListener(id = "basic-listener", topics = "${app.topic.example1}")
     */
    
    @KafkaListener(id = "listener-1",topics = "${app.topic.example1}")
    public void receive(@Payload String message, @Headers MessageHeaders headers) {
        LOG.info("[KafkaConsumerService] received message='{}'", message);
        setPayload(message);
        
        LOG.info("[KafkaConsumerService] Show details...");
        showMessageHeaders(headers);
        
        LOG.info("[KafkaConsumerService] latch.countDown()...");
        latchTest.countDown();
    }
    
    @KafkaListener(id = "listener-2", topics = "${app.topic.example2}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        LOG.info("[KafkaConsumerService] received consumerRecord message='{}'", consumerRecord.toString());
        setPayload(consumerRecord.toString());
        
        LOG.info("[KafkaConsumerService] latch.countDown()...");
        latchTest.countDown();
    }
    
    

}