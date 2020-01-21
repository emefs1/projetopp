package br.com.projeto.entidades.filtros;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;

public interface FiltroInterface<E, F>  extends Serializable {

	public List<E> filtrados(F filtro);
	public int quantidadeFiltrados(F filtro);
	public Criteria criarCriteriaParaFiltro(F filtro);
	
}
