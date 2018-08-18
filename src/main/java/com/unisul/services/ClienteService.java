package com.unisul.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.unisul.domain.Cidade;
import com.unisul.domain.Cliente;
import com.unisul.domain.Endereco;
import com.unisul.domain.enumns.Perfil;
import com.unisul.domain.enumns.TipoCliente;
import com.unisul.dto.ClienteDTO;
import com.unisul.dto.ClienteNewDTO;
import com.unisul.repositories.ClienteRepository;
import com.unisul.repositories.EnderecoRepository;
import com.unisul.security.UserSS;
import com.unisul.services.exceptions.AuthorizationException;
import com.unisul.services.exceptions.DataIntegrityException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.cliente.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
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

			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
			
		}
		
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Cliente findByEmail(String email) throws ObjectNotFoundException {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO (ClienteDTO cliente) {
		return new Cliente(cliente.getId(), cliente.getNome(), cliente.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO (ClienteNewDTO cliente) {
		Cliente cli = new Cliente(null, cliente.getNome(), cliente.getEmail(), cliente.getCpfCnpj(), TipoCliente.toEnum(cliente.getTipo()), passwordEncoder.encode(cliente.getSenha()));
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
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
	
}
