package com.acme.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BasicConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(BasicConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    //Use in testing for received a message
    public CountDownLatch getLatchTest() {
      return latch;
    }
    
    // Create @KafkaListener -> Depends application properties
    @KafkaListener(topics = "${app.topic.example1}")
    public void receive(@Payload String message, @Headers MessageHeaders headers) {
        LOG.info("[BasicConsumer] received message='{}'", message);
        
        // Mostrar detalles de la recepción
        LOG.info("[BasicConsumer] details...");
        headers.keySet().forEach(key -> 
        	LOG.info("{}: {}", key, headers.get(key))
        );
        
        //No Usar en Prod
        LOG.info("[BasicConsumer] latch.countDown()...");
        latch.countDown();
    }

}