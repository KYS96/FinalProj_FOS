package com.kh.slumber.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.slumber.common.interceptor.CheckAdminInterceptor;
import com.kh.slumber.common.interceptor.CheckLoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new CheckAdminInterceptor())
        .addPathPatterns("/*.admin", "/faqWrite.faq", "/faqUpdate.faq", "/faqDelete.faq", "/noticeWrite.no",
            "/goNoticeUpdatePage.no", "/noticeDelete.no");

    registry.addInterceptor(new CheckLoginInterceptor())
        .addPathPatterns("/*.me")
        .excludePathPatterns("/loginView.me", "/login.me", "/findPasswordView.me", "/findIdView.me", "/enroll.me",
            "/adminLogin.me", "/sendCode.me", "/enrollMember.me", "/kakaoEnroll.me", "/kakaoLogin.me", "/findId.me",
            "/findPassword.me", "/changePassword.me", "/checkId.me", "/checkNickname.me", "/logout.me");
  }
}
