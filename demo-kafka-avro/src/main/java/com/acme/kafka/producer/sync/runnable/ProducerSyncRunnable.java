package com.acme.kafka.producer.sync.runnable;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.config.KafkaProducerConfig;

public class ProducerSyncRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ProducerSyncRunnable.class);

	private final KafkaProducer<String, String> kafkaProducer;
    private final String topic;
	
	public ProducerSyncRunnable(String bootstrapServers, String topic) {
		LOG.info("[ProducerSyncRunnable] *** Init ***");
		
		// Create producer properties
		Properties producerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue(bootstrapServers);

		// Create Kafka producer
		this.kafkaProducer = new KafkaProducer<>(producerProperties);

		// Prepare topic
		this.topic = topic;
	}

	@Override
	public void run() {
		LOG.info("[ProducerSyncRunnable] *** Run ***");
		
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
	            
	            // Send data synchronous -> blocking call
	            LOG.info("[*] Sending message='{}' to topic='{}'", message, topic);
				RecordMetadata metadata = kafkaProducer.send(record).get();
					
		        LOG.info("[RecordMetadata] Received metadata \n" +
		                    "\tTopic: {} \n" +
		                    "\tPartition: {} \n" +
		                    "\tOffset: {} \n" +
		                    "\tTimestamp: {}",
		                    metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp());            
				
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
	            // Prepare counter num sent messages
	            numSentMessages++;
	            
	            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
	            
	        }
			
        } catch (InterruptedException e) {
			LOG.error("Received interruption signal : {}",e);
		} catch (ExecutionException e) {
			LOG.error("Execution Exception : {}",e);
			e.printStackTrace();
		} finally {
			// Flush data
	        kafkaProducer.flush();
	        
	        // Flush + close producer
	        kafkaProducer.close();
		}
        
    }

}
