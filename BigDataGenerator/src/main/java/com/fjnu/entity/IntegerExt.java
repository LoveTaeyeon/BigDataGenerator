package com.fjnu.entity;

/**
 *
 * Integer类型的JsonObject最终的转化类
 */
public class IntegerExt {

	private String integerFieldName;
	private String maxInteger;
	private String mixInteger;
	private String averageInteger;
	private String deviationInteger;

	public String getIntegerFieldName() {
		return integerFieldName;
	}

	public void setIntegerFieldName(String integerFieldName) {
		this.integerFieldName = integerFieldName;
	}

	public String getMaxInteger() {
		return maxInteger;
	}

	public void setMaxInteger(String maxInteger) {
		this.maxInteger = maxInteger;
	}

	public String getMixInteger() {
		return mixInteger;
	}

	public void setMixInteger(String mixInteger) {
		this.mixInteger = mixInteger;
	}

	public String getAverageInteger() {
		return averageInteger;
	}

	public void setAverageInteger(String averageInteger) {
		this.averageInteger = averageInteger;
	}

	public String getDeviationInteger() {
		return deviationInteger;
	}

	public void setDeviationInteger(String deviationInteger) {
		this.deviationInteger = deviationInteger;
	}

	@Override
	public String toString() {
		return "IntegerExt [integerFieldName=" + integerFieldName + ", maxInteger=" + maxInteger + ", mixInteger="
				+ mixInteger + ", averageInteger=" + averageInteger + ", deviationInteger=" + deviationInteger + "]";
	}

	public IntegerExt(String integerFieldName, String maxInteger, String mixInteger, String averageInteger,
			String deviationInteger) {
		super();
		this.integerFieldName = integerFieldName;
		this.maxInteger = maxInteger;
		this.mixInteger = mixInteger;
		this.averageInteger = averageInteger;
		this.deviationInteger = deviationInteger;
	}

	public IntegerExt() {
		super();
	}

}
