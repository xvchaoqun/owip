package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberReturnExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberReturnMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberReturnController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private VerifyAuth<MemberReturn> checkVerityAuth(int id){
        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberReturn, memberReturn.getPartyId(), memberReturn.getBranchId());
    }

    private VerifyAuth<MemberReturn> checkVerityAuth2(int id){
        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberReturn, memberReturn.getPartyId());
    }

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn")
    public String memberReturn() {

        return "index";
    }

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn_page")
    public String memberReturn_page(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核
                                Integer userId,
                                Integer partyId,
                                Integer branchId,ModelMap modelMap) {

        modelMap.put("cls", cls);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        // 党支部待审核总数
        modelMap.put("branchApprovalCount", memberReturnService.count(null, null, (byte)1));
        // 分党委待审核数目
        modelMap.put("partyApprovalCount", memberReturnService.count(null, null, (byte)2));

        return "party/memberReturn/memberReturn_page";
    }

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn_data")
    public void memberReturn_data(@RequestParam(defaultValue = "1")Integer cls, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_return") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberReturnExample example = new MemberReturnExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if(cls==1){
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_APPLY);
        }else if(cls==2){
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_DENY);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY);
        }

        if (export == 1) {
            memberReturn_export(example, response);
            return;
        }

        int count = memberReturnMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberReturn> memberReturns = memberReturnMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberReturns);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberReturn.class, MemberReturnMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("memberReturn:edit")
    @RequestMapping(value = "/memberReturn_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_au(MemberReturn record, String _applyTime, String _activeTime, String _candidateTime,
                                  String _growTime, String _positiveTime,HttpServletRequest request) {

        Integer id = record.getId();

        if (memberReturnService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if(StringUtils.isNotBlank(_applyTime)){
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_activeTime)){
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_candidateTime)){
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_growTime)){
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_positiveTime)){
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            enterApplyService.memberReturn(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加留学归国人员申请恢复组织生活：%s", record.getId()));
        } else {

            record.setStatus(null); // 更新的时候不能更新状态
            memberReturnService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新留学归国人员申请恢复组织生活：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn_approval")
    public String memberReturn_approval(@CurrentUser SysUser loginUser, Integer id,
                                    byte type, // 1:支部审核 2：分党委审核
                                    ModelMap modelMap) {

        MemberReturn currentMemberReturn = null;
        if (id != null) {
            currentMemberReturn = memberReturnMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberReturn.getStatus() != SystemConstants.MEMBER_RETURN_STATUS_APPLY)
                    currentMemberReturn = null;
            }
            if (type == 2) {
                if (currentMemberReturn.getStatus() != SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY)
                    currentMemberReturn = null;
            }
        } else {
            currentMemberReturn = memberReturnService.next(type, null);
        }
        if (currentMemberReturn == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberReturn", currentMemberReturn);

        // 是否是当前记录的管理员
        if(type==1){
            modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), currentMemberReturn.getBranchId()));
        }
        if(type==2){
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberReturn.getPartyId()));
        }

        // 读取总数
        modelMap.put("count", memberReturnService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberReturnService.next(type, currentMemberReturn));
        // 上一条记录
        modelMap.put("last", memberReturnService.last(type, currentMemberReturn));

        return "party/memberReturn/memberReturn_approval";
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping("/memberReturn_deny")
    public String memberReturn_deny(Integer id, ModelMap modelMap) {

        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        modelMap.put("memberReturn", memberReturn);
        Integer userId = memberReturn.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberReturn/memberReturn_deny";
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_deny(@CurrentUser SysUser loginUser, String reason,
                                Integer id,
                                byte type, // 1:支部审核 2：分党委审核
                                HttpServletRequest request) {
        MemberReturn memberReturn = null;
        if(type==1) {
            VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth(id);
            memberReturn = verifyAuth.entity;
        }else if(type==2){
            VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth2(id);
            memberReturn = verifyAuth.entity;
        }else{
            throw new RuntimeException("审核类型错误");
        }
        int loginUserId = loginUser.getId();
        int userId = memberReturn.getUserId();

        enterApplyService.applyBack(memberReturn.getUserId(), reason, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT );
        logger.info(addLog(SystemConstants.LOG_OW, "拒绝留学归国人员申请恢复组织生活：%s", id));

        applyApprovalLogService.add(memberReturn.getId(),
                memberReturn.getPartyId(), memberReturn.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN, (type == 1)
                        ? "党支部审核" : "分党委审核", (byte) 0, reason);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_check(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                      byte type, // 1:支部审核 2：分党委审核
                                      Integer id) {
        MemberReturn memberReturn = null;
        if(type==1) {
            VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth(id);
            boolean isDirectBranch = verifyAuth.isDirectBranch;
            boolean isPartyAdmin = verifyAuth.isPartyAdmin;
            memberReturn = verifyAuth.entity;

            if (isDirectBranch && isPartyAdmin) { // 直属党支部管理员，不需要通过党支部审核
                memberReturnService.addMember(memberReturn.getUserId(), memberReturn.getPoliticalStatus(), true);
                logger.info(addLog(SystemConstants.LOG_OW, "留学归国人员申请恢复组织生活-直属党支部审核：%s", id));
            } else {
                memberReturnService.checkMember(memberReturn.getUserId());
                logger.info(addLog(SystemConstants.LOG_OW, "留学归国人员申请恢复组织生活-党支部审核：%s", id));
            }
        }

        if(type==2) {
            VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth2(id);
            memberReturn = verifyAuth.entity;

            memberReturnService.addMember(memberReturn.getUserId(), memberReturn.getPoliticalStatus(), false);
            logger.info(addLog(SystemConstants.LOG_OW, "留学归国人员申请恢复组织生活-分党委审核：%s", id));
        }

        int loginUserId = loginUser.getId();
        int userId = memberReturn.getUserId();
        applyApprovalLogService.add(memberReturn.getId(),
                memberReturn.getPartyId(), memberReturn.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN, (type == 1)
                        ? "党支部审核" : "分党委审核", (byte) 1, null);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:edit")
    @RequestMapping("/memberReturn_au")
    public String memberReturn_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
            modelMap.put("memberReturn", memberReturn);

            modelMap.put("userBean", userBeanService.get(memberReturn.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberReturn.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberReturn.getPartyId()));
            }
            if (memberReturn.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberReturn.getBranchId()));
            }
        }
        return "party/memberReturn/memberReturn_au";
    }

    @RequiresPermissions("memberReturn:del")
    @RequestMapping(value = "/memberReturn_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberReturnService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除留学归国人员申请恢复组织生活：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:del")
    @RequestMapping(value = "/memberReturn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberReturnService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除留学归国人员申请恢复组织生活：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberReturn_export(MemberReturnExample example, HttpServletResponse response) {

        List<MemberReturn> memberReturns = memberReturnMapper.selectByExample(example);
        int rownum = memberReturnMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","所属分党委","所属党支部","确定为入党积极分子时间","确定为发展对象时间","入党时间","转正时间","状态","备注","创建时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberReturn memberReturn = memberReturns.get(i);
            String[] values = {
                        memberReturn.getUserId()+"",
                                            memberReturn.getPartyId()+"",
                                            memberReturn.getBranchId()+"",
                                            DateUtils.formatDate(memberReturn.getActiveTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(memberReturn.getCandidateTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(memberReturn.getGrowTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(memberReturn.getPositiveTime(), DateUtils.YYYY_MM_DD),
                                            memberReturn.getStatus()+"",
                                            memberReturn.getRemark(),
                                            DateUtils.formatDate(memberReturn.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "留学归国人员申请恢复组织生活_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
