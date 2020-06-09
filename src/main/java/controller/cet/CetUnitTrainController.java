package controller.cet;

import domain.cet.CetUnitProject;
import domain.cet.CetUnitTrain;
import domain.cet.CetUnitTrainExample;
import domain.cet.CetUnitTrainExample.Criteria;
import mixin.MixinUtils;
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
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
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

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/cetUnitTrain")
    public String cetUnitTrain(Integer projectId,
                               ModelMap modelMap) {

        modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(projectId));

        return "cet/cetUnitTrain/cetUnitTrain_page";
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/cetUnitTrain_info")
    public String cetUnitTrain_info(@RequestParam(required = false, defaultValue = "2") Byte cls,
                                    Integer userId,
                                    Integer addUserId,
                                    String projectName,
                                    Integer traineeTypeId,
                                    ModelMap modelMap) {

        modelMap.put("projectName", projectName);
        modelMap.put("traineeTypeId", traineeTypeId);
        modelMap.put("cls", cls);

        boolean addPermits = ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN);
        List<Integer> adminPartyIdList = new ArrayList<>();
        List<Integer> projectIds = new ArrayList<>();
        if(addPermits) {
            adminPartyIdList = loginUserService.adminPartyIdList();
        }
        List<Map> results = iCetMapper.unitTrainGroupByStatus(addPermits, adminPartyIdList);
        Map<Byte, Integer> statusCountMap = new HashMap<>();
        for (Map result : results) {
            Byte status = ((Integer) result.get("status")).byteValue();
            Integer num = ((Long) result.get("num")).intValue();
            statusCountMap.put(status, num);
        }
        modelMap.put("statusCountMap", statusCountMap);

        if (null != userId) {
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }
        if (null != addUserId){
            modelMap.put("addSysUser", CmTag.getUserById(userId));
        }

        return "cet/cetUnitTrain/cetUnitTrain_info";
    }
    
    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/cetUnitTrain_data")
    @ResponseBody
    public void cetUnitTrain_data(HttpServletResponse response,
                                  Integer projectId,
                                  Integer userId,

                                  Byte cls,
                                  Integer traineeTypeId,
                                  String projectName,
                                  Integer addUserId,
                                  @RequestDateRange DateRange _startDate,
                                  @RequestDateRange DateRange _endDate,

                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {


        List<Integer> projectIds = new ArrayList<>();
        if (null != projectId) {
            CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
            if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
                List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
                if (adminPartyIdList.size() == 0 || !adminPartyIdList.contains(cetUnitProject.getPartyId())) {
                    throw new UnauthorizedException();
                }
            }
        }else {
            if (cls == null) {
                cls = ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN) ? (byte) 1 : 2;
            }
            boolean addPermits = ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN);
            List<Integer> adminPartyIdList = new ArrayList<>();
            if(addPermits) {
                adminPartyIdList = loginUserService.adminPartyIdList();
            }
            projectIds = cetUnitProjectService.getByStatus(cls, adminPartyIdList, projectName, _endDate, _startDate);
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        
        CetUnitTrainExample example = new CetUnitTrainExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(CetConstants.CET_UNITTRAIN_RERECORD_PASS);
        example.setOrderByClause("id desc");

        if (null != cls){
            if (null != projectIds && projectIds.size() > 0)
                criteria.andProjectIdIn(projectIds);
            else
                criteria.andProjectIdIsNull();
        }
        if (null != projectId){
            criteria.andProjectIdEqualTo(projectId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (null != addUserId){
            criteria.andAddUserIdEqualTo(addUserId);
        }
        
        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUnitTrain_export(example, response);
            return;
        }
        if (null != traineeTypeId){
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
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
    
    @RequiresPermissions("cetUnitProject:edit")
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

        if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
            if (!adminPartyIdList.contains(cetUnitProject.getPartyId())) {
                return failed("没有权限。");
            }
        }
        if (id == null) {
            record.setAddTime(new Date());
            record.setAddUserId(ShiroHelper.getCurrentUserId());
            cetUnitTrainService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加二级单位培训班培训记录：%s", record.getId()));
        } else {
            
            cetUnitTrainService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新二级单位培训班培训记录：%s", record.getId()));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping("/cetUnitTrain_au")
    public String cetUnitTrain_au(Integer id,
                                  Integer projectId, ModelMap modelMap) {

        if (id != null) {
            CetUnitTrain cetUnitTrain = cetUnitTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetUnitTrain", cetUnitTrain);
            projectId = cetUnitTrain.getProjectId();
        }
        modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(projectId));

        return "cet/cetUnitTrain/cetUnitTrain_au";
    }
    
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping("/cetUnitTrain_batchAdd")
    public String cetUnitTrain_batchAdd(int projectId, ModelMap modelMap) {

        modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(projectId));
        
        return "cet/cetUnitTrain/cetUnitTrain_batchAdd";
    }
    
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitTrain_batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_batchAdd(HttpServletRequest request,
                                        int projectId,
                                        int traineeTypeId,
                                        @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                                        ModelMap modelMap) {
        
       cetUnitTrainService.batchAdd(projectId, traineeTypeId, userIds);
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping("/cetUnitTrain_import")
    public String cetUnitTrain_import() {

        return "cet/cetUnitTrain/cetUnitTrain_import";
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequiresPermissions("cetUnitProject:edit")
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

        int totalCount = xlsRows.size();
        int successCount = (int) retMap.get("success");
        List<Map<Integer, String>> failedXlsRows = (List<Map<Integer, String>>)retMap.get("failedXlsRows");

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("failedXlsRows", failedXlsRows);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入调训结果成功，总共{0}条记录，其中成功导入{1}条记录，{2}条失败",
                totalCount, successCount, failedXlsRows.size()));

        return resultMap;
    }
    
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetUnitTrain_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {
        
        if (null != ids && ids.length > 0) {
            cetUnitTrainService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除二级单位培训班培训记录：%s", StringUtils.join(ids, ",")));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    public void cetUnitTrain_export(CetUnitTrainExample example, HttpServletResponse response) {
        
        List<CetUnitTrain> records = cetUnitTrainMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"参训人|100", "时任单位及职务|100", "职务属性|100", "完成培训学时|100", "培训总结|100", "操作人|100", "添加时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUnitTrain record = records.get(i);
            String[] values = {
                    record.getUserId() + "",
                    record.getTitle(),
                    record.getPostType() + "",
                    record.getPeriod() + "",
                    record.getWordNote(),
                    record.getAddUserId() + "",
                    DateUtils.formatDate(record.getAddTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "二级单位培训班培训记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
