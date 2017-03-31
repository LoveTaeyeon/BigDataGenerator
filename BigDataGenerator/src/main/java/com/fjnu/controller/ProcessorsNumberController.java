package com.fjnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fjnu.utils.KeyValueObject;

@Controller
@RequestMapping("/process")
public class ProcessorsNumberController {

	@RequestMapping("/getCoreNumber")
	public @ResponseBody KeyValueObject getProcessNumber(){
		return new KeyValueObject("CoreNumber",Runtime.getRuntime().availableProcessors());
	}
	
}
