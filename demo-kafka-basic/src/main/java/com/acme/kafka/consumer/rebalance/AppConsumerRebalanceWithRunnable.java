package com.acme.kafka.consumer.rebalance;

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

public class AppConsumerRebalanceWithRunnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppConsumerRebalanceWithRunnable.class);
	
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("[AppConsumerRebalanceWithRunnable] *** Init ***");

    	ConsumerRebalanceRunnable consumerThread = new ConsumerRebalanceRunnable("localhost:9092","group-1",DemoConstant.TOPIC);
    	
        Thread t1 = new Thread(consumerThread);
        t1.start();
        
        LOG.info("*** End ***");
    }

}
