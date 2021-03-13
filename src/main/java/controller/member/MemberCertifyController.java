package controller.member;

import domain.member.Member;
import domain.member.MemberCertify;
import domain.member.MemberCertifyExample;
import domain.member.MemberCertifyExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberCertifyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/memberCertify")
    public String memberCertify(@RequestParam(defaultValue = "1") byte cls,
                                Integer userId,
                                ModelMap modelMap) {

        //权限
        ShiroHelper.checkPermission(cls == 0 ? "userMemberCertify:list" : "memberCertify:list");

        modelMap.put("cls", cls);

        if (cls == 0){//党员信息界面介绍信
            return "member/user/memberCertify/memberCertify";
        }

        if (userId != null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }
        if (cls == 1 || cls == 2){
            modelMap.put("approvalCountNew", memberCertifyService.count((byte)1, (byte)1));
            modelMap.put("approvalCountBack", memberCertifyService.count((byte)1, (byte)2));
        }else if (cls == 4 || cls == 5){
            modelMap.put("approvalCountNew", memberCertifyService.count((byte)2, (byte)4));
            modelMap.put("approvalCountBack", memberCertifyService.count((byte)2, (byte)5));
        }

        return "member/memberCertify/memberCertify_page";
    }

    @RequestMapping("/memberCertify_data")
    @ResponseBody
    public void memberCertify_data(HttpServletResponse response,
                                   Integer userId,
                                   Integer sn,
                                   Integer year,
                                   Byte politicalStatus,
                                   String fromUnit,
                                   String toTitle,
                                   String toUnit,
                                   @RequestParam(required = false, defaultValue = "1") byte cls,//cls=0党员信息界面
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer[] ids, // 导出的记录
                                   Integer pageSize, Integer pageNo)  throws IOException{

        //权限
        ShiroHelper.checkPermission(cls == 0 ? "userMemberCertify:list" : "memberCertify:list");

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberCertifyExample example = new MemberCertifyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("certify_date desc,sn desc");

        if (cls != 0) {
            criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        }else {
            criteria.andUserIdEqualTo(ShiroHelper.getCurrentUserId());
        }

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (sn!=null) {
            criteria.andSnEqualTo(sn);
        }
        if (politicalStatus!=null) {
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (year != null){
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(fromUnit)){
            criteria.andFromUnitLike(SqlUtils.trimLike(fromUnit));
        }
        if (StringUtils.isNotBlank(toUnit)){
            criteria.andToUnitLike(SqlUtils.trimLike(toUnit));
        }
        if (StringUtils.isNotBlank(toTitle)) {
            criteria.andToTitleLike(SqlUtils.trimLike(toTitle));
        }
        if (cls == 1){
            criteria.andStatusEqualTo(MemberConstants.MEMBER_CERTIFY_STATUS_APPLY)
                    .andIsBackNotEqualTo(true);
        }else if (cls == 2){
            criteria.andStatusEqualTo(MemberConstants.MEMBER_CERTIFY_STATUS_APPLY)
                    .andIsBackEqualTo(true);
        }else if (cls == 3){
            criteria.andStatusGreaterThanOrEqualTo(MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY);
        }else if (cls == 4){
            criteria.andStatusEqualTo(MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY)
                    .andIsBackNotEqualTo(true);
        }else if (cls == 6){
            List<Byte> lists = new ArrayList<>();
            criteria.andStatusEqualTo(MemberConstants.MEMBER_CERTIFY_STATUS_BACK);
        }else if (cls == 7){
            criteria.andStatusEqualTo(MemberConstants.MEMBER_CERTIFY_STATUS_OW_VERIFY);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            memberCertify_export(example, response);
            return;
        }

        long count = memberCertifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberCertify> records= memberCertifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(memberCertify.class, memberCertifyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequestMapping(value = "/memberCertify_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberCertify_au(MemberCertify record, Boolean reapply, Boolean apply, HttpServletRequest request) {

        Integer id = record.getId();
        Member member = null;
        if (BooleanUtils.isTrue(apply)){
            ShiroHelper.checkPermission("userMemberCertify:list");
            // 个人只能提交已撤销和未提交的申请
            if (record.getId() != null) {
                MemberCertify before = memberCertifyMapper.selectByPrimaryKey(record.getId());
                if (before.getStatus() > MemberConstants.MEMBER_CERTIFY_STATUS_APPLY) {
                    return failed("该申请已进入审核流程，无法修改。");
                }
            }
            member = memberMapper.selectByPrimaryKey(ShiroHelper.getCurrentUserId());
            record.setUserId(ShiroHelper.getCurrentUserId());
            record.setStatus(MemberConstants.MEMBER_CERTIFY_STATUS_APPLY);
        }else {
            if (BooleanUtils.isTrue(reapply)){
                record.setStatus(MemberConstants.MEMBER_CERTIFY_STATUS_APPLY);
            }else {
                ShiroHelper.checkPermission("memberCertify:list");
                member = memberMapper.selectByPrimaryKey(record.getUserId());
                //===========权限
                Integer loginUserId = ShiroHelper.getCurrentUserId();
                if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {

                    if (!PartyHelper.hasBranchAuth(loginUserId, member.getPartyId(), member.getBranchId()))
                        throw new UnauthorizedException();

                    if (record.getId() != null) {
                        // 分党委只能修改还未提交组织部审核的记录
                        MemberCertify before = memberCertifyMapper.selectByPrimaryKey(record.getId());
                        if (before.getStatus() == MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY) {
                            return failed("该申请已经提交组织部审核，不可修改。");
                        }
                    }
                    record.setStatus(MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY);
                } else {
                    record.setStatus(MemberConstants.MEMBER_CERTIFY_STATUS_OW_VERIFY);
                }
            }
        }

        if (id == null) {
            record.setPartyId(member.getPartyId());
            record.setBranchId(member.getBranchId());
            record.setPoliticalStatus(member.getPoliticalStatus());
            
            memberCertifyService.insertSelective(record, apply);
            logger.info(log( LogConstants.LOG_MEMBER, "添加临时组织关系介绍信：账号{0}", record.getUserId()));
        } else {

            memberCertifyService.updateByPrimaryKeySelective(record, reapply, apply);
            logger.info(log( LogConstants.LOG_MEMBER, "更新临时组织关系介绍信：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/memberCertify_au")
    public String memberCertify_au(Integer id, Boolean reapply, Boolean apply, ModelMap modelMap) {

        //权限
        if (BooleanUtils.isTrue(reapply)){
            modelMap.put("reapply", reapply);
        }
        if (BooleanUtils.isTrue(apply)) {
            ShiroHelper.checkPermission("userMemberCertify:list");
            modelMap.put("apply", apply);
        }else {
            ShiroHelper.checkPermission("memberCertify:list");
        }

        if (id != null) {
            MemberCertify memberCertify = memberCertifyMapper.selectByPrimaryKey(id);
            modelMap.put("sysUser", memberCertify.getUser());
            modelMap.put("memberCertify", memberCertify);
        }
        return "member/memberCertify/memberCertify_au";
    }

    @RequiresPermissions("memberCertify:check")
    @RequestMapping("/memberCertify_check")
    public String memberCertify_check(Integer[] ids,
                                      byte type, // 1:分党委审核 2：组织部审核
                                      ModelMap modelMap) {

        modelMap.put("type", type);

        if (null != ids && ids.length == 1)
            modelMap.put("memberCertify", memberCertifyMapper.selectByPrimaryKey(ids[0]));

        return "member/memberCertify/memberCertify_check";
    }

    @RequiresPermissions("memberCertify:check")
    @RequestMapping(value = "/memberCertify_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_check(HttpServletRequest request, Integer[] ids,
                                     byte type,//1分党委，2组织部
                                     Boolean pass, String reason) {

        if (null != ids && ids.length > 0) {

            memberCertifyService.batchCheck(ids, type, pass, reason);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量审核临时组织关系介绍信：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberCertify:edit")
    @RequestMapping("/memberCertify_back")
    public String memberCertify_back(Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0){
            MemberCertify memberCertify = memberCertifyMapper.selectByPrimaryKey(ids[0]);
            modelMap.put("status", memberCertify.getStatus());
        }

        return "member/memberCertify/memberCertify_back";
    }

    @RequiresPermissions("memberCertify:edit")
    @RequestMapping(value = "/memberCertify_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberCertify_back(HttpServletRequest request, Integer[] ids,
                                     byte status,
                                     String reason, @CurrentUser SysUserView loginUser) {

        if (null != ids && ids.length > 0) {

            memberCertifyService.batchBack(ids, status, reason, loginUser);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量审核临时组织关系介绍信：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/memberCertify_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map memberCertify_batchDel(HttpServletRequest request, Boolean apply, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            memberCertifyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_MEMBER, "批量删除临时组织关系介绍信：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberCertify_export(MemberCertifyExample example, HttpServletResponse response) {

        List<MemberCertify> records = memberCertifyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号|100","姓名|100","年份|70","证明信编号|100","所在党组织|300","政治面貌|100","转出单位|200","转入单位抬头|200",
                "转入单位|200","证明信日期|100","申请日期|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberCertify record = records.get(i);
            String[] values = {
                    record.getUser().getCode(),
                    record.getUser().getRealname(),
                    record.getYear() + "",
                    record.getSn() + "",
                    CmTag.getParty(record.getPartyId()).getName() + "-" + CmTag.getBranch(record.getBranchId()).getName(),
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),
                    record.getFromUnit(),
                    record.getToTitle(),
                    record.getToUnit(),
                    DateUtils.formatDate(record.getCertifyDate(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getApplyTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = String.format("临时组织关系介绍信_(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
