package com.br.spring.ioc.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanConfig {
	

	// setter Injection 예시1
	@Bean
	public Music music1() {
		Music m = new Music();
		m.setTitle("Love wins all");
		m.setGenre("발라드");
		return m;
	}
	/* 
	[xml방식]
  	<bean class="com.br.spring.ioc.java.Music" id="music1">
		<property name="title" value="Love wins all" ></property>
		<property name="genre" value="발라드"  />
	</bean>
	*/
	
	
	
	// setter Injection 예시2
	@Bean
	public Singer singer1() {
		Singer s = new Singer();
		s.setName("아이유");
		s.setMusic(music1());
		return s;
	}
	
	
	
	// constructor Injection 예시
	/*
	@Bean
	public Music music2() {
		return new Music("EASY", "K-POP");
	}
	*/
	
	// 메소드명은 마음대로 짓고 빈의 이름을 따로 작성하는 방법 (name속성으로 별도로 빈 이름 지정 가능)
	@Bean(name="music2")
	public Music abcd() {
		return new Music("EASY", "K-POP");
	}
	
	
	
	/*
	@Bean
	public Singer singer2() {
		return new Singer("르세라핌", music2());
	}
	*/
	
	// 메소드명은 마음대로 짓고 빈의 이름을 따로 작성하는 방법 (name속성으로 별도로 빈 이름 지정 가능)
	@Bean(name="singer2")
	public Singer qwer() {
		return new Singer("르세라핌", abcd());
	}
	
}
