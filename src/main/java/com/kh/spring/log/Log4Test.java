package com.kh.spring.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4Test {
	
	private Logger logger = LoggerFactory.getLogger(Log4Test.class);
	
	public static void main(String[] args) {
		new Log4Test().test();
	}
	
	public void test() {
		logger.trace("trace 로그");
		logger.debug("debug 로그");
		logger.info("info 로그");
		logger.warn("warn 로그");
		logger.error("error 로그");
	}
}
