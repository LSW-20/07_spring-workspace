package com.br.spring.di;

import org.springframework.stereotype.Service;

//@Service
public class PhoneServiceWebImpl implements PhoneService {

	@Override
	public void selectList() {
		System.out.println("웹용 폰 전체조회 서비스 실행");
	}

	@Override
	public void insertPhone() {
		System.out.println("웹용 폰 등록 서비스 실행");
	}

}
