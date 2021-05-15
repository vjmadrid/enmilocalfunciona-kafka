package com.acme.kafka.consumer.group;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.consumer.runnable.ConsumerRunnable;
import com.acme.kafka.consumer.runnable.factory.ConsumerRunnableFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerGroupRunnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerGroupRunnable.class);

	private String idConsumer;
	private String brokers;
	private String groupId;
	private String topic;

	private int numberConsumers;
	private List<ConsumerRunnable> kafkaConsumerList;

	public ConsumerGroupRunnable(String idConsumer, String brokers, String groupId, String topic, int numberConsumers) {
		this.idConsumer = idConsumer;
		this.brokers = brokers;
		this.topic = topic;
		this.groupId = groupId;
		
		this.numberConsumers = numberConsumers;
		kafkaConsumerList = new ArrayList<>();
		
		// Prepare Consumers
		for (int i = 0; i < this.numberConsumers; i++) {
			ConsumerRunnable consumerThread = ConsumerRunnableFactory.createConsumerRunnable(this.idConsumer, this.brokers, this.groupId, this.topic);
			kafkaConsumerList.add(consumerThread);
		}
	}
	
	public void executeConsumers() {
		LOG.info("[ConsumerGroupRunnable] *** Execute Consumers ***");
		
		if (!kafkaConsumerList.isEmpty()) {
			for (ConsumerRunnable consumerThread : kafkaConsumerList) {
	            Thread t = new Thread(consumerThread);
	            t.start();
	            
	            LOG.info("[*] Thread Id=[{}] \n" +
            			"\tName: {} \n" +
            			"\tPriority: {} \n" +
                        "\tState: {} \n", 
                        t.getId(), t.getName(), t.getPriority(), t.getState() );
	        }
		}
    }
	
	

}
