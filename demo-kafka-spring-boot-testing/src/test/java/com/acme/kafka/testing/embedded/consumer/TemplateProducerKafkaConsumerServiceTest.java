package com.acme.kafka.testing.embedded.consumer;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import org.springframework.test.annotation.DirtiesContext;

import com.acme.kafka.testing.constant.TestingConstant;
import com.acme.kafka.testing.consumer.KafkaConsumerService;
import com.acme.kafka.testing.producer.util.KafkaProducerTemplateUtil;

@SpringBootTest
@EmbeddedKafka
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemplateProducerKafkaConsumerServiceTest {

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private KafkaConsumerService kafkaConsumerService;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	@Value("${app.topic.example1}")
	private String topic;
	
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
	}

	@Test
	public void whenCallASendMessageWithTemplate_ThenReceiveMessage() throws InterruptedException {
		String messageTest_1 = String.format(TestingConstant.MESSAGE_TEMPLATE, "id_1", new Date().toString(), "");
		
		kafkaTemplate.sendDefault(messageTest_1);

		kafkaConsumerService.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
	    //AssertJ
		assertThat(kafkaConsumerService.getLatchTest().getCount()).isEqualTo(0);
	    assertThat(kafkaConsumerService.getPayload()).contains(messageTest_1);
	    assertThat(kafkaConsumerService.getTopic()).contains(topic);
	}
	
	@Test
	public void whenCallASendMessageWithTemplateWithTopic_ThenReceiveMessage() throws InterruptedException {
		String messageTest_2 = String.format(TestingConstant.MESSAGE_TEMPLATE, "id_2", new Date().toString(), "");
		
		kafkaTemplate.send(topic, messageTest_2);

		kafkaConsumerService.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
	    //AssertJ
	    assertThat(kafkaConsumerService.getLatchTest().getCount()).isEqualTo(0);
	    assertThat(kafkaConsumerService.getPayload()).contains(messageTest_2);
	    assertThat(kafkaConsumerService.getTopic()).contains(topic);
	}

}
