package com.br.sbatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.sbatis.dao.NoticeDao;
import com.br.sbatis.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	
	
	private final NoticeDao noticeDao;
	

	@Override
	public List<NoticeDto> selectNoticeList() {
		return noticeDao.selectNoticeList();
	}

	@Override
	public NoticeDto selectNoticeByNo(int noticeNo) {
		return noticeDao.selectNoticeByNo(noticeNo);
	}

	@Override
	public int insertNotice(NoticeDto n) {
		return noticeDao.insertNotice(n);
	}

	@Override
	public int updateNotice(NoticeDto n) {
		return noticeDao.updateNotice(n);
	}

	@Override
	public int deleteNotice(String[] deleteNo) {
		return noticeDao.deleteNotice(deleteNo);
	}

	
	
	@Override
	public int transactionTest() {
		
		int result = noticeDao.insertNotice(NoticeDto.builder()
													 .title("트랜잭션테스트제목2")
													 .content("트랜잭션테스트내용2")
													 .build()); // title, content 필드에 값을 담은 Notice 객체 생성
		
		if(result > 0) {
			result = noticeDao.insertNotice(NoticeDto.builder().content("실패예정").build()); // content 필드에만 값을 담은 Notice 객체 생성
		}
		
		
	
		
		return result;
	}
	
	

}
