package controller.cla;

import controller.global.OpException;
import domain.base.Country;
import domain.cadre.CadreView;
import domain.cla.ClaApply;
import domain.cla.ClaApplyExample;
import domain.cla.ClaApplyFile;
import domain.cla.ClaApprovalLog;
import domain.cla.ClaApproverType;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import persistence.cla.common.ClaApprovalResult;
import persistence.cla.common.ClaApproverTypeBean;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.ClaConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/cla")
public class ClaApplyController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("claApply:approval")
    @RequestMapping("/claApply_approval")
    public String claApply_approval() {

        return "cla/claApply/claApply_approval";
    }

    // 干部管理员直接审批（代审）
    @RequiresPermissions("claApply:approval")
    @RequestMapping("/claApply_approval_direct")
    public String claApply_approval_direct(int applyId, int approvalTypeId, ModelMap modelMap) {

        ClaApply claApply = claApplyMapper.selectByPrimaryKey(applyId);
        int cadreId = claApply.getCadreId();

        List<SysUserView> approvers = claApplyService.findApprovers(cadreId, approvalTypeId);
        modelMap.put("approvers", approvers);

        return "cla/claApply/claApply_approval_direct";
    }

    @RequiresPermissions("claApply:approval")
    @RequestMapping(value = "/claApply_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApply_approval( int applyId, int approvalTypeId,
                                     boolean pass, String remark,
                                     Boolean isAdmin, // 干部管理员直接审批
                                      Integer approvalUserId, // 干部管理员直接审批时，选择的审批人
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date approvalTime, // 干部管理员直接审批时可修改时间
                                     HttpServletRequest request) {

        //int userId = ShiroHelper.getCurrentUserId();

        if(BooleanUtils.isTrue(isAdmin)){
            SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CADREADMIN);
            if(approvalTime==null) approvalTime = new Date();
            if(approvalUserId==null) approvalUserId = ShiroHelper.getCurrentUserId();
        }else{
            approvalUserId = ShiroHelper.getCurrentUserId();
            if (!claApplyService.canApproval(approvalUserId, applyId, approvalTypeId))
                return failed("您没有权限进行审批");
            approvalTime = new Date();
        }

        claApprovalLogService.approval(approvalUserId, applyId, approvalTypeId, pass, approvalTime, remark);

        if (BooleanUtils.isNotTrue(isAdmin)) {
            //if (springProps.claApplySendApprovalMsg) {
            // 如果在工作日周一至周五，8:30-17:30，那么就立即发送给下一个领导
            // 短信通知下一个审批人
            Date now = new Date();
            String nowTime = DateUtils.formatDate(now, "HHmm");
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if ((dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY)
                    && (nowTime.compareTo("0830") >= 0 && nowTime.compareTo("1730") <= 0)) {

                Map<String, Integer> resultMap = claShortMsgService.sendApprovalMsg(applyId);
                logger.info("【干部请假】在指定时间自动发送给下一个审批人，结果:" + JSONUtils.toString(resultMap, MixinUtils.baseMixins(), false));
            }
            //}
        }

        logger.info(addLog(LogConstants.LOG_CLA, "干部请假申请审批：%s", applyId));

        return success(FormUtils.SUCCESS);
    }

    // 干部管理员直接修改审批
    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping("/claApply_approval_direct_au")
    public String claApply_approval_direct_au(int approvalLogId, ModelMap modelMap) {

        ClaApprovalLog approvalLog = claApprovalLogMapper.selectByPrimaryKey(approvalLogId);
        modelMap.put("approvalLog", approvalLog);

        return "cla/claApply/claApply_approval_direct_au";
    }

    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping(value = "/claApply_approval_direct_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApply_approval_direct_au(HttpServletRequest request,
                                               int applyId,
                                               int approvalLogId,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date approvalTime,
                                               String remark, ModelMap modelMap) {


        ClaApprovalLog approvalLog = claApprovalLogMapper.selectByPrimaryKey(approvalLogId);
        String before = JSONUtils.toString(approvalLog, false);
        logger.info(addLog(LogConstants.LOG_CLA, "修改审批意见和审批时间，修改前：%s", before));

        ClaApprovalLog record = new ClaApprovalLog();
        record.setId(approvalLogId);
        record.setCreateTime(approvalTime);
        record.setRemark(remark);
        claApprovalLogMapper.updateByPrimaryKeySelective(record);

        ClaApply claApply = claApplyMapper.selectByPrimaryKey(applyId);
        sysApprovalLogService.add(applyId, claApply.getUser().getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CLA_APPLY,
                "修改审批", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, before);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("claApply:download")
    @RequestMapping("/claApply_download")
    public void claApply_download(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ClaApplyFile claApplyFile = claApplyFileMapper.selectByPrimaryKey(id);

        if (ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)) { // 干部管理员有下载权限
            int userId = loginUser.getId();
            CadreView cadre = cadreService.dbFindByUserId(userId);
            Integer applyId = claApplyFile.getApplyId();
            ClaApply claApply = claApplyMapper.selectByPrimaryKey(applyId); // 本人有下载权限
            if (claApply.getCadreId().intValue() != cadre.getId().intValue()) {

                Set<Integer> cadreIdSet = claApplyService.findApprovalCadreIdSet(loginUser.getId()); // 审批人有下载权限
                if (!cadreIdSet.contains(claApply.getCadreId()))
                    throw new UnauthorizedException();
            }
        }

        DownloadUtils.download(request, response,
                springProps.uploadPath + claApplyFile.getFilePath(), claApplyFile.getFileName());
    }

    @RequiresPermissions("claApply:view")
    @RequestMapping("/claApply_view")
    public String claApply_view(Integer id, ModelMap modelMap) {

        ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
        Integer cadreId = claApply.getCadreId();

        // 判断一下查看权限++++++++++++++++++++???
        if (ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ClaApproverTypeBean approverTypeBean = shiroUser.getClaApproverTypeBean();
                if (approverTypeBean == null || !approverTypeBean.getApprovalCadreIdSet().contains(claApply.getCadreId()))
                    throw new OpException("您没有权限");
            }
        }

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        SysUserView uv = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", uv);
        modelMap.put("cadre", cadre);
        modelMap.put("claApply", claApply);

        modelMap.put("cadreMobile", userBeanService.getMsgMobile(cadre.getUserId()));

        List<ClaApplyFile> files = claApplyService.getFiles(claApply.getId());
        modelMap.put("files", files);

        Map<Integer, ClaApprovalResult> approvalResultMap = claApplyService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);

        // 有书记、校长审批时，需要导出
        boolean needExport = false;
        Map<Integer, ClaApproverType> approverTypeMap = claApproverTypeService.findAll();
        for (Integer key : approvalResultMap.keySet()) {
            ClaApprovalResult approvalResult = approvalResultMap.get(key);
            if (key > 0 && (approvalResult.getValue() == null || approvalResult.getValue() != -1)) {
                ClaApproverType approverType = approverTypeMap.get(key);
                if (approverType.getType() == ClaConstants.CLA_APPROVER_TYPE_SECRETARY) {
                    needExport = true;
                } else if (approverType.getType() == ClaConstants.CLA_APPROVER_TYPE_MASTER) {
                    needExport = true;
                }
            }
        }
        modelMap.put("needExport", needExport);

        return "cla/user/claApply/claApply_view";
    }

    @RequiresPermissions("claApply:view")
    @RequestMapping("/claApply_yearLogs")
    public String claApply_yearLogs(Integer id, ModelMap modelMap) {

        ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
        Integer currentYear = DateUtils.getYear(claApply.getApplyDate());
        modelMap.put("claApply", claApply);
        modelMap.put("currentYear", currentYear);
        return "cla/user/claApply/claApply_yearLogs";
    }

    @RequiresPermissions("claApply:view")
    @RequestMapping("/claApply_yearLogs_data")
    @ResponseBody
    public void claApply_yearLogs_data(@CurrentUser SysUserView loginUser, Integer cadreId, Integer year,
                                        Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {

        // 判断一下查看权限++++++++++++++++++++???
        if (ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ClaApproverTypeBean approverTypeBean = shiroUser.getClaApproverTypeBean();
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
        ClaApplyExample example = new ClaApplyExample();
        ClaApplyExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        criteria.andIsAgreedEqualTo(true);
        criteria.andApplyDateBetween(DateUtils.parseDate(year + "-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(year + "-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");

        long count = claApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaApply> records = claApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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


    @RequiresPermissions("claApply:list")
    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping("/claApply")
    public String claApply(Integer cadreId,
                            // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(同意申请) 2 已完成审批(不同意申请) 或0：未完成审批）
                            @RequestParam(required = false, defaultValue = "0") int status, // -1: 已删除的记录
                            ModelMap modelMap) {

        modelMap.put("status", status);
        if (cadreId != null) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "cla/claApply/claApply_page";
    }

    // 管理员添加申请
    @RequiresPermissions("claApply:edit")
    @RequestMapping("/claApply_au")
    public String claApply_au() {

        return "cla/claApply/claApply_au";
    }

    // 管理员销假
    @RequiresPermissions("claApply:edit")
    @RequestMapping("/claApply_back")
    public String claApply_back(int applyId, ModelMap modelMap) {

        ClaApply claApply = claApplyService.get(applyId);
        modelMap.put("claApply", claApply);

        return "cla/user/claApply/claApply_back";
    }


    @RequiresPermissions("claApply:list")
    @RequestMapping("/claApply_approvers")
    @ResponseBody
    public void claApply_approvers(int applyId, int approvalTypeId, HttpServletResponse response) throws IOException {

        // 读取所有审批人
        ClaApply claApply = claApplyMapper.selectByPrimaryKey(applyId);
        List<SysUserView> approvers = claApplyService.findApprovers(claApply.getCadreId(), approvalTypeId);

        Map<String, Object> resultMap = success();

        ClaApprovalLog approvalLog = claApprovalLogService.getApprovalLog(applyId, approvalTypeId);
        if (approvalLog != null) { // 如果已审批，显示审批人
            SysUserView uv = sysUserService.findById(approvalLog.getUserId());
            resultMap.put("uv", uv);
        }


        resultMap.put("approvers", approvers);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.write(response, resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("claApply:list")
    @RequiresRoles(RoleConstants.ROLE_CADREADMIN)
    @RequestMapping("/claApply_data")
    public void claApply_data(HttpServletResponse response,
                               @SortParam(required = false, defaultValue = "create_time", tableName = "cla_apply") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,
                               Integer cadreId,
                               @RequestDateRange DateRange _applyDate,
                               Byte type, // 出行时间范围
                               Boolean isModify,
                               Boolean isBack,
                               // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(同意申请) 2 已完成审批(不同意申请) 或0：未完成审批）
                               @RequestParam(required = false, defaultValue = "0") int status,// -1: 已删除的记录
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {


        Map map = claApplyService.findApplyList(response, cadreId, _applyDate,
                type, isModify, isBack, status, sort, order, pageNo, springProps.pageSize, export);
        if (map == null) return; // 导出
        CommonList commonList = (CommonList) map.get("commonList");

        Map resultMap = new HashMap();
        resultMap.put("rows", map.get("applys"));
        resultMap.put("records", commonList.recNum);
        resultMap.put("page", commonList.pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", false);
        //request.setAttribute("needApproverList", true);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 非管理员  审批人身份 审批记录
    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequiresPermissions("claApply:approvalList")
    @RequestMapping("/claApplyList")
    public String claApplyList(// 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                @RequestParam(required = false, defaultValue = "0") int status,
                                Integer cadreId, ModelMap modelMap) {

        modelMap.put("status", status);

        if (cadreId != null) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "cla/claApply/claApplyList_page";
    }

    // 非管理员  审批人身份 审批记录
    @RequiresRoles(RoleConstants.ROLE_CADRE)
    @RequiresPermissions("claApply:approvalList")
    @RequestMapping("/claApplyList_data")
    public void claApplyList_data(@CurrentUser SysUserView loginUser, HttpServletResponse response,
                                   Integer cadreId,
                                   @RequestDateRange DateRange _applyDate,
                                   Byte type, // 出行时间范围
                                   // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                   @RequestParam(required = false, defaultValue = "0") int status,
                                   Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {


        Map map = claApplyService.findApplyList(loginUser.getId(), cadreId, _applyDate,
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

    @RequiresPermissions("claApply:edit")
    @RequestMapping(value = "/claApply_change", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApply_change(ClaApply record,
                                   String _applyDate,
                                   MultipartFile _modifyProof, String modifyRemark,
                                   HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_applyDate)) {
            record.setApplyDate(DateUtils.parseDate(_applyDate, DateUtils.YYYY_MM_DD));
        }

        String modifyProof = null;
        String modifyProofFileName = null;
        if (_modifyProof != null && !_modifyProof.isEmpty()) {

            modifyProofFileName = _modifyProof.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "cla_apply_modify" + FILE_SEPARATOR
                    + fileName;
            String ext = FileUtils.getExtention(modifyProofFileName);
            modifyProof = realPath + ext;
            FileUtils.copyFile(_modifyProof, new File(springProps.uploadPath + modifyProof));

            String swfPath = realPath + ".swf";
            pdf2Swf(modifyProof, swfPath);
        }

        /*if (id == null) {
            record.setCreateTime(new Date());
            record.setIp(IpUtils.getRealIp(request));

            record.setStatus(true);// 提交
            record.setFlowNode(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST);

            claApplyService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CLA, "添加干部请假申请：%s", record.getId()));
        } else {*/
        //record.setStatus(true);
        claApplyService.modify(record, modifyProof, modifyProofFileName, modifyRemark);
        logger.info(addLog(LogConstants.LOG_CLA, "更新干部请假申请：%s", record.getId()));
        /*}*/

        return success(FormUtils.SUCCESS);
    }

    // 行程变更
    @RequiresPermissions("claApply:edit")
    @RequestMapping("/claApply_change")
    public String claApply_change(Integer id, ModelMap modelMap) {

        if (id != null) {
            ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
            modelMap.put("claApply", claApply);

            CadreView cadre = cadreViewMapper.selectByPrimaryKey(claApply.getCadreId());
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        List<String> countryList = new ArrayList<>();
        Map<Integer, Country> countryMap = countryService.findAll();
        for (Country country : countryMap.values()) {
            countryList.add(country.getCninfo());
        }
        modelMap.put("countryList", JSONUtils.toString(countryList));

        return "cla/claApply/claApply_change";
    }

    // 逻辑删除
    @RequiresPermissions("claApply:del")
    @RequestMapping(value = "/claApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map claApply_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            claApplyService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CLA, "批量删除[可找回]干部请假申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("claApply:del")
    @RequestMapping(value = "/claApply_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map claApply_batchUnDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            claApplyService.batchUnDel(ids);
            logger.info(addLog(LogConstants.LOG_CLA, "批量找回干部请假申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 删除已经逻辑删除的申请
    @RequiresPermissions("claApply:del")
    @RequestMapping(value = "/claApply_doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map claApply_doBatchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            claApplyService.doBatchDel(ids);
            logger.info(addLog(LogConstants.LOG_CLA, "批量删除[真删除]干部请假申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
