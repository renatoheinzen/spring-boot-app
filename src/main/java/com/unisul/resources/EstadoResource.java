package com.unisul.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unisul.domain.Cidade;
import com.unisul.domain.Estado;
import com.unisul.dto.CidadeDTO;
import com.unisul.dto.EstadoDTO;
import com.unisul.services.CidadeService;
import com.unisul.services.EstadoService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value= "/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() throws ObjectNotFoundException {

		List<Estado> estados = service.findAll();
		List<EstadoDTO> estadosDTO = estados.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(estadosDTO);

	}
	
	@RequestMapping(value="/{estadoId}/cidades",method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) throws ObjectNotFoundException {

		List<Cidade> cidades = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> cidadesDTO = cidades.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadesDTO);

	}

}
