package com.kh.slumber.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.slumber.member.model.vo.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckAdminInterceptor implements HandlerInterceptor {
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		Member loginUser = (Member)request.getSession().getAttribute("loginUser");
		
		if(loginUser == null || !loginUser.getMemberNickname().equals("관리자")) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("<script>location.href='errorPage?type=2';</script>");
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}