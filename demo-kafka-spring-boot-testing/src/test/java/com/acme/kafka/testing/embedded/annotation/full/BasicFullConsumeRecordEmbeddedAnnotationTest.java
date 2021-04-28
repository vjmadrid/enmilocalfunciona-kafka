package com.acme.kafka.testing.embedded.annotation.full;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
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

import com.acme.kafka.testing.constant.TestingConstant;
import com.acme.kafka.testing.consumer.KafkaConsumerService;
import com.acme.kafka.testing.producer.KafkaProducerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
		partitions = TestingConstant.NUM_PARTITIONS, 
		topics = {
				TestingConstant.TOPIC_1,
				TestingConstant.TOPIC_2
		}
)
public class BasicFullConsumeRecordEmbeddedAnnotationTest {
	
	public static final Logger LOG = LoggerFactory.getLogger(BasicFullConsumeRecordEmbeddedAnnotationTest.class);

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private KafkaConsumerService kafkaConsumerService;
	
	private String messageTest;

	@BeforeEach
	public void setup() throws Exception {
		
	}
	
	@Test
	public void givenEmbeddedKafkaWithTopic2_whenSendMenssageWithService_thenMessageIsReceived() throws InterruptedException {
		messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, null, new Date().toString(), "");
		LOG.info("message='{}'", messageTest);
		
		kafkaProducerService.send(TestingConstant.TOPIC_2, messageTest);

		kafkaConsumerService.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
	    
	    //AssertJ
	    assertThat(kafkaConsumerService.getLatchTest().getCount()).isEqualTo(0L);
	    assertThat(kafkaConsumerService.getPayload()).contains(messageTest);
	    assertThat(kafkaConsumerService.getTopic()).isNull();
	}
	


}
