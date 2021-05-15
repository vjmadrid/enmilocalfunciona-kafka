package com.acme.kafka.util;

import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;

public class KafkaPropertiesUtil {
	
	private KafkaPropertiesUtil() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static void printProperties(Properties prop, Logger LOG) {
		Objects.requireNonNull(prop);
	
		if (!prop.isEmpty()) {
			
			for (Object key: prop.keySet()) {
				
				if (LOG == null) {
					 System.out.println("\t - "+ key + ": " + prop.getProperty(key.toString()));
				} else {
					 LOG.info("\t - {}: {}",key, prop.getProperty(key.toString()));
				}
		    }

		}
		
	}

}
