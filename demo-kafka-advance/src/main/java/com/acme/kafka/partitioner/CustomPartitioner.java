package com.acme.kafka.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class CustomPartitioner implements Partitioner {

	private Map<String, Integer> partitionMap;

	@Override
	public void configure(Map<String, ?> configs) {
		System.out.println("Configure " + configs);
		partitionMap = new HashMap<String, Integer>();

		for (Map.Entry<String, ?> entry : configs.entrySet()) {

			if (entry.getKey().startsWith("partitions.")) {
				String keyName = entry.getKey();
				String value = (String) entry.getValue();

				System.out.println(keyName.substring(11));

				int paritionId = Integer.parseInt(keyName.substring(11));
				partitionMap.put(value, paritionId);
			}

		}

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		// List partitions = cluster.availablePartitionsForTopic(topic);
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
