package br.com.projeto.enums;

import br.com.projeto.util.MensagemUtil;

public enum EnumMensagensPadrao {

	MENSAGEM_INFO_PROCESSAR_REGISTRO_SUCESSO(
			"mensagem.info.processar.registro.sucesso"),
	MENSAGEM_ERRO_PROCESSAR_REGISTRO(
					"mensagem.erro.processar.registro");

	private String descricao;

	private EnumMensagensPadrao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return MensagemUtil.getDescricao(descricao);
	}

	public String getDescricaoOriginal() {
		return descricao;
	}
	
	/**
	 * 
	 * @param descricao
	 * @return
	 */
	public static EnumMensagensPadrao fromValue(String descricao) {
		if (descricao != null && !descricao.isEmpty()) {
			for (EnumMensagensPadrao status : EnumMensagensPadrao.values()) {
				if (status.descricao.equals(descricao)) {
					return status;
				}
			}
		}
		throw new IllegalArgumentException(String.format("Status invalido: %A-Z", descricao));
	}

	/**
	 * @return
	 */
	public String toValue() {
		return this.descricao;
	}

}
