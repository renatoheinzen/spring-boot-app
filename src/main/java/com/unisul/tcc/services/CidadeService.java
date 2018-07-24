package com.unisul.tcc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unisul.tcc.domain.Cidade;
import com.unisul.tcc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findByEstado(Integer estado_id) {
		return repo.findCidades(estado_id);
	}

}
