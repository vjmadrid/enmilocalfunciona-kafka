package com.acme.kafka.consumer.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.constant.KafkaConstant;

public class KafkaConsumerConfig {

	private KafkaConsumerConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static Properties consumerConfigsStringKeyStringValue(String idConsumer, String brokers, String groupId) {
		Properties kafkaConsumerProperties = new Properties();
		
		KafkaConsumerPropertiesConfig.setupClientIdAndBootstrap(kafkaConsumerProperties, idConsumer, brokers, groupId);
		KafkaConsumerPropertiesConfig.setupSerializerStringKeyStringValue(kafkaConsumerProperties);
		KafkaConsumerPropertiesConfig.setupBasic(kafkaConsumerProperties);
		
		// Other values
	
		return kafkaConsumerProperties;
	}

	public static Properties consumerConfigsStringKeyStringValue() {
		Properties kafkaConsumerProperties = consumerConfigsStringKeyStringValue(KafkaConstant.DEFAULT_CONSUMER_CLIENT_ID, KafkaConstant.DEFAULT_BOOTSTRAP_SERVERS,
				DemoConstant.GROUP_ID);

		// Other values
		kafkaConsumerProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		kafkaConsumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		kafkaConsumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);

		return kafkaConsumerProperties;
	}

}
