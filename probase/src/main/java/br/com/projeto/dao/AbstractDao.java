package br.com.projeto.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

/**
 * Dao abstrato que implementa os metodos genericos para acesso ao banco de
 * dados.
 *
 * 
 */
public abstract class AbstractDao<E> {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	private Class<E> persistenceClazz = null;

	@SuppressWarnings("unchecked")
	@Produces
	@RequestScoped
	public E salvar(E entidade) throws Exception {
		getEntityManager().getTransaction().begin();
		Object resultado = (E) DaoUtil.salvar(getEntityManager(), entidade);
		getEntityManager().getTransaction().commit();
		getEntityManager().close();
		return (E) resultado;
	}

	@SuppressWarnings("unchecked")
	@Produces
	@RequestScoped
	public E atualizar(E entidade) throws Exception {
		getEntityManager().getTransaction().begin();
		Object resultado = (E) DaoUtil.update(getEntityManager(), entidade);
		getEntityManager().getTransaction().commit();
		getEntityManager().close();
		return (E) resultado;
	}

	@Produces
	@RequestScoped
	public void remover(E entidade) throws Exception {
		getEntityManager().getTransaction().begin();
		DaoUtil.remover(getEntityManager(), entidade);
		getEntityManager().getTransaction().commit();
		getEntityManager().close();
	}

	@SuppressWarnings("unchecked")
	@Produces
	@RequestScoped
	public E findById(Long id) {
		return (E) DaoUtil.recuperarPorId(getEntityManager(), getPersistenceClass(), id);
	}

	@SuppressWarnings("unchecked")
	@Produces
	@RequestScoped
	public List<E> findList(String query) {
		List<E> list = (List<E>) DaoUtil.recuperarTodos(getEntityManager(), query, getPersistenceClass());
		return list;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = HibernateUtil.getSessionEntityManagerFactory().createEntityManager();
		}
		return entityManager;
	}

	protected Query createQuery(String query) {
		return getEntityManager().createQuery(query);
	}

	protected Query createNamedQuery(String query) {
		return getEntityManager().createNamedQuery(query);
	}

	/**
	 * Executa o <code>clear()</code> para liberar o cache 1o nivel do Hibernate.
	 */
	public void clear() {
		entityManager.clear();
	}

	/**
	 * Desconecta uma entidade da sessão do Hibernate.
	 *
	 * @param entidade
	 */
	public void evict(E entidade) {
		Session session = (Session) entityManager.getDelegate();
		session.evict(entidade);
	}

	/**
	 * Atualiza a entidade com os valores atuais do banco de dados.
	 *
	 * @param entidade
	 */
	public void refresh(E entidade) {
		if (entidade != null)
			entityManager.refresh(entidade);
	}

	/**
	 * Método que retorna a classe persistente do tipo E.
	 *
	 * @return classe do tipo E
	 */
	@SuppressWarnings("unchecked")
	protected Class<E> getPersistenceClass() {
		if (persistenceClazz == null) {
			persistenceClazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		}
		return persistenceClazz;
	}

}
