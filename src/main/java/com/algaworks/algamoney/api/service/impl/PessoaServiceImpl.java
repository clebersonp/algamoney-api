package com.algaworks.algamoney.api.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;
import com.algaworks.algamoney.api.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		final Pessoa pessoaSalva = this.buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return this.pessoaRepository.save(pessoaSalva);
	}

	@Override
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		final Pessoa pessoaSalva = this.buscarPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		this.pessoaRepository.save(pessoaSalva);
	}

	@Override
	public Pessoa buscarPeloCodigo(Long codigo) {
		final Optional<Pessoa> pessoaOptional = Optional.ofNullable(this.pessoaRepository.findOne(codigo));
		
		if (BooleanUtils.isNotTrue(pessoaOptional.isPresent())) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaOptional.get();
	}

	@Override
	public Page<Pessoa> buscarPorFiltro(final PessoaFilter filter, final Pageable pageable) {
		return this.pessoaRepository.pesquisar(filter, pageable);
	}
}
