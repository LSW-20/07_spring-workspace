package com.br.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/book") // 해당 컨트롤러는 앞으로 /book으로 시작하는 모든 url 요청을 처리하는 클래스로 선언
@Controller
public class BookController {
	

	// 요청보단 응답(forward, redirect)과 관련된 내용 학습
	
	//@RequestMapping(value="/book/list.do", method=RequestMethod.GET)
	//@RequestMapping(value="book/list.do", method=RequestMethod.GET) 
	//@RequestMapping(value="book/list.do")
	//@RequestMapping("book/list.do")
	@RequestMapping("/list.do")
	public String bookList() {
		
		// /WEB-INF/views/book/list.jsp 포워딩
		return "book/list";
		
	}

	
	//@RequestMapping("book/detail.do")
	@RequestMapping("/detail.do")
	public void bookDetail() {

		// /WEB-INF/views/book/detail.jsp 포워딩
		// 아무것도 작성하지 않았는데도 잘 뜬다.

	}
	


	
	//@RequestMapping("book/modifyForm.do")
	@RequestMapping("modifyForm.do")
	public String bookModifyForm() {

		// /WEB-INF/views/book/modify.jsp 포워딩

		return "book/modify";
	}
		

	//@RequestMapping(value="book/modify.do", method=RequestMethod.POST)
	@RequestMapping(value="/modify.do", method=RequestMethod.POST)
	public String bookModify() {

		// 수정 성공했다는 가정 하에 포워딩이 아닌
		// "contextPath/book/detail.do?no=번호" redirect(url 재요청)

		return "redirect:/book/detail.do?no=" + 1;
	}





	//@RequestMapping("book/enrollForm.do")
	@RequestMapping("/enrollForm.do")
	public String bookEnrollForm() {

		// /WEB-INF/views/book/enroll.jsp 포워딩

		return "book/enroll";
	}


	// 따로 응답을 포워드나 redirect하지 않고 
	// 요청한 브라우저로 <script>문을 돌려줘서 흐름 제어하기
	// 브라우저로 <script>문 돌려주면 그 스크립트문이 바로 실행된다.
	//@RequestMapping("book/enroll.do")
	@RequestMapping("/enroll.do")
	public void bookEnroll(HttpServletResponse response) throws IOException {


		// db 연동이 안되어있어서 그냥 0 아니면 1이 돌아오게끔.
		int result = Math.random() < 0.5 ? 1 : 0;


		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();


		out.println("<script>");
		if(result > 0) {
			
			// alert로 성공메세지 출력 후 목록페이지
			out.println("alert('성공적으로 등록되었습니다');");
			out.println("location.href='/mvc/book/list.do';");
			
		}else {
			
			// alert로 실패메세지 출력 후 기존에 작업중이던 작성페이지 유지
			out.println("alert('등록에 실패하였습니다');");
			out.println("history.back();");

		}
		out.println("</script>");

	}
}
