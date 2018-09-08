package com.algaworks.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Categoria_;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Lancamento_;
import com.algaworks.algamoney.api.model.Pessoa_;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.repository.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> pesquisar(final LancamentoFilter lancamentoFilter, final Pageable pageable) {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = this.criarRestricoes(lancamentoFilter, builder, root);
		List<Order> orders = this.aplicarOrdenacoes(pageable, builder, root);
		criteria.where(predicates).orderBy(orders);
		final TypedQuery<Lancamento> query = this.manager.createQuery(criteria);
		
		this.adicionarRestricoesPaginacao(query, pageable);
		final Long totalRegistros = this.countPorFiltro(lancamentoFilter);
		
		return new PageImpl<>(query.getResultList(), pageable, totalRegistros);
	}

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		final CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class,
											root.get(Lancamento_.codigo),
											root.get(Lancamento_.descricao),
											root.get(Lancamento_.dataVencimento),
											root.get(Lancamento_.dataPagamento),
											root.get(Lancamento_.valor),
											root.get(Lancamento_.tipo),
											root.get(Lancamento_.categoria).get(Categoria_.nome),
											root.get(Lancamento_.pessoa).get(Pessoa_.nome))
						);
		
		Predicate[] predicates = this.criarRestricoes(lancamentoFilter, builder, root);
		List<Order> orders = this.aplicarOrdenacoes(pageable, builder, root);
		criteria.where(predicates).orderBy(orders);
		final TypedQuery<ResumoLancamento> query = this.manager.createQuery(criteria);
		
		this.adicionarRestricoesPaginacao(query, pageable);
		final Long totalRegistros = this.countPorFiltro(lancamentoFilter);
		
		return new PageImpl<>(query.getResultList(), pageable, totalRegistros);
	}
	
	private void adicionarRestricoesPaginacao(final TypedQuery<?> query, final Pageable pageable) {
		final int posicaoPrimeiroRegistro = this.recuperarPosicaoPrimeiroRegistro(pageable);
		final int quantidadeMaximaPorPagina = pageable.getPageSize();
		query.setFirstResult(posicaoPrimeiroRegistro).setMaxResults(quantidadeMaximaPorPagina);
	}


	private List<Order> aplicarOrdenacoes(Pageable pageable, CriteriaBuilder builder, Root<Lancamento> root) {
		List<Order> orders = new ArrayList<>();
		
		pageable.getSort().forEach(sort -> {
			if (sort.isAscending()) {
				orders.add(builder.asc(root.get(sort.getProperty())));
			} else {
				orders.add(builder.desc(root.get(sort.getProperty())));
			}
		});
		
		return orders;
	}


	private Predicate[] criarRestricoes(final LancamentoFilter lancamentoFilter, final CriteriaBuilder builder, final Root<Lancamento> root) {
		
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

	private Long countPorFiltro(final LancamentoFilter lancamentoFilter) {
		final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		final CriteriaQuery<Long> query = builder.createQuery(Long.class);
		final Root<Lancamento> root = query.from(Lancamento.class);
		
		final Predicate[] predicates = this.criarRestricoes(lancamentoFilter, builder, root);
		
		query.select(builder.count(root)).where(predicates);
		
		return this.manager.createQuery(query).getSingleResult();
	}

	private int recuperarPosicaoPrimeiroRegistro(final Pageable pageable) {
		final int totalRegistrosPorPagina = pageable.getPageSize();
		final int numeroDaPagina = pageable.getPageNumber();
		return numeroDaPagina * totalRegistrosPorPagina;
	}
}