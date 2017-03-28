package com.fjnu.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 多线程下Spring注入Bean的时候，会出现NullPointException的错误<p>
 * 该方法实时从ApplicationContext中获取Bean，可以解决多线程问题
 */
public class SpringApplicationContextHolder implements ApplicationContextAware{

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringApplicationContextHolder.context = context;
	}
	
	 public static Object getSpringBean(String beanName) {
        return context==null?null:context.getBean(beanName);
    }

    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }

}
