package controller.party;

import bean.UserBean;
import controller.BaseController;
import domain.member.Member;
import domain.member.MemberTransfer;
import domain.member.MemberTransferExample;
import domain.member.MemberTransferExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberTransferMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.helper.ExportHelper;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberTransferController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_view")
    public String memberTransfer_view(int userId, ModelMap modelMap) {

        UserBean userBean = userBeanService.get(userId);
        modelMap.put("userBean", userBean);

        /*Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();*/

        /*modelMap.put("fromParty", partyMap.get(userBean.getPartyId()));
        modelMap.put("fromBranch", branchMap.get(userBean.getBranchId()));*/

        MemberTransfer memberTransfer = memberTransferService.get(userId);
        modelMap.put("memberTransfer", memberTransfer);

        modelMap.put("locationMap", locationService.codeMap());

        /*if(memberTransfer!=null) {
            if (memberTransfer.getToPartyId() != null) {
                modelMap.put("toParty", partyMap.get(memberTransfer.getToPartyId()));
            }
            if (memberTransfer.getToBranchId() != null) {
                modelMap.put("toBranch", branchMap.get(memberTransfer.getToBranchId()));
            }
        }*/

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
                                  Integer branchId,
                                  Integer toPartyId,
                                  Integer toBranchId,
                                  ModelMap modelMap) {

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
        if (toPartyId != null) {
            modelMap.put("toParty", partyMap.get(toPartyId));
        }
        if (toBranchId != null) {
            modelMap.put("toBranch", branchMap.get(toBranchId));
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
                                 Byte status,
                                 Boolean isBack,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                    Integer toPartyId,
                                    Integer toBranchId,
                                    String _fromHandleTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
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

        if(status!=null){
            criteria.andStatusEqualTo(status);
        }
        if(isBack!=null){
            criteria.andIsBackEqualTo(isBack);
        }

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (toPartyId!=null) {
            criteria.andToPartyIdEqualTo(toPartyId);
        }
        if (toBranchId!=null) {
            criteria.andToBranchIdEqualTo(toBranchId);
        }
        if (StringUtils.isNotBlank(_fromHandleTime)) {
            String start = _fromHandleTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _fromHandleTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andFromHandleTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andFromHandleTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }

        if(cls==1){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
            statusList.add(SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
            criteria.andStatusIn(statusList);
        }else if(cls==2){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK);
            statusList.add(SystemConstants.MEMBER_TRANSFER_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
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

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_approval")
    public String memberTransfer_approval(@CurrentUser SysUserView loginUser, Integer id,
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

        return "party/memberTransfer/memberTransfer_approval";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberTransfer:update")
    @RequestMapping("/memberTransfer_deny")
    public String memberTransfer_deny(Integer id, ModelMap modelMap) {

        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        modelMap.put("memberTransfer", memberTransfer);
        Integer userId = memberTransfer.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberTransfer/memberTransfer_deny";
    }
    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                   byte type, // 1:转出分党委审核 2：转入分党委审核
                                   @RequestParam(value = "ids[]") Integer[] ids) {


        memberTransferService.memberTransfer_check(ids, type, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "暂留申请-审核：%s", StringUtils.join( ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberTransfer:update")
    @RequestMapping("/memberTransfer_back")
    public String memberTransfer_back() {

        return "party/memberTransfer/memberTransfer_back";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_back(@CurrentUser SysUserView loginUser,
                                  @RequestParam(value = "ids[]") Integer[] ids,
                                  byte status,
                                  String reason) {


        memberTransferService.memberTransfer_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "暂留申请：%s", StringUtils.join( ids, ",")));
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping(value = "/memberTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_au(@CurrentUser SysUserView loginUser,MemberTransfer record,
                                    String _payTime, String _fromHandleTime, HttpServletRequest request) {

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);
        if(member.getPartyId().byteValue() == record.getToPartyId()){
            return failed("转入不能是当前所在分党委");
        }
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        //===========权限
        Integer loginUserId = loginUser.getId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin && branchId!=null) {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }

            boolean isToAdmin = partyMemberService.isPresentAdmin(loginUserId, record.getToPartyId());
            if(!isToAdmin && record.getToBranchId()!=null) {
                isToAdmin = branchMemberService.isPresentAdmin(loginUserId, record.getToPartyId(), record.getToBranchId());
            }

            if(!isAdmin && !isToAdmin) throw new UnauthorizedException();
        }

        Integer id = record.getId();

        if (memberTransferService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }
        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }


        if (id == null) {
            record.setApplyTime(new Date());
            record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
            memberTransferService.insertSelective(record);

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER,
                    "后台添加",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交校内组织关系转接申请");


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

    /*@RequiresPermissions("memberTransfer:del")
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
    }*/
    public void memberTransfer_export(MemberTransferExample example, HttpServletResponse response) {

        List<MemberTransfer> records = memberTransferMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号","姓名","所在分党委","所在党支部", "转入分党委",
                "转入党支部", "介绍信有效期天数","转出办理时间","状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberTransfer record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Integer toPartyId = record.getToPartyId();
            Integer toBranchId = record.getToBranchId();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    toPartyId==null?"":partyService.findAll().get(toPartyId).getName(),
                    toBranchId==null?"":branchService.findAll().get(toBranchId).getName(),
                    record.getValidDays()+"",
                    DateUtils.formatDate(record.getFromHandleTime(), DateUtils.YYYY_MM_DD),
                    record.getStatus()==null?"":SystemConstants.MEMBER_TRANSFER_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = "校内组织关系转接_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
