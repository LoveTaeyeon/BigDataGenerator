package com.fjnu.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"rawtypes"})
public class SqlBulider {
	
	private List<String> fieldsNameList = new ArrayList<String>();			//存储表结构各个属性的排序

	/**
	 * 
	 * @param fieldsMap 属性以及属性类型Map
	 * @return 拼接好的生成表的String
	 */
	public String bulidCreateTableSql(HashMap<String,Class> fieldsMap){
		if(fieldsNameList.size() != 0){
			fieldsNameList.clear();
		}
		StringBuilder sql = new StringBuilder();
		Set keySet = fieldsMap.keySet();
		for(Iterator i = keySet.iterator();i.hasNext();){
			String key = (String)i.next();
			fieldsNameList.add(key);
			sql.append(key + " " + getMySqlDataType(fieldsMap.get(key)) + " NOT NULL,\n");
		}
		return sql.toString();
	}
	
	/**
	 * 通过Java封装类的类型，转化为MySql中数据类型的String
	 * @param className 属性的类型封装类
	 * @return 对应MySql中的数据类型
	 */
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
	
	/**
	 * 
	 * @return
	 * @throws SecurityException 
	 * @throws Exception 
	 */
	public String buildInsertSql(Object data) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("(");
		Class dataClass = data.getClass();
		for(int i = 0;i < this.fieldsNameList.size();i ++){
			String fieldName = this.fieldsNameList.get(i);
			Field field = dataClass.getDeclaredField("$cglib_prop_" + fieldName);
			field.setAccessible(true);
			sql.append("'" + field.get(data) + "'");
			if(i != this.fieldsNameList.size() - 1){
				sql.append(",");
			}
		}
		sql.append(")");
		System.out.println("insertSql:" + sql.toString());
		return sql.toString();
	}
	
	/**
	 * 
	 * @return 属性的顺序拼接成的Sql
	 */
	public String getFieldOrderSql(){
		StringBuilder sql = new StringBuilder();
		sql.append("(");
		for(int i = 0;i < this.fieldsNameList.size();i ++){
			String fieldName = this.fieldsNameList.get(i);
			sql.append(fieldName);
			if(i != (this.fieldsNameList.size() - 1)){
				sql.append(",");
			}
		}
		sql.append(")");
		return sql.toString();
	}
	
	public static void main(String[] args) {
		HashMap<String,Class> fieldsMap = new HashMap<String,Class>();
		fieldsMap.put("name",String.class);
		fieldsMap.put("age",Integer.class);
		fieldsMap.put("avg",Double.class);
		fieldsMap.put("date",Date.class);
		fieldsMap.put("sex",Boolean.class);
		System.out.println(new SqlBulider().bulidCreateTableSql(fieldsMap));
	}
	
}
