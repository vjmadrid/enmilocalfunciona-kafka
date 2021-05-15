package com.acme.kafka.consumer.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.constant.KafkaConstant;

/**
 * 	Sends a set number of messages (10) defined as "String" and with a delay between them (2 seconds)
 *  
 *  Asynchronous
 * 
 * 	Message Template : Hello World! CUSTOM_ID - SEND_DATE
 *  
 *  Different consumers can be used
 *   - Java consumer with appropriate configuration
 *   - kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --property print.key=true --from-beginning
 * 
 */

public class AppConsumerWithConsumerGroupRunnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppConsumerWithConsumerGroupRunnable.class);
	
	private static final int NUM_CONSUMERS = 3;
	
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("*** Init ***");

    	//Se puede disparar aqui el productor
    	
    	// xxx
        ConsumerGroupRunnable consumerGroup =  new ConsumerGroupRunnable(KafkaConstant.DEFAULT_CONSUMER_CLIENT_ID, KafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, DemoConstant.GROUP_ID, DemoConstant.TOPIC, NUM_CONSUMERS);

        consumerGroup.executeConsumers();
        
        LOG.info("*** End ***");
    }

}
