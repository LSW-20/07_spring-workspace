package com.br.spring.service;

import org.springframework.stereotype.Service;

import com.br.spring.dao.NoticeDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	  private final NoticeDao noticeDao;
}
