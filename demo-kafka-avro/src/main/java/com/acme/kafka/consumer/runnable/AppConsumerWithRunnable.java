package com.acme.kafka.consumer.runnable;

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

public class AppConsumerWithRunnable {
	
    public static void main(String[] args) {
    	
    	ConsumerRunnable consumerThread = new ConsumerRunnable(KafkaConstant.BOOTSTRAP_SERVERS, KafkaConstant.GROUP_ID, KafkaConstant.TOPIC);
    	
        Thread t1 = new Thread(consumerThread);
        t1.start();

    }

}
