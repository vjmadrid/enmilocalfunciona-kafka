package com.acme.kafka.producer.generator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.acme.kafka.producer.constant.DemoConstant;
import com.acme.kafka.producer.service.KafkaProducerService;

@Component
public class KafkaProducerGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerGenerator.class);
	
	private int counter = 1;
	
	private String hostName;
	
	@Autowired
	private KafkaProducerService kafkaProducerService;
	
	@PostConstruct
	public void setUp() throws UnknownHostException {
		hostName = InetAddress.getLocalHost().getHostName();
	}
	
	@Scheduled(fixedRate = 2000)
    public void generate() {
		String message = String.format(DemoConstant.MESSAGE_TEMPLATE, counter, new Date().toString());
		
		LOG.info("[KafkaProducerGenerator] [{}] Prepare message='{}' Fixed Delay Task='{}'", hostName, message, System.currentTimeMillis() / 1000);
       
		kafkaProducerService.send(message);
        counter++;
    }

}
