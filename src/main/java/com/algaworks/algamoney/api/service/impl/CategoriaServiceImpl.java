package com.algaworks.algamoney.api.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import com.algaworks.algamoney.api.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public Categoria buscarPeloCodigo(Long codigo) {
		final Optional<Categoria> categoriaOptional = Optional.ofNullable(this.categoriaRepository.findOne(codigo));
		
		if (BooleanUtils.isNotTrue(categoriaOptional.isPresent())) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaOptional.get();
	}
}