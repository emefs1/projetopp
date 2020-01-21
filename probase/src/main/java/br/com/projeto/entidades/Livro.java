package br.com.projeto.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@SuppressWarnings("deprecation")
@Entity(name = "livro")
@Table(name = "livro")
public class Livro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4655360806478792928L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_livro")
	private long id_livro;
	@Column(name = "titulo", length = 100)
	private String titulo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_autor")
	@ForeignKey(name = "fk_id_autor")
	private Autor autor;

	@Column(name = "paginas", length = 4)
	private int paginas;
	@Column(name = "editora", length = 100)
	private String editora;
	@Column(name = "isbn", length = 2)
	private String isbn;
	@Column(name = "avaliacao", length = 2)
	private int avaliacao;

	/**
	 * 
	 */
	public Livro() {
	}

	public Livro(long id_livro, String titulo, Autor autor, int paginas, String editora, String isbn, int avaliacao) {
		this.id_livro = id_livro;
		this.titulo = titulo;
		this.autor = autor;
		this.paginas = paginas;
		this.editora = editora;
		this.isbn = isbn;
		this.avaliacao = avaliacao;
	}

	public long getId_livro() {
		return id_livro;
	}

	public void setId_livro(long id_livro) {
		this.id_livro = id_livro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}

}
