package com.algaworks.algamoney.api.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Optional<Pessoa> pessoaOptional = Optional.ofNullable(this.pessoaRepository.findOne(codigo));
		
		if (BooleanUtils.isNotTrue(pessoaOptional.isPresent())) {
			throw new EmptyResultDataAccessException(1);
		}
		final Pessoa pessoaSalva = pessoaOptional.get();
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		return this.pessoaRepository.save(pessoaSalva);
	}
}
