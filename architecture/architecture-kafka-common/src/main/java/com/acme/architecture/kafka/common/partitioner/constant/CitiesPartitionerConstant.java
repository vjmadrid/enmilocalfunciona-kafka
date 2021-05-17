package com.acme.architecture.kafka.common.partitioner.constant;

public final class CitiesPartitionerConstant {

	private CitiesPartitionerConstant() {
	}

	public static final String PROPERTY_CITIES_SELECTED = "citiesSelected";
	
	public static final String PREFIX_PARTITION = "partitions.";
	
	public static final String PARTITION_0_ID = PREFIX_PARTITION + "0";
	public static final String PARTITION_1_ID = PREFIX_PARTITION + "1";
	public static final String PARTITION_2_ID = PREFIX_PARTITION + "2";
	
	public static final String PARTITION_0_VALUE_MADRID = "Madrid";
	public static final String PARTITION_1_VALUE_BARCELONA = "Barcelona";
	public static final String PARTITION_2_VALUE_SEVILLA = "Sevilla";
	
}
