package com.acme.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.acme.kafka.consumer.service.KafkaConsumerStringService;
import com.acme.kafka.producer.service.KafkaProducerStringService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = BasicSenderReceiverTest.NUM_BROKERS	, topics = {BasicSenderReceiverTest.EXAMPLE_TOPIC})
public class BasicSenderReceiverTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicSenderReceiverTest.class);

	public static final String EXAMPLE_TOPIC = "topic-example";
	
	public static final int NUM_BROKERS = 1;
	
	private String messageTest;
	
	@Autowired
	private KafkaProducerStringService basicSender;
	
	@Autowired
	private KafkaConsumerStringService basicReceiver;
	
	@BeforeEach
	public void init() throws Exception {
		messageTest = "TEST";
	}

	@Test
	public void shouldSend() throws InterruptedException {
		basicSender.send(messageTest);
		
		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
	    assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);
	}
	
	@Test
	public void shouldSendWithTopic() throws InterruptedException {
		basicSender.send(EXAMPLE_TOPIC,messageTest);
		
		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
	    assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);
	}

}
