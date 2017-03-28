package com.fjnu.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fjnu.service.DoubleGenerator;
import com.fjnu.service.EunmGenerator;
import com.fjnu.service.GeneratorInteger;
import com.fjnu.service.GenerlBeanGenerator;
import com.fjnu.service.GenerlBeanService;
import com.fjnu.service.StringGenerator;

/**
 * 数据生成以及sql拼接线程
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class DataMosaicThread implements Runnable {

	private GenerlBeanService generlBeanService;
	//sql生成器
	private SqlBulider sqlBulider;
	//数据生成器，generator在reset之前使用同一固定规则，可以避免new太多的类
	private HashMap<String,Object> dataGenerator = new HashMap<String,Object>();
	//生成Bean的封装类
	private GenerlBeanGenerator beanGenerator;
	//需要数据的条数
	private int dataNumber;
	//要插入的表的表名
	private String tableName;
	//拼接的sql的结果
	private StringBuilder resultSql = new StringBuilder();
	
	public DataMosaicThread(List<Object> dataRules,int dataNumber,HashMap<String,Class> fieldsMap,SqlBulider sqlBulider,String tableName,GenerlBeanService generlBeanService) throws Exception{
		this.dataNumber = dataNumber;
		this.beanGenerator = new GenerlBeanGenerator(fieldsMap);
		this.sqlBulider = sqlBulider;
		this.tableName = tableName;
		this.generlBeanService = generlBeanService;
		//先对为该类生成不同种类的数据生成器
		for(Object obj : dataRules){
			Class ext = obj.getClass();
			switch(ext.getSimpleName()){
				case "EnumExt":
					String enumFieldName = (String)ext.getDeclaredMethod("getEnumFieldName").invoke(obj);
					Method getValue = ext.getDeclaredMethod("getEnumValue");
					Method getProbability = ext.getDeclaredMethod("getEnumProbability");
					//为线程类添加固有随机数生成器
					dataGenerator.put(enumFieldName,new EunmGenerator((String)getValue.invoke(obj),(String)getProbability.invoke(obj)));
					break;
				case "DoubleExt":
					String doubleFieldName = (String)ext.getDeclaredMethod("getDoubleFieldName").invoke(obj);
					double maxDouble = Double.parseDouble((String)ext.getDeclaredMethod("getMaxDouble").invoke(obj));
					double mixDouble = Double.parseDouble((String)ext.getDeclaredMethod("getMixDouble").invoke(obj));
					double averageDouble = Double.parseDouble((String)ext.getDeclaredMethod("getAverageDouble").invoke(obj));
					double deviationDouble = Double.parseDouble((String)ext.getDeclaredMethod("getDeviationDouble").invoke(obj));
					dataGenerator.put(doubleFieldName,new DoubleGenerator(maxDouble,mixDouble,averageDouble,deviationDouble));
					break;
				case "IntegerExt":
					String integerFieldName = (String)ext.getDeclaredMethod("getIntegerFieldName").invoke(obj);
					int max = Integer.parseInt((String)ext.getDeclaredMethod("getMaxInteger").invoke(obj));
					int mix = Integer.parseInt((String)ext.getDeclaredMethod("getMixInteger").invoke(obj));
					double average = Double.parseDouble((String)ext.getDeclaredMethod("getAverageInteger").invoke(obj));
					double deviation = Double.parseDouble((String)ext.getDeclaredMethod("getDeviationInteger").invoke(obj));
					dataGenerator.put(integerFieldName,new GeneratorInteger(max,mix,average,deviation));
					break;
				case "StringExt":
					String stringFieldName = (String)ext.getDeclaredMethod("getStringFieldName").invoke(obj);
					dataGenerator.put(stringFieldName,new StringGenerator((String)ext.getDeclaredMethod("getRegex").invoke(obj)));
					break;
			}
		}
		this.beanGenerator.setBeanDefination();
	}
	/**
	 * 生成相应存储好数据的动态类（cglib生成的类）
	 * @param dataRules List集合，存储各种Ext类
	 * @return 存储好数据的生成类
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Object generatorData() throws Exception{
		Set keySet = this.dataGenerator.keySet();
		String methodName = "";
		for (Iterator i = keySet.iterator();i.hasNext();) {
			String key = (String)i.next();
			Object val = this.dataGenerator.get(key);
			switch(val.getClass().getSimpleName()){
				case "EunmGenerator":
					methodName = "generatorEunm";
					break;
				case "GeneratorInteger":
					methodName = "generatorInteger";
					break;
				case "DoubleGenerator":
					methodName = "generatorDouble";
					break;
				case "StringGenerator":
					methodName = "generatorString";
					break;
			}
			Object value = val.getClass().getDeclaredMethod(methodName).invoke(val);
			System.out.println("name:" + key.toString() + " value:" + value);
			this.beanGenerator.setValue(key.toString(),value);
		}
		System.out.println("one bean is create");
		return this.beanGenerator.generatorBean();
	}
	
	@Override
	public void run() {
		try {
			synchronized (resultSql) {
				for(int i = 0;i < dataNumber;i ++){
					this.resultSql.append(sqlBulider.buildInsertSql(generatorData()));
					if((i >= 10000) && (i == (dataNumber - 1) || (i % 10000 == 0))){
						System.out.println(resultSql);
						generlBeanService.insertTable(this.resultSql.toString(),this.tableName,sqlBulider.getFieldOrderSql());
						this.resultSql.delete(0,this.resultSql.length());
						continue;
					}
					if(i != (dataNumber - 1)){
						this.resultSql.append(",");
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
