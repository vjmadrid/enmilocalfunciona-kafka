package com.acme.kafka.testing.consumer.util;

import java.util.Map;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

public class KafkaConsumerFactoryUtil {
	
	private KafkaConsumerFactoryUtil() {
		throw new IllegalStateException("KafkaConsumerFactoryUtil");
	}
	
	public static ConsumerFactory<String, String> generateKafkaProducerFactory(Map<String, Object> kafkaConsumerProperties){
		
		DefaultKafkaConsumerFactory<String, String> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(
				kafkaConsumerProperties
		);
	
		return kafkaConsumerFactory;
	}

}
