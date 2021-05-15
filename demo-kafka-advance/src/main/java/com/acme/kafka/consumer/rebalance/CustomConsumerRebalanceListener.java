package com.acme.kafka.consumer.rebalance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomConsumerRebalanceListener implements ConsumerRebalanceListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomConsumerRebalanceListener.class);
	
	private Map<TopicPartition, OffsetAndMetadata> processedOffsets;
	
	public CustomConsumerRebalanceListener(Map<TopicPartition, OffsetAndMetadata> processedOffsets) {
		super();
		this.processedOffsets = processedOffsets;
	}

	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		LOG.info("[CustomConsumerRebalanceListener] New partition assigned partition=[{}]", partitions);
		LOG.info("\t [*] %s topic-partitions are revoked from this consumer\n", Arrays.toString(partitions.toArray()));
		
	}

	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		LOG.info("[CustomConsumerRebalanceListener] Lost partitions in rebalance.  partition=[{}]", partitions);
		LOG.info("\t [*] Committing processed offsets processedOffsets=[{}]", processedOffsets);
		LOG.info("\t [*] %s topic-partitions are assigned to this consumer\n", Arrays.toString(partitions.toArray()));
		
	}

}
