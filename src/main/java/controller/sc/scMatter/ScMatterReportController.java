package controller.sc.scMatter;

import domain.sc.scMatter.ScMatterAccess;
import domain.sc.scMatter.ScMatterAccessItemView;
import domain.sc.scMatter.ScMatterAccessItemViewExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sys.utils.ConfigUtil;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScMatterReportController extends ScMatterBaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/scMatterAccess_report", method = RequestMethod.GET)
    public String scMatterAccess_report(int id,
                      @RequestParam(defaultValue = "pdf") String format,
                      @RequestParam(defaultValue = "0") int export,
                      HttpServletResponse response,
                      HttpServletRequest request,
                      Model model)
            throws IOException, JRException {

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();

        ScMatterAccess scMatterAccess = scMatterAccessMapper.selectByPrimaryKey(id);
        Integer unitId = scMatterAccess.getUnitId();
        Unit unit = unitService.findAll().get(unitId);
        Date accessDate = scMatterAccess.getAccessDate();
        String msg = MessageFormat.format("贵单位{0}于{1}来函，因工作需要调阅领导干部个人有关事项。材料明细如下，请予以接收。",
                unit.getName(), DateUtils.formatDate(accessDate, DateUtils.YYYY_MM_DD_CHINA));

        map.put("msg", msg);
        map.put("unitName", unit.getName());

        Integer handleUserId = scMatterAccess.getHandleUserId();
        if(handleUserId!=null){
            SysUserView uv = sysUserService.findById(handleUserId);
            map.put("operator", uv.getRealname());
        }
        Date handleDate = scMatterAccess.getHandleDate();
        if(handleDate!=null)
            map.put("opDate", DateUtils.formatDate(handleDate, DateUtils.YYYY_MM_DD_CHINA));
        data.add(map);

        List<Map<String, ?>> itemData = new ArrayList<Map<String, ?>>();
        ScMatterAccessItemViewExample example = new ScMatterAccessItemViewExample();
        example.createCriteria().andAccessIdEqualTo(id);
        example.setOrderByClause("id asc");
        List<ScMatterAccessItemView> scMatterAccessItemViews = scMatterAccessItemViewMapper.selectByExample(example);
        for (int i = 0; i < scMatterAccessItemViews.size(); i++) {
            ScMatterAccessItemView scMatterAccessItemView = scMatterAccessItemViews.get(i);
            Map<String, Object> _map = new HashMap<String, Object>();
            _map.put("idx", i+1);
            _map.put("realname", scMatterAccessItemView.getRealname());
            _map.put("title", scMatterAccessItemView.getTitle());
            _map.put("fillTime", DateUtils.formatDate(scMatterAccessItemView.getFillTime(), "yyyy.MM.dd"));
            _map.put("remark", scMatterAccessItemView.getType()?"原件":"复印件");

            itemData.add(_map);
        }

        if (export == 1) {
            exportPdf("jasper/sc_matter_access.jasper", data, itemData, "领导干部个人有关事项调阅接收单.pdf", response);
            return null;
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);
        model.addAttribute("url", "/WEB-INF/jasper/sc_matter_access.jasper");
        model.addAttribute("format", format); // 报表格式
        if(format.equals("image")){
            model.addAttribute("image.zoom", 2f);
        }
        model.addAttribute("jrMainDataSource", jrDataSource);

        JRDataSource jrDataSource2 = new JRMapCollectionDataSource(itemData);
        model.addAttribute("items", jrDataSource2);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    @RequestMapping("/printPreview")
    public String printPreview() {

        return "jasper/print_preview";
    }

    public static void exportPdf(String jasperFile,
                                 List<Map<String, ?>> data,
                                 List<Map<String, ?>> itemData,
                                 String pdfFileName,
                                 HttpServletResponse response) throws IOException, JRException {

        File jasper = new File(ConfigUtil.defaultConfigPath() + File.separator + jasperFile);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasper.getPath());
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(data);

        JRDataSource jrDataSource2 = new JRMapCollectionDataSource(itemData);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("items", jrDataSource2);
        parameters.put("net.sf.jasperreports.awt.ignore.missing.font", true);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, collectionDataSource);

        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        //可以通过configuration对象设置输出pdf文档的各种属性
        //configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);

        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(pdfFileName, "UTF-8"));
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");

        exporter.exportReport();
    }
}
