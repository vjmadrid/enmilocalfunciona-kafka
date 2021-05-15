package com.acme.kafka.producer.runnable.sync;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.constant.GlobalKafkaTemplateConstant;
import com.acme.kafka.constant.DemoConstant;

import lombok.Data;

@Data
public class ProducerSyncRunnable implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ProducerSyncRunnable.class);

	private KafkaProducer<String, String> kafkaProducer;
	
    private String topic;
	
	@Override
	public void run() {
		LOG.info("*** Run ***");
		
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
					
		        LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_RECORD_METADATA,
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
