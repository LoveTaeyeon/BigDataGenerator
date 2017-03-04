package com.fjnu.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class GeneratorInteger {
	
	private Integer max;			//最大值最小值	
	private Integer mix;
	private Double average;			//正态分布的最均值
	private Double deviation;		//正态分布的标准差
	
	public GeneratorInteger(int max,int mix,Double average,Double deviation){
		this.max = max;
		this.mix = mix;
		this.average = average;
		this.deviation = deviation;
	}

	/**
	 * Box Muller算法，按照算法公式生成随机的正态分布的值
	 * @return 返回生成的符合正态分布的值
	 * @throws Exception 
	 */
	public Integer generatorInteger() throws Exception{
		
		if(!judgeLegitimacy()){
			throw new Exception("您输入的最小值大于最大值！");
		}
		Random random = new Random();
		double u1 = random.nextDouble();
		double u2 = random.nextDouble();
		double result = this.average + Math.pow(this.deviation,2)*Math.sqrt(-2.0*(Math.log(u1)/Math.log(Math.E))) * Math.cos(2*Math.PI*u2);
		System.out.println("生成了：" + result);
		//如果生成的结果不满足用户输入的值域，则重新生成
		if(result < this.mix || result > this.max){
			return generatorInteger();
		}
		return (int) result;
	}
	
	/**
	 * 重置生成器的正态分布的参数
	 * @param average 正态分布的最高点（平均数）
	 * @param deviation 正太分布六个间断的间隔（标准差）
	 * @throws Exception 
	 */
	public void reSet(int max,int mix,Double average,Double deviation) throws Exception{
		if(!judgeLegitimacy(max, mix)){
			throw new Exception("您输入的最小值大于最大值！");
		}
		this.max = max;
		this.mix = mix;
		this.average = average;
		this.deviation = deviation;
	}
	
	/**
	 * 判断当前的最大值和最小值是否合法
	 * @return 返回合法性判断的结果
	 */
	public boolean judgeLegitimacy(){
		if(this.mix > this.max){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 判断当前的最大值和最小值是否合法
	 * @param max 最大值
	 * @param mix 最小值
	 * @return 返回合法性判断的结果
	 */
	public boolean judgeLegitimacy(int max,int mix){
		if(mix > max){
			return false;
		}else{
			return true;
		}
	}
	
	public static void main(String[] args) {
	
		try {
			GeneratorInteger generator = new GeneratorInteger(10000,0,20.0,2.0);
			System.out.println(generator.generatorInteger());
			generator.reSet(21,19,20.0,1.5);
			System.out.println(generator.generatorInteger());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
}
