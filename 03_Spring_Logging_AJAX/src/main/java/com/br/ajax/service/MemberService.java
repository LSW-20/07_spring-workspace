package com.br.ajax.service;

import java.util.List;

import com.br.ajax.dto.MemberDto;

public interface MemberService {

	// 아이디와 비번 가지고 특정 회원 이름 조회하는 서비스
	String selectMemberByIdPwd(String userId, String userPwd);

	// 번호 가지고 회원전체정보 조회하는 서비스
	MemberDto selectMemberByNo(int userNo);

	// 회원 전체 목록을 조회하는 서비스
	List<MemberDto> selectMemberList();
	
}
