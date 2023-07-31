package com.kh.slumber.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateResolverConfig {
    
	@Bean
    public ClassLoaderTemplateResolver mainResolver() {
        ClassLoaderTemplateResolver main = new ClassLoaderTemplateResolver();

        main.setPrefix("templates/");
        main.setSuffix(".html");
        main.setTemplateMode(TemplateMode.HTML);
        main.setCharacterEncoding("UTF-8");
        main.setOrder(1);
        main.setCacheable(false);
        main.setCheckExistence(true);

        return main;
    }

    @Bean
	public ClassLoaderTemplateResolver memberResolver() {
		ClassLoaderTemplateResolver member = new ClassLoaderTemplateResolver();
		
		member.setPrefix("templates/views/member/");
		member.setSuffix(".html");
		member.setTemplateMode(TemplateMode.HTML);
		member.setCharacterEncoding("UTF-8");
		member.setOrder(1);
		member.setCacheable(false);
		member.setCheckExistence(true);
		
		return member;
	}

    @Bean
	public ClassLoaderTemplateResolver marketResolver() {
		ClassLoaderTemplateResolver market = new ClassLoaderTemplateResolver();
		
		market.setPrefix("templates/views/market/");
		market.setSuffix(".html");
		market.setTemplateMode(TemplateMode.HTML);
		market.setCharacterEncoding("UTF-8");
		market.setOrder(1);
		market.setCacheable(false);
		market.setCheckExistence(true);
		
		return market;
	}

    @Bean
	public ClassLoaderTemplateResolver commonResolver() {
		ClassLoaderTemplateResolver common = new ClassLoaderTemplateResolver();
		
		common.setPrefix("templates/views/common/");
		common.setSuffix(".html");
		common.setTemplateMode(TemplateMode.HTML);
		common.setCharacterEncoding("UTF-8");
		common.setOrder(1);
		common.setCacheable(false);
		common.setCheckExistence(true);
		
		return common;
	}

	@Bean
	public ClassLoaderTemplateResolver adminResolver() {
		ClassLoaderTemplateResolver admin = new ClassLoaderTemplateResolver();
		
		admin.setPrefix("templates/views/admin/");
		admin.setSuffix(".html");
		admin.setTemplateMode(TemplateMode.HTML);
		admin.setCharacterEncoding("UTF-8");
		admin.setOrder(1);
		admin.setCacheable(false);
		admin.setCheckExistence(true);
		
		return admin;
	}

	//커뮤니티 담당 viewresolver
	@Bean
	public ClassLoaderTemplateResolver communityResolver() {
		ClassLoaderTemplateResolver dotComm = new ClassLoaderTemplateResolver();
		dotComm.setPrefix("templates/views/community/");
		dotComm.setSuffix(".html");
		dotComm.setTemplateMode(TemplateMode.HTML);
		dotComm.setCharacterEncoding("UTF-8");
		dotComm.setOrder(1);
		dotComm.setCacheable(false);
		dotComm.setCheckExistence(true);

		return dotComm;
	}

	@Bean
	public ClassLoaderTemplateResolver errorResolver() {
		ClassLoaderTemplateResolver error = new ClassLoaderTemplateResolver();

		error.setPrefix("templates/error/");
		error.setSuffix(".html");
		error.setTemplateMode(TemplateMode.HTML);
		error.setCharacterEncoding("UTF-8");
		error.setOrder(1);
		error.setCacheable(false);
		error.setCheckExistence(true);

		return error;
	}
	
}
