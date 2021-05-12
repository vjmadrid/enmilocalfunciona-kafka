package com.acme.kafka.producer.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.acme.kafka.constant.KafkaConstant;

public  class KafkaProducerConfig {
	
	private KafkaProducerConfig() {
		throw new IllegalStateException("KafkaProducerConfig");
	}
	
	public static Properties producerConfigsStringKeyStringValue(String brokers) {
		Properties kafkaProducerProperties = new Properties();
        kafkaProducerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        
        //Option 1 : Used Class
        kafkaProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
//      //Option 2 : Used String
//      kafkaProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//      kafkaProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
      
        // Other values
        //kafkaProducerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        //kafkaProducerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        //kafkaProducerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //kafkaProducerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        //kafkaProducerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        
        return kafkaProducerProperties;
	}

	public static Properties producerConfigsStringKeyStringValue() {
		Properties kafkaProducerProperties = producerConfigsStringKeyStringValue(KafkaConstant.DEFAULT_BOOTSTRAP_SERVERS);

        // Other values
        
        return kafkaProducerProperties;
	}
	
	public static Properties producerConfigsLongKeyStringValue(String brokers) {
		Properties kafkaProducerProperties = new Properties();
        kafkaProducerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        
        kafkaProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        kafkaProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        kafkaProducerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        kafkaProducerProperties.put("acks", "all");
        kafkaProducerProperties.put("batch.size", 16384);
        kafkaProducerProperties.put("linger.ms", 1);
        kafkaProducerProperties.put("buffer.memory", 33554432);
        
        return kafkaProducerProperties;
	}

}
