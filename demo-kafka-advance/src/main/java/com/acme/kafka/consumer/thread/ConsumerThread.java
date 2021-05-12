package com.acme.kafka.consumer.thread;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.KafkaTemplateConstant;

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
    	
        ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
        LOG.info(KafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORDS, consumerRecords.count(), consumerRecords.partitions().size());

        for (ConsumerRecord<String, String> record : consumerRecords){          	
        	LOG.info(KafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORD, 
                    record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
        }
        
    }

}
