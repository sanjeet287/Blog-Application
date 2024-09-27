package com.blog_app.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	private String username;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public ResourceNotFoundException(String resourceName, String fieldName,String username) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,username));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.username = username;
	}
	
	public ResourceNotFoundException(String resourceName) {
		super(String.format("%s not found with this URL ",resourceName));
		this.resourceName=resourceName;
	}
	
	

}
