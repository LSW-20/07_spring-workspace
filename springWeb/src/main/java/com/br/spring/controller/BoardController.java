package com.br.spring.controller;

import java.io.File;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.spring.dto.AttachDto;
import com.br.spring.dto.BoardDto;
import com.br.spring.dto.MemberDto;
import com.br.spring.dto.PageInfoDto;
import com.br.spring.dto.ReplyDto;
import com.br.spring.service.BoardService;
import com.br.spring.util.FileUtil;
import com.br.spring.util.PagingUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
	
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService boardService;
	private final PagingUtil pagingUtil; 
	private final FileUtil fileUtil;
	
	
	// 메뉴 바에 있는 메뉴 클릭시	  /board/list.do => 1번 페이지 요청
	// 페이징 바에 있는 페이지 클릭시 /board/list.do?page=xx
	@GetMapping("/list.do")
	public void list(@RequestParam(value="page", defaultValue="1") int currentPage, Model model) {
		
		int listCount = boardService.selectBoardListCount();
		
		PageInfoDto pi = pagingUtil.getPageInfoDto(listCount, currentPage, 5, 5);
		List<BoardDto> list = boardService.selectBoardList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		// return "board/list"; 생략해도 됨.
		
	}
  
	  
	
	@GetMapping("/search.do")
	public String search(@RequestParam(value="page") int currentPage
					   , @RequestParam Map<String, String> search
					   , Model model) { 
		// 요청시 전달값을 매개변수를 둬서 받기 
		// 지금은 무조건 page라는 key값으로 1번이 올거라 defaultValue를 안썼다. 써도 된다.
		// 요청하는 페이지 번호는 currentPage에 담긴다.
		
		// 지금 condition과 keyword 값을 받아줄 dto 커맨드 객체가 없다. String 형 변수 2개를 둬서 받아도 된다. 근데 어차피 넘길때는 Map에 담아서 넘기도록 서비스 impl를 설계했었다.
		// ajax때 했던 @RequestBody로 Map으로 바로 받을 수 있었다.
		// 아니면 @RequestParam Map<String, String> search로 해도 된다. 이렇게 해본다. 알아서 key과 value값이 담긴다.
		// Map<String, String> search => {condition=user_id|board_Title|board_content, keyword=란}
		
		int listCount = boardService.selectSearchListCount(search);
		PageInfoDto pi = pagingUtil.getPageInfoDto(listCount, currentPage, 5, 5);
		List<BoardDto> list = boardService.selectSearchList(search, pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		model.addAttribute("search", search); // list.jsp가 로드되는 경우가 2가지 경우다. /search.do라는 요청으로 로드될 때는 search라는 key값으로 Map이 담겨있다.
		
		return "board/list"; // 이미 만든 list.jsp로 포워딩
	}
	
	
	
	@GetMapping("/regist.do")
	public void registPage() {} // 메소드명은 별 의미 없다. 마음대로 작성.
	
	@PostMapping("/insert.do")
	public String regist(BoardDto board // 글제목, 글내용은 담겨있다.
					   , List<MultipartFile> uploadFiles // 첨부파일 개수만큼 MultipartFile 객체가 담긴다.
					   , HttpSession session // 로그인한 회원정보(회원번호)는 session에서 뽑아야 한다.
					   , RedirectAttributes rdAttributes) { 
		
		// board테이블에 insert할 데이터
		board.setBoardWriter( String.valueOf( ((MemberDto)session.getAttribute("loginUser")).getUserNo() ) );
		
		// 첨부파일 업로드 후에 attachment테이블에 insert할 데이터
		List<AttachDto> attachList = new ArrayList<>();
		for(MultipartFile file : uploadFiles) {
			if(file != null && !file.isEmpty()) {
				Map<String, String> map = fileUtil.fileupload(file, "board");
				attachList.add(AttachDto.builder()
										.filePath(map.get("filePath"))
										.originalName(map.get("originalName"))
										.filesystemName(map.get("filesystemName"))
										.refType("B")
										.build() );
			}
		}
		
		board.setAttachList(attachList); // 제목, 내용, 작성자회원번호, 첨부파일들정보
		
		int result = boardService.insertBoard(board);
		
		if( attachList.isEmpty() && result == 1 || !attachList.isEmpty() && result == attachList.size() ) { // && (AND) 연산자가 || (OR) 연산자보다 우선순위가 높습니다.
			
			rdAttributes.addFlashAttribute("alertMsg", "게시글 등록 성공");
			
		}else {
			
			rdAttributes.addFlashAttribute("alertMsg", "게시글 등록 실패");
			
		}
		
		
		return "redirect:/board/list.do";
		
	}
	
	
	  
	
	
	@GetMapping("/increase.do") // 조회수 증가용 (타인의 글일 경우 호출) => /board/detail.do 재요청

	public String increaseCount(int no) {
		
		boardService.updateIncreaseCount(no);
		
		return "redirect:/board/detail.do?no=" + no;
	}


	
	@GetMapping("/detail.do")
	public void detail(int no, Model model) { // 게시글 상세조회용 (내 글일 경우 이걸로 바로 호출)
		// 상세페이지에 필요한 데이터
		// 게시글(제목, 작성자, 작성일, 내용) 데이터, 첨부파일 데이터(원본명, 저장경로, 실제파일명)들
		
		// 게시글을 조회하는 쿼리 따로, 첨부파일 조회하는 쿼리 따로 담아서 상세페이지로 이동을 해도 된다.
		// 모든 데이터를 하나의 쿼리로 조회해서 하나의 BoardDto 객체에 담아서 상세페이지로 이동할 예정이다.
		
		BoardDto b = boardService.selectBoard(no);
		// boardNo, boardTitle, boardContent, boardWriter, registDt, attachList		
		
		model.addAttribute("b", b);
		
		log.debug("BoardDto:{}", b);
	}
	
	
	@ResponseBody // 기본적으로 응답 뷰로 인식하기 때문에 붙여야 한다.
	@GetMapping(value="/rlist.do", produces="application/json")
	public List<ReplyDto> replyList(int no) {
		return boardService.selectReplyList(no); // ajax로 응답데이터를 돌려줄 때는 그냥 return만 해주면 된다.
	}
	
	@ResponseBody
	@PostMapping("/rinsert.do")
	public String replyInsert(ReplyDto r, HttpSession session) { // ajax의 data에서 보낸 데이터의 key값과 매개변수 dto의 필드명이 같으면 바로 담긴다.
		r.setReplyWriter(String.valueOf( ((MemberDto)session.getAttribute("loginUser")).getUserNo()  ));
		int result = boardService.insertReply(r);
		
		return result > 0 ? "SUCCESS" : "FAIL";
	}
	
	
	@PostMapping("/delete.do")
	public String remove(int no, RedirectAttributes rdAttributes) {
		int result = boardService.deleteBoard(no);
		
		if(result > 0) {
			rdAttributes.addFlashAttribute("alertMsg", "성공적으로 삭제되었습니다.");
		}else {
			rdAttributes.addFlashAttribute("alertMsg", "게시글 삭제에 실패하였습니다.");			
		}
		
		return "redirect:/board/list.do";
	}
	
	
	@PostMapping("/modify.do")
	public void modifyPage(int no, Model model) {
		model.addAttribute("b", boardService.selectBoard(no));
	}
	
	@PostMapping("/update.do")
	public String modify(BoardDto board // 번호, 제목, 내용
					 , String[] delFileNo // 삭제할 첨부파일 번호들 (x누르면 hidden으로 생긴다)
					 , List<MultipartFile> uploadFiles // 새로 넘어온 첨부파일들
					 , RedirectAttributes rdAttributes) {
		
		// log.debug("board: {}", board);
		// log.debug("delFileNo: {}", Arrays.toString(delFileNo));
		// log.debug("uploadFiles: {}", uploadFiles);
		
	
		// 후에 db에 반영 성공시, 삭제할 파일들의 삭제를 위해 미리 조회
		List<AttachDto> delAttachList = boardService.selectDelAttach(delFileNo);
		
		List<AttachDto> attachList = new ArrayList<>();
		for(MultipartFile file : uploadFiles) {
			if(file != null && !file.isEmpty()) {
				Map<String, String> map = fileUtil.fileupload(file, "board");
				attachList.add(AttachDto.builder()
										.filePath(map.get("filePath"))
										.originalName(map.get("originalName"))
										.filesystemName(map.get("filesystemName"))
										.refType("B")
										.refNo(board.getBoardNo())
										.build() );
			}
		}
		
		board.setAttachList(attachList);
		
		int result = boardService.updateBoard(board, delFileNo);
		
		if(result > 0) { // 성공
			rdAttributes.addFlashAttribute("alertMsg", "성공적으로 수정되었습니다.");
			for(AttachDto at : delAttachList) {
				new File(at.getFilePath() + "/" + at.getFilesystemName()).delete();
			}
		}else { // 실패
			rdAttributes.addFlashAttribute("alertMsg", "게시글 수정에 실패했습니다.");
			// db에 기록에 실패했을 때도 새로 넘어온 첨부파일은 저장이 된다. 삭제해줘야 한다. 그건 직접 해보세요.
		}
		
		return "redirect:/board/detail.do?no=" + board.getBoardNo();
		
		
		// board 테이블 update 무조건 진행
		
		// 삭제할 첨부파일이 있었을 경우 => attachment 테이블로부터 delete, 파일 삭제
		
		// 새로 넘어온 첨부파일이 있었을 경우 => 파일업로드, attachment 테이블로부터 insert
		
	}
	
	
	
}
