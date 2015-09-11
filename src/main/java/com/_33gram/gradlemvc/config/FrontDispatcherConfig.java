package com._33gram.gradlemvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration		// <beans></beans>
@EnableWebMvc 		// <mvc:annotation-driven />
@ComponentScan(basePackages = { "com._33gram.gradlemvc.controller" }) // <context:component-scan base-package="" />
public class FrontDispatcherConfig extends WebMvcConfigurerAdapter {
	
	// <resources mapping="/resources/**" location="/resources/">에 해당됨.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		//registry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico/");
	}
	
	// <mvc:default-servlet-handler>에 해당됨.
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		configurer.enable();
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(100000000);
		return multipartResolver;
	}
	
	@Bean
	public InternalResourceViewResolver configureViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

}
