package controller.sc.scAd;

import bean.CadreInfoForm;
import controller.sc.ScBaseController;
import domain.cadre.CadreView;
import domain.sc.scAd.ScAdUse;
import domain.sc.scAd.ScAdUseExample;
import domain.sc.scAd.ScAdUseExample.Criteria;
import freemarker.template.TemplateException;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.cadre.CadreAdformService;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScAdUseController extends ScBaseController {

    @Autowired
    private CadreAdformService cadreAdformService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scAdUse:list")
    @RequestMapping("/scAdUse")
    public String scAdUse(@RequestParam(defaultValue = "1") Integer cls,
                          ModelMap modelMap) {

        modelMap.put("cls", cls);
        
        return "sc/scAd/scAdUse/scAdUse_page";
    }

    @RequiresPermissions("scAdUse:list")
    @RequestMapping("/scAdUse_data")
    public void scAdUse_data(HttpServletResponse response,
                                    Integer year,
                                    String useDate,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScAdUseExample example = new ScAdUseExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scAdUse_export(example, response);
            return;
        }

        long count = scAdUseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScAdUse> records= scAdUseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scAdUse.class, scAdUseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scAdUse:edit")
    @RequestMapping(value = "/scAdUse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdUse_au(ScAdUse record, HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {
            scAdUseService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_AD, "添加干部任免审批表：%s", record.getId()));
        } else {

            scAdUseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_AD, "更新干部任免审批表：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdUse:edit")
    @RequestMapping("/scAdUse_au")
    public String scAdUse_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScAdUse scAdUse = scAdUseMapper.selectByPrimaryKey(id);
            modelMap.put("scAdUse", scAdUse);
        }
        return "sc/scAd/scAdUse/scAdUse_au";
    }

    // 干部任免审批表
    @RequiresPermissions("scAdUse:edit")
    @RequestMapping("/scAdUse_preview")
    public String scAdUse_preview(int useId, Boolean view, ModelMap modelMap) throws IOException {

        // logger.info(addLog(LogConstants.LOG_SC_AD, "预览干部任免审批表：%s"));
        CadreInfoForm cadreAdForm = null;
        ScAdUse scAdUse = scAdUseMapper.selectByPrimaryKey(useId);
        if(BooleanUtils.isTrue(view)){
            String adform = scAdUse.getAdform();
            cadreAdForm = XmlSerializeUtils.unserialize(adform, CadreInfoForm.class);
        }else {
            cadreAdForm = cadreAdformService.getCadreAdform(scAdUse.getCadreId());
        }

        modelMap.put("bean", cadreAdForm);

        return "sc/scAd/scAdUse/scAdUse_preview";
    }

    @RequiresPermissions("scAdUse:edit")
    @RequestMapping(value = "/scAdUse_save", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdUse_save(Integer useId) {

        scAdUseService.save(useId);
        logger.info(addLog(LogConstants.LOG_SC_AD, "归档保存干部任免审批表：%s", useId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdUse:download")
    @RequestMapping("/scAdUse_download")
    public void scAdUse_download(int useId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        ScAdUse scAdUse = scAdUseMapper.selectByPrimaryKey(useId);
        Integer cadreId = scAdUse.getCadreId();
        CadreView cadre = cadreService.findAll().get(cadreId);
        String adform = scAdUse.getAdform();
        if(StringUtils.isBlank(adform)) return;

        //输出文件
        String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd") + " 干部任免审批表 " + cadre.getRealname();
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");

        CadreInfoForm bean = XmlSerializeUtils.unserialize(adform, CadreInfoForm.class);
        cadreAdformService.process(bean, response.getOutputStream());
    }

    @RequiresPermissions("scAdUse:edit")
    @RequestMapping(value = "/scAdUse_archive", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdUse_archive(int id,
                                      String remark,
                                      MultipartFile _filePath,
                                      MultipartFile _signFilePath,
                                      HttpServletRequest request) throws IOException, InterruptedException {

        ScAdUse record = new ScAdUse();
        record.setId(id);
        record.setFilePath(upload(_filePath, "scAdUse-word"));
        record.setSignFilePath(uploadPdf(_signFilePath, "scAdUse-sign"));
        record.setRemark(remark);

        scAdUseMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_SC_AD, "正式归档：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdUse:edit")
    @RequestMapping("/scAdUse_archive")
    public String scAdUse_archive(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScAdUse scAdUse = scAdUseMapper.selectByPrimaryKey(id);
            modelMap.put("scAdUse", scAdUse);
        }
        return "sc/scAd/scAdUse/scAdUse_archive";
    }

    @RequiresPermissions("scAdUse:del")
    @RequestMapping(value = "/scAdUse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scAdUse_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scAdUseService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_AD, "批量删除干部任免审批表：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scAdUse_export(ScAdUseExample example, HttpServletResponse response) {

        List<ScAdUse> records = scAdUseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份|100","日期|100","是否校内单位|100","校内单位|100","校外单位|100","干部|100","干部任免审批表签字扫描件|100","用途|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScAdUse record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            DateUtils.formatDate(record.getUseDate(), DateUtils.YYYY_MM_DD),
                            record.getIsOnCampus() +"",
                            record.getUnitId()+"",
                            record.getOutUnit(),
                            record.getCadreId()+"",
                            record.getSignFilePath(),
                            record.getUseage(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部任免审批表_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("scAdUse:list")
    @RequestMapping("/scAdUse_down")
    public void scAdUse_download(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ScAdUse scAdUse = scAdUseMapper.selectByPrimaryKey(id);
        String path = scAdUse.getSignFilePath();
        String filename = "干部任免审批表归档扫描件("+ scAdUse.getCadre().getRealname()+")";

        DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
    }
}
