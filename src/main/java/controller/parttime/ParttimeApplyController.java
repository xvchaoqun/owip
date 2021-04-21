package controller.parttime;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.parttime.*;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import persistence.parttime.common.ParttimeApprovalResult;
import persistence.parttime.common.ParttimeApproverTypeBean;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.ParttimeConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.helper.ParttimeHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ParttimeApplyController extends ParttimeBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("parttimeApply:list")
    @RequestMapping("/parttime/parttimeApply_page")
    public String parttimeCompanyApply_page(
            @RequestParam(required = false, defaultValue = "0") int status,
            ModelMap modelMap) {
        modelMap.put("status", status);
        return "parttime/parttimeApply/parttimeApply_page";
    }

    @RequiresPermissions("parttimeApply:list")
    @RequestMapping("/parttime/parttimeApply")
    public String parttimeApply(
                           // 流程状态，0.申请中 1.同意申请 2.不同意申请 3.已删除
                           @RequestParam(required = false, defaultValue = "0") byte status, // -1: 已删除的记录
                           ModelMap modelMap) {

        modelMap.put("status", status);

        return "parttime/parttimeApply/parttimeApply_page";
    }

    @RequiresPermissions("parttimeApply:list")
    @RequestMapping("/parttime/parttimeApply_data")
    @ResponseBody
    public void parttimeApply_data(
                              // 流程状态，0.已提交 1.同意申请 2.不同意申请 3.已删除
                              @RequestParam(required = false, defaultValue = "0") byte status,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer cadreId, String applyTime, String parttime, Integer isFirst, Integer background, Integer hasPay,
                              Integer pageSize, Integer pageNo, HttpServletRequest request, HttpServletResponse response,
                              Integer ids[]) throws IOException {

        ParttimeApply parttimeApply = new ParttimeApply();
        if (cadreId != null) {
            parttimeApply.setCadreId(cadreId);
        }
        if (isFirst != null) {
            parttimeApply.setIsFirst(isFirst==0?false:true);
        }
        if (background != null) {
            parttimeApply.setBackground(background==0?false:true);
        }
        if (hasPay != null) {
            parttimeApply.setHasPay(hasPay==0?false:true);
        }
        Map map = parttimeApplyService.findApplyList(parttimeApply, applyTime, parttime, status, pageNo, springProps.pageSize, ids, export, response);
        if (map == null) return;
        CommonList commonList = (CommonList) map.get("commonList");

        Map resultMap = new HashMap();
        resultMap.put("rows", map.get("applys"));
        resultMap.put("records", commonList.recNum);
        resultMap.put("page", commonList.pageNo);
        resultMap.put("total", commonList.pageNum);
//        resultMap.put("background", map.get)

        request.setAttribute("isView", false);
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("parttimeApply:list")
    @RequestMapping("/parttimeApply_au_page")
    public String parttime_au_page() {
        return "parttime/user/parttimeApply/parttimeApply_au";
    }

    @RequiresPermissions("parttimeApply:list")
    @RequestMapping("/parttimeApply_select")
    public String parttimeApply_select() {

        return "parttime/parttimeApply/parttimeApply_select";
    }

    @RequiresPermissions("parttimeApply:edit")
    @RequestMapping(value = "/parttime/parttimeApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApply_au(Integer cadreId,
                              ParttimeApply record,
                              MultipartFile[] _files,
                              HttpServletRequest request) throws IOException, InterruptedException {

        // 是否本人操作
        boolean self = false;
        if(cadreId==null || !ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTTIMEAPPLY_ADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
            self = true;
        }

        if(record.getStartTime().after(record.getEndTime())){
            return failed("兼职开始时间不能晚于结束时间");
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        List<ParttimeApplyFile> parttimeApplyFiles = new ArrayList<>();
        if (_files.length > 0) {
            for (MultipartFile _file : _files) {
                String originalFilename = _file.getOriginalFilename();
                String savePath = upload(_file, "parttime_apply");

                ParttimeApplyFile parttimeApplyFile = new ParttimeApplyFile();
                parttimeApplyFile.setFileName(FileUtils.getFileName(originalFilename));
                parttimeApplyFile.setFilePath(savePath);
                parttimeApplyFile.setCreateTime(new Date());

                parttimeApplyFiles.add(parttimeApplyFile);
            }
        }

        if(record.getId()==null) {
            record.setStatus(true);
            Date date = new Date();
            record.setCreateTime(date);
            record.setApplyTime(date);
            record.setIp(IpUtils.getRealIp(request));
            record.setFlowNode(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST);
            parttimeApplyMapper.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "提交兼职申报申请：%s", record.getId()));

            // 给干部管理员发消息提醒
            parttimeApplyService.sendApplySubmitMsgToCadreAdmin(record.getId(), IpUtils.getRealIp(request));
            sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                    self?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PARTTIME_APPLY,
                    "提交干部兼职申报", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false));
        }else{

            ParttimeApply pApply = parttimeApplyMapper.selectByPrimaryKey(record.getId());
            Integer firstTrialStatus = ParttimeHelper.getParttimeAdminFirstTrialStatus(record.getId());
            if(pApply.getCadreId().intValue() != cadreId.intValue()
                    || (firstTrialStatus!=null&&firstTrialStatus==1)){ // 没有初审或初审未通过时才允许更新
                return failed("不允许更新");
            }
            record.setCadreId(null);
            record.setStatus(true);// 重新提交
            record.setFlowNode(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST);
            record.setIsFinish(false);

            ParttimeApprovalLogExample example = new ParttimeApprovalLogExample();
            example.createCriteria().andApplyIdEqualTo(record.getId());
            parttimeApprovalLogMapper.deleteByExample(example);

            parttimeApplyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "更新兼职申报：%s", record.getId()));

            sysApprovalLogService.add(record.getId(), cadre.getUserId(),
                    self? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PARTTIME_APPLY,
                    "修改兼职申报申请", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(pApply, MixinUtils.baseMixins(), false));
        }
        Integer applyId = record.getId();
        for (ParttimeApplyFile pApplyFile : parttimeApplyFiles) {
            pApplyFile.setApplyId(applyId);
            parttimeApplyFileMapper.insert(pApplyFile);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("applyId", applyId);
        return resultMap;
    }

    @RequiresPermissions("parttimeApply:del")
    @RequestMapping(value = "/parttimeApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApply_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            parttimeApplyService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "批量删除[可找回]兼职申报申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:list")
    @RequestMapping("/parttimeApply_approvers")
    @ResponseBody
    public void parttimeApply_approvers(int applyId, int approvalTypeId, HttpServletResponse response) throws IOException {

        // 读取所有审批人
        ParttimeApply claApply = parttimeApplyMapper.selectByPrimaryKey(applyId);
        List<SysUserView> approvers = parttimeApplyService.findApprovers(claApply.getCadreId(), approvalTypeId);

        Map<String, Object> resultMap = success();

        ParttimeApprovalLog approvalLog = parttimeApplyService.getApprovalLog(applyId, approvalTypeId);
        if (approvalLog != null) { // 如果已审批，显示审批人
            SysUserView uv = sysUserService.findById(approvalLog.getUserId());
            resultMap.put("uv", uv);
        }
        resultMap.put("approvers", approvers);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.write(response, resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("parttimeApply:view")
    @RequestMapping("/parttime/parttimeApply_view")
    public String parttimeApply_view(Integer id, ModelMap modelMap) {

        ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);
        Integer cadreId = parttimeApply.getCadreId();

        // 判断一下查看权限++++++++++++++++++++???
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTTIMEAPPLY_ADMIN)) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                //ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ParttimeApproverTypeBean approverTypeBean = parttimeApplyService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
                if (approverTypeBean == null || !approverTypeBean.getApprovalCadreIdSet().contains(parttimeApply.getCadreId()))
                    throw new OpException("您没有权限");
            }
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView uv = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", uv);
        modelMap.put("cadre", cadre);
        modelMap.put("parttimeApply", parttimeApply);

        modelMap.put("cadreMobile", userBeanService.getMsgMobile(cadre.getUserId()));

        List<ParttimeApplyFile> files = parttimeApplyService.getFiles(parttimeApply.getId());
        modelMap.put("files", files);

        Map<Integer, ParttimeApprovalResult> approvalResultMap = parttimeApplyService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);

        // 有书记、校长审批时，需要导出
        boolean needExport = false;
        Map<Integer, ParttimeApproverType> approverTypeMap = parttimeApproverTypeService.findAll();
        for (Integer key : approvalResultMap.keySet()) {
            ParttimeApprovalResult approvalResult = approvalResultMap.get(key);
            if (key > 0 && (approvalResult.getValue() == null || approvalResult.getValue() != -1)) {
                ParttimeApproverType approverType = approverTypeMap.get(key);
                if (approverType.getType() == ParttimeConstants.PARTTIME_APPROVER_TYPE_FOREIGN) {
                    needExport = true;
                }
            }
        }
        modelMap.put("needExport", needExport);

        return "parttime/user/parttimeApply/parttimeApply_view";
    }

    @RequiresPermissions("parttimeApply:download")
    @RequestMapping("/parttime/parttimeApply_download")
    public void parttimeApply_download(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ParttimeApplyFile pApplyFile = parttimeApplyFileMapper.selectByPrimaryKey(id);

        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTTIMEAPPLY_ADMIN)) { // 干部管理员有下载权限
            int userId = loginUser.getId();
            CadreView cadre = cadreService.dbFindByUserId(userId);
            Integer applyId = pApplyFile.getApplyId();
            ParttimeApply cpApply = parttimeApplyMapper.selectByPrimaryKey(applyId); // 本人有下载权限
            if (cpApply.getCadreId().intValue() != cadre.getId().intValue()) {

                Set<Integer> cadreIdSet = parttimeApplyService.findApprovalCadreIdSet(loginUser.getId()); // 审批人有下载权限
                if (!cadreIdSet.contains(cpApply.getCadreId()))
                    throw new UnauthorizedException();
            }
        }

        DownloadUtils.download(request, response,
                springProps.uploadPath + pApplyFile.getFilePath(), pApplyFile.getFileName());
    }

    @RequiresPermissions("parttimeApply:view")
    @RequestMapping("/parttime/parttimeApply_yearLogs")
    public String parttimeApply_yearLogs(Integer id, ModelMap modelMap) {

        ParttimeApply claApply = parttimeApplyMapper.selectByPrimaryKey(id);
        Integer currentYear = DateUtils.getYear(claApply.getApplyTime());
        modelMap.put("claApply", claApply);
        modelMap.put("currentYear", currentYear);
        return "parttime/user/parttimeApply_yearLogs";
    }

    @RequiresPermissions("parttimeApply:view")
    @RequestMapping("/parttime/parttimeApply_yearLogs_data")
    @ResponseBody
    public void parttimeApply_yearLogs_data(@CurrentUser SysUserView loginUser, Integer cadreId, Integer year,
                                       Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {

        // 判断一下查看权限++++++++++++++++++++???
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTTIMEAPPLY_ADMIN)) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                //ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ParttimeApproverTypeBean approverTypeBean = parttimeApplyService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
                if (approverTypeBean == null || !approverTypeBean.getApprovalCadreIdSet().contains(cadreId))
                    throw new OpException("您没有权限");
            }
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        // 本年度的申请记录（通过审批的）
        ParttimeApplyExample example = new ParttimeApplyExample();
        ParttimeApplyExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        criteria.andIsAgreedEqualTo(true);
        criteria.andApplyTimeBetween(DateUtils.parseDate(year + "-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(year + "-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");

        long count = parttimeApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<ParttimeApply> records = parttimeApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", true);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("parttimeApply:del")
    @RequestMapping(value = "/parttime/parttimeApply_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApply_batchUnDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            parttimeApplyService.batchUnDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "批量找回兼职申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:del")
    @RequestMapping(value = "/parttime/parttimeApply_doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApply_doBatchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            parttimeApplyService.doBatchDel(ids);
            logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "批量删除[真删除]兼职申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 干部管理员直接审批（代审）
    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttime/parttimeApply_approval_direct")
    public String parttimeApply_approval_direct(int applyId, int approvalTypeId, ModelMap modelMap) {

        ParttimeApply claApply = parttimeApplyMapper.selectByPrimaryKey(applyId);
        int cadreId = claApply.getCadreId();

        List<SysUserView> approvers = parttimeApplyService.findApprovers(cadreId, approvalTypeId);
        modelMap.put("approvers", approvers);

        return "parttime/parttimeApply/parttimeApply_approval_direct";
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping(value = "/parttime/parttimeApply_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApply_approval(int applyId, int approvalTypeId,
                                    boolean pass, String remark,
                                    Boolean isAdmin, // 干部管理员直接审批
                                    Integer approvalUserId, // 干部管理员直接审批时，选择的审批人
                                    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date approvalTime, // 干部管理员直接审批时可修改时间
                                    HttpServletRequest request) {

        //int userId = ShiroHelper.getCurrentUserId();
        if(BooleanUtils.isTrue(isAdmin)){
            ShiroHelper.checkPermission(RoleConstants.PERMISSION_PARTTIMEAPPLY_ADMIN);
            if(approvalTime==null) approvalTime = new Date();
            if(approvalUserId==null) approvalUserId = ShiroHelper.getCurrentUserId();
        }else{
            approvalUserId = ShiroHelper.getCurrentUserId();
            if (!parttimeApplyService.canApproval(approvalUserId, applyId, approvalTypeId))
                return failed("您没有权限进行审批");
            approvalTime = new Date();
        }

        parttimeApprovalLogService.approval(approvalUserId, applyId, approvalTypeId, pass, approvalTime, remark);

        logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "兼职申报审批：%s", applyId));

        return success(FormUtils.SUCCESS);
    }

    // 干部管理员直接修改审批
    @RequiresPermissions(RoleConstants.PERMISSION_PARTTIMEAPPLY_ADMIN)
    @RequestMapping("/parttime/parttimeApply_approval_direct_au")
    public String parttimeApply_approval_direct_au(int approvalLogId, ModelMap modelMap) {

        ParttimeApprovalLog approvalLog = parttimeApprovalLogMapper.selectByPrimaryKey(approvalLogId);
        modelMap.put("approvalLog", approvalLog);

        return "parttime/parttimeApply/parttimeApply_approval_direct_au";
    }

    @RequiresPermissions(RoleConstants.PERMISSION_PARTTIMEAPPLY_ADMIN)
    @RequestMapping(value = "/parttime/parttimeApply_approval_direct_au", method = RequestMethod.POST)
    @ResponseBody
    public Map parttimeApply_approval_direct_au(HttpServletRequest request,
                                              int applyId,
                                              int approvalLogId,
                                              @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date approvalTime,
                                              String remark, ModelMap modelMap) {


        ParttimeApprovalLog approvalLog = parttimeApprovalLogMapper.selectByPrimaryKey(approvalLogId);
        String before = JSONUtils.toString(approvalLog, false);
        logger.info(addLog(LogConstants.LOG_PARTTIME_APPLY, "修改审批意见和审批时间，修改前：%s", before));

        ParttimeApprovalLog record = new ParttimeApprovalLog();
        record.setId(approvalLogId);
        record.setCreateTime(approvalTime);
        record.setRemark(remark);
        parttimeApprovalLogMapper.updateByPrimaryKeySelective(record);

        ParttimeApply claApply = parttimeApplyMapper.selectByPrimaryKey(applyId);
        sysApprovalLogService.add(applyId, claApply.getUser().getId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PARTTIME_APPLY,
                "修改审批", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, before);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttime/parttimeApply_approval")
    public String parttimeApply_approval() {

        return "parttime/parttimeApply/parttimeApply_approval";
    }

    // 非管理员  审批人身份 审批记录
    @RequiresPermissions("parttimeApply:approvalList")
    @RequestMapping("/parttime/parttimeApplyList")
    public String parttimeApplyList(// 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                               @RequestParam(required = false, defaultValue = "0") int status,
                               Integer cadreId, ModelMap modelMap) {

        modelMap.put("status", status);

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "parttime/parttimeApply/parttimeApplyList_page";
    }

    // 非管理员  审批人身份 审批记录
    @RequiresPermissions("parttimeApply:approvalList")
    @RequestMapping("/parttime/parttimeApplyList_data")
    public void parttimeApplyList_data(@CurrentUser SysUserView loginUser, HttpServletResponse response,
                                  Integer cadreId,
                                  @RequestDateRange DateRange _applyDate,
                                  Byte type, // 出行时间范围
                                  // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                  @RequestParam(required = false, defaultValue = "0") int status,
                                  Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {


        Map map = parttimeApplyService.findApplyList(loginUser.getId(), cadreId, _applyDate,
                type, status, pageNo, springProps.pageSize);
        CommonList commonList = (CommonList) map.get("commonList");

        Map resultMap = new HashMap();
        resultMap.put("rows", map.get("applys"));
        resultMap.put("records", commonList.recNum);
        resultMap.put("page", commonList.pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", false);
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }


}
