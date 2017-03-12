package com.fjnu.entity;



import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Advice {

	private String advice;
	private Date date;

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Advice(String advice, Date date) {
		super();
		this.advice = advice;
		this.date = date;
	}

	public Advice() {
		super();
	}

	@Override
	public String toString() {
		return "Advice [advice=" + advice + ", date=" + date.toString() + "]";
	}

}
