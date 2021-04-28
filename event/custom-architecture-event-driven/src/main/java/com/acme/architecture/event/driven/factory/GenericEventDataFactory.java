package com.acme.architecture.event.driven.factory;
import java.util.Date;

import com.acme.architecture.event.driven.entity.GenericEvent;

public final class GenericEventDataFactory {
	
	protected GenericEventDataFactory() {
		throw new IllegalStateException("GenericEventDataFactory");
	}

	public static GenericEvent create(String id, String parentId, String name,  String type, String author, long expirationSeconds, String payload) {
		final GenericEvent genericEvent = new GenericEvent();
		genericEvent.setId(id);
		genericEvent.setParentId(parentId);
		genericEvent.setName(name);
		genericEvent.setType(type);
		genericEvent.setAuthor(author);
		genericEvent.setExpirationSeconds(expirationSeconds);
		genericEvent.setPayload(payload);
		genericEvent.setCreatedDate(new Date());
		genericEvent.setUpdatedDate(null);
		genericEvent.setDeletedDate(null);
		return genericEvent;
	}
	
	public static GenericEvent create(long id, long parentId,String name,  String type, String author, long expirationSeconds, String payload) {
		return create(String.valueOf(id), String.valueOf(parentId), name,   type,  author,  expirationSeconds,  payload);
	}
	
}
