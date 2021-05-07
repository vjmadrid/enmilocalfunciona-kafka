package com.acme.kafka.producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 *	Producer configuration class
 * 
 * 	Gets the configuration of ad-hoc values and access to very specific properties 
 * 	from the application.properties file.
 *   
 */

@Configuration
@EnableKafka
public class KafkaProducerConfig {
	
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
//	    //The batch.size in bytes of record size  (0 disables batching(
//      props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768);
		
//		props.put(ProducerConfig.SEND_BUFFER_CONFIG, 100000000);
		
//	    //Control how much time Producer blocks before throwing BufferExhaustedException.
//		props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000);
//		
//		//Linger how much to wait for other records before sending the batch (over the network)
//		props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
//		props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 6000);
		
//	    // The total bytes of memory the producer can use to buffer records waiting to be sent
//	    // to the Kafka broker. If records are sent faster than broker can handle than
//	    // the producer blocks. Used for compression and in-flight records.
//		//props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, properties.getBufferSize());
//		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1000000000);
		
		// See https://kafka.apache.org/documentation/#producerconfigs for more properties

		return props;
	}

	@Bean
	public ProducerFactory<String, String> producerFactoryString() {
		return new DefaultKafkaProducerFactory<>(producerConfigsString());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplateString() {
		return new KafkaTemplate<>(producerFactoryString());
	}

}
