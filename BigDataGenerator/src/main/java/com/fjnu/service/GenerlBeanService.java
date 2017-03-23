package com.fjnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fjnu.mapper.GenerlMapper;

@Service
public class GenerlBeanService {

	@Autowired
	private GenerlMapper generlMapper;
	
	public void createTable(String tableName,String sql){
		generlMapper.isExists(tableName);
		generlMapper.createTable(tableName,sql);
	}
	
	public void insertTable(String sql){
		generlMapper.insert("");
	}
	
	
}