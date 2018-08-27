package com.algaworks.algamoney.api.resource;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.pessoaRepository.findAll());
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> recuperar(@PathVariable(name = "codigo") final Long codigo) {
		final Optional<Pessoa> pessoa = Optional.ofNullable(this.pessoaRepository.findOne(codigo));
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody final Pessoa pessoa) {
		final Pessoa pessoaCriada = this.pessoaRepository.save(pessoa);
		
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(pessoa.getCodigo()).toUri();
		return ResponseEntity.created(uri).body(pessoaCriada);
	}
}