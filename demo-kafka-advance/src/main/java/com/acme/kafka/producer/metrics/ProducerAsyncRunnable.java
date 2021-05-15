package com.acme.kafka.producer.metrics;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.producer.config.KafkaProducerConfig;
import com.acme.architecture.kafka.common.util.KafkaPropertiesUtil;
import com.acme.kafka.constant.DemoConstant;

public class ProducerAsyncRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ProducerAsyncRunnable.class);

	private final KafkaProducer<String, String> kafkaProducer;
    private final String topic;
	
	public ProducerAsyncRunnable(String idProducer, String brokers, String topic) {
		LOG.info("[ProducerAsyncRunnable] *** Init ***");
		
		// Create producer properties
		Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue(idProducer, brokers);
		
		LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaProducerProperties, LOG);

		// Create Kafka producer
		this.kafkaProducer = new KafkaProducer<>(kafkaProducerProperties);

		// Prepare topic
		this.topic = topic;
	}

	@Override
	public void run() {
		LOG.info("[ProducerAsyncRunnable] *** Run ***");
		
		// Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        LOG.info("Preparing to send menssages");
        try {
		
        	int numSentMessages=1;
	        while (true) {
	        	// Prepare message
	        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, numSentMessages, new Date().toString());
	        	
	        	// Create producer record
	            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
	            
	            // Send data asynchronous -> Fire & Forget
	            LOG.info("[*] Sending message='{}' to topic='{}'", message, topic);
	            kafkaProducer.send(record);
	            
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
	            // Prepare counter num sent messages
	            numSentMessages++;
	            
				TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
				
	        }
			
        } catch (InterruptedException e) {
			LOG.error("Received interruption signal : {}",e);
		} finally {
			// Flush data
	        kafkaProducer.flush();
	        
	        // Flush + close producer
	        kafkaProducer.close();
		}
		
	}

}
