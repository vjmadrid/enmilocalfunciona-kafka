package com.acme.kafka.consumer.runnable.factory;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.consumer.config.KafkaConsumerConfig;
import com.acme.kafka.consumer.runnable.ConsumerRunnable;
import com.acme.kafka.util.KafkaPropertiesUtil;

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

}
