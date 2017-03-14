package com.fjnu.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class SqlBulider {

	/**
	 * 
	 * @param fieldsMap 属性以及属性类型Map
	 * @return 拼接好的生成表的String
	 */
	@SuppressWarnings("rawtypes")
	public String bulidCreateTableSql(HashMap<String,Class> fieldsMap){
		
		StringBuilder sql = new StringBuilder();
		Set keySet = fieldsMap.keySet();
		for(Iterator i = keySet.iterator();i.hasNext();){
			String key = (String)i.next();
			sql.append(key + " " + getMySqlDataType(fieldsMap.get(key)) + " NOT NULL,\n");
		}
		return sql.toString();
	}
	
	/**
	 * 通过Java封装类的类型，转化为MySql中数据类型的String
	 * @param className 属性的类型封装类
	 * @return 对应MySql中的数据类型
	 */
	@SuppressWarnings("rawtypes")
	public String getMySqlDataType(Class className){
		
		switch(className.getSimpleName()){
			case "String" : return "varchar(255)";
			case "Integer" : return "mediumint(9)";
			case "Boolean" : return "bit(1)";
			case "Double" : return "double";
			case "Date" : return "datetime";
		}
		return "";
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("rawtypes")
		HashMap<String,Class> fieldsMap = new HashMap<String,Class>();
		fieldsMap.put("name",String.class);
		fieldsMap.put("age",Integer.class);
		fieldsMap.put("avg",Double.class);
		fieldsMap.put("date",Date.class);
		fieldsMap.put("sex",Boolean.class);
		System.out.println(new SqlBulider().bulidCreateTableSql(fieldsMap));
	}
	
}
