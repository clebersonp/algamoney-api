package com.algaworks.algamoney.api.resource;

import java.net.URI;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.categoriaRepository.findAll());
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> recuperar(@PathVariable(name = "codigo") final Long codigo) {
		final Categoria categoria = this.categoriaRepository.findOne(codigo);
		return ObjectUtils.allNotNull(categoria) ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> criar(@RequestBody final Categoria categoria) {
		final Categoria categoriaCriada = this.categoriaRepository.save(categoria);
		
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(categoriaCriada.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(categoriaCriada);		
	}
}