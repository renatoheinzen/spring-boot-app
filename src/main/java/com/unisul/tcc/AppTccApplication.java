package com.unisul.tcc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.unisul.tcc.domain.Categoria;
import com.unisul.tcc.repositories.CategoriaRepository;

@SpringBootApplication
public class AppTccApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AppTccApplication.class, args);
	}
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
	}
}
