package com.algaworks.algamoney.api.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.LancamentoService;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public Lancamento buscarPeloCodigo(final Long codigo) {
		final Optional<Lancamento> lancamentoOptional = Optional.ofNullable(this.lancamentoRepository.findOne(codigo));
		if (BooleanUtils.isNotTrue(lancamentoOptional.isPresent())) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoOptional.get();
	}

	@Override
	public Lancamento salvar(final Lancamento lancamento) {
		Optional<Pessoa> optional = Optional.ofNullable(this.pessoaRepository.findOne(lancamento.getPessoa().getCodigo()));
		if (BooleanUtils.isNotTrue(optional.isPresent()) || optional.get().isInativa()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}
}