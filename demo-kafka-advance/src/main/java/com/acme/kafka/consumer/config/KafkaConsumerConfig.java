package com.acme.kafka.consumer.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.kafka.constant.DemoConstant;

public class KafkaConsumerConfig {

	private KafkaConsumerConfig() {
		throw new IllegalStateException("KafkaConsumerConfig");
	}

	public static Properties consumerConfigsStringKeyStringValue(String brokers, String groupId) {
		Properties kafkaConsumerProperties = new Properties();
		kafkaConsumerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);

		// Option 1 : Used Class
		kafkaConsumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		kafkaConsumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());

//      //Option 2 : Used String
//      kafkaProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//      kafkaProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 

		// Other values
		kafkaConsumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		kafkaConsumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		kafkaConsumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return kafkaConsumerProperties;
	}

	public static Properties consumerConfigsStringKeyStringValue() {
		Properties kafkaConsumerProperties = consumerConfigsStringKeyStringValue(GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS,
				DemoConstant.GROUP_ID);

		// Other values
		kafkaConsumerProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		kafkaConsumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		kafkaConsumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);

		return kafkaConsumerProperties;
	}

}
