package com.unisul.tcc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.unisul.tcc.domain.Cliente;
import com.unisul.tcc.dto.ClienteDTO;
import com.unisul.tcc.dto.ClienteNewDTO;
import com.unisul.tcc.services.ClienteService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value= "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) throws ObjectNotFoundException {

		Cliente obj = service.find(id);
		return ResponseEntity.ok(obj);

	}
	
	@PreAuthorize("hasAnyRole(ADMIN)")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() throws ObjectNotFoundException {

		List<Cliente> categorias = service.findAll();
		List<ClienteDTO> categoriasDTO = categorias.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok(categoriasDTO);

	}
	
	@PreAuthorize("hasAnyRole(ADMIN)")
	@RequestMapping(value="/page",  method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC")String direction)
			throws ObjectNotFoundException {

		Page<Cliente> categorias = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> categoriasDTO = categorias.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok(categoriasDTO);

	}
	
	@PreAuthorize("hasAnyRole(ADMIN)")
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) throws ObjectNotFoundException {
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole(ADMIN)")
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody ClienteNewDTO objDTO) {
		Cliente obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				  .path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture (@RequestParam(name="file") MultipartFile file) {
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}

}
