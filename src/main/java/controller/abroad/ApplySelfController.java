package controller.abroad;

import bean.ApprovalResult;
import bean.ApproverTypeBean;
import controller.BaseController;
import domain.*;
import domain.ApplySelfExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.ApplySelfMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ApplySelfController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySelf:note")
    @RequestMapping("/applySelf_note")
    public String applySelf_note(ModelMap modelMap) {

        SysConfig SysConfig = sysConfigService.get();
        modelMap.put("sysConfig", SysConfig);

        return "abroad/applySelf/applySelf_note";
    }

    @RequiresPermissions("applySelf:note")
    @RequestMapping(value = "/applySelf_note", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_note(String notice, ModelMap modelMap) {

        sysConfigService.updateApplySelfNote(notice);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:approval")
    @RequestMapping("/applySelf_approval")
    public String applySelf_approval() {

        return "abroad/applySelf/applySelf_approval";
    }

    @RequiresPermissions("applySelf:approval")
    @RequestMapping(value = "/applySelf_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_approval(@CurrentUser SysUser loginUser,
                                     int applySelfId, int approvalTypeId,
                                     int status, String remark, HttpServletRequest request) {

        int userId = loginUser.getId();
        if (!applySelfService.canApproval(userId, applySelfId, approvalTypeId))
            throw new RuntimeException("您没有权限进行审批");

        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(applySelfId);
        Integer result = approvalResultMap.get(approvalTypeId).getValue();
        if (result != null && result != -1) {
            throw new RuntimeException("重复审批");
        }
        if (result != null && result == -1) {
            throw new RuntimeException("不需该审批人身份进行审批");
        }
        if (approvalTypeId == -1) { // 管理员初审
            Assert.isTrue(result == null);
            SecurityUtils.getSubject().checkRole("cadreAdmin");
        }
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        if (approvalTypeId > 0) {
            for (Map.Entry<Integer, ApproverType> entry : approverTypeMap.entrySet()) {
                Integer key = entry.getKey();
                if (key != approvalTypeId) {
                    Integer preResult = approvalResultMap.get(key).getValue();
                    if (preResult == null || preResult == 0)
                        throw new RuntimeException(entry.getValue().getName() + "审批未通过，不允许进行当前审批");
                }
                if (key == approvalTypeId) break;
            }
        }
        if (approvalTypeId == 0) {
            // 验证前面的审批均已完成（通过或未通过）
            for (Map.Entry<Integer, ApproverType> entry : approverTypeMap.entrySet()) {
                Integer key = entry.getKey();
                //if (key != 0) {
                Integer preResult = approvalResultMap.get(key).getValue();

                if (preResult != null && preResult == 0) break; // 前面有审批未通过的，则可以直接终审
                if (preResult != null && preResult == -1) continue; // 跳过不需要审批的

                if (preResult == null) // 前面存在 未审批
                    throw new RuntimeException(entry.getValue().getName() + "未完成审批");
                // }
            }
        }

        ApprovalLog record = new ApprovalLog();
        record.setApplyId(applySelfId);
        if (approvalTypeId > 0)
            record.setTypeId(approvalTypeId);
        if (approvalTypeId == -1) {
            record.setOdType(SystemConstants.APPROVER_LOG_OD_TYPE_FIRST); // 初审
            if (status != 1) { // 不通过，打回申请
                ApplySelf applySelf = new ApplySelf();
                applySelf.setId(applySelfId);
                applySelf.setStatus(false); // 打回

                //如果管理员初审未通过，就不需要领导审批，也不需要管理员再终审一次，直接就退回给干部了。
                // 也就是说只要管理员初审不通过，就相当于此次申请已经完成了审批。那么这条记录应该转移到“已完成审批”中去。
                applySelf.setIsFinish(true);

                applySelfService.updateByPrimaryKeySelective(applySelf);
            }
        }
        if (approvalTypeId == 0) {
            record.setOdType(SystemConstants.APPROVER_LOG_OD_TYPE_LAST); // 终审
        }
        record.setStatus(status == 1);
        record.setRemark(remark);
        record.setUserId(userId);
        record.setCreateTime(new Date());
        record.setIp(IpUtils.getRealIp(request));

        approvalLogService.add(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:download")
    @RequestMapping("/applySelf_download")
    public void applySelf_download(@CurrentUser SysUser loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ApplySelfFile applySelfFile = applySelfFileMapper.selectByPrimaryKey(id);

        if (!SecurityUtils.getSubject().hasRole("cadreAdmin")) { // 干部管理员有下载权限
            int userId = loginUser.getId();
            Cadre cadre = cadreService.findByUserId(userId);
            Integer applyId = applySelfFile.getApplyId();
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId); // 本人有下载权限
            if (applySelf.getCadreId().intValue() != cadre.getId().intValue()) {

                Set<Integer> cadreIdSet = applySelfService.findApprovalCadreIdSet(loginUser.getId()); // 审批人有下载权限
                if (!cadreIdSet.contains(applySelf.getCadreId()))
                    throw new UnauthorizedException();
            }
        }

        DownloadUtils.download(request, response,
                springProps.uploadPath + applySelfFile.getFilePath(), applySelfFile.getFileName());
    }

    @RequiresPermissions("applySelf:view")
    @RequestMapping("/applySelf_view")
    public String applySelf_view(Integer id, ModelMap modelMap) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
        Integer cadreId = applySelf.getCadreId();

        // 判断一下查看权限++++++++++++++++++++???
        if(!SecurityUtils.getSubject().hasRole("cadreAdmin")) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            if(cadre.getId().intValue()!=cadreId) {
                ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();
                if (!approverTypeBean.getApprovalCadreIdSet().contains(applySelf.getCadreId()))
                    throw new RuntimeException("您没有权限");
            }
        }

        Cadre cadre = cadreService.findAll().get(cadreId);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);
        modelMap.put("applySelf", applySelf);

        List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
        modelMap.put("files", files);

        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);
        modelMap.put("approverTypeMap", approverTypeService.findAll());

        return "user/applySelf/applySelf_view";
    }

    @RequiresPermissions("applySelf:view")
    @RequestMapping("/applySelf_yearLogs")
    public String applySelf_yearLogs(Integer id, ModelMap modelMap) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
        Integer currentYear = DateUtils.getYear(applySelf.getApplyDate());
        modelMap.put("applySelf", applySelf);
        modelMap.put("currentYear", currentYear);
        return "user/applySelf/applySelf_yearLogs";
    }

    @RequiresPermissions("applySelf:view")
    @RequestMapping("/applySelf_yearLogs_data")
    @ResponseBody
    public void applySelf_yearLogs_data(@CurrentUser SysUser loginUser,Integer cadreId, Integer year,
                                        Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {

        // 判断一下查看权限++++++++++++++++++++???
        if(!SecurityUtils.getSubject().hasRole("cadreAdmin")) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            if(cadre.getId().intValue()!=cadreId) {
                ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();
                if (!approverTypeBean.getApprovalCadreIdSet().contains(cadreId))
                    throw new RuntimeException("您没有权限");
            }
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        // 本年度的申请记录
        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);

        criteria.andApplyDateBetween(DateUtils.parseDate(year + "-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(year + "-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");

        int count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", applySelfs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", true);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(ApplySelf.class, ApplySelfMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("applySelf:list")
    @RequestMapping("/applySelf")
    public String applySelf() {

        return "index";
    }

    @RequiresPermissions("applySelf:list")
    @RequiresRoles("cadreAdmin")
    @RequestMapping("/applySelf_page")
    public String applySelf_page(Integer cadreId,
                               // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(通过或不通过)或0：未审批）
                               @RequestParam(required = false, defaultValue = "0") int status,
                               ModelMap modelMap) {

        modelMap.put("status", status);
        if (cadreId != null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        modelMap.put("approverTypeMap", approverTypeMap);

        return "abroad/applySelf/applySelf_page";
    }

    @RequiresPermissions("applySelf:list")
    @RequiresRoles("cadreAdmin")
    @RequestMapping("/applySelf_data")
    public void applySelf_data(@CurrentUser SysUser loginUser, HttpServletResponse response,
                               @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_apply_self") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,
                               Integer cadreId,
                               String _applyDate,
                               Byte type, // 出行时间范围
                               // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(通过或不通过)或0：未审批）
                               @RequestParam(required = false, defaultValue = "0") int status,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        criteria.andIsFinishEqualTo(status == 1);

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (StringUtils.isNotBlank(_applyDate)) {
            String applyDateStart = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String applyDateEnd = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(applyDateStart)) {
                criteria.andApplyDateGreaterThanOrEqualTo(DateUtils.parseDate(applyDateStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(applyDateEnd)) {
                criteria.andApplyDateLessThanOrEqualTo(DateUtils.parseDate(applyDateEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            applySelf_export(example, response);
            return;
        }

        int count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", applySelfs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", false);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(ApplySelf.class, ApplySelfMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresRoles("cadre")
    @RequiresPermissions("applySelf:approvalList")
    @RequestMapping("/applySelfList")
    public String applySelfList() {

        return "index";
    }

    // 非管理员  审批人身份 审批记录
    @RequiresRoles("cadre")
    @RequiresPermissions("applySelf:approvalList")
    @RequestMapping("/applySelfList_page")
    public String applySelfList_page(@CurrentUser SysUser loginUser, HttpServletResponse response,
                                     Integer cadreId,
                                     String _applyDate,
                                     Byte type, // 出行时间范围
                                     // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                     @RequestParam(required = false, defaultValue = "0") int status,
                                     Integer pageSize, Integer pageNo, ModelMap modelMap) {

        modelMap.put("status", status);
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        modelMap.put("approverTypeMap", approverTypeMap);

        return "abroad/applySelf/applySelfList_page";
    }
    // 非管理员  审批人身份 审批记录
    @RequiresRoles("cadre")
    @RequiresPermissions("applySelf:approvalList")
    @RequestMapping("/applySelfList_data")
    public void applySelfList_data(@CurrentUser SysUser loginUser, HttpServletResponse response,
                                     Integer cadreId,
                                     String _applyDate,
                                     Byte type, // 出行时间范围
                                     // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                     @RequestParam(required = false, defaultValue = "0") int status,
                                     Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {

        Integer userId = loginUser.getId();
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        //==============================================
        Map<Integer, List<Integer>> approverTypeUnitIdListMap = new HashMap<>();
        //Map<Integer, List<Integer>> approverTypePostIdListMap = new HashMap<>();

        ApproverType mainPostApproverType = approverTypeService.getMainPostApproverType();
        ApproverType leaderApproverType = approverTypeService.getLeaderApproverType();

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();

        if (approverTypeBean.getMainPostUnitId() != null) {
            List unitIds = new ArrayList();
            unitIds.add(approverTypeBean.getMainPostUnitId());
            approverTypeUnitIdListMap.put(mainPostApproverType.getId(), unitIds);
        }
        if (approverTypeBean.getLeaderUnitIds().size() > 0) {
            approverTypeUnitIdListMap.put(leaderApproverType.getId(), approverTypeBean.getLeaderUnitIds());
        }

        Map<Integer, List<Integer>> approverTypePostIdListMap = approverTypeBean.getApproverTypePostIdListMap();

        /*// 本单位正职
        Integer mainPostUnitId = applySelfService.getMainPostUnitId(userId);
        if(mainPostUnitId!=null) {
            List unitIds = new ArrayList();
            unitIds.add(mainPostUnitId);
            approverTypeUnitIdListMap.put(mainPostApproverType.getId(), unitIds);
        }
        // 分管校领导
        List<Integer> leaderUnitIds = applySelfService.getLeaderUnitIds(userId);
        if(leaderUnitIds.size()>0){
            approverTypeUnitIdListMap.put(leaderApproverType.getId(), leaderUnitIds);
        }

        // 其他身份
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (ApproverType approverType : approverTypeMap.values()) {
            if(approverType.getType() ==SystemConstants.APPROVER_TYPE_OTHER){
                List<Integer> approvalPostIds = applySelfService.getApprovalPostIds(userId, approverType.getId());
                if(approvalPostIds.size()>0){
                    approverTypePostIdListMap.put(approverType.getId(), approvalPostIds);
                }
            }
        }*/
        if (approverTypeUnitIdListMap.size() == 0) approverTypeUnitIdListMap = null;
        if (approverTypePostIdListMap.size() == 0) approverTypePostIdListMap = null;
        //==============================================

        int count = 0;
        if (status == 0)
            count = selectMapper.countNotApproval(approverTypeUnitIdListMap, approverTypePostIdListMap);
        if (status == 1)
            count = selectMapper.countHasApproval(approverTypeUnitIdListMap, approverTypePostIdListMap, loginUser.getId());

        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = null;
        if (status == 0)
            applySelfs = selectMapper.selectNotApprovalList(approverTypeUnitIdListMap, approverTypePostIdListMap,
                    new RowBounds((pageNo - 1) * pageSize, pageSize));
        if (status == 1)
            applySelfs = selectMapper.selectHasApprovalList(approverTypeUnitIdListMap, approverTypePostIdListMap, loginUser.getId(),
                    new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", applySelfs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", false);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(ApplySelf.class, ApplySelfMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("applySelf:edit")
    @RequestMapping(value = "/applySelf_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_au(ApplySelf record, String _applyDate, String _startDate, String _endDate, HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_applyDate)) {
            record.setApplyDate(DateUtils.parseDate(_applyDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_startDate)) {
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_endDate)) {
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            record.setCreateTime(new Date());
            record.setStatus(true);// 提交
            applySelfService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加因私出国申请：%s", record.getId()));
        } else {
            //record.setStatus(true);
            applySelfService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新因私出国申请：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:edit")
    @RequestMapping("/applySelf_au")
    public String applySelf_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            modelMap.put("applySelf", applySelf);

            Cadre cadre = cadreService.findAll().get(applySelf.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        List<String> countryList = new ArrayList<>();
        Map<Integer, Country> countryMap = countryService.findAll();
        for (Country country : countryMap.values()) {
            countryList.add(country.getCninfo());
        }
        modelMap.put("countryList", JSONUtils.toString(countryList));

        return "abroad/applySelf/applySelf_au";
    }

    @RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            applySelfService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除因私出国申请：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            applySelfService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批量删除因私出国申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void applySelf_export(ApplySelfExample example, HttpServletResponse response) {

        List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
        int rownum = applySelfMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"干部", "申请日期", "出行时间范围", "出发时间", "返回时间", "前往国家或地区", "出国事由", "同行人员", "费用来源", "所需证件", "创建时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            ApplySelf applySelf = applySelfs.get(i);
            String[] values = {
                    applySelf.getCadreId() + "",
                    DateUtils.formatDate(applySelf.getApplyDate(), DateUtils.YYYY_MM_DD),
                    applySelf.getType() + "",
                    DateUtils.formatDate(applySelf.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(applySelf.getEndDate(), DateUtils.YYYY_MM_DD),
                    applySelf.getToCountry(),
                    applySelf.getReason(),
                    applySelf.getPeerStaff(),
                    applySelf.getCostSource(),
                    applySelf.getNeedPassports(),

                    DateUtils.formatDate(applySelf.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "因私出国申请_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
