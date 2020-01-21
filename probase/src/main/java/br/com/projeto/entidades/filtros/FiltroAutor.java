package br.com.projeto.entidades.filtros;

import java.io.Serializable;

public class FiltroAutor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9126595329008910600L;

	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
	private String nomeAutor;

	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}

	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}

	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}

	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}

	public String getPropriedadeOrdenacao() {
		return propriedadeOrdenacao;
	}

	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}

	public boolean isAscendente() {
		return ascendente;
	}

	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

}
