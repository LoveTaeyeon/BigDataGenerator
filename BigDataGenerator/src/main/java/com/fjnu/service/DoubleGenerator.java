package com.fjnu.service;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class DoubleGenerator {

	private Double max;			//最大值最小值	
	private Double mix;
	private Double average;			//正态分布的最均值
	private Double deviation;		//正态分布的标准差
	
	public DoubleGenerator(){}
	
	public DoubleGenerator(Double max,Double mix,Double average,Double deviation){
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
	public Double generatorDouble() throws Exception{
		
		if(!judgeLegitimacy()){
			throw new Exception("您输入的最小值大于最大值！");
		}
		Random random = new Random();
		double u1 = random.nextDouble();
		double u2 = random.nextDouble();
		double result = this.average + Math.pow(this.deviation,2)*Math.sqrt(-2.0*(Math.log(u1)/Math.log(Math.E))) * Math.cos(2*Math.PI*u2);
		//如果生成的结果不满足用户输入的值域，则重新生成
		if(result < this.mix || result > this.max){
			return generatorDouble();
		}
		//使用BigDecimal 四舍五入保留两位小数
		BigDecimal decimal = new BigDecimal(result);
		return decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 重置生成器的正态分布的参数
	 * @param average 正态分布的最高点（平均数）
	 * @param deviation 正太分布六个间断的间隔（标准差）
	 * @throws Exception 
	 */
	public void reSet(Double max,Double mix,Double average,Double deviation) throws Exception{
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
	public boolean judgeLegitimacy(Double max,Double mix){
		if(mix > max){
			return false;
		}else{
			return true;
		}
	}
	
}
