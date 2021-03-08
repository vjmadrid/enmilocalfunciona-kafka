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


public class BasicProducerWithKey {
	
	private static final Logger LOG = LoggerFactory.getLogger(BasicProducerWithKey.class);

	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "topic-1";
    
    public static final int NUM_MESSAGES = 10;
    public static final String MESSAGE_TEMPLATE = "Hello World! %s - %s";
    public static final String ID_TEMPLATE = "id_%s";
    public static final int NUM_SECONDS_DELAY_MESSAGE = 2;
        
    public static void main(String[] args) throws InterruptedException, ExecutionException {

    	// Create producer properties
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);
        
        for (int i=1; i<=NUM_MESSAGES; i++ ) {
        	String message = String.format(MESSAGE_TEMPLATE, i, new Date().toString());
        	String key = String.format(ID_TEMPLATE, i);
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, key, message);
            
            // Send data asynchronous
            LOG.info("[BasicProducerWithKey] sending message='"+message+"' to topic='"+TOPIC+"'");
            LOG.info(" * Key: " + key);
            
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                   
                    if (e == null) {
                    	LOG.info("Received new metadata \n" +
                                "Topic:" + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp());
                    } else {
                    	LOG.error("Error while producing", e);
                    }
                    
                }
            }).get();
            
            TimeUnit.SECONDS.sleep(NUM_SECONDS_DELAY_MESSAGE);
        }
        
        // Flush data
        producer.flush();
        
        // Flush + close producer
        producer.close();
        
        LOG.info("[BasicProducerWithKey] *** End ***");
    }
 
}
