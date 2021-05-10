package com.acme.kafka.producer.sync.runnable;

import com.acme.kafka.constant.KafkaConstant;

/**
 * 	Sends a set of messages defined as "String" and with a delay between them (2 seconds)
 *  
 *  Synchronous
 *  
 *  NO Limit Messages
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

public class AppProducerSyncWithRunnable {
	
    public static void main(String[] args) {
    	
    	// Option 1: No limit messages
    	ProducerSyncRunnable producerNoLimitThread = new ProducerSyncRunnable(KafkaConstant.BOOTSTRAP_SERVERS, KafkaConstant.TOPIC);
        Thread t1 = new Thread(producerNoLimitThread);
        t1.start();
       
    }

}
