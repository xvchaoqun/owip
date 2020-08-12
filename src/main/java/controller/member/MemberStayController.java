package controller.member;

import controller.global.OpException;
import domain.member.MemberStay;
import domain.member.MemberStayExample;
import domain.member.MemberStayView;
import domain.member.MemberStayViewExample;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.member.MemberStayExportService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberStayController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberStayExportService memberStayExportService;

    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay_view")
    public String memberStay_view(int userId, byte type, ModelMap modelMap) {

        modelMap.put("type", type);

        MemberStay memberStay = memberStayService.get(userId, type);
        if(memberStay!=null && memberStay.getType().intValue() == type)
            modelMap.put("memberStay", memberStay);

        /*if(memberStay!=null) {
            Integer partyId = memberStay.getPartyId();
            Integer branchId = memberStay.getBranchId();
            Integer toBranchId = memberStay.getToBranchId();
            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            if (partyId != null) {
                modelMap.put("party", partyMap.get(partyId));
            }
            if (branchId != null) {
                modelMap.put("branch", branchMap.get(branchId));
            }
            if (toBranchId != null) {
                modelMap.put("toBranch", branchMap.get(toBranchId));
            }
        }*/
        modelMap.put("student", studentInfoService.get(userId));
        modelMap.put("userBean", userBeanService.get(userId));

        return "member/memberStay/memberStay_view";
    }

    /*
    cls==1||cls==11||cls==12
        支部审核
    cls==2||cls==21||cls==22
        分党委审核
    cls==3||cls==31
        组织部审核
    cls==4
        未通过
    cls==5||cls==6
        已完成审批 未转出  已转出

   */
    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay")
    public String memberStay(@RequestParam(defaultValue = "1") Byte cls,
                             @RequestParam(defaultValue = MemberConstants.MEMBER_STAY_TYPE_ABROAD+"")Byte type,
                             @RequestParam(required = false, defaultValue = "0") int export,
                                      Integer userId,
                                      Integer partyId,
                                      Integer branchId, ModelMap modelMap, HttpServletResponse response) {

        if(export==2){

            MemberStayViewExample example = new MemberStayViewExample();
            example.createCriteria().andTypeEqualTo(type).andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY);

            String title = CmTag.getSysConfig().getSchoolName() + "出国（境）毕业生党员组织关系暂留汇总表";
            if(type == MemberConstants.MEMBER_STAY_TYPE_INTERNAL)
                title = CmTag.getSysConfig().getSchoolName() +"非出国（境）毕业生党员组织关系暂留汇总表";

            SXSSFWorkbook wb = memberStayExportService.toXlsx(type, example, title);
            ExportHelper.output(wb, title + ".xlsx", response);
            return null;
        }

        modelMap.put("cls", cls);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        if (cls == 1 || cls == 11) {
            // 支部待审核总数（新申请 cls=1）
            modelMap.put("approvalCountNew", memberStayService.count(null, null, (byte) 1, type, (byte) 1));
            // 支部待审核总数（返回修改 cls=4）
            modelMap.put("approvalCountBack", memberStayService.count(null, null, (byte) 1, type, (byte) 11));
            // 支部待审核总数
            modelMap.put("approvalCount", memberStayService.count(null, null, (byte) 1, type, cls));
        }
        if (cls == 2 || cls == 21) {
            // 分党委待审核总数（新申请 cls=1）
            modelMap.put("approvalCountNew", memberStayService.count(null, null, (byte) 2, type, (byte) 2));
            // 分党委待审核总数（返回修改 cls=4）
            modelMap.put("approvalCountBack", memberStayService.count(null, null, (byte) 2, type, (byte) 21));
            // 分党委待审核总数
            modelMap.put("approvalCount", memberStayService.count(null, null, (byte) 2, type, cls));
        }
        if (cls == 3 || cls == 31) {
            // 组织部待审核总数（新申请 cls=1）
            modelMap.put("approvalCountNew", memberStayService.count(null, null, (byte) 3, type, (byte) 3));
            // 组织部待审核总数（返回修改 cls=4）
            modelMap.put("approvalCountBack", memberStayService.count(null, null, (byte) 3, type, (byte) 31));

            modelMap.put("approvalCount", memberStayService.count(null, null, (byte) 3, type, cls));
        }
       /*
        // 支部待审核总数
        modelMap.put("branchApprovalCount", memberStayService.count(null, null, (byte)1));
        // 分党委党总支直属党支部待审核总数
        modelMap.put("partyApprovalCount", memberStayService.count(null, null, (byte)2));
        // 组织部待审核数目
        modelMap.put("odApprovalCount", memberStayService.count(null, null, (byte)3));*/

        return "member/memberStay/memberStay_page";
    }

    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay_data")
    public void memberStay_data(@RequestParam(defaultValue = "1") Byte cls,
                                @RequestParam(defaultValue = MemberConstants.MEMBER_STAY_TYPE_ABROAD+"")Byte type,
                                    HttpServletResponse response,
                                    @SortParam(required = false, defaultValue = "id", tableName = "ow_member_stay") String sort,
                                    @OrderParam(required = false, defaultValue = "desc") String order,
                                    Byte status,
                                    Boolean isBack,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                    String country,
                                    @RequestDateRange DateRange _abroadTime, // 留学时间
                                    @RequestDateRange DateRange  _saveTime, // 申请保留组织关系起止时间（年/月）
                                    @RequestDateRange DateRange  _payTime,
                                    String mobile,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer[] ids, // 导出的记录
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberStayViewExample example = new MemberStayViewExample();
        MemberStayViewExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (isBack != null) {
            criteria.andIsBackEqualTo(isBack);
        }
        if (StringUtils.isNotBlank(country)) {
            criteria.andCountryLike(SqlUtils.like(country));
        }
        if (_abroadTime.getStart()!=null) {
            criteria.andEndTimeGreaterThanOrEqualTo(_abroadTime.getStart());
        }

        if (_abroadTime.getEnd()!=null) {
            criteria.andStartTimeLessThanOrEqualTo(_abroadTime.getEnd());
        }
        if (_saveTime.getStart()!=null) {
            criteria.andSaveEndTimeGreaterThanOrEqualTo(_saveTime.getStart());
        }

        if (_saveTime.getEnd()!=null) {
            criteria.andSaveStartTimeLessThanOrEqualTo(_saveTime.getEnd());
        }
        if (_payTime.getStart()!=null) {
            criteria.andPayTimeGreaterThanOrEqualTo(_payTime.getStart());
        }

        if (_payTime.getEnd()!=null) {
            criteria.andPayTimeLessThanOrEqualTo(_payTime.getEnd());
        }

        if (StringUtils.isNotBlank(mobile)) {
            criteria.andMobileLike(SqlUtils.like(mobile));
        }
        if (cls == 1) {// 支部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_APPLY)
                    .andIsBackNotEqualTo(true);
        } else if (cls == 11) {// 支部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_APPLY)
                    .andIsBackEqualTo(true);
        } else if (cls == 12) {// 支部审核（已审核）
            criteria.andStatusGreaterThanOrEqualTo(MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY);
        } else if (cls == 2) { // 分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY)
                    .andIsBackNotEqualTo(true);
        } else if (cls == 21) { // 分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY)
                    .andIsBackEqualTo(true);
        } else if (cls == 22) {// 分党委审核（已审核）
            criteria.andStatusGreaterThanOrEqualTo(MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        } else if (cls == 3) {// 组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY)
                    .andIsBackNotEqualTo(true);
        } else if (cls == 31) {// 组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY)
                    .andIsBackEqualTo(true);
        } else if (cls == 4) {
            List<Byte> statusList = new ArrayList<>();
            statusList.add(MemberConstants.MEMBER_STAY_STATUS_SELF_BACK);
            statusList.add(MemberConstants.MEMBER_STAY_STATUS_BACK);
            criteria.andStatusIn(statusList);
        } else {
            criteria.andStatusIn(Arrays.asList(MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY,
                    MemberConstants.MEMBER_STAY_STATUS_ARCHIVE));
            if (cls == 5)
                criteria.andMemberStatusNotEqualTo(MemberConstants.MEMBER_STATUS_TRANSFER);
            if (cls == 6)
                criteria.andMemberStatusEqualTo(MemberConstants.MEMBER_STATUS_TRANSFER);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            String title = "出国（境）毕业生党员组织关系暂留表";
            if(type == MemberConstants.MEMBER_STAY_TYPE_INTERNAL)
                title = "非出国（境）毕业生党员组织关系暂留表";

            SXSSFWorkbook wb = memberStayExportService.toXlsx(type, example, title);
            ExportHelper.output(wb, title + ".xlsx", response);
            return;
        }

        long count = memberStayViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberStayView> memberStays = memberStayViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberStays);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay_approval")
    public String memberStay_approval(@RequestParam(defaultValue = "1") byte cls,
                                      @CurrentUser SysUserView loginUser, Integer id,
                                          byte checkType, // 1:支部审核 2:分党委审核 3：组织部审核
                                            byte type,
                                          ModelMap modelMap) {

        modelMap.put("type", type);

        MemberStay currentMemberStay = null;
        if (id != null) {
            currentMemberStay = memberStayMapper.selectByPrimaryKey(id);
            if (checkType == 1) {
                if (currentMemberStay.getStatus() != MemberConstants.MEMBER_STAY_STATUS_APPLY)
                    currentMemberStay = null;
            }
            if (checkType == 2) {
                if (currentMemberStay.getStatus() != MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY)
                    currentMemberStay = null;
            }
            if (checkType == 3) {
                if (currentMemberStay.getStatus() != MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY)
                    currentMemberStay = null;
            }
        } else {
            currentMemberStay = memberStayService.next(type, checkType, null, cls);
        }
        if (currentMemberStay == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberStay", currentMemberStay);

        Integer userId = currentMemberStay.getUserId();
        modelMap.put("userBean", userBeanService.get(userId));
        modelMap.put("student", studentInfoService.get(userId));

        Integer partyId = currentMemberStay.getPartyId();
        Integer branchId = currentMemberStay.getBranchId();
        Integer toBranchId = currentMemberStay.getToBranchId();
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }
        if (toBranchId != null) {
            modelMap.put("toBranch", branchMap.get(toBranchId));
        }

        // 是否是当前记录的管理员
        if (checkType == 1) {
            modelMap.put("isAdmin", branchMemberService.hasAdminAuth(loginUser.getId(), partyId, branchId));
        }
        if (checkType == 2) {
            modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), partyId));
        }
        if (checkType == 3) {
            modelMap.put("isAdmin", ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL));
        }


        // 读取总数
        modelMap.put("count", memberStayService.count(null, null, checkType, currentMemberStay.getType(), cls));
        // 下一条记录
        modelMap.put("next", memberStayService.next(currentMemberStay.getType(), checkType, currentMemberStay, cls));
        // 上一条记录
        modelMap.put("last", memberStayService.last(currentMemberStay.getType(), checkType, currentMemberStay, cls));

        return "member/memberStay/memberStay_approval";
    }

    @RequiresPermissions("memberStay:update")
    @RequestMapping("/memberStay_deny")
    public String memberStay_deny(Integer id, ModelMap modelMap) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        modelMap.put("memberStay", memberStay);
        Integer userId = memberStay.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberStay/memberStay_deny";
    }

    @RequiresPermissions("memberStay:update")
    @RequestMapping(value = "/memberStay_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                       byte type, // 1:支部审核 2:分党委审核 3：组织部审核
                                       Integer[] ids) {


        memberStayService.memberStay_check(ids, type, null, null, null, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "暂留申请-审核：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberStay:update")
    @RequestMapping("/memberStay_back")
    public String memberStay_back() {

        return "member/memberStay/memberStay_back";
    }

    @RequiresPermissions("memberStay:update")
    @RequestMapping(value = "/memberStay_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_back(@CurrentUser SysUserView loginUser,
                                      Integer[] ids,
                                      byte status,
                                      String reason) {


        memberStayService.memberStay_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "暂留申请：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberStay:update")
    @RequestMapping("/memberStay_transfer")
    public String memberStay_transfer(Integer[] ids, ModelMap modelMap) {

        int id = ids[0]; /// 分党委审核时必须在同一个分党委内部审核
        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        modelMap.put("memberStay", memberStay);

        Integer partyId = memberStay.getPartyId();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }

        return "member/memberStay/memberStay_transfer";
    }

    @RequiresPermissions("memberStay:update")
    @RequestMapping(value = "/memberStay_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_transfer(@CurrentUser SysUserView loginUser,
                                          Integer[] ids,
                                          Integer branchId, Integer orgBranchAdminId, String orgBranchAdminPhone) {


        memberStayService.memberStay_check(ids, (byte) 2, branchId, orgBranchAdminId, orgBranchAdminPhone, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "分党委审核暂留申请：%s，转移至支部%s", StringUtils.join(ids, ","), branchId));
        return success(FormUtils.SUCCESS);
    }

    // 管理员添加
    @RequiresPermissions("memberStay:edit")
    @RequestMapping("/memberStay_au")
    public String memberStay_au(byte type, ModelMap modelMap) {

        modelMap.put("type", type);
        modelMap.put("countryList", countryService.getCountryList());

        return "member/memberStay/memberStay_au";
    }

    @RequiresPermissions("memberStay:edit")
    @RequestMapping(value = "/memberStay_transfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map memberStay_transfer_au(@CurrentUser SysUserView loginUser,
                                          int id, int branchId, int orgBranchAdminId, String orgBranchAdminPhone, HttpServletRequest request) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        Map<Integer, Branch> branchMap = branchService.findAll();
        Branch branch = branchMap.get(branchId);
        if (branch == null || branch.getPartyId().intValue() != memberStay.getPartyId()) {
            return failed("转移支部不存在");
        }

        MemberStay record = new MemberStay();
        record.setId(id);
        record.setToBranchId(branchId);
        record.setOrgBranchAdminId(orgBranchAdminId);
        record.setOrgBranchAdminPhone(orgBranchAdminPhone);
        memberStayService.trasferAu(record);
        logger.info(addLog(LogConstants.LOG_MEMBER, "更新暂留党支部等信息：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    // 修改暂留支部
    @RequiresPermissions("memberStay:edit")
    @RequestMapping("/memberStay_transfer_au")
    public String memberStay_transfer_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
            modelMap.put("memberStay", memberStay);

            if (partyService.isDirectBranch(memberStay.getPartyId())) {
                throw new OpException("直属党支部不需要添加暂留党支部");
            }

            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("party", partyMap.get(memberStay.getPartyId()));

            // 暂留党支部
            if (memberStay.getToBranchId() != null) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                Branch branch = branchMap.get(memberStay.getToBranchId());
                modelMap.put("branch", branch);
            }

            // 原支部负责人
            if (memberStay.getOrgBranchAdminId() != null) {
                SysUserView sysUser = sysUserService.findById(memberStay.getOrgBranchAdminId());
                modelMap.put("sysUser", sysUser);
            }

        }
        return "member/memberStay/memberStay_transfer_au";
    }

  /*  @RequiresPermissions("memberStay:del")
    @RequestMapping(value = "/memberStay_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberStayService.del(id);
            logger.info(addLog(LogConstants.LOG_MEMBER, "删除暂留：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions({"memberStay:del", SystemConstants.PERMISSION_PARTYVIEWALL})
    @RequestMapping(value = "/memberStay_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {

            MemberStayExample example = new MemberStayExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<MemberStay> memberStays = memberStayMapper.selectByExample(example);

            memberStayService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量删除暂留：%s", JSONUtils.toString(memberStays,false)));
        }
        return success(FormUtils.SUCCESS);
    }

    /*public void memberStay_export(byte type, MemberStayViewExample example, HttpServletResponse response) {

        List<MemberStayView> records = memberStayViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号", "姓名", "所在分党委", "所在党支部",
                "留学国家", "留学学校（院系）",
                "留学起止时间（年/月）", "留学起止时间（年/月）", "手机号码", "状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberStayView record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    record.getCountry(), record.getSchool(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD),
                    record.getMobile(),
                    record.getStatus() == null ? "" : MemberConstants.MEMBER_STAY_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = String.format("党员申请组织关系暂留(%s)_", MemberConstants.MEMBER_STAY_TYPE_MAP.get(type))
                + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }*/

}
