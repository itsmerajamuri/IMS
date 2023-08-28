package com.ims.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
@Configuration
@PropertySource(value = "classpath:error.properties")
public class ErrorConfig {

	@Value("${SUCCESS_CODE}")
	public String SUCCESS_CODE;
	
	@Value("${INVALID_USER_ID}")
	public String INVALID_USER_ID;

	@Value("${INVALID_PASSWORD_ID}")
	public String INVALID_PASSWORD_ID;

	@Value("${INVALID_USER_TYPES}")
	public String INVALID_USER_TYPES;
	
	@Value("${Admin_NOTAVAILABLE}")
	public String Admin_NOTAVAILABLE;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
