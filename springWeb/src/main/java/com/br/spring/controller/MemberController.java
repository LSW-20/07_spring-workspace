package com.br.spring.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.spring.dto.MemberDto;
import com.br.spring.service.MemberService;
import com.br.spring.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

	  private final MemberService memberService;
	  private final BCryptPasswordEncoder bcryptPwdEncoder;
	  private final FileUtil fileUtil;
	
	  
    @PostMapping("/signin.do")
    public void signin(MemberDto m, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException {
    	MemberDto loginUser = memberService.selectMember(m);

        // 로그인 성공시 => 세션에 회원정보를 담고, alert와 함께 메인 페이지로 이동
        // 로그인 실패시 => alert와 함께 기존에 보던 페이지 유지(아이디, 비번 입력하는 로그인 폼 유지. 작성하던 입력데이터도 그대로.)

        // script문을 응답데이터로 돌려줘서 흐름제어
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");

        if(loginUser != null && bcryptPwdEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { // 로그인 성공
            session.setAttribute("loginUser", loginUser);
            out.println("alert('" + loginUser.getUserName() + "님 환영합니다~');");
            out.println("location.href = '" + request.getHeader("referer") + "';"); // 메인페이지가 아닌 이전에 보던 페이지로 이동
        }else { // 로그인 실패
            out.println("alert('로그인에 실패하였습니다. 아이디 및 비밀번호를 다시 확인해주세요.');");
            out.println("history.back();"); // modal이 띄워진 상태였으면 modal도 유지된다.
        }

        out.println("</script>");
        
        log.debug("유저네임 { }", session.getAttribute("loginUser"));
        log.debug("유저네임: {}", ((MemberDto) session.getAttribute("loginUser")).getUserName());
    }
    
    
    @GetMapping("/signout.do")
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
    
    
    
    @GetMapping("/myinfo.do")
    public void myinfoPage() {}
    
    
    @PostMapping("/update.do")
    public String modify(MemberDto m
    				   , RedirectAttributes rdAttributes
    				   , HttpSession session) {
    	
    	int result = memberService.updateMember(m);
    	
    	if(result > 0) {
    		session.setAttribute("loginUser", memberService.selectMember(m));// 동일한 키값으로 담으면 덮어씌워진다.
    		rdAttributes.addFlashAttribute("alertMsg", "성공적으로 정보수정 되었습니다.");
    	}else {
    		rdAttributes.addFlashAttribute("alertMsg", "정보수정에 실패하였습니다.");   		
    	}
    	
    	return "redirect:/member/myinfo.do";
    }
    
    
    @ResponseBody
    @PostMapping("/updateProfile.do")
    public String modifyProfile(MultipartFile uploadFile, HttpSession session) {
    	
    	// 현재 로그인한 회원정보
    	MemberDto loginUser = (MemberDto)session.getAttribute("loginUser");
    	
    	// 현재 로그인한 회원의 기존 프로필 url (불필요한 파일은 삭제처리하는 것이 좋다)
    	String originalProfileURL = loginUser.getProfileURL();
    	
    	// 변경요청한 프로필 파일 업로드
    	Map<String, String> map = fileUtil.fileupload(uploadFile, "profile");
    	
    	// 현재 로그인한 회원 객체의 profileURL 필드값을 새로운 프로필 이미지 경로로 수정
    	String newURL = map.get("filePath") + "/" + map.get("filesystemName"); // 새로 저장된 프로필 이미지의 url 주소.
    	loginUser.setProfileURL(newURL);
    	
    	// db에 기록하기 위해 service 호출
    	int result = memberService.updateProfileImg(loginUser);
    	
    	if(result > 0) {
    		// 성공시 기존 프로필이 존재했다면 파일을 삭제한다.
    		if(originalProfileURL != null) {
    			new File(originalProfileURL).delete();
    		}
    		return "SUCCESS";
    		
    	}else {
    		// 실패시 변경요청시 전달된 파일을 삭제한다.
    		new File(loginUser.getProfileURL()).delete();
    		loginUser.setProfileURL(originalProfileURL); // 기존걸로 다시 변경
    		return "FAIL";
    		
    	}
    }
    
    
    
    @PostMapping("/resign.do")
    public String resign(String userPwd 			// 사용자가 입력한 비밀번호값(평문)이 담긴다.
    				   , HttpSession session 
    				   , RedirectAttributes rdAttributes) {
    	
    	MemberDto loginUser = (MemberDto)session.getAttribute("loginUser"); // 로그인 유저에 암호문이 담겨있다. 
    	
    	if(bcryptPwdEncoder.matches(userPwd, loginUser.getUserPwd())) { // 비밀번호를 맞게 입력한 경우
    		
    		int result = memberService.deleteMember(loginUser.getUserId());
    		rdAttributes.addFlashAttribute("alertMsg", "성공적으로 탈퇴되었습니다. 그동안 이용해주셔서 감사합니다.");
    		session.invalidate(); // 세션만료시키기
    		
    	}else { // 비밀번호를 틀린 경우
    		
    		rdAttributes.addFlashAttribute("alertMsg", "비밀번호가 틀렸습니다. 다시 입력해주세요.");
    		rdAttributes.addFlashAttribute("historyBackYN", "Y");  // modal이 떠있는 상태로 보이게끔
    		
    	}
    	
    	return "redirect:/"; // 메인페이지 url 재요청 (비밀번호를 틀린 경우에는 실행되지 않는 코드)
    }
    
    
    
    @PostMapping("/updatePwd.do")
    public String updatePwd(String userPwd 		// 사용자가 입력한 현재 비밀번호(평문)
    					  , String updatePwd 	// 사용자가 입력한 변경할 비밀번호(평문)	
    					  , RedirectAttributes rdAttributes // redirect 후에도 응답데이터를 유지하기 위해
    					  , HttpSession session) { // 현재 로그인한 유저의 비밀번호(암호문)을 뽑기 위해
    	
    	
    	MemberDto loginUser = (MemberDto)session.getAttribute("loginUser"); // 현재 로그인한 유저의 비밀번호(암호문)
    	
    	Map<String, String> s = new HashMap<>();
    	s.put("userId", loginUser.getUserId());
    	s.put("updatePwd", bcryptPwdEncoder.encode(updatePwd) );
    	
    	
    	if(bcryptPwdEncoder.matches(userPwd, loginUser.getUserPwd())) { // 비밀번호를 맞게 입력한 경우
    		int result = memberService.updatePwd(s);
    		session.setAttribute("loginUser", memberService.selectMember(loginUser));// 동일한 키값으로 담으면 덮어씌워진다.
    		rdAttributes.addFlashAttribute("alertMsg", "비밀번호 변경에 성공했습니다.");
    		
    	}else { // 비밀번호를 틀린 경우
    		rdAttributes.addFlashAttribute("alertMsg", "비밀번호가 틀렸습니다.");
    	}
    		
    	
    	return "redirect:/member/myinfo.do"; // 성공이든 실패든 마이페이지 url 재요청 
    }
    
    
    
}
