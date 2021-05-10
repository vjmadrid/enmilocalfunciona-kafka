package com.acme.kafka.consumer.group;

import java.util.ArrayList;
import java.util.List;

import com.acme.kafka.consumer.runnable.ConsumerRunnable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerGroupRunnable {

	private String brokers;
	private String groupId;
	private String topic;

	private int numberConsumers;
	private List<ConsumerRunnable> kafkaConsumerList;

	public ConsumerGroupRunnable(String brokers, String groupId, String topic, int numberConsumers) {
		this.brokers = brokers;
		this.topic = topic;
		this.groupId = groupId;
		
		this.numberConsumers = numberConsumers;
		kafkaConsumerList = new ArrayList<>();
		
		// Prepare Consumers
		for (int i = 0; i < this.numberConsumers; i++) {
			ConsumerRunnable consumerThread = new ConsumerRunnable(this.brokers, this.groupId, this.topic);
			kafkaConsumerList.add(consumerThread);
		}
	}
	
	public void executeConsumers() {
		if (!kafkaConsumerList.isEmpty()) {
			for (ConsumerRunnable consumerThread : kafkaConsumerList) {
	            Thread t = new Thread(consumerThread);
	            t.start();
	        }
		}
    }
	
	

}
