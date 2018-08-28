package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Pessoa;

public interface PessoaService {
	Pessoa buscarPeloCodigo(Long codigo);
	
	Pessoa atualizar(Long codigo, Pessoa pessoa);

	void atualizarPropriedadeAtivo(Long codigo, Boolean ativo);
}
