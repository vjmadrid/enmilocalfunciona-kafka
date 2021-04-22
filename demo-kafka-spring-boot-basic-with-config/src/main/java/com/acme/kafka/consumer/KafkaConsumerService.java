package com.acme.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.kafka.constant.KafkaConfigConstant;

@Service
public class KafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);

	private CountDownLatch latchTest = new CountDownLatch(KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH);

    //Use in testing for received a message -> No production environment
    public CountDownLatch getLatchTest() {
      return latchTest;
    }
    private void showMessageHeaders(MessageHeaders headers) {
		if (headers != null) {
			headers.keySet().forEach(key -> 
        		LOG.info("\t{}: {}", key, headers.get(key))
			);
		}
	}
    
    /** Create @KafkaListener -> Depends application properties
     * 
     * 	Other options : 
     * 		@KafkaListener(topics = DemoConstant.TOPIC, groupId = DemoConstant.GROUP_ID)
     * 		@KafkaListener(id = "basic-listener", topics = "${app.topic.example1}")
     */
    
    @KafkaListener(id = "basic-listener", topics = "${app.topic.example1}")
    public void receive(@Payload String message, @Headers MessageHeaders headers) {
        LOG.info("[KafkaConsumerService] received message='{}'", message);
        
        LOG.info("[KafkaConsumerService] Show details...");
        showMessageHeaders(headers);
        
        LOG.info("[KafkaConsumerService] latch.countDown()...");
        latchTest.countDown();
    }

}