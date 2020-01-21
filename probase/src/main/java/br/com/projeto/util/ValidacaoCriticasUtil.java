package br.com.projeto.util;

import java.math.BigDecimal;

/**
 * Esta classe efetua validacoes.
 *
 */
public class ValidacaoCriticasUtil {

	/**
	 * Construtor default.
	 */
	public ValidacaoCriticasUtil() {

	}

	public static boolean isCampoPreenchido(String campo) throws Exception {
		if (campo == null || campo.isEmpty()) {
			throw new Exception();
		}
		return true;
	}

	public static boolean isValorPreenchido(BigDecimal valor) throws Exception {
		if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new Exception();
		}
		return true;
	}

	public static boolean isObjeto(Object objeto) throws Exception {
		if (objeto == null) {
			throw new Exception();
		}
		return true;
	}

}
