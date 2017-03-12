package com.fjnu.mapper;

import org.springframework.stereotype.Repository;

import com.fjnu.entity.Advice;

@Repository
public interface AdviceMapper {

	public abstract void insert(Advice advice);
	
}
