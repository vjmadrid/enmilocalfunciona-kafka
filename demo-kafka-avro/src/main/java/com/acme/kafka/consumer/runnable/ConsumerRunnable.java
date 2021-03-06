package com.acme.kafka.consumer.runnable;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.consumer.config.KafkaConsumerConfig;

public class ConsumerRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ConsumerRunnable.class);

	private KafkaConsumer<String, String> kafkaConsumer;
	
	private final String topic;
	
	private CountDownLatch countDownLatch;

	public ConsumerRunnable(String bootstrapServers, String groupId, String topic) {
		LOG.info("[ConsumerRunnable] *** Init ***");
		
		// Create consumer properties
		Properties consumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(bootstrapServers, groupId);
		
		// Create Kafka consumer
		kafkaConsumer = new KafkaConsumer<>(consumerProperties);
		
		// Prepare topic
		this.topic = topic;

		// Subscribe topic
		kafkaConsumer.subscribe(Arrays.asList(this.topic));
	}
	
	public ConsumerRunnable(String bootstrapServers, String groupId, String topic, CountDownLatch countDownLatch) {
		LOG.info("[ConsumerRunnable] *** Init ***");
		
		// Create consumer properties
		Properties consumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(bootstrapServers, groupId);
		
		// Create Kafka consumer
		kafkaConsumer = new KafkaConsumer<>(consumerProperties);
		
		// Prepare topic
		this.topic = topic;

		// Subscribe topic
		kafkaConsumer.subscribe(Arrays.asList(this.topic));
		
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		LOG.info("[ConsumerRunnable] *** Run ***");

		LOG.info("Preparing to receive menssages");
		try {
			
			while (true) {
				// Create consumer records
				ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
	            LOG.info("Check records -> \n" +
	            		"\tRecord Count: {} \n" +
	            		"\tPartition Count: {} ", consumerRecords.count(), consumerRecords.partitions().size());
				
	            // Show Consumer Record info
				for (ConsumerRecord<String, String> record : consumerRecords){          	
		            	LOG.info("[*] Received record with ThreadId=[{}] \n" +
		            			"\tKey: {} \n" +
		            			"\tValue: {} \n" +
		                        "\tTopic: {} \n" +
		                        "\tPartition: {}\n" +
		                        "\tOffset: {} \n" +
		                        "\tTimestamp: {}" , 
		                        Thread.currentThread().getId(),record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
		         }
				
				TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);

			}
		} catch (WakeupException e) {
			LOG.info("Received shutdown signal");
		} catch (InterruptedException e) {
			LOG.error("Received interruption signal : {}",e);
		} finally {
			// Close consumer
			kafkaConsumer.close();
			
			countDownLatch.countDown();
		}

	}

	public void shutdown() {
		LOG.info("[ConsumerRunnable] *** Shutdown ***");
		// interrupt consumer.poll() and throw WakeUpException
		kafkaConsumer.wakeup();
	}

}
