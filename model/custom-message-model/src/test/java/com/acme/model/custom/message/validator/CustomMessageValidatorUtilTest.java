package com.acme.model.custom.message.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.model.custom.message.dummy.DummyCustomMessage;
import com.acme.model.custom.message.entity.CustomMessage;
import com.acme.model.custom.message.validator.CustomMessageValidatorUtil;

public class CustomMessageValidatorUtilTest {
	
	private CustomMessage valueTest;

	@BeforeEach
	public void init() {
		valueTest = DummyCustomMessage.createDefault();
	}

	@Test
	public void whenCallIsNullWithNull_thenReturnTrue() {
		assertTrue(CustomMessageValidatorUtil.isNull(null));
	}

	@Test
	public void whenCallIsNullWithValid_thenReturnFalse() {
		assertFalse(CustomMessageValidatorUtil.isNull(valueTest));
	}

	@Test
	public void whenCallIsNotNullWithValid_thenReturnTrue() {
		assertTrue(CustomMessageValidatorUtil.isNotNull(valueTest));
	}

	@Test
	public void whenCallIsNotNullWithNull_thenReturnFalse() {
		assertFalse(CustomMessageValidatorUtil.isNotNull(null));
	}

	@Test
	public void whenCallIsValidWithNull_thenReturnFalse() {
		assertFalse(CustomMessageValidatorUtil.isValid(null));
	}

	@Test
	public void whenCallIsValidWithNullId_thenReturnFalse() {
		valueTest.setId(null);
		assertFalse(CustomMessageValidatorUtil.isValid(valueTest));
	}

	@Test
	public void whenCallIsValidWithVAlid_thenReturnTrue() {
		assertTrue(CustomMessageValidatorUtil.isValid(valueTest));
	}

}
