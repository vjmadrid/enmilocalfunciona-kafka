package com.acme.architecture.event.driven.util.validator;

import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.enumerate.GenericEventTypeEnum;

public final class GenericEventValidatorUtil {
	
	protected GenericEventValidatorUtil() {
		throw new IllegalStateException("GenericEventValidatorUtil");
	}

	public static boolean isNull(GenericEvent genericEvent) {
		return (genericEvent == null);
	}

	public static boolean isNotNull(GenericEvent genericEvent) {
		return (genericEvent != null);
	}

	public static boolean isValid(GenericEvent genericEvent) {
		return (isNotNull(genericEvent) && genericEvent.getId() != null);
	}
	
	public static boolean isGenericCreateType(GenericEvent genericEvent) {
		return (isValid(genericEvent) && GenericEventTypeEnum.CREATE.toString().contentEquals(genericEvent.getType()));
	}
	
	public static boolean isGenericUpdateType(GenericEvent genericEvent) {
		return (isValid(genericEvent) && GenericEventTypeEnum.UPDATE.toString().contentEquals(genericEvent.getType()));
	}
	
	public static boolean isGenericDeleteType(GenericEvent genericEvent) {
		return (isValid(genericEvent) && GenericEventTypeEnum.DELETE.toString().contentEquals(genericEvent.getType()));
	}
	
	
	public static boolean isDeletedLogical(GenericEvent genericEvent) {
		return (isValid(genericEvent) && (genericEvent.getDeletedDate()!=null));
	}
}
