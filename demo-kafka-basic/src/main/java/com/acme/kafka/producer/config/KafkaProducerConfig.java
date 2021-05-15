package com.acme.kafka.producer.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.acme.kafka.constant.KafkaConstant;

public class KafkaProducerConfig {
	
	private KafkaProducerConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}
	
	public static Properties producerConfigsStringKeyStringValue(String brokers, String idProducer) {
		Properties kafkaProducerProperties = new Properties();
        kafkaProducerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        kafkaProducerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, idProducer);

        //Option 1 : Used Class
        kafkaProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
//      //Option 2 : Used String
//      kafkaProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//      kafkaProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
      
        // Other values
        //kafkaProducerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        
        // ACKs (Default All) : Todas las replicas ISR in-sync tienen que responder para que la escritura se considere válida
        // 	Las replicas in-sync se pueden configurar vía brokers (min.insync.replicas) o bien vía topic
        //  Con valor "ack" : El leader contestará con un ACK exitoso después de que todos los followers hayan escrito el mensaje en el log
        //	Con valor "1" 	: El leader contestará con un ACK exitoso después de escribir en escribir en su log y no espera a que lo hagan sus followers (replicas)
        //kafkaProducerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        //kafkaProducerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //kafkaProducerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        //kafkaProducerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        
        return kafkaProducerProperties;
	}

	public static Properties producerConfigsStringKeyStringValue() {
		Properties kafkaProducerProperties = producerConfigsStringKeyStringValue(KafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, KafkaConstant.DEFAULT_CLIENT_ID);

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
