package com.br.mvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.mvc.dao.NoticeDao;
import com.br.mvc.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@Service // Service 역할의 클래스에 부여하는 Component 어노테이션 (빈 스캐닝에 의해 자동으로 빈 등록됨)
@RequiredArgsConstructor
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

}
