package com.br.spring.ioc.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class SpringMain {

	public static void main(String[] args) {
		
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBeanConfig.class);
		
		Music m1 = ctx.getBean("music1", Music.class);
		Singer s1 = ctx.getBean("singer1", Singer.class);
		System.out.println(m1);
		System.out.println(s1);
		
		System.out.println("------------------------------------------------");
		
		Music m2 = ctx.getBean("music2", Music.class);
		Singer s2 = ctx.getBean("singer2", Singer.class);
		System.out.println(m2);
		System.out.println(s2);
		
	}
	
}
