package com.acme.kafka.producer.async.callback.partitioner;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalKafkaTemplateConstant;
import com.acme.architecture.kafka.common.constant.GlobalProducerKafkaConstant;
import com.acme.architecture.kafka.common.partitioner.CustomPartitioner;
import com.acme.architecture.kafka.common.producer.config.KafkaProducerConfig;
import com.acme.architecture.kafka.common.util.KafkaPropertiesUtil;
import com.acme.kafka.constant.DemoConstant;

/**
 * 	Sends a set of messages defined as "String" and with a delay between them (2 seconds)
 *  
 *  Asynchronous
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

public class AppProducerAsyncPartitionerWithCallbackAdhoc {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppProducerAsyncPartitionerWithCallbackAdhoc.class);
	
	private static final String PARTITION_0_ID = "partitions.0";
	private static final String PARTITION_1_ID = "partitions.1";
	private static final String PARTITION_2_ID = "partitions.2";
	
	private static final String PARTITION_0_VALUE = "Madrid";
	private static final String PARTITION_1_VALUE = "Barcelona";
	private static final String PARTITION_2_VALUE = "Sevilla";
	
	public static String getRandomPartition() {
		String value[] = { PARTITION_0_VALUE, PARTITION_1_VALUE, PARTITION_2_VALUE };
		
		int valueRandom = new Random().nextInt(value.length);
		
		return value[valueRandom];
	}
	
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("*** Init ***");

    	// Create producer properties
        Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue(GlobalProducerKafkaConstant.DEFAULT_PRODUCER_CLIENT_ID, GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS);
        
        kafkaProducerProperties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getCanonicalName());
        kafkaProducerProperties.put(PARTITION_0_ID, PARTITION_0_VALUE);
        kafkaProducerProperties.put(PARTITION_1_ID, PARTITION_1_VALUE);
        kafkaProducerProperties.put(PARTITION_2_ID, PARTITION_2_VALUE);

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
	        	
	        	// Prepare key
	        	String key = getRandomPartition();
	        	
	        	// Create producer record
	            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
	            
	            // Send data asynchronous -> Fire & Forget
	            LOG.info("Sending message='{}' to topic='{}'", message, topic);
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