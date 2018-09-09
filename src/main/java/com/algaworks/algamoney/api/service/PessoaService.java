package com.algaworks.algamoney.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;

public interface PessoaService {
	Pessoa buscarPeloCodigo(Long codigo);
	
	Pessoa atualizar(Long codigo, Pessoa pessoa);

	void atualizarPropriedadeAtivo(Long codigo, Boolean ativo);

	Page<Pessoa> buscarPorFiltro(PessoaFilter filter, Pageable pageable);
}
