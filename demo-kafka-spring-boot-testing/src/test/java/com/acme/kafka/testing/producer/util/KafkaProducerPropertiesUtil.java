package com.acme.kafka.testing.producer.util;

import java.util.Map;

import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

public class KafkaProducerPropertiesUtil {

	private KafkaProducerPropertiesUtil() {
		throw new IllegalStateException("KafkaProducerPropertiesUtil");
	}
	
	public static Map<String, Object> generateKafkaProducerProperties(EmbeddedKafkaBroker embeddedKafkaBroker){
		
		Map<String, Object> kafkaProducerProperties = KafkaTestUtils.producerProps(
				embeddedKafkaBroker
		);
		
		return kafkaProducerProperties;
	}
	
}
