package com.acme.kafka.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
 *  Asynchronous
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
    	
    	LOG.info("*** Init ***");
    	
    	// Create consumer properties
        Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(GlobalConsumerKafkaConstant.DEFAULT_CONSUMER_CLIENT_ID, GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, GlobalConsumerKafkaConstant.DEFAULT_GROUP_ID);
        
        LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaConsumerProperties, LOG);

        // Create Kafka consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        
        // Define topic
        String topic = DemoConstant.TOPIC;
        
        // Create topic list with Collections
        //  * with Collections : Collections.singletonList(topic)
        //  * with Arrays : Arrays.asList(topic);
        List<String> topicList = Collections.singletonList(topic);
       
        // Subscribe topic
        LOG.info("Preparing to subscribe {}", topicList);
        kafkaConsumer.subscribe(topicList);
        
        // Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        LOG.info("Preparing to receive menssages");
        try {
        	
	        while(true){
	        	// Create consumer records
	            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
	            LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORDS, consumerRecords.count(), consumerRecords.partitions().size());
	
	            // Show Consumer Record info
	            
	            // * Option 1 : With "for"
	            for (ConsumerRecord<String, String> record : consumerRecords){
	            	
	            	LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORD, 
	                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
	            	
	            	Map<String, Object> data = new HashMap<>();
	            	data.put("key", record.key());
	            	data.put("value", record.value());
					data.put("partition", record.partition());
					data.put("offset", record.offset());
					
	            	long latency = (long) (System.nanoTime() - Long.parseLong(record.value())); 
	            	LOG.info("\t * latency='{}' ", latency);
	            }
	            
	            // * Option 2 : With "forEach"
//	            consumerRecords.forEach(record -> {
//	            	LOG.info("KafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORD , 
//	                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
//	     
//                });
	            
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
	            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
	           
	        }
        }
        finally {
        	// Close consumer
        	kafkaConsumer.close();
        }
        
    }
    
}