package com.acme.architecture.kafka.common.partitioner.util;

import java.util.Random;

import com.acme.architecture.kafka.common.partitioner.constant.CitiesPartitionerConstant;

public class CitiesPartitionerUtil {
	
	private CitiesPartitionerUtil() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static String getRandomCitiesPartition() {
		String value[] = { CitiesPartitionerConstant.PARTITION_0_VALUE_MADRID, CitiesPartitionerConstant.PARTITION_1_VALUE_BARCELONA, CitiesPartitionerConstant.PARTITION_2_VALUE_SEVILLA};
		
		return value[new Random().nextInt(value.length)];
	}

}
