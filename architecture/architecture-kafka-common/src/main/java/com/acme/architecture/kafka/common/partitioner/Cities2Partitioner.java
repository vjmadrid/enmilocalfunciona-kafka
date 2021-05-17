package com.acme.architecture.kafka.common.partitioner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.partitioner.constant.CitiesPartitionerConstant;

public class Cities2Partitioner implements Partitioner {
	
	private static final Logger LOG = LoggerFactory.getLogger(Cities2Partitioner.class);

	private Set<String> citiesSelectedSet;
	
	@Override
	public void configure(Map<String, ?> configs) {
		LOG.info("[Cities2Partitioner] *** configure ***");
		LOG.info("\t [*] Configure {}", configs);
		
		// Use Property Full
		final String citiesSelectedString = (String) configs.get(CitiesPartitionerConstant.PROPERTY_CITIES_SELECTED);
        Arrays.stream(citiesSelectedString.split(","))
                .forEach(citiesSelectedSet::add);
		
		LOG.info("\t [*] citiesSelectedSet {}", citiesSelectedSet);
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		LOG.info("[Cities2Partitioner] *** partition ***");
		
		List<PartitionInfo> partitionInfoList = cluster.availablePartitionsForTopic(topic);
		LOG.info("\t [*] partitionInfoList =[{}]", partitionInfoList);
		
		int numPartitions = partitionInfoList.size();
		LOG.info("\t [*] numPartitions =[{}]", numPartitions);
		
		int importantPartition = numPartitions -1;
        int normalPartitionCount = numPartitions -1;
		
        final String keyString = ((String) key);
		
		if (citiesSelectedSet.contains(keyString.toString())) {
			return importantPartition;
		} else {
			return Math.abs(keyString.hashCode()) % normalPartitionCount;
		}
	}

	@Override
	public void close() {

	}

}
