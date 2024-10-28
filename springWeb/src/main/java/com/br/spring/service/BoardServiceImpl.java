package com.br.spring.service;

import org.springframework.stereotype.Service;

import com.br.spring.dao.BoardDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	  private final BoardDao boardDao;
	
}
