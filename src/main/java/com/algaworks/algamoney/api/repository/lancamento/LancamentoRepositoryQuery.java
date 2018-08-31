package com.algaworks.algamoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable);
}