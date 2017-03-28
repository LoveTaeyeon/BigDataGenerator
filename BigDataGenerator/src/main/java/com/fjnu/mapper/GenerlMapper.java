package com.fjnu.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerlMapper {

	//在数据库新建该表
	public abstract void createTable(@Param("tableName") String tableName,@Param("sql") String sql);
	//判断数据库是否存在该表，如果存在则删除
	public abstract void isExists(@Param("tableName") String tableName);
	//插入数据,一般为多行
	public abstract void insert(@Param("sql") String sql,@Param("tableName") String tableName,@Param("fieldOrder") String fieldOrder);
	
}
