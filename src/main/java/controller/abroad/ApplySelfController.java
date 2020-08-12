package controller.abroad;

import controller.global.OpException;
import domain.abroad.*;
import domain.abroad.ApplySelfExample.Criteria;
import domain.cadre.CadreView;
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
import persistence.abroad.common.ApprovalResult;
import persistence.abroad.common.ApproverTypeBean;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
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
@RequestMapping("/abroad")
public class ApplySelfController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySelf:approval")
    @RequestMapping("/applySelf_approval")
    public String applySelf_approval() {

        return "abroad/applySelf/applySelf_approval";
    }

    // 干部管理员直接审批（代审）
    @RequiresPermissions("applySelf:approval")
    @RequestMapping("/applySelf_approval_direct")
    public String applySelf_approval_direct(int applySelfId, int approvalTypeId, ModelMap modelMap) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        int cadreId = applySelf.getCadreId();

        List<SysUserView> approvers = applySelfService.findApprovers(cadreId, approvalTypeId);
        modelMap.put("approvers", approvers);

        return "abroad/applySelf/applySelf_approval_direct";
    }

    @RequiresPermissions("applySelf:approval")
    @RequestMapping(value = "/applySelf_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_approval( int applySelfId, int approvalTypeId,
                                     boolean pass, String remark,
                                     Boolean isAdmin, // 干部管理员直接审批
                                      Integer approvalUserId, // 干部管理员直接审批时，选择的审批人
                                      MultipartFile _filePath,
                                      @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date approvalTime, // 干部管理员直接审批时可修改时间
                                     HttpServletRequest request) throws IOException, InterruptedException {

        //int userId = ShiroHelper.getCurrentUserId();

        String filePath = null;
        String fileName = null;
        if(BooleanUtils.isTrue(isAdmin)){
            SecurityUtils.getSubject().checkPermission(SystemConstants.PERMISSION_ABROADADMIN);
            if(approvalTime==null) approvalTime = new Date();
            if(approvalUserId==null) approvalUserId = ShiroHelper.getCurrentUserId();
            if(_filePath!=null) {
                filePath = uploadPdfOrImage(_filePath, "applySelf_approval");
                fileName = FileUtils.getFileName(_filePath.getOriginalFilename());
            }
        }else{
            approvalUserId = ShiroHelper.getCurrentUserId();
            if (!applySelfService.canApproval(approvalUserId, applySelfId, approvalTypeId))
                return failed("您没有权限进行审批");
            approvalTime = new Date();
        }

        approvalLogService.approval(approvalUserId, applySelfId, approvalTypeId, pass, approvalTime, remark, filePath, fileName);

        if (BooleanUtils.isNotTrue(isAdmin)) {
            //if (springProps.applySelfSendApprovalMsg) {
            // 如果在工作日周一至周五，8:30-17:30，那么就立即发送给下一个领导
            // 短信通知下一个审批人
            Date now = new Date();
            String nowTime = DateUtils.formatDate(now, "HHmm");
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if ((dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY)
                    && (nowTime.compareTo("0830") >= 0 && nowTime.compareTo("1730") <= 0)) {

                Map<String, Integer> resultMap = abroadShortMsgService.sendApprovalMsg(applySelfId);
                logger.info("【因私审批】在指定时间自动发送给下一个审批人，结果:" + JSONUtils.toString(resultMap, MixinUtils.baseMixins(), false));
            }
            //}
        }

        logger.info(addLog(LogConstants.LOG_ABROAD, "因私出国申请审批：%s", applySelfId));

        return success(FormUtils.SUCCESS);
    }

    // 干部管理员直接修改审批
    @RequiresPermissions(SystemConstants.PERMISSION_ABROADADMIN)
    @RequestMapping("/applySelf_approval_direct_au")
    public String applySelf_approval_direct_au(int approvalLogId, ModelMap modelMap) {

        ApprovalLog approvalLog = approvalLogMapper.selectByPrimaryKey(approvalLogId);
        modelMap.put("approvalLog", approvalLog);

        return "abroad/applySelf/applySelf_approval_direct_au";
    }

    @RequiresPermissions(SystemConstants.PERMISSION_ABROADADMIN)
    @RequestMapping(value = "/applySelf_approval_direct_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_approval_direct_au(HttpServletRequest request,
                                               int applySelfId,
                                               int approvalLogId,
                                               Boolean pass,
                                               @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date approvalTime,
                                               String remark,
                                               MultipartFile _filePath,
                                               ModelMap modelMap) throws IOException, InterruptedException {


        ApprovalLog approvalLog = approvalLogMapper.selectByPrimaryKey(approvalLogId);
        String before = JSONUtils.toString(approvalLog, false);
        logger.info(addLog(LogConstants.LOG_ABROAD, "修改审批意见和审批时间，修改前：%s", before));

        ApprovalLog record = new ApprovalLog();
        record.setId(approvalLogId);
        record.setCreateTime(approvalTime);
        record.setStatus(BooleanUtils.isTrue(pass));
        record.setRemark(remark);
        if(_filePath!=null) {
            record.setFilePath(uploadPdfOrImage(_filePath, "applySelf_approval"));
            record.setFileName(FileUtils.getFileName(_filePath.getOriginalFilename()));
        }

        approvalLogMapper.updateByPrimaryKeySelective(record);

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        sysApprovalLogService.add(applySelfId, applySelf.getUser().getId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_APPLYSELF,
                "修改审批", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, before);

        return success(FormUtils.SUCCESS);
    }

    //清除审批记录
    @RequiresPermissions("applySelf:approval")
    @RequestMapping(value = "/applySelf_clearApproval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_clearApproval(HttpServletRequest request, Integer id) {

        if (id != null) {

            applySelfService.clearApproval(id);
            logger.info(addLog(LogConstants.LOG_ABROAD, "因私出国申请清除审批记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:download")
    @RequestMapping("/applySelf_download")
    public void applySelf_download(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ApplySelfFile applySelfFile = applySelfFileMapper.selectByPrimaryKey(id);

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)) { // 干部管理员有下载权限
            int userId = loginUser.getId();
            CadreView cadre = cadreService.dbFindByUserId(userId);
            int cadreId = cadre.getId();
            Integer applyId = applySelfFile.getApplyId();
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId); // 本人有下载权限
            if (applySelf.getCadreId().intValue() != cadreId) {

                Set<Integer> cadreIdSet = applySelfService.findApprovalCadreIdSet(cadreId); // 审批人有下载权限
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
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                //ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ApproverTypeBean approverTypeBean = applySelfService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
                if (approverTypeBean == null || !approverTypeBean.getApprovalCadreIdSet().contains(applySelf.getCadreId()))
                    throw new OpException("您没有权限");
            }
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView uv = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", uv);
        modelMap.put("cadre", cadre);
        modelMap.put("applySelf", applySelf);

        modelMap.put("cadreMobile", userBeanService.getMsgMobile(cadre.getUserId()));

        List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
        modelMap.put("files", files);

        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);

        // 有书记、校长审批时，需要导出
        boolean needExport = false;
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (Integer key : approvalResultMap.keySet()) {
            ApprovalResult approvalResult = approvalResultMap.get(key);
            if (key > 0 && (approvalResult.getValue() == null || approvalResult.getValue() != -1)) {
                ApproverType approverType = approverTypeMap.get(key);
                if (approverType.getType() == AbroadConstants.ABROAD_APPROVER_TYPE_SECRETARY) {
                    needExport = true;
                } else if (approverType.getType() == AbroadConstants.ABROAD_APPROVER_TYPE_MASTER) {
                    needExport = true;
                }
            }
        }
        modelMap.put("needExport", needExport);

        return "abroad/user/applySelf/applySelf_view";
    }

    @RequiresPermissions("applySelf:view")
    @RequestMapping("/applySelf_yearLogs")
    public String applySelf_yearLogs(Integer id, ModelMap modelMap) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
        Integer currentYear = DateUtils.getYear(applySelf.getApplyDate());
        modelMap.put("applySelf", applySelf);
        modelMap.put("currentYear", currentYear);
        return "abroad/user/applySelf/applySelf_yearLogs";
    }

    @RequiresPermissions("applySelf:view")
    @RequestMapping("/applySelf_yearLogs_data")
    @ResponseBody
    public void applySelf_yearLogs_data(@CurrentUser SysUserView loginUser, Integer cadreId, Integer year,
                                        Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {

        // 判断一下查看权限++++++++++++++++++++???
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                //ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ApproverTypeBean approverTypeBean = applySelfService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
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
        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        criteria.andIsAgreedEqualTo(true);
        criteria.andApplyDateBetween(DateUtils.parseDate(year + "-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(year + "-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");

        long count = applySelfMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }


    @RequiresPermissions("applySelf:list")
    @RequestMapping("/applySelf")
    public String applySelf(Integer cadreId,
                            // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(同意申请) 2 已完成审批(不同意申请) 或0：未完成审批）
                            @RequestParam(required = false, defaultValue = "0") int status, // -1: 已删除的记录
                            ModelMap modelMap) {

        modelMap.put("status", status);
        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "abroad/applySelf/applySelf_page";
    }

    // 管理员添加申请
    @RequiresPermissions("applySelf:edit")
    @RequestMapping("/applySelf_au")
    public String applySelf_au() {

        return "abroad/applySelf/applySelf_au";
    }

    @RequiresPermissions("applySelf:list")
    @RequestMapping("/applySelf_approvers")
    @ResponseBody
    public void applySelf_approvers(int applySelfId, int approvalTypeId, HttpServletResponse response) throws IOException {

        // 读取所有审批人
        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        List<SysUserView> approvers = applySelfService.findApprovers(applySelf.getCadreId(), approvalTypeId);

        Map<String, Object> resultMap = success();
        resultMap.put("approvers", approvers);

        ApprovalLog approvalLog = approvalLogService.getApprovalLog(applySelfId, approvalTypeId);
        if (approvalLog != null) { // 如果已审批，显示审批人
            SysUserView uv = sysUserService.findById(approvalLog.getUserId());
            resultMap.put("uv", uv);
        }
        resultMap.put("approvers", approvers);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.write(response, resultMap, baseMixins);
        return;
    }

    @RequiresPermissions(SystemConstants.PERMISSION_ABROADADMIN)
    @RequestMapping("/applySelf_data")
    public void applySelf_data(HttpServletResponse response,
                               @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_apply_self") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,
                               Integer cadreId,
                               @RequestDateRange DateRange _applyDate,
                               Byte type, // 出行时间范围
                               Boolean isModify,
                               // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(同意申请) 2 已完成审批(不同意申请) 或0：未完成审批）
                               @RequestParam(required = false, defaultValue = "0") int status,// -1: 已删除的记录
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {


        Map map = applySelfService.findApplySelfList(response, cadreId, _applyDate,
                type, isModify, status, sort, order, pageNo, springProps.pageSize, export);
        if (map == null) return; // 导出
        CommonList commonList = (CommonList) map.get("commonList");

        Map resultMap = new HashMap();
        resultMap.put("rows", map.get("applySelfs"));
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
    //@RequiresPermissions("applySelf:approvalList")
    @RequestMapping("/applySelfList")
    public String applySelfList(Integer userId, // 审批人
                                // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                @RequestParam(required = false, defaultValue = "0") int status,
                                Integer cadreId, ModelMap modelMap) {

        modelMap.put("status", status);

        if(userId==null){
            SecurityUtils.getSubject().checkPermission("applySelf:approvalList");
        }else{
            SecurityUtils.getSubject().checkPermission(SystemConstants.PERMISSION_ABROADADMIN);
        }

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "abroad/applySelf/applySelfList_page";
    }

    // 非管理员  审批人身份 审批记录
    //@RequiresPermissions("applySelf:approvalList")
    @RequestMapping("/applySelfList_data")
    public void applySelfList_data(Integer userId, // 审批人
                                   HttpServletResponse response,
                                   Integer cadreId,
                                   @RequestDateRange DateRange _applyDate,
                                   Byte type, // 出行时间范围
                                   // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                   @RequestParam(required = false, defaultValue = "0") int status,
                                   Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {
        if(userId==null){
            userId = ShiroHelper.getCurrentUserId();
            SecurityUtils.getSubject().checkPermission("applySelf:approvalList");
        }else{
            SecurityUtils.getSubject().checkPermission(SystemConstants.PERMISSION_ABROADADMIN);
        }

        Map map = applySelfService.findApplySelfList(userId, cadreId, _applyDate,
                type, status, pageNo, springProps.pageSize);
        CommonList commonList = (CommonList) map.get("commonList");

        Map resultMap = new HashMap();
        resultMap.put("rows", map.get("applySelfs"));
        resultMap.put("records", commonList.recNum);
        resultMap.put("page", commonList.pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", false);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("applySelf:edit")
    @RequestMapping(value = "/applySelf_change", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_change(ApplySelf record,
                                   String _applyDate, String _startDate,
                                   String _endDate,
                                   MultipartFile _modifyProof, String modifyRemark,
                                   HttpServletRequest request) throws IOException, InterruptedException {

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

        String modifyProof = null;
        String modifyProofFileName = null;
        if (_modifyProof != null && !_modifyProof.isEmpty()) {

            modifyProofFileName = _modifyProof.getOriginalFilename();
            modifyProof = upload(_modifyProof, "apply_self_modify");
        }

        applySelfService.modify(record, modifyProof, modifyProofFileName, modifyRemark);
        logger.info(addLog(LogConstants.LOG_ABROAD, "更新因私出国申请：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    // 行程变更
    @RequiresPermissions("applySelf:edit")
    @RequestMapping("/applySelf_change")
    public String applySelf_change(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            modelMap.put("applySelf", applySelf);

            CadreView cadre = iCadreMapper.getCadre(applySelf.getCadreId());
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        modelMap.put("countryList", JSONUtils.toString(countryService.getCountryList()));

        return "abroad/applySelf/applySelf_change";
    }

    // 逻辑删除
    @RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_batchDel(HttpServletRequest request,
                          boolean isDeleted,
                          Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            applySelfService.batchDel(ids, isDeleted);
            logger.info(log(LogConstants.LOG_ABROAD, "批量{0}因私出国申请：{1}",
                    isDeleted?"删除[可找回]":"恢复", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 删除已经逻辑删除的申请
    @RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map applySelf_doBatchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            applySelfService.doBatchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除[真删除]因私出国申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
