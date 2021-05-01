package com.acme.kafka.producer;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.config.KafkaProducerConfig;

/**
 * 	Sends a set number of messages (10) defined as "String" and with a delay between them (2 seconds)
 *  
 *  Message Template : Hello World! CUSTOM_ID - SEND_DATE
 *  
 *  Incorporates the use of a key 
 *  
 *  Different consumers can be used
 *   - Java consumer with appropriate configuration
 *   - kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --property print.key=true --from-beginning
 * 
 */

public class BasicProducerWithKey {
	
	private static final Logger LOG = LoggerFactory.getLogger(BasicProducerWithKey.class);

    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("[BasicProducerWithKey] *** Init ***");

    	// Create producer properties
        Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsString();

        // Create producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProducerProperties);
        
        LOG.info("[BasicProducerWithKey] Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
        for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	// Prepare message
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	// Prepare key
        	String key = String.format(DemoConstant.KEY_TEMPLATE, i);
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(DemoConstant.TOPIC, key, message);
            
            // Send data asynchronous
            LOG.info("[BasicProducerWithKey] sending message='{}' to topic='{}'", message, DemoConstant.TOPIC);
            LOG.info(" * Key: {}", key);
            
            kafkaProducer.send(record);
            
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }
        
        // Flush data
        kafkaProducer.flush();
        
        // Flush + close producer
        kafkaProducer.close();
        
        LOG.info("[BasicProducerWithKey] *** End ***");
    }
 
}
