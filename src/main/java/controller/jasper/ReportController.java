package controller.jasper;

import controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("/printPreview")
    public String printPreview() {

        return "jasper/print_preview";
    }

    /**
     * 返回iReport报表视图
     * @param model
     * @return
     */
    /*@RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report(Model model) throws IOException, DocumentException {
        // 报表数据源
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(JavaBeanPerson.getList());

        ///BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/report1.jasper");
        model.addAttribute("format", "html"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }*/
}
