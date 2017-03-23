package com.fjnu.entity;

/**
 * 
 * String类型的JSONObject最终的转化类
 */
public class StringExt {

	private String stringFieldName;
	private String regex;

	public String getStringFieldName() {
		return stringFieldName;
	}

	public void setStringFieldName(String stringFieldName) {
		this.stringFieldName = stringFieldName;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	@Override
	public String toString() {
		return "StringExt [stringFieldName=" + stringFieldName + ", regex=" + regex + "]";
	}

	public StringExt(String stringFieldName, String regex) {
		super();
		this.stringFieldName = stringFieldName;
		this.regex = regex;
	}

	public StringExt() {
		super();
	}

}
