package com.acme.kafka.consumer.runnable;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;

public class ConsumerRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ConsumerRunnable.class);

	private CountDownLatch latch;

	private KafkaConsumer<String, String> consumer;

	public ConsumerRunnable(String bootstrapServers, String groupId, String topic, CountDownLatch latch) {
		LOG.info("[BasicConsumerRunnable] *** Init ***");
		
		this.latch = latch;

		// Create consumer properties
		Properties consumerProperties = new Properties();
		consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		// Create consumer
		consumer = new KafkaConsumer<>(consumerProperties);

		// Receive data asynchronous
		LOG.info("[BasicConsumerRunnable] Preparing to subscribe {}", Arrays.asList(DemoConstant.TOPIC));
		consumer.subscribe(Arrays.asList(topic));
	}

	@Override
	public void run() {
		LOG.info("[BasicConsumerRunnable] *** Run ***");

		try {
			LOG.info("[BasicConsumerRunnable] Preparing to receive menssages");
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));
				LOG.info("Check records -> Count {}", records.count());
				
				for (ConsumerRecord<String, String> record : records){          	
		            	LOG.info("[*] Received record \n" +
		            			"Key: {} \n" +
		            			"Value: {} \n" +
		                        "Topic: {} \n" +
		                        "Partition: {}\n" +
		                        "Offset: {} \n" +
		                        "Timestamp: {}" , 
		                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
		         }

			}
		} catch (WakeupException e) {
			LOG.info("Received shutdown signal");
		} finally {
			consumer.close();
			latch.countDown();
		}

	}

	public void shutdown() {
		LOG.info("[BasicConsumerRunnable] *** Shutdown ***");
		// interrupt consumer.poll() and throw WakeUpException
		consumer.wakeup();
	}

}
