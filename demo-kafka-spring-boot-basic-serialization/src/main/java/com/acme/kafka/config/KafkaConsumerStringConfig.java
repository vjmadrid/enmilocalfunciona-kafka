package com.acme.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@EnableKafka
public class KafkaConsumerStringConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;

	@Bean
	public Map<String, Object> consumerConfigsString() {
		Map<String, Object> props = new HashMap<>();
		
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		
//		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "15000");
//		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "50000");
//		props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "60000");
//		props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "50000");
//		props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG,200);
//		props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,10485760);
		
		return props;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactoryString() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigsString());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryString());

		return factory;
	}
	
	@Bean
	public DefaultKafkaConsumerFactory defaultKafkaConsumerFactory() {
		DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<Integer, String>(
				consumerConfigsString());
		return cf;
	}
	
}
