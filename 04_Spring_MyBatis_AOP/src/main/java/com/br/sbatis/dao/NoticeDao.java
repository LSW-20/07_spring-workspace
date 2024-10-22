package com.br.sbatis.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.br.sbatis.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NoticeDao {
	
	private final SqlSessionTemplate sqlSession;
	
	
	public List<NoticeDto> selectNoticeList() {
		return sqlSession.selectList("noticeMapper.selectNoticeList");
	}
	
	public NoticeDto selectNoticeByNo(int noticeNo) {
		return sqlSession.selectOne("noticeMapper.selectNoticeByNo", noticeNo);
	}
	
	public int insertNotice(NoticeDto n) {
		return sqlSession.insert("noticeMapper.insertNotice", n);
	}
	
	public int updateNotice(NoticeDto n) {
		return sqlSession.update("noticeMapper.updateNotice", n);
	}

	public int deleteNotice(String[] deleteNo) {
		return sqlSession.delete("noticeMapper.deleteNotice", deleteNo);
	}
	
}
