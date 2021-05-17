package com.acme.kafka.producer.runnable.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.runnable.async.ProducerAsyncRunnable;
import com.acme.kafka.producer.runnable.factory.ProducerRunnableFactory;
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

public class AppProducerWithExecutorService2 {

	private static final int NUM_PRODUCERS = 3;
	
	public static List<ProducerAsyncRunnable> producerList = new ArrayList<>();

	
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
    	

    	ExecutorService executorService = Executors.newFixedThreadPool(NUM_PRODUCERS);
    	
        for (Integer threads = 0; threads < NUM_PRODUCERS; threads++) {
        	ProducerAsyncRunnable producerAsyncRunnable = ProducerRunnableFactory.createProducerAsyncRunnable("producer-"+threads+"-"+System.currentTimeMillis(), GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, DemoConstant.TOPIC);
        	producerList.add(producerAsyncRunnable);
        	executorService.submit(producerAsyncRunnable);
        }
    	
    	

    }

}
