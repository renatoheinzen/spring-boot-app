package com.unisul.tcc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unisul.tcc.domain.Cidade;
import com.unisul.tcc.domain.Cliente;
import com.unisul.tcc.domain.Endereco;
import com.unisul.tcc.domain.enumns.TipoCliente;
import com.unisul.tcc.dto.ClienteDTO;
import com.unisul.tcc.dto.ClienteNewDTO;
import com.unisul.tcc.repositories.ClienteRepository;
import com.unisul.tcc.repositories.EnderecoRepository;
import com.unisul.tcc.services.exceptions.DataIntegrityException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
								"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) throws ObjectNotFoundException {
		Cliente newCliente = find(obj.getId());
		updateData(newCliente, obj);
		return repo.save(newCliente);
	}
	
	private void updateData (Cliente newCliente, Cliente cliente) {
		
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
		
	}
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {

			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
			
		}
		
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO (ClienteDTO cliente) {
		return new Cliente(cliente.getId(), cliente.getNome(), cliente.getEmail(), null, null);
	}
	
	public Cliente fromDTO (ClienteNewDTO cliente) {
		Cliente cli = new Cliente(null, cliente.getNome(), cliente.getEmail(), cliente.getCpfCnpj(), TipoCliente.toEnum(cliente.getTipo()));
		Cidade cid = new Cidade(cliente.getCidadeId(), null, null);
		Endereco end = new Endereco(null, cliente.getLogradouro(), cliente.getNumero(), cliente.getComplemento(), cliente.getBairro(), cliente.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(cliente.getTelefone1());
		if (cliente.getTelefone2()!=null) {
			cli.getTelefones().add(cliente.getTelefone2());
		}
		if (cliente.getTelefone3()!=null) {
			cli.getTelefones().add(cliente.getTelefone3());
		}
		return cli;
		
	}
	
}
