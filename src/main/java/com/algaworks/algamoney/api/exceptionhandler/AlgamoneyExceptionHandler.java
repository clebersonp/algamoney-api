package com.algaworks.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algamoney.api.service.exception.LancamentoInexistenteException;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final String mensagemUsuario = messageSource.getMessage("propriedade.invalida", null,
				LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ObjectUtils.allNotNull(ex.getCause()) ? ex.getCause().toString() : ex.toString();
		final List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final List<Erro> erros = this.criarErrosValidacaoCampos(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, status, request);
	}
	
	@ExceptionHandler(value = { EmptyResultDataAccessException.class })
	public ResponseEntity<?> handleEmptyResultDataAccessException(final EmptyResultDataAccessException ex, final WebRequest request) {
		final String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null,	LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ex.toString();
		final List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<?> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final WebRequest request) {
		final String mensagemUsuario = messageSource.getMessage("operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		final List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<?> handlePessoaInexistenteOuInativaException(final PessoaInexistenteOuInativaException ex) {
		final String mensagemUsuario = this.messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ex.toString();
		final List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ LancamentoInexistenteException.class })
	public ResponseEntity<?> handleLancamentoInexistenteOuInativaException(final LancamentoInexistenteException ex) {
		final String mensagemUsuario = this.messageSource.getMessage("lancamento.inexistente", null, LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ex.toString();
		final List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	private List<Erro> criarErrosValidacaoCampos(final BindingResult bindingResult) {
		final List<Erro> erros = new ArrayList<>();

		bindingResult.getFieldErrors().forEach(field -> {
			// String format = MessageFormat.format(field.getDefaultMessage(),
			// field.getField());
			final String mensagemUsuario = this.messageSource.getMessage(field, LocaleContextHolder.getLocale());
			final String mensagemDesenvolvedor = field.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		});

		return erros;
	}

	public static class Erro {
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

		@Override
		public String toString() {
			return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
		}
	}
}
