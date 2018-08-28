package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Categoria;

public interface CategoriaService {
	Categoria buscarPeloCodigo(Long codigo);
}
