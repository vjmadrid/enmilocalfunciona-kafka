package com.acme.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.consumer.runnable.BasicConsumerRunnable;

public class BasicConsumerWithRunnable {

	private static final Logger LOG = LoggerFactory.getLogger(BasicConsumerWithRunnable.class);

	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
	private static final String TOPIC = "topic-1";
	private static final String GROUP_ID = "my-group";

	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	private static Runnable basicConsumerRunnable = null;

	public static void main(String[] args) {
		LOG.info("Creating consumer runnable");
		basicConsumerRunnable = new BasicConsumerRunnable(BOOTSTRAP_SERVERS, GROUP_ID, TOPIC, countDownLatch);
		
		new BasicConsumerWithRunnable().run();
	}

	private void run() {
		LOG.info("Creating consumer thread");
		new Thread(basicConsumerRunnable).start();

		// Add shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				LOG.info("Capture shutdown hook");
				
				((BasicConsumerRunnable) basicConsumerRunnable).shutdown();
				
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
			LOG.info("Application is closing");
		}
	}

}