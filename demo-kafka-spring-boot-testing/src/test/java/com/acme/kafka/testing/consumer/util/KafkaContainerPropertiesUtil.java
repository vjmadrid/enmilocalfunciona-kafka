package com.acme.kafka.testing.consumer.util;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

public class KafkaContainerPropertiesUtil {
	
	private KafkaContainerPropertiesUtil() {
		throw new IllegalStateException("KafkaContainerPropertiesUtil");
	}
	
	public static ContainerProperties generateContainer(String topic, CountDownLatch latch, Logger LOG) {
		
		ContainerProperties containerProps = new ContainerProperties(topic);
	    containerProps.setMessageListener(new MessageListener<Integer, String>() {

	        @Override
	        public void onMessage(ConsumerRecord<Integer, String> message) {
	        	LOG.info("Received: " + message);
	            latch.countDown();
	        }

	    });
	    
	    return containerProps;
	}

}
