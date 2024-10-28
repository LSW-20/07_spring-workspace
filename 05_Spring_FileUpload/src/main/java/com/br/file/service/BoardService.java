package com.br.file.service;

import java.util.List;

import com.br.file.dto.AttachDto;
import com.br.file.dto.BoardDto;

public interface BoardService {

	// 한 개의 첨부파일과 함께 게시글 등록
	int insertOneFileBoard(BoardDto board, AttachDto attach);
	
	// 다중 첨부파일과 함께 게시글 등록
	int insertManyFileBoard(BoardDto board, List<AttachDto> list);
	
	// 첨부파일 목록 조회
	List<AttachDto> selectAttachList();
	
	
	// 글번호로 게시글 조회
	BoardDto selectBoard(int boardNo);
	
}
