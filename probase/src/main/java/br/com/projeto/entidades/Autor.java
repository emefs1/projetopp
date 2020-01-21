package br.com.projeto.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "autor")
@Table(name = "autor")
public class Autor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 608678223500672556L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_autor")
	private long id_autor;
	@Column(name = "nome", length = 100)
	private String nome;

	/**
	 * 
	 */
	public Autor() {
	}

	public Autor(long id, String nome) {
		this.id_autor = id;
		this.nome = nome;
	}

	public long getIdAutor() {
		return id_autor;
	}

	public void setIdAutor(long id_autor) {
		this.id_autor = id_autor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
	    return super.toString();
	}
	
}
