package com.test.spring.configuration;

import org.springframework.context.annotation.*;

import com.test.spring.beans.Counter;

@Configuration
public class SimpleSpringConfiguration {
	
	@Bean()
	@Scope(value="session")
	public Counter methodGetCounter(){
		return new Counter();
	}
}
