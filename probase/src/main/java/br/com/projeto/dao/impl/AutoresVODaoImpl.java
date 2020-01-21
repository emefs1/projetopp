package br.com.projeto.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import br.com.projeto.dao.AbstractDao;
import br.com.projeto.dao.HibernateUtil;
import br.com.projeto.entidades.AutoresVO;

public class AutoresVODaoImpl extends AbstractDao<AutoresVO> implements Serializable {

	private static final String QUERY_AUTORES_VO = "AutoresVO.listarAutores";

	/**
	 * 
	 */
	private static final long serialVersionUID = 8894885165455539605L;

	public AutoresVODaoImpl() {

	}

	public List<AutoresVO> findList() throws Exception {
		EntityManager em = getEntityManager();
		try {
			Session session = (Session) em.getDelegate();
			// List<AutoresVO> q = queryComResultTransformers(session);
			List<AutoresVO> q = queryComStream(session);
			return q;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			em.close();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<AutoresVO> queryComResultTransformers(Session session) {
		org.hibernate.Query<AutoresVO> q = session.createSQLQuery(QUERY_AUTORES_VO)
				.addScalar("nome", StringType.INSTANCE).addScalar("qtdPublicacoes", IntegerType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(AutoresVO.class));

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<AutoresVO> queryComStream(Session session) {
		Query query = HibernateUtil.getNamedQuery(getEntityManager(), QUERY_AUTORES_VO);
		Stream<Object[]> autores = query.getResultList().stream();

		List<AutoresVO> lista = autores.map(a -> new AutoresVO((String) a[0], ((BigInteger) a[1]).intValue()))
				.collect(Collectors.toList());

		return lista;
	}

}
