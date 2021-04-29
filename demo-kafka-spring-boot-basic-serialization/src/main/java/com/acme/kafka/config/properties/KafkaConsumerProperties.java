package com.acme.kafka.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("spring.kafka")
@Getter
@Setter
public class KafkaConsumerProperties {

	private String bootstrapServers;
	private String acks;
	private String keySerializer;
	private String keyDeserializer;
	private String valueSerializer;
	private String valueDeserializer;
	private int batchSize;
	private int lingerms;
	private long bufferSize;

}
