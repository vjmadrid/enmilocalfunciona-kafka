package com.acme.core.architecture.connector.constant;

public final class KafkaConnectorConsumerConstant {
	
	private KafkaConnectorConsumerConstant() {	
	}
	
	public static final String CONNECTOR_BEAN_KAFKA_CONSUMER_FACTORY_STRING_NAME = "consumerFactoryString";
	public static final String CONNECTOR_BEAN_KAFKA_CONSUMER_FACTORY_JSON_NAME = "consumerFactoryJson";
	public static final String CONNECTOR_BEAN_KAFKA_CONSUMER_FACTORY_BYTE_ARRAY_NAME = "consumerFactoryByteArray";

	public static final String CONNECTOR_BEAN_KAFKA_LISTENER_CONTAINER_FACTORY_STRING_NAME = "kafkaListenerContainerFactoryString";
	public static final String CONNECTOR_BEAN_KAFKA_LISTENER_CONTAINER_FACTORY_JSON_NAME = "kafkaListenerContainerFactoryJson";
	public static final String CONNECTOR_BEAN_KAFKA_TLISTENER_CONTAINER_FACTORY_BYTE_ARRAY_NAME = "kafkaListenerContainerFactoryBytesArray";
	
}
