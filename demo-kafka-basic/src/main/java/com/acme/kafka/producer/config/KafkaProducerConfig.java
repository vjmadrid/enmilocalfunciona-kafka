package com.acme.kafka.producer.config;

import java.util.Properties;

public class KafkaProducerConfig {

	private KafkaProducerConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static Properties producerConfigsStringKeyStringValue(final String idProducer, final String brokers) {
		Properties kafkaProducerProperties = new Properties();
		
		KafkaProducerPropertiesConfig.setupClientIdAndBootstrap(kafkaProducerProperties, idProducer, brokers);
		KafkaProducerPropertiesConfig.setupSerializerStringKeyStringValue(kafkaProducerProperties);
		KafkaProducerPropertiesConfig.setupBasic(kafkaProducerProperties);
		
		return kafkaProducerProperties;
	}

	public static Properties producerConfigsLongKeyStringValue(final String idProducer,final String brokers) {
		Properties kafkaProducerProperties = new Properties();
		
		KafkaProducerPropertiesConfig.setupClientIdAndBootstrap(kafkaProducerProperties, idProducer, brokers);
		KafkaProducerPropertiesConfig.setupSerializerLongKeyStringValue(kafkaProducerProperties);
		KafkaProducerPropertiesConfig.setupBasic(kafkaProducerProperties);

		return kafkaProducerProperties;
	}

}
