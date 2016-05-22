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
public class MemberInflowOutController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberInflowOut:list")
    @RequestMapping("/memberInflowOut")
    public String memberInflow() {

        return "index";
    }

    @RequiresPermissions("memberInflowOut:list")
    @RequestMapping("/memberInflowOut_page")
    public String memberInflowOut_page(@RequestParam(defaultValue = "1") byte cls, Integer userId,
                                    Integer partyId,
                                    Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (userId != null) {
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

        // 支部待审核总数
        modelMap.put("branchApprovalCount", memberInflowOutService.count(null, null, (byte) 1, cls));
        // 分党委待审核数目
        modelMap.put("partyApprovalCount", memberInflowOutService.count(null, null, (byte) 2, cls));

        return "party/memberInflowOut/memberInflowOut_page";
    }

    @RequiresPermissions("memberInflowOut:list")
    @RequestMapping("/memberInflowOut_data")
    public void memberInflowOut_data(@RequestParam(defaultValue = "1") byte cls, HttpServletResponse response,
                                  @SortParam(required = false, defaultValue = "id", tableName = "ow_member_inflow") String sort,
                                  @OrderParam(required = false, defaultValue = "desc") String order,
                                  Integer userId,
                                  Byte status,
                                  Boolean isBack,
                                  Byte type,
                                  Integer partyId,
                                  Integer branchId,
                                  String outUnit,
                                  Integer outLocation,
                                  String _outTime,
                                  Integer originalJob,
                                  Integer province,
                                  String flowReason,
                                  Boolean hasPapers,
                                  String orLocation,
                                  String _flowTime,
                                  String _growTime,
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

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(status!=null){
            criteria.andOutStatusEqualTo(status);
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        if(isBack!=null){
            criteria.andIsBackEqualTo(isBack);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (StringUtils.isNotBlank(outUnit)) {
            criteria.andOutUnitLike("%" + outUnit + "%");
        }
        if (outLocation != null) {
            criteria.andOutLocationEqualTo(outLocation);
        }
        if (StringUtils.isNotBlank(_outTime)) {
            String start = _outTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _outTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andOutTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andOutTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }
        if(originalJob!=null){
            criteria.andOriginalJobEqualTo(originalJob);
        }
        if(province!=null){
            criteria.andProvinceEqualTo(province);
        }
        if (StringUtils.isNotBlank(flowReason)) {
            criteria.andFlowReasonLike("%" + flowReason + "%");
        }
        if(hasPapers!=null){
            criteria.andHasPapersEqualTo(hasPapers);
        }
        if (StringUtils.isNotBlank(orLocation)) {
            criteria.andOrLocationLike("%" + orLocation + "%");
        }
        if (StringUtils.isNotBlank(_flowTime)) {
            String start = _flowTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _flowTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andFlowTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andFlowTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }
        if (StringUtils.isNotBlank(_growTime)) {
            String start = _growTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _growTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andGrowTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }


        if(cls==1){ // 支部审核（新申请）
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY)
                    .andOutIsBackNotEqualTo(true);
        }else if(cls==4){ // 支部审核(返回修改)
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY)
                    .andOutIsBackEqualTo(true);;
        }else if(cls==5 || cls==6){ // 支部已审核
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY);
        }else if(cls==2) {// 未通过
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_INFLOW_OUT_STATUS_SELF_BACK);
            statusList.add(SystemConstants.MEMBER_INFLOW_OUT_STATUS_BACK);
            criteria.andOutStatusIn(statusList);
        }else {// 已审核
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY);
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

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberInflowOut:list")
    @RequestMapping("/memberInflowOut_approval")
    public String memberInflowOut_approval(@RequestParam(defaultValue = "1")byte cls,@CurrentUser SysUser loginUser, Integer id,
                                        byte type, // 1:支部审核 2：分党委审核
                                        ModelMap modelMap) {

        MemberInflow currentMemberInflow = null;
        if (id != null) {
            currentMemberInflow = memberInflowMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberInflow.getOutStatus() != SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY)
                    currentMemberInflow = null;
            }
            if (type == 2) {
                if (currentMemberInflow.getOutStatus() != SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY)
                    currentMemberInflow = null;
            }
        } else {
            currentMemberInflow = memberInflowOutService.next(null, type, cls);
        }
        if (currentMemberInflow == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberInflow", currentMemberInflow);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), currentMemberInflow.getBranchId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberInflow.getPartyId()));
        }

        // 读取总数
        modelMap.put("count", memberInflowOutService.count(null, null, type, cls));
        // 下一条记录
        modelMap.put("next", memberInflowOutService.next(currentMemberInflow, type, cls));
        // 上一条记录
        modelMap.put("last", memberInflowOutService.last(currentMemberInflow, type, cls));

        return "party/memberInflowOut/memberInflowOut_approval";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberInflowOut:update")
    @RequestMapping("/memberInflowOut_deny")
    public String memberInflowOut_deny(Integer id, ModelMap modelMap) {

        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        modelMap.put("memberInflow", memberInflow);
        Integer userId = memberInflow.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberInflowOut/memberInflowOut_deny";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberInflowOut:update")
    @RequestMapping(value = "/memberInflowOut_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflowOut_check(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                      byte type, // 1:支部审核 2：分党委审核
                                      @RequestParam(value = "ids[]") int[] ids) {


        memberInflowOutService.memberInflowOut_check(ids, type, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "流入党员申请-审核：%s", StringUtils.join( ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberInflowOut:update")
    @RequestMapping("/memberInflowOut_back")
    public String memberInflowOut_back() {

        return "party/memberInflowOut/memberInflowOut_back";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberInflowOut:update")
    @RequestMapping(value = "/memberInflowOut_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflowOut_back(@CurrentUser SysUser loginUser,
                                     @RequestParam(value = "ids[]") int[] ids,
                                     byte status,
                                     String reason) {


        memberInflowOutService.memberInflowOut_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "分党委打回流入党员申请：%s", StringUtils.join( ids, ",")));
        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("memberInflowOut:edit")
    @RequestMapping("/memberInflowOut_au")
    public String memberInflowOut_au(Integer id, ModelMap modelMap) {

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
        return "party/memberInflowOut/memberInflowOut_au";
    }

    @RequestMapping(value = "/memberInflowOut_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflowOut(@CurrentUser SysUser loginUser, int userId, String outUnit, Integer outLocation,
                                  String _outTime, HttpServletRequest request) {

        MemberInflow memberInflow = memberInflowOutService.out(userId, outUnit, outLocation, _outTime, false);

        applyApprovalLogService.add(memberInflow.getId(),
                memberInflow.getPartyId(), memberInflow.getBranchId(), userId,
                loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT,
                "提交",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "后台提交流入党员转出申请");

        logger.info(addLog(SystemConstants.LOG_OW, "后台提交流入党员转出申请"));

        return success(FormUtils.SUCCESS);
    }
}
