package com.acme.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(BasicConsumer.class);

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "topic-1";
    private static final String GROUP_ID = "my-group";
    
    public static void main(String[] args) {
    	
    	// Create consumer properties
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        // Create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProperties);
        
        // Receive data asynchronous
        consumer.subscribe(Arrays.asList(TOPIC));
        
        while(true){
        	// Create consumer records
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));

            for (ConsumerRecord<String, String> record : records){          	
            	LOG.info("Received record \n" +
            			"Key:" + record.key() + "\n" +
            			"Value:" + record.value() + "\n" +
                        "Topic:" + record.topic() + "\n" +
                        "Partition: " + record.partition() + "\n" +
                        "Offset: " + record.offset() + "\n" +
                        "Timestamp: " + record.timestamp());
            }
        }
      
    }
    

}