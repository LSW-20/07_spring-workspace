package com.br.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.br.spring.dao.BoardDao;
import com.br.spring.dto.AttachDto;
import com.br.spring.dto.BoardDto;
import com.br.spring.dto.PageInfoDto;
import com.br.spring.dto.ReplyDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;
	

	@Override
	public int selectBoardListCount() {
		return boardDao.selectBoardListCount();
	}

	@Override
	public List<BoardDto> selectBoardList(PageInfoDto pi) {
		return boardDao.selectBoardList(pi);
	}

	@Override
	public int selectSearchListCount(Map<String, String> search) {
		return boardDao.selectSearchListCount(search);
	}

	@Override
	public List<BoardDto> selectSearchList(Map<String, String> search, PageInfoDto pi) {
		return boardDao.selectSearchList(search, pi);
	}

	@Override
	public int insertBoard(BoardDto b) { // 컨트롤러에서 BoardDto에 작성자, 제목, 내용, 첨부파일 list도 담아서 넘겼다.
		
		int result = boardDao.insertBoard(b);
		
		List<AttachDto> list = b.getAttachList();
		if(result > 0 && !list.isEmpty()) {
			result = 0;
			for(AttachDto attach : list) {
				result += boardDao.insertAttach(attach);
			}
		}
		
		return result;
	}

	@Override
	public int updateIncreaseCount(int boardNo) {
		return boardDao.updateIncreaseCount(boardNo);
	}
	

	@Override
	public BoardDto selectBoard(int boardNo) {
		return boardDao.selectBoard(boardNo);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return boardDao.deleteBoard(boardNo);
	}

	@Override
	public List<ReplyDto> selectReplyList(int boardNo) {
		return boardDao.selectReplyList(boardNo);
	}

	@Override
	public int insertReply(ReplyDto r) {
		return boardDao.insertReply(r);
	}
	
	

	@Override
	public List<AttachDto> selectDelAttach(String delFileNo[]) {
		return delFileNo == null ? new ArrayList<>() : boardDao.selectDelAttach(delFileNo);
	}

	@Override
	public int updateBoard(BoardDto b, String[] delFileNo) {
		
		// 1) board 테이블에 update
		int result1 = boardDao.updateBoard(b);
		
		// 2) attachment 테이블에 delete
		int result2 = 1; // delFileNo이 null일 때도 ~
		if(result1 > 0 && delFileNo != null) {
			result2 = boardDao.deleteAttach(delFileNo);
		}
		
		// 3) attachment 테이블에 insert
		List<AttachDto> list = b.getAttachList();
		int result3 = 0;
		for(AttachDto at : list) {
			result3 += boardDao.insertAttach(at);
		}
		
		
		// 성공에 대한 조건- result1이 1이어야 하고, result2가 0보다 커야 하고, result3가 list의 사이즈와 동일해야 한다.

		return result1 == 1
					&& result2 > 0
						&& result3 == list.size()
							? 1 : -1;
		
	}
	
	
	

	@Override
	public int deleteReplyCompletely() {
		return boardDao.deleteReplyCompletely();
	}
	
}
