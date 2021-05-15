package com.acme.kafka.consumer.rebalance;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.constant.GlobalKafkaTemplateConstant;

import lombok.Data;

@Data
public class ConsumerRebalanceStartingOffsetRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ConsumerRebalanceStartingOffsetRunnable.class);

	private KafkaConsumer<String, String> kafkaConsumer;
	
	private String topic;
	
	private Map<TopicPartition, OffsetAndMetadata> processedOffsets = new HashMap<>();
	
	private long startingOffset;
	

	@Override
	public void run() {
		LOG.info("*** Run ***");

		try {
			LOG.info("Preparing to receive menssages");
			while (true) {
				ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
				LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORDS, consumerRecords.count(), consumerRecords.partitions().size());
				
				for (ConsumerRecord<String, String> record : consumerRecords){          	
		            	LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORD_FOR_THREAD , 
		                        Thread.currentThread().getId(),record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
		            	
		            	processedOffsets.put(new TopicPartition(record.topic(),
		                            record.partition()), new
		                            OffsetAndMetadata(record.offset() + 1, "no metadata"));
		         }
				
				if (startingOffset == -2)
					kafkaConsumer.commitSync();

			}
		} catch (WakeupException e) {
			LOG.error("Received shutdown signal");
		}catch (Exception e) {
			LOG.error("Unexpected error" + e);
        } finally {
        	
            try {
            	kafkaConsumer.commitSync();
            } finally {
            	kafkaConsumer.close();
            }
            
        }

	}

	public void shutdown() {
		LOG.info("*** Shutdown ***");
		// interrupt consumer.poll() and throw WakeUpException
		kafkaConsumer.wakeup();
	}

}
