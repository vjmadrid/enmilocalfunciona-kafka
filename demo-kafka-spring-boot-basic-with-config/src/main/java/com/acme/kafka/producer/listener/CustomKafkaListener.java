package com.acme.kafka.producer.listener;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;

public class CustomKafkaListener {

	private final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "id-listener", topics = "annotated1")
    public void listen1(String foo) {
        this.latch1.countDown();
    }
    
}
