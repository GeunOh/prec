package com.kh.spring.common.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TestInterceptor extends HandlerInterceptorAdapter{
	
	private Logger logger = LoggerFactory.getLogger(TestInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// DispatcherServlet이 Controller를 호출하기 전 수행
		
		logger.debug("========== START ===========");
		logger.debug(request.getRequestURI());
		
		
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// Controller에서 DispatcherServlet으로 리턴되는 순간 수행
		
		logger.debug("========== View ===========");
		
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 모든 뷰에서 최종 결과를 생성하는 일을 포함한 모든 작업이 완료된 후
		
		logger.debug("========== END ===========");
		
		super.afterCompletion(request, response, handler, ex);
	}
}
