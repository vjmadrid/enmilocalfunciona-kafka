package com.acme.kafka;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.producer.BasicProducer;

@SpringBootApplication
public class SpringKafkaApplication implements CommandLineRunner {
	
	public static final String MESSAGE_VALUE = "Hello World! "+new Date();

    @Autowired
    private BasicProducer basicProducer;
	
    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	basicProducer.send(MESSAGE_VALUE);
    }
}