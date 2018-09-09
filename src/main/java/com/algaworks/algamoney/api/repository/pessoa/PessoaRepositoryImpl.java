package com.algaworks.algamoney.api.repository.pessoa;

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

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.model.Pessoa_;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Pessoa> pesquisar(final PessoaFilter filter, final Pageable page) {
		final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		final CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		final Root<Pessoa> root = criteria.from(Pessoa.class);
		
		
		final Predicate[] predicates = this.criarRestricoes(filter, builder, root);
		final List<Order> orders = this.aplicarOrdenacao(page, builder, root);
		criteria.where(predicates).orderBy(orders);
		
		final TypedQuery<Pessoa> query = this.manager.createQuery(criteria);
		this.adicionarRestricoesPaginacao(page, query);
		final Long totalRegistros = this.countPorFiltro(filter);
		
		return new PageImpl<>(query.getResultList(), page, totalRegistros);
	}

	private Predicate[] criarRestricoes(final PessoaFilter filter, final CriteriaBuilder builder, final Root<Pessoa> root) {
		final List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(filter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get(Pessoa_.nome)), String.format("%%%s%%", filter.getNome().toLowerCase())));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private List<Order> aplicarOrdenacao(final Pageable page, final CriteriaBuilder builder, final Root<Pessoa> root) {
		final List<Order> orders = new ArrayList<>();
		page.getSort().forEach(sort -> {
			if (sort.isAscending()) {
				orders.add(builder.asc(root.get(sort.getProperty())));
			} else {
				orders.add(builder.desc(root.get(sort.getProperty())));
			}
		});
		return orders;
	}
	
	private void adicionarRestricoesPaginacao(final Pageable page, final TypedQuery<Pessoa> query) {
		final int posicaoPrimeiroRegistro = this.recuperarPosicaoPrimeiroRegistro(page);
		query.setFirstResult(posicaoPrimeiroRegistro).setMaxResults(page.getPageSize());
	}

	private int recuperarPosicaoPrimeiroRegistro(final Pageable page) {
		final int numeroDaPagina = page.getPageNumber();
		final int quantidadeDeRegistros = page.getPageSize();
		return numeroDaPagina * quantidadeDeRegistros;
	}
	
	private Long countPorFiltro(final PessoaFilter filter) {
		final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		final Root<Pessoa> root = criteria.from(Pessoa.class);
		
		final Predicate[] predicates = this.criarRestricoes(filter, builder, root);
		criteria.select(builder.count(root)).where(predicates);
		
		return this.manager.createQuery(criteria).getSingleResult();
	}
}