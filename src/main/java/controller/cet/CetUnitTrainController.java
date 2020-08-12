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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
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
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetUnitTrainController extends CetBaseController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/cetUnitTrain")
    public String cetUnitTrain(Integer projectId,
                                    Integer reRecord,
                                    Integer userId,
                                    Integer[] identities,
                                    ModelMap modelMap) {

        if (null != identities){
            modelMap.put("selectIdentities", Arrays.asList(identities));
        }
        modelMap.put("reRecord", reRecord);

        modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(projectId));
        if (null != userId) {
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }

        return "cet/cetUnitTrain/cetUnitTrain_page";
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/cetUnitTrain_info")
    public String cetUnitTrain_info(@RequestParam(required = false, defaultValue = "2") Byte cls,
                                    Integer reRecord,
                                    Integer userId,
                                    Integer addUserId,
                                    String projectName,
                                    Integer traineeTypeId,
                                    Integer[] identities,
                                    ModelMap modelMap) {

        if (null != identities){
            modelMap.put("selectIdentities", Arrays.asList(identities));
        }
        modelMap.put("reRecord", reRecord);
        modelMap.put("projectName", projectName);
        modelMap.put("traineeTypeId", traineeTypeId);
        if (null == reRecord)
            modelMap.put("cls", cls);

        boolean addPermits = ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN);
        List<Integer> adminPartyIdList = new ArrayList<>();
        if(addPermits) {
            adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            if (adminPartyIdList.size() == 0) {
                throw new UnauthorizedException();
            }
        }

        Integer reRecondCount = iCetMapper.unitTrainReRecord(addPermits, adminPartyIdList);
        modelMap.put("reRecondCount", reRecondCount);

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
                                  Integer partyId,
                                  String title,
                                  Integer[] identities,
                                  Integer postType,
                                  BigDecimal prePeriod,
                                  BigDecimal subPeriod,

                                  Integer reRecord,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {


        List<Integer> projectIds = new ArrayList<>();
        if (null != projectId) {
            CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);
            if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
                List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
                if (adminPartyIdList.size() == 0 || !adminPartyIdList.contains(cetUnitProject.getCetPartyId())) {
                    throw new UnauthorizedException();
                }
            }
        }else {//参训人员信息汇总
            if (cls == null) {
                cls = ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN) ? (byte) 1 : 2;
            }
            boolean addPermits = ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN);
            List<Integer> adminPartyIdList = new ArrayList<>();
            if(addPermits) {
                adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
                if (adminPartyIdList.size() == 0) {
                    throw new UnauthorizedException();
                }
            }
            projectIds = cetUnitProjectService.getByStatus(cls, reRecord, adminPartyIdList, projectName, _endDate, _startDate);
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        
        CetUnitTrainExample example = new CetUnitTrainExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (null != cls){
            if (null != projectIds && projectIds.size() > 0)
                criteria.andProjectIdIn(projectIds);
            else
                criteria.andProjectIdIsNull();
        }
        if (null != reRecord){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(CetConstants.CET_UNITTRAIN_RERECORD_PARTY);
            statusList.add(CetConstants.CET_UNITTRAIN_RERECORD_CET);
            statusList.add(CetConstants.CET_UNITTRAIN_RERECORD_SAVE);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(CetConstants.CET_UNITTRAIN_RERECORD_PASS);
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
        if (null != traineeTypeId){
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
        }
        if (partyId != null) {
            List<Integer> proIds = cetUnitTrainService.getProjectIds(partyId);
            criteria.andProjectIdIn(proIds);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(SqlUtils.like(title));
        }
        if (null !=identities){
            criteria.andIdentityLike(identities);
        }
        if (postType != null) {
            criteria.andPostTypeEqualTo(postType);
        }
        if (null != prePeriod){
            criteria.andPeriodGreaterThanOrEqualTo(prePeriod);
        }
        if (null != subPeriod){
            criteria.andPeriodLessThanOrEqualTo(subPeriod);
        }
        
        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUnitTrain_export(example, projectId, response);
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
    
    @RequestMapping(value = "/cetUnitTrain_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_au(CetUnitTrain record,
                                  Boolean apply,
                                  Integer[] identities,
                                  MultipartFile _word, MultipartFile _pdf,
                                  HttpServletRequest request) throws IOException, InterruptedException {

        if(BooleanUtils.isTrue(apply)){
           record.setUserId(ShiroHelper.getCurrentUserId());
           record.setStatus(CetConstants.CET_UNITTRAIN_RERECORD_PARTY);
        }else{
             SecurityUtils.getSubject().checkPermission("cetUnitProject:edit");
        }

        Integer id = record.getId();
        
        if (cetUnitTrainService.idDuplicate(id, record.getProjectId(), record.getUserId())) {
            
            return failed("添加重复。");
        }
        record.setIdentity(StringUtils.trimToNull(StringUtils.join(identities, ",")) == null
                ? "" : "," + StringUtils.join(identities, ",") + ",");
        record.setWordNote(upload(_word, "cetUnitTrain_note"));
        record.setPdfNote(uploadPdf(_pdf, "cetUnitTrain_note"));
        
        int projectId = record.getProjectId();
        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(projectId);

        if (BooleanUtils.isNotTrue(apply) && ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            if (!adminPartyIdList.contains(cetUnitProject.getCetPartyId())) {
                return failed("没有权限。");
            }
        }
        if (id == null) {
            record.setAddTime(new Date());
            record.setAddUserId(ShiroHelper.getCurrentUserId());
            cetUnitTrainService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加二级单位培训班培训记录：参训人%s", record.getUserId()));
        } else {
            
            cetUnitTrainService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新二级单位培训班培训记录：%s", record.getId()));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequestMapping("/cetUnitTrain_au")
    public String cetUnitTrain_au(Integer id,
                                  Boolean apply,
                                  Integer projectId, ModelMap modelMap) {

        if(BooleanUtils.isNotTrue(apply)){
            SecurityUtils.getSubject().checkPermission("cetUnitProject:edit");
        }

        if (id != null) {

            CetUnitTrain cetUnitTrain = cetUnitTrainMapper.selectByPrimaryKey(id);
            if(BooleanUtils.isTrue(apply)){
                if(cetUnitTrain.getUserId().intValue()!=ShiroHelper.getCurrentUserId()){
                    return null;
                }
            }
            modelMap.put("cetUnitTrain", cetUnitTrain);
            projectId = cetUnitTrain.getProjectId();
        }

        modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(projectId));

        return "cet/cetUnitTrain/cetUnitTrain_au";
    }
    
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping("/cetUnitTrain_import")
    public String cetUnitTrain_import() {

        return "cet/cetUnitTrain/cetUnitTrain_import";
    }

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
    public Map cetUnitTrain_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {
        
        if (null != ids && ids.length > 0) {
            cetUnitTrainService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除二级单位培训班培训记录：%s", StringUtils.join(ids, ",")));
        }
        
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetUnitProject:check")
    @RequestMapping("/cetUnitTrain_check")
    public String cetUnitTrain_pass(Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length == 1)
            modelMap.put("cetUnitTrain", cetUnitTrainMapper.selectByPrimaryKey(ids[0]));

        return "/cet/cetUnitTrain/cetUnitTrain_check";
    }

    @RequiresPermissions("cetUnitProject:check")
    @RequestMapping(value = "/cetUnitTrain_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_check(HttpServletRequest request, Integer[] ids,
                                     Boolean pass, String reason, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {

            cetUnitTrainService.batchCheck(ids, pass, reason);
            logger.info(addLog(LogConstants.LOG_CET, "批量审批通过二级党委培训班补录申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    
    public void cetUnitTrain_export(CetUnitTrainExample example, Integer projectId, HttpServletResponse response) {
        
        List<CetUnitTrain> records = cetUnitTrainMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"参训人工号|100", "参训人|100", "参训人类型|100", "时任单位及职务|150", "时任职务属性|100", "参训人身份|120", "完成培训学时|100", "培训成绩|100", "备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUnitTrain record = records.get(i);
            List<String> _identities = new ArrayList();
            if (StringUtils.isNotBlank(record.getIdentity())) {
                String[] identities = record.getIdentity().split(",");
                for (String identity : identities) {
                    if (StringUtils.trimToNull(identity) == null)
                        continue;
                    _identities.add(metaTypeService.getName(Integer.valueOf(identity)));
                }
            }
            String[] values = {
                    record.getUser().getCode(),
                    record.getUser().getRealname(),
                    record.getTraineeTypeId() == 0 ? record.getOtherTraineeType() : cetTraineeTypeMapper.selectByPrimaryKey(record.getTraineeTypeId()).getName(),
                    record.getTitle(),
                    record.getPostType() == null ? "" : CmTag.getMetaTypeName(record.getPostType()),
                    record.getIdentity() != "" ? StringUtils.join(_identities, ",") : "",
                    record.getPeriod() + "",
                    record.getScore(),
                    record.getRemark(),
            };
            valuesList.add(values);
        }
        String fileName = cetUnitProjectMapper.selectByPrimaryKey(projectId).getProjectName() + "培训记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
