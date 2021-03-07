package com.acme.architecture.event.driven.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GenericEvent implements Serializable {
	
	private static final long serialVersionUID = 118822525129883857L;

	private String id;
	
	private String parentId;
	
	private String name;
	
	private String type; 
     
	private String author;
	
	private Long expirationSeconds;
	
	private String payload;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private Date deletedDate;

}

