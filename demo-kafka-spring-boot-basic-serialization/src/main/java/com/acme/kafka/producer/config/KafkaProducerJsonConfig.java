package com.acme.kafka.producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaProducerJsonConfig {
	
	@Autowired
    private KafkaProperties kafkaProperties;
	
	@Bean
	public Map<String, Object> producerConfigsJson() {
		Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}
	
	@Bean
    public ProducerFactory<String, Object> producerFactoryJson() {
        // Option 1
		return new DefaultKafkaProducerFactory<>(producerConfigsJson());
        
        // Option 2
//        return new DefaultKafkaProducerFactory<>(producerConfigsJson(),
//                new StringSerializer(),
//                new JsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplateJson() {
        return new KafkaTemplate<>(producerFactoryJson());
    }

}
