package com.acme.kafka.testing.consumer.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

public class KafkaConsumerPropertiesUtil {

	private KafkaConsumerPropertiesUtil() {
		throw new IllegalStateException("KafkaConsumerPropertiesUtil");
	}

	public static Map<String, Object> generateKafkaConsumerProperties(EmbeddedKafkaBroker embeddedKafkaBroker) {

		Map<String, Object> kafkaconsumerProperties = new HashMap<>();

		kafkaconsumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString());
		kafkaconsumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-1");
		kafkaconsumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		kafkaconsumerProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10");
		kafkaconsumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000");
		kafkaconsumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		kafkaconsumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		kafkaconsumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		return kafkaconsumerProperties;
	}

}
