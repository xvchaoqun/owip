package controller.cet;

import domain.cet.CetUnitProject;
import domain.cet.CetUnitTrain;
import domain.cet.CetUnitTrainExample;
import domain.cet.CetUnitTrainExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetUnitTrainController extends CetBaseController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequiresPermissions("cetUnitTrain:list")
    @RequestMapping("/cetUnitTrain")
    public String cetUnitTrain(Integer projectId,
                               byte addType,
                               ModelMap modelMap) {
        
        modelMap.put("addType", addType);
        modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(projectId));
        
        return "cet/cetUnitTrain/cetUnitTrain_page";
    }
    
    @RequiresPermissions("cetUnitTrain:list")
    @RequestMapping("/cetUnitTrain_data")
    @ResponseBody
    public void cetUnitTrain_data(HttpServletResponse response,
                                  byte addType,
                                  int projectId,
                                  Integer userId,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {
        
        
        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
        Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIds(CetConstants.CET_UPPER_TRAIN_UNIT, addType);
        if(adminUnitIdSet==null || !adminUnitIdSet.contains(cetUnitProject.getUnitId())){
            throw new UnauthorizedException();
        }
        
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        
        CetUnitTrainExample example = new CetUnitTrainExample();
        Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause("id desc");
        
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        
        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUnitTrain_export(example, response);
            return;
        }
        
        long count = cetUnitTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            
            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUnitTrain> records = cetUnitTrainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetUnitTrain.class, cetUnitTrainMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    
    @RequiresPermissions("cetUnitTrain:edit")
    @RequestMapping(value = "/cetUnitTrain_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_au(CetUnitTrain record,
                                  MultipartFile _word, MultipartFile _pdf,
                                  HttpServletRequest request) throws IOException, InterruptedException {
        
        Integer id = record.getId();
        
        if (cetUnitTrainService.idDuplicate(id, record.getProjectId(), record.getUserId())) {
            
            return failed("添加重复。");
        }
        
        record.setWordNote(upload(_word, "cetUnitTrain_note"));
        record.setPdfNote(uploadPdf(_pdf, "cetUnitTrain_note"));
        
        int projectId = record.getProjectId();
        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
        Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIds(CetConstants.CET_UPPER_TRAIN_UNIT, record.getAddType());
        if(adminUnitIdSet==null || !adminUnitIdSet.contains(cetUnitProject.getUnitId())){
            return failed("没有权限。");
        }
    
        if (id == null) {
            record.setAddTime(new Date());
            record.setAddUserId(ShiroHelper.getCurrentUserId());
            if(record.getAddType()==CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
            }else if(record.getAddType()==CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
            }
            
            cetUnitTrainService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加二级单位培训班培训记录：%s", record.getId()));
        } else {
            
            cetUnitTrainService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新二级单位培训班培训记录：%s", record.getId()));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetUnitTrain:edit")
    @RequestMapping("/cetUnitTrain_au")
    public String cetUnitTrain_au(Integer id,
                                  byte addType,
                                  Integer projectId, ModelMap modelMap) {
        
        modelMap.put("addType", addType);

        if(ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            Set<Integer> unitIds = cetUpperTrainAdminService.adminUnitIds(CetConstants.CET_UPPER_TRAIN_UNIT, addType);
            modelMap.put("unitIds", StringUtils.join(unitIds, ","));
        }
        
        if (id != null) {
            CetUnitTrain cetUnitTrain = cetUnitTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetUnitTrain", cetUnitTrain);
            projectId = cetUnitTrain.getProjectId();
        }
        modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(projectId));
        
        return "cet/cetUnitTrain/cetUnitTrain_au";
    }
    
    @RequiresPermissions("cetUnitTrain:edit")
    @RequestMapping("/cetUnitTrain_batchAdd")
    public String cetUnitTrain_batchAdd(byte addType,
                                        int projectId, ModelMap modelMap) {
        
        modelMap.put("addType", addType);
        
        modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(projectId));
        
        return "cet/cetUnitTrain/cetUnitTrain_batchAdd";
    }
    
    @RequiresPermissions("cetUnitTrain:edit")
    @RequestMapping(value = "/cetUnitTrain_batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_batchAdd(HttpServletRequest request,
                                        int projectId,
                                        byte addType,
                                        @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                                        ModelMap modelMap) {
        
       cetUnitTrainService.batchAdd(projectId, userIds, addType);
        
        return success(FormUtils.SUCCESS);
    }
    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequiresPermissions("cetUnitTrain:check")
    @RequestMapping("/cetUnitTrain_check")
    public String cetUnitTrain_check(@RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {
        
        if(ids!=null && ids.length==1)
            modelMap.put("cetUnitTrain", cetUnitTrainMapper.selectByPrimaryKey(ids[0]));
        
        return "cet/cetUnitTrain/cetUnitTrain_check";
    }
    
    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequiresPermissions("cetUnitTrain:edit")
    @RequestMapping(value = "/cetUnitTrain_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_check(HttpServletRequest request,
                                        @RequestParam(value = "ids[]") Integer[] ids,
                                        Boolean pass, String backReason, ModelMap modelMap) {
    
        if(ids!=null && ids.length>0) {
            
            CetUnitTrain record = new CetUnitTrain();
            record.setStatus(BooleanUtils.isTrue(pass) ? CetConstants.CET_UPPER_TRAIN_STATUS_PASS :
                    CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS);
            record.setBackReason(backReason);
    
            CetUnitTrainExample example = new CetUnitTrainExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cetUnitTrainMapper.updateByExampleSelective(record, example);
    
            if (BooleanUtils.isTrue(pass)) {
                commonMapper.excuteSql("update cet_unit_train set back_reason=null where id in (" + StringUtils.join(ids, ",") + ")");
            }
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetUnitTrain:import")
    @RequestMapping("/cetUnitTrain_import")
    public String cetUnitTrain_import() {

        return "cet/cetUnitTrain/cetUnitTrain_import";
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequiresPermissions("cetUnitTrain:import")
    @RequestMapping(value="/cetUnitTrain_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_import(int projectId,
                                            HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);


        Map<String, Object> retMap = cetUnitTrainService.imports(xlsRows, projectId);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", retMap.get("success"));
        resultMap.put("failedXlsRows", retMap.get("failedXlsRows"));
        resultMap.put("total", xlsRows.size());

        return resultMap;
    }
    
    @RequiresPermissions("cetUnitTrain:del")
    @RequestMapping(value = "/cetUnitTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetUnitTrain_batchDel(HttpServletRequest request, byte addType,
                                     @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {
        
        
        if (null != ids && ids.length > 0) {
            cetUnitTrainService.batchDel(ids, addType);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除二级单位培训班培训记录：%s", StringUtils.join(ids, ",")));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    public void cetUnitTrain_export(CetUnitTrainExample example, HttpServletResponse response) {
        
        List<CetUnitTrain> records = cetUnitTrainMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"参训人|100", "时任单位及职务|100", "职务属性|100", "完成培训学时|100", "培训总结|100", "添加类型|100", "操作人|100", "添加时间|100", "审批状态|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUnitTrain record = records.get(i);
            String[] values = {
                    record.getUserId() + "",
                    record.getTitle(),
                    record.getPostType() + "",
                    record.getPeriod() + "",
                    record.getWordNote(),
                    record.getAddType() + "",
                    record.getAddUserId() + "",
                    DateUtils.formatDate(record.getAddTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                    record.getStatus() + ""
            };
            valuesList.add(values);
        }
        String fileName = "二级单位培训班培训记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
