package com.acme.kafka.producer.async.future;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalProducerKafkaConstant;
import com.acme.architecture.kafka.common.producer.config.KafkaProducerConfig;
import com.acme.architecture.kafka.common.util.KafkaPropertiesUtil;
import com.acme.kafka.constant.DemoConstant;

/**
 * 	Sends a set of messages defined as "String" and with a delay between them (2 seconds)
 *  
 *  Asynchronous
 *  
 *  	- returns a Future for the RecordMetadata that will be assigned to this record
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

public class AppProducerAsyncWithFuture {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppProducerAsyncWithFuture.class);
	
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
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
	            
	            // Send data asynchronous + Future
	            //	 * When called it adds the record to a buffer of pending record sends and immediately returns
	            //   * The destination will accept the request and "commits" to Future answer
	            //	 * The producer is responsible for checking the Future
	            //		* Blocking : The producer waits until the consumer responds with the Future answer
	            //		
	            LOG.info("Sending message='{}' to topic='{}'", message, topic);
	            Future<RecordMetadata> outFutureRecordMetadata = kafkaProducer.send(record);
	            
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
	            
	            if (outFutureRecordMetadata.isCancelled()) {
	            	LOG.info("[Future] Unable to send message=[{}] due to : {}", outFutureRecordMetadata.get().toString());
	            }
	            
	            if (outFutureRecordMetadata.isDone()) {
	            	LOG.info("[Future] Sent message=[{}] with offset=[{}]", outFutureRecordMetadata.get().toString(), outFutureRecordMetadata.get().offset());
	            }
	            
	            // Get RecordMetadata
	            RecordMetadata metadata = outFutureRecordMetadata.get();
	            LOG.info("[Future] Received metadata \n" +
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
        
    }

}
