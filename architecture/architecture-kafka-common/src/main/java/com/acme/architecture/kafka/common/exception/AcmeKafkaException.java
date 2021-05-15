package com.acme.architecture.kafka.common.exception;

public class AcmeKafkaException extends Exception {

	private static final long serialVersionUID = -1463050916312131247L;

	public AcmeKafkaException(String message) {
		super(message);
	}

	public AcmeKafkaException(Throwable cause) {
		super(cause);
	}

	public AcmeKafkaException(String message, Throwable cause) {
		super(message, cause);
	}

}
