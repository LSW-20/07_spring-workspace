package com.br.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MvcController {

	
	@RequestMapping(value= {"/", "/main.do"}, method=RequestMethod.GET)
	public String abcd() {
		System.out.println("MvcController 클래스의 abcd 메소드 작동됨");
		return "main"; 
	}
	
}
