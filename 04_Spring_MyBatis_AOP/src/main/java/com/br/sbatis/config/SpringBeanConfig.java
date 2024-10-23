package com.br.sbatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.br.sbatis.aop.AfterLoggingAdvice;
import com.br.sbatis.aop.AroundLoggingAdvice;
import com.br.sbatis.aop.BeforeLoggingAdvice;

@EnableAspectJAutoProxy
@Configuration
public class SpringBeanConfig {

	
	@Bean
	public BeforeLoggingAdvice beforeLoggingAdvice() {
		return new BeforeLoggingAdvice();
	}
	
	@Bean
	public AfterLoggingAdvice afterLoggingAdvice() {
		return new AfterLoggingAdvice();
	}
	
	@Bean
	public AroundLoggingAdvice aroundLoggingAdvice() {
		return new AroundLoggingAdvice();
	}

}
