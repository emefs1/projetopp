package br.com.projeto.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.projeto.dao.impl.AutorDaoImp;
import br.com.projeto.entidades.Autor;

@FacesConverter(value = "autorConverter")
public class AutorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				AutorDaoImp autorDAO = new AutorDaoImp();
				Autor autor = autorDAO.buscarAutorPorNome(value);
				if (autor == null) {
					return null;
				}
				return autor.getIdAutor();
			} catch (Exception e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de conversão", "Valor não é valido."));
			}
		} else {
			return null;
		}
	}

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

}
