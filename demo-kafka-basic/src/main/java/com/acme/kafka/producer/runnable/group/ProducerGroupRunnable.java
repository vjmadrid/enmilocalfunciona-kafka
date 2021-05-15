package com.acme.kafka.producer.runnable.group;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.producer.runnable.factory.ProducerRunnableFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerGroupRunnable<T> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProducerGroupRunnable.class);

	private String brokers;
	
	private String groupId;
	
	private String topic;

	private int numberProducer;
	
	private List<T> kafkaProducerRunnableList;

	public ProducerGroupRunnable(String brokers, String topic, int numberProducer) {
		
		// Prepare brokers
		this.brokers = brokers;
		
		// Prepare topic
		this.topic = topic;
		
		// Prepare producer number
		this.numberProducer = numberProducer;
		
		// Define Kafka Producer Runnable
		kafkaProducerRunnableList = new ArrayList<>();
		
		// Prepare Producer
		for (int i = 0; i < this.numberProducer; i++) {
			kafkaProducerRunnableList.add((T) ProducerRunnableFactory.createProducerAsyncRunnable("producer-"+i, this.brokers, this.topic));
		}
	}
	
	public void executeProducers() {
		LOG.info("*** Execute Producers ***");
		
		if (!kafkaProducerRunnableList.isEmpty()) {
			for (T producerThread : kafkaProducerRunnableList) {
				
				// Define + Start Producer Thread
	            Thread t = new Thread((Runnable) producerThread);
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
