package controller.jasper;

import com.lowagie.text.DocumentException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
public class ReportController {

    /**
     * 返回iReport报表视图
     * @param model
     * @return
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report(Model model) throws IOException, DocumentException {
        // 报表数据源
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(JavaBeanPerson.getList());

        ///BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/report1.jasper");
        model.addAttribute("format", "html"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }
}
