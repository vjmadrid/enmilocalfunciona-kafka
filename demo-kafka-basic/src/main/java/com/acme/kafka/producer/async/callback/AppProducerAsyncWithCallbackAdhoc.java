package com.acme.kafka.producer.async.callback;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalKafkaTemplateConstant;
import com.acme.architecture.kafka.common.constant.GlobalProducerKafkaConstant;
import com.acme.architecture.kafka.common.producer.config.KafkaProducerConfig;
import com.acme.architecture.kafka.common.util.KafkaPropertiesUtil;
import com.acme.kafka.constant.DemoConstant;

/**
 * 	Sends a set of messages defined as "String" and with a delay between them (2 seconds)
 *  
 *  Asynchronous
 *  
 *  	- NO Blocking Call -> Send many Records (Fast)
 *  	- Use callback mechanism -> Callback interface for asynchronous operations
 *  	- Callback interface interface allows code to execute when the request is complete
 *  	- Execute in a background I/O Thread
 *  	- onCompletion method is called when the operation send is complete
 *  		with value = RecordMetadata
 *  		without value = Exception
 *      
 *  
 *  
 *  NO Limit Messages
 *  
 *  No Key
 *  
 * 	Message Template : Hello World! CUSTOM_ID - SEND_DATE
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
	
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("*** Init ***");

    	// Create producer properties
        Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue(GlobalProducerKafkaConstant.DEFAULT_PRODUCER_CLIENT_ID, GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS);
        
        LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaProducerProperties, LOG);
        
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
	            
	            // Send data asynchronous -> Fire & Forget
	            LOG.info("[*] Sending message='{}' to topic='{}'", message, topic);

	            // Option 1
	            kafkaProducer.send(record, new Callback() {
	            	
	                public void onCompletion(RecordMetadata metadata, Exception exception) {
	                	long elapsedTime = System.currentTimeMillis() - startTime;
	     
	                	if (exception == null) {
	                		LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_PRODUCER_CALLBACK_RECEIVED_METADA,
	                                metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp(), (elapsedTime / 1000));
	                    } else {
	                    	LOG.error(GlobalKafkaTemplateConstant.TEMPLATE_LOG_PRODUCER_CALLBACK_ERROR, exception);
	                    	exception.printStackTrace();
	                    }
	                    
	                }
	                
	            });
	            
//	            //Option 2
//	            kafkaProducer.send(record, (metadata, exception) -> {
//	            	long elapsedTime = System.currentTimeMillis() - startTime;
//	            	
//	            	if (exception == null) {
//	            		LOG.info(KafkaTemplateConstant.TEMPLATE_LOG_PRODUCER_CALLBACK_RECEIVED_METADA,
//	                            metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp(), (elapsedTime / 1000));
//	                } else {
//	                	LOG.error(KafkaTemplateConstant.TEMPLATE_LOG_PRODUCER_CALLBACK_ERROR,, exception);
//	                }
//	            	
//	            });
	            
	            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
	            
	        }
			
		} finally {
			// Flush data
	        kafkaProducer.flush();
	        
	        // Flush + close producer
	        kafkaProducer.close();
		}
        
    }

}
