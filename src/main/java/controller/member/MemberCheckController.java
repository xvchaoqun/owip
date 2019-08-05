package controller.member;

import domain.member.Member;
import domain.member.MemberCheck;
import domain.member.MemberCheckExample;
import domain.member.MemberCheckExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
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
import org.springframework.web.multipart.MultipartFile;
import service.sys.AvatarService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.gson.GsonUtils;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberCheckController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected AvatarService avatarService;

    @RequiresPermissions("memberCheck:list")
    @RequestMapping("/memberCheck")
    public String memberCheck(@RequestParam(defaultValue = "1") byte cls,
                              Integer userId,
                              Integer partyId,
                              Integer branchId,
                              ModelMap modelMap) {

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

        if (cls != 1) {

            modelMap.put("applyCount", memberCheckService.count(MemberConstants.MEMBER_CHECK_STATUS_APPLY));
            modelMap.put("passCount", memberCheckService.count(MemberConstants.MEMBER_CHECK_STATUS_PASS));
            modelMap.put("backCount", memberCheckService.count(MemberConstants.MEMBER_CHECK_STATUS_BACK));
        }

        return "member/memberCheck/memberCheck_page";
    }

    @RequiresPermissions("memberCheck:list")
    @RequestMapping("/memberCheck_data")
    @ResponseBody
    public void memberCheck_data(HttpServletResponse response,
                                 Integer userId,
                                 Integer partyId,
                                 Integer branchId,
                                 // 1：本人申请列表  2: 待审批 3：审批通过 4：返回修改  5：全部申请
                                 @RequestParam(defaultValue = "1") byte cls,
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

        MemberCheckExample example = new MemberCheckExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (cls == 1) {
            criteria.andUserIdEqualTo(ShiroHelper.getCurrentUserId());
        } else {

            criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

            if (cls == 2) {
                criteria.andStatusEqualTo(MemberConstants.MEMBER_CHECK_STATUS_APPLY);
            } else if (cls == 3) {
                criteria.andStatusEqualTo(MemberConstants.MEMBER_CHECK_STATUS_PASS);
            } else if (cls == 4) {
                criteria.andStatusEqualTo(MemberConstants.MEMBER_CHECK_STATUS_BACK);
            }
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            memberCheck_export(example, response);
            return;
        }

        long count = memberCheckMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberCheck> records = memberCheckMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(memberCheck.class, memberCheckMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    //@RequiresPermissions("memberCheck:edit")
    @RequestMapping(value = "/memberCheck_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberCheck_au(MemberCheck record, MultipartFile _avatar, HttpServletRequest request) throws IOException {

        Integer id = record.getId();
        int userId = record.getUserId();
        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();

        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (userId != loginUserId
                && !ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin && branchId != null) {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }
            if (!isAdmin) throw new UnauthorizedException();
        }

        record.setStatus(MemberConstants.MEMBER_CHECK_STATUS_APPLY);
        record.setCreateTime(new Date());
        record.setIp(ContextHelper.getRealIp());

        MemberCheck memberCheck = memberCheckService.getNotApply(userId);
        record.setOriginalJson(JSONUtils.toString(memberCheck, false));

        String avatar = avatarService.uploadAvatar(_avatar);
        record.setAvatar(avatar);
        if(record.getAvatar()==null){
            record.setAvatar(memberCheck.getAvatar());
        }

        if (id == null) {

            memberCheckService.insertSelective(record);
            logger.info(log(LogConstants.LOG_MEMBER, "添加党员信息修改申请：{0}", record.getId()));
        } else {
            // 只能修改还未审核的记录
            MemberCheck before = memberCheckMapper.selectByPrimaryKey(record.getId());
            if (before.getStatus() == MemberConstants.MEMBER_CHECK_STATUS_PASS) {
                return failed("该申请已经审核，不可以进行修改。");
            }
            memberCheckService.updateByPrimaryKey(record); // 覆盖更新
            logger.info(log(LogConstants.LOG_MEMBER, "更新党员信息修改申请：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("memberCheck:edit")
    @RequestMapping("/memberCheck_au")
    public String memberCheck_au(ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        SysUserView uv = sysUserService.findById(userId);
        modelMap.put("sysUser", uv);
        MemberCheck memberCheck = memberCheckService.getApply(userId);
        if (memberCheck == null) {
            memberCheck = memberCheckService.getNotApply(userId);
        }

        Integer partyId = memberCheck.getPartyId();
        Integer branchId = memberCheck.getBranchId();
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        modelMap.put("memberCheck", memberCheck);

        return "member/memberCheck/memberCheck_au";
    }

    //@RequiresPermissions("memberCheck:approval")
    @RequestMapping("/memberCheck_approval")
    public String memberCheck_approval(Integer id, ModelMap modelMap) {

        MemberCheck memberCheck = memberCheckMapper.selectByPrimaryKey(id);
        modelMap.put("memberCheck", memberCheck);

        if (!ShiroHelper.isPermitted("memberCheck:approval")) {
            if (memberCheck.getUserId() != ShiroHelper.getCurrentUserId().intValue()) {
                throw new UnauthorizedException();
            }
        }

        MemberCheck original = GsonUtils.ToBean(memberCheck.getOriginalJson(), MemberCheck.class);
        modelMap.put("original", original);

        int userId = memberCheck.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        modelMap.put("sysUser", uv);

        Member member = memberMapper.selectByPrimaryKey(userId);
        modelMap.put("member", member);

        return "member/memberCheck/memberCheck_approval";
    }

    @RequiresPermissions("memberCheck:approval")
    @RequestMapping(value = "/memberCheck_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberCheck_approval(@CurrentUser SysUserView loginUser,
                                       Integer id,
                                       boolean pass,
                                       String reason) {

        memberCheckService.approval(id, pass, reason);

        logger.info(addLog(LogConstants.LOG_MEMBER, "审批党员信息修改申请：%s", id));
        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("memberCheck:del")
    @RequestMapping(value = "/memberCheck_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map memberCheck_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            memberCheckService.batchDel(ids);
            logger.info(log(LogConstants.LOG_MEMBER, "撤回党员信息修改申请：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberCheck_export(MemberCheckExample example, HttpServletResponse response) {

        List<MemberCheck> records = memberCheckMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"用户|100", "当前所属分党委|100", "当前所属党支部|100", "返回修改原因|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberCheck record = records.get(i);
            String[] values = {
                    record.getUserId() + "",
                    record.getPartyId() + "",
                    record.getBranchId() + "",
                    record.getReason()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党员信息修改申请(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
