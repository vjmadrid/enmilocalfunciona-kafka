package com.acme.kafka.consumer.sync;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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
 *  ENABLE_AUTO_COMMIT_CONFIG = False
 *  
 *  Different producers can be used
 *   - Java producer with appropriate configuration
 *   - kafka-console-producer.sh --broker-list localhost:9092 --topic topic-1
 * 
 */

public class AppSyncOffsetCommitConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(AppSyncOffsetCommitConsumer.class);
    
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("[AppSyncOffsetCommitConsumer] *** Init ***");
    	
    	// Create consumer properties
        Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue();
        
        kafkaConsumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaConsumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

        // Create Kafka consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        
        // Subscribe topic
        kafkaConsumer.subscribe(Arrays.asList(DemoConstant.TOPIC));
        
        // Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        LOG.info("[AppSyncOffsetCommitConsumer] Preparing to receive menssages");
        try {
        	
	        while(true){
	        	// Create consumer records
	            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
	            LOG.info("Check records -> \n" +
	            		"\tRecord Count: {} \n" +
	            		"\tPartition Count: {} ", consumerRecords.count(), consumerRecords.partitions().size());
	
	            // Show Consumer Record info
	            for (ConsumerRecord<String, String> record : consumerRecords){          	
	            	LOG.info("[*] Received record \n" +
	            			"\tKey: {} \n" +
	            			"\tValue: {} \n" +
	                        "\tTopic: {} \n" +
	                        "\tPartition: {}\n" +
	                        "\tOffset: {} \n" +
	                        "\tTimestamp: {}" , 
	                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
	            	
	            	// When ENABLE_AUTO_COMMIT_CONFIG
		            // 	* false : use commit
		            //  * true : No use commit
		            
		            LOG.info("Manual commit offset synchronous -> Count {}", consumerRecords.count());
	                try {
	                	// Blocking call -> It will return only after offset is committed
	                	kafkaConsumer.commitSync();
	                } catch (CommitFailedException e) {
	                	LOG.error("Manual commit offset synchronous failed" + e);
	                }
	                
	            }
	            
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