package br.com.mapfre.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageUtil {

	/**
	 * Recupera a mensagem definida no properties pela chave
	 * 
	 * @param chave
	 *            - chave definida no arquivo
	 * @return
	 */
	public static String getMessage(ResourceBundle bundle, String chave) {
		String result = "";
		try {
			result = bundle.getString(chave);
			if (result == null || result.isEmpty()) {
				result = chave;
			}
		} catch (MissingResourceException e) {
			result = chave;
		}

		return result;
	}

	/**
	 * Recupera a mensagem definida no properties pela chave
	 * 
	 * @param chave
	 *            - chave definida no arquivo
	 * @return
	 */
	public static String getMessage(String chave) {
		return getMessage(ResourceBundle.getBundle("messages"), chave);
	}

	/**
	 * Recupera a mensagem definida no properties pela chave, formatando a mesma com
	 * os parametros
	 * 
	 * @param chave
	 *            - chave definida no arquivo
	 * @param params
	 *            - parametros
	 * @return
	 */
	public static String getMessage(String chave, Object... params) {
		return getMessage(ResourceBundle.getBundle("messages"), chave, params);
	}

	/**
	 * Recupera a mensagem definida no properties pela chave, formatando a mesma com
	 * os parametros
	 * 
	 * @param chave
	 *            - chave definida no arquivo
	 * @param params
	 *            - parametros
	 * @return
	 */
	public static String getMessage(ResourceBundle bundle, String chave, Object... params) {
		return MessageFormat.format(getMessage(bundle, chave), params);
	}

}
