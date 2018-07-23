package com.unisul.tcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.unisul.tcc.services.S3Service;

@SpringBootApplication
public class AppTccApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(AppTccApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\Renato.Renato-PC\\Desktop\\orl_faces\\s1\\1.jpg");
		
	}
}
