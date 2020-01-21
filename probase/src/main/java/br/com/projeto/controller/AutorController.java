package br.com.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import br.com.projeto.entidades.Autor;
import br.com.projeto.entidades.filtros.FiltroAutor;
import br.com.projeto.service.AutorService;
import br.com.projeto.util.MensagemUtil;

@ManagedBean(name = "autorController")
@SessionScoped
public class AutorController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9002598643828188483L;

	@Inject
	private AutorService autorService;

	private Autor autor;
	private String inputName;
	private FiltroAutor filtro = new FiltroAutor();

	public AutorController() {

	}

	public String prepararAdicionarAutorStr() {
		autor = new Autor();
		return "gerenciarAutor";
	}

	public String excluirAutor() {
		getAutorService().excluirAutor(getAutor());
		setAutor(null);
		return "index";
	}

	public String adicionarAutorStr() {
		getAutorService().adicionarAutor(autor);
		return "index";
	}

	public String alterarAutorStr() {
		getAutorService().alterarAutor(autor);
		return "index";
	}

	// ActionEvent
	public void prepararAdicionarAutor(ActionEvent actionEvent) {
		autor = new Autor();
	}

	public void adicionarAutor(ActionEvent actionEvent) {
		getAutorService().persistAutor(autor);
	}

	public void alterarAutor(ActionEvent actionEvent) {
		alterarAutorStr();
		MensagemUtil.addInfo("mensagem.info.processar.registro.sucesso");
	}

	public List<String> nameSuggestions(String valorPreenchido) {
		return getAutorService().nomeSugestaoAutor(valorPreenchido, 10);
	}

	public void buscarAutor() {
		setAutor(getAutorService().buscarAutorPorFiltro(filtro));
	}

	/*
	 * Secao com métodos GETTER AND SETTER
	 * 
	 */
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor Autor) {
		this.autor = Autor;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		System.out.println(inputName);
		this.inputName = inputName;
	}

	public FiltroAutor getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroAutor filtro) {
		this.filtro = filtro;
	}

	public AutorService getAutorService() {
		return autorService;
	}

}
