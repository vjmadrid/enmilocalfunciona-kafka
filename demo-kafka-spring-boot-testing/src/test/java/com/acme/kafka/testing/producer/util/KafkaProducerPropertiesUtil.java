package com.acme.kafka.testing.producer.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.testcontainers.shaded.com.google.common.collect.Maps;

public class KafkaProducerPropertiesUtil {

	private KafkaProducerPropertiesUtil() {
		throw new IllegalStateException("KafkaProducerPropertiesUtil");
	}
	
	public static Map<String, Object> generateKafkaProducerProperties(EmbeddedKafkaBroker embeddedKafkaBroker){
		Objects.requireNonNull(embeddedKafkaBroker);
		
		Map<String, Object> kafkaProducerProperties = KafkaTestUtils.producerProps(
				embeddedKafkaBroker
		);
		
		return kafkaProducerProperties;
	}
	
	public static Map<String, Object> generateKafkaProducerStringProperties(String brokerListString, Serializer keySerializer, Serializer valueSerializer) {
		Objects.requireNonNull(brokerListString);
		Objects.requireNonNull(keySerializer);
		Objects.requireNonNull(valueSerializer);
		
		Map<String, Object> kafkaProducerProperties = new HashMap<>();
	    kafkaProducerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerListString);
	    
	    kafkaProducerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
	    kafkaProducerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
	    
	    return kafkaProducerProperties;
	}
	
	public static Map<String, String> generateKafkaProducerStringProperties(Properties producerProperties, Serializer keySerializer, Serializer valueSerializer) {
		Objects.requireNonNull(producerProperties);
		Objects.requireNonNull(keySerializer);
		Objects.requireNonNull(valueSerializer);
		
		Map<String, String> kafkaProducerProperties = Maps.fromProperties(producerProperties);
		
		kafkaProducerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer.toString());
		kafkaProducerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer.toString());
	    
	    return kafkaProducerProperties;
	}
	
}
