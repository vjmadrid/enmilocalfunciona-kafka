package com.acme.architecture.kafka.common.partitioner;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class CustomMod2Partitioner implements Partitioner{

	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		Integer keyInt=Integer.parseInt(key.toString());
		return keyInt % 2;
	}

	@Override
	public void close() {
		
	}
	
}
