package com.br.spring.ioc.xml;

public class Student {

	private String name;
	private String academy;
	private String classColor;
	private Calculator calc;
	
	public Student() {
		super();
	}

	public Student(String name, String academy, String classColor, Calculator calc) {
		super();
		this.name = name;
		this.academy = academy;
		this.classColor = classColor;
		this.calc = calc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public String getClassColor() {
		return classColor;
	}

	public void setClassColor(String classColor) {
		this.classColor = classColor;
	}

	public Calculator getCalc() {
		return calc;
	}

	public void setCalc(Calculator calc) {
		this.calc = calc;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", academy=" + academy + ", classColor=" + classColor + ", calc=" + calc + "]";
	}
	
	
	
}
