package com.algaworks.algamoney.api.service.exception;

public class LancamentoInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LancamentoInexistenteException() {
		super();
	}

	public LancamentoInexistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public LancamentoInexistenteException(String message) {
		super(message);
	}

	public LancamentoInexistenteException(Throwable cause) {
		super(cause);
	}
}