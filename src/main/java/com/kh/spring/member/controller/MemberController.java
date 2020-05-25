package com.kh.spring.member.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kh.spring.member.model.exception.MemberException;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@SessionAttributes("loginUser")
@Controller
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
//  의존성 주입(DI) : 만드 객체를 가져올 수 있는 것
	@Autowired
	private MemberService mService;
	
	@Autowired
	private BCryptPasswordEncoder BCryptPasswordEncoder; 
	/************* 파라미터 전송 받는 방법 **************/
	// 1. HttpServletRequest를 통해 전송 받기 (JSP/Servlet 방식)
//	@RequestMapping(value = "login.me", method = RequestMethod.POST)
//	public void memberLogin(HttpServletRequest request) {
//		String id = request.getParameter("id");
//		String pwd = request.getParameter("pwd");
//		System.out.println("아이디 :" + id + "패스워드 : " + pwd);
//	}
	
	
	// 2. @RequestParam 어노테이션 방식
//	@RequestMapping(value="login.me", method = RequestMethod.GET)
////	public void memberLogin(@RequestParam("id") String userId, @RequestParam("pwd") String userPwd) {
//	public void memberLogin(@RequestParam(value = "idpp", required = false, defaultValue="idpp" ) String userId, @RequestParam("pwdpp") String userPwd) {
//		System.out.println("아이디 :" + userId + " 패스워드 : " + userPwd);
//	}
	
	
	// 3. @RequestParam 생략
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public void memberLogin(String id, String pwd) {
//		System.out.println("아이디 :" + id + " 패스워드 : " + pwd);
//	}
	
	
	// 4. @ModelAttribute 어노테이션 방식
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public void memberLogin(@ModelAttribute Member m) {
//		System.out.println(m);
//	}
	
	// 5. @ModelAttribute 생략
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public String memberLogin(Member m, HttpSession session) {
//		System.out.println(m);
//		
////		MemberService mService = new MemberServiceImpl();
//		System.out.println(mService.hashCode());
//		
//		Member loginUser = mService.memberLogin(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser",loginUser);
//		}
//		
//		return "redirect:home.do";
//	}
	
	/************* 요청 후 전달하고자 하는 데이터가 있을 경우  **************/
	// 1. Model 객체를 이용하는 방법
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public String memberLogin(Member m, HttpSession session, Model model) {
//		System.out.println(m);
//		
////		MemberService mService = new MemberServiceImpl();
//		System.out.println(mService.hashCode());
//		
//		Member loginUser = mService.memberLogin(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser",loginUser);
//			return "redirect:home.do";
//		} else {
//			model.addAttribute("message", "로그인에 실패하였습니다.");
//			return "../common/errorPage";
//		}
//	}
	
//	 //2.ModelAndView 객체 사용하는 방법
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public ModelAndView memberLogin(Member m, HttpSession session, ModelAndView mv) {
//		System.out.println(m);
//		
////		MemberService mService = new MemberServiceImpl();
//		System.out.println(mService.hashCode());
//		
//		Member loginUser = mService.memberLogin(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser",loginUser);
//			mv.setViewName("redirect:home.do");
//		} else {
//			mv.addObject("message", "로그인에 실패했습니다.");
//			mv.setViewName("../common/errorPage");
//		}
//		
//		return mv;
//	}
	
	// 3. session에 저장할 때 @SessionAttributes 사용하기: Model이랑 같이 사용 돼야함
	//model에 attribute가 추가될 때 자동으로 키 값을 찾아 세션에 동록하는 어노테이션
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public String memberLogin(Member m,  Model model) {
//		System.out.println(m);
//		
////		MemberService mService = new MemberServiceImpl();
//		System.out.println(mService.hashCode());
//		
//		Member loginUser = mService.memberLogin(m);
//		
//		if(loginUser != null) {
//			model.addAttribute("loginUser", loginUser);
//			return "redirect:home.do";
//		} else {
////			model.addAttribute("message", "로그인에 실패했습니다.");
////			return "../common/errorPage";
//			throw new MemberException("로그인에 실패했습니다.");
//		}
//	}
	
	//로그아웃 컨트롤러 1
//	@RequestMapping("logout.me")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		
//		return "redirect:home.do";
//	}
	
	// 로그아웃 컨트롤러 2
	@RequestMapping("logout.me")
	public String logout(SessionStatus status) {
		status.setComplete();
		
		return "redirect:home.do";
	}
	
	//회원가입 페이지 이동
	@RequestMapping("enrollView.me")
	public String enrollView() {
		
		logger.debug("회원등록페이지 로그");
		
		return "memberJoin";
	}
	
	//회원가입
	@RequestMapping("minsert.me")
	public String memberInsert(@ModelAttribute Member m, @RequestParam("post") String post,
														 @RequestParam("address1") String address1,
														 @RequestParam("address2") String address2) {
		m.setAddress(post + "/" + address1 + "/" + address2);
		System.out.println(m);
		
		//bcryt 암호화 방식
		String encPwd = BCryptPasswordEncoder.encode(m.getPwd());
		System.out.println(encPwd);
		m.setPwd(encPwd);
		
		int result = mService.insertMember(m);
		
		if(result > 0) {
			return "redirect:home.do";
		} else {
			throw new MemberException("회원가입에 실패했습니다.");
		}
	}
	
	//암호화 후 로그인
	@RequestMapping(value="login.me", method=RequestMethod.POST)
	public String memberLogin(Member m,  Model model) {
		System.out.println(m);
		
//		MemberService mService = new MemberServiceImpl();
//		System.out.println(mService.hashCode());
		
		Member loginUser = mService.memberLogin(m);
		
		if(BCryptPasswordEncoder.matches(m.getPwd(), loginUser.getPwd())){
			model.addAttribute("loginUser", loginUser);
			logger.info(loginUser.getId());
			return "redirect:home.do";
		}else {
			throw new MemberException("로그인에 실패했습니다.");
		}
	}	
	//회원 정보보기 페이지 이동
	@RequestMapping("myinfo.me")
	public String myInfoView() {
		return "mypage";
	}
	//정보 수정 페이지 이동
	@RequestMapping("mupdateView.me")
	public String updateFormView() {
		return "memberUpdateForm";
	}
	//회원 정보 수정
	@RequestMapping("mupdate.me")
	public String memberUpdate(@ModelAttribute Member m, @RequestParam("post") String post,
														 @RequestParam("address1") String address1,
														 @RequestParam("address2") String address2,
														 Model model) {
		
		m.setAddress(post + "/" + address1 + "/" + address2);
		int result = mService.updateMember(m);
		
		if (result > 0) {
			model.addAttribute("loginUser",m);
			return "mypage";
		}else {
			throw new MemberException("회원정보 수정에 실패했습니다.");
		}
		
	}
	
	//비밀번호 수정 페이지 이동
	@RequestMapping("mpwdUpdateView.me")
	public String mpwdUpdateFormView() {
		return "memberPwdUpdate";
	}
	
	//비밀번호 수정
	@RequestMapping("mPwdUpdate.me")
	public String memeberPwdUpdate(HttpSession session, @RequestParam("pwd") String pwd, @RequestParam("newPwd1") String newPwd, Model model) {
		String userId = ((Member)session.getAttribute("loginUser")).getId();
		String userpwd = ((Member)session.getAttribute("loginUser")).getPwd();
		String encNewPwd = BCryptPasswordEncoder.encode(newPwd);
		System.out.println(session.getAttribute("loginUser"));
		System.out.println("con"+userpwd);
		
		int result = 0;
		
		if(BCryptPasswordEncoder.matches(pwd, userpwd)) {
			((Member)session.getAttribute("loginUser")).setPwd(encNewPwd);
			result = mService.pwdUpdateMember(userId,encNewPwd);
			if(result>0) {
				return "mypage";
			}else {
				throw new MemberException("비밀번호 수정에 실패했습니다.");
			}
		}else {
			throw new MemberException("비밀번호가 일치하지 않습니다.");
		}
		
		
		
	}
	
	@RequestMapping("mdelete.me")
	public String memberDelete(HttpSession session, SessionStatus status) {
		String userId = ((Member)session.getAttribute("loginUser")).getId();
		String userpwd = ((Member)session.getAttribute("loginUser")).getPwd();
		
		int result = mService.deleteMember(userId,userpwd);
		
		if(result > 0) {
			
			status.setComplete();
			return "redirect:home.do";
		}else {
			throw new MemberException("회원 탈퇴 실패");
		}
	}
	
	
	@RequestMapping("dupid.me")
	public void idDuplicateCheck(@RequestParam("id") String userId, HttpServletResponse response) {
		System.out.println(userId);
		int result = mService.duplicateId(userId);
		
		boolean isUsable = result == 0 ? true : false;

		try {
			response.getWriter().print(isUsable);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	 
}

