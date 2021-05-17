package com.acme.kafka.consumer.runnable.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.consumer.runnable.ConsumerRunnable;
import com.acme.kafka.consumer.runnable.factory.ConsumerRunnableFactory;

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

public class AppConsumerWithExecutorService2 {

	private static final int NUM_CONSUMERS = 3;
	
	public static List<ConsumerRunnable> consumerList = new ArrayList<>();

    public static void main(String[] args) {
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Shutting down");
                for(ConsumerRunnable consumerRunnable : consumerList) {
                	consumerRunnable.shutdown();
                }
            }
        });
    	
    	ExecutorService executorService = Executors.newFixedThreadPool(NUM_CONSUMERS);
    	
        for (Integer threads = 0; threads < NUM_CONSUMERS; threads++) {
        	ConsumerRunnable consumerRunnable = ConsumerRunnableFactory.createConsumerRunnable("consumer-"+threads+"-"+System.currentTimeMillis(), GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, DemoConstant.GROUP_ID, DemoConstant.TOPIC);
        			
        	consumerList.add(consumerRunnable);
        	executorService.submit(consumerRunnable);
        }
    	
    	

    }

}
