package com.acme.kafka.producer.group;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.kafka.producer.async.runnable.ProducerAsyncRunnable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerGroupRunnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProducerGroupRunnable.class);

	private String brokers;
	private String groupId;
	private String topic;

	private int numberProducer;
	private List<ProducerAsyncRunnable> kafkaProducerList;

	public ProducerGroupRunnable(String brokers, String topic, int numberProducer) {
		
		this.brokers = brokers;
		this.topic = topic;
		
		this.numberProducer = numberProducer;
		kafkaProducerList = new ArrayList<>();
		
		// Prepare Producer
		for (int i = 0; i < this.numberProducer; i++) {
			ProducerAsyncRunnable producerThread = new ProducerAsyncRunnable(this.brokers, this.topic);
			kafkaProducerList.add(producerThread);
		}
	}
	
	public void executeProducers() {
		LOG.info("[ProducerGroupRunnable] *** Execute Producers ***");
		
		if (!kafkaProducerList.isEmpty()) {
			for (ProducerAsyncRunnable producerThread : kafkaProducerList) {
	            Thread t = new Thread(producerThread);
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
