package com.fjnu.utils;

import org.springframework.stereotype.Component;

@Component
public class KeyValueObject {

	private Object key;
	private Object value;
	
	public KeyValueObject() {
		super();
	}

	public KeyValueObject(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
