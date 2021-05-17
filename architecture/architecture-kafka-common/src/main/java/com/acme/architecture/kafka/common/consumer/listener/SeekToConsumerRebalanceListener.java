package com.acme.architecture.kafka.common.consumer.listener;

import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import com.acme.architecture.kafka.common.consumer.enumeration.SeekToTypeEnumeration;

public class SeekToConsumerRebalanceListener implements ConsumerRebalanceListener {

	private KafkaConsumer<String, String> kafkaConsumer;
	
	private SeekToTypeEnumeration seekTo;
	
	private long location;
	
	public SeekToConsumerRebalanceListener(final KafkaConsumer<String, String> kafkaConsumer, final SeekToTypeEnumeration seekTo, final long location) {
        this.seekTo = seekTo;
        this.location = location;
        this.kafkaConsumer = kafkaConsumer;
    }
	
	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		switch (this.seekTo) {
        case END:                   //Seek to end
        	kafkaConsumer.seekToEnd(partitions);
            break;
        case START:                 //Seek to start
        	kafkaConsumer.seekToBeginning(partitions);
            break;
        case LOCATION:              //Seek to a given location
            partitions.forEach(topicPartition ->
            kafkaConsumer.seek(topicPartition, location));
            break;
    }
		
	}

}
