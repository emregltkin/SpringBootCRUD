package com.crud.exceptions;

public class CustomNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String name;
	private String fieldName;
	private Object fieldValue;

	public CustomNotFoundException(String name, String fieldName, Object fieldValue) {
		super(String.format("%s: '%s' ile %s bulunamadı.", fieldName, fieldValue, name));
		this.name = name;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getName() {
		return name;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

}
