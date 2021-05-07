package com.acme.kafka.testing.producer.util;

import java.util.Map;
import java.util.Objects;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

public class KafkaProducerFactoryUtil {
	
	private KafkaProducerFactoryUtil() {
		throw new IllegalStateException("KafkaProducerFactoryUtil");
	}
	
	public static ProducerFactory<String, String> generateKafkaProducerFactory(Map<String, Object> kafkaProducerPropertiesMap){
		Objects.requireNonNull(kafkaProducerPropertiesMap);
		
		ProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<String, String>(
				kafkaProducerPropertiesMap,
				new StringSerializer(), 
				new StringSerializer()
		);
		
		return kafkaProducerFactory;
	}

}
