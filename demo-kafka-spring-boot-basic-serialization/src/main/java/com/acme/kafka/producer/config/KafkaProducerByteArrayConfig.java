package com.acme.kafka.producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class KafkaProducerByteArrayConfig {
	
	@Autowired
    private KafkaProperties kafkaProperties;

	@Bean
	public Map<String, Object> producerConfigsByteArray() {
		Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
		
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

		return props;
	}

	@Bean
	public ProducerFactory<String, String> producerFactoryByteArray() {
		return new DefaultKafkaProducerFactory<>(producerConfigsByteArray());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplateByteArray() {
		return new KafkaTemplate<>(producerFactoryByteArray());
	}

}
