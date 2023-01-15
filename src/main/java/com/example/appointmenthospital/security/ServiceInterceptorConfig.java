package com.example.appointmenthospital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ServiceInterceptorConfig implements WebMvcConfigurer {
	@Autowired
	private ServiceInterceptor serviceInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(serviceInterceptor)
		.addPathPatterns("/dentist-system/**")
		.excludePathPatterns("/dentist-system/auth/login")
		.excludePathPatterns("/dentist-system/auth/register");
	}

}
