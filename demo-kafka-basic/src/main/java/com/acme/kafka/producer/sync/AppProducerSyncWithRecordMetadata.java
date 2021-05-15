package com.acme.kafka.producer.sync;

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
import com.acme.kafka.constant.KafkaTemplateConstant;
import com.acme.kafka.producer.config.KafkaProducerConfig;

/**
 * 	Sends a set of messages defined as "String" and with a delay between them (2 seconds)
 *  
 *  Synchronous
 *  
 *  NO Limit Messages
 *  
 *  No Key
 *	
 * 	Message Template : Hello World! CUSTOM_ID - SEND_DATE
 *  
 *  Different consumers can be used
 *   - Java consumer with appropriate configuration
 *   - kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --property print.key=true --from-beginning
 * 
 */

public class AppProducerSyncWithRecordMetadata {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppProducerSyncWithRecordMetadata.class);
	
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
    	LOG.info("*** Init ***");

    	// Create producer properties
        Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue();

        // Create producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProducerProperties);
        
        // Define topic
        String topic = DemoConstant.TOPIC;
        
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
	            // 	* The send method returns a Java Future
	            LOG.info("[*] Sending message='{}' to topic='{}'", message, topic);
				RecordMetadata metadata = kafkaProducer.send(record).get();
				
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
				// Receive sent record -> RecordMetadata
		        LOG.info(KafkaTemplateConstant.TEMPLATE_LOG_PRODUCER_RECORDMETADATA,
		                    metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp());            
				
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
