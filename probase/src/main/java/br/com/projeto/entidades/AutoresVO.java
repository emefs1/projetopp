package br.com.projeto.entidades;

import java.io.Serializable;

public class AutoresVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7548151762289240039L;

	private String nome;
	private Integer qtdPublicacoes;

	public AutoresVO() {
	}

	public AutoresVO(String nome, Integer qtdPublicacoes) {
		this.nome = nome;
		this.qtdPublicacoes = qtdPublicacoes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdPublicacoes() {
		return qtdPublicacoes;
	}

	public void setQtdPublicacoes(Integer qtdPublicacoes) {
		this.qtdPublicacoes = qtdPublicacoes;
	}

}
