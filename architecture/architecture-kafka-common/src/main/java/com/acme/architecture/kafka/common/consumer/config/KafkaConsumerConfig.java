package com.acme.architecture.kafka.common.consumer.config;

import java.util.Properties;

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

}
