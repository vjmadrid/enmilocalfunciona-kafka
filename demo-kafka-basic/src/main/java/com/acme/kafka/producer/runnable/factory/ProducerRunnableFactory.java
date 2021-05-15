package com.acme.kafka.producer.runnable.factory;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.producer.config.KafkaProducerConfig;
import com.acme.kafka.producer.config.KafkaProducerPropertiesConfig;
import com.acme.kafka.producer.runnable.async.ProducerAsyncRunnable;
import com.acme.kafka.producer.runnable.async.ProducerAsyncWithLimitRunnable;
import com.acme.kafka.producer.runnable.sync.ProducerSyncRunnable;
import com.acme.kafka.util.KafkaPropertiesUtil;

public class ProducerRunnableFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProducerRunnableFactory.class);
	
	protected ProducerRunnableFactory() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static ProducerAsyncRunnable createProducerAsyncRunnable(final String idProducer, final String brokers, final String topic) {
		LOG.info("[ProducerRunnableFactory] *** createProducerAsyncRunnable ***");
		final ProducerAsyncRunnable runnable = new ProducerAsyncRunnable();
		
		// Create producer properties
		Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue(brokers, idProducer);
		
		LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaProducerProperties, LOG);

		// Create Kafka producer
		runnable.setKafkaProducer(new KafkaProducer<>(kafkaProducerProperties));
		
		// Prepare topic
		runnable.setTopic(topic);
		
		return runnable;
	}
	
	public static ProducerAsyncWithLimitRunnable createProducerAsyncWithLimitRunnable(final String idProducer, final String brokers, final String topic) {
		LOG.info("[ProducerRunnableFactory] *** createProducerAsyncWithLimitRunnable ***");
		final ProducerAsyncWithLimitRunnable runnable = new ProducerAsyncWithLimitRunnable();
		
		// Create producer properties
		Properties kafkaProducerProperties = new Properties();
		
		KafkaProducerPropertiesConfig.setupClientIdAndBootstrap(kafkaProducerProperties, idProducer, brokers);
		KafkaProducerPropertiesConfig.setupSerializerStringKeyStringValue(kafkaProducerProperties);
		
		LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaProducerProperties, LOG);
		
		// Create Kafka producer
		runnable.setKafkaProducer(new KafkaProducer<>(kafkaProducerProperties));
		
		// Prepare topic
		runnable.setTopic(topic);
		
		return runnable;
	}
	
	public static ProducerSyncRunnable createProducerSyncRunnable(final String idProducer, final String brokers,  final String topic) {
		LOG.info("[ProducerRunnableFactory] *** createProducerSyncRunnable ***");
		final ProducerSyncRunnable runnable = new ProducerSyncRunnable();
		
		// Create producer properties
		Properties kafkaProducerProperties = new Properties();
		
		KafkaProducerPropertiesConfig.setupClientIdAndBootstrap(kafkaProducerProperties, idProducer, brokers);
		KafkaProducerPropertiesConfig.setupSerializerStringKeyStringValue(kafkaProducerProperties);
		
		LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaProducerProperties, LOG);
		
		// Create Kafka producer
		runnable.setKafkaProducer(new KafkaProducer<>(kafkaProducerProperties));
		
		// Prepare topic
		runnable.setTopic(topic);
		
		return runnable;
	}
	
	

}
