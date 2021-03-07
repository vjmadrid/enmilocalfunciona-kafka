package com.acme.architecture.event.driven.util.validator;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.architecture.event.driven.dummy.DummyGenericEvent;
import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.enumerate.GenericEventTypeEnum;

public class GenericEventValidatorUtilTest {

	private GenericEvent genericEventTest;

	@BeforeEach
	public void init() {
		genericEventTest = DummyGenericEvent.createSampleDefault();
	}

	@Test
	public void shouldCreateDefaultConstructor_ThenTrowIllegalStateException() {

		assertThrows(IllegalStateException.class, () -> {
			new GenericEventValidatorUtil();
		});
	}
	
	@Test
	public void shouldIsNull() {
		assertTrue(GenericEventValidatorUtil.isNull(null));
	}

	@Test
	public void shouldIsNullWithNotNull() {
		assertFalse(GenericEventValidatorUtil.isNull(genericEventTest));
	}

	@Test
	public void shouldIsNotNull() {
		assertTrue(GenericEventValidatorUtil.isNotNull(genericEventTest));
	}

	@Test
	public void shouldIsNotNullWithNull() {
		assertFalse(GenericEventValidatorUtil.isNotNull(null));
	}

	@Test
	public void shouldIsValidWithNull() {
		assertFalse(GenericEventValidatorUtil.isValid(null));
	}

	@Test
	public void shouldIsValidWithIdNull() {
		genericEventTest.setId(null);
		assertFalse(GenericEventValidatorUtil.isValid(genericEventTest));
	}

	@Test
	public void shouldIsValid() {
		assertTrue(GenericEventValidatorUtil.isValid(genericEventTest));
	}
	
	@Test
	public void shouldIsGenericCreateTypeWithNull() {
		assertFalse(GenericEventValidatorUtil.isGenericCreateType(null));
	}
	
	@Test
	public void shouldIsGenericCreateTypeWithIdNull() {
		genericEventTest.setId(null);
		assertFalse(GenericEventValidatorUtil.isGenericCreateType(genericEventTest));
	}

	@Test
	public void shouldIsGenericCreateTypeWithNoValidValue() {
		genericEventTest.setType(GenericEventTypeEnum.DELETE.toString());
		assertFalse(GenericEventValidatorUtil.isGenericCreateType(genericEventTest));
	}
	
	@Test
	public void shouldIsGenericCreateType() {
		assertTrue(GenericEventValidatorUtil.isGenericCreateType(genericEventTest));
	}
	
	@Test
	public void shouldIsGenericUpdateTypeWithNull() {
		assertFalse(GenericEventValidatorUtil.isGenericUpdateType(null));
	}
	
	@Test
	public void shouldIsGenericUpdateTypeWithIdNull() {
		genericEventTest.setType(GenericEventTypeEnum.UPDATE.toString());
		genericEventTest.setId(null);
		assertFalse(GenericEventValidatorUtil.isGenericUpdateType(genericEventTest));
	}

	@Test
	public void shouldIsGenericUpdateTypeWithNoValidValue() {
		genericEventTest.setType(GenericEventTypeEnum.DELETE.toString());
		assertFalse(GenericEventValidatorUtil.isGenericUpdateType(genericEventTest));
	}
	
	@Test
	public void shouldIsGenericUpdateType() {
		genericEventTest.setType(GenericEventTypeEnum.UPDATE.toString());
		assertTrue(GenericEventValidatorUtil.isGenericUpdateType(genericEventTest));
	}
	
	@Test
	public void shouldIsGenericDeleteTypeWithNull() {
		assertFalse(GenericEventValidatorUtil.isGenericDeleteType(null));
	}
	
	@Test
	public void shouldIsGenericDeleteTypeWithIdNull() {
		genericEventTest.setType(GenericEventTypeEnum.DELETE.toString());
		genericEventTest.setId(null);
		assertFalse(GenericEventValidatorUtil.isGenericDeleteType(genericEventTest));
	}

	@Test
	public void shouldIsGenericDeleteTypeWithNoValidValue() {
		genericEventTest.setType(GenericEventTypeEnum.UPDATE.toString());
		assertFalse(GenericEventValidatorUtil.isGenericDeleteType(genericEventTest));
	}
	
	@Test
	public void shouldIsGenericDeleteType() {
		genericEventTest.setType(GenericEventTypeEnum.DELETE.toString());
		assertTrue(GenericEventValidatorUtil.isGenericDeleteType(genericEventTest));
	}
	
	@Test
	public void shouldIsDeletedLogicalVipWithIdUserMessageNull() {
		genericEventTest.setId(null);
		assertFalse(GenericEventValidatorUtil.isDeletedLogical(genericEventTest));
	}
	
	@Test
	public void shouldIsDeletedLogicalVipNoValidValue() {
		assertFalse(GenericEventValidatorUtil.isDeletedLogical(genericEventTest));
	}
	
	@Test
	public void shouldUsDeletedLogical() {
		genericEventTest.setDeletedDate(new Date());
		assertTrue(GenericEventValidatorUtil.isDeletedLogical(genericEventTest));
	}

}