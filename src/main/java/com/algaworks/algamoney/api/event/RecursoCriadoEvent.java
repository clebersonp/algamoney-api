package com.algaworks.algamoney.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
	private Long codigo;
	private String path;

	public RecursoCriadoEvent(final Object source, final HttpServletResponse response, final Long codigo, final String path) {
		super(source);
		this.response = response;
		this.codigo = codigo;
		this.path = path;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getPath() {
		return path;
	}
}
