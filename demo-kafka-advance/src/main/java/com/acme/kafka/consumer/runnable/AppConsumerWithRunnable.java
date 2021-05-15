package com.acme.kafka.consumer.runnable;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
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

public class AppConsumerWithRunnable {
	
    public static void main(String[] args) {
    	
    	ConsumerRunnable consumerThread = new ConsumerRunnable(GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, DemoConstant.GROUP_ID, DemoConstant.TOPIC);
    	
        Thread t1 = new Thread(consumerThread);
        t1.start();

    }

}
