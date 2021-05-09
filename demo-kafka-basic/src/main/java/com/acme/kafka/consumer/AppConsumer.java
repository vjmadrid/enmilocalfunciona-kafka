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
 *  ENABLE_AUTO_COMMIT_CONFIG = True
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
        Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue();

        // Create Kafka consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        
        // Create topic list with Collections
        //  * with Collections : Collections.singletonList(DemoConstant.TOPIC)
        //  * with Arrays : Arrays.asList(DemoConstant.TOPIC);
        List<String> topicList = Collections.singletonList(DemoConstant.TOPIC);
        
        // Subscribe topic
        kafkaConsumer.subscribe(topicList);
        
        // Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        LOG.info("Preparing to receive menssages");
        try {
        	
	        while(true){
	        	// Create consumer records
	            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
	            LOG.info("Check records -> \n" +
	            		"\tRecord Count: {} \n" +
	            		"\tPartition Count: {} ", consumerRecords.count(), consumerRecords.partitions().size());
	
	            // Show Consumer Record info
	            
	            // * Option 1 : With "for"
	            for (ConsumerRecord<String, String> record : consumerRecords){          	
	            	LOG.info("[*] Received record \n" +
	            			"\tKey: {} \n" +
	            			"\tValue: {} \n" +
	                        "\tTopic: {} \n" +
	                        "\tPartition: {}\n" +
	                        "\tOffset: {} \n" +
	                        "\tTimestamp: {}" , 
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
	            
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
	            Thread.sleep(2000);
	           
	        }
        }
        finally {
        	// Close consumer
        	kafkaConsumer.close();
        }
        
    }
    
}