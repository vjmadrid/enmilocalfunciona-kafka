package com.acme.kafka.testing.embedded.producer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import com.acme.kafka.testing.constant.TestingConstant;
import com.acme.kafka.testing.consumer.util.KafkaConsumerPropertiesUtil;
import com.acme.kafka.testing.producer.KafkaProducerService;

@SpringBootTest
@EmbeddedKafka
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaProducerServiceTest {

	public static final Logger LOG = LoggerFactory.getLogger(KafkaProducerServiceTest.class);

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	private KafkaMessageListenerContainer<String, String> container;

	private BlockingQueue<ConsumerRecord<String, String>> records;

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Value("${app.topic.example1}")
	private String topic;
	
	private String messageTest;

	private void setupKafkaConsumerTestEnvironment() throws Exception {
		LOG.debug("*** SETUP Kafka Consumer Test Environment ***");

		LOG.debug("\t[*] Create a Kafka consumer factory (Spring wrapper)");
		DefaultKafkaConsumerFactory<String, String> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(
				KafkaConsumerPropertiesUtil.generateKafkaConsumerProperties(embeddedKafkaBroker));

		LOG.debug("\t[*] Set up the container properties with TOPIC :: " + topic);
		ContainerProperties containerProperties = new ContainerProperties(topic);

		LOG.debug("\t[*] Create a Container with MessageListenerContainer -> topic, ...");
		container = new KafkaMessageListenerContainer<>(kafkaConsumerFactory, containerProperties);

		LOG.debug("\t[*] Create a thread safe queue to store the received message");
		records = new LinkedBlockingQueue<>();

		LOG.debug("\t[*] Setup a Kafka message listener");
		container.setupMessageListener((MessageListener<String, String>) records::add);

		LOG.debug("\t[*] Start Container");
		container.start();

		LOG.debug("\t[*] Wait until the container has the required number of assigned partitions");
		ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
	}

	@BeforeEach
	public void setUp() throws Exception {
		System.out.println("*** SETUP ***");
		
		String kafkaBootstrapServers = embeddedKafkaBroker.getBrokersAsString();
		System.out.println("\t[*] kafkaBootstrapServers :: "+kafkaBootstrapServers);
		
		setupKafkaConsumerTestEnvironment();
	}

	@AfterEach
	public void tearDown() {
		container.stop();
	}
	
	@Test
	public void whenSendMenssageWithService_thenMessageIsReceived() throws InterruptedException {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, "id_1", new Date().toString(), "");
		
		kafkaProducerService.send(messageTest);

		ConsumerRecord<String, String> receivedOneRecord = records.poll(1000, TimeUnit.MILLISECONDS);

		// AssertJ
		assertThat(receivedOneRecord).isNotNull();
		assertThat(receivedOneRecord.key()).isNull();
		assertThat(receivedOneRecord.value()).isEqualTo(messageTest);
	}

}
