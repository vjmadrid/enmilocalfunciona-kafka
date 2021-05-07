package com.acme.kafka.producer.sync.callback;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.config.KafkaProducerConfig;

/**
 * 	Sends a set number of messages (10) defined as "String" and with a delay between them (2 seconds)
 *  
 *  Message Template : Hello World! CUSTOM_ID - SEND_DATE
 *  
 *  Retrieve meta information about the message being sent directly
 *  
 *  Different consumers can be used
 *   - Java consumer with appropriate configuration
 *   - kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --property print.key=true --from-beginning
 * 
 */

public class AppProducerSyncWithCallbackAdhocAndRecordMetadata {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppProducerSyncWithCallbackAdhocAndRecordMetadata.class);
	
    public static void main(String[] args) throws InterruptedException, ExecutionException   {
    	
    	LOG.info("[AppProducerSyncWithCallbackAdhoc] *** Init ***");

    	// Create producer properties
        Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue();
        
        // Create producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProducerProperties);

        long startTime = System.currentTimeMillis();
        
        try {
        
	        LOG.info("Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
	        for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
	        	// Prepare message
	        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
	        	
	        	// Create producer record
	            ProducerRecord<String, String> record = new ProducerRecord<>(DemoConstant.TOPIC, message);
	            
	            // Send data asynchronous
	            LOG.info("Sending message='{}' to topic='{}'", message, DemoConstant.TOPIC);
	           
	            RecordMetadata metadata = kafkaProducer.send(record, new Callback() {
	            	
	            	public void onCompletion(RecordMetadata metadata, Exception exception) {
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
                
	            }).get();
	          
	            LOG.info("[RecordMetadata] Received metadata \n" +
	                    "\tTopic: {} \n" +
	                    "\tPartition: {} \n" +
	                    "\tOffset: {} \n" +
	                    "\tTimestamp: {}",
	                    metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp());            

	                        
            	TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
	        }
	        
		} finally {
			// Flush data
	        kafkaProducer.flush();
	        
	        // Flush + close producer
	        kafkaProducer.close();
		}
       

        LOG.info("*** End ***");
    }

}
