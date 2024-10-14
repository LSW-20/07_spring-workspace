package com.br.spring.ioc.xml;

public class Calculator {

	
	public void plus(int x, int y) {
		System.out.printf("%d + %d = %d\n", x, y, x+y);
	}
	
	public void minus(int x, int y) {
		System.out.printf("%d - %d = %d\n", x, y, x-y);
	}
	
	
}
