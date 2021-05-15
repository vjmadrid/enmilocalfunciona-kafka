package com.acme.kafka.consumer.rebalance;

import java.util.HashMap;

import com.acme.architecture.kafka.common.constant.GlobalConsumerKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.architecture.kafka.common.consumer.listener.CustomConsumerRebalanceListener;
import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.consumer.runnable.factory.ConsumerRunnableFactory;

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
	
    public static void main(String[] args) throws InterruptedException {
    	CustomConsumerRebalanceListener customConsumerRebalanceListener = new CustomConsumerRebalanceListener(new HashMap<>());
    	ConsumerRebalanceRunnable consumerThread = ConsumerRunnableFactory.createConsumerRebalanceRunnable(GlobalConsumerKafkaConstant.DEFAULT_CONSUMER_CLIENT_ID, GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, GlobalConsumerKafkaConstant.DEFAULT_GROUP_ID, DemoConstant.TOPIC, customConsumerRebalanceListener);

        Thread t1 = new Thread(consumerThread);
        t1.start();
    }

}
