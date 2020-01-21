package br.com.projeto.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MensagemUtil {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("../META-INF.messages",
			Locale.getDefault());

	public static void addFromResourceBundle(Severity gravidade, String msg, String msgPadrao) {
		FacesContext context = FacesContext.getCurrentInstance();
		String msgparam = msg;
		try {
			if (msgPadrao != null) {
				msgparam = msgPadrao;
				context.addMessage(msg, new FacesMessage(gravidade, getDescricao(msgPadrao), msg));
			} else {
				context.addMessage(msg, new FacesMessage(gravidade, getDescricao(msg), msg));
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(msg, new FacesMessage(gravidade, msgparam, msgparam));
		}
	}

	public static void addWarn(String msg, String msgPadrao) {
		MensagemUtil.addFromResourceBundle(FacesMessage.SEVERITY_WARN, msg, null);
	}

	public static void addInfo(String msg) {
		MensagemUtil.addFromResourceBundle(FacesMessage.SEVERITY_INFO, msg, null);
	}

	public static void addErrorMessage(Exception ex, String defaultMsg, String nameMsg) {
		String msg = ex.getLocalizedMessage();
		if (msg != null && msg.length() > 0) {
			addErrorMessage(msg, nameMsg);
		} else {
			addErrorMessage(defaultMsg, nameMsg);
		}
	}

	/**
	 * Limpar as mensagens que possam estar ainda no contexto.
	 */
	public static void clearMessages() {
		if (FacesContext.getCurrentInstance().getMessages() != null) {
			FacesContext.getCurrentInstance().getMessages().remove();
		}
	}

	private static void addErrorMessage(String msg, String nameMsg) {
		MensagemUtil.addFromResourceBundle(FacesMessage.SEVERITY_ERROR, msg, nameMsg);
	}

	public static String getDescricao(String mensagem) {
		return RESOURCE_BUNDLE.getString(mensagem);
	}
	
	private MensagemUtil() {
	}

}
