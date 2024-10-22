package com.br.sbatis.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.sbatis.dto.NoticeDto;
import com.br.sbatis.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller
public class NoticeController {

	
	private final NoticeService noticeService;
	
	@GetMapping("/list.do") // /notice/list.do
	public void noticeList(Model model) {
		List<NoticeDto> list = noticeService.selectNoticeList();
		log.debug("list: {}", list);
		model.addAttribute("list", list);
		
		// return "notice/list"; void여도 포워딩하려 한다. url mapping("/notice/list")를 가지고 포워딩한다.
	}
	
	
	
	@GetMapping("/detail.do") // /notice/detail.do
	public void noticeDetail(int no, Model model) {
		NoticeDto n = noticeService.selectNoticeByNo(no);
		model.addAttribute("n",n);
	}
	
	
	@GetMapping("/enroll.do") // /notice/enroll.do
	public void noticeEnroll() {}
	// 이렇게만 작성해도 페이지 이동이 된다.
	
	
	@PostMapping("/insert.do")
	public String noticeInsert(NoticeDto n) {
		int result = noticeService.insertNotice(n);
		
		if(result > 0) { // 성공시 다시 목록페이지
			return "redirect:/notice/list.do";
		}else { // 실패시 메인페이지
			return "redirect:/";
		}
	}
	
	
	
	@GetMapping("/modify.do") // /notice/modify.do
	public void noticeModify(int no, Model model) {
		model.addAttribute("n", noticeService.selectNoticeByNo(no));
	}
	
	
	@PostMapping("/update.do")
	public String noticeUpdate(NoticeDto n) {
		int result = noticeService.updateNotice(n);

		if(result > 0){ // 성공시 상세페이지
			return "redirect:/notice/detail.do?no=" + n.getNo();
		}else { // 실패시 목록페이지
			return "redirect:/notice/list.do";
		}
	}
	
	@PostMapping("/delete.do")
	public String noticeDelete(String[] deleteNo) {
		// String[] arr = request.getParameterValues("deleteNo") <- request 객체가 있었다면
		

	    // 체크박스 선택 안 한 경우 예외 처리
	    if (deleteNo == null || deleteNo.length == 0) {
	        // 선택 항목이 없을 경우 에러 메시지와 함께 목록 페이지로 이동
	        return "redirect:/notice/list.do?error=noSelection";
	    }
		
		
		
		// 실행할 sql문 : delete from notice where no in (xx, xx, xx) <- 동적 쿼리
		
		int result = noticeService.deleteNotice(deleteNo);
		
		if(result == deleteNo.length){ // 성공시 목록페이지
			return "redirect:/notice/list.do";
		}else { // 실패시 메인페이지
			return "redirect:/";
		}
	}

}
