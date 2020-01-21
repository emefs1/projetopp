package br.com.projeto.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.Query;
import javax.ws.rs.Path;

import br.com.projeto.dao.AbstractDao;
import br.com.projeto.dao.DaoInterface;
import br.com.projeto.entidades.Autor;

@Named(value = "autorDaoImp")
@ApplicationScoped
@Path("/AutorDaoImp")
public class AutorDaoImp extends AbstractDao<Autor> implements DaoInterface<Autor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -755105070984435341L;

	public AutorDaoImp() {

	}

	@Override
	public void save(Autor objeto) throws Exception {
		super.salvar(objeto);
	}

	@Override
	public Autor findById(Class<Autor> objeto, long id) throws Exception {
		// String query = "Select a From autor a where id_autor = :id";
		return super.findById(id);
	}

	@Override
	public List<Autor> findList() throws Exception {
		String query = "Select a From autor a Order by id";
		return super.findList(query);
	}

	@Override
	public void remove(Autor objeto) throws Exception {
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(getEntityManager().getReference(Autor.class, objeto.getIdAutor()));
		getEntityManager().getTransaction().commit();
		System.out.println("successfull");
		getEntityManager().close();
	}

	@Override
	public void update(Autor objeto) throws Exception {
		super.atualizar(objeto);
	}

	@Override
	public void persist(Autor objeto) throws Exception {
		objeto.setIdAutor(0);
		super.salvar(objeto);
	}

	@SuppressWarnings("unchecked")
	public List<Autor> recuperarAutor(String valorPreenchido, Integer limite) {
		getEntityManager().getTransaction().begin();
		StringBuilder consulta = new StringBuilder();
		consulta.append("Select a.* From autor a where UPPER(a.nome) LIKE UPPER('%");
		consulta.append(valorPreenchido + "%') ");
		consulta.append(" Order by a.id_autor");
		consulta.append(" LIMIT ");
		consulta.append(String.valueOf(limite));

		List<Autor> resultado = null;
		try {
			Query query = getEntityManager().createNativeQuery(consulta.toString(), Autor.class);
			// query.setParameter("valorPreenchido", valorPreenchido);
			// query.setParameter("limite", limite);

			resultado = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
		return resultado == null ? new ArrayList<>() : resultado;
	}

	@SuppressWarnings("unchecked")
	public List<String> recuperarNomeAutor(String valorPreenchido, Integer limite) {
		getEntityManager().getTransaction().begin();
		StringBuilder consulta = new StringBuilder();
		consulta.append("Select a.nome From autor a where UPPER(a.nome) LIKE UPPER('%");
		consulta.append(valorPreenchido + "%') ");
		consulta.append(" Order by a.nome");
		consulta.append(" LIMIT ");
		consulta.append(String.valueOf(limite));

		List<String> resultado = null;
		try {
			Query query = getEntityManager().createNativeQuery(consulta.toString());
			resultado = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
		return resultado == null ? new ArrayList<>() : resultado;
	}

	public Autor buscarAutorPorNome(String value) {
		StringBuilder consulta = new StringBuilder();
		consulta.append("Select a From autor a where UPPER(a.nome) = UPPER(:nomeAutor)");

		Query query = getEntityManager().createQuery(consulta.toString(), Autor.class);
		query.setParameter("nomeAutor", value);
		try {
			return (Autor) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
