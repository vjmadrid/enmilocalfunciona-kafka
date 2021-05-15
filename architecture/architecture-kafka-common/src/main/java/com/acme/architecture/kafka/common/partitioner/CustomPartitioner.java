package com.acme.architecture.kafka.common.partitioner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomPartitioner implements Partitioner {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomPartitioner.class);

	private Map<String, Integer> partitionMap;

	@Override
	public void configure(Map<String, ?> configs) {
		LOG.info("[CustomPartitioner] *** configure ***");
		LOG.info("\t [*] Configure {}", configs);
		
		partitionMap = new HashMap<String, Integer>();

		// Mapping Value with Partition number
		for (Map.Entry<String, ?> entry : configs.entrySet()) {

			if (entry.getKey().startsWith("partitions.")) {
				String keyName = entry.getKey();
				String value = (String) entry.getValue();

				LOG.info(keyName.substring(11));

				int paritionId = Integer.parseInt(keyName.substring(11));
				partitionMap.put(value, paritionId);
			}

		}
		
		LOG.info("\t [*] Map {}", partitionMap);
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		LOG.info("[CustomPartitioner] *** partition ***");
		
		List partitions = cluster.availablePartitionsForTopic(topic);
		LOG.info("\t [*] partitions =[{}]", partitions);
		
		int numPartitions = partitions.size();
		LOG.info("\t [*] numPartitions =[{}]", numPartitions);
		
		// String valueStr = (String) value;
		// String countryName = ((String) value).split(":")[0];
		if (partitionMap.containsKey(key.toString())) {
			// If the country is mapped to particular partition return it
			return partitionMap.get(key.toString());
		} else {
			// If no country is mapped to particular partition distribute
			// between remaining partitions
			int noOfPartitions = cluster.topics().size();
			return value.hashCode() % noOfPartitions + partitionMap.size();
		}
	}

	@Override
	public void close() {

	}

}
