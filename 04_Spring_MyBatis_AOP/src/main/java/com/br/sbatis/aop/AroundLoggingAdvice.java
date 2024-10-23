package com.br.sbatis.aop;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AroundLoggingAdvice {

	
	/*
	 * Around Advice 메소드 작성법
	 * 1. 반환타입 : Object
	 * 2. 메소드명 : 마음대로
	 * 3. 매개변수 : ProceedingJoinPoint 타입 객체 (생략 불가능)
	 */
	
	@Around("execution (* com.br.sbatis.controller.*Controller.*(..) )")
	public Object aroundLoggingAdvice(ProceedingJoinPoint prJoinPoint) throws Throwable {
		
		// Pointcut 동작이전 시점 (@Before)
		HttpServletRequest request
		= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		// 요청시 전달값(파라미터)을 문자열로 만들어서 로그 출력
		Map<String,String[]> map = request.getParameterMap(); // ex) { no=["1"], name=["홍길동", "김말똥"] } 
		
		String params = "";
		if(map.isEmpty()) {
			params += "No Parameter";
		}else {
			for(Entry<String, String[]> entry : map.entrySet()) {
				params += entry.getKey() + ":" + Arrays.toString(entry.getValue()) + " ";
			}
		}
		
		
		
		log.debug("{}", "-".repeat(50));
		log.debug("{} | {}", request.getMethod(), request.getRequestURI());
		log.debug("{}", params);

		
		Object obj = prJoinPoint.proceed(); // 핵심로직 메소드를 동작 // 이 코드에 의해서 Pointcut(핵심로직)의 메소드가 실행된다. before after는 자동으로 실행되는데 around는 강제로 실행시켜줘야 한다.
		
		
		// Pointcut 동작이후 시점 (@After)
		log.debug("{}\n", "-".repeat(50));
		
		
		return obj;
		
	}
	
}
