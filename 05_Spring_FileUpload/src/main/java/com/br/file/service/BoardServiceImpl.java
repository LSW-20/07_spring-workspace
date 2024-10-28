package com.br.file.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.file.dao.BoardDao;
import com.br.file.dto.AttachDto;
import com.br.file.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;

	@Override
	public int insertOneFileBoard(BoardDto board, AttachDto attach) {
		
		
		int result = boardDao.insertBoard(board);
		
		if(result > 0 && attach != null) {
			result = boardDao.insertAttach(attach);
		}
		
		
		return result;
	}
	

	@Override
	public int insertManyFileBoard(BoardDto board, List<AttachDto> list) {
		
		int result = boardDao.insertBoard(board);
		
		
		if(result > 0 && !list.isEmpty()) {
			result = 0;
			for(AttachDto attach : list) {
				result += boardDao.insertAttach(attach);
			}
		}
		
		return result;
	}
	

	@Override
	public List<AttachDto> selectAttachList() {
		return boardDao.selectAttachList();
	}


	@Override
	public BoardDto selectBoard(int boardNo) {
		return boardDao.selectBoard(boardNo);
	}
	
}
