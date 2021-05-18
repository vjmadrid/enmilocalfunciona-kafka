package com.acme.kafka.producer.partitioner;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.callback.LoggerProducerCallback;
import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalProducerKafkaConstant;
import com.acme.architecture.kafka.common.partitioner.CitiesPartitioner;
import com.acme.architecture.kafka.common.partitioner.constant.CitiesPartitionerConstant;
import com.acme.architecture.kafka.common.partitioner.util.CitiesPartitionerUtil;
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

public class AppProducerAsyncCallbackWithCitiesPartitioner {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppProducerAsyncCallbackWithCitiesPartitioner.class);
	
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("*** Init ***");

    	// Create producer properties
        Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsStringKeyStringValue(GlobalProducerKafkaConstant.DEFAULT_PRODUCER_CLIENT_ID, GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS);
        
        // Option define each partition
        kafkaProducerProperties.put(CitiesPartitionerConstant.PARTITION_0_ID, CitiesPartitionerConstant.PARTITION_0_VALUE_MADRID);
        kafkaProducerProperties.put(CitiesPartitionerConstant.PARTITION_1_ID, CitiesPartitionerConstant.PARTITION_1_VALUE_BARCELONA);
        kafkaProducerProperties.put(CitiesPartitionerConstant.PARTITION_2_ID, CitiesPartitionerConstant.PARTITION_2_VALUE_SEVILLA);
        
        kafkaProducerProperties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CitiesPartitioner.class.getCanonicalName());

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
	        	String key = CitiesPartitionerUtil.getRandomCitiesPartition();
	        	
	        	// Create producer record
	            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
	            
	            // Send data asynchronous -> Fire & Forget
	            LOG.info("Sending key='{}' message='{}' to topic='{}'", key, message, topic);
	            kafkaProducer.send(record, new LoggerProducerCallback(startTime, key, message));
	            
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
