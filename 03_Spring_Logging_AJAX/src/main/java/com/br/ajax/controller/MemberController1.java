package com.br.ajax.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ajax.dto.MemberDto;
import com.br.ajax.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/member1")
@RequiredArgsConstructor
@Controller
public class MemberController1 {

	private final MemberService memService;
	
	private Logger logger = LoggerFactory.getLogger(MemberController1.class);
	

	
	/* 기존의 HttpServletResponse 객체 이용하는 방식

    @GetMapping("/detail1.do")
    public void memberDetail(String id, String pwd, HttpServletResponse response) throws IOException {
        logger.debug("request id: {}, pwd: {}", id, pwd);
        String result = memService.selectMemberByIdPwd(id, pwd);
        
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result);
    } 
	*/
	
	
	// Spring 방식
	@ResponseBody
    @GetMapping(value="/detail1.do", produces="text/html; charset=utf-8")
    public String memberDetail(String id, String pwd) {

        logger.debug("request id: {}, pwd: {}", id, pwd);

        String result = memService.selectMemberByIdPwd(id, pwd);
        
        return result;
    } 
	
	
	@ResponseBody
	@GetMapping(value="/detail2.do", produces="text/html; charset=utf-8")
	public String memberDetail2(String userId, String userPwd) {
		return memService.selectMemberByIdPwd(userId, userPwd); // 문자열 바로 반환
	}
	
		
	@ResponseBody
	@GetMapping(value="/detail3.do", produces="application/json")
	public MemberDto memberDetail3(@RequestParam(value="no", defaultValue="1") int no) { // 요청시 전달값 받기

		MemberDto mem = memService.selectMemberByNo(no);
        return mem; // {필드명:필드값, 필드명:필드값, ...}
	}
	

	@ResponseBody
    @GetMapping(value="list.do", produces="application/json")
    public List<MemberDto> memberList() {
        List<MemberDto> list = memService.selectMemberList();
        return list; // [{}, {}, {}, ...]
    }


    @ResponseBody
    @GetMapping(value="etc1.do", produces="application/json")
    public Map<String, Object> responseMapTest() {
       
        // 만일 응답할 데이터로 숫자, List, Dto가 있다는 가정
        Map<String, Object> map = new HashMap<>();
        map.put("no", 1);
        map.put("list", memService.selectMemberList());
        map.put("m", memService.selectMemberByNo(2));

        return map;

        /*
            {
                no: 1,
                list: [{}, {}, {}],
                m: {}
            }
        */

    }



	@ResponseBody
    @PostMapping(value="/etc2.do", produces="application/json")
    public void requestBodyTest(@RequestBody Map<String, Object> map) {
        logger.debug("map: {}", map);
        logger.debug("map>no: {}", map.get("no"));
        logger.debug("map>name: {}", map.get("name"));
        logger.debug("map>arr: {}", map.get("arr")); // list 객체가 나온다. 배열 형태로 출력된다. 
    }


}
