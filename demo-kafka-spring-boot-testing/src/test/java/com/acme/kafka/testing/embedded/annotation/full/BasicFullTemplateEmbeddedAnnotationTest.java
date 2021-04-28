package com.acme.kafka.testing.embedded.annotation.full;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.acme.kafka.testing.constant.TestingConstant;
import com.acme.kafka.testing.consumer.KafkaConsumerService;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
		partitions = 1, 
		brokerProperties = { 
				"listeners=PLAINTEXT://localhost:9092", "port=9092" 
		}
)
public class BasicFullTemplateEmbeddedAnnotationTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicFullTemplateEmbeddedAnnotationTest.class);
	
	@Autowired
	private KafkaConsumerService kafkaConsumerService;
	
	@Autowired
    public KafkaTemplate<String, String> template;
	
    @Value("${app.topic.example1}")
    private String topic;
    
    private String messageTest;
    
    @BeforeEach
	public void setup() throws Exception {

	}
    
    @Test
	public void whenSendMenssageWithTemplate_thenMessageIsReceived() throws InterruptedException {
    	messageTest = String.format(TestingConstant.MESSAGE_TEMPLATE, "id_1", new Date().toString(), "");
		
    	template.send(topic, messageTest);

		kafkaConsumerService.getLatchTest().await(1000, TimeUnit.MILLISECONDS);
		
	    // AssertJ
	    assertThat(kafkaConsumerService.getLatchTest().getCount()).isEqualTo(0L);
	    assertThat(kafkaConsumerService.getPayload()).contains(messageTest);
	    assertThat(kafkaConsumerService.getTopic()).contains(topic);
	}

}
