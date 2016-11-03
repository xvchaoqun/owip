package controller.abroad;

import bean.ApprovalResult;
import bean.ApproverTypeBean;
import controller.BaseController;
import domain.abroad.*;
import domain.abroad.ApplySelfExample.Criteria;
import domain.base.Country;
import domain.cadre.Cadre;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.ApplySelfMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import org.springframework.web.multipart.MultipartFile;
import shiro.CurrentUser;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ApplySelfController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySelf:approval")
    @RequestMapping("/applySelf_approval")
    public String applySelf_approval() {

        return "abroad/applySelf/applySelf_approval";
    }

    @RequiresPermissions("applySelf:approval")
    @RequestMapping(value = "/applySelf_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_approval(@CurrentUser SysUserView loginUser,
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

                applySelfService.approval(applySelf);
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

        logger.info(addLog(SystemConstants.LOG_ABROAD, "因私出国申请审批：%s", applySelfId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applySelf:download")
    @RequestMapping("/applySelf_download")
    public void applySelf_download(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

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
                if (approverTypeBean==null || !approverTypeBean.getApprovalCadreIdSet().contains(applySelf.getCadreId()))
                    throw new RuntimeException("您没有权限");
            }
        }

        Cadre cadre = cadreService.findAll().get(cadreId);
        SysUserView uv = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", uv);
        modelMap.put("cadre", cadre);
        modelMap.put("applySelf", applySelf);

        modelMap.put("cadreMobile", cadreConcatService.getCadreMobile(cadre.getUserId()));

        List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
        modelMap.put("files", files);

        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);

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
    public void applySelf_yearLogs_data(@CurrentUser SysUserView loginUser,Integer cadreId, Integer year,
                                        Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {

        // 判断一下查看权限++++++++++++++++++++???
        if(!SecurityUtils.getSubject().hasRole("cadreAdmin")) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            if(cadre.getId().intValue()!=cadreId) {
                ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();
                if (approverTypeBean==null || !approverTypeBean.getApprovalCadreIdSet().contains(cadreId))
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

        // 本年度的申请记录（通过审批的）
        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        criteria.andIsAgreedEqualTo(true);
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
                               // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(同意申请) 2 已完成审批(不同意申请) 或0：未完成审批）
                               @RequestParam(required = false, defaultValue = "0") int status, // -1: 已删除的记录
                               ModelMap modelMap) {

        modelMap.put("status", status);
        if (cadreId != null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "abroad/applySelf/applySelf_page";
    }

    @RequiresPermissions("applySelf:list")
    @RequestMapping("/applySelf_approvers")
    @ResponseBody
    public void getApprovers(int applySelfId, int approvalTypeId, HttpServletResponse response) throws IOException {

        // 读取所有审批人
        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        List<SysUserView> approvers = applySelfService.findApprovers(applySelf.getCadreId(), approvalTypeId);

        Map<String, Object> resultMap = success();
        resultMap.put("approvers", approvers);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(ApplySelf.class, ApplySelfMixin.class);
        JSONUtils.write(response, resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("applySelf:list")
    @RequiresRoles("cadreAdmin")
    @RequestMapping("/applySelf_data")
    public void applySelf_data(HttpServletResponse response,
                               @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_apply_self") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,
                               Integer cadreId,
                               String _applyDate,
                               Byte type, // 出行时间范围
                               Boolean isModify,
                               // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(同意申请) 2 已完成审批(不同意申请) 或0：未完成审批）
                               @RequestParam(required = false, defaultValue = "0") int status,// -1: 已删除的记录
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {


        Map map = applySelfService.findApplySelfList(response, cadreId, _applyDate,
                type, isModify, status, sort, order, pageNo, springProps.pageSize, export);
        if(map == null) return; // 导出
        CommonList commonList = (CommonList) map.get("commonList");

        Map resultMap = new HashMap();
        resultMap.put("rows", map.get("applySelfs"));
        resultMap.put("records", commonList.recNum);
        resultMap.put("page", commonList.pageNo);
        resultMap.put("total", commonList.pageNum);

        request.setAttribute("isView", false);
        //request.setAttribute("needApproverList", true);

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
    public String applySelfList_page(// 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                     @RequestParam(required = false, defaultValue = "0") int status,
                                     Integer cadreId, ModelMap modelMap) {

        modelMap.put("status", status);

        if (cadreId != null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "abroad/applySelf/applySelfList_page";
    }
    // 非管理员  审批人身份 审批记录
    @RequiresRoles("cadre")
    @RequiresPermissions("applySelf:approvalList")
    @RequestMapping("/applySelfList_data")
    public void applySelfList_data(@CurrentUser SysUserView loginUser, HttpServletResponse response,
                                     Integer cadreId,
                                     String _applyDate,
                                     Byte type, // 出行时间范围
                                     // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                     @RequestParam(required = false, defaultValue = "0") int status,
                                     Integer pageSize, Integer pageNo, HttpServletRequest request) throws IOException {


        Map map = applySelfService.findApplySelfList(loginUser.getId(), cadreId, _applyDate,
                type, status, pageNo, springProps.pageSize);
        CommonList commonList = (CommonList) map.get("commonList");

        Map resultMap = new HashMap();
        resultMap.put("rows", map.get("applySelfs"));
        resultMap.put("records", commonList.recNum);
        resultMap.put("page", commonList.pageNo);
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
    public Map do_applySelf_au(ApplySelf record,
                               String _applyDate, String _startDate,
                               String _endDate,
                               MultipartFile _modifyProof, String remark,
                               HttpServletRequest request) {

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
            String fileName = UUID.randomUUID().toString();
            String realPath = File.separator
                    + "apply_self_modify" + File.separator
                    + fileName;
            String ext = FileUtils.getExtention(modifyProofFileName);
            modifyProof = realPath + ext;
            FileUtils.copyFile(_modifyProof, new File(springProps.uploadPath + modifyProof));
        }

        /*if (id == null) {
            record.setCreateTime(new Date());
            record.setIp(IpUtils.getRealIp(request));

            record.setStatus(true);// 提交
            record.setFlowNode(SystemConstants.APPROVER_TYPE_ID_OD_FIRST);

            applySelfService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "添加因私出国申请：%s", record.getId()));
        } else {*/
            //record.setStatus(true);
            applySelfService.modify(record, modifyProof, modifyProofFileName, remark);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "更新因私出国申请：%s", record.getId()));
        /*}*/

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
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
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

    /*@RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            applySelfService.del(id);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "删除因私出国申请：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
*/
    // 逻辑删除
    @RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            applySelfService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量删除[可找回]因私出国申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("applySelf:del")
    @RequestMapping(value = "/applySelf_batchUnDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchUnDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            applySelfService.batchUnDel(ids);
            logger.info(addLog(SystemConstants.LOG_ABROAD, "批量找回因私出国申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
