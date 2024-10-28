package com.br.spring.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.spring.dto.MemberDto;
import com.br.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

	  private final MemberService memberService;
	  private final BCryptPasswordEncoder bcryptPwdEncoder;
	
	  
    @PostMapping("/signin.do")
    public void signin(MemberDto m, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException {
    	MemberDto loginUser = memberService.selectMember(m);

        // 로그인 성공시 => 세션에 회원정보를 담고, alert와 함께 메인 페이지로 이동
        // 로그인 실패시 => alert와 함께 기존에 보던 페이지 유지(아이디, 비번 입력하는 로그인 폼 유지. 작성하던 입력데이터도 그대로.)

        // script문을 응답데이터로 돌려줘서 흐름제어
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");

        if(loginUser != null) { // 로그인 성공
            session.setAttribute("loginUser", loginUser);
            out.println("alert('" + loginUser.getUserName() + "님 환영합니다~');");
            out.println("location.href = '" + request.getHeader("referer") + "';"); // 메인페이지가 아닌 이전에 보던 페이지로 이동
        }else { // 로그인 실패
            out.println("alert('로그인에 실패하였습니다. 아이디 및 비밀번호를 다시 확인해주세요.');");
            out.println("history.back();"); // modal이 띄워진 상태였으면 modal도 유지된다.
        }

        out.println("</script>");
    }
    
    
    @GetMapping("/singout.do")
    public String signout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }
    
    
    @GetMapping("/signup.do")
    public void signupPage() { } //WEB-INF/views/member/signup.jsp
    	
    @ResponseBody
    @GetMapping("/idcheck.do")
    public String idCheck(String checkId) {
    	return memberService.selectUserIdCount(checkId) == 0 ? "YYYY" : "NNNN";
    }
    
    
    @PostMapping("/insert.do")
    public String signup(MemberDto m, RedirectAttributes rdAttributes) {
    	
    	log.debug("암호화 전 member: {}", m);
    	
        m.setUserPwd( bcryptPwdEncoder.encode(m.getUserPwd()) );
        
        log.debug("암호화 후 member: {}", m);
        
        
        int result = memberService.insertMember(m);
        
        // 성공시 alert와 함께 메인페이지 이동
        // 실패시 alert와 함께 기존에 작업중이던 페이지 유지
        if(result > 0) {
        	rdAttributes.addFlashAttribute("alertMsg", "성공적으로 회원가입 되었습니다.");
        }else {
        	rdAttributes.addFlashAttribute("alertMsg", "회원가입에 실패하였습니다.");
        	rdAttributes.addFlashAttribute("historyBackYN", "Y");
        }
        
        return "redirect:/";
    }
    
    
    
    

    
}
