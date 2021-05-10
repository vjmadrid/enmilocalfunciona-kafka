package com.acme.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.constant.KafkaConstant;
import com.acme.kafka.consumer.config.KafkaConsumerConfig;

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

public class AppConsumerWithRuntime {

    private static final Logger LOG = LoggerFactory.getLogger(AppConsumerWithRuntime.class);
    
    private static final AtomicBoolean closed = new AtomicBoolean(false); // Close Process
    
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("*** Init ***");
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
            	LOG.info("Shutting down");
                closed.set(true);
            }
        });
    	
    	// Create consumer properties
        Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue();

        // Create Kafka consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        
        // Define topic
        String topic = KafkaConstant.TOPIC;
        
        // Subscribe topic
        LOG.info("Preparing to subscribe {}", Arrays.asList(topic));
        kafkaConsumer.subscribe(Arrays.asList(topic));
        
        // Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        LOG.info("Preparing to receive menssages");
        try {
        	
        	while (!closed.get()) {
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
	            }
	            
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
        
        LOG.info("*** End ***");
    }
    

}