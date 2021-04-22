package com.acme.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.consumer.runnable.BasicConsumerRunnable;

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

public class BasicConsumerWithRunnable {

	private static final Logger LOG = LoggerFactory.getLogger(BasicConsumerWithRunnable.class);

	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	
	private static Runnable basicConsumerRunnable = null;

	public static void main(String[] args) {
		LOG.info("[BasicConsumerWithRunnable] *** Init ***");
		
		basicConsumerRunnable = new BasicConsumerRunnable(DemoConstant.BOOTSTRAP_SERVERS, DemoConstant.GROUP_ID, DemoConstant.TOPIC, countDownLatch);
		
		new BasicConsumerWithRunnable().run();
	}

	private void run() {
		LOG.info("[BasicConsumerWithRunnable] *** Run ***");
		
		LOG.info("[BasicConsumerWithRunnable] Creating consumer thread");
		new Thread(basicConsumerRunnable).start();

		// Add shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				LOG.info("[BasicConsumerWithRunnable] Capture shutdown hook");
				
				((BasicConsumerRunnable) basicConsumerRunnable).shutdown();
				
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				LOG.info("[BasicConsumerWithRunnable] Application has exited");
			}
		));

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			LOG.error("Application got interrupted", e);
		} finally {
			LOG.info("[BasicConsumerWithRunnable] *** Close ***");
		}
	}

}