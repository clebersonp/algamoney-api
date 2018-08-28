package com.algaworks.algamoney.api.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
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
	public ResponseEntity<?> criar(@Valid @RequestBody final Pessoa pessoa, HttpServletResponse response) {
		final Pessoa pessoaCriada = this.pessoaRepository.save(pessoa);
		// publica o evento para o listener usar
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getCodigo(), "/{codigo}"));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
	}
	
	@DeleteMapping(path = "/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable(name = "codigo") final Long codigo) {
		this.pessoaRepository.delete(codigo);
	}
}