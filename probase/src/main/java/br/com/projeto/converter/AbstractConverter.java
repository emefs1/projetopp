package br.com.projeto.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

/**
 * @author
 *
 */
public abstract class AbstractConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * Retorna nulo caso a String seja vazia.
	 */
	public Object getAsObject(FacesContext ctx, UIComponent ui, String valor) {
		if (StringUtils.isEmpty(valor)) {
			return null;
		}

		if ((valor != null) && valor.trim().length() < 0) {
			return null;
		}

		return getAsObject(valor);
	}

	/**
	 * Metodo a ser sobrescrito para todos os conversores.
	 * 
	 * @param valor
	 * @return
	 */
	public abstract Object getAsObject(String valor);

	/**
	 * Retorna o objeto como uma String.
	 */
	public String getAsString(FacesContext ctx, UIComponent ui, Object valor) {
		return getAsString(valor);
	}

	/**
	 * @param valor
	 * @return
	 */
	public String getAsString(Object valor) {
		return valor.toString();
	}

}
