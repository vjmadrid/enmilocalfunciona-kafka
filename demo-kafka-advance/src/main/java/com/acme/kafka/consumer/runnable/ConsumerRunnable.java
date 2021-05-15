package com.acme.kafka.consumer.runnable;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.constant.GlobalKafkaTemplateConstant;

import lombok.Data;

@Data
public class ConsumerRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ConsumerRunnable.class);

	private KafkaConsumer<String, String> kafkaConsumer;
	
	private String topic;
	
	private CountDownLatch countDownLatch;

	@Override
	public void run() {
		LOG.info("[ConsumerRunnable] *** Run ***");

		try {
			LOG.info("[ConsumerRunnable] Preparing to receive menssages");
			while (true) {
				ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
				LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORDS, consumerRecords.count(), consumerRecords.partitions().size());
				
				for (ConsumerRecord<String, String> record : consumerRecords){          	
		            	LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORD_FOR_THREAD , 
		                        Thread.currentThread().getId(),record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
		         }

			}
		} catch (WakeupException e) {
			LOG.info("Received shutdown signal");
		} finally {
			kafkaConsumer.close();
			countDownLatch.countDown();
		}

	}

	public void shutdown() {
		LOG.info("[ConsumerRunnable] *** Shutdown ***");
		// interrupt consumer.poll() and throw WakeUpException
		kafkaConsumer.wakeup();
	}

}
