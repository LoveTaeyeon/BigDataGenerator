package com.fjnu.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

@Service
public class GenerlBeanGenerator {
	
	private BeanGenerator generator = null;
	private BeanMap beanMap = null;
	@SuppressWarnings("rawtypes")
	private HashMap<String,Class> fieldsMap = null;
	private Object object = null;
	
	public GenerlBeanGenerator(){}
	
	@SuppressWarnings("rawtypes")
	public GenerlBeanGenerator(HashMap<String,Class> fieldsMap){
		this.generator = new BeanGenerator();
		this.fieldsMap = fieldsMap;
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap<String,Class> getFieldsMap() {
		return fieldsMap;
	}

	@SuppressWarnings("rawtypes")
	public void setFieldsMap(HashMap<String,Class>  fieldsMap) {
		this.fieldsMap = fieldsMap;
	}

	/**
	 * 为创建的类设置值
	 * @param key 参数名称
	 * @param value 参数值
	 */
	public void setValue(String key,Object value){
		beanMap.put(key, value);
	}
	
	/**
	 * 为创建的类设置值
	 * @param key 参数名称
	 * @param value 参数值
	 */
	@SuppressWarnings("rawtypes")
	public void setValue(HashMap<String,Object> valueMap){
		
		Iterator iter = valueMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			this.beanMap.put(key, val);
		}
	
	}
	
	/**
	 * 获得指定参数的值
	 * @param key 参数名
	 * @return 参数值
	 */
	public Object getValue(String key){
		return beanMap.get(key);
	}
	
	/**
	 * 为Bean装配属性
	 * @throws Exception 报错信息
	 */
	@SuppressWarnings("rawtypes")
	public void setBeanDefination() throws Exception{
		if(!judgeLegitimacy()){
			throw new Exception("没有输入属性！");
		}
		Set keySet = this.fieldsMap.keySet();
		//遍历用户的输入，为Object添加属性
		for(Iterator i = keySet.iterator();i.hasNext();){
			String key = (String)i.next();
			//通过generator为Object添加用户输入的属性
			this.generator.addProperty(key,fieldsMap.get(key));
		}
		//把bean构造为制定的bean
		this.object = generator.create();
		//形成object和beanMap的映射，以便beanMap对object的值进行管理
		this.beanMap = BeanMap.create(this.object);
	}
	
	/**
	 * 判断信息的合法性以便生成Bean
	 * @return 合法性判断
	 */
	public boolean judgeLegitimacy(){
		if(this.fieldsMap.isEmpty() || this.generator == null){
			return false;
		}
		return true;
	}
	
	/**
	 * 返回生成的Bean
	 * @return 返回生成的bean（带数据）
	 */
	public Object generatorBean() throws Exception{
		Object obj = generator.create();
		//使用BeanCopier对生成的类进行深拷贝，比普通的Spring的Beantils速度快（Beantils大概1000ms，BeanCopier只要600ms）
		BeanCopier copier = BeanCopier.create(this.object.getClass(),obj.getClass(),false);
		copier.copy(this.object,obj,null);
		return obj;
	}

	public static void main(String[] args) {
		
		@SuppressWarnings("rawtypes")
		HashMap<String,Class> fieldsMap = new HashMap<String,Class>();
		fieldsMap.put("age",Integer.class);
		fieldsMap.put("name",String.class);
		fieldsMap.put("avg",Double.class);
		GenerlBeanGenerator beanGenerator = new GenerlBeanGenerator(fieldsMap);
		try {
			beanGenerator.setBeanDefination();
			beanGenerator.setValue("age",123);
			beanGenerator.setValue("name","Han");
			beanGenerator.setValue("avg",12.3);
			HashMap<String,Object> valueMap = new HashMap<String, Object>();
			valueMap.put("age",456);
			valueMap.put("name","Shuo");
			valueMap.put("avg",1.1);
			Object obj = beanGenerator.generatorBean();
			Method getName = obj.getClass().getMethod("getName");
			Method getAge = obj.getClass().getMethod("getAge");
			Method getAvg = obj.getClass().getMethod("getAvg");
			System.out.println("name:" + getName.invoke(obj));
			System.out.println("age:" + getAge.invoke(obj));
			System.out.println("avg:" + getAvg.invoke(obj));
			//重新赋值
			beanGenerator.setValue(valueMap);
			Object obj1 = beanGenerator.generatorBean();
			Method getName1 = obj.getClass().getMethod("getName");
			Method getAge1 = obj.getClass().getMethod("getAge");
			Method getAvg1 = obj.getClass().getMethod("getAvg");
			System.out.println("name:" + getName1.invoke(obj1));
			System.out.println("age:" + getAge1.invoke(obj1));
			System.out.println("avg:" + getAvg1.invoke(obj1));
			System.out.println("name:" + getName.invoke(obj));
			System.out.println("age:" + getAge.invoke(obj));
			System.out.println("avg:" + getAvg.invoke(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
