package com.br.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/member")
@Controller
public class MemberController {


    // 1. HttpServletRequest 방법
	@RequestMapping("/detail.do")
	public String memberDetail(HttpServletRequest request) {
		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println("조회할 회원번호: " + no);
		
		
		return "main"; // 응답은 메인페이지로 포워딩. 이게 중요한 건아닌데 404 거슬리니까.
	}

	
	// @RequestMapping(value="/enroll1.do", method=RequestMethod.GET) - 405 에러 발생
    // @RequestMapping(value="/enroll1.do", method=RequestMethod.POST) - 이렇게 해도 되고,
    @PostMapping("/enroll1.do") // 스프링이 버전업되며 새로 나온 방식
	public String memberEnroll1(HttpServletRequest request) {

		// request.setCharacterEncoding("utf-8"); -> 스프링에서 제공하는 인코딩 필터 등록 (web.xml)
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String address = request.getParameter("address");
		
		System.out.println("이름: " + name);
		System.out.println("나이: " + age);
		System.out.println("주소: " + address);
    	
		return "main"; 
	}

    
    
    // (2) @RequestParam 방법
    // request.getParameter()를 자동으로 실행하는 어노테이션
    @PostMapping("/enroll2.do")
    public String memberEnroll2(/*@RequestParam(value="name")*/ String name 
    						  ,	@RequestParam(value="age", defaultValue="10") int age
    						  , @RequestParam(value="address") String addr) {
    	
		System.out.println("이름: " + name);
		System.out.println("나이: " + age);
		System.out.println("주소: " + addr);
    	
    	return "main";
    }
    


    @GetMapping("/detail2.do")
	public String memberDetail2(int no) {
		System.out.println("조회할 회원번호: " + no);
		
		return "main"; 
	}
    
    
    
}
