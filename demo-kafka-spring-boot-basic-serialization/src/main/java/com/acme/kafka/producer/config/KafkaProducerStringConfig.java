package com.acme.kafka.producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
@EnableKafka
public class KafkaProducerStringConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> producerConfigsString() {
		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

//		props.put(ProducerConfig.RETRIES_CONFIG, 3 );
//		props.put(ProducerConfig.ACKS_CONFIG, "all");
//
//		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10);
//		props.put(ProducerConfig.SEND_BUFFER_CONFIG, 100000000);
//		props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000);
//		
//		props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
//		props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 6000);
//		//props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, properties.getBufferSize());
//		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1000000000);

		return props;
	}

	@Bean
	public ProducerFactory<String, String> producerFactoryString() {
		// Option 1
		return new DefaultKafkaProducerFactory<>(producerConfigsString());
		
//        // Option 2
//		return new DefaultKafkaProducerFactory<>(producerConfigsString(),
//              new StringSerializer(),
//              new StringSerializer());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplateString() {
		return new KafkaTemplate<>(producerFactoryString());
	}
	
	@Bean
	public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(ProducerFactory<String, String> pf,
			ConcurrentKafkaListenerContainerFactory<String, String> factory) {
		ConcurrentMessageListenerContainer<String, String> replyContainer = factory.createContainer("topic-example");
		replyContainer.getContainerProperties().setMissingTopicsFatal(false);
		replyContainer.getContainerProperties().setGroupId("group-example");
		return new ReplyingKafkaTemplate<>(pf, replyContainer);
	}

	@Bean
	public KafkaTemplate<String, String> replyTemplate(ProducerFactory<String, String> pf,
			ConcurrentKafkaListenerContainerFactory<String, String> factory) {
		KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(pf);
		factory.getContainerProperties().setMissingTopicsFatal(false);
		factory.setReplyTemplate(kafkaTemplate);
		return kafkaTemplate;
	}

}
