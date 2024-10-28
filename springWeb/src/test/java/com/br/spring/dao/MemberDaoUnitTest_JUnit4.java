package com.br.spring.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.br.spring.dto.MemberDto;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 메소드의 이름 순으로 테스트를 수행하겠다는걸 의미
@ContextConfiguration(locations= { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
									"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class) // JUnit4를 이용하겠다는 것을 의미한다.
public class MemberDaoUnitTest_JUnit4 {

	@Autowired
	private MemberDao memberDao;
	
	
	@Test
	public void test01_로그인테스트() {
		// given
		MemberDto m = MemberDto.builder()
							   .userId("admin01")
							   .userPwd("1234")
							   .build();
		
		// when
		MemberDto result = memberDao.selectMember(m); // 유효한 아이디 비번이기때문에 조회결과가 반드시 존재할거다. null이 아닐거다.
		// 우리가 예상하기로 result는 null이 아닐 거다. 그럼 검증알 해야 한다. result가 null이 아닌지.
		
		// then
		assertNotNull(result); // 스태틱 메소드다. ctrl shift o 하면 import static문이 생긴다.
		// ~~
		
		
	}
	
	
	@Test
	public void test02_회원가입테스트() {
		// given
		MemberDto m = MemberDto.builder()
				      		   .userId("test01") // insert를 위해 db에 없는 아이디 제시
				               .userPwd("1234")
				               .userName("테스트")
				               .email("test@br.or.kr")
				               .gender("M")
				               .phone("010-1111-2222")
				               .address("서울시 강서구 마곡동")
				               .build(); // 참고로 실제로 insert까지 진행된다. 이 데이터는 이미 들어가서 다시 실행하면 unique 제약조건 위배될 수 있다.
		
		// when
		int result = memberDao.insertMember(m); // 쿼리에 문제가 없고 예상하는 값으로는 1이 올것 같다.
		
		// then
		assertEquals(1, result); // 스태틱 메소드다. ctrl shift o 하면 import static문이 생긴다.
		// 둘이 일치하다면 테스트 성공, 일치하지 않으면 테스트 실패로 안내된다.
	}
	
	
	@Test
	public void test03_일치하는아이디수조회테스트() {
		// 중복체크할 아이디를 문자열로 쿼리에 넘긴다.
		// given
		String checkId = "user01";
		
		// when
		int result = memberDao.selectUserIdCount(checkId);// 존재하는 아이디이기 때문에 예상값이 1이다. 1이 돌아올것 같다.
				
		// then
		assertEquals(1, result);
	}
	
	
	@Test
	public void test04_회원정보변경테스트() {
		// given
		MemberDto m = MemberDto.builder()
							   .userId("user01")
							   .userName("변경테스트")
							   .email("updatetest@br.com")
							   .phone("010-0000-0000")
							   .address("서울시 강남구")
							   .gender("F")
							   .build();
		
		// when
		int result = memberDao.updateMember(m); // 문제없다면 1로 돌아올 것이다. 변경될 데이터도 딱히 제약조건에 위배되는 데이터가 아니다.
		
		// then
		assertEquals(1, result);
	}
	
	
	@Test
	public void test05_회원탈퇴테스트() {
		// given
		String userId = "user02";
		
		// when
		int result = memberDao.deleteMember(userId); // id 문제 없기 때문에 예상값 1.
		
		// then
		assertEquals(1, result);
	}

}
