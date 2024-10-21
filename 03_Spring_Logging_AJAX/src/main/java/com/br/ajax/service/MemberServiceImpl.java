package com.br.ajax.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.ajax.dto.MemberDto;

@Service
public class MemberServiceImpl implements MemberService {

	// 이게 db에 저장된 정보라고 가정
	private List<MemberDto> list = Arrays.asList(
										new MemberDto(1, "user01", "pass01", "홍길동")
									  , new MemberDto(2, "user02", "pass02", "김말똥")
									  , new MemberDto(3, "user03", "pass03", "강개순")
												);


	@Override
	public String selectMemberByIdPwd(String userId, String userPwd) {
		for(MemberDto m : list) {
			if(m.getUserId().equals(userId) && m.getUserPwd().equals(userPwd)) {
				return m.getUserName();
			}
		}
		return null;
	}
	

	@Override
	public MemberDto selectMemberByNo(int userNo) {
		for(MemberDto m : list) {
			if(m.getUserNo() == userNo) {
				return m;
			}
		}
		return null;
	}
	

	@Override
	public List<MemberDto> selectMemberList() {
		return list;
	}

}
