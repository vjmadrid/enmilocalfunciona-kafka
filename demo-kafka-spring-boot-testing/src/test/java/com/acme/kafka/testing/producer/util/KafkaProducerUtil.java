package com.acme.kafka.testing.producer.util;

import java.util.Map;

import org.apache.kafka.clients.producer.Producer;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

public final class KafkaProducerUtil {

	private KafkaProducerUtil() {
		throw new IllegalStateException("KafkaProducerUtil");
	}

	public static Producer<String, String> generateKafkaProducer(EmbeddedKafkaBroker embeddedKafkaBroker) {
		
		//SetUp Kafka producer properties
		Map<String, Object> kafkaProducerProperties = KafkaProducerPropertiesUtil.generateKafkaProducerProperties(embeddedKafkaBroker);

		//Create a Kafka producer factory (Spring wrapper)
		ProducerFactory<String, String> kafkaProducerFactory = KafkaProducerFactoryUtil.generateKafkaProducerFactory(kafkaProducerProperties);

		// Create a Kafka Producer
		return kafkaProducerFactory.createProducer();
	}

}
