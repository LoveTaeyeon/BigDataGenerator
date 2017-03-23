package com.fjnu.entity;

/**
 * 
 * Double类型的JSONObject最终的转化类
 */
public class DoubleExt {

	private String doubleFieldName;
	private String maxDouble;
	private String mixDouble;
	private String averageDouble;
	private String deviationDouble;

	public String getDoubleFieldName() {
		return doubleFieldName;
	}

	public void setDoubleFieldName(String doubleFieldName) {
		this.doubleFieldName = doubleFieldName;
	}

	public String getMaxDouble() {
		return maxDouble;
	}

	public void setMaxDouble(String maxDouble) {
		this.maxDouble = maxDouble;
	}

	public String getMixDouble() {
		return mixDouble;
	}

	public void setMixDouble(String mixDouble) {
		this.mixDouble = mixDouble;
	}

	public String getAverageDouble() {
		return averageDouble;
	}

	public void setAverageDouble(String averageDouble) {
		this.averageDouble = averageDouble;
	}

	public String getDeviationDouble() {
		return deviationDouble;
	}

	public void setDeviationDouble(String deviationDouble) {
		this.deviationDouble = deviationDouble;
	}

	@Override
	public String toString() {
		return "DoubleExt [doubleFieldName=" + doubleFieldName + ", maxDouble=" + maxDouble + ", mixDouble=" + mixDouble
				+ ", averageDouble=" + averageDouble + ", deviationDouble=" + deviationDouble + "]";
	}

	public DoubleExt(String doubleFieldName, String maxDouble, String mixDouble, String averageDouble,
			String deviationDouble) {
		super();
		this.doubleFieldName = doubleFieldName;
		this.maxDouble = maxDouble;
		this.mixDouble = mixDouble;
		this.averageDouble = averageDouble;
		this.deviationDouble = deviationDouble;
	}

	public DoubleExt() {
		super();
	}

}
