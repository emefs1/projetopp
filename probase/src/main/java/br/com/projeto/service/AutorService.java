package br.com.projeto.service;

import java.io.Serializable;
import java.util.List;

import br.com.projeto.entidades.Autor;
import br.com.projeto.entidades.filtros.FiltroAutor;


/**
 * @author emerson.santos
 *
 */
public interface AutorService extends Serializable {

	public void adicionarAutor(Autor autor);

	public void persistAutor(Autor autor);

	public void alterarAutor(Autor autor);

	public Autor buscarAutorPorFiltro(FiltroAutor filtro);

	public void excluirAutor(Autor autor);

	public List<String> nomeSugestaoAutor(String valorPreenchido, int i);

}
