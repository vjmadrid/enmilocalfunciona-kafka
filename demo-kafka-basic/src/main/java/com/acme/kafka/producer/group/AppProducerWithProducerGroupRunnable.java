package com.acme.kafka.producer.group;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.constant.KafkaConstant;

/**
 * 	Sends a set of messages defined as "String" and with a delay between them (2 seconds)
 *  
 *  Generate N Producers -> ProducerAsyncRunnable
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

public class AppProducerWithProducerGroupRunnable {

	private static final int NUM_PRODUCERS = 3;
	
    public static void main(String[] args) {

    	ProducerGroupRunnable producerGroup =  new ProducerGroupRunnable(KafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, DemoConstant.TOPIC, NUM_PRODUCERS);
    	producerGroup.executeProducers();

    }

}
