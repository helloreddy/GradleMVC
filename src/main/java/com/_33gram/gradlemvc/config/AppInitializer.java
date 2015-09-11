package com._33gram.gradlemvc.config;

import java.util.Arrays;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com._33gram.gradlemvc.common.constant.Log;
import com._33gram.gradlemvc.config.DefaultProfiles.Profile;
import com._33gram.gradlemvc.util.ContextPathHolder;

public class AppInitializer implements WebApplicationInitializer {
	
    private ConfigurableEnvironment environment;
    private ServletContext servletContext;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	this.servletContext = servletContext;
    	setContextPathHolder();
    	setEnvironment(getRootContext());
    	setServletDispatchers();
    	setFilters();
    }
    
    private void setServletDispatchers() throws ServletException {
    	setFrontServletContext();
    }
    
    private void setFrontServletContext() throws ServletException {
        AnnotationConfigWebApplicationContext frontContext = new AnnotationConfigWebApplicationContext();
        frontContext.register(FrontDispatcherConfig.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("frontServlet", new DispatcherServlet(frontContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
    
    private void setEnvironment(AnnotationConfigWebApplicationContext context) {
        DefaultProfiles prop = new DefaultProfiles(servletContext);
        environment = context.getEnvironment();
        environment.setDefaultProfiles(prop.get(Profile.APP_TYPE), prop.get(Profile.DB_FRAMEWORK));
        Log.DEV_LOGGER.info("Start 'BEEVER' Service!!...Profiles::{}", getActiveProfiles(environment));
    }
    
    private String getActiveProfiles(ConfigurableEnvironment environment) {
        if (environment.getActiveProfiles() != null && environment.getActiveProfiles().length > 0) {
            return Arrays.toString(environment.getActiveProfiles());
        }
        return Arrays.toString(environment.getDefaultProfiles());
    }
    
    private void setContextPathHolder() {
        ContextPathHolder.setContextPath(servletContext.getContextPath());
    }
    
    private AnnotationConfigWebApplicationContext getRootContext() {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        return rootContext;
    }
    
    private void setFilters() throws ServletException {
    	//setAAAFilter();
    	setCharacterEncodingFilter();
    	setHttpMethodFilter();
    	//setHibernateSessionFilter();
    }
	
    private void setCharacterEncodingFilter() throws ServletException {
        FilterRegistration charEncodingFilterReg = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        charEncodingFilterReg.setInitParameter("encoding", "UTF-8");
        charEncodingFilterReg.setInitParameter("forceEncoding", "true");
        charEncodingFilterReg.addMappingForUrlPatterns(null, false, "/*");
    }
    
    private void setHttpMethodFilter() throws ServletException {
    	FilterRegistration charEncodingFilterReg = servletContext.addFilter("HiddenHttpMethodFilter", HiddenHttpMethodFilter.class);
    	charEncodingFilterReg.addMappingForUrlPatterns(null, false, "/*");
    }

//	@Override
//	protected Class<?>[] getRootConfigClasses() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected Class<?>[] getServletConfigClasses() {
//		return new Class<?>[] { AppConfig.class };
//	}
//
//	@Override
//	protected Filter[] getServletFilters() {
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//
//		return new Filter[] { characterEncodingFilter };
//	}
//
//	@Override
//	protected String[] getServletMappings() {
//		return new String[] { "/" };
//	}

}
