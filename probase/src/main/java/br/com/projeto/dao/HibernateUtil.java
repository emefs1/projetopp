package br.com.projeto.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ApplicationScoped
public class HibernateUtil {

	private static final String PERSISTENCE_UNIT_NAME = "ProbasePersistenceUnit";

	private static EntityManager em;

	private static final EntityManagerFactory entityFactory = buildEntityManagerFactory();

	private HibernateUtil() {
	}

	private static EntityManagerFactory buildEntityManagerFactory() {
		if (entityFactory == null) {
			try {
				// Create the SessionFactory
				EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

				return factory;
			} catch (Throwable ex) {
				// Make sure you log the exception, as it might be swallowed
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		} else {
			return entityFactory;
		}
	}

	public static EntityManagerFactory getSessionEntityManagerFactory() {
		return entityFactory;
	}

	public static Query getNamedQuery(EntityManager entityManager, String name) {
		em = null;
		em = entityManager;
		return em.createNamedQuery(name);
	}

}
