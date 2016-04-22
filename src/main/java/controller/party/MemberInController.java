package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberInExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberInMixin;
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
import java.util.*;

@Controller
public class MemberInController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

  /*  private VerifyAuth<MemberIn> checkVerityAuth(int id){
        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberIn, memberIn.getPartyId(), memberIn.getBranchId());
    }*/

    private VerifyAuth<MemberIn> checkVerityAuth2(int id){
        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberIn, memberIn.getPartyId());
    }

    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn")
    public String memberIn() {

        return "index";
    }
    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn_page")
    public String memberIn_page(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核
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

        // 分党委党总支直属党支部待审核总数
        modelMap.put("partyApprovalCount", memberInService.count(null, null, (byte)1));
        // 组织部待审核数目
        modelMap.put("odApprovalCount", memberInService.count(null, null, (byte)2));

        return "party/memberIn/memberIn_page";
    }
    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn_data")
    public void memberIn_data(@RequestParam(defaultValue = "1")Integer cls, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_in") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                     Byte type,
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

        MemberInExample example = new MemberInExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if(cls==1){
            criteria.andStatusEqualTo(SystemConstants.MEMBER_IN_STATUS_APPLY);
        }else if(cls==2){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_IN_STATUS_SELF_BACK);
            statusList.add(SystemConstants.MEMBER_IN_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_IN_STATUS_OW_VERIFY);
        }

        if (export == 1) {
            memberIn_export(example, response);
            return;
        }

        int count = memberInMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberIn> memberIns = memberInMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberIns);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberIn.class, MemberInMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("memberIn:edit")
    @RequestMapping(value = "/memberIn_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_au(MemberIn record, String _payTime, String _applyTime, String _activeTime, String _candidateTime,
                              String _growTime, String _positiveTime,
                              String _fromHandleTime, String _handleTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (memberInService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        record.setHasReceipt((record.getHasReceipt() == null) ? false : record.getHasReceipt());

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
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
        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleTime)){
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            enterApplyService.memberIn(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加组织关系转入：%s", record.getId()));
        } else {
            record.setStatus(null); // 更新的时候不能更新状态
            memberInService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新组织关系转入：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn_approval")
    public String memberIn_approval(@CurrentUser SysUser loginUser, Integer id,
                                        byte type, // 1:支部审核 2：分党委审核
                                        ModelMap modelMap) {

        MemberIn currentMemberIn = null;
        if (id != null) {
            currentMemberIn = memberInMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberIn.getStatus() != SystemConstants.MEMBER_IN_STATUS_APPLY)
                    currentMemberIn = null;
            }
            if (type == 2) {
                if (currentMemberIn.getStatus() != SystemConstants.MEMBER_IN_STATUS_PARTY_VERIFY)
                    currentMemberIn = null;
            }
        } else {
            currentMemberIn = memberInService.next(type, null);
        }
        if (currentMemberIn == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberIn", currentMemberIn);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberIn.getPartyId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", SecurityUtils.getSubject().hasRole("odAdmin"));
        }

        // 读取总数
        modelMap.put("count", memberInService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberInService.next(type, currentMemberIn));
        // 上一条记录
        modelMap.put("last", memberInService.last(type, currentMemberIn));

        return "party/memberIn/memberIn_approval";
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping("/memberIn_deny")
    public String memberIn_deny(Integer id, ModelMap modelMap) {

        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        modelMap.put("memberIn", memberIn);
        Integer userId = memberIn.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberIn/memberIn_deny";
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping(value = "/memberIn_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_deny(@CurrentUser SysUser loginUser, String reason,
                                Integer id,
                                byte type, // 1:分党委党总支直属党支部审核 2：组织部审核
                                HttpServletRequest request) {
        MemberIn memberIn = null;
        if(type==1) {
            VerifyAuth<MemberIn> verifyAuth = checkVerityAuth2(id);
            memberIn = verifyAuth.entity;
        }else if(type==2){
            SecurityUtils.getSubject().checkRole("odAdmin");
            memberIn = memberInMapper.selectByPrimaryKey(id);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        int loginUserId = loginUser.getId();
        int userId = memberIn.getUserId();

        enterApplyService.applyBack(memberIn.getUserId(), reason, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT );
        logger.info(addLog(SystemConstants.LOG_OW, "组织关系转入-返回修改：%s", id));

        applyApprovalLogService.add(memberIn.getId(),
                memberIn.getPartyId(), memberIn.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, (type == 1)
                        ? "分党委审核" : "组织部审核", (byte) 0, reason);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping(value = "/memberIn_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_check(@CurrentUser SysUser loginUser,
                                  Integer id,
                                  byte type, // 1:分党委党总支直属党支部审核 2：组织部审核
                                  HttpServletRequest request) {
        MemberIn memberIn = null;
        if(type==1) {
            VerifyAuth<MemberIn> verifyAuth = checkVerityAuth2(id);
            memberIn = verifyAuth.entity;
            boolean isParty = verifyAuth.isParty;

            if (isParty) { // 分党委审核，需要跳过下一步的组织部审核
                memberInService.checkByParty(memberIn.getUserId(), memberIn.getPoliticalStatus());
                logger.info(addLog(SystemConstants.LOG_OW, "组织关系转入-分党委审核：%s", id));
            } else {
                memberInService.checkMember(memberIn.getUserId());
                logger.info(addLog(SystemConstants.LOG_OW, "组织关系转入-党总支、直属党支部审核：%s", id));
            }
        }
        if(type==2) {
            SecurityUtils.getSubject().checkRole("odAdmin");

            memberIn = memberInMapper.selectByPrimaryKey(id);
            memberInService.addMember(memberIn.getUserId(), memberIn.getPoliticalStatus());
            logger.info(addLog(SystemConstants.LOG_OW, "组织关系转入-审核2：%s", id));
        }

        int loginUserId = loginUser.getId();
        int userId = memberIn.getUserId();
        applyApprovalLogService.add(memberIn.getId(),
                memberIn.getPartyId(), memberIn.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, (type == 1)
                        ? "分党委审核" : "组织部审核", (byte) 1, null);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:edit")
    @RequestMapping("/memberIn_au")
    public String memberIn_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
            modelMap.put("memberIn", memberIn);

            modelMap.put("userBean", userBeanService.get(memberIn.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberIn.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberIn.getPartyId()));
            }
            if (memberIn.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberIn.getBranchId()));
            }
        }
        return "party/memberIn/memberIn_au";
    }

    @RequiresPermissions("memberIn:del")
    @RequestMapping(value = "/memberIn_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberInService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除组织关系转入：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:del")
    @RequestMapping(value = "/memberIn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberInService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除组织关系转入：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberIn_export(MemberInExample example, HttpServletResponse response) {

        List<MemberIn> memberIns = memberInMapper.selectByExample(example);
        int rownum = memberInMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","类别","转出单位","转出单位抬头","介绍信有效期天数","转出办理时间","状态"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberIn memberIn = memberIns.get(i);
            String[] values = {
                        memberIn.getUserId()+"",
                                            memberIn.getType() +"",
                                            memberIn.getFromUnit(),
                                            memberIn.getFromTitle(),
                                            memberIn.getValidDays()+"",
                                            DateUtils.formatDate(memberIn.getFromHandleTime(), DateUtils.YYYY_MM_DD),
                                            memberIn.getStatus()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "组织关系转入_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
