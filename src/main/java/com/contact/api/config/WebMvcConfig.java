package com.contact.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
		AntPathMatcher matcher = new AntPathMatcher();  
	    matcher.setCaseSensitive(false); 	// To enable case insensitive URL
	    
	    configurer.setPathMatcher(matcher);
		configurer.setUseSuffixPatternMatch(false); //  To allow URL with *.* 
		configurer.setUseRegisteredSuffixPatternMatch(true);
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }
}
