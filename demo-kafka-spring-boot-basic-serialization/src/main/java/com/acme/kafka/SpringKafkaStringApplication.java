package com.acme.kafka;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.producer.service.KafkaProducerStringService;

@SpringBootApplication
public class SpringKafkaStringApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringKafkaStringApplication.class);
	
    @Autowired
    private KafkaProducerStringService kafkaProducerStringService;
    
    @Value("${app.topic.example-string}")
    private String topic;
    
    public static void main(String[] args) {
    	LOG.info("[SpringKafkaStringApplication] *** Init ***");
    	
        SpringApplication.run(SpringKafkaStringApplication.class, args);
        
        LOG.info("[SpringKafkaStringApplication] *** End ***");
    }

    @Override
    public void run(String... strings) throws Exception {
    	
    	LOG.info("[SpringKafkaStringApplication] Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
    	for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            
            // Send data asynchronous
            LOG.info("[SpringKafkaStringApplication] sending message='{}' to topic='{}'", message, topic);
            kafkaProducerStringService.send(topic,record);
            
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }

    }
}