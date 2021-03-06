package com.acme.kafka.consumer.async.manual;

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

import com.acme.architecture.kafka.common.constant.GlobalConsumerKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalKafkaTemplateConstant;
import com.acme.architecture.kafka.common.consumer.config.KafkaConsumerConfig;
import com.acme.architecture.kafka.common.util.KafkaPropertiesUtil;
import com.acme.kafka.constant.DemoConstant;

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

public class AppAsyncManualWithSyncOffsetCommitConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(AppAsyncManualWithSyncOffsetCommitConsumer.class);
    
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("*** Init ***");
    	
    	// Create consumer properties
        Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(GlobalConsumerKafkaConstant.DEFAULT_CONSUMER_CLIENT_ID, GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, GlobalConsumerKafkaConstant.DEFAULT_GROUP_ID);
        
        kafkaConsumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaConsumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        
        LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaConsumerProperties, LOG);

        // Create Kafka consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        
        // Define topic
        String topic = DemoConstant.TOPIC;
        
        // Subscribe topic
        LOG.info("Preparing to subscribe {}", Arrays.asList(topic));
        kafkaConsumer.subscribe(Arrays.asList(topic));
        
        // Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        LOG.info("Preparing to receive menssages");
        try {
        	
	        while(true){
	        	// Create consumer records
	            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
	            LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORDS, consumerRecords.count(), consumerRecords.partitions().size());
	
	            // Show Consumer Record info
	            for (ConsumerRecord<String, String> record : consumerRecords){          	
	            	LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORD , 
	                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
	            }
	            
	            // When ENABLE_AUTO_COMMIT_CONFIG
	            // 	* false : use commit
	            //  * true : No use commit
	            
	            LOG.info("Manual commit offset asynchronous -> Count {}", consumerRecords.count());
                try {
                	kafkaConsumer.commitAsync();
                } catch (CommitFailedException e) {
                	LOG.error("Manual Commit Offset Asynchronous failed " + e);
                }
	            
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
	            Thread.sleep(2000);
	           
	        }
        }
        finally {
        	try {
        		LOG.info("Manual commit offset synchronous");
        		kafkaConsumer.commitSync();
            } finally {
            	// Close consumer
            	kafkaConsumer.close();
            }
        }
        
    }
    

}