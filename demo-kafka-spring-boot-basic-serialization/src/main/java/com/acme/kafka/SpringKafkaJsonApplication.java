package com.acme.kafka;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.constant.DemoConstant;
import com.acme.kafka.entity.CustomMessage;
import com.acme.kafka.producer.service.KafkaProducerJsonService;

@SpringBootApplication
public class SpringKafkaJsonApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringKafkaJsonApplication.class);
	
    @Autowired
    private KafkaProducerJsonService kafkaProducerJsonService;
    
    private String topic = DemoConstant.TOPIC_JSON;
    
    public static void main(String[] args) {
    	LOG.info("[SpringKafkaJsonApplication] *** Init ***");
    	
        SpringApplication.run(SpringKafkaJsonApplication.class, args);
        
        LOG.info("[SpringKafkaJsonApplication] *** End ***");
    }

    @Override
    public void run(String... strings) throws Exception {
    	
    	LOG.info("[SpringKafkaJsonApplication] Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
    	for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	CustomMessage customMessage = new CustomMessage();
        	customMessage.setIdentifier(i);
        	customMessage.setMessage(message);
        	
            // Send data asynchronous
            LOG.info("[SpringKafkaJsonApplication] sending custom message='{}' to topic='{}'", customMessage, topic);
            kafkaProducerJsonService.send(topic,customMessage);
            
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }

    }
}