package com.br.spring.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.br.spring.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // final 필드에 대한 매개변수 생성자가 만들어지고, 매개변수 생성자 주입에 의해서 생성된 객체가 boardService에 대입된다.
@Component 
public class BoardScheduler {
	
	private final BoardService boardService;
	
	

	@Scheduled(cron="0 10 11 * * *") // 매일 오전 11시 10분마다 로그 출력 (로그쓰게 @Slf4j)
	public void esecute1() { // 반환형은 void, 메소드명은 마음대로
		
		log.debug("매일 오전 11시 10분마다 실행됨");
	}
	
	
	@Scheduled(cron="0 0/1 * * * *") // 1분마다 실행
	public void execute2() {
		log.debug("1분마다 매번 실행됨");
	}
	
	
	@Scheduled(cron="0 0 0/1 * * *") // 1분마다 실행
	public void execute3() {
		log.debug("1시간 마다 매번 실행됨"); 
	}
	
	
	
	// 통계 정보를 위해 매일 밤 12시에 현재 게시글의 총 갯수를 로그로 기록을 남기는 스케줄러가 필요하다고 가정한다.
	@Scheduled(cron="0 0 0 * * *") // 매일 밤 12시마다 실행
	public void execute4() {
		log.debug("현재 게시글의 총 갯수: {}", boardService.selectBoardListCount());
	}
	// 지금은 개발서버고 나중에 실서버에 배포하면 실서버는 24시간 계속 돌아간다.
	
	
	// 일요일 새벽 1시마다 현재 status가 N인 댓글을 완벽히 delete하는 스케줄러가 필요하다고 가정한다.
	//@Scheduled(cron="0 0 1 * * SUN")
	@Scheduled(cron="30 0/1 * * * *")
	public void execute5() {
		int result = boardService.deleteReplyCompletely();
		log.debug("현재 완전 삭제된 댓글 갯수: {}", result);
	}
	
}
