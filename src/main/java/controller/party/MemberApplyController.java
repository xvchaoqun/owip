package controller.party;

import bean.MemberApplyCount;
import controller.BaseController;
import domain.member.MemberApply;
import domain.member.MemberApplyExample;
import domain.member.MemberApplyView;
import domain.member.MemberApplyViewExample;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.party.MemberApplyOpService;
import shiro.CurrentUser;
import shiro.ShiroHelper;
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
public class MemberApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MemberApplyOpService memberApplyOpService;

    private VerifyAuth<MemberApply> checkVerityAuth(int userId){
        MemberApply memberApply = memberApplyService.get(userId);
        return super.checkVerityAuth(memberApply, memberApply.getPartyId(), memberApply.getBranchId());
    }

    private VerifyAuth<MemberApply> checkVerityAuth2(int userId){
        MemberApply memberApply = memberApplyService.get(userId);
        return super.checkVerityAuth2(memberApply, memberApply.getPartyId());
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply_approval")
    public String memberApply_approval(@CurrentUser SysUserView loginUser,Integer userId,
                                       byte type,
                                       byte stage,
                                       Byte status, // status=-1 代表对应的状态值为NULL
                                       ModelMap modelMap) {

        MemberApply currentMemberApply = null;
        if(userId!=null) {
            //SysUser sysUser = sysUserService.findById(userId);
            //modelMap.put("user", sysUser);
            currentMemberApply = memberApplyService.get(userId);
        }else{
            currentMemberApply = memberApplyService.next(type, stage, status, null);
        }
        modelMap.put("memberApply", currentMemberApply);

        Integer branchId = currentMemberApply.getBranchId();
        Integer partyId = currentMemberApply.getPartyId();
        // 是否是当前记录的管理员
        switch (stage){
            case SystemConstants.APPLY_STAGE_INIT:
            case SystemConstants.APPLY_STAGE_PASS:
                modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
                break;
            case SystemConstants.APPLY_STAGE_ACTIVE:
            case SystemConstants.APPLY_STAGE_CANDIDATE:
                if(status==-1)
                    modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
                else
                    modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
                break;
            case SystemConstants.APPLY_STAGE_PLAN:
                modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
                break;
            case SystemConstants.APPLY_STAGE_DRAW:
                if(status==-1)
                    modelMap.put("isAdmin", ShiroHelper.hasRole(SystemConstants.ROLE_ODADMIN));
                else if(status==2) // 组织部审核之后，党支部才提交
                    modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
                else if(status==0) // 党支部提交后，分党委审核
                    modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
                break;
            case SystemConstants.APPLY_STAGE_GROW:
                if(status==-1)
                    modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
                else if(status==0)
                    modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
                else
                    modelMap.put("isAdmin", ShiroHelper.hasRole(SystemConstants.ROLE_ODADMIN));
                break;
        }

        // 读取总数
        modelMap.put("count", memberApplyService.count(null, null, type, stage, status));
        // 下一条记录
        modelMap.put("next", memberApplyService.next(type, stage, status, currentMemberApply));
        // 上一条记录
        modelMap.put("last", memberApplyService.last(type, stage, status, currentMemberApply));

        return "party/memberApply/memberApply_approval";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply")
    public String memberApply(@RequestParam(defaultValue = "1")int cls,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                   @RequestParam(defaultValue = SystemConstants.APPLY_TYPE_STU+"")Byte type,
                                   @RequestParam(defaultValue = "0")Byte stage,
                                   ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);
        modelMap.put("stage", stage);
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

        switch (stage){
            case SystemConstants.APPLY_STAGE_INIT:
            case SystemConstants.APPLY_STAGE_PASS:
                modelMap.put("applyCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_INIT, null));
                modelMap.put("activeCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_PASS, null));
                break;
            case SystemConstants.APPLY_STAGE_ACTIVE:
                modelMap.put("candidateCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_ACTIVE, (byte)-1));
                modelMap.put("candidateCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_ACTIVE, (byte)0));
                break;
            case SystemConstants.APPLY_STAGE_CANDIDATE:
                modelMap.put("planCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_CANDIDATE, (byte)-1));
                modelMap.put("planCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_CANDIDATE, (byte) 0));
                break;
            case SystemConstants.APPLY_STAGE_PLAN:
                modelMap.put("drawCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_PLAN, (byte)-1));
                //modelMap.put("drawCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_PLAN, (byte) 0));
                break;
            case SystemConstants.APPLY_STAGE_DRAW:
                // 组织部先审核 - 支部提交发展时间 - 分党委审核
                modelMap.put("growOdCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_DRAW, (byte) -1));
                modelMap.put("growCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_DRAW, (byte)2));
                modelMap.put("growCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_DRAW, (byte) 0));
                break;
            case SystemConstants.APPLY_STAGE_GROW:
                modelMap.put("positiveCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_GROW, (byte)-1));
                modelMap.put("positiveCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_GROW, (byte) 0));
                modelMap.put("positiveOdCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_GROW, (byte) 1));
                break;
        }

        Map<Byte, Integer> stageCountMap = new HashMap<>();
        Map<String, Integer> stageTypeCountMap = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        boolean addPermits = !(subject.hasRole(SystemConstants.ROLE_ADMIN)
                || subject.hasRole(SystemConstants.ROLE_ODADMIN));
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();
        List<MemberApplyCount> memberApplyCounts = commonMapper.selectMemberApplyCount(addPermits, adminPartyIdList, adminBranchIdList);
        for (MemberApplyCount memberApplyCount : memberApplyCounts) {

            byte _stage = memberApplyCount.getStage();
            byte _type = memberApplyCount.getType();
            Integer _count = memberApplyCount.getNum();
            Integer stageCount = stageCountMap.get(_stage);
            if(stageCount==null) stageCount = 0;
            stageCountMap.put(_stage, stageCount+_count);

            Integer stageTypeCount = stageTypeCountMap.get(_stage + "_" + _type);
            if(stageTypeCount==null) stageTypeCount = 0;
            stageTypeCountMap.put(_stage + "_" + _type, stageTypeCount+_count);
        }
        modelMap.put("stageCountMap",stageCountMap);
        modelMap.put("stageTypeCountMap",stageTypeCountMap);

        return "party/memberApply/memberApply_page";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply_data")
    public void memberApply_data(HttpServletResponse response,
                                   @SortParam(required = false, defaultValue = "create_time", tableName = "ow_member_apply") String sort,
                                   @OrderParam(required = false, defaultValue = "desc") String order,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                 @RequestParam(defaultValue = SystemConstants.APPLY_TYPE_STU+"")Byte type,
                                   @RequestParam(defaultValue = "0")Byte stage,
                                   Byte growStatus, // 领取志愿书阶段查询
                                   Byte positiveStatus, // 预备党员阶段查询
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if(type !=null) {
            criteria.andTypeEqualTo(type);
        }
        if (stage != null) {
            if(stage==SystemConstants.APPLY_STAGE_INIT || stage==SystemConstants.APPLY_STAGE_PASS) {
                List<Byte> stageList = new ArrayList<>();
                stageList.add(SystemConstants.APPLY_STAGE_INIT);
                stageList.add(SystemConstants.APPLY_STAGE_PASS);
                criteria.andStageIn(stageList);
            }else if(stage>SystemConstants.APPLY_STAGE_PASS || stage == SystemConstants.APPLY_STAGE_DENY) {
                criteria.andStageEqualTo(stage);
            }else if(stage == SystemConstants.APPLY_STAGE_DRAW){
                if(growStatus!=null && growStatus>=0)
                    criteria.andGrowStatusEqualTo(growStatus);
                if(growStatus!=null && growStatus==-1)
                    criteria.andGrowStatusIsNull();
            }else if(stage == SystemConstants.APPLY_STAGE_GROW){
                if(positiveStatus!=null && positiveStatus>=0)
                    criteria.andPositiveStatusEqualTo(positiveStatus);
                if(positiveStatus!=null && positiveStatus==-1)
                    criteria.andPositiveStatusIsNull(); // 待支部提交预备党员转正
            }
            // 考虑已经转出的情况 2016-12-19
            else if(stage==SystemConstants.APPLY_STAGE_OUT){
                criteria.andMemberStatusEqualTo(1); // 已转出的党员的申请
            }else{
                criteria.andMemberStatusEqualTo(0); // 不是党员或未转出的党员的申请
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
            memberApply_export(example, response);
            return;
        }

        int count = memberApplyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberApplyView> MemberApplys = memberApplyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", MemberApplys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    // 后台添加入党申请
    //@RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN}, logical = Logical.OR)
    @RequestMapping("/memberApply_au")
    public String memberApply_au(Integer userId, ModelMap modelMap) {

        if(userId!=null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
            MemberApply memberApply = memberApplyService.get(sysUser.getId());
            modelMap.put("memberApply", memberApply);

            if (memberApply != null) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                Map<Integer, Party> partyMap = partyService.findAll();
                Integer partyId = memberApply.getPartyId();
                Integer branchId = memberApply.getBranchId();
                if (partyId != null) {
                    modelMap.put("party", partyMap.get(partyId));
                }
                if (branchId != null) {
                    modelMap.put("branch", branchMap.get(branchId));
                }
            }
        }

        return "party/memberApply/memberApply_au";
    }

    //@RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/memberApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_au(@CurrentUser SysUserView loginUser, int userId, Integer partyId,
                              Integer branchId, String _applyTime, String _activeTime,
                                 String _candidateTime,String _trainTime,
                                 String _planTime, String _drawTime,
                                 String _growTime, String _positiveTime, String remark, HttpServletRequest request) {

        Integer oldPartyId = null;
        Integer oldBranchId = null;
        MemberApply _memberApply = memberApplyService.get(userId);
        if(_memberApply!=null) {
            oldPartyId = _memberApply.getPartyId();
            oldBranchId = _memberApply.getBranchId();
        }
        //===========权限
        Integer loginUserId = loginUser.getId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, oldPartyId);
            if(isAdmin) {
                // 分党委管理员不能修改所属分党委
                if(_memberApply!=null) partyId = null;
            }else{
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, oldPartyId, oldBranchId);
                // 支部管理员不能修改所属分党委及所属党支部
                if(isAdmin && _memberApply!=null) {
                    partyId = null;
                    branchId = null;
                }
            }
            if(!isAdmin) throw new UnauthorizedException();
        }


        if(_memberApply==null) {

            enterApplyService.checkMemberApplyAuth(userId);
            SysUserView sysUser = sysUserService.findById(userId);
            Date birth = sysUser.getBirth();
            if (birth != null && DateUtils.intervalYearsUntilNow(birth) < 18) {
                throw new RuntimeException("未满18周岁，不能申请入党。");
            }

            MemberApply memberApply = new MemberApply();
            memberApply.setUserId(userId);

            if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {
                memberApply.setType(SystemConstants.APPLY_TYPE_TEACHER); // 教职工
            } else if (sysUser.getType() == SystemConstants.USER_TYPE_BKS
                    || sysUser.getType() == SystemConstants.USER_TYPE_YJS) {
                memberApply.setType(SystemConstants.APPLY_TYPE_STU); // 学生
            } else {
                throw new UnauthorizedException("没有权限");
            }

            memberApply.setPartyId(partyId);
            memberApply.setBranchId(branchId);
            memberApply.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
            memberApply.setRemark(remark);
            memberApply.setFillTime(new Date());
            memberApply.setCreateTime(new Date());
            memberApply.setStage(SystemConstants.APPLY_STAGE_INIT);
            enterApplyService.memberApply(memberApply);

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "提交入党申请",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED, "");

            logger.info(addLog(SystemConstants.LOG_OW, "提交入党申请"));
        }else{

            StringBuffer _remark = new StringBuffer();
            MemberApply record = new MemberApply();

            record.setPartyId(partyId);
            record.setBranchId(branchId);
            if (partyId != null && partyId.intValue() != oldPartyId) {
                Map<Integer, Party> partyMap = partyService.findAll();
                _remark.append("所属分党委由" + partyMap.get(oldPartyId).getName() + "修改为"
                        + partyMap.get(partyId).getName() + ";");
            }

            if (branchId != null && (oldBranchId==null || branchId.intValue() != oldBranchId)) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                if(oldBranchId==null){
                    _remark.append("所属党支部修改为" + branchMap.get(branchId).getName() + ";");
                }else {
                    _remark.append("所属党支部由" + branchMap.get(oldBranchId).getName() + "修改为"
                            + branchMap.get(branchId).getName() + ";");
                }
            }

            String applyTime = DateUtils.formatDate(_memberApply.getApplyTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_applyTime) && !StringUtils.equalsIgnoreCase(applyTime, _applyTime.trim())) {
                record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
                _remark.append("提交书面申请书时间由" + applyTime + "修改为" + _applyTime + ";");
            }

            String activeTime = DateUtils.formatDate(_memberApply.getActiveTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_activeTime) && !StringUtils.equalsIgnoreCase(activeTime, _activeTime.trim())) {
                record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
                _remark.append("确定为入党积极分子时间由" + activeTime + "修改为" + _activeTime + ";");

                if(record.getApplyTime()!=null && record.getActiveTime().before(record.getApplyTime())){
                    throw new RuntimeException("确定为入党积极分子时间不能早于提交书面申请书时间");
                }
            }
            /*if(record.getApplyTime()==null && _memberApply.getApplyTime()!=null
                    && record.getActiveTime().before(_memberApply.getApplyTime())){
                throw new RuntimeException("确定为入党积极分子时间不能早于提交书面申请书时间");
            }*/

            String candidateTime = DateUtils.formatDate(_memberApply.getCandidateTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_candidateTime) && _memberApply.getStage()>=SystemConstants.APPLY_STAGE_CANDIDATE
                    && !StringUtils.equalsIgnoreCase(candidateTime, _candidateTime.trim())) {

                record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
                _remark.append("确定为发展对象时间由" + candidateTime+ "修改为" + _candidateTime + ";");

                if(record.getActiveTime()!=null && record.getCandidateTime().before(record.getActiveTime())){
                    throw new RuntimeException("确定为发展对象时间应该在确定为入党积极分子之后");
                }

                /*if(record.getActiveTime()==null && _memberApply.getActiveTime()!=null
                        && record.getCandidateTime().before(_memberApply.getActiveTime())){
                    throw new RuntimeException("确定为发展对象时间应该在确定为入党积极分子之后");
                }*/
            }

            String trainTime = DateUtils.formatDate(_memberApply.getTrainTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_trainTime) && _memberApply.getStage()>=SystemConstants.APPLY_STAGE_CANDIDATE
                    && !StringUtils.equalsIgnoreCase(trainTime, _trainTime.trim())) {
                record.setTrainTime(DateUtils.parseDate(_trainTime, DateUtils.YYYY_MM_DD));
                _remark.append("参加培训时间由" +trainTime + "修改为" + _trainTime + ";");
            }

            String planTime = DateUtils.formatDate(_memberApply.getPlanTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_planTime) && _memberApply.getStage()>=SystemConstants.APPLY_STAGE_PLAN
                    && !StringUtils.equalsIgnoreCase(planTime, _planTime.trim())) {
                record.setPlanTime(DateUtils.parseDate(_planTime, DateUtils.YYYY_MM_DD));
                _remark.append("列入发展计划时间由" + planTime + "修改为" + _planTime + ";");
            }

            String drawTime = DateUtils.formatDate(_memberApply.getDrawTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_drawTime) && _memberApply.getStage()>=SystemConstants.APPLY_STAGE_DRAW
                    && !StringUtils.equalsIgnoreCase(drawTime, _drawTime.trim())) {
                record.setDrawTime(DateUtils.parseDate(_drawTime, DateUtils.YYYY_MM_DD));
                _remark.append("领取志愿书时间由" + drawTime + "修改为" + _drawTime + ";");
            }

            String growTime = DateUtils.formatDate(_memberApply.getGrowTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_growTime) && _memberApply.getStage()>=SystemConstants.APPLY_STAGE_GROW
                    && !StringUtils.equalsIgnoreCase(growTime, _growTime.trim())) {
                record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
                _remark.append("入党时间由" + growTime + "修改为" + _growTime + ";");

                if(record.getCandidateTime()!=null && record.getGrowTime().before(record.getCandidateTime())) {
                    throw new RuntimeException("入党时间应该在确定为发展对象之后");
                }
            }

            String positiveTime = DateUtils.formatDate(_memberApply.getPositiveTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_positiveTime) && _memberApply.getStage()==SystemConstants.APPLY_STAGE_POSITIVE
                    && !StringUtils.equalsIgnoreCase(positiveTime, _positiveTime.trim())) {
                record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
                _remark.append("转正时间由" + positiveTime + "修改为" + _positiveTime + ";");

                if(record.getGrowTime()!=null && record.getPositiveTime().before(record.getGrowTime())) {
                    throw new RuntimeException("转正时间应该在入党时间之后");
                }
            }

            if(_remark.length()>0) {
                MemberApplyExample example = new MemberApplyExample();
                example.createCriteria().andUserIdEqualTo(userId);
                memberApplyService.updateByExampleSelective(userId, record, example);

                applyApprovalLogService.add(userId,
                        _memberApply.getPartyId(), _memberApply.getBranchId(), userId,
                        loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "修改",
                        SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED, _remark.toString());

                logger.info(addLog(SystemConstants.LOG_OW, "修改入党申请"));
            }/*else{
                return failed("您没有进行任何字段的修改。");
            }*/
        }
        return success(FormUtils.SUCCESS);
    }

    // 申请不通过
    @RequiresPermissions("memberApply:deny")
    @RequestMapping(value = "/apply_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_deny(@RequestParam(value = "ids[]") Integer[] ids, String remark, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        for (Integer userId : ids) {
            checkVerityAuth(userId);
        }

        memberApplyOpService.apply_deny(ids, remark);

        return success(FormUtils.SUCCESS);
    }

    // 申请通过
    @RequiresPermissions("memberApply:pass")
    @RequestMapping(value = "/apply_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_pass(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        for (Integer userId : ids) {
            checkVerityAuth(userId);
        }

        memberApplyOpService.apply_pass(ids, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:active")
    @RequestMapping(value = "/apply_active")
    public String apply_active(){

        return "party/memberApply/apply_active";
    }

    // 申请通过 成为积极分子
    @RequiresPermissions("memberApply:active")
    @RequestMapping(value = "/apply_active", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_active(@RequestParam(value = "ids[]") Integer[] ids, String _activeTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        Date activeTime = DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD);
        for (Integer userId : ids) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth(userId);
            MemberApply memberApply = verifyAuth.entity;
            if (activeTime.before(memberApply.getApplyTime())) {
                throw new RuntimeException("确定为入党积极分子时间不能早于提交书面申请书时间");
            }
        }

        memberApplyOpService.apply_active(ids, activeTime, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:candidate")
    @RequestMapping(value = "/apply_candidate")
    public String apply_candidate(){

        return "party/memberApply/apply_candidate";
    }

    // 提交 确定为发展对象
    @RequiresPermissions("memberApply:candidate")
    @RequestMapping(value = "/apply_candidate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_candidate(@RequestParam(value = "ids[]") Integer[] ids, String _candidateTime, String _trainTime,
                                  @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_candidate(ids, _candidateTime, _trainTime, loginUser.getId());

        return success();
    }
    // 审核 确定为发展对象
    @RequiresPermissions("memberApply:check")
    @RequestMapping(value = "/apply_candidate_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_candidate_check(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_candidate_check(ids, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:plan")
    @RequestMapping(value = "/apply_plan")
    public String apply_plan(){

        return "party/memberApply/apply_plan";
    }

    //提交 列入发展计划
    @RequiresPermissions("memberApply:plan")
    @RequestMapping(value = "/apply_plan", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_plan(@RequestParam(value = "ids[]") Integer[] ids, String _planTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_plan(ids, _planTime, loginUser.getId());

        return success();
    }

    //审核 列入发展计划
    @RequiresPermissions("memberApply:plan_check")
    @RequestMapping(value = "/apply_plan_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_plan_check(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_plan_check(ids, loginUser.getId());

        return success();
    }
    @RequiresPermissions("memberApply:draw")
    @RequestMapping(value = "/apply_draw")
    public String apply_draw(){

        return "party/memberApply/apply_draw";
    }

    //提交 领取志愿书
    @RequiresPermissions("memberApply:draw")
    @RequestMapping(value = "/apply_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_draw(@RequestParam(value = "ids[]") Integer[] ids, String _drawTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_draw(ids, _drawTime, loginUser.getId());

        return success();
    }
    //审核 领取志愿书
   /* @RequiresPermissions("memberApply:draw_check")
    @RequestMapping(value = "/apply_draw_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_draw_check(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_draw_check(ids, loginUser.getId());

        return success();
    }*/

    @RequiresPermissions("memberApply:grow")
    @RequestMapping(value = "/apply_grow")
    public String apply_grow(){

        return "party/memberApply/apply_grow";
    }

    //组织部管理员审核 预备党员 , 在领取志愿书模块
    @RequiresRoles(SystemConstants.ROLE_ODADMIN)
    @RequiresPermissions("memberApply:grow_check2")
    @RequestMapping(value = "/apply_grow_od_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_grow_od_check(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow_od_check(ids, loginUser.getId());

        return success(FormUtils.SUCCESS);
    }

    //党支部提交 预备党员， 在组织部审核之后
    @RequiresPermissions("memberApply:grow")
    @RequestMapping(value = "/apply_grow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_grow(@RequestParam(value = "ids[]") Integer[] ids, String _growTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow(ids, _growTime, loginUser.getId());

        return success();
    }
    //分党委审核 预备党员
    @RequiresPermissions("memberApply:grow_check")
    @RequestMapping(value = "/apply_grow_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_grow_check(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow_check(ids, loginUser.getId());

        return success();
    }


    @RequiresPermissions("memberApply:positive")
    @RequestMapping(value = "/apply_positive")
    public String apply_positive(){

        return "party/memberApply/apply_positive";
    }

    //提交 正式党员
    @RequiresPermissions("memberApply:positive")
    @RequestMapping(value = "/apply_positive", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_positive(@RequestParam(value = "ids[]") Integer[] ids, String _positiveTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive(ids, _positiveTime, loginUser.getId());

        return success();
    }

    //审核 正式党员
    @RequiresPermissions("memberApply:positive_check")
    @RequestMapping(value = "/apply_positive_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive_check(ids, loginUser.getId());

        return success();
    }

    //组织部管理员审核 正式党员， 在预备党员模块
    @RequiresRoles(SystemConstants.ROLE_ODADMIN)
    @RequiresPermissions("memberApply:positive_check2")
    @RequestMapping(value = "/apply_positive_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check2(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive_check2(ids, loginUser.getId());

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value ={SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberApply:update")
    @RequestMapping("/memberApply_back")
    public String memberApply_back() {

        return "party/memberApply/memberApply_back";
    }

    @RequiresRoles(value ={SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberApply:update")
    @RequestMapping(value = "/memberApply_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_back(@CurrentUser SysUserView loginUser,
                                   @RequestParam(value = "ids[]") Integer[] ids, byte stage,
                                   String reason) {

        memberApplyOpService.memberApply_back(ids, stage, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "打回入党申请：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApplyLog")
    public String memberApplyLog(@RequestParam(defaultValue = "1")int cls,
                                      Integer userId,
                                      String stage, Integer partyId,
                                      Integer branchId, ModelMap modelMap){

        modelMap.put("cls", cls);
        modelMap.put("stage", stage);
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

        return "party/memberApply/memberApplyLog_page";
    }

    public void memberApply_export(MemberApplyViewExample example, HttpServletResponse response) {

        List<MemberApplyView> memberApplys = memberApplyViewMapper.selectByExample(example);
        int rownum = memberApplyViewMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户", "所属分党委", "所属党支部", "类型"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberApplyView memberApply = memberApplys.get(i);
            String[] values = {
                    memberApply.getUserId() + "",
                    memberApply.getPartyId() + "",
                    memberApply.getBranchId() + "",
                    memberApply.getType() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "申请入党人员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
