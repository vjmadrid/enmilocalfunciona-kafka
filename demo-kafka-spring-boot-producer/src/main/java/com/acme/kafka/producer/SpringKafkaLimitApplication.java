package com.acme.kafka.producer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.producer.constant.DemoConstant;
import com.acme.kafka.producer.service.KafkaProducerService;

//@SpringBootApplication
public class SpringKafkaLimitApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringKafkaLimitApplication.class);
	
    @Autowired
    private KafkaProducerService kafkaProducerService;
    
    private static String hostName;
	
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(SpringKafkaLimitApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	LOG.info("[SpringKafkaLimitApplication] *** Init ***");
    	
    	hostName = InetAddress.getLocalHost().getHostName();
    	
    	LOG.info("[SpringKafkaLimitApplication] Hostname {}", hostName);
    	
    		
    	LOG.info("[SpringKafkaApplication] Preparing to send {} menssages", DemoConstant.NUM_MESSAGES);
    	for (int i=1; i<=DemoConstant.NUM_MESSAGES; i++ ) {
        	String message = String.format(DemoConstant.MESSAGE_TEMPLATE, i, new Date().toString());
        	
        	// Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(DemoConstant.TOPIC, message);
            
            // Send data asynchronous
            LOG.info("[SpringKafkaLimitApplication] [{}] sending message='{}' to topic='{}'", hostName, message, DemoConstant.TOPIC);
            kafkaProducerService.send(DemoConstant.TOPIC,record);
            
            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
        }
    	
    	LOG.info("[SpringKafkaLimitApplication] *** End ***");

    }
}