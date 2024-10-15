package com.br.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PhoneController1 {

	
	
	// (1) 필드
	@Autowired
	private Phone phone1;
	@Autowired
	private Phone phone2;
	
	@RequestMapping("/test1")
	public void diTest1() {
		System.out.println(phone1);
		System.out.println(phone2);
	}
	
	
	
	// (2) 생성자
	private Phone a;
	private Phone b;
	
	// @Autowired <- 생략가능
	public PhoneController1(Phone phone1, Phone phone2) {
		this.a = phone1;
		this.b = phone2;
	}
	
	@RequestMapping("/test2")
	public void diTest2() {
		System.out.println(a);
		System.out.println(b);
	}
	
	
	
	// (3) 메소드
	private Phone c;
	private Phone d;
	
	@Autowired
	public void setC(Phone phone1) {
		this.c = phone1;
	}
	
	@Autowired
	public void setD(Phone phone2) {
		this.d = phone2;
	}
	
	/* [이렇게도 가능]
	@Autowired
	public void setCandD(Phone phone1, Phone phone2) {
		this.c = phone1;
		this.d = phone2;
	}
	*/
	
	@RequestMapping("/test3")
	public void diTest3() {
		System.out.println(c);
		System.out.println(d);
	}
}
