package controller.party;

import bean.UserBean;
import controller.BaseController;
import domain.*;
import domain.MemberTransferExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberTransferMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class MemberTransferController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private VerifyAuth<MemberTransfer> checkVerityAuth(int id){
        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberTransfer, memberTransfer.getPartyId());
    }

    private VerifyAuth<MemberTransfer> checkVerityAuth2(int id){
        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberTransfer, memberTransfer.getToPartyId());
    }

    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_view")
    public String memberTransfer_view(int userId, ModelMap modelMap) {

        UserBean userBean = userBeanService.get(userId);
        modelMap.put("userBean", userBean);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);

        modelMap.put("fromParty", partyMap.get(userBean.getPartyId()));
        modelMap.put("fromBranch", branchMap.get(userBean.getBranchId()));

        MemberTransfer memberTransfer = memberTransferService.get(userId);
        modelMap.put("memberTransfer", memberTransfer);

        modelMap.put("locationMap", locationService.codeMap());

        if(memberTransfer!=null) {
            if (memberTransfer.getToPartyId() != null) {
                modelMap.put("toParty", partyMap.get(memberTransfer.getToPartyId()));
            }
            if (memberTransfer.getToBranchId() != null) {
                modelMap.put("toBranch", branchMap.get(memberTransfer.getToBranchId()));
            }
        }

        return "party/memberTransfer/memberTransfer_view";
    }

    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer")
    public String memberTransfer() {

        return "index";
    }

    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_page")
    public String memberTransfer_page(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核
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

        // 转出分党委待审核总数
        modelMap.put("partyApprovalCount", memberTransferService.count(null, null, (byte)1));
        // 转入分党委待审核数目
        modelMap.put("toPartyApprovalCount", memberTransferService.count(null, null, (byte)2));

        return "party/memberTransfer/memberTransfer_page";
    }
    
    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_data")
    public void memberTransfer_data(@RequestParam(defaultValue = "1")Integer cls,HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_transfer") String sort,
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

        MemberTransferExample example = new MemberTransferExample();
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
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
        }else if(cls==2){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK);
            statusList.add(SystemConstants.MEMBER_TRANSFER_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        }
        if (export == 1) {
            memberTransfer_export(example, response);
            return;
        }

        int count = memberTransferMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberTransfers);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberTransfer.class, MemberTransferMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_approval")
    public String memberTransfer_approval(@CurrentUser SysUser loginUser, Integer id,
                                      byte type, // 1:转出分党委审核 2：转入分党委审核
                                      ModelMap modelMap) {

        MemberTransfer currentMemberTransfer = null;
        if (id != null) {
            currentMemberTransfer = memberTransferMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberTransfer.getStatus() != SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
                    currentMemberTransfer = null;
            }
            if (type == 2) {
                if (currentMemberTransfer.getStatus() != SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY)
                    currentMemberTransfer = null;
            }
        } else {
            currentMemberTransfer = memberTransferService.next(type, null);
        }
        if (currentMemberTransfer == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberTransfer", currentMemberTransfer);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberTransfer.getPartyId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberTransfer.getToPartyId()));
        }

        // 读取总数
        modelMap.put("count", memberTransferService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberTransferService.next(type, currentMemberTransfer));
        // 上一条记录
        modelMap.put("last", memberTransferService.last(type, currentMemberTransfer));

        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("branchMap", branchService.findAll());

        return "party/memberTransfer/memberTransfer_approval";
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping("/memberTransfer_deny")
    public String memberTransfer_deny(Integer id, ModelMap modelMap) {

        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        modelMap.put("memberTransfer", memberTransfer);
        Integer userId = memberTransfer.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberTransfer/memberTransfer_deny";
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_deny(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                  Integer id,
                                  byte type,
                                  String reason) {

        MemberTransfer memberTransfer = null;
        if(type==1) {
            VerifyAuth<MemberTransfer> verifyAuth = checkVerityAuth(id);
            memberTransfer = verifyAuth.entity;
        }else if(type==2){
            VerifyAuth<MemberTransfer> verifyAuth = checkVerityAuth2(id);
            memberTransfer = verifyAuth.entity;
        }else{
            throw new RuntimeException("审核类型错误");
        }
        int loginUserId = loginUser.getId();
        int userId = memberTransfer.getUserId();

        memberTransferService.deny(memberTransfer.getUserId(), reason);
        logger.info(addLog(SystemConstants.LOG_OW, "拒绝校内组织关系转接申请：%s", id));

        applyApprovalLogService.add(memberTransfer.getId(),
                memberTransfer.getPartyId(), memberTransfer.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, (type == 1)
                        ? "转出分党委审核" : "转入分党委审核", (byte) 0, reason);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_check(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                       byte type, // 1:转出分党委审核 2：转入分党委审核
                                       Integer id) {
        MemberTransfer memberTransfer = null;
        if(type==1) {
            VerifyAuth<MemberTransfer> verifyAuth = checkVerityAuth(id);
            memberTransfer = verifyAuth.entity;

            memberTransferService.check1(memberTransfer.getUserId());
            logger.info(addLog(SystemConstants.LOG_OW, "审核校内组织关系转接申请：%s", id));
        }
        if(type==2) {
            VerifyAuth<MemberTransfer> verifyAuth = checkVerityAuth2(id);
            memberTransfer = verifyAuth.entity;

            memberTransferService.check2(memberTransfer.getUserId(), false);
            logger.info(addLog(SystemConstants.LOG_OW, "通过校内组织关系转接申请：%s", id));
        }
        int loginUserId = loginUser.getId();
        int userId = memberTransfer.getUserId();
        applyApprovalLogService.add(memberTransfer.getId(),
                memberTransfer.getPartyId(), memberTransfer.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, (type == 1)
                        ? "转出分党委审核" : "转入分党委审核", (byte) 1, null);
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping(value = "/memberTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_au(MemberTransfer record,
                                    String _payTime, String _fromHandleTime, HttpServletRequest request) {

        Integer id = record.getId();

        if (memberTransferService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);
        if(member.getPartyId().byteValue() == record.getToPartyId()){
            return failed("转入不能是当前所在分党委");
        }

        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());


        if (id == null) {
            record.setApplyTime(new Date());
            record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
            memberTransferService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "添加校内组织关系转接申请：%s", record.getId()));
        } else {
            record.setStatus(null); // 不改状态
            memberTransferService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新校内组织关系转接申请：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping("/memberTransfer_au")
    public String memberTransfer_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
            modelMap.put("memberTransfer", memberTransfer);

            modelMap.put("userBean", userBeanService.get(memberTransfer.getUserId()));

            Integer userId = memberTransfer.getUserId();
            UserBean userBean = userBeanService.get(userId);
            modelMap.put("userBean", userBean);

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);

            modelMap.put("fromParty", partyMap.get(userBean.getPartyId()));
            modelMap.put("fromBranch", branchMap.get(userBean.getBranchId()));

            if (memberTransfer.getToPartyId() != null) {
                modelMap.put("toParty", partyMap.get(memberTransfer.getToPartyId()));
            }
            if (memberTransfer.getToBranchId() != null) {
                modelMap.put("toBranch", branchMap.get(memberTransfer.getToBranchId()));
            }

        }
        return "party/memberTransfer/memberTransfer_au";
    }

    @RequiresPermissions("memberTransfer:del")
    @RequestMapping(value = "/memberTransfer_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberTransferService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除校内组织关系转接：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:del")
    @RequestMapping(value = "/memberTransfer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberTransferService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除校内组织关系转接：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberTransfer_export(MemberTransferExample example, HttpServletResponse response) {

        List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExample(example);
        int rownum = memberTransferMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户",/*"人员类别","转入单位","转出单位",*/"转出办理时间","状态"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberTransfer memberTransfer = memberTransfers.get(i);
            String[] values = {
                        memberTransfer.getUserId()+"",/*
                                            memberTransfer.getType()+"",
                                            memberTransfer.getToUnit(),
                                            memberTransfer.getFromUnit(),*/
                                            DateUtils.formatDate(memberTransfer.getFromHandleTime(), DateUtils.YYYY_MM_DD),
                                            memberTransfer.getStatus()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "校内组织关系转接_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
