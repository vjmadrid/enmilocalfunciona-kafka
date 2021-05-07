package com.acme.kafka.producer.async.callback;

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
 *  Asynchronous
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

public class AppProducerAsyncWithCallbackAdhoc {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppProducerAsyncWithCallbackAdhoc.class);
	
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
    	LOG.info("[AppProducerAsyncWithCallbackAdhoc] *** Init ***");

    	// Create producer properties
        Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue();

        // Create producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProducerProperties);
        
        // Define topic
        String topic = DemoConstant.TOPIC;
        
        // Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        try {
        	
	        LOG.info("Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
	        for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
	        	// Prepare message
	        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
	        	
	        	// Create producer record
	            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
	            
	            // Send data asynchronous -> Fire & Forget
	            LOG.info("Sending message='{}' to topic='{}'", message, topic);

	            // Option 1
	            kafkaProducer.send(record, new Callback() {
	            	
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
	                
	            });
	            
//	            //Option 2
//	            kafkaProducer.send(record, (metadata, exception) -> {
//	            	long elapsedTime = System.currentTimeMillis() - startTime;
//	            	
//	            	if (exception == null) {
//	            		LOG.info("[Callback] Received metadata \n" +
//	                            "\tTopic: {} \n" +
//	                            "\tPartition: {} \n" +
//	                            "\tOffset: {} \n" +
//	                            "\tTimestamp: {}",
//	                            "\tElapsed Time: {} ms",
//	                            metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp(), elapsedTime);
//	                } else {
//	                	LOG.error("[Callback] Error while producing message ", exception);
//	                }
//	            	
//	            });
	            
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
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
