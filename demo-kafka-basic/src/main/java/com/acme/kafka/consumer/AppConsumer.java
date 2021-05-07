package com.acme.kafka.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.consumer.config.KafkaConsumerConfig;

/**
 * 	Receives a set of messages defined as "String" performing "poll" every certain time (2 seconds)
 * 
 * 	No message limit
 *  
 *  Different producers can be used
 *   - Java producer with appropriate configuration
 *   - kafka-console-producer.sh --broker-list localhost:9092 --topic topic-1
 * 
 */

public class AppConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(AppConsumer.class);
    
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("[AppConsumer] *** Init ***");
    	
    	// Create consumer properties
        Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsString();

        // Create consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        
        // Receive data asynchronous
       
        // * Option 1 : Create topic list with Collections
        List<String> topicList = Collections.singletonList(DemoConstant.TOPIC);
        
        // * Option 2 : Create topic list with Arrays
        //List<String> topicList = Arrays.asList(DemoConstant.TOPIC);
        
        LOG.info("Preparing to subscribe {}", topicList);
        kafkaConsumer.subscribe(topicList);
        
        LOG.info("Preparing to receive menssages");
        try {
	        while(true){
	        	// Create consumer records
	            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
	            LOG.info("Check records -> \n" +
	            		"Record Count: {} \n" +
	            		"Partition Count: {} ", consumerRecords.count(), consumerRecords.partitions().size());
	
	            // Show Consumer Record info
	            
	            // * Option 1 : With "for"
	            for (ConsumerRecord<String, String> record : consumerRecords){          	
	            	LOG.info("[*] Received record \n" +
	            			"Key: {} \n" +
	            			"Value: {} \n" +
	                        "Topic: {} \n" +
	                        "Partition: {}\n" +
	                        "Offset: {} \n" +
	                        "Timestamp: {}" , 
	                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
	            }
	            
	            // * Option 2 : With "forEach"
//	            consumerRecords.forEach(record -> {
//	            	LOG.info("[*] Received record \n" +
//	            			"Key: {} \n" +
//	            			"Value: {} \n" +
//	                        "Topic: {} \n" +
//	                        "Partition: {}\n" +
//	                        "Offset: {} \n" +
//	                        "Timestamp: {}" , 
//	                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
//	     
//                });
	            
	            Thread.sleep(200);
	            
	            
	            // When ENABLE_AUTO_COMMIT_CONFIG
	            // 	* false : use commit
	            //  * true : No use commit
	            kafkaConsumer.commitSync();
	            //kafkaConsumer.commitAsync();
	        }
        }
        finally {
        	kafkaConsumer.close();
        }
        
    }
    
}