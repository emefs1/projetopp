package br.com.projeto.relatorios;

import java.util.List;

import br.com.projeto.entidades.AutoresVO;
import br.com.projeto.entidades.Livro;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioLivro<T> extends JasperBase<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 676807480856366892L;
	private static final String REPORT_RELATORIO_DIRETORIO = "/META-INF/report/";
	private static final String REPORT_RELATORIO_JASPER = "/META-INF/report/relatorio.jasper";

	private String tipoSaida;
	private List<Livro> lista;
	private List<AutoresVO> listaVO;

	public RelatorioLivro(List<Livro> lista, String tipoSaida, List<AutoresVO> listaVO) {
		this.lista = lista;
		this.tipoSaida = tipoSaida;
		this.listaVO = listaVO;
	}

	@Override
	protected JRBeanCollectionDataSource carregarJRBeanCollectionDataSource() {
		return new JRBeanCollectionDataSource(this.lista, false);
	}

	@Override
	protected String getNomeTemplate() {
		return "relatorio";
	}

	@Override
	protected String getDiretorioRelatorio() {
		return REPORT_RELATORIO_JASPER;
	}

	@Override
	protected String getTipoSaida() {
		return this.tipoSaida;
	}

	@Override
	protected String getSubDiretorioRelatorio() {
		return REPORT_RELATORIO_DIRETORIO;
	}

	@Override
	protected void montarParametros() {
		adicionarParam("listaSubRelatorio", listaVO);
		adicionarParam("nomeEmpresa", "Livraria S/A");
	}

}
