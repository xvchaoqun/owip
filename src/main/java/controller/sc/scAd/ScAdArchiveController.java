package controller.sc.scAd;

import bean.CadreInfoForm;
import controller.sc.ScBaseController;
import domain.cadre.CadreView;
import domain.cis.CisInspectObj;
import domain.sc.scAd.*;
import freemarker.template.TemplateException;
import mixin.MixinUtils;
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
import persistence.cis.common.ICisMapper;
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
public class ScAdArchiveController extends ScBaseController {

    @Autowired
    private CadreAdformService cadreAdformService;
    @Autowired(required = false)
    private ICisMapper iCisMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scAdArchive:list")
    @RequestMapping("/scAdArchive")
    public String scAdArchive(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/sc/scAdUse";
        }

        return "sc/scAd/scAdArchive/scAdArchive_page";
    }

    @RequiresPermissions("scAdArchive:list")
    @RequestMapping("/scAdArchive_data")
    public void scAdArchive_data(HttpServletResponse response,
                                    Integer committeeId,
                                    Integer cadreId,
                                    Integer objId, // 从干部考察报告跳转过来时传递
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

        ScAdArchiveViewExample example = new ScAdArchiveViewExample();
        ScAdArchiveViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (committeeId!=null) {
            criteria.andCommitteeIdEqualTo(committeeId);
        }
        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if(objId!=null){
            criteria.andObjIdEqualTo(objId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scAdArchive_export(example, response);
            return;
        }

        long count = scAdArchiveViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScAdArchiveView> records= scAdArchiveViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scAdArchive.class, scAdArchiveMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping(value = "/scAdArchive_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdArchive_au(int committeeId, @RequestParam(value = "cadreIds[]") Integer[] cadreIds,
                                 HttpServletRequest request) {

        scAdArchiveService.add(committeeId, cadreIds);
        logger.info(addLog(LogConstants.LOG_SC_AD, "添加干部任免审批表：%s, %s",
                committeeId, StringUtils.join(cadreIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping("/scAdArchive_au")
    public String scAdArchive_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScAdArchiveWithBLOBs scAdArchive = scAdArchiveMapper.selectByPrimaryKey(id);
            modelMap.put("scAdArchive", scAdArchive);
        }
        return "sc/scAd/scAdArchive/scAdArchive_au";
    }

    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping("/scAdArchive_selectVotes")
    public String scAdArchive_selectVotes(int archiveId, Integer cadreId, ModelMap modelMap) {

        ScAdArchiveVoteExample example = new ScAdArchiveVoteExample();
        example.createCriteria().andArchiveIdEqualTo(archiveId);
        List<ScAdArchiveVote> scAdArchiveVotes = scAdArchiveVoteMapper.selectByExample(example);

        modelMap.put("selectedVotes", scAdArchiveVotes);

        modelMap.put("scCommitteeVotes", iScMapper.selectScAdVotes(archiveId, cadreId));

        return "sc/scAd/scAdArchive/scAdArchive_selectVotes";
    }

    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping("/scAdArchive_preview")
    public String scAdArchive_preview(int archiveId,
                                      @RequestParam(required = false, value = "voteIds[]") Integer[] voteIds,
                                      ModelMap modelMap) throws IOException {

        // logger.info(addLog(LogConstants.LOG_SC_AD, "预览干部任免审批表：%s"));
        CadreInfoForm cadreAdForm = null;

        if(voteIds==null || voteIds.length==0){

            ScAdArchiveWithBLOBs scAdArchive = scAdArchiveMapper.selectByPrimaryKey(archiveId);
            String adform = scAdArchive.getAdform();
            cadreAdForm = XmlSerializeUtils.unserialize(adform, CadreInfoForm.class);
        }else {
            cadreAdForm = scAdArchiveService.getCadreAdForm(archiveId, voteIds);
        }

        modelMap.put("bean", cadreAdForm);

        return "sc/scAd/scAdArchive/scAdArchive_preview";
    }

    @RequiresPermissions("scAdArchive:del")
    @RequestMapping(value = "/scAdArchive_checkVotes", method = RequestMethod.POST)
    @ResponseBody
    public Map scAdArchive_checkVotes(Integer archiveId, @RequestParam(value = "voteIds[]") Integer[] voteIds) {

        scAdArchiveService.checkVotes(archiveId, voteIds);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping(value = "/scAdArchive_save", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdArchive_save(Integer archiveId, @RequestParam(value = "voteIds[]") Integer[] voteIds) {

        scAdArchiveService.save(archiveId, voteIds);
        logger.info(addLog(LogConstants.LOG_SC_AD, "归档保存干部任免审批表：%s, %s",
                archiveId, StringUtils.join(voteIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    // 干部任免审批表下载
    @RequiresPermissions("scAdArchive:download")
    @RequestMapping("/scAdArchive_download")
    public void scAdArchive_download(int archiveId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        ScAdArchiveWithBLOBs scAdArchive = scAdArchiveMapper.selectByPrimaryKey(archiveId);
        Integer cadreId = scAdArchive.getCadreId();
        CadreView cadre = cadreService.findAll().get(cadreId);
        String adform = scAdArchive.getAdform();
        if(StringUtils.isBlank(adform)) return;

        Date saveTime = scAdArchive.getAdformSaveTime();
        //输出文件 (系统一开始没设定归档时间，所以部分数据无归档时间）
        String filename = StringUtils.trimToEmpty(DateUtils.formatDate(saveTime, "yyyy.MM.dd")) + " 干部任免审批表 " + cadre.getRealname();
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");

        CadreInfoForm bean = XmlSerializeUtils.unserialize(adform, CadreInfoForm.class);
        cadreAdformService.process(bean, response.getWriter());
    }

    // 干部考察报告
    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping("/scAdArchive_selectCisInspectObj")
    public String scAdArchive_selectCisInspectObj(int archiveId, Integer cadreId, ModelMap modelMap) {

        ScAdArchive scAdArchive = scAdArchiveMapper.selectByPrimaryKey(archiveId);
        Integer objId = scAdArchive.getObjId();
        modelMap.put("selectedObjId", objId);

        modelMap.put("cisInspectObjs", iCisMapper.selectScAdCisInspectObjs(archiveId, cadreId));

        return "sc/scAd/scAdArchive/scAdArchive_selectCisInspectObj";
    }

    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping("/scAdArchive_cisPreview")
    public String scAdArchive_cisPreview(int archiveId, Integer objId, ModelMap modelMap) throws IOException, TemplateException {

        // logger.info(addLog(LogConstants.LOG_SC_AD, "预览干部考察报告：%s"));
        Map<String, Object> dataMap = new HashMap<>();
        if(objId!=null){
            CisInspectObj cisInspectObj = scAdArchiveService.getCisInspectObj(archiveId, objId);
            dataMap = scAdArchiveService.getCisInspectObjService().getDataMap(cisInspectObj);
        }else{
            ScAdArchiveWithBLOBs scAdArchive = scAdArchiveMapper.selectByPrimaryKey(archiveId);
            String cis = scAdArchive.getCis();
            if(StringUtils.isNotBlank(cis))
                dataMap = XmlSerializeUtils.unserialize(cis, HashMap.class);
        }

        modelMap.put("dataMap", dataMap);
        return "sc/scAd/scAdArchive/scAdArchive_cisPreview";
    }

    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping(value = "/scAdArchive_cisSave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdArchive_cisSave(Integer archiveId, int objId) throws IOException, TemplateException {

        scAdArchiveService.cisSave(archiveId, objId);
        logger.info(addLog(LogConstants.LOG_SC_AD, "归档保存干部考察报告：%s, %s", archiveId, objId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdArchive:download")
    @RequestMapping("/scAdArchive_cisDownload")
    public void scAdArchive_cisDownload(int archiveId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        ScAdArchiveWithBLOBs scAdArchive = scAdArchiveMapper.selectByPrimaryKey(archiveId);
        Integer cadreId = scAdArchive.getCadreId();
        CadreView cadre = cadreService.findAll().get(cadreId);
        String cis = scAdArchive.getCis();
        if(StringUtils.isBlank(cis)) return;

        Date saveTime = scAdArchive.getCisSaveTime();

        //输出文件
        String filename = StringUtils.trimToEmpty(DateUtils.formatDate(saveTime, "yyyy.MM.dd")) + cadre.getRealname() + "同志考察材料";
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");

        scAdArchiveService.getCisInspectObjService().process(XmlSerializeUtils.unserialize(cis, HashMap.class), response.getWriter());
    }


    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping(value = "/scAdArchive_archive", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scAdArchive_archive(int id,
                                      String remark,
                                      MultipartFile _filePath,
                                      MultipartFile _cisFilePath,
                                      MultipartFile _signFilePath,
                                      MultipartFile _cisSignFilePath,
                                 HttpServletRequest request) throws IOException, InterruptedException {

        ScAdArchiveWithBLOBs record = new ScAdArchiveWithBLOBs();
        record.setId(id);
        record.setFilePath(upload(_filePath, "scAdArchive-word"));
        record.setSignFilePath(uploadPdf(_signFilePath, "scAdArchive-sign"));
        record.setCisFilePath(upload(_cisFilePath, "scAdArchive-word"));
        record.setCisSignFilePath(uploadPdf(_cisSignFilePath, "scAdArchive-sign"));
        record.setRemark(remark);

        scAdArchiveMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_SC_AD, "正式归档：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scAdArchive:edit")
    @RequestMapping("/scAdArchive_archive")
    public String scAdArchive_archive(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScAdArchive scAdArchive = scAdArchiveMapper.selectByPrimaryKey(id);
            modelMap.put("scAdArchive", scAdArchive);
        }
        return "sc/scAd/scAdArchive/scAdArchive_archive";
    }

    @RequiresPermissions("scAdArchive:del")
    @RequestMapping(value = "/scAdArchive_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scAdArchive_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scAdArchiveService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_AD, "批量删除干部任免审批表：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void scAdArchive_export(ScAdArchiveViewExample example, HttpServletResponse response) {

        List<ScAdArchiveView> records = scAdArchiveViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"党委常委会|100","干部|100","干部任免审批表签字扫描件|100","干部考察材料签字扫描件|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScAdArchiveView record = records.get(i);
            String[] values = {
                record.getCommitteeId()+"",
                            record.getCadreId()+"",
                            record.getSignFilePath(),
                            record.getCisSignFilePath(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部任免审批表_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
