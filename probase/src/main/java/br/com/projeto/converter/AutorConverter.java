package br.com.projeto.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.projeto.dao.impl.AutorDaoImpl;
import br.com.projeto.entidades.Autor;

@FacesConverter(value = "autorConverter")
public class AutorConverter extends AbstractConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -662598437413985571L;

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			if (object instanceof Number || object instanceof String) {
				return object.toString();
			} else {
				return String.valueOf(((Autor) object).getNome());
			}
		} else {
			return null;
		}
	}

	@Override
	public Object getAsObject(String valor) {
		try {
			AutorDaoImpl autorDAO = new AutorDaoImpl();
			Autor autor = autorDAO.buscarAutorPorNome(valor);
			if (autor == null) {
				return null;
			}
			return autor.getIdAutor();
		} catch (Exception e) {
			throw new ConverterException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de conversão", "Valor não é valido."));
		}
	}

}
