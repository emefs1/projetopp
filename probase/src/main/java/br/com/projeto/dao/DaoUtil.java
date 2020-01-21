package br.com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Classe Dao Util.
 * 
 * 
 */
public class DaoUtil {

	private DaoUtil() {
	}

	public static final Object salvar(EntityManager entityManager, Object entidade) {
		entityManager.persist(entidade);
		return entidade;
	}

	public static final void remover(EntityManager entityManager, Object entidade) {
		entityManager.remove(entidade);
		entityManager.flush();
	}

	public static final Object update(EntityManager entityManager, Object entidade) {
		entidade = entityManager.merge(entidade);
		return entidade;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Object recuperarPorId(EntityManager entityManager, Class classePersistente, Long id) {
		return entityManager.find(classePersistente, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Object> recuperarTodos(EntityManager entityManager, String query, Object objeto) {
		Query consulta = entityManager.createQuery(query);
		return consulta.getResultList();
	}

}
