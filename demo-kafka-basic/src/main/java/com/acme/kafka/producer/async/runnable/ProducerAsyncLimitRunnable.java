package com.acme.kafka.producer.async.runnable;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.config.KafkaProducerConfig;

public class ProducerAsyncLimitRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ProducerAsyncLimitRunnable.class);

	private final KafkaProducer<String, String> kafkaProducer;
    private final String topic;
	
	public ProducerAsyncLimitRunnable(String bootstrapServers, String topic) {
		LOG.info("[ProducerAsyncLimitRunnable] *** Init ***");
		
		// Create producer properties
		Properties producerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue(bootstrapServers);

		// Create Kafka producer
		this.kafkaProducer = new KafkaProducer<>(producerProperties);

		// Prepare topic
		this.topic = topic;
	}

	@Override
	public void run() {
		LOG.info("[ProducerAsyncLimitRunnable] *** Run ***");
		
		// Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        try {
		
			LOG.info("Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
			for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
	        	// Prepare message
	        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
	        	
	        	// Create producer record
	            ProducerRecord<String, String> record = new ProducerRecord<>(this.topic, message);
	            
	            // Send data asynchronous
	            LOG.info("sending message='{}' to topic='{}'", message, this.topic);
	            kafkaProducer.send(record);
	            
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
				TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
	        }
			
        } catch (InterruptedException e1) {
			LOG.error("Received interruption signal");
		} finally {
			kafkaProducer.close();
		}
		
	}
	
	

}
