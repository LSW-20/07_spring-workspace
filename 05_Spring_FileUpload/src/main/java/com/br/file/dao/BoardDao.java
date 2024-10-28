package com.br.file.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.br.file.dto.AttachDto;
import com.br.file.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardDao {

	private final SqlSessionTemplate sqlSession;
	
	
	public int insertBoard(BoardDto board) {
		return sqlSession.insert("boardMapper.insertBoard", board);
	}
	
	public int insertAttach(AttachDto attach) {
		return sqlSession.insert("boardMapper.insertAttach", attach);
	}
	
	public List<AttachDto> selectAttachList() {
		return sqlSession.selectList("boardMapper.selectAttachList");
	}
	
	
	public BoardDto selectBoard(int boardNo) {
		return sqlSession.selectOne("boardMapper.selectBoard", boardNo);
	}
}
