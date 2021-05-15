package com.acme.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
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
 * 	With message limit (10) and no record count limit (5)
 *  
 *  ENABLE_AUTO_COMMIT_CONFIG = True
 *  
 *  Different producers can be used
 *   - Java producer with appropriate configuration
 *   - kafka-console-producer.sh --broker-list localhost:9092 --topic topic-1
 * 
 */

public class AppConsumerWithLimit {

    private static final Logger LOG = LoggerFactory.getLogger(AppConsumerWithLimit.class);
    
    private static final int NO_RECORD_COUNT_LIMIT = 5;

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
        
        // Subscribe topic
        LOG.info("Preparing to subscribe {}", Arrays.asList(topic));
        kafkaConsumer.subscribe(Arrays.asList(topic));
        
        // Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        LOG.info("Preparing to receive {} menssages", DemoConstant.NUM_MESSAGES);
        try {
        	
        	int readedMessages=0;
        
        	int noReadedRecords = 0;
        	
	        while(true){
	        	// Create consumer records
	            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
	            LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORDS, consumerRecords.count(), consumerRecords.partitions().size());
	            
	            // Check no readed records limit
	            if (consumerRecords.count() == 0) {
	            	noReadedRecords++;
                    if (noReadedRecords > NO_RECORD_COUNT_LIMIT) {
                    	LOG.info("No Readed Records Limit");
                    	break;
                    }
                }

	            // Show Consumer Record info
	            for (ConsumerRecord<String, String> record : consumerRecords){          	
	            	LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORD , 
	                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
	            }
	            
	        	// Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
	            // Check num messages limit
	            readedMessages++;
	            
	            LOG.info("[*] Readed message number '{}'", readedMessages);
	            if (readedMessages >= DemoConstant.NUM_MESSAGES) {
	            	LOG.info("Readed Messages Limit");
	            	break;
	            }
	            
	            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
	            
	        }
        }
        finally {
        	// Close consumer
        	kafkaConsumer.close();
        }
        
        LOG.info("*** End ***");
    }
    

}