package com.algaworks.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Lancamento_;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> query = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = query.from(Lancamento.class);
		
		Predicate[] predicates = this.criarRestricoes(lancamentoFilter, builder, root);
		
		query.where(predicates);
		return this.manager.createQuery(query).getResultList();
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		final List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(lancamentoFilter.getDescricao())) {
			predicates.add(
					builder.like(builder.lower(root.get(Lancamento_.descricao)), String.format("%%%s%%", lancamentoFilter.getDescricao().toLowerCase()))
					);
		}
		
		if (ObjectUtils.allNotNull(lancamentoFilter.getDataVencimentoDe())) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (ObjectUtils.allNotNull(lancamentoFilter.getDataVencimentoAte())) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}