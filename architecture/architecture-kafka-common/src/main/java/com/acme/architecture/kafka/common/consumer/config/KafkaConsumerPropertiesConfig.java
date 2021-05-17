package com.acme.architecture.kafka.common.consumer.config;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerPropertiesConfig {
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerPropertiesConfig.class);

	private KafkaConsumerPropertiesConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}
	
	public static void setupClientIdAndBootstrap(Properties kafkaConsumerProperties, String idConsumer, String brokers, String groupId) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupClientIdAndBootstrap ***");
		Objects.requireNonNull(kafkaConsumerProperties);
		Objects.requireNonNull(idConsumer);
		Objects.requireNonNull(groupId);
		Objects.requireNonNull(brokers);

		// "bootstrap.servers" : Set "initial" server / broker connections (list of broker addresses) -> host/port pairs with comma separated list
		kafkaConsumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);

		// "client.id" : Set client id (uniquely identifier) -> tracking the source of requests
		kafkaConsumerProperties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, idConsumer);
		
		// "group.id" : Set group id
		kafkaConsumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	}
	
	public static void setupSerializerStringKeyStringValue(Properties kafkaConsumerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupSerializerStringKeyStringValue ***");
		Objects.requireNonNull(kafkaConsumerProperties);

		// "key.serializer" : Set key serializer class -> StringSerializer for String
		kafkaConsumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		//kafkaConsumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		
		// "value.serializer": Set value serializer class -> StringSerializer for String
		kafkaConsumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		//kafkaConsumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
	}
	
	public static void setupBasic(Properties kafkaConsumerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupBasic ***");
		Objects.requireNonNull(kafkaConsumerProperties);
		
		// "enable.auto.commit" : Set commit
		kafkaConsumerProperties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		
		// "auto.offset.reset" : Set offset
		kafkaConsumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
//		kafkaConsumerProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
//		kafkaConsumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
//		kafkaConsumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
	}

}
