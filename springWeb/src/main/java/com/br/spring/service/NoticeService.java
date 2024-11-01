package com.br.spring.service;

import java.util.List;

import com.br.spring.dto.NoticeDto;
import com.br.spring.dto.PageInfoDto;

public interface NoticeService {

	// 게시글 목록 조회 (페이징 처리)
	int selectNoticeListCount(); 
	List<NoticeDto> selectNoticeList(PageInfoDto pi);
	
	// 게시글 등록
	int insertNotice(NoticeDto n); // 첨부파일도 NoticeDto에 담았다.
	
	
	// 게시글 상세 - 게시글 조회
	NoticeDto selectNotice(int noticeNo); // 첨부파일관련 조회 데이터도 NoticeDto에 담아서 가져온다.

}
