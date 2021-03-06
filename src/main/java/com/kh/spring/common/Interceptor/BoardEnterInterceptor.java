package com.kh.spring.common.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.spring.member.model.vo.Member;

public class BoardEnterInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			request.setAttribute("msg", "로그인 후 이용하세요");
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
			
			return false;
		} 
		
		return super.preHandle(request, response, handler);
	}

}
