         package com.acme.architecture.kafka.common.consumer.listener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.consumer.enumeration.SeekToTypeEnumeration;

public class Custom2ConsumerRebalanceListener implements ConsumerRebalanceListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(Custom2ConsumerRebalanceListener.class);
	
	private Map<TopicPartition, OffsetAndMetadata> processedOffsets;
	
	private long startingOffset;
	
	private KafkaConsumer<String, String> kafkaConsumer;
	
	private SeekToTypeEnumeration seekTo;
	
	public Custom2ConsumerRebalanceListener(Map<TopicPartition, OffsetAndMetadata> processedOffsets, final long startingOffset, KafkaConsumer<String, String> kafkaConsumer) {
		super();
		this.processedOffsets = processedOffsets;
		this.startingOffset = startingOffset;
		this.kafkaConsumer = kafkaConsumer;
	}

	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		LOG.info("[CustomConsumerRebalanceListener] New partition assigned partition=[{}]", partitions);
		LOG.info("\t [*] {} topic-partitions are revoked from this consumer\n", Arrays.toString(partitions.toArray()));
		
	}

	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		LOG.info("[CustomConsumerRebalanceListener] Lost partitions in rebalance.  partition=[{}]", partitions);
		LOG.info("\t [*] Committing processed offsets processedOffsets=[{}]", processedOffsets);
		LOG.info("\t [*] {} topic-partitions are assigned to this consumer\n", Arrays.toString(partitions.toArray()));
		
		Iterator<TopicPartition> topicPartitionIterator = partitions.iterator();
		
		switch (seekTo) {
		
	        case END: //Seek to end
	        	LOG.info("Setting it to the end");
	        	kafkaConsumer.seekToEnd(partitions);
	            break;
	        case START: //Seek to start
	        	LOG.info("Setting offset to begining");
	        	kafkaConsumer.seekToBeginning(partitions);
	            break;
	        case LOCATION: //Seek to a given location
	        	LOG.info("Resetting offset to " + startingOffset);
	        	
	        	while (topicPartitionIterator.hasNext()) {
					TopicPartition topicPartition = topicPartitionIterator.next();
					System.out.println("Current offset is " + kafkaConsumer.position(topicPartition)
							+ " committed offset is ->" + kafkaConsumer.committed(topicPartition));
					kafkaConsumer.seek(topicPartition, startingOffset);

				}
	        	
//	            partitions.forEach(topicPartition -> 
//	            		kafkaConsumer.seek(topicPartition, startingOffset)
//	            );
	            break;
	        case NONE:
	        	LOG.info("Leaving it alone");
	        	break;
	        	
		}

		
	}

	
}
