package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAspect1 {
	
	private Logger logger = LoggerFactory.getLogger(LoggerAspect1.class);
	
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		// 현재 AOP가 적용되는 메소드의 정보를 가져옴
		Signature signature = joinPoint.getSignature();
		logger.debug("signature : " + signature);
		
		String type = signature.getDeclaringTypeName(); // 클래스 풀네임
		String methodName = signature.getName();
		logger.debug("type : " + type);
		logger.debug("methodName : " + methodName);
		
		String componentName = "";
		if(type.indexOf("Controller") > -1) {
			componentName = "Controller  \t:  ";
		}else if(type.indexOf("Service") > -1) {
			componentName = "ServiceImpl  \t:  ";
		}else if(type.indexOf("DAO") > -1) {
			componentName = "DAO  \t:  ";
		}
		
		logger.debug("[Before]" + componentName + type + "." + methodName + "()");
		Object obj = joinPoint.proceed();
		logger.debug("[After]" + componentName + type + "." + methodName + "()");
		
		return obj;
		
		//다음 advice나 target으로 넘어갈 수 있게 진행시킴
//		return joinPoint.proceed();
	}
}
