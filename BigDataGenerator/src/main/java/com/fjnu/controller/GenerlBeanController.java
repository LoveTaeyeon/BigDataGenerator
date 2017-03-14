package com.fjnu.controller;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fjnu.service.GenerlBeanGenerator;
import com.fjnu.service.GenerlBeanService;
import com.fjnu.utils.SqlBulider;

@Controller
@RequestMapping("/generlBean")
public class GenerlBeanController {

	@Autowired
	private GenerlBeanService generlBeanService;
	@Autowired
	private SqlBulider sqlBulider;
	
	@RequestMapping("create/{tableName}")
	public void crateTable(@PathVariable("tableName") String tableName,HttpServletRequest request){
		HashMap<String,Class> fieldsMap = new HashMap<String,Class>();
		fieldsMap.put("sum",Integer.class);
		fieldsMap.put("avg",Double.class);
		fieldsMap.put("sex",Boolean.class);
		fieldsMap.put("name",String.class);
		fieldsMap.put("birthday",Date.class);
		GenerlBeanGenerator beanGenerator = new GenerlBeanGenerator(fieldsMap);
		String sql = sqlBulider.bulidCreateTableSql(beanGenerator.getFieldsMap());
		generlBeanService.createTable(tableName,sql);
	}
	
}
