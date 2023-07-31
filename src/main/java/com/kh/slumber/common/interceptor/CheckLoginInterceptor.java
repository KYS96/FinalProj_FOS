package com.kh.slumber.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.slumber.member.model.vo.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckLoginInterceptor implements HandlerInterceptor{
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
        Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("<script>location.href='errorPage?type=1';</script>");
			return false;
		} else if(loginUser.getMemberNickname().equals("관리자")) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("<script>location.href='errorPage?type=3';</script>");
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
