package br.com.projeto.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;

import br.com.projeto.entidades.Autor;
import br.com.projeto.entidades.Livro;
import br.com.projeto.entidades.filtros.FiltroLivro;
import br.com.projeto.entidades.filtros.FiltroLivroConsulta;
import br.com.projeto.service.impl.AutorServiceImpl;
import br.com.projeto.service.impl.LivroServiceImpl;
import br.com.projeto.util.MensagemUtil;

@ManagedBean(name = "livroController")
@SessionScoped
public class LivroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4283232024973687788L;

	@Inject
	private LivroServiceImpl livroService;
	@Inject
	private AutorServiceImpl livroAutorService;

	private Livro livro;
	private List<Autor> autores = new ArrayList<>();
	private String inputName;

	private FiltroLivro filtro = new FiltroLivro();
	private FiltroLivroConsulta filtros = new FiltroLivroConsulta();
	private LazyDataModel<Livro> model;
	private boolean alterarLivro;

	public LivroController() {
		model = filtros.popularLazyModelLivros(filtro);
	}

	public String prepararAdicionarLivroStr() {
		livro = new Livro();
		livro.setAutor(new Autor());
		setAlterarLivro(false);
		return "gerenciarLivro";
	}

	public String prepararAlterarLivroStr() {
		livro = (Livro) (model.getRowData());
		return "gerenciarLivro";
	}

	public String excluirLivro() {
		Livro livroTemp = (Livro) (model.getRowData());
		getLivroService().removerLivro(livroTemp);
		return "index";
	}

	public String adicionarLivroStr() {
		getLivroService().adicionarLivro(livro, alterarLivro);
		return "index";
	}

	public String alterarLivroStr() {
		getLivroService().atualizarLivro(livro, alterarLivro);
		return "index";
	}

	// ActionEvent
	public void prepararAdicionarLivro(ActionEvent actionEvent) {
		setAlterarLivro(false);
		livro = new Livro();
		livro.setAutor(new Autor());
	}

	public void prepararAlterarLivro(ActionEvent actionEvent) {
		setAlterarLivro(true);
		livro = (Livro) (model.getRowData());
	}

	public void adicionarLivro(ActionEvent actionEvent) {
		getLivroService().persistLivro(livro, alterarLivro);
	}

	public void alterarLivro(ActionEvent actionEvent) {
		alterarLivroStr();
	}

	/**
	 * @return
	 */
	public String baixarRelatorio() {
		return getLivroService().gerarRelatorio("inline");
	}

	/**
	 * @return
	 */
	public String carregarRelatorio() {
		return getLivroService().gerarRelatorio("attachment");
	}

	public boolean isLista() {
		if (model == null || model.getRowCount() == 0) {
			return true;
		}
		return false;
	}

	public List<String> nameSuggestions(String valorPreenchido) {
		return getLivroAutorService().nomeSugestaoAutor(valorPreenchido, 10);
	}

	/*
	 * Secao com métodos GETTER AND SETTER
	 * 
	 */
	public List<Autor> getAutores() {
		try {
			autores = getLivroAutorService().getAutorDaoImpl().findList();
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, "mensagem.erro.processar.lista", "messages");
		}
		return autores;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public FiltroLivro getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroLivro filtro) {
		this.filtro = filtro;
	}

	public FiltroLivroConsulta getFiltros() {
		return filtros;
	}

	public void setFiltros(FiltroLivroConsulta filtros) {
		this.filtros = filtros;
	}

	public LazyDataModel<Livro> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Livro> model) {
		this.model = model;
	}

	public boolean isAlterarLivro() {
		return alterarLivro;
	}

	public void setAlterarLivro(boolean alterarLivro) {
		this.alterarLivro = alterarLivro;
	}

	public LivroServiceImpl getLivroService() {
		return livroService;
	}

	public AutorServiceImpl getLivroAutorService() {
		return livroAutorService;
	}

}
