package com.acme.kafka.producer.runnable.async;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.constant.KafkaConstant;
import com.acme.kafka.producer.runnable.factory.ProducerRunnableFactory;

/**
 * 	Sends a set of messages defined as "String" and with a delay between them (2 seconds)
 *  
 *  Asynchronous
 *  
 *  NO Limit Messages / Limit Messages
 *  
 *  No Key
 *	
 * 	Message Template : Hello World! CUSTOM_ID - SEND_DATE
 *  
 *  Different consumers can be used
 *   - Java consumer with appropriate configuration
 *   - kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --property print.key=true --from-beginning
 * 
 */

public class AppProducerAsyncWithRunnable {
	
    public static void main(String[] args) {
    	
    	// Option 1: No limit messages
    	ProducerAsyncRunnable producerNoLimitThread = ProducerRunnableFactory.createProducerAsyncRunnable(KafkaConstant.DEFAULT_CLIENT_ID, KafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, DemoConstant.TOPIC);
        Thread t1 = new Thread(producerNoLimitThread);
        t1.start();
        
//        // Option 2: limit messages
//    	ProducerAsyncWithLimitRunnable producerLimitThread = ProducerRunnableFactory.createProducerAsyncWithLimitRunnable(KafkaConstant.DEFAULT_CLIENT_ID, KafkaConstant.BOOTSTRAP_SERVERS, KafkaConstant.TOPIC);
//        Thread t2 = new Thread(producerLimitThread);
//        t2.start();

    }

}
