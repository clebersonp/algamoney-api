package com.algaworks.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.service.LancamentoService;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody final Lancamento lancamento, HttpServletResponse response) {
		final Lancamento lancamentoSalvo = this.lancamentoService.salvar(lancamento);
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo(), "/{codigo}"));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.lancamentoRepository.findAll());
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscar(@PathVariable(name = "codigo") final Long codigo) {
		return ResponseEntity.ok(this.lancamentoService.buscarPeloCodigo(codigo));
	}
	
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<?> handlePessoaInexistenteOuInativaException(final PessoaInexistenteOuInativaException ex) {
		final String mensagemUsuario = this.messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ex.toString();
		final List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}