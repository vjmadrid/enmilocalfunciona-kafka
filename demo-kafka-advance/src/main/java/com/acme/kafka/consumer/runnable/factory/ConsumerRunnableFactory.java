package com.acme.kafka.consumer.runnable.factory;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.consumer.config.KafkaConsumerConfig;
import com.acme.architecture.kafka.common.consumer.listener.CustomConsumerRebalanceListener;
import com.acme.architecture.kafka.common.util.KafkaPropertiesUtil;
import com.acme.kafka.consumer.rebalance.ConsumerRebalanceRunnable;
import com.acme.kafka.consumer.rebalance.ConsumerRebalanceStartingOffsetRunnable;
import com.acme.kafka.consumer.runnable.ConsumerRunnable;

public class ConsumerRunnableFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerRunnableFactory.class);
	
	protected ConsumerRunnableFactory() {
		throw new IllegalStateException(this.getClass().getName());
	}
	
	public static ConsumerRunnable createConsumerRunnable(final String idConsumer, final String brokers, final String groupId, final String topic) {
		LOG.info("[ConsumerRunnableFactory] *** createConsumerRunnable ***");
		final ConsumerRunnable runnable = new ConsumerRunnable();
		
		// Create consumer properties
		Properties consumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(idConsumer, brokers, groupId);
				
		LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(consumerProperties, LOG);

		// Create Kafka producer
		runnable.setKafkaConsumer(new KafkaConsumer<>(consumerProperties));
		
		// Prepare topic
		runnable.setTopic(topic);
		
		// Subscribe topic
		runnable.getKafkaConsumer().subscribe(Arrays.asList(runnable.getTopic()));

		return runnable;
	}
	

	public static ConsumerRebalanceRunnable createConsumerRebalanceRunnable(final String idConsumer, final String brokers, final String groupId, final String topic, CustomConsumerRebalanceListener rebalanceListener) {
		LOG.info("[ConsumerRunnableFactory] *** createConsumerRebalanceRunnable ***");
		final ConsumerRebalanceRunnable runnable = new ConsumerRebalanceRunnable();
		
		// Create consumer properties
		Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(idConsumer, brokers, groupId);
		
		kafkaConsumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
				
		LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaConsumerProperties, LOG);

		// Create Kafka producer
		runnable.setKafkaConsumer(new KafkaConsumer<>(kafkaConsumerProperties));
		
		// Prepare topic
		runnable.setTopic(topic);
		
		// Subscribe topic
		runnable.getKafkaConsumer().subscribe(Arrays.asList(runnable.getTopic()), rebalanceListener);

		return runnable;
	}
	
	public static ConsumerRebalanceStartingOffsetRunnable createConsumerRebalanceStartingOffsetRunnable(final String idConsumer, final String brokers, final String groupId, final String topic, CustomConsumerRebalanceListener rebalanceListener,  long startingOffset) {
		LOG.info("[ConsumerRunnableFactory] *** createConsumerRebalanceStartingOffsetRunnable ***");
		final ConsumerRebalanceStartingOffsetRunnable runnable = new ConsumerRebalanceStartingOffsetRunnable();
		
		// Create consumer properties
		Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(idConsumer, brokers, groupId);
		
		kafkaConsumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
				
		LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaConsumerProperties, LOG);

		// Create Kafka producer
		runnable.setKafkaConsumer(new KafkaConsumer<>(kafkaConsumerProperties));
		
		// Prepare topic
		runnable.setTopic(topic);
		
		// Prepare starting offset
		runnable.setStartingOffset(startingOffset);
		
		// Subscribe topic
		runnable.getKafkaConsumer().subscribe(Arrays.asList(runnable.getTopic()), rebalanceListener);

		return runnable;
	}
	
}
