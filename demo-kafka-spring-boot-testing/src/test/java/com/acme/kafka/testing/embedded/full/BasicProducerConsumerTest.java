package com.acme.kafka.testing.embedded.full;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.acme.kafka.testing.constant.TestingConstant;
import com.acme.kafka.testing.consumer.KafkaConsumerService;
import com.acme.kafka.testing.producer.KafkaProducerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EmbeddedKafka
@DirtiesContext
public class BasicProducerConsumerTest {
	
	public static final Logger LOG = LoggerFactory.getLogger(BasicProducerConsumerTest.class);

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;
	
	@Autowired
	private KafkaProducerService kafkaProducerService;
	
	@Autowired
	private KafkaConsumerService kafkaConsumerService;
	
	@Value("${app.topic.example1}")
	private String topic;
	
	private String messageTest;
	
	@BeforeEach
	public void setup() throws Exception {
		System.out.println("*** SETUP ***");
		
		String kafkaBootstrapServers = embeddedKafkaBroker.getBrokersAsString();
		System.out.println(" [*] kafkaBootstrapServers :: "+kafkaBootstrapServers);
		
		// IMPORTANT: override the property in application.yml
	    //System.setProperty("spring.kafka.bootstrap-servers", kafkaBootstrapServers);
		
		embeddedKafkaBroker.addTopics(new NewTopic("create-topic", 1, (short) 1));
		
		Set<String> kafkaTopics = embeddedKafkaBroker.getTopics();
		System.out.println(" [*] kafkaTopics [size "+kafkaTopics.size()+"] :: "+Arrays.toString(kafkaTopics.toArray()));
	}

	@Test
	public void whenCallASendMessage_ThenReceiveMessage() throws InterruptedException {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, "id_1", new Date().toString(), "");
		LOG.info("message='{}'", messageTest);
		
		kafkaProducerService.send(messageTest);
		
		kafkaConsumerService.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
	    //AssertJ
	    assertThat(kafkaConsumerService.getLatchTest().getCount()).isEqualTo(0);
	    assertThat(kafkaConsumerService.getPayload()).contains(messageTest);
	    assertThat(kafkaConsumerService.getTopic()).contains(topic);
	}

}
