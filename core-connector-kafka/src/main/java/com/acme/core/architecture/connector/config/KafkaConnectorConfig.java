package com.acme.core.architecture.connector.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.acme.core.architecture.connector.constant.KafkaConnectorConstant;

@Configuration
@ComponentScan(basePackages = { KafkaConnectorConstant.CONNECTOR_PACKAGE })
public class KafkaConnectorConfig {
	
	
}
