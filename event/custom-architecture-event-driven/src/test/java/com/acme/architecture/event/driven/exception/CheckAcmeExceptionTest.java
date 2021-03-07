package com.acme.architecture.event.driven.exception;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CheckAcmeExceptionTest {
	
	@Test
	public void whenExceptionWithString_thenThrowException() {
		assertThrows(AcmeEventDrivenException.class, () -> {
			throw new AcmeEventDrivenException("Test");
		});
	}
	
	@Test
	public void whenExceptionWithThrowable_thenThrowException() {
		assertThrows(AcmeEventDrivenException.class, () -> {
			throw new AcmeEventDrivenException(new ArithmeticException());
		});
	}
	
	@Test
	public void whenExceptionWithStringAndThrowable_thenThrowException() {
		assertThrows(AcmeEventDrivenException.class, () -> {
			throw new AcmeEventDrivenException("Test", new ArithmeticException());
		});
	}

}
