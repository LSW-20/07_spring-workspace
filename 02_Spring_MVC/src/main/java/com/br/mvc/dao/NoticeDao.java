package com.br.mvc.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.br.mvc.dto.NoticeDto;

@Repository  //  dao 역할의 클래스에 부여하는 Componenet 어노테이션(빈 스캐닝에 의해 빈 등록됨)
public class NoticeDao {

	
	// db에서 조회되었다고 가정하고 샘플데이터 만들기
	// db에 담겨있는 공지사항 데이터 재사용
	// list를 생성하고 add로 담지 않고, 한줄로 끝냄.
	private List<NoticeDto> dbList = Arrays.asList(
										new NoticeDto(1, "제목1", "내용1")
									  , new NoticeDto(2, "제목2", "내용2")	
									  , new NoticeDto(3, "제목3", "내용3")  );

	
	public List<NoticeDto> selectNoticeList(){
		return dbList;
	}
	
	
	public NoticeDto selectNoticeByNo(/*SqlSession sqlSession,*/int noticeNo) {
		
		// return 쿼리결과;
		
		
		// 그냥 3개의 NoticeDto 객체 중에서 하나를 찝어도 되고,
		for(NoticeDto n : dbList) {
			if(n.getNo() == noticeNo) {
				return n;
			}
		}
		
		
		return null; // db 연동해도 조회결과 없으면 null 반환이다. for문이 끝나기 전까지 조회결과를 못찾으면 null 반환.
		
	}
	
	
}
