package com.fjnu.service;

import org.springframework.stereotype.Service;

import com.mifmif.common.regex.Generex;

@Service
public class StringGenerator {
	
	private String regex;

	public StringGenerator(String regex) {
		super();
		this.regex = regex;
	}
	
	/**
	 * 生成负责正则表达式的字符串
	 * @return 返回负责正则表达式的字符串
	 * @throws Exception 报错信息
	 */
	public String generatorString() throws Exception{
		if(!judgeLegitimacy()){
			throw new Exception("输入的正则表达式不负责规则！");
		}
		Generex generator = new Generex(this.regex);
		return generator.random();
	}
	
	/**
	 * 判断用户输入的正则表达式是否为合法的表达式
	 * @return true/false
	 */
	public boolean judgeLegitimacy(){
		return Generex.isValidPattern(this.regex);
	}
	
	/**
	 * 重置正则表达式的规则
	 * @param regex 正则表达式
	 * @throws Exception 报错信息
	 */
	public void reSet(String regex) throws Exception{
		if(regex.length() == 0){
			throw new Exception("用户输入的为空字符串！");
		}else{
			this.regex = regex;
		}
	}

	public static void main(String[] args) {
		//[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}  手机号
		//[a-zA-Z]\\w{5,17} 用户名
		//[a-zA-Z0-9]{6,16} 密码
		//([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,} 邮箱
		//[\u4e00-\u9fa5],{0,} 汉字
		//(^\\d{18}$)|(^\\d{15}$) 身份证
		Generex generator = new Generex("[a-zA-Z]\\w{5,17}");
		long begin = System.currentTimeMillis();
		for(int i = 0;i < 1000000;i ++){
			System.out.println(generator.random());
		}
		long end = System.currentTimeMillis();
		System.out.println("运行时间：" + (end - begin) + "毫秒");//应该是end - start
	}
	
}
