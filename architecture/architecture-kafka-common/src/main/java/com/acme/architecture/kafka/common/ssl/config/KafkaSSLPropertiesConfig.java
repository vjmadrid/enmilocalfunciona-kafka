package com.acme.architecture.kafka.common.ssl.config;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaSSLPropertiesConfig {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaSSLPropertiesConfig.class);
	
	private KafkaSSLPropertiesConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static void setupSSL(Properties kafkaProperties, String pathTruststore,String passwordTruststore,  String pathKeystore, String passwordKeystore) {
		LOG.info("[KafkaSSLPropertiesConfig] *** setupSSL ***");
		Objects.requireNonNull(kafkaProperties);
		Objects.requireNonNull(pathTruststore);
		Objects.requireNonNull(passwordTruststore);
		Objects.requireNonNull(pathKeystore);
		Objects.requireNonNull(passwordKeystore);

		kafkaProperties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		kafkaProperties.setProperty(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, pathTruststore);
		kafkaProperties.setProperty(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, passwordTruststore);
		kafkaProperties.setProperty(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, pathKeystore);
		kafkaProperties.setProperty(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, passwordKeystore);
		kafkaProperties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "HTTPS");
	}
	
	
	
	

}
