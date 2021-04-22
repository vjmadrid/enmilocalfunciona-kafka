package com.acme.kafka.producer;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.callback.CustomProducerCallback;

/**
 * 	Help
 *  
 *  Different consumers can be used
 *   - Java consumer with appropriate configuration
 *   - kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --property print.key=true --from-beginning
 * 
 */

public class BasicProducerWithKey {
	
	private static final Logger LOG = LoggerFactory.getLogger(BasicProducerWithKey.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
    	LOG.info("[BasicProducerWithKey] *** Init ***");

    	// Create producer properties
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, DemoConstant.BOOTSTRAP_SERVERS);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);
        
        for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	String key = String.format(DemoConstant.KEY_TEMPLATE, i);
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(DemoConstant.TOPIC, key, message);
            
            // Send data asynchronous
            LOG.info("[BasicProducerWithKey] sending message='{}' to topic='{}'", message, DemoConstant.TOPIC);
            LOG.info(" * Key: {}", key);
            
            producer.send(record, new CustomProducerCallback()).get();
            
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }
        
        // Flush data
        producer.flush();
        
        // Flush + close producer
        producer.close();
        
        LOG.info("[BasicProducerWithKey] *** End ***");
    }
 
}
