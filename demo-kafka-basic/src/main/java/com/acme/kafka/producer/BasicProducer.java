package com.acme.kafka.producer;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BasicProducer {
	
	private static final Logger LOG = LoggerFactory.getLogger(BasicProducer.class);
	
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "topic-1";
    
    public static final int NUM_MESSAGES = 10;
    public static final String MESSAGE_TEMPLATE = "Hello World! %s - %s";
    public static final int NUM_SECONDS_DELAY_MESSAGE = 2;
        
    public static void main(String[] args) throws InterruptedException {

    	// Create producer properties
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);

        for (int i=1; i<=NUM_MESSAGES; i++ ) {
        	String message = String.format(MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, message);
            
            // Send data asynchronous
            LOG.info("[BasicProducer] sending message='"+message+"' to topic='"+TOPIC+"'");
            producer.send(record);
            
            TimeUnit.SECONDS.sleep(NUM_SECONDS_DELAY_MESSAGE);
        }

        // Flush data
        producer.flush();
        
        // Flush + close producer
        producer.close();
        
        LOG.info("[BasicProducer] *** End ***");
    }

}
