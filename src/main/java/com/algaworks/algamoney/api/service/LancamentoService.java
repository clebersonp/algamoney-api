package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Lancamento;

public interface LancamentoService {
	Lancamento buscarPeloCodigo(Long codigo);
}
