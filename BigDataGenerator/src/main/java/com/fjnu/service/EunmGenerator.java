package com.fjnu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fjnu.utils.KeyValueObject;

@Service
public class EunmGenerator {
	
	private List<KeyValueObject> keyValueList;
	private String value;
	private String probability;
	
	/**
	 * 
	 * @param value 用户输入的枚举值[A,B,C]
	 * @param probability 用户输入的枚举值的概率[0.3,0.3,0.4]
	 */
	public EunmGenerator(String value,String probability){
		this.value = value;
		this.probability = probability;
		this.keyValueList = new ArrayList<KeyValueObject>();
	}

	public static void main(String[] args) {
		
		EunmGenerator generator = new EunmGenerator("A,B,C,D,E","0.05,0.15,0.2,0.25,0.35");
		try {
			String str = generator.generatorEunm();
			String s = generator.generatorEunm();
			generator.reSet("~,!,@,$","0.1,0.2,0.3,0.4");
			String a = generator.generatorEunm();
			System.out.println(str);
			System.out.println(s);
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * 根据用户的输入生成相应的枚举类型
	 * <p>
	 * 采用动态规划算法，动态生成相应的随机数据段。
	 * <p>
	 * 与随机生成的Double对比之后，返回具体的值
	 * @return 随机生成的String类型的枚举类型
	 * @throws Exception
	 *             包含报错的信息
	 */
	public String generatorEunm() throws Exception {

		// 获取相应的HashMap
		HashMap<String, Double> map = translateToMap(this.value,this.probability);
		List<KeyValueObject> enumList = getKeyValueList(map);
		for(int i = 0;i < enumList.size();i ++){
			KeyValueObject temp = enumList.get(i);
			System.out.println("key:" + temp.getKey() + " value:" + temp.getValue());
		}
		//随机数生成器
		double randomDouble = new Random().nextDouble();
		System.out.println("随机数为：" + randomDouble);
		String result = "";
		//根据顺利来判断已经被构造的List
		for(int i = 0;i < enumList.size();i ++){
			//初始状态应该在0~enumList.get(0)中
			if(i == 0){
				if(0 < randomDouble && randomDouble < (Double)enumList.get(0).getValue()){
					result = (String)enumList.get(0).getKey();
				}
				continue;
			}
			//随机生成的值在随机段中间
			if(Double.compare((Double)enumList.get(i-1).getValue(),randomDouble) <= 0 && Double.compare((Double)enumList.get(i).getValue(),randomDouble) > 0){
				System.out.println(enumList.get(i-1).getValue() + " < "  + randomDouble + " < " + enumList.get(i).getValue());
				result =  (String)enumList.get(i).getKey();
				break;
			}
		}
		return result;
	}
	
	/**
	 * 把Key/Value形式的map转换为带有随机段的有序的List
	 * @param map 被构造好的Map
	 * @return 返回随机段List
	 */
	public List<KeyValueObject> getKeyValueList(HashMap<String,Double> map){
		if(!this.keyValueList.isEmpty()){
			System.out.println("keyValueList不为空");
			return this.keyValueList;
		}
		@SuppressWarnings("rawtypes")
		Iterator iter = map.entrySet().iterator();
		//被构造的概率段
		List<KeyValueObject> enumList = new ArrayList<KeyValueObject>();
		double tempPro = 0;
		//遍历并构造List
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry)iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			tempPro += (Double)val;
			enumList.add(new KeyValueObject(key,tempPro));
		}
		//构造过后把这个生成的List保存起来，直至用户销毁它
		this.keyValueList = enumList;
		return enumList;
	}

	/**
	 * 把用户输入的字符串转化为需要使用的hashMap
	 * 
	 * @param value
	 *            用户输入的枚举值[A,B,C]
	 * @param probability
	 *            用户输入的枚举值的概率[0.3,0.3,0.4]
	 * @return 返回构造生成的Key/Value形式的HashMap
	 * @throws Exception
	 *             包含报错的信息
	 */
	public HashMap<String, Double> translateToMap(String value, String probability) throws Exception {

		// 判断输入的合法性，不合法的话向上级返回报错信息
		try {
			judgeLegitimacy(value, probability);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		String[] values = value.split(",");
		String[] probabilitieString = probability.split(",");
		HashMap<String, Double> eunmMap = new HashMap<String, Double>();
		// 构造hashMap
		for (int i = 0; i < probabilitieString.length; i++) {
			eunmMap.put(values[i], Double.valueOf(probabilitieString[i]));
		}
		return eunmMap;
	}

	/**
	 * 判断用户输入的合法性
	 * 
	 * @param value
	 *            用户输入的枚举值[A,B,C]
	 * @param probability
	 *            用户输入的枚举值的概率[0.3,0.3,0.4]
	 * @return 返回判断的结果，true为通过，false为不通过
	 * @throws Exception
	 *             包含报错的信息
	 */
	public boolean judgeLegitimacy(String value, String probability) throws Exception {
		boolean result = true;
		String[] values = value.split(",");
		String[] probabilitieString = probability.split(",");
		if (value.length() == 0 || probability.length() == 0) {
			result = false;
			throw new Exception("请输入枚举类型信息！");
		}
		// 个数判断，防止用户错误输入，输入错误则抛出到上级
		if (values.length != probabilitieString.length) {
			result = false;
			throw new Exception("枚举类型与概率的个数不一致！");
		}
		Double probabilities[] = new Double[probabilitieString.length];
		double sum = 0;
		for (int i = 0; i < probabilitieString.length; i++) {
			probabilities[i] = Double.valueOf(probabilitieString[i]);
			sum += probabilities[i];
		}
		// 概率输入判断，判断用户输入的概率之和是否为1
		if (sum != 1) {
			result = false;
			throw new Exception("您输入的概率之和不为1！");
		}
		return result;
	}
	
	/**
	 * 重置枚举类型随机生成器
	 * @param value 用户输入的枚举值[A,B,C]
	 * @param probability 用户输入的枚举值的概率[0.3,0.3,0.4]
	 * @throws Exception
	 */
	public void reSet(String value, String probability) throws Exception{
		boolean result;
		try {
			result = judgeLegitimacy(value, probability);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if(result){
			this.value = value;
			this.probability = probability;
			this.keyValueList.clear();
		}
	}

}
