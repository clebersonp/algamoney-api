package com.algaworks.algamoney.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.lancamentoRepository.findAll());
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscar(@PathVariable(name = "codigo") final Long codigo) {
		return ResponseEntity.ok(this.lancamentoService.buscarPeloCodigo(codigo));
	}
}
