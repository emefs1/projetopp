package br.com.projeto.relatorios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projeto.dao.HibernateUtil;
import br.com.projeto.entidades.AutoresVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class Relatorio<T> {

	private static final String REPORT_RELATORIO_JASPER = "/META-INF/report/relatorio.jasper";
	private static final String PATH_DIRECTORY_IMG = "img";
	private HttpServletResponse response;
	private FacesContext context;
	private Connection con;
	private EntityManager entityManager;
	protected Map<String, Object> parameters;

	public Relatorio() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) this.context.getExternalContext().getResponse();
	}

	/**
	 * @param lista
	 * @param listaVO 
	 * @param saida
	 * @throws Exception 
	 */
	public void getRelatorio(List<T> lista, List<AutoresVO> listaVO, String saida) throws Exception {
		try {
			ByteArrayOutputStream baos = gerarByteArrayOutput(lista, listaVO);
			response.reset();
			response.setContentType("application/force-download");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition",
					String.format("%s%s%s%s", saida, "; filename=", "relatorio", ".pdf"));
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			
			closeConnection();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(e.getMessage(), new FacesMessage("Erro ao gerar o relatorio!"));
			throw new Exception(); 
		} finally {
			response.getOutputStream().close();
			context.responseComplete();
		}

	}

	/**
	 * @param lista
	 * @param listaVO 
	 * @return
	 * @throws JRException
	 */
	public ByteArrayOutputStream gerarByteArrayOutput(List<T> lista, List<AutoresVO> listaVO) throws JRException {
		InputStream stream = this.getClass().getResourceAsStream(REPORT_RELATORIO_JASPER);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		JRBeanCollectionDataSource datasrc = new JRBeanCollectionDataSource(lista, false);
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String imagemPath = String.format("%s%s",
				request.getSession().getServletContext().getRealPath(PATH_DIRECTORY_IMG), File.separator);
		
		parameters.put("listaSubRelatorio", listaVO);
		parameters.put("nomeEmpresa", "Livraria S/A");
		parameters.put("IMAGE_PATH", imagemPath);
		//parameters.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));

		JasperReport jasper = (JasperReport) JRLoader.loadObject(stream);

		// para usar JavaBeanDataSource define 'datasrc' como datasource
		JasperPrint print = JasperFillManager.fillReport(jasper, parameters, datasrc);

		JasperExportManager.exportReportToPdfStream(print, baos);
		return baos;
	}

	/**
	 * @return
	 */
	public EntityManager getEntityManager() {
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
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
