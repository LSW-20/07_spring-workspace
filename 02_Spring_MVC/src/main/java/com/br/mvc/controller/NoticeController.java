package com.br.mvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.mvc.dto.NoticeDto;
import com.br.mvc.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // lombok
//@AllArgsConstructor lombok
@RequestMapping("/notice")
@Controller
public class NoticeController {

	
	// 직접 생성하면 결합도가 높아지는 문제가 있다. 매번 생성해서 메모리도 많이 사용한다.
	// private NoticeService noticeService = new NoticeServiceImpl();
	// @Autowired로 스프링에게서 주입받아 사용한다. 
	// 그러려면 NoticeServiceImpl을 빈으로 등록해야 한다.
	// xml 파일에 가서 빈 등록을 해도 되지만 NoticeServiceImpl 클래스 위에 @Service 어노테이션을 작성하면 빈 스캐닝에 의해 빈 등록이 된다.
	

/* 
	(1) 필드 주입 방식
	@Autowired
	private NoticeService noticeService; 
*/
	
	

/* 
	(2) 메소드 주입 방식
	private NoticeService noticeService;
	
	@Autowired
	public void setNoticeService(NoticeService noticeService) { // 자동실행
		this.noticeService = noticeService;
	}
*/
	
	
    //(3) 생성자 주입 방식 (생성자는 자동으로 실행되기 때문에 @Autowired 생략 가능)
	private int no;
	private final NoticeService noticeService;
	
	
/*
	public NoticeController(NoticeService noticeService) {   <- 롬복
		this.noticeService = noticeService;
	}
*/
	
	
	
	// =========== 포워딩할 응답페이지에 필요한 데이터를 담는 방법 ===========
	
	// (1) Model 객체 이용하기
	@GetMapping("/list.do")
	public String noticeList(Model model) {
		List<NoticeDto> list = noticeService.selectNoticeList(); // 응답페이지에 필요한 데이터
		
		model.addAttribute("list", list);
		
		// /WEB-INF/views/notice/list.jsp로 포워딩
		return "notice/list";
		
	}
	
	
	// (2) ModelAndView 객체 이용하기
	@GetMapping("/detail.do")
	public ModelAndView noticeDetail(int no, ModelAndView mv) { // key값과 매개변수명이 같으면 @RequestParam 생략가능
		
		// NoticeDto n = noticeService.selectNoticeByNo(no); 응답 페이지에 필요한 데이터
		// return "notice/detial"; 응답뷰
		
		mv.addObject("notice", noticeService.selectNoticeByNo(no)); // 응답 페이지에 필요한 데이터
		mv.setViewName("notice/detail"); // 응답뷰
		
		// mv.addObject("notice", noticeService.selectNoticeByNo(no)).setViewName("notice/detail"); 
		// addObject 메소드가 해당 ModelAndView 객체를 반환하기 때문에 한줄로 메소드 체이닝 가능하다.
		
		return mv;
		
	}
	
	
	
	@RequestMapping("/modifyForm.do")
	public String noticeModifyForm(int no, Model model) {
		
		NoticeDto notice = noticeService.selectNoticeByNo(no);
		model.addAttribute("notice", notice);
		return "notice/modifyForm";
		
	}
	
	
	@PostMapping("/update.do")
	public String noticeUpdate(NoticeDto n, /*Model model*/ RedirectAttributes ra) { // 1) 요청시 전달값 처리 - 커맨드 객체
		
		// 2) 요청처리를 위한 서비스 호출
		int result = noticeService.updateNotice(n);
		
		// 3) 응답
		if(result > 0) {
			
			// return "notice/detail"; 이건 포워딩이다. 

			//model.addAttribute("alertMsg", "성공적으로 수정되었습니다.");
			ra.addFlashAttribute("alertMsg", "성공적으로 수정되었습니다.");
			
			return "redirect:/notice/detail.do?no=" + n.getNo(); // context path 안줘도 됨. 여기에도 글번호 넘겨야 함.
		}else {
			// return "main"; 이렇게 하면 메인페이지가 뜨긴 하는데 url이 메인 페이지가 아니라 /update.do다.
			// MvcController 가보면 이미 메인페이지로 이동하는 메소드가 정의되어 있음.
			return "redirect:/";
		}
		
	}
	
	
}
