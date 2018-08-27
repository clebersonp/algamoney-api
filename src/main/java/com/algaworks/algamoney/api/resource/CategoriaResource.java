package com.algaworks.algamoney.api.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.categoriaRepository.findAll());
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> recuperar(@PathVariable(name = "codigo") final Long codigo) {
		final Optional<Categoria> categoria = Optional.ofNullable(this.categoriaRepository.findOne(codigo));
		return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody final Categoria categoria, HttpServletResponse response) {
		final Categoria categoriaCriada = this.categoriaRepository.save(categoria);
		// publica o evento para o listener capturar e utilizar
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo(), "/{codigo}"));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
	}
}