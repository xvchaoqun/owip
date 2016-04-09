package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberInflowExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberInflowMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
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
public class MemberInflowController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow")
    public String memberInflow() {

        return "index";
    }
    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow_page")
    public String memberInflow_page(@RequestParam(defaultValue = "4")Integer cls, Integer userId,
                                    Integer partyId,
                                    Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        if(cls==4) {
            // 支部待审核总数
            modelMap.put("branchApprovalCount", memberInflowService.count(null, null, (byte)1));
            // 分党委待审核数目
            modelMap.put("partyApprovalCount", memberInflowService.count(null, null, (byte)2));
        }

        return "party/memberInflow/memberInflow_page";
    }
    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow_data")
    public void memberInflow_data(@RequestParam(defaultValue = "4")Integer cls, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id",tableName = "ow_member_inflow") String sort,
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

        MemberInflowExample example = new MemberInflowExample();
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

        if(cls==4){// 未完成审核

            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_INFLOW_STATUS_APPLY);
            statusList.add(SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY);
            criteria.andInflowStatusIn(statusList);
        }
        if(cls==5) {// 已完成审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY);
        }
        if(cls==6) {// 未通过
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_BACK);
        }

        if (export == 1) {
            memberInflow_export(example, response);
            return;
        }

        int count = memberInflowMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberInflow> memberInflows = memberInflowMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", memberInflows);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberInflow.class, MemberInflowMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("memberInflow:edit")
    @RequestMapping(value = "/memberInflow_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_au(MemberInflow record,
                                  String _flowTime, String _growTime, String _outflowTime,
                                  HttpServletRequest request) {

        Integer id = record.getId();

        if (memberInflowService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        record.setHasPapers((record.getHasPapers() == null) ? false : record.getHasPapers());

        if(StringUtils.isNotBlank(_flowTime)){
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_growTime)){
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_outflowTime)) {
            record.setOutflowTime(DateUtils.parseDate(_outflowTime, DateUtils.YYYY_MM_DD));
        }

        if(record.getPartyId()!=null) {
            record.setPartyName(partyService.findAll().get(record.getPartyId()).getName());
        }
        if(record.getBranchId()!=null) {
            record.setBranchName(branchService.findAll().get(record.getBranchId()).getName());
        }

        if (id == null) {
            enterApplyService.memberInflow(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "添加流入党员：%s", record.getId()));
        } else {

            memberInflowService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "更新流入党员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow_approval")
    public String memberInflow_approval(@CurrentUser SysUser loginUser, Integer id,
                                         byte type, // 1:支部审核 2：分党委审核
                                         ModelMap modelMap) {

        MemberInflow currentMemberInflow = null;
        if(id!=null) {
            currentMemberInflow = memberInflowMapper.selectByPrimaryKey(id);
            if(type==1){
                if(currentMemberInflow.getInflowStatus()!=SystemConstants.MEMBER_INFLOW_STATUS_APPLY)
                    currentMemberInflow = null;
            }
            if(type==2){
                if(currentMemberInflow.getInflowStatus()!=SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY)
                    currentMemberInflow = null;
            }
        }else{
            currentMemberInflow = memberInflowService.next(type, null);
        }
        if(currentMemberInflow==null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberInflow", currentMemberInflow);

        // 是否是当前记录的管理员
        if(type==1){
            modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), currentMemberInflow.getBranchId()));
        }
        if(type==2){
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberInflow.getPartyId()));
        }

        // 读取总数
        modelMap.put("count", memberInflowService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberInflowService.next(type, currentMemberInflow));
        // 上一条记录
        modelMap.put("last", memberInflowService.last(type, currentMemberInflow));

        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("branchMap", branchService.findAll());

        return "party/memberInflow/memberInflow_approval";
    }

    @RequiresPermissions("memberInflow:update")
    @RequestMapping(value = "/memberInflow_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_deny(@CurrentUser SysUser loginUser,HttpServletRequest request,
                                    Integer id,
                                    byte type, // 1:支部审核 2：分党委审核
                                    String remark) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        int userId= memberInflow.getUserId();
        Integer branchId = memberInflow.getBranchId();
        Integer partyId = memberInflow.getPartyId();
        boolean branchAdmin = branchMemberService.isPresentAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectBranch(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        enterApplyService.applyBack(memberInflow.getUserId(), remark, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT );
        logger.info(addLog(request, SystemConstants.LOG_OW, "拒绝流入党员申请：%s", id));

        applyApprovalLogService.add(memberInflow.getId(),
                memberInflow.getPartyId(), memberInflow.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW, (type==1)?"支部审核":"分党委审核", (byte)0);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberInflow:update")
    @RequestMapping(value = "/memberInflow_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_check(@CurrentUser SysUser loginUser,
                                      Integer id,
                                      byte type, // 1:支部审核 2：分党委审核
                                      HttpServletRequest request) {

        int loginUserId = loginUser.getId();
        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        int userId = memberInflow.getUserId();
        if(type==1) {
            //该支部管理员应是申请人所在党支部或直属党支部
            Integer branchId = memberInflow.getBranchId();
            Integer partyId = memberInflow.getPartyId();
            boolean branchAdmin = branchMemberService.isPresentAdmin(loginUserId, branchId);
            boolean partyAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            boolean directParty = partyService.isDirectBranch(partyId);
            if (!branchAdmin && (!directParty || !partyAdmin)) { // 不是党支部管理员， 也不是直属党支部管理员
                throw new UnauthorizedException();
            }

            if (directParty && partyAdmin) { // 直属党支部管理员，不需要通过党支部审核
                memberInflowService.addMember(memberInflow.getUserId(), true);
                logger.info(addLog(request, SystemConstants.LOG_OW, "通过流入党员申请：%s", id));
            } else {
                memberInflowService.checkMember(memberInflow.getUserId());
                logger.info(addLog(request, SystemConstants.LOG_OW, "审核流入党员申请：%s", id));
            }
        }

        if(type==2) {
            //操作人应是申请人所在分党委管理员
            Integer partyId = memberInflow.getPartyId();
            if (!partyMemberService.isPresentAdmin(loginUserId, partyId)) { // 分党委管理员
                throw new UnauthorizedException();
            }

            memberInflowService.addMember(memberInflow.getUserId(), false);
            logger.info(addLog(request, SystemConstants.LOG_OW, "通过流入党员申请：%s", id));
        }

        applyApprovalLogService.add(memberInflow.getId(),
                memberInflow.getPartyId(), memberInflow.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW, (type==1)?"支部审核":"分党委审核", (byte)1);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow_approvalLogs")
    public String memberInflow_approvalLogs(int id, ModelMap modelMap) {

        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        modelMap.put("memberInflow", memberInflow);
        SysUser sysUser = sysUserService.findById(memberInflow.getUserId());
        modelMap.put("sysUser", sysUser);

        return "party/memberInflow/memberInflow_approvalLogs";
    }

    @RequiresPermissions("memberInflow:edit")
    @RequestMapping("/memberInflow_au")
    public String memberInflow_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
            modelMap.put("memberInflow", memberInflow);

            modelMap.put("sysUser", sysUserService.findById(memberInflow.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberInflow.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberInflow.getPartyId()));
            }
            if (memberInflow.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberInflow.getBranchId()));
            }
        }
        return "party/memberInflow/memberInflow_au";
    }

    @RequiresPermissions("memberInflow:del")
    @RequestMapping(value = "/memberInflow_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberInflowService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除流入党员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberInflow:del")
    @RequestMapping(value = "/memberInflow_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberInflowService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_OW, "批量删除流入党员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberInflow_export(MemberInflowExample example, HttpServletResponse response) {

        List<MemberInflow> memberInflows = memberInflowMapper.selectByExample(example);
        int rownum = memberInflowMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户","分党委名称","党支部名称","原职业","流入前所在省份","是否持有《中国共产党流动党员活动证》","流入时间","流入原因","入党时间","组织关系所在地"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberInflow memberInflow = memberInflows.get(i);
            String[] values = {
                        memberInflow.getUserId()+"",
                                            memberInflow.getPartyName(),
                                            memberInflow.getBranchName(),
                                            memberInflow.getOriginalJob()+"",
                                            memberInflow.getProvince()+"",
                                            memberInflow.getHasPapers() +"",
                                            DateUtils.formatDate(memberInflow.getFlowTime(), DateUtils.YYYY_MM_DD),
                                            memberInflow.getReason(),
                                            DateUtils.formatDate(memberInflow.getGrowTime(), DateUtils.YYYY_MM_DD),
                                            memberInflow.getOrLocation()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "流入党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
