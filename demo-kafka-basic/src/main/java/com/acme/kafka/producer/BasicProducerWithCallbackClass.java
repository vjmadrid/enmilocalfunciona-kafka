package com.acme.kafka.producer;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.callback.CustomProducerCallback;
import com.acme.kafka.producer.config.KafkaProducerConfig;

/**
 * 	Sends a set number of messages (10) defined as "String" and with a delay between them (2 seconds)
 * 
 *  Message Template : Hello World! CUSTOM_ID - SEND_DATE
 *  
 *  Retrieves meta information about the message being sent from a Callback class
 *  
 *  Different consumers can be used
 *   - Java consumer with appropriate configuration
 *   - kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --property print.key=true --from-beginning
 * 
 */

public class BasicProducerWithCallbackClass {
	
	private static final Logger LOG = LoggerFactory.getLogger(BasicProducerWithCallbackClass.class);
	
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
    	LOG.info("[BasicProducerWithCallbackClass] *** Init ***");

    	// Create producer properties
        Properties kafkaProducerProperties = KafkaProducerConfig.producerConfigsString();
        
        // Create producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(kafkaProducerProperties);

        LOG.info("[BasicProducerWithCallbackClass] Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
        for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	// Prepare message
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(DemoConstant.TOPIC, message);
            
            // Send data asynchronous
            LOG.info("[BasicProducerWithCallbackClass] sending message='{}' to topic='{}'", message, DemoConstant.TOPIC);
            kafkaProducer.send(record, new CustomProducerCallback()).get();
        	
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }
        
        // Flush data
        kafkaProducer.flush();
        
        // Flush + close producer
        kafkaProducer.close();

        LOG.info("[BasicProducerWithCallbackClass] *** End ***");
    }

}
