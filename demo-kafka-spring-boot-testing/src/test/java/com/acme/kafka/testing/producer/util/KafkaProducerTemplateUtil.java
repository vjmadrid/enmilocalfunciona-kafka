package com.acme.kafka.testing.producer.util;

import java.util.Map;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

public final class KafkaProducerTemplateUtil {

	private KafkaProducerTemplateUtil() {
		throw new IllegalStateException("KafkaProducerTemplateUtil");
	}

	public static KafkaTemplate<String, String> generateKafkaTemplate(EmbeddedKafkaBroker embeddedKafkaBroker) {
		// SetUp producer properties
		Map<String, Object> kafkaProducerProperties = KafkaProducerPropertiesUtil.generateKafkaProducerProperties(embeddedKafkaBroker);

		// Create Kafka producer factory
		ProducerFactory<String, String> producerFactory = KafkaProducerFactoryUtil.generateKafkaProducerFactory(kafkaProducerProperties);

		// Create a Kafka template
		return new KafkaTemplate<>(producerFactory);
	}
	
	public static KafkaTemplate<Integer, String> generateKafkaTemplateProducer(Map<String, Object> producerProps) {
	    ProducerFactory<Integer, String> producerFactory =
	              new DefaultKafkaProducerFactory<Integer, String>(producerProps);
	    KafkaTemplate<Integer, String> template = new KafkaTemplate<>(producerFactory);
	    return template;
	}
	
	

}
