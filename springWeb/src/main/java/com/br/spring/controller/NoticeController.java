package com.br.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.spring.dto.AttachDto;
import com.br.spring.dto.MemberDto;
import com.br.spring.dto.NoticeDto;
import com.br.spring.dto.PageInfoDto;
import com.br.spring.service.NoticeService;
import com.br.spring.util.FileUtil;
import com.br.spring.util.PagingUtil;

import lombok.RequiredArgsConstructor;

@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller
public class NoticeController {

	  private final NoticeService noticeService;
	  private final PagingUtil pagingUtil;
	  private final FileUtil fileUtil;
	  
	  
	  @GetMapping("/list.do")
	  public void list(@RequestParam(value="page", defaultValue="1") int currentPage, Model model) {

		  int listCount = noticeService.selectNoticeListCount();
		  
		  PageInfoDto pi = pagingUtil.getPageInfoDto(listCount, currentPage, 5, 5);
		  List<NoticeDto> list = noticeService.selectNoticeList(pi);
		  
		  model.addAttribute("pi", pi);
		  model.addAttribute("list", list);
		  
		  // return "notice/list"; 생략
	  }	  
	  
	  
	  @GetMapping("/regist.do")
	  public void registPage() {} // 메소드명은 별 의미 없다. 마음대로 작성.
	  
	  @PostMapping("/insert.do")
	  public String regist(NoticeDto notice // 공지사항 제목, 공지사항 내용이 담김.
						 , List<MultipartFile> uploadFiles // 첨부파일 개수만큼 MultipartFile 객체가 담긴다.
						 , HttpSession session // 로그인한 회원정보(회원번호)는 session에서 뽑아야 한다.
						 , RedirectAttributes rdAttributes) { 
		  
		  // notice 테이블의 notice_writer 컬럼에 insert할 회원번호
		  notice.setNoticeWriter( String.valueOf( ((MemberDto)session.getAttribute("loginUser")).getUserNo() ) );
		  
		  // 첨부파일 업로드 후에 attachment테이블에 insert할 데이터
			List<AttachDto> attachList = new ArrayList<>();
			for(MultipartFile file : uploadFiles) {
				if(file != null && !file.isEmpty()) {
					Map<String, String> map = fileUtil.fileupload(file, "notice");
					attachList.add(AttachDto.builder()
											.filePath(map.get("filePath"))
											.originalName(map.get("originalName"))
											.filesystemName(map.get("filesystemName"))
											.refType("N")
											.build() );
				}
			}
			
            notice.setAttachList(attachList); // 제목, 내용, 작성자회원번호, 첨부파일들정보			
		  
          
          
            int result = noticeService.insertNotice(notice);
            
          
			if( attachList.isEmpty() && result == 1 || !attachList.isEmpty() && result == attachList.size() ) { // && (AND) 연산자가 || (OR) 연산자보다 우선순위가 높다.
				
				rdAttributes.addFlashAttribute("alertMsg", "게시글 등록 성공");
				
			}else {
				
				rdAttributes.addFlashAttribute("alertMsg", "게시글 등록 실패");
				
			}
			
		  
		    return "redirect:/notice/list.do";
	  }
	  
	  
	  
	  
	  @GetMapping("/detail.do")
	  public void detail(int no, Model model) {
		// 상세페이지에 필요한 데이터
		// 게시글(제목, 작성자, 작성일, 내용) 데이터, 첨부파일 데이터(원본명, 저장경로, 실제파일명)들
		
		// 게시글을 조회하는 쿼리 따로, 첨부파일 조회하는 쿼리 따로 담아서 상세페이지로 이동을 해도 된다.
		// 모든 데이터를 하나의 쿼리로 조회해서 하나의 NoticeDto 객체에 담아서 상세페이지로 이동할 예정이다.
		
		
		NoticeDto n = noticeService.selectNotice(no);
		// NoticeNo, NoticeTitle, NoticeContent, NoticeWriter, registDt, attachList
		
		model.addAttribute("n", n);
		  
		  
	  }
	  
	  
}
