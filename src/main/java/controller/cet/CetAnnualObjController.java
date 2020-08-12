package controller.cet;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cet.*;
import domain.cet.CetAnnualObjExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetAnnualObjController extends CetBaseController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    //@RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj_detail")
    public String cetAnnualObj_detail(Integer objId, Integer year, ModelMap modelMap) {

        CetAnnualObj cetAnnualObj = null;
        CetAnnual cetAnnual = null;

        if(objId != null){

            SecurityUtils.getSubject().checkPermission("cetAnnualObj:list");
            cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        }else{
            // 本人查看
            Integer userId = ShiroHelper.getCurrentUserId();
            List<Integer> annualYears = iCetMapper.getAnnualYears(userId);
            modelMap.put("years", annualYears);
            if(year == null && annualYears.size()>0){
                year = annualYears.get(0);
            }
            if(year!=null) {
                cetAnnualObj = iCetMapper.getCetAnnualObj(userId, year);
            }
        }

        modelMap.put("year", year);
        modelMap.put("cetAnnualObj", cetAnnualObj);

        if(cetAnnualObj!=null) {
            Integer annualId = cetAnnualObj.getAnnualId();
            cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
            modelMap.put("cetAnnual", cetAnnual);
        }
        
        return "cet/cetAnnualObj/cetAnnualObj_detail";
    }
    
    
    //@RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj_items")
    public String cetAnnualObj_items(int objId, @RequestParam(defaultValue = "0") Boolean isValid, ModelMap modelMap) {

        CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        int userId = cetAnnualObj.getUserId();
        if(userId!= ShiroHelper.getCurrentUserId()){
            // 非本人查看，需要检查权限
            SecurityUtils.getSubject().checkPermission("cetAnnualObj:list");
        }

        Integer annualId = cetAnnualObj.getAnnualId();
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        int year = cetAnnual.getYear();
        int traineeTypeId = cetAnnual.getTraineeTypeId();

        List<CetRecord> cetRecords = cetRecordService.getRecords(year, userId, traineeTypeId,null, isValid);
        modelMap.put("cetRecords", cetRecords);
        
        return "cet/cetAnnualObj/cetAnnualObj_items";
    }
    
    
    @RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj")
    public String cetAnnualObj(Integer userId, @RequestParam(defaultValue = "0") Boolean isQuit,
                               Integer[] adminLevels,
                               Integer[] postTypes,
                               ModelMap modelMap) {
        
        modelMap.put("isQuit", isQuit);
        
        if (adminLevels != null) {
            modelMap.put("selectAdminLevels", Arrays.asList(adminLevels));
        }
        if (postTypes != null) {
            modelMap.put("selectPostTypes", Arrays.asList(postTypes));
        }
        
        if (userId != null)
            modelMap.put("sysUser", CmTag.getUserById(userId));
        
        return "cet/cetAnnualObj/cetAnnualObj_page";
    }
    
    @RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj_data")
    @ResponseBody
    public void cetAnnualObj_data(HttpServletResponse response,
                                  HttpServletRequest request,
                                  int annualId,
                                  Integer userId,
                                  @RequestParam(defaultValue = "0") Boolean isQuit,
                                  Integer[] adminLevels,
                                  Integer[] postTypes,
                                  Boolean isFinishedOffline,
                                  Boolean isFinishedOnline,
                                  Boolean needUpdateRequire,
                                  Byte sortBy,
                                  Boolean displayFinishedOffline,
                                  Boolean displayFinishedOnline,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {
        
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        Criteria criteria = example.createCriteria()
                .andAnnualIdEqualTo(annualId)
                .andIsQuitEqualTo(isQuit);
        if (NumberUtils.byteEqual(sortBy, (byte)1)) {
            example.setOrderByClause("finish_period_offline desc, finish_period_online desc, sort_order desc");
        } else {
            example.setOrderByClause("sort_order desc");
        }
        
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        
        if (adminLevels != null) {
            criteria.andAdminLevelIn(Arrays.asList(adminLevels));
        }
        if (postTypes != null) {
            criteria.andPostTypeIn(Arrays.asList(postTypes));
        }
        if (isFinishedOffline != null) {
            criteria.isFinishedOffline(BooleanUtils.isTrue(isFinishedOffline));
        }
        if(displayFinishedOffline!=null) {
            criteria.isFinishedOffline(BooleanUtils.isTrue(displayFinishedOffline));
        }
        if (isFinishedOnline != null) {
            criteria.isFinishedOnline(BooleanUtils.isTrue(isFinishedOnline));
        }
        if(displayFinishedOnline!=null) {
            criteria.isFinishedOnline(BooleanUtils.isTrue(displayFinishedOnline));
        }
        
        if (needUpdateRequire != null) {
            criteria.andNeedUpdateRequireEqualTo(BooleanUtils.isTrue(needUpdateRequire));
        }
        
        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetAnnualObj_export(annualId, example, request, response);
            return;
        }
        
        long count = cetAnnualObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            
            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetAnnualObj> records = cetAnnualObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetAnnualObj.class, cetAnnualObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

     @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/archiveFinishPeriod", method = RequestMethod.POST)
    @ResponseBody
    public Map do_archiveFinishPeriod(int annualId,
                                      //boolean isQuit,
                                      HttpServletRequest request) {

        cetAnnualObjService.archiveFinishPeriod(annualId);

        logger.info(addLog(LogConstants.LOG_CET, "归档已完成学时： %s", annualId));

        return success(FormUtils.SUCCESS);
    }

	@RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/archiveObjFinishPeriod", method = RequestMethod.POST)
    @ResponseBody
    public Map do_archiveObjFinishPeriod(int annualId,
                                         Integer[] ids,
                                         //boolean isQuit,
                                         HttpServletRequest request) {

        cetAnnualObjService.archiveObjFinishPeriod(ids);

        logger.info(addLog(LogConstants.LOG_CET, "归档已完成学时： %s, %s", annualId, StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_sync(int annualId,
                                    HttpServletRequest request) {
        
        int adminLevelChangedCount = cetAnnualObjService.sync(annualId);
        logger.info(addLog(LogConstants.LOG_CET, "同步培训对象信息： %s", annualId));
        
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("adminLevelChangedCount", adminLevelChangedCount);
        
        return resultMap;
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_au(CetAnnualObj cetAnnualObj,HttpServletRequest request,
                                   Integer[] identities) {

        cetAnnualObj.setIdentity(StringUtils.trimToNull(StringUtils.join(identities, ",")) == null ?
                "" : StringUtils.join(identities, ","));
        cetAnnualObjService.addOrUpdate(cetAnnualObj);
        logger.info(addLog(LogConstants.LOG_CET, "编辑培训对象： %s, %s", cetAnnualObj.getAnnualId(), cetAnnualObj.getUserId()));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping("/cetAnnualObj_au")
    public String cetAnnualObj_au(Integer id, int annualId, ModelMap modelMap) {

        if(id!=null){
            CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(id);
            annualId = cetAnnualObj.getAnnualId();
            modelMap.put("cetAnnualObj", cetAnnualObj);
        }

        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        modelMap.put("cetAnnual", cetAnnual);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(cetAnnual.getTraineeTypeId());
        modelMap.put("cetTraineeType", cetTraineeType);

        return "cet/cetAnnualObj/cetAnnualObj_au";
    }

    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping("/cetAnnualObj_import")
    public String cetAnnualObj_import(Integer annualId, ModelMap modelMap){

         CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        modelMap.put("cetAnnual", cetAnnual);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(cetAnnual.getTraineeTypeId());
        modelMap.put("cetTraineeType", cetTraineeType);

        return "cet/cetAnnualObj/cetAnnualObj_import";
    }

    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_import(int annualId, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CetAnnualObj> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            CetAnnualObj record = new CetAnnualObj();
            row++;
            record.setAnnualId(annualId);

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)){
                throw new OpException("Excel中第{0}行学工号不能为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getUserId());
            record.setTitle(StringUtils.trimToNull(xlsRow.get(2)));
            MetaType metaType = metaTypeService.findByName("mc_post", xlsRow.get(3));
            if (metaType != null){
                record.setPostType(metaType.getId());
            }
            String _identity = StringUtils.trim(xlsRow.get(4));
            if (StringUtils.isNotBlank(_identity)) {
                String[] identities = _identity.split(",|，|、");
                List<Integer> identityList = new ArrayList<>();
                for (String s : identities) {
                    MetaType metaType1 = metaTypeService.findByName("mc_cet_identity", s);
                    if (metaType1 != null) {
                        identityList.add(metaType1.getId());
                    }
                }
                if(identityList.size()>0) {
                    record.setIdentity(StringUtils.join(identityList, ","));
                }
            }else {
                record.setIdentity(""); // 为了更新时覆盖
            }
            records.add(record);
        }
        int successCount = cetAnnualObjService.importCetProjectObj(records);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount",successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping("/cetAnnualObj_selectCadres_tree")
    @ResponseBody
    public Map cetAnnualObj_selectCadres_tree(int annualId) throws IOException {
        
        Set<Integer> selectIdSet = cetAnnualObjService.getSelectedAnnualObjUserIdSet(annualId);
        
        Set<Byte> cadreStatusList = new HashSet<>();
        cadreStatusList.add(CadreConstants.CADRE_STATUS_CJ);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);
        
        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_quit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_quit(boolean isQuit,
                                    Integer[] ids,
                                    HttpServletRequest request) {
        
        cetAnnualObjService.quit(isQuit, ids);
        logger.info(addLog(LogConstants.LOG_CET, "培训对象： %s, %s", isQuit ? "退出" : "返回",
                StringUtils.join(ids, ",")));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_updateRequire", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_updateRequire(BigDecimal periodOffline, BigDecimal periodOnline,
                                             Integer[] ids) {
        
        cetAnnualObjService.updateRequire(periodOffline, periodOnline, ids);
        logger.info(addLog(LogConstants.LOG_CET, "设定年度学习任务：periodOffline=%s, periodOnline=%s, %s",
                periodOffline, periodOnline,
                StringUtils.join(ids, ",")));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping("/cetAnnualObj_updateRequire")
    public String cetAnnualObj_updateRequire() {
        
        return "cet/cetAnnualObj/cetAnnualObj_updateRequire";
    }
    
    @RequiresPermissions("cetAnnualObj:del")
    @RequestMapping(value = "/cetAnnualObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetAnnualObj_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {
        
        
        if (null != ids && ids.length > 0) {
            cetAnnualObjService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除年度学习档案包含的培训对象：%s", StringUtils.join(ids, ",")));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:changeOrder")
    @RequestMapping(value = "/cetAnnualObj_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {
        
        cetAnnualObjService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "年度学习档案包含的培训对象调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    
    // 导出明细表
    @RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj_exportDetails")
    public void cetAnnual_exportObjs(int objId, HttpServletResponse response) throws IOException {
        
        CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        Integer annualId = cetAnnualObj.getAnnualId();
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        
        Map<Integer, CetTraineeType> traineeTypeMap = cetTraineeTypeService.findAll();
        Integer traineeTypeId = cetAnnual.getTraineeTypeId();
        CetTraineeType cetTraineeType = traineeTypeMap.get(traineeTypeId);
        String typeName = cetTraineeType.getName();
        
        XSSFWorkbook wb = cetExportService.cetAnnual_exportObjDetails(cetAnnualObj, cetAnnual, typeName);
        
        String filename = String.format("%s%s%s年度培训学习明细表（%s）.xlsx",
                CmTag.getSysConfig().getSchoolName(), typeName, cetAnnual.getYear(),
                cetAnnualObj.getUser().getRealname());
        ExportHelper.output(wb, filename, response);
        return;
    }
    
    public void cetAnnualObj_export(int annualId, CetAnnualObjExample example,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        Map<Integer, CetTraineeType> traineeTypeMap = cetTraineeTypeService.findAll();
        Integer traineeTypeId = cetAnnual.getTraineeTypeId();
        CetTraineeType cetTraineeType = traineeTypeMap.get(traineeTypeId);
        String typeName = cetTraineeType.getName();
        
        List<CetAnnualObj> records = cetAnnualObjMapper.selectByExample(example);
        
        Map<String, File> fileMap = new LinkedHashMap<>();
        String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "annual" + annualId;
        FileUtils.mkdirs(tmpdir, false);

        Set<String> filenameSet = new HashSet<>();
        for (CetAnnualObj cetAnnualObj : records) {
            
            XSSFWorkbook wb = cetExportService.cetAnnual_exportObjDetails(cetAnnualObj, cetAnnual, typeName);
            SysUserView uv = cetAnnualObj.getUser();
            String filename = String.format("%s%s%s年度培训学习明细表（%s）.xlsx",
                    CmTag.getSysConfig().getSchoolName(), typeName, cetAnnual.getYear(),
                    uv.getRealname());

            // 保证文件名不重复
            if(filenameSet.contains(filename)){
                filename = uv.getCode() + filename;
            }
            filenameSet.add(filename);

            String filepath = tmpdir + FILE_SEPARATOR + filename;
            FileOutputStream output = new FileOutputStream(new File(filepath));
            wb.write(output);
            output.close();
            
            fileMap.put(filename, new File(filepath));
        }
        
        String filename = String.format("%s%s%s年度培训学习明细表",
                    CmTag.getSysConfig().getSchoolName(), typeName, cetAnnual.getYear());
        DownloadUtils.addFileDownloadCookieHeader(response);
        DownloadUtils.zip(fileMap, filename, request, response);
        
        FileUtils.deleteDir(new File(tmpdir));
        return;
    }
}
