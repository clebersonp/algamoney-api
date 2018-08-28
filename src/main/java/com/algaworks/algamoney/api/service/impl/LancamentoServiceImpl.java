package com.algaworks.algamoney.api.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Override
	public Lancamento buscarPeloCodigo(final Long codigo) {
		final Optional<Lancamento> lancamentoOptional = Optional.ofNullable(this.lancamentoRepository.findOne(codigo));
		if (BooleanUtils.isNotTrue(lancamentoOptional.isPresent())) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoOptional.get();
	}
}