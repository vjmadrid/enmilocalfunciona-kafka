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
import com.acme.kafka.producer.KafkaProducerService;

@SpringBootApplication
public class SpringKafkaApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringKafkaApplication.class);
	
    @Autowired
    private KafkaProducerService kafkaConsumerService;
	
    public static void main(String[] args) {
    	LOG.info("[SpringKafkaApplication] *** Init ***");
    	
        SpringApplication.run(SpringKafkaApplication.class, args);
        
        LOG.info("[SpringKafkaApplication] *** End ***");
    }

    @Override
    public void run(String... strings) throws Exception {
    	
    	LOG.info("[SpringKafkaApplication] Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
    	for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(DemoConstant.TOPIC, message);
            
            // Send data asynchronous
            LOG.info("[SpringKafkaApplication] sending message='{}' to topic='{}'", message, DemoConstant.TOPIC);
            kafkaConsumerService.send(DemoConstant.TOPIC,record);
            
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }

    }
}