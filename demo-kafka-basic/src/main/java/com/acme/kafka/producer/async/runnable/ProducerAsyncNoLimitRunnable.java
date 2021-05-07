package com.acme.kafka.producer.async.runnable;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.config.KafkaProducerConfig;

public class ProducerAsyncNoLimitRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ProducerAsyncNoLimitRunnable.class);

	private final KafkaProducer<String, String> kafkaProducer;
    private final String topic;
	
	public ProducerAsyncNoLimitRunnable(String bootstrapServers, String topic) {
		LOG.info("[ProducerAsyncNoLimitRunnable] *** Init ***");
		
		// Create producer properties
		Properties producerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue(bootstrapServers);

		// Create Kafka producer
		this.kafkaProducer = new KafkaProducer<>(producerProperties);

		// Prepare topic
		this.topic = topic;
	}

	@Override
	public void run() {
		LOG.info("[ProducerAsyncNoLimitRunnable] *** Run ***");
		int i = 0;
		try {
			LOG.info("Preparing to send menssages");
			
			// Prepare send execution time
	        long startTime = System.currentTimeMillis();
	        
			while (true) {
				// Prepare message
	        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
	        	
	        	// Create producer record
	            ProducerRecord<String, String> record = new ProducerRecord<>(this.topic, message);
	            
	            // Send data asynchronous -> Fire & Forget
	            LOG.info("Sending message='{}' to topic='{}'", message, this.topic);
	            
	            // Send data asynchronous -> Fire and Forget
	            
	            // Option 1
                // kafkaProducer.send(record);
	            
	            // Option 2
	            kafkaProducer.send(record, new Callback() {
	            	
	                public void onCompletion(RecordMetadata metadata, Exception exception) {
	                	// Define send execution time
	    	            long elapsedTime = System.currentTimeMillis() - startTime;
	     
	                	if (exception == null) {
	                		LOG.info("[Callback] Received metadata \n" +
	                                "\tTopic: {} \n" +
	                                "\tPartition: {} \n" +
	                                "\tOffset: {} \n" +
	                                "\tTimestamp: {}",
	                                "\tElapsed Time: {} ms",
	                                metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp(), elapsedTime);
	                    } else {
	                    	LOG.error("[Callback] Error while producing message ", exception);
	                    }
	                    
	                }
	                
	            });
	            
	            i++;
                Thread.sleep(2000);

			}
			
		} catch (WakeupException e) {
			LOG.error("Received shutdown signal");
		} catch (InterruptedException e1) {
			LOG.error("Received interruption signal");
		} finally {
			kafkaProducer.close();
		}

	}

}
