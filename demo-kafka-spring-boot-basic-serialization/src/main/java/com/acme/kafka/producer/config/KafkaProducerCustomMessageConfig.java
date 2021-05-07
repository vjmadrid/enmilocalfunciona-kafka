package com.acme.kafka.producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.acme.kafka.custom.message.entity.CustomMessage;
import com.acme.kafka.custom.message.serializer.CustomMessageSerializer;

@Configuration
@EnableKafka
public class KafkaProducerCustomMessageConfig {
	
	@Autowired
    private KafkaProperties kafkaProperties;
	
	@Bean
	public Map<String, Object> producerConfigsCustomMessage() {
		Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
		
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomMessageSerializer.class);
		
		return props;
	}
	
	@Bean
    public ProducerFactory<String, CustomMessage> producerFactoryCustomMessage() {
		return new DefaultKafkaProducerFactory<>(producerConfigsCustomMessage());
    }

    @Bean
    public KafkaTemplate<String, CustomMessage> kafkaTemplateCustomMessage() {
        return new KafkaTemplate<>(producerFactoryCustomMessage());
    }

}
