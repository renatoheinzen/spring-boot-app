package com.unisul.tcc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unisul.tcc.domain.Pagamento;
import com.unisul.tcc.repositories.PagamentoRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class PagamentoService {
	
	@Autowired
	private PagamentoRepository repo;
	
	public Pagamento find(Integer id) throws ObjectNotFoundException {
		Optional<Pagamento> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
								"Objeto não encontrado! Id: " + id + ", Tipo: " + Pagamento.class.getName()));
		}
	
}
