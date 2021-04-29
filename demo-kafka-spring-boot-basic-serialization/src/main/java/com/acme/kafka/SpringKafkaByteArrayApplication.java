package com.acme.kafka;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.service.KafkaProducerByteArrayService;

@SpringBootApplication
public class SpringKafkaByteArrayApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringKafkaByteArrayApplication.class);
	
    @Autowired
    private KafkaProducerByteArrayService kafkaProducerByteArrayService;
    
    private String topic = DemoConstant.TOPIC_STRING;
    
    public static void main(String[] args) {
    	LOG.info("[SpringKafkaByteArrayApplication] *** Init ***");
    	
        SpringApplication.run(SpringKafkaByteArrayApplication.class, args);
        
        LOG.info("[SpringKafkaByteArrayApplication] *** End ***");
    }

    @Override
    public void run(String... strings) throws Exception {
    	
    	LOG.info("[SpringKafkaByteArrayApplication] Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
    	for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            
            // Send data asynchronous
            LOG.info("[SpringKafkaByteArrayApplication] sending message='{}' to topic='{}'", message, topic);
            kafkaProducerByteArrayService.send(topic,record.toString().getBytes());
            
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }

    }
}