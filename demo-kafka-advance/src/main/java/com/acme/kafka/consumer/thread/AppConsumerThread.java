package com.acme.kafka.consumer.thread;

import java.util.Properties;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.consumer.config.KafkaConsumerConfig;

/**
 * 	Receives a set of messages defined as "String" performing "poll" every certain time (2 seconds)
 * 
 * 	No message limit
 *  
 *  Different producers can be used
 *   - Java producer with appropriate configuration
 *   - kafka-console-producer.sh --broker-list localhost:9092 --topic topic-1
 * 
 */

public class AppConsumerThread {

    public static void main(String[] args) {
    	
    	// Create consumer properties
        Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue();

        // Receive data asynchronous
      
        ConsumerThread consumerThread = new ConsumerThread(DemoConstant.TOPIC, kafkaConsumerProperties);
        consumerThread.start();
    }
    
}