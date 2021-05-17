package com.acme.architecture.kafka.common.producer.interceptor;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProducerInterceptor implements ProducerInterceptor {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomProducerInterceptor.class);
	
	private int sendCount;
	
    private int ackCount;

	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProducerRecord onSend(ProducerRecord record) {
		sendCount++;
		
		LOG.info("[*] onSend \n" +
				"\tCount: {} \n" +
		        "\tTopic: {} \n" +
		        "\tPartition: {} \n" +
		        "\tKey: {} \n" +
		        "\tValue: {}", sendCount, record.topic(), record.partition(), record.key(), record.value().toString()
        );
		
		return record;
	}

	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		ackCount++;
		
		 LOG.info("[*] onAcknowledgement Received metadata \n" +
				 "\tCount: {} \n" +
		         "\tTopic: {} \n" +
		         "\tPartition: {} \n" +
		         "\tOffset: {} \n" +
		         "\tTimestamp: {}", 
				 ackCount, metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp()
         );

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
