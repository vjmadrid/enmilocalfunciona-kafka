package com.acme.core.architecture.connector.constant;

public final class KafkaConnectorProducerConstant {
	
	private KafkaConnectorProducerConstant() {	
	}
	
	public static final String CONNECTOR_BEAN_KAFKA_PRODUCER_FACTORY_STRING_NAME = "producerFactoryString";
	public static final String CONNECTOR_BEAN_KAFKA_PRODUCER_FACTORY_JSON_NAME = "producerFactoryJson";
	public static final String CONNECTOR_BEAN_KAFKA_PRODUCER_FACTORY_BYTE_ARRAY_NAME = "producerFactoryByteArray";

	public static final String CONNECTOR_BEAN_KAFKA_TEMPLATE_STRING_NAME = "kafkaTemplateString";
	public static final String CONNECTOR_BEAN_KAFKA_TEMPLATE_JSON_NAME = "kafkaTemplateJson";
	public static final String CONNECTOR_BEAN_KAFKA_TEMPLATE_BYTE_ARRAY_NAME = "kafkaTemplateByteArray";
	
}
