package com.acme.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.consumer.config.KafkaConsumerConfig;

/**
 * 	Receives a set of messages defined as "String" performing "poll" every certain time (2 seconds)
 * 
 * 	With message limit (10)
 *  
 *  Different producers can be used
 *   - Java producer with appropriate configuration
 *   - kafka-console-producer.sh --broker-list localhost:9092 --topic topic-1
 * 
 */

public class BasicConsumerWithRuntime {

    private static final Logger LOG = LoggerFactory.getLogger(BasicConsumerWithRuntime.class);
    
    private static final AtomicBoolean closed = new AtomicBoolean(false); // Close Process
    
    public static void main(String[] args) {
    	
    	LOG.info("[BasicConsumerWithRuntime] *** Init ***");
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
            	LOG.info("[BasicConsumerWithRuntime] Shutting down");
                closed.set(true);
            }
        });
    	
    	// Create consumer properties
    	Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsString();

        // Create consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        
        // Receive data asynchronous
        LOG.info("[BasicConsumerWithRuntime] Preparing to subscribe {}", Arrays.asList(DemoConstant.TOPIC));
        kafkaConsumer.subscribe(Arrays.asList(DemoConstant.TOPIC));
        
        int readedMessages=0;
        
        LOG.info("[BasicConsumerWithRuntime] Preparing to receive {} menssages", DemoConstant.NUM_MESSAGES);
        while (!closed.get()) {
        	// Create consumer records
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(2000));
            LOG.info("Check records -> Count {}", records.count());

            for (ConsumerRecord<String, String> record : records){          	
            	LOG.info("[*] Received record \n" +
            			"Key: {} \n" +
            			"Value: {} \n" +
                        "Topic: {} \n" +
                        "Partition: {}\n" +
                        "Offset: {} \n" +
                        "Timestamp: {}" , 
                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
            }
            
            // Check num messages limit
            readedMessages++;
            
            LOG.info("[*] Readed message number '{}'", readedMessages);
            if (readedMessages>=DemoConstant.NUM_MESSAGES) {
            	break;
            }
        }
        
        // Close consumer
        kafkaConsumer.close();
        
        LOG.info("[BasicConsumerWithRuntime] *** End ***");
    }
    

}