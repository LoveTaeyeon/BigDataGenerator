package com.fjnu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fjnu.service.AdviceService;
import com.fjnu.utils.KeyValueObject;

@Controller
@RequestMapping("/advice")
public class AdviceController {
	
	@Autowired
	private AdviceService adviceService;

	@RequestMapping(value = "/saveAdvice",method = RequestMethod.POST)
	public @ResponseBody KeyValueObject saveAdvice(HttpServletRequest request){
		String advice = request.getParameter("advice");
		try {
			adviceService.insertAdvice(advice);
			return new KeyValueObject("info","success");
		} catch (Exception e) {
			return new KeyValueObject("info","faild");
		}
	}
	
}
