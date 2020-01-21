package br.com.projeto.entidades.filtros;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.projeto.dao.HibernateUtil;
import br.com.projeto.entidades.Livro;

public class FiltroLivroConsulta implements FiltroInterface<Livro, FiltroLivro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2090354871458884715L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Livro> filtrados(FiltroLivro filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		return criteria.list();
	}

	@Override
	public int quantidadeFiltrados(FiltroLivro filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Criteria criarCriteriaParaFiltro(FiltroLivro filtro) {
		Session session = (Session) HibernateUtil.getSessionEntityManagerFactory().createEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(Livro.class);

		if (filtro != null) {
			if (filtro.getNomeLivro() != null && !filtro.getNomeLivro().isEmpty()) {
				criteria.add(Restrictions.ilike("titulo", filtro.getNomeLivro(), MatchMode.ANYWHERE));
			}
		}

		return criteria;
	}

	public LazyDataModel<Livro> popularLazyModelLivros(FiltroLivro filtro) {
		return new LazyDataModel<Livro>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1381445614829489031L;

			@Override
			public List<Livro> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, String> filters) {
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(quantidadeFiltrados(filtro));

				return filtrados(filtro);
			}
		};
	}

}
