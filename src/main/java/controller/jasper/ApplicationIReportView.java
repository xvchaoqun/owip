package controller.jasper;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.web.servlet.view.jasperreports.*;

import java.util.HashMap;
import java.util.Map;

public class ApplicationIReportView extends JasperReportsMultiFormatView {
	private JasperReport jasperReport;
	
	public ApplicationIReportView() {
		super();
		Map<String, Class<? extends AbstractJasperReportsView>> formatMappings = new HashMap(5);
		formatMappings.put("csv", JasperReportsCsvView.class);
		formatMappings.put("html", JasperReportsHtmlView.class);
		formatMappings.put("pdf", JasperReportsPdfView.class);
		formatMappings.put("xls", JasperReportsXlsView.class);
		formatMappings.put("image", JasperReportsImageView.class);

		setFormatMappings(formatMappings);
	}

	protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
		if (model.containsKey("url")) {
			setUrl(String.valueOf(model.get("url")));
			this.jasperReport = loadReport();
		}
		
		return super.fillReport(model);
	}
	
	protected JasperReport getReport() {
		return this.jasperReport;
	}
}
