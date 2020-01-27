package br.com.projeto.service;

import java.io.Serializable;

import br.com.projeto.entidades.Livro;


public interface LivroService extends Serializable {

	public void adicionarLivro(Livro livro, boolean botaoAlterar);

	public void atualizarLivro(Livro livro, boolean alterarLivro);

	public void persistLivro(Livro livro, boolean alterarLivro);

	public void removerLivro(Livro livroTemp);

	public String gerarRelatorio(String saida);	

}
