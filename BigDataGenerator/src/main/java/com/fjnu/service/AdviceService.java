package com.fjnu.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fjnu.entity.Advice;
import com.fjnu.mapper.AdviceMapper;

@Service
public class AdviceService {

	@Autowired
	private AdviceMapper adviceMapper;
	
	public void insertAdvice(String advice){
		//'0000-00-00 00:00:00'
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Advice info = new Advice(advice,format.parse(format.format(new java.util.Date())));
			System.out.println(info.toString());
			adviceMapper.insert(info);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
