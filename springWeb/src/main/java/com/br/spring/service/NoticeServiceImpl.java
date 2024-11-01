package com.br.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.spring.dao.NoticeDao;
import com.br.spring.dto.AttachDto;
import com.br.spring.dto.NoticeDto;
import com.br.spring.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	
	private final NoticeDao noticeDao;

	
	@Override
	public int selectNoticeListCount() {
		return noticeDao.selectNoticeListCount();
	}

	@Override
	public List<NoticeDto> selectNoticeList(PageInfoDto pi) {
		return noticeDao.selectNoticeList(pi);
	}

	@Override
	public int insertNotice(NoticeDto n) { // 컨트롤러에서 NoticeDto에 작성자, 제목, 내용, 첨부파일 list를 담아 넘겼다.

		int result = noticeDao.insertNotice(n);
		
		List<AttachDto> list = n.getAttachList();
		if(result > 0 && !list.isEmpty()) {
			result = 0;
			for(AttachDto attach : list) {
				result += noticeDao.insertAttach(attach);
			}
		}
		
		return result;
	}

	@Override
	public NoticeDto selectNotice(int noticeNo) {
		return noticeDao.selectNotice(noticeNo);
	}
	
}
