package com.acme.kafka.testing.basic;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.acme.kafka.testing.constant.TestingConstant;
import com.acme.kafka.testing.consumer.util.KafkaContainerPropertiesUtil;
import com.acme.kafka.testing.consumer.util.KafkaContainerUtil;
import com.acme.kafka.testing.producer.dummy.DummyKafkaProducerPropertiesUtil;
import com.acme.kafka.testing.producer.util.KafkaProducerTemplateUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
public class BasicTest {
	
	public static final Logger LOG = LoggerFactory.getLogger(BasicTest.class);

	KafkaTemplate<Integer, String> kafkaTemplateProducer; 
	
	@Value("${app.topic.example1}")
	private String topic;
	
	private String messageTest;
	
	@BeforeEach
	public void setup() throws Exception {
		
	}
	

	@Test
	public void whenCallASendMessage_ThenReceiveMessage() throws InterruptedException {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, "id_1", new Date().toString(), "");
		LOG.info("message='{}'", messageTest);
		
		LOG.info("Start auto");
		final CountDownLatch latch = new CountDownLatch(4);
		
	    ContainerProperties containerProps = KafkaContainerPropertiesUtil.generateContainer(topic, latch, LOG);
	    
	    KafkaMessageListenerContainer<Integer, String> container = KafkaContainerUtil.generateContainer(containerProps);
	    container.setBeanName("testAuto");
	    container.start();
	    
	    Thread.sleep(1000); // wait a bit for the container to start
	    
	    kafkaTemplateProducer = KafkaProducerTemplateUtil.generateKafkaTemplateProducer(DummyKafkaProducerPropertiesUtil.defaultGenerateKafkaProducerStringProperties());
	    kafkaTemplateProducer.setDefaultTopic(topic);
	    
	    kafkaTemplateProducer.sendDefault(0, "example-1");
	    kafkaTemplateProducer.sendDefault(2, "example-2");
	    kafkaTemplateProducer.sendDefault(0, "example-3");
	    kafkaTemplateProducer.sendDefault(2, "example-4");
	    
	    kafkaTemplateProducer.flush();
	    
	    assertTrue(latch.await(60, TimeUnit.SECONDS));
	    
	    container.stop();
	    LOG.info("Stop auto");
	}

}
