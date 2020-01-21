package br.com.projeto.relatorios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projeto.dao.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public abstract class JasperBase<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -956014663192156733L;
	private static final String PDF_MIME_TYPE = "application/force-download";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	private static final String IMAGE_PATH = "IMAGE_PATH";
	private static final String IMAGE_DIR = "img";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private static final String EXTENSAO_PDF = ".pdf";

	private HttpServletResponse response;
	private FacesContext context;
	private Connection con;
	private EntityManager entityManager;

	protected Map<String, Object> parameters;
	protected ByteArrayOutputStream baos;
	protected static final String CONTENT_DISPOSITION_V_INIT_ATT = "attachment; filename=\"";
	protected static final String CONTENT_DISPOSITION_V_FILENAME = "; filename=\"";

	/**
	 * 
	 */
	public JasperBase() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) this.context.getExternalContext().getResponse();
	}

	/**
	 * @throws Exception
	 */
	public void getRelatorio() throws Exception {
		try {
			baos = gerarByteArrayOutput();
			response.reset();
			response.setContentType(PDF_MIME_TYPE);
			response.setContentLength(baos.size());
			response.setHeader(CONTENT_DISPOSITION,
					String.format("%s%s%s%s", getTipoSaida(), CONTENT_DISPOSITION_V_FILENAME, getNomeTemplate(), EXTENSAO_PDF));
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();

			closeConnection();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(e.getMessage(),
					new FacesMessage("Erro ao gerar o relatorio!"));
			throw new Exception();
		} finally {
			response.getOutputStream().close();
			context.responseComplete();
		}

	}

	public ByteArrayOutputStream gerarByteArrayOutput() throws JRException {
		InputStream stream = this.getClass().getResourceAsStream(getDiretorioRelatorio());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		JRBeanCollectionDataSource datasrc = carregarJRBeanCollectionDataSource();
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String imagemPath = String.format("%s%s", request.getSession().getServletContext().getRealPath(IMAGE_DIR),
				File.separator);

		montarParametros();
		parameters.put(IMAGE_PATH, imagemPath);
		parameters.put(SUBREPORT_DIR, getSubDiretorioRelatorio());

		JasperReport jasper = (JasperReport) JRLoader.loadObject(stream);

		// para usar JavaBeanDataSource define 'datasrc' como datasource
		JasperPrint print = JasperFillManager.fillReport(jasper, parameters, datasrc);

		JasperExportManager.exportReportToPdfStream(print, baos);
		return baos;
	}

	protected abstract JRBeanCollectionDataSource carregarJRBeanCollectionDataSource();

	protected abstract String getNomeTemplate();

	protected abstract String getDiretorioRelatorio();

	protected abstract String getTipoSaida();

	protected abstract String getSubDiretorioRelatorio();

	protected abstract void montarParametros();

	/**
	 * Adiciona os parametros a serem passados para o JasperReports.
	 *
	 * @param param
	 * @param value
	 */
	protected void adicionarParam(String param, Object value) {
		this.parameters.put(param, value);
	}

	/**
	 * @return
	 */
	protected EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = HibernateUtil.getSessionEntityManagerFactory().createEntityManager();
		}
		return entityManager;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	private Connection getConexao() {
		try {
			getEntityManager().getTransaction().begin();
			con = getEntityManager().unwrap(java.sql.Connection.class);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection() {
		try {
			if (con != null) {
				con.close();				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
