package controller.jasper;

import com.lowagie.text.DocumentException;
import controller.BaseController;
import domain.Cadre;
import domain.SysUser;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sys.tags.CmTag;
import sys.utils.ConfigUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    @RequestMapping(value = "/passportApply", method = RequestMethod.GET)
    public String country(int classId, int userId, Model model) throws IOException, DocumentException {

        SysUser user = sysUserService.findById(userId);
        Cadre cadre = cadreService.findByUserId(userId);
        String unit = unitService.findAll().get(cadre.getUnitId()).getName();
        String title = cadre.getTitle();

        String to = "country";
        if(CmTag.typeEqualsCode(classId, "mt_hk"))
            to = "hk";
        if(CmTag.typeEqualsCode(classId, "mt_tw"))
            to = "tw";

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getRealname());
        map.put("locate", "北京市");
        map.put("idcard", user.getIdcard());
        map.put("unit", unit);
        map.put("title", title);
        map.put("imagePath", ConfigUtil.defaultConfigPath() + File.separator + "jasper" + File.separator + to + ".jpg" );

        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/country.jasper");

        model.addAttribute("format", "pdf"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

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
