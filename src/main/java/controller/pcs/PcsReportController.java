package controller.pcs;

import domain.base.MetaType;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsProposalFile;
import domain.pcs.PcsProposalView;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;
import sys.constants.PcsConstants;
import sys.utils.ConfigUtil;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/1/12.
 */
@Controller
@RequestMapping("/report")
public class PcsReportController extends PcsBaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());
    @RequiresPermissions("pcsProposalOw:*")
    @RequestMapping(value = "/pcsProposal", method = RequestMethod.GET)
    public String pcsProposal(@RequestParam(value = "ids[]")int[] ids,
                      @RequestParam(defaultValue = "pdf") String format,
                      @RequestParam(defaultValue = "0") int type, // type=1 导出pdf type=2导出word
                      HttpServletResponse response,
                      HttpServletRequest request,
                      Model model)
            throws IOException, JRException {

        List<Map<String, ?>> pcsProposalData = new ArrayList<Map<String, ?>>();

        for (int id : ids) {

            PcsProposalView pcsProposal = pcsProposalViewMapper.selectByPrimaryKey(id);
            Map<String, Object> pcsProposalMap = getPcsProposalMap(pcsProposal, type);
            pcsProposalData.add(pcsProposalMap);
        }

        if (type == 1) {
            exportPdf("jasper/pcs_proposal.jasper", pcsProposalData, "党员代表提案.pdf", response);
            return null;
        }
        if (type == 2) {
            exportDoc("jasper/pcs_proposal.jasper", pcsProposalData, "党员代表提案.docx", response);
            return null;
        }

        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(pcsProposalData);
        model.addAttribute("url", "/WEB-INF/jasper/pcs_proposal.jasper");
        model.addAttribute("format", format); // 报表格式
        if(format.equals("image")){
            model.addAttribute("image.zoom", 2f);
        }
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }

    public Map<String, Object> getPcsProposalMap(PcsProposalView pcsProposal, int type) throws UnsupportedEncodingException {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        PcsPrCandidateView candidate = pcsPrCandidateService.find(pcsProposal.getUserId(), configId, PcsConstants.PCS_STAGE_SECOND);

        Map<Integer, MetaType> prTypes = metaTypeService.metaTypes("mc_pcs_proposal");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", candidate.getCode());
        map.put("realname", candidate.getRealname());
        map.put("mobile", candidate.getMobile());
        map.put("unitName", candidate.getUnitName());
        map.put("email", candidate.getEmail());
        map.put("createDate",  DateUtils.formatDate(pcsProposal.getCreateTime(), "yyyy.MM.dd"));
        map.put("name", HtmlUtils.htmlUnescape(pcsProposal.getName()));
        map.put("keywords", HtmlUtils.htmlUnescape(pcsProposal.getKeywords()));
        MetaType metaType = prTypes.get(pcsProposal.getType());
        map.put("type", metaType==null?"":metaType.getName());

        String _content = pcsProposal.getContent();
        String[] strs = _content.split("\n");
        StringBuffer content = new StringBuffer();
        for (int i=0; i<strs.length; i++) {
            if(i>0 && type==2){
                content.append("　　"); // 导出WORD时，需要多加两个全角空格
            }
            content.append(StringUtils.trimToEmpty(strs[i]) + "\n");
        }
        //map.put("content", content.toString());
        map.put("content", HtmlUtils.htmlUnescape(content.toString()));
        //map.put("content", HtmlUtils.htmlUnescape(_content));
        String files = "";
        List<PcsProposalFile> _files = pcsProposal.getFiles();
        for (PcsProposalFile file : _files) {
            files += HtmlUtils.htmlUnescape(file.getFileName()) + "\r\n";
        }
        map.put("files", files);

        map.put("seconders", new JRMapCollectionDataSource( getSeconderMapList(pcsProposal)));

        return map;
    }

    public List<Map<String, ?>> getSeconderMapList(PcsProposalView pcsProposal){

        List<Map<String, ?>> seconderMapList = new ArrayList<Map<String, ?>>();

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        List<PcsPrCandidateView> candidates = pcsProposalService.getSeconderCandidates(configId, pcsProposal);

        for (PcsPrCandidateView candidate : candidates) {

            Map<String, Object> seconderMap = new HashMap<String, Object>();
            seconderMap.put("realname", candidate.getRealname());
            seconderMap.put("unitName", candidate.getUnitName());
            seconderMap.put("mobile", candidate.getMobile());
            seconderMap.put("email", candidate.getEmail());

            seconderMapList.add(seconderMap);
        }

        return seconderMapList;
    }


    public static void exportPdf(String jasperFile,
                                 List<Map<String, ?>> pcsProposalData,
                                 String pdfFileName,
                                 HttpServletResponse response) throws IOException, JRException {

        File jasper = new File(ConfigUtil.defaultConfigPath() + File.separator + jasperFile);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasper.getPath());
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(pcsProposalData);

        Map<String,Object> parameters = new HashMap<>();
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


    public static void exportDoc(String jasperFile,
                                 List<Map<String, ?>> pcsProposalData,
                                 String pdfFileName,
                                 HttpServletResponse response) throws IOException, JRException {

        File jasper = new File(ConfigUtil.defaultConfigPath() + File.separator + jasperFile);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasper.getPath());
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(pcsProposalData);

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("net.sf.jasperreports.awt.ignore.missing.font", true);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, collectionDataSource);

        JRDocxExporter exporter = new JRDocxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

        SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
        //可以通过configuration对象设置输出pdf文档的各种属性
        //configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);

        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(pdfFileName, "UTF-8"));
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setCharacterEncoding("UTF-8");

        exporter.exportReport();
    }
}
