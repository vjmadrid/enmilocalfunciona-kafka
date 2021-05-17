package com.acme.kafka.consumer.runnable.executorservice;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.runnable.async.ProducerAsyncRunnable;
import com.acme.kafka.producer.runnable.group.ProducerGroupRunnable;

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

public class AppConsumerWithExecutorService {

	private static final int NUM_PRODUCERS = 3;
	
    public static void main(String[] args) {
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Shutting down");
//                for(ProducerAsyncRunnable producerRunner : producerList) {
//                	// close producer
//                }
            }
        });

    	ProducerGroupRunnable<ProducerAsyncRunnable> producerGroup =  new ProducerGroupRunnable<>(GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, DemoConstant.TOPIC, NUM_PRODUCERS);

    	List<ProducerAsyncRunnable> producerAsyncRunnableList = producerGroup.getKafkaProducerRunnableList();
    	
    	final ExecutorService executorService = Executors.newFixedThreadPool(producerAsyncRunnableList.size());
    	
    	// Execute each ProducerAsyncRunnable -> own thread
    	producerAsyncRunnableList.forEach(executorService::submit);
    }

}
