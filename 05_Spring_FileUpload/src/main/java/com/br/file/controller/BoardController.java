package com.br.file.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.br.file.dto.AttachDto;
import com.br.file.dto.BoardDto;
import com.br.file.service.BoardService;
import com.br.file.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService boardService;
	private final FileUtil fileUtil;
	
	
	@PostMapping("/insert1.do")
	public String insertOneFileBoard(BoardDto board, MultipartFile uploadFile) {
		
		log.debug("board: {}", board); 			// 잘 담겼는지 확인용 로그 출력
		log.debug("attach: {}", uploadFile);	// 잘 담겼는지 확인용 로그 출력
		
		
		AttachDto attach = null;
		
		if(uploadFile != null && !uploadFile.isEmpty()) { // 첨부파일이 존재할 경우 => 업로드
			// 전달된 파일 업로드 처리
			

			// FileUitl 클래스의 fileupload 메소드를 호출 (uploadFile을 넘기면서)
			Map<String, String> map = fileUtil.fileupload(uploadFile);
			
			
			// (4) db에 기록할 정보를 자바 객체(AttachDto)에 세팅
			attach = AttachDto.builder()
							  .filePath(map.get("filePath"))
							  .originalName(map.get("originalFilename"))
							  .filesystemName(map.get("filesystemName"))
							  .build();
			
		}
		


		int result = boardService.insertOneFileBoard(board, attach);
		
		if(result > 0) {
			log.debug("게시글 등록 성공");
		}else {
			log.debug("게시글 등록 실패");
		}

		return "redirect:/"; // 성공이든 실패든 메인페이지 요청
		
	}
	
	
	
	
	
	@PostMapping("/insert2.do")
	public String insertManyFileBoard(BoardDto board, List<MultipartFile> uploadFile) {
		
		List<AttachDto> list = new ArrayList<>();
		
		
		for(MultipartFile file : uploadFile) {
			
			if(file != null && !file.isEmpty()) { // 파일이 존재할 경우
				
				Map<String, String> map = fileUtil.fileupload(file);
				list.add(AttachDto.builder()
								  .filePath(map.get("filePath"))
								  .originalName(map.get("originalFilename"))
								  .filesystemName(map.get("filesystemName"))
								  .build());
			}
		}
		
		
		// 넘어온 첨부파일이 없었다면 list는 비어있는 상태다.
		int result = boardService.insertManyFileBoard(board, list);
		
		// 첨부파일이 없었다면 result는 board에만 insert되고 result는 1이다.
		// 첨부파일이 있었다면 첨부파일의 개수만큼
		if(list.isEmpty() && result == 1 || !list.isEmpty() && result == list.size()) {
			log.debug("다중 첨부파일 게시글 등록 성공");
		}else {
			log.debug("다중 첨부파일 게시글 등록 실패");
		}
		
		
		return "redirect:/"; // 성공이든 실패든 메인페이지 요청
	}
	
	
	@ResponseBody
	@PostMapping("/ajaxInsert.do")
	public String insertAjaxFileBoard(BoardDto board, MultipartFile uploadFile) {
		
	
		AttachDto attach = null;
		
		if(uploadFile != null && !uploadFile.isEmpty()) { 
			
			Map<String, String> map = fileUtil.fileupload(uploadFile);
			
			attach = AttachDto.builder()
							  .filePath(map.get("filePath"))
							  .originalName(map.get("originalFilename"))
							  .filesystemName(map.get("filesystemName"))
							  .build();
		}
		
		
		int result = boardService.insertOneFileBoard(board, attach);
		
		if(result > 0) {
			log.debug("ajax 첨부파일 게시글 등록 성공");
			return "SUCCESS";
		}else {
			log.debug("ajax 첨부파일 게시글 등록 실패");
			return "FAIL"; 
		}
		
	}
	
	
	@ResponseBody
	@GetMapping(value="/atlist.do", produces="application/json")
	public List<AttachDto> selectAttachList() {
		
		return boardService.selectAttachList(); // success 함수의 매개변수로 리턴된다.
	}
	
	
	@PostMapping("/editor/insert.do")
	public String editorInsertBoard(BoardDto board) {
		
		log.debug("board: {}", board);
		int result = boardService.insertOneFileBoard(board, null);
		
		if(result > 0) {
			log.debug("에디터 게시글 등록 성공");
		}else {
			log.debug("에디터 게시글 등록 실패");
		}
		
		return "redirect:/";
	}
	
	
	@ResponseBody
	@PostMapping("/editor/imageUpload.do")
	public String editorImageUpload(MultipartFile image) {
		Map<String, String> map = fileUtil.fileupload(image);
		return map.get("filePath") + "/" + map.get("filesystemName");
		//   /upload/20241025/~~~~~~~~~.jpg <- 이렇게 제작한 경로를 이미지의 src 속성값으로 부여할 예정이다.
	}
	
	@ResponseBody
	@GetMapping(value = "/editor/detail.do", produces="application/json")
	public BoardDto editorSelectBoard(int boardNo) {
		BoardDto b = boardService.selectBoard(boardNo);
		return b;
	}
	
}
