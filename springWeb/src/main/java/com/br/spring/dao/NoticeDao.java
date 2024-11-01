package com.br.spring.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.br.spring.dto.AttachDto;
import com.br.spring.dto.NoticeDto;
import com.br.spring.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NoticeDao {

	private final SqlSessionTemplate sqlSession;
	

	public int selectNoticeListCount() {
		return sqlSession.selectOne("noticeMapper.selectNoticeListCount");
	}

	public List<NoticeDto> selectNoticeList(PageInfoDto pi) {
	
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage() - 1) * pi.getBoardLimit(), pi.getBoardLimit());
		
		return sqlSession.selectList("noticeMapper.selectNoticeList", null, rowBounds);
	}
	  
	
	public int insertNotice(NoticeDto n) {
		return sqlSession.insert("noticeMapper.insertNotice", n);
	}
	
	public int insertAttach(AttachDto attach) {
		return sqlSession.insert("noticeMapper.insertAttach", attach);
	}

	public NoticeDto selectNotice(int noticeNo) {
		return sqlSession.selectOne("noticeMapper.selectNotice", noticeNo);
	}
	  
	  
}
