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
		return 0;
	}

	@Override
	public int insertManyFileBoard(BoardDto board, List<AttachDto> list) {
		return 0;
	}

	@Override
	public List<AttachDto> selectAttachList() {
		return null;
	}
	
}
