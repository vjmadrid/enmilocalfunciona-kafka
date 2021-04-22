package com.acme.kafka.producer;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;

/**
 * 	Sends a set number of messages (10) defined as "String" and with a delay between them (2 seconds)
 *  
 *  Retrieve meta information about the message being sent directly
 *  
 *  Different consumers can be used
 *   - Java consumer with appropriate configuration
 *   - kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --property print.key=true --from-beginning
 * 
 */

public class BasicProducerWithCallbackAdhoc {
	
	private static final Logger LOG = LoggerFactory.getLogger(BasicProducerWithCallbackAdhoc.class);
	
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
    	LOG.info("[BasicProducerWithCallbackAdhoc] *** Init ***");

    	// Create producer properties
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, DemoConstant.BOOTSTRAP_SERVERS);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); //"org.apache.kafka.common.serialization.StringSerializer"
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); //"org.apache.kafka.common.serialization.StringSerializer"

        // Create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);

        LOG.info("[BasicProducerWithCallbackAdhoc] Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
        for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(DemoConstant.TOPIC, message);
            
            // Send data asynchronous
            LOG.info("[BasicProducerWithCallbackAdhoc] sending message='{}' to topic='{}'", message, DemoConstant.TOPIC);
            producer.send(record, new Callback() {
            	
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                   
                	if (exception == null) {
                    	LOG.info("Received metadata \n" +
                                "Topic: {} \n" +
                                "Partition: {} \n" +
                                "Offset: {} \n" +
                                "Timestamp: {}",
                                metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp());
                    } else {
                    	LOG.error("Error while producing message ", exception);
                    }
                    
                }
                
            }).get();
        	
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }
        
        // Flush data
        producer.flush();
        
        // Flush + close producer
        producer.close();

        LOG.info("[BasicProducerWithCallbackAdhoc] *** End ***");
    }

}