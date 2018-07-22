package com.unisul.tcc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.unisul.tcc.services.DBService;
import com.unisul.tcc.services.EmailService;
import com.unisul.tcc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		if (!"create".equals(strategy)) {
			return false;
		}
		dbService.InstantiateDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailservice () {
		return new SmtpEmailService();
	}

}
