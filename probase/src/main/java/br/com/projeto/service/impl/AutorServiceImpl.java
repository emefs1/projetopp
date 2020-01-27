package br.com.projeto.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

import br.com.projeto.dao.impl.AutorDaoImpl;
import br.com.projeto.entidades.Autor;
import br.com.projeto.entidades.filtros.FiltroAutor;
import br.com.projeto.enums.EnumMensagensPadrao;
import br.com.projeto.service.AutorService;
import br.com.projeto.util.MensagemUtil;
import br.com.projeto.util.ValidacaoCriticasUtil;

@Named
@RequestScoped
public class AutorServiceImpl implements AutorService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3900569877440433661L;

	@Inject
	private AutorDaoImpl autorDaoImpl;

	/**
	 * 
	 */
	public AutorServiceImpl() {
	}

	public void adicionarAutor(Autor autor) {
		try {
			validacao(autor);
			getAutorDaoImpl().save(autor);
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
	}

	public void persistAutor(Autor autor) {
		try {
			validacao(autor);
			getAutorDaoImpl().persist(autor);
			MensagemUtil.addInfo(EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal());
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
	}

	public void alterarAutor(Autor autor) {
		try {
			validacao(autor);
			getAutorDaoImpl().update(autor);
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
	}

	public Autor buscarAutorPorFiltro(FiltroAutor filtro) {
		Autor autor = getAutorDaoImpl().buscarAutorPorNome(filtro.getNomeAutor());
		return autor;
	}

	public void excluirAutor(Autor autor) {
		try {
			if (autor == null || autor.getIdAutor() == 0) {
				MensagemUtil.addInfo("mensagem.erro.validacao.autor.naoexiste");
			} else {
				getAutorDaoImpl().remove(autor);
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
			matches.addAll(getAutorDaoImpl().recuperarNomeAutor(valorPreenchido, 10));
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
		if (getAutorDaoImpl().buscarAutorPorNome(autor.getNome()) != null) {
			MensagemUtil.addWarn("mensagem.erro.validacao.autor.ja.cadastrado",
					"mensagem.erro.validacao.autor.ja.cadastrado");
			throw new Exception();
		}
	}

	@javax.enterprise.context.RequestScoped
	public AutorDaoImpl getAutorDaoImpl() {
		return autorDaoImpl;
	}
}
