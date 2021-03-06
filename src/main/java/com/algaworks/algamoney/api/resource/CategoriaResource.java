package com.algaworks.algamoney.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import com.algaworks.algamoney.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	// Cors. Ativar a requisicao java para dominios diferentes da API. Criado o filtro CorsFilter
	// @CrossOrigin(maxAge = 10, origins = { "http://localhost:8000" })
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.categoriaRepository.findAll());
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> recuperar(@PathVariable(name = "codigo") final Long codigo) {
		return ResponseEntity.ok(this.categoriaService.buscarPeloCodigo(codigo));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<?> criar(@Valid @RequestBody final Categoria categoria, HttpServletResponse response) {
		final Categoria categoriaCriada = this.categoriaRepository.save(categoria);
		// publica o evento para o listener capturar e utilizar
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo(), "/{codigo}"));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
	}
}