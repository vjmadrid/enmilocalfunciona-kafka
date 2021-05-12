package com.acme.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.acme.model.custom.message.deserializer.CustomMessageDeserializer;
import com.acme.model.custom.message.entity.CustomMessage;

@Configuration
public class KafkaConsumerCustomMessageConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public Map<String, Object> consumerConfigsCustomMessage() {
		Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());

		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomMessageDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "example-group");
		
		props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");

		return props;
	}

	@Bean
	public ConsumerFactory<String, Object> consumerFactoryCustomMessage() {
		//Option 1
		//spring.kafka.consumer.properties.spring.json.trusted.packages=*
		
		//Option 2
//		final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
//		jsonDeserializer.addTrustedPackages("*");
//		
//		return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(),
//				jsonDeserializer);
		
		//Option 3
		return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                new JsonDeserializer<>().trustedPackages("*"));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactoryJson() {
		
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory(consumerFactoryCustomMessage());

		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, CustomMessage> customMessageConsumerFactoryJson() {
		return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                new JsonDeserializer<>(CustomMessage.class).trustedPackages("*"));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CustomMessage> kafkaListenerContainerFactoryCustomMessageJson() {
		
		ConcurrentKafkaListenerContainerFactory<String, CustomMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory(customMessageConsumerFactoryJson());

		return factory;
	}

}
