package com.acme.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.kafka.constant.KafkaConfigConstant;
import com.acme.kafka.util.KafkaUtil;

@Service
public class KafkaConsumerService {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);

	private CountDownLatch latchTest = new CountDownLatch(KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH);

	// Use in testing for received a message -> No production environment
	public CountDownLatch getLatchTest() {
		return latchTest;
	}

	/**
	 * Create @KafkaListener -> Depends application properties
	 * 
	 * Other options :
	 * 
	 * @KafkaListener(topics = DemoConstant.TOPIC, groupId = DemoConstant.GROUP_ID)
	 * @KafkaListener(id = "basic-listener", topics = "${app.topic.example1}")
	 * @KafkaListener(id = "basic-listener", topics = "${app.topic.example1}",
	 *                   groupId = "${spring.kafka.consumer.group-id}")
	 */

	@KafkaListener(id = "basic-listener", 
			topics = "${app.topic.example1}", 
			groupId = "${spring.kafka.consumer.group-id}",
			errorHandler = "customersErrorHandler",
            containerFactory = "kafkaListenerContainerFactory"
	)
	public void receive(@Payload String message, @Headers MessageHeaders headers) {
		LOG.info("[KafkaConsumerService] received message='{}'", message);

		LOG.info("[KafkaConsumerService] Show details...");
		KafkaUtil.showMessageHeaders(headers,LOG);

		LOG.info("[KafkaConsumerService] latch.countDown()...");
		latchTest.countDown();
		
		//throw new RuntimeException("Active customersErrorHandler");
	}

//	@KafkaListener(topics = "advice-topic", clientIdPrefix = "string", containerFactory = "kafkaListenerStringContainerFactoryString")
//	public void listenasString(ConsumerRecord<String, String> cr, @Payload String payload) {
//		LOG.info("[KafkaConsumerService] [String] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
//				typeIdHeader(cr.headers()), payload, cr.toString());
//		latchTest.countDown();
//	}
//	
//	@KafkaListener(topics = "advice-topic", clientIdPrefix = "bytearray",
//            containerFactory = "kafkaListenerByteArrayContainerFactory")
//    public void listenAsByteArray(ConsumerRecord<String, byte[]> cr,
//                                  @Payload byte[] payload) {
//		LOG.info("[KafkaConsumerService][ByteArray] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
//                typeIdHeader(cr.headers()), payload, cr.toString());
//		latchTest.countDown();
//    }
	
	 @SuppressWarnings("ConstantConditions")
	    @Bean
	    public ConsumerAwareListenerErrorHandler customersErrorHandler() {
	        return (m, e, c) -> {
	            LOG.error("Error consuming a message, let's retry the last 5!", e);
	            MessageHeaders headers = m.getHeaders();
	            c.seek(new org.apache.kafka.common.TopicPartition(
	                            headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
	                            headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class)),
	                    Math.max(0, headers.get(KafkaHeaders.OFFSET, Long.class) - 5)); // panic! replay last 5
	            return null;
	        };
	    }

}