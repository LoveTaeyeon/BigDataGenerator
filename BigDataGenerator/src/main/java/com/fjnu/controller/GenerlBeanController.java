package com.fjnu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fjnu.entity.DoubleExt;
import com.fjnu.entity.EnumExt;
import com.fjnu.entity.IntegerExt;
import com.fjnu.entity.StringExt;
import com.fjnu.service.GenerlBeanGenerator;
import com.fjnu.service.GenerlBeanService;
import com.fjnu.utils.DataMosaicThread;
import com.fjnu.utils.KeyValueObject;
import com.fjnu.utils.SqlBulider;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/generlBean")
public class GenerlBeanController {

	@Autowired
	private GenerlBeanService generlBeanService;
	@Autowired
	private SqlBulider sqlBulider;
	
	@SuppressWarnings({"static-access","rawtypes"})
	@RequestMapping("/create")
	public @ResponseBody KeyValueObject crateTable(HttpServletRequest request){
		String tableName = request.getParameter("tableName");
		String dataNumber = request.getParameter("dataNumber");
		String processNumber = request.getParameter("processNumber");
		JSONArray enumList = JSONArray.fromObject(request.getParameter("enumList"));
		JSONArray integerList = JSONArray.fromObject(request.getParameter("integerList"));
		JSONArray doubleList = JSONArray.fromObject(request.getParameter("doubleList"));
		JSONArray stringList = JSONArray.fromObject(request.getParameter("stringList"));
		//属性列表Map
		HashMap<String,Class> fieldsMap = new HashMap<String,Class>();
		List<Object> dataRules = new ArrayList<Object>();
		GenerlBeanGenerator beanGenerator = new GenerlBeanGenerator();
		//先对表结构进行构造
		for(int i = 0;i < enumList.size();i ++){
			EnumExt temp = (EnumExt)enumList.getJSONObject(i).toBean(enumList.getJSONObject(i),EnumExt.class);
			dataRules.add(temp);
			fieldsMap.put(temp.getEnumFieldName(),String.class);
		}
		for(int i = 0;i < integerList.size();i ++){
			IntegerExt temp = (IntegerExt)integerList.getJSONObject(i).toBean(integerList.getJSONObject(i),IntegerExt.class);
			dataRules.add(temp);
			fieldsMap.put(temp.getIntegerFieldName(),Integer.class);
		}
		for(int i = 0;i < doubleList.size();i ++){
			DoubleExt temp = (DoubleExt)doubleList.getJSONObject(i).toBean(doubleList.getJSONObject(i),DoubleExt.class);
			dataRules.add(temp);
			fieldsMap.put(temp.getDoubleFieldName(),Double.class);
		}
		for(int i = 0;i < stringList.size();i ++){
			StringExt temp = (StringExt)stringList.getJSONObject(i).toBean(stringList.getJSONObject(i),StringExt.class);
			dataRules.add(temp);
			fieldsMap.put(temp.getStringFieldName(),String.class);
		}
		//表结构构造完成,名字和类型已经保存进fieldsMap
		beanGenerator.setFieldsMap(fieldsMap);
		String sql = sqlBulider.bulidCreateTableSql(beanGenerator.getFieldsMap());
		System.out.println("create table sql :" + sql);
		generlBeanService.createTable(tableName,sql);
		//建表结束,开始生成insert语句
		try {
			for(int i = 0;i < Integer.parseInt(processNumber);i ++){
				DataMosaicThread thread1 = new DataMosaicThread(dataRules,Integer.parseInt(dataNumber)/Integer.parseInt(processNumber),fieldsMap,sqlBulider,tableName,generlBeanService);
				Thread t = new Thread(thread1);
				t.start();
			}
			return new KeyValueObject("info","success");
		} catch (Exception e) {
			e.printStackTrace();
			return new KeyValueObject("info","faild");
		}
	}
	
	@RequestMapping("/dataNumber")
	public @ResponseBody KeyValueObject getDataNumber(HttpServletRequest request){
		String tableName = request.getParameter("tableName");
		int result = generlBeanService.getDataNumber(tableName);
		System.out.println(result);
		return new KeyValueObject(tableName,result);
	}
	
}
