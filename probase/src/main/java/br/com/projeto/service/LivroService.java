package br.com.projeto.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.projeto.dao.impl.AutorDaoImp;
import br.com.projeto.dao.impl.AutoresVODaoImpl;
import br.com.projeto.dao.impl.LivroDaoImp;
import br.com.projeto.entidades.Autor;
import br.com.projeto.entidades.AutoresVO;
import br.com.projeto.entidades.Livro;
import br.com.projeto.enums.EnumMensagensPadrao;
import br.com.projeto.relatorios.RelatorioLivro;
import br.com.projeto.util.MensagemUtil;
import br.com.projeto.util.ValidacaoCriticasUtil;

@Named
@RequestScoped
public class LivroService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5056146738488247226L;

	@Inject
	private LivroDaoImp livroDaoImp;
	@Inject
	private AutorDaoImp livroAutorDaoImp;

	private RelatorioLivro<Livro> relatorioLivro;

	/**
	 * 
	 */
	public LivroService() {
	}

	public void adicionarLivro(Livro livro, boolean botaoAlterar) {
		try {
			validacao(livro, botaoAlterar);
			getLivroDaoImp().save(livro);
			MensagemUtil.addInfo(EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal());
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					"messages");
		}
	}

	public void atualizarLivro(Livro livro, boolean alterarLivro) {
		try {
			validacao(livro, alterarLivro);
			getLivroDaoImp().update(livro);
			MensagemUtil.addInfo(EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal());
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					"messages");
		}
	}

	public void persistLivro(Livro livro, boolean alterarLivro) {
		try {
			validacao(livro, alterarLivro);
			getLivroDaoImp().persist(livro);
			MensagemUtil.addInfo(EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal());
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
	}

	public void removerLivro(Livro livroTemp) {
		try {
			getLivroDaoImp().remove(livroTemp);
			MensagemUtil.addInfo(EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal());
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
		}
	}

	/**
	 * @param botaoAlterar
	 * @throws Exception
	 */
	private void validacao(Livro livro, boolean botaoAlterar) throws Exception {
		ValidacaoCriticasUtil.isObjeto(livro);
		ValidacaoCriticasUtil.isCampoPreenchido(livro.getTitulo());
		if (livro.getAutor().getIdAutor() == 0) {
			if (botaoAlterar == false) {
				MensagemUtil.addWarn("mensagem.erro.validacao.autor.obrigatorio", null);
				throw new Exception();
			}
			Autor autor = getLivroAutorDaoImp().buscarAutorPorNome(livro.getAutor().getNome());
			livro.setAutor(new Autor());
			livro.setAutor(autor);
		}
	}

	public String gerarRelatorio(String saida) {
		List<Livro> lista = new ArrayList<>();
		List<AutoresVO> listaVO = new ArrayList<>();
		try {
			lista = getLivroDaoImp().findList();
			listaVO = new AutoresVODaoImpl().findList();
			if (lista != null && !lista.isEmpty()) {
				relatorioLivro = new RelatorioLivro<Livro>(lista, saida, listaVO);
				relatorioLivro.getRelatorio();
				MensagemUtil
						.addInfo(EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal());
			} else {
				MensagemUtil.addWarn(
						EnumMensagensPadrao.MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO.getDescricaoOriginal(), null);
			}
			return "index";
		} catch (Exception e) {
			MensagemUtil.addErrorMessage(e, EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal(),
					EnumMensagensPadrao.MENSAGEM_ERRO_PROCESSAR_REGISTRO.getDescricaoOriginal());
			return "index";
		}
	}

	@javax.enterprise.context.RequestScoped
	public LivroDaoImp getLivroDaoImp() {
		return livroDaoImp;
	}

	@javax.enterprise.context.RequestScoped
	public AutorDaoImp getLivroAutorDaoImp() {
		return livroAutorDaoImp;
	}

}
