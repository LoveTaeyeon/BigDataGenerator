package com.fjnu.entity;
/**
 * 
 * Enum类型的JSONObject最终的转化类
 */
public class EnumExt {

	private String enumFieldName;
	private String enumValue;
	private String enumProbability;

	public String getEnumFieldName() {
		return enumFieldName;
	}

	public void setEnumFieldName(String enumFieldName) {
		this.enumFieldName = enumFieldName;
	}

	public String getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	}

	public String getEnumProbability() {
		return enumProbability;
	}

	public void setEnumProbability(String enumProbability) {
		this.enumProbability = enumProbability;
	}

	@Override
	public String toString() {
		return "EnumExt [enumFieldName=" + enumFieldName + ", enumValue=" + enumValue + ", enumProbability="
				+ enumProbability + "]";
	}

	public EnumExt(String enumFieldName, String enumValue, String enumProbability) {
		super();
		this.enumFieldName = enumFieldName;
		this.enumValue = enumValue;
		this.enumProbability = enumProbability;
	}

	public EnumExt() {
		super();
	}

}
