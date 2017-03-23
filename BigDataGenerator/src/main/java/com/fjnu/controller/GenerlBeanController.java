package com.fjnu.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fjnu.entity.DoubleExt;
import com.fjnu.entity.EnumExt;
import com.fjnu.entity.IntegerExt;
import com.fjnu.entity.StringExt;
import com.fjnu.service.GenerlBeanGenerator;
import com.fjnu.service.GenerlBeanService;
import com.fjnu.utils.SqlBulider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/generlBean")
public class GenerlBeanController {

	@Autowired
	private GenerlBeanService generlBeanService;
	@Autowired
	private SqlBulider sqlBulider;
	
	@SuppressWarnings({"static-access","rawtypes"})
	@RequestMapping("create")
	public void crateTable(HttpServletRequest request){
		String tableName = request.getParameter("tableName");
		String dataNumber = request.getParameter("dataNumber");
		JSONArray enumList = JSONArray.fromObject(request.getParameter("enumList"));
		JSONArray integerList = JSONArray.fromObject(request.getParameter("integerList"));
		JSONArray doubleList = JSONArray.fromObject(request.getParameter("doubleList"));
		JSONArray stringList = JSONArray.fromObject(request.getParameter("stringList"));
		HashMap<String,Class> fieldsMap = new HashMap<String,Class>();
		//先对表结构进行构造
		for(int i = 0;i < enumList.size();i ++){
			EnumExt temp = (EnumExt)enumList.getJSONObject(i).toBean(enumList.getJSONObject(i),EnumExt.class);
			System.out.println("enum:" + temp.getEnumFieldName());
			fieldsMap.put(temp.getEnumFieldName(),String.class);
		}
		for(int i = 0;i < integerList.size();i ++){
			IntegerExt temp = (IntegerExt)integerList.getJSONObject(i).toBean(integerList.getJSONObject(i),IntegerExt.class);
			System.out.println("integer:" + temp.getIntegerFieldName());
			fieldsMap.put(temp.getIntegerFieldName(),Integer.class);
		}
		for(int i = 0;i < doubleList.size();i ++){
			DoubleExt temp = (DoubleExt)doubleList.getJSONObject(i).toBean(doubleList.getJSONObject(i),DoubleExt.class);
			System.out.println("double:" + temp.getDoubleFieldName());
			fieldsMap.put(temp.getDoubleFieldName(),Double.class);
		}
		for(int i = 0;i < stringList.size();i ++){
			StringExt temp = (StringExt)stringList.getJSONObject(i).toBean(stringList.getJSONObject(i),StringExt.class);
			System.out.println("string:" + temp.getStringFieldName());
			fieldsMap.put(temp.getStringFieldName(),String.class);
		}
		//表结构构造完成,名字和类型已经保存进fieldsMap
		GenerlBeanGenerator beanGenerator = new GenerlBeanGenerator(fieldsMap);
		String sql = sqlBulider.bulidCreateTableSql(beanGenerator.getFieldsMap());
		System.out.println(sql);
		generlBeanService.createTable(tableName,sql);
	}
	
}
