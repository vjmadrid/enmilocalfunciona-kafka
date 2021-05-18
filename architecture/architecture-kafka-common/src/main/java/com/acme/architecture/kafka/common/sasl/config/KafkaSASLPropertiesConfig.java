package com.acme.architecture.kafka.common.sasl.config;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaSASLPropertiesConfig {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaSASLPropertiesConfig.class);
	
	private KafkaSASLPropertiesConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static void setupSASL(Properties kafkaProperties) {
		LOG.info("[KafkaSASLPropertiesConfig] *** setupSASL ***");
		Objects.requireNonNull(kafkaProperties);

		kafkaProperties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT"); // Use SASL_SSL for SASL over SSL
		kafkaProperties.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"userX\" password=\"123456\";");
		kafkaProperties.setProperty(SaslConfigs.SASL_MECHANISM, "PLAIN");
	}
	
	
	
	

}
