package com.acme.kafka.testing.embedded.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.messaging.MessageHeaders;

import com.acme.kafka.testing.consumer.KafkaConsumerService;
import com.acme.kafka.testing.producer.util.KafkaProducerUtil;

@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaConsumerServiceTest {

	public static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerServiceTest.class);

	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Hello World! "+new Date();

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@SpyBean
	private KafkaConsumerService kafkaConsumerService;

	private Producer<String, String> kafkaProducer;
	
	@Captor
    ArgumentCaptor<String> messageArgumentCaptor;

    @Captor
    ArgumentCaptor<MessageHeaders> headerArgumentCaptor;

	@BeforeEach
	public void setUp() throws Exception {
		System.out.println("*** SETUP ***");

		String kafkaBootstrapServers = embeddedKafkaBroker.getBrokersAsString();
		System.out.println(" [*] kafkaBootstrapServers :: " + kafkaBootstrapServers);

		kafkaProducer = KafkaProducerUtil.generateKafkaProducer(embeddedKafkaBroker);
	}

	@Test
	public void shouldConsumeFromTemplate() throws InterruptedException {
		
		kafkaProducer.send(new ProducerRecord<>(EXAMPLE_TOPIC, 0, "", TEST_MESSAGE_VALUE));
		
		LOG.debug("should sent message='{}'", TEST_MESSAGE_VALUE);
		kafkaProducer.flush();
		
		verify(kafkaConsumerService, timeout(1000).times(1))
        .receive(messageArgumentCaptor.capture(), headerArgumentCaptor.capture());

		 String value = messageArgumentCaptor.getValue();
	     assertNotNull(value);
	     assertEquals("11111", value);
	}

}
