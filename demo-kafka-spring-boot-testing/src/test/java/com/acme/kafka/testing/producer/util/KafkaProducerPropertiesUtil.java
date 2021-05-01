package com.acme.kafka.testing.producer.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
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
	
	public static Map<String, Object> generateKafkaProducerProperties() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	    
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    
	    props.put(ProducerConfig.RETRIES_CONFIG, 0);
	    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
	    props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	    
	    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	    
	    return props;
	}
	
}
