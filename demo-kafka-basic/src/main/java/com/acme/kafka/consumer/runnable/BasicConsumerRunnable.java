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

public class BasicConsumerRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(BasicConsumerRunnable.class);

	private CountDownLatch latch;

	private KafkaConsumer<String, String> consumer;

	public BasicConsumerRunnable(String bootstrapServers, String groupId, String topic, CountDownLatch latch) {
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
		consumer = new KafkaConsumer<String, String>(consumerProperties);

		// Receive data asynchronous
		consumer.subscribe(Arrays.asList(topic));
	}

	@Override
	public void run() {

		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));

				for (ConsumerRecord<String, String> record : records) {
					LOG.info("Received record \n" + "Key:" + record.key() + "\n" + "Value:" + record.value() + "\n"
							+ "Topic:" + record.topic() + "\n" + "Partition: " + record.partition() + "\n" + "Offset: "
							+ record.offset() + "\n" + "Timestamp: " + record.timestamp());
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
		// interrupt consumer.poll() and throw WakeUpException
		consumer.wakeup();
	}

}
