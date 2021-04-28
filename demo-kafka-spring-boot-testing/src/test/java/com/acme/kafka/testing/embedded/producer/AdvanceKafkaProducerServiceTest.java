package com.acme.kafka.testing.embedded.producer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
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
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import com.acme.kafka.testing.constant.TestingConstant;
import com.acme.kafka.testing.producer.KafkaProducerService;


@SpringBootTest
@EmbeddedKafka
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdvanceKafkaProducerServiceTest {

	public static final Logger LOG = LoggerFactory.getLogger(AdvanceKafkaProducerServiceTest.class);

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;
	
	private KafkaMessageListenerContainer<String, String> container;

	private BlockingQueue<ConsumerRecord<String, String>> records;

	private Consumer<String, String> kafkaConsumer;
	
	@Autowired
	private KafkaProducerService kafkaProducerService;
	
	@Value("${app.topic.example1}")
	private String topic;
	
	private String messageTest;
	
	private void setupKafkaConsumerTestEnvironment() throws Exception {
		LOG.debug("*** SETUP Kafka Consumer Test Environment ***");
		
		LOG.debug("\t[*] Setup Kafka consumer properties");
		Map<String, Object> kafkaConsumerProperties = KafkaTestUtils.consumerProps(
				TestingConstant.CONSUMER_GROUP_ID, 
				TestingConstant.CONSUMER_AUTO_COMMIT, 
				embeddedKafkaBroker
		);
		
		kafkaConsumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		LOG.debug("\t[*] Create a Kafka consumer factory (Spring wrapper)");
		DefaultKafkaConsumerFactory<String, String> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, String>(
				kafkaConsumerProperties,
				new StringDeserializer(),
				new StringDeserializer()
		);
				
		LOG.debug("\t[*]  Create a Kafka consumer");
		kafkaConsumer = kafkaConsumerFactory.createConsumer();
		kafkaConsumer.subscribe(Arrays.asList(topic));

		LOG.debug("\t[*] Set up the container properties with TOPIC :: "+topic);
		ContainerProperties containerProperties = new ContainerProperties(topic);

		LOG.debug("\t[*] Create a Container with MessageListenerContainer -> topic, ...");
		container = new KafkaMessageListenerContainer<>(kafkaConsumerFactory, containerProperties);

		LOG.debug("\t[*] Create a thread safe queue to store the received message");
		records = new LinkedBlockingQueue<>();

		LOG.debug("\t[*] Setup a Kafka message listener");
		container.setupMessageListener(new MessageListener<String, String>() {
			
			@Override
			public void onMessage(ConsumerRecord<String, String> record) {
				LOG.debug("test-message-listener received message='{}'", record.toString());
				records.add(record);
			}
			
		});

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
		
	    ConsumerRecord<String, String> receivedOneRecord = records.poll(100, TimeUnit.MILLISECONDS);
	    
	    // AssertJ
	    assertThat(receivedOneRecord).isNotNull();
	    assertThat(receivedOneRecord.key()).isNull();
	    assertThat(receivedOneRecord.value()).isEqualTo(messageTest);
	}
	
	@Test
	public void whenSendMenssageWithService_thenMessageIsReceived2() throws InterruptedException {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, "id_2", new Date().toString(), "");
		
		kafkaProducerService.send(messageTest);

	    ConsumerRecord<String, String> receivedOneRecord = KafkaTestUtils.getSingleRecord(kafkaConsumer, topic);
	    
	    // AssertJ
	    assertThat(receivedOneRecord).isNotNull();
	    assertThat(receivedOneRecord.key()).isNull();
	    assertThat(receivedOneRecord.value()).isEqualTo(messageTest);
	}
	
	@Test
    public void whenSendMenssageWithProducerTest_thenMessageIsReceived() throws Exception {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, "id_3", new Date().toString(), "");
		
        Map<String, Object> configKafkaProducerTest = new HashMap<>(KafkaTestUtils.producerProps(
        		embeddedKafkaBroker
        ));
        
        Producer<String, String> kafkaProducerTest = new DefaultKafkaProducerFactory<>(
        		configKafkaProducerTest, 
        		new StringSerializer(), 
        		new StringSerializer())
        .createProducer();

        kafkaProducerTest.send(new ProducerRecord<>(topic, "my-id", messageTest));
        kafkaProducerTest.flush();

        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        
        // AssertJ
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.key()).isEqualTo("my-id");
        assertThat(singleRecord.value()).isEqualTo(messageTest);
    }
	
	
	

}
