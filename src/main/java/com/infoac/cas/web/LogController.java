package com.infoac.cas.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author zp
 *日志列表
 */
@CrossOrigin
@RestController
@RequestMapping("/log")
public class LogController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/*
	 * 日志页面得展示
	 */
	@RequestMapping(value = "/log")
	public ModelAndView loginInfoac(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reel/record");
		return mv;
	}
}
