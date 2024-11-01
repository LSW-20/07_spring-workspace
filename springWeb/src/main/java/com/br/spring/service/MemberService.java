package com.br.spring.service;

import java.util.Map;

import com.br.spring.dto.MemberDto;

public interface MemberService {

	// 로그인
	MemberDto selectMember(MemberDto m);
	
	// 회원가입
	int insertMember(MemberDto m);
	
	// 아이디중복체크
	int selectUserIdCount(String checkId);
	
	// 회원정보변경
	int updateMember(MemberDto m);
	
	// 회원탈퇴
	int deleteMember(String userId);
	
	// 회원프로필변경
	int updateProfileImg(MemberDto m);

	
	// 비밀번호 변경
	int updatePwd(Map<String, String> map);
}
