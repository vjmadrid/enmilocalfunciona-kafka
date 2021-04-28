package com.acme.kafka.testing.embedded.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.acme.kafka.testing.consumer.KafkaConsumerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EmbeddedKafka
public class AdvanceConsumerTest {

	public static final Logger LOG = LoggerFactory.getLogger(AdvanceConsumerTest.class);

	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Hello World! "+new Date();

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private KafkaConsumerService basicConsumer;

	private KafkaTemplate<String, String> kafkaTemplate;
	
	private Producer<String, String> kafkaProducer;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	private void setupKafkaProducerTestEnvironment() throws Exception {
		LOG.debug("*** SETUP Kafka Producer Test Environment ***");

		LOG.debug("\t[*] SetUp Kafka producer properties");
		Map<String, Object> kafkaProducerProperties = KafkaTestUtils.producerProps(
				embeddedKafkaBroker
		);

		LOG.debug("\t[*] Create a Kafka producer factory (Spring wrapper)");
		ProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<String, String>(
				kafkaProducerProperties,
				new StringSerializer(), 
				new StringSerializer()
		);

		LOG.debug("\t[*] Create a Kafka template");
		kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
		kafkaTemplate.setDefaultTopic(EXAMPLE_TOPIC);

		LOG.debug("\t[*]  Create a Kafka Producer");
		kafkaProducer = kafkaProducerFactory.createProducer();
	
		LOG.debug("\t[*]  Wait until the container has the required number of assigned partitions");
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer,
					embeddedKafkaBroker.getPartitionsPerTopic());
		}
	}

	@BeforeEach
	public void setUp() throws Exception {
		System.out.println("*** SETUP ***");

		String kafkaBootstrapServers = embeddedKafkaBroker.getBrokersAsString();
		System.out.println(" [*] kafkaBootstrapServers :: " + kafkaBootstrapServers);

		setupKafkaProducerTestEnvironment();
	}

	@Test
	public void shouldConsumeFromTemplate() throws InterruptedException {
		
		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);
		LOG.debug("should sent message='{}'", TEST_MESSAGE_VALUE);
		kafkaTemplate.flush();

		basicConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
	    //AssertJ
	    assertThat(basicConsumer.getLatchTest().getCount()).isEqualTo(0);
	}

}
