package com.fjnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fjnu.service.AdviceService;

@Controller
@RequestMapping("/advice")
public class AdviceController {
	
	@Autowired
	private AdviceService adviceService;

	@RequestMapping("/saveAdvice/{advice}")
	public void saveAdvice(@PathVariable("advice") String advice){
		System.out.println(advice);
		adviceService.insertAdvice(advice);
	}
	
}
