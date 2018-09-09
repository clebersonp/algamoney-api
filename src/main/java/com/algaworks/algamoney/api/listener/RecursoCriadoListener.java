package com.algaworks.algamoney.api.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(final RecursoCriadoEvent event) {
		final Long codigo = event.getCodigo();
		final String path = event.getPath();
		final HttpServletResponse response = event.getResponse();
		
		this.criarHeaderLocation(codigo, path, response);
	}

	private void criarHeaderLocation(final Long codigo, final String path, final HttpServletResponse response) {
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(path).buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toString());
	}
}