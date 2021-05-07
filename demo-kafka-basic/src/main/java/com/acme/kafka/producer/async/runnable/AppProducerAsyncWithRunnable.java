package com.acme.kafka.producer.async.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;

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

public class AppProducerAsyncWithRunnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppProducerAsyncWithRunnable.class);
	
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("[ProducerAsyncWithRunnable] *** Init ***");

    	// Option 1: No limit messages
    	ProducerAsyncNoLimitRunnable producerNoLimitThread = new ProducerAsyncNoLimitRunnable("localhost:9092", DemoConstant.TOPIC);
        Thread t1 = new Thread(producerNoLimitThread);
        t1.start();
        
        // Option 2: limit messages
    	ProducerAsyncLimitRunnable producerLimitThread = new ProducerAsyncLimitRunnable("localhost:9092", DemoConstant.TOPIC);
        Thread t2 = new Thread(producerLimitThread);
        t2.start();
        
        LOG.info("*** End ***");
    }

}
