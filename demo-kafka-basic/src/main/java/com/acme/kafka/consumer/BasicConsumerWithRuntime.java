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

public class BasicConsumerWithRuntime {

    private static final Logger LOG = LoggerFactory.getLogger(BasicConsumerWithRuntime.class);
    
    private static final AtomicBoolean closed = new AtomicBoolean(false); // Close Process
    
    public static void main(String[] args) {
    	
    	LOG.info("[BasicConsumerWithRuntime] *** Init ***");
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Shutting down");
                closed.set(true);
            }
        });
    	
    	// Create consumer properties
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, DemoConstant.BOOTSTRAP_SERVERS);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // "org.apache.kafka.common.serialization.StringDeserializer"
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // "org.apache.kafka.common.serialization.StringDeserializer"
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, DemoConstant.GROUP_ID);
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        //consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //consumerProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");

        // Create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        
        // Receive data asynchronous
        consumer.subscribe(Arrays.asList(DemoConstant.TOPIC));
        
        int readedMessages=0;
        
        while (!closed.get()) {
        	// Create consumer records
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));
            LOG.info("Check records -> Count {}", records.count());

            for (ConsumerRecord<String, String> record : records){          	
            	LOG.info("Received record \n" +
            			"Key: {} \n" +
            			"Value: {} \n" +
                        "Topic: {} \n" +
                        "Partition: {}\n" +
                        "Offset: {} \n" +
                        "Timestamp: {}" , 
                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
            }
            
            readedMessages++;
            
            LOG.info("Readed message='{}'", readedMessages);
            
            if (readedMessages>=DemoConstant.NUM_MESSAGES) {break;}
        }
        
        // Close consumer
        consumer.close();
        
        LOG.info("[BasicConsumerWithRuntime] *** End ***");
    }
    

}