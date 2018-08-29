package com.algaworks.algamoney.api.service.exception;

public class PessoaInexistenteOuInativaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PessoaInexistenteOuInativaException() {
		super();
	}

	public PessoaInexistenteOuInativaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PessoaInexistenteOuInativaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PessoaInexistenteOuInativaException(String message) {
		super(message);
	}

	public PessoaInexistenteOuInativaException(Throwable cause) {
		super(cause);
	}
}