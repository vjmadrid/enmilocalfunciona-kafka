package com.acme.kafka.consumer.rebalance;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.consumer.config.KafkaConsumerConfig;

public class ConsumerRebalanceRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ConsumerRebalanceRunnable.class);

	private KafkaConsumer<String, String> kafkaConsumer;
	
	private final String topic;
	
	private Map<TopicPartition, OffsetAndMetadata> processedOffsets = new HashMap<>();
	
	public ConsumerRebalanceRunnable(String bootstrapServers, String groupId, String topic) {
		LOG.info("*** Init ***");
		
		// Create consumer properties
		Properties consumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(bootstrapServers, groupId);
		consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		
		// Create Kafka consumer
		kafkaConsumer = new KafkaConsumer<>(consumerProperties);
		
		// Prepare topic
		this.topic = topic;

		// Subscribe topic
		kafkaConsumer.subscribe(Arrays.asList(this.topic), new CustomConsumerRebalanceListener(processedOffsets));
	}

	@Override
	public void run() {
		LOG.info("*** Run ***");

		try {
			LOG.info("Preparing to receive menssages");
			while (true) {
				ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(2000));
				LOG.info("Check records -> Count {}", records.count());
				
				for (ConsumerRecord<String, String> record : records){          	
		            	LOG.info("[*] Received record with ThreadId=[{}] \n" +
		            			"\tKey: {} \n" +
		            			"\tValue: {} \n" +
		                        "\tTopic: {} \n" +
		                        "\tPartition: {}\n" +
		                        "\tOffset: {} \n" +
		                        "\tTimestamp: {}" , 
		                        Thread.currentThread().getId(),record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
		            	
		            	processedOffsets.put(new TopicPartition(record.topic(),
		                            record.partition()), new
		                            OffsetAndMetadata(record.offset() + 1, "no metadata"));
		         }
				
				LOG.info("Commit Async");
				kafkaConsumer.commitAsync();

			}
		} catch (WakeupException e) {
			LOG.info("Received shutdown signal");
		}catch (Exception e) {
			LOG.error("Unexpected error" + e);
        } finally {
        	
            try {
            	kafkaConsumer.commitSync();
            } finally {
            	kafkaConsumer.close();
            }
            
        }

	}

	public void shutdown() {
		LOG.info("*** Shutdown ***");
		// interrupt consumer.poll() and throw WakeUpException
		kafkaConsumer.wakeup();
	}

}
