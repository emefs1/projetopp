package br.com.projeto.dao.impl;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.Path;

import br.com.projeto.dao.AbstractDao;
import br.com.projeto.dao.DaoInterface;
import br.com.projeto.entidades.Livro;

@Named(value = "livroDaoImpl")
@ApplicationScoped
@Path("/LivroDaoImpl")
public class LivroDaoImpl extends AbstractDao<Livro> implements DaoInterface<Livro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7083849625760879281L;

	/**
	 * 
	 */
	public LivroDaoImpl() {

	}
	
	@Override
	public void save(Livro objeto) throws Exception {
		super.salvar(objeto);
	}

	@Override
	public void persist(Livro objeto) throws Exception {
		objeto.setId_livro(0);
		super.salvar(objeto);
	}

	@Override
	public Livro findById(Class<Livro> objeto, long id) throws Exception {
		return super.findById(id);
	}

	@Override
	public List<Livro> findList() throws Exception {
		String query = "Select a From livro a Order by id_livro";
		return super.findList(query);
	}

	@Override
	public void remove(Livro objeto) throws Exception {
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(getEntityManager().getReference(Livro.class, objeto.getId_livro()));
		getEntityManager().getTransaction().commit();
		System.out.println("successfull");
		getEntityManager().close();
	}

	@Override
	public void update(Livro objeto) throws Exception {
		super.atualizar(objeto);
	}

}
