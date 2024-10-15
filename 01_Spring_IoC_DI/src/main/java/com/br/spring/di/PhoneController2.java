package com.br.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PhoneController2 {
	
	
	@Autowired 
	private PhoneService pService; // 이러면 현재 서비스로 등록되어 있는 객체가 여기에 주입된다.
	

	@RequestMapping("/list.ph")
	public void selectList() {
		pService.selectList();
	}
	
	@RequestMapping("/insert.ph")
	public void insertPhone() {
		pService.insertPhone();
	}
	
}
