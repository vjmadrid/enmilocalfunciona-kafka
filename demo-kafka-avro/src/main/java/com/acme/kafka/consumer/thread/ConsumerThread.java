package com.acme.kafka.consumer.thread;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data
public class ConsumerThread extends Thread {
	
	 private static final Logger LOG = LoggerFactory.getLogger(ConsumerThread.class);
	
	private KafkaConsumer<String, String> kafkaConsumer;
    private String topic;
    
    public ConsumerThread(String topic, Properties kafkaConsumerProperties) {
    	this.kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        this.topic = topic;
        
        this.kafkaConsumer.subscribe(Collections.singletonList(this.topic));
    }
    
    public void doWork() {
    	
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
        
    }

}
