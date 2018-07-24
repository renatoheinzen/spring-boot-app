package com.unisul.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.unisul.services.DBService;
import com.unisul.services.EmailService;
import com.unisul.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		dbService.InstantiateDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailservice () {
		return new MockEmailService();
	}

}
