package com.kh.todoApi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.todoApi.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private final LoginInterceptor loginInterceptor;
	public WebConfig(LoginInterceptor loginInterceptor) {
		this.loginInterceptor = loginInterceptor;
	}

	@Value("${client.origins}")
	private String origins;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// CorsRegistry => 전역으로 CORS 설정 가능

		registry.addMapping("/**")		// 어떤 경로(주소)에 CORS를 허용할 것인지 설정
				.allowedOrigins(origins)	// 허용할 출처(origin) 설정
				.allowedMethods("GET", "POST", "PUT", "DELETE")	// 허용할 요청 방식(method) 설정
				.allowedHeaders("*")	// 허용할 요청 헤더 설정
				.allowCredentials(true);	// 쿠키, 인증정보 포함 여부 설정 (Y/N)
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 위에 생성자 주입 하였음!!
		registry.addInterceptor(loginInterceptor).addPathPatterns("/todo/**"); // *을 두개를 작성해야 url에서는 *하나로 적용됨!! (파일기준에서는 *로 작성)
														// todo로 시작하는 path들은 interceptor 적용
	}

	
	
}