package com.br.sbatis.service;

import java.util.List;

import com.br.sbatis.dto.NoticeDto;

public interface NoticeService {

	
	// 전체목록조회
	List<NoticeDto> selectNoticeList();
	
	// 번호로 공지사항 한 개 조회
	NoticeDto selectNoticeByNo(int noticeNo);
	
	// 공지사항 등록
	int insertNotice(NoticeDto n);
	
	// 공지사항 수정
	int updateNotice(NoticeDto n);
	
	// 다수의 번호들 가지고 공지사항 일괄삭제
	int deleteNotice(String[] deleteNo);
	
	
	
	// 트랜잭션 테스트용 메소드
	int transactionTest();
}
