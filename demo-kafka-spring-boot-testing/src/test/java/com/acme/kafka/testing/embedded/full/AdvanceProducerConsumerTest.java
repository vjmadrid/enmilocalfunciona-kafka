package com.acme.kafka.testing.embedded.full;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import com.acme.kafka.testing.constant.TestingConstant;
import com.acme.kafka.testing.consumer.KafkaConsumerService;
import com.acme.kafka.testing.producer.KafkaProducerService;
import com.acme.kafka.testing.producer.util.KafkaProducerTemplateUtil;

@SpringBootTest
@EmbeddedKafka
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdvanceProducerConsumerTest {

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private KafkaConsumerService kafkaConsumerService;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private Consumer<Integer, String> consumer;
	
	@Value("${app.topic.example1}")
	private String topic;
	
	private String messageTest;


	private KafkaConsumer<Integer, String> setUpConsumerBasic() {
		return new KafkaConsumer<Integer, String>( 
                KafkaTestUtils.consumerProps("spring_group", "true", embeddedKafkaBroker.getBrokersAsString()));
	}

	@BeforeEach
	public void setUp() throws Exception {
		System.out.println("*** SETUP ***");
		
		String kafkaBootstrapServers = embeddedKafkaBroker.getBrokersAsString();
		System.out.println(" [*] kafkaBootstrapServers :: "+kafkaBootstrapServers);

		kafkaTemplate = KafkaProducerTemplateUtil.generateKafkaTemplate(embeddedKafkaBroker);
		kafkaTemplate.setDefaultTopic(topic);

		// Wait until the partitions are assigned
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer,
					embeddedKafkaBroker.getPartitionsPerTopic());
		}

//		consumer = prepareConsumerBasic();
//		consumer.subscribe(Collections.singleton(EXAMPLE_TOPIC));
		
		// IMPORTANT: override the property in application.yml
		// System.setProperty("spring.kafka.bootstrap-servers", kafkaBootstrapServers);
	}

	@Test
	public void whenCallASendMessageDefaultWithTemplate_ThenReceiveMessage() throws InterruptedException {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, null, new Date().toString(), "");
		
		kafkaTemplate.sendDefault(messageTest);

		kafkaConsumerService.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, kafkaConsumerService.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(kafkaConsumerService.getLatchTest().getCount()).isEqualTo(0);
	}
	
	@Test
	public void whenCallASendMessageWithTemplate_ThenReceiveMessage() throws InterruptedException {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, null, new Date().toString(), "");
		
		kafkaTemplate.send(topic, messageTest);

		kafkaConsumerService.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, kafkaConsumerService.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(kafkaConsumerService.getLatchTest().getCount()).isEqualTo(0);
	}
	
	@Test
	public void whenCallASendMessageWithSender_ThenReceiveMessage() throws InterruptedException {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, null, new Date().toString(), "");
		
		kafkaProducerService.send(messageTest);

		kafkaConsumerService.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, kafkaConsumerService.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(kafkaConsumerService.getLatchTest().getCount()).isEqualTo(0);
	}
	
//	@Test
//	public void whenCallASendMessageDefaultWithTemplateAndConsumer_ThenReceiveMessage() throws InterruptedException {
//		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);
//
//		ConsumerRecords<Integer, String> records = KafkaTestUtils.getRecords(consumer);
//        assertThat(records).isNull();
//	}

}
