package br.com.projeto.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

import br.com.projeto.dao.impl.AutorDaoImp;
import br.com.projeto.entidades.Autor;
import br.com.projeto.entidades.filtros.FiltroAutor;
import br.com.projeto.enums.EnumMensagensPadrao;
import br.com.projeto.util.MensagemUtil;
import br.com.projeto.util.ValidacaoCriticasUtil;

@Named
@RequestScoped
public class AutorService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3900569877440433661L;

	@Inject
	private AutorDaoImp autorDaoImp;

	/**
	 * 
	 */
	public AutorService() {
	}

	public void adicionarAutor(Autor autor) {
		try {
			validacao(autor);
			getAutorDaoImp().save(autor);
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
	}

	public void persistAutor(Autor autor) {
		try {
			validacao(autor);
			getAutorDaoImp().persist(autor);
			MensagemUtil.addInfo(EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal());
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
	}

	public void alterarAutor(Autor autor) {
		try {
			validacao(autor);
			getAutorDaoImp().update(autor);
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
	}

	public Autor buscarAutorPorFiltro(FiltroAutor filtro) {
		Autor autor = getAutorDaoImp().buscarAutorPorNome(filtro.getNomeAutor());
		return autor;
	}

	public void excluirAutor(Autor autor) {
		try {
			if (autor == null || autor.getIdAutor() == 0) {
				MensagemUtil.addInfo("mensagem.erro.validacao.autor.naoexiste");
			} else {
				getAutorDaoImp().remove(autor);
				MensagemUtil
						.addInfo(EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal());
			}
		} catch (Exception e) {
			if (e.getCause().getCause() instanceof ConstraintViolationException) {
				MensagemUtil.addErrorMessage(e, "mensagem.erro.validacao.autor.vinculo.com.livro",
						"mensagem.erro.validacao.autor.vinculo.com.livro");
			} else {
				MensagemUtil.addErrorMessage(e,
						EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
						EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
			}
		}
	}

	public List<String> nomeSugestaoAutor(String valorPreenchido, int i) {
		List<String> matches = new ArrayList<>();
		try {
			matches.addAll(getAutorDaoImp().recuperarNomeAutor(valorPreenchido, 10));
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
		return matches;
	}

	/**
	 * @param autor
	 * @throws Exception
	 */
	private void validacao(Autor autor) throws Exception {
		ValidacaoCriticasUtil.isObjeto(autor);
		ValidacaoCriticasUtil.isCampoPreenchido(autor.getNome());
		if (getAutorDaoImp().buscarAutorPorNome(autor.getNome()) != null) {
			MensagemUtil.addWarn("mensagem.erro.validacao.autor.ja.cadastrado",
					"mensagem.erro.validacao.autor.ja.cadastrado");
			throw new Exception();
		}
	}

	@javax.enterprise.context.RequestScoped
	public AutorDaoImp getAutorDaoImp() {
		return autorDaoImp;
	}
}
