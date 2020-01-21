package br.com.projeto.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Classe que estabelece quais métodos básicos podem ser utilizados no Dao.
 *
 * @param <E>
 */
public interface DaoInterface<E> extends Serializable {

	public void save(E objeto) throws Exception;

	public void persist(E objeto) throws Exception;

	public E findById(Class<E> objeto, long id) throws Exception;

	public List<E> findList() throws Exception;

	public void remove(E objeto) throws Exception;

	public void update(E objeto) throws Exception;

}
