package com.acme.kafka.consumer.runnable;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.constant.KafkaConstant;
import com.acme.kafka.consumer.runnable.factory.ConsumerRunnableFactory;

/**
 * 	Receives a set of messages defined as "String" performing "poll" every certain time (2 seconds)
 * 
 * 	No message limit
 * 
 *  Creates a Thread in charge of consumption
 * 
 *  Uses a runnable case
 *  
 *  Different producers can be used
 *   - Java producer with appropriate configuration
 *   - kafka-console-producer.sh --broker-list localhost:9092 --topic topic-1
 * 
 */

public class AppConsumerWithRunnableAdv {

	private static final Logger LOG = LoggerFactory.getLogger(AppConsumerWithRunnableAdv.class);

	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	
	private static Runnable basicConsumerRunnable = null;

	public static void main(String[] args) {
		LOG.info("*** Init ***");
		
		basicConsumerRunnable = ConsumerRunnableFactory.createConsumerRunnable(KafkaConstant.DEFAULT_CONSUMER_CLIENT_ID, KafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, DemoConstant.GROUP_ID, DemoConstant.TOPIC);
		
		new AppConsumerWithRunnableAdv().run();
	}

	private void run() {
		LOG.info("*** Run ***");
		
		LOG.info("Creating consumer thread");
		new Thread(basicConsumerRunnable).start();

		// Add shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				LOG.info("Capture shutdown hook");
				
				((ConsumerRunnable) basicConsumerRunnable).shutdown();
				
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				LOG.info("Application has exited");
			}
		));

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			LOG.error("Application got interrupted", e);
		} finally {
			LOG.info("*** Close ***");
		}
	}

}