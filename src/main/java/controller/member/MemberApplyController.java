package controller.member;

import bean.XlsUpload;
import controller.global.OpException;
import domain.member.MemberApply;
import domain.member.MemberApplyExample;
import domain.member.MemberApplyView;
import domain.member.MemberApplyViewExample;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import persistence.member.common.MemberApplyCount;
import service.member.MemberApplyOpService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberApplyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MemberApplyOpService memberApplyOpService;

    private VerifyAuth<MemberApply> checkVerityAuth(int userId) {
        MemberApply memberApply = memberApplyService.get(userId);
        return super.checkVerityAuth(memberApply, memberApply.getPartyId(), memberApply.getBranchId());
    }
    @RequiresPermissions({"memberApply:import", SystemConstants.PERMISSION_PARTYVIEWALL})
    @RequestMapping("/memberApply_import")
    public String memberApply_import(ModelMap modelMap) {

        return "member/memberApply/memberApply_import";
    }

    // 导入
    @RequiresPermissions({"memberApply:import", SystemConstants.PERMISSION_PARTYVIEWALL})
    @RequestMapping(value = "/memberApply_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<String, Party> runPartyMap = new HashMap<>();
        for (Party party : partyMap.values()) {
            if(BooleanUtils.isNotTrue(party.getIsDeleted())){
                runPartyMap.put(party.getCode(), party);
            }
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<String, Branch> runBranchMap = new HashMap<>();
        for (Branch branch : branchMap.values()) {
            if(BooleanUtils.isNotTrue(branch.getIsDeleted())){
                runBranchMap.put(branch.getCode(), branch);
            }
        }

        Map<String, Byte> stageMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : OwConstants.OW_APPLY_STAGE_MAP.entrySet()) {

            stageMap.put(entry.getValue(), entry.getKey());
        }

        Date now = new Date();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = XlsUpload.getXlsRows(sheet);

        List<MemberApply> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            MemberApply record = new MemberApply();

            String userCode = StringUtils.trim(xlsRow.get(0));
            if(StringUtils.isBlank(userCode)){
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getId());

            if(uv.getType()==SystemConstants.USER_TYPE_JZG){
                record.setType(OwConstants.OW_APPLY_TYPE_TEACHER);
            }else{
                record.setType(OwConstants.OW_APPLY_TYPE_STU);
            }

            String partyCode = StringUtils.trim(xlsRow.get(2));
            if(StringUtils.isBlank(partyCode)){
                throw new OpException("第{0}行联系分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null){
                throw new OpException("第{0}行联系分党委编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(party.getId());
            if(!partyService.isDirectBranch(party.getId())){

                String branchCode = StringUtils.trim(xlsRow.get(4));
                if(StringUtils.isBlank(branchCode)){
                    throw new OpException("第{0}行联系党支部编码为空", row);
                }
                Branch branch = runBranchMap.get(branchCode);
                if (branch == null){
                    throw new OpException("第{0}行联系党支部编码[{1}]不存在", row, partyCode);
                }
                record.setBranchId(branch.getId());
            }

            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(6))));
            record.setFillTime(record.getApplyTime());

            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(7))));
            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(8))));
            record.setTrainTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(9))));
            record.setPlanTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(10))));
            record.setDrawTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(11))));
            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(12))));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(13))));

            String _stage = StringUtils.trimToNull(xlsRow.get(14));
            if(StringUtils.isBlank(_stage)){
                throw new OpException("第{0}行当前状态为空");
            }
            Byte stage = stageMap.get(_stage);
            if(stage==null){
                throw new OpException("第{0}行当前状态[{1}]不存在", row, _stage);
            }
            record.setStage(stage);

            record.setIsRemove(false);
            record.setCreateTime(now);
            record.setRemark(StringUtils.trimToNull(xlsRow.get(15)));

            records.add(record);
        }

        int successCount = memberApplyService.batchImport(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply_approval")
    public String memberApply_approval(@CurrentUser SysUserView loginUser, Integer userId,
                                       byte type,
                                       byte stage,
                                       Byte status, // status=-1 代表对应的状态值为NULL
                                       ModelMap modelMap) {

        MemberApply currentMemberApply = null;
        if (userId != null) {
            //SysUser sysUser = sysUserService.findById(userId);
            //modelMap.put("user", sysUser);
            currentMemberApply = memberApplyService.get(userId);
        } else {
            currentMemberApply = memberApplyService.next(type, stage, status, null);
        }
        modelMap.put("memberApply", currentMemberApply);

        Integer branchId = currentMemberApply.getBranchId();
        Integer partyId = currentMemberApply.getPartyId();
        // 是否是当前记录的管理员
        switch (stage) {
            case OwConstants.OW_APPLY_STAGE_INIT:
            case OwConstants.OW_APPLY_STAGE_PASS:
                modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
                break;
            case OwConstants.OW_APPLY_STAGE_ACTIVE:
            case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                if (status == -1)
                    modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
                else
                    modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
                break;
            case OwConstants.OW_APPLY_STAGE_PLAN:
                modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
                break;
            case OwConstants.OW_APPLY_STAGE_DRAW:
                if (status == -1)
                    modelMap.put("isAdmin", ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL));
                else if (status == 2) // 组织部审核之后，党支部才提交
                    modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
                else if (status == 0) // 党支部提交后，分党委审核
                    modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
                break;
            case OwConstants.OW_APPLY_STAGE_GROW:
                if (status == -1)
                    modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
                else if (status == 0)
                    modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
                else
                    modelMap.put("isAdmin", ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL));
                break;
        }
        // 组织部可以审批所有
        if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
            modelMap.put("isAdmin", true);
        }

        // 读取总数
        modelMap.put("count", memberApplyService.count(null, null, type, stage, status));
        // 下一条记录
        modelMap.put("next", memberApplyService.next(type, stage, status, currentMemberApply));
        // 上一条记录
        modelMap.put("last", memberApplyService.last(type, stage, status, currentMemberApply));

        return "member/memberApply/memberApply_approval";
    }

    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply")
    public String memberApply(@RequestParam(defaultValue = "1") int cls,
                              Integer userId,
                              Integer partyId,
                              Integer branchId,
                              @RequestParam(defaultValue = OwConstants.OW_APPLY_TYPE_STU + "") Byte type,
                              @RequestParam(defaultValue = "0") Byte stage,
                              ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);
        modelMap.put("stage", stage);
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

        switch (stage) {
            case OwConstants.OW_APPLY_STAGE_INIT:
            case OwConstants.OW_APPLY_STAGE_PASS:
                modelMap.put("applyCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_INIT, null));
                modelMap.put("activeCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_PASS, null));
                break;
            case OwConstants.OW_APPLY_STAGE_ACTIVE:
                modelMap.put("candidateCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_ACTIVE, (byte) -1));
                modelMap.put("candidateCheckCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_ACTIVE, (byte) 0));
                break;
            case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                modelMap.put("planCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_CANDIDATE, (byte) -1));
                modelMap.put("planCheckCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_CANDIDATE, (byte) 0));
                break;
            case OwConstants.OW_APPLY_STAGE_PLAN:
                modelMap.put("drawCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_PLAN, (byte) -1));
                //modelMap.put("drawCheckCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_PLAN, (byte) 0));
                break;
            case OwConstants.OW_APPLY_STAGE_DRAW:
                // 组织部先审核 - 支部提交发展时间 - 分党委审核
                modelMap.put("growOdCheckCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_DRAW, (byte) -1));
                modelMap.put("growCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_DRAW, (byte) 2));
                modelMap.put("growCheckCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_DRAW, (byte) 0));
                break;
            case OwConstants.OW_APPLY_STAGE_GROW:
                modelMap.put("positiveCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_GROW, (byte) -1));
                modelMap.put("positiveCheckCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_GROW, (byte) 0));
                modelMap.put("positiveOdCheckCount", memberApplyService.count(null, null, type, OwConstants.OW_APPLY_STAGE_GROW, (byte) 1));
                break;
        }

        Map<Byte, Integer> stageCountMap = new HashMap<>();
        Map<String, Integer> stageTypeCountMap = new HashMap<>();

        boolean addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();
        List<MemberApplyCount> memberApplyCounts = iMemberMapper.selectMemberApplyCount(addPermits, adminPartyIdList, adminBranchIdList);
        for (MemberApplyCount memberApplyCount : memberApplyCounts) {

            byte _stage = memberApplyCount.getStage();
            byte _type = memberApplyCount.getType();
            Integer _count = memberApplyCount.getNum();
            Integer stageCount = stageCountMap.get(_stage);
            if (stageCount == null) stageCount = 0;
            stageCountMap.put(_stage, stageCount + _count);

            Integer stageTypeCount = stageTypeCountMap.get(_stage + "_" + _type);
            if (stageTypeCount == null) stageTypeCount = 0;
            stageTypeCountMap.put(_stage + "_" + _type, stageTypeCount + _count);
        }
        modelMap.put("stageCountMap", stageCountMap);
        modelMap.put("stageTypeCountMap", stageTypeCountMap);

        return "member/memberApply/memberApply_page";
    }

    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply_data")
    public void memberApply_data(HttpServletResponse response,
                                 Integer userId,
                                 Integer partyId,
                                 Integer branchId,
                                 @RequestParam(defaultValue = OwConstants.OW_APPLY_TYPE_STU + "") Byte type,
                                 @RequestParam(defaultValue = "0") Byte stage,
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

        example.setOrderByClause("party_sort_order desc, branch_sort_order desc, create_time desc");

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (stage != null) {
            if (stage == OwConstants.OW_APPLY_STAGE_INIT || stage == OwConstants.OW_APPLY_STAGE_PASS) {
                List<Byte> stageList = new ArrayList<>();
                stageList.add(OwConstants.OW_APPLY_STAGE_INIT);
                stageList.add(OwConstants.OW_APPLY_STAGE_PASS);
                criteria.andStageIn(stageList);
            } else if (stage > OwConstants.OW_APPLY_STAGE_PASS || stage == OwConstants.OW_APPLY_STAGE_DENY) {
                criteria.andStageEqualTo(stage);
            }

            if (stage == OwConstants.OW_APPLY_STAGE_DRAW) {
                if (growStatus != null && growStatus >= 0)
                    criteria.andGrowStatusEqualTo(growStatus);
                if (growStatus != null && growStatus == -1)
                    criteria.andGrowStatusIsNull();
            } else if (stage == OwConstants.OW_APPLY_STAGE_GROW) {
                if (positiveStatus != null && positiveStatus >= 0)
                    criteria.andPositiveStatusEqualTo(positiveStatus);
                if (positiveStatus != null && positiveStatus == -1)
                    criteria.andPositiveStatusIsNull(); // 待支部提交预备党员转正
            }

            // 考虑已经转出的情况 2016-12-19
            if (stage == OwConstants.OW_APPLY_STAGE_OUT) {
                criteria.andMemberStatusEqualTo(1); // 已转出的党员的申请
            } else {
                criteria.andMemberStatusEqualTo(0); // 不是党员或未转出的党员的申请
            }

            // 已移除的记录
            if(stage == -3){
                criteria.andIsRemoveEqualTo(true);
            }else{
                criteria.andIsRemoveEqualTo(false);
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

        long count = memberApplyViewMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 后台添加入党申请
    @RequestMapping("/memberApply_au")
    public String memberApply_au(Integer userId, ModelMap modelMap) {

        if (userId != null) {
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

        return "member/memberApply/memberApply_au";
    }

    @RequestMapping(value = "/memberApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_au(@CurrentUser SysUserView loginUser, int userId, Integer partyId,
                                 Integer branchId, String _applyTime, String _activeTime,
                                 String _candidateTime, String _trainTime,
                                 String _planTime, String _drawTime,
                                 String _growTime, String _positiveTime, String remark, HttpServletRequest request) {

        Integer oldPartyId = null;
        Integer oldBranchId = null;
        MemberApply _memberApply = memberApplyService.get(userId);
        if (_memberApply != null) {
            oldPartyId = _memberApply.getPartyId();
            oldBranchId = _memberApply.getBranchId();
        }
        //===========权限
        Integer loginUserId = loginUser.getId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, oldPartyId);
            if (isAdmin) {
                // 分党委管理员不能修改所属分党委
                if (_memberApply != null) partyId = null;
            } else {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, oldPartyId, oldBranchId);
                // 支部管理员不能修改所属分党委及所属党支部
                if (isAdmin && _memberApply != null) {
                    partyId = null;
                    branchId = null;
                }
            }
            if (!isAdmin) throw new UnauthorizedException();
        }


        if (_memberApply == null) {

            enterApplyService.checkMemberApplyAuth(userId);
            SysUserView sysUser = sysUserService.findById(userId);
            Date birth = sysUser.getBirth();
            if (birth != null && DateUtils.intervalYearsUntilNow(birth) < 18) {
                return failed("未满18周岁，不能申请入党。");
            }

            MemberApply memberApply = new MemberApply();
            memberApply.setUserId(userId);

            if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {
                memberApply.setType(OwConstants.OW_APPLY_TYPE_TEACHER); // 教职工
            } else if (sysUser.getType() == SystemConstants.USER_TYPE_BKS
                    || sysUser.getType() == SystemConstants.USER_TYPE_YJS) {
                memberApply.setType(OwConstants.OW_APPLY_TYPE_STU); // 学生
            } else {
                throw new UnauthorizedException("没有权限");
            }

            memberApply.setPartyId(partyId);
            memberApply.setBranchId(branchId);

            Date applyTime = DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD);
            if(applyTime==null){
                return failed("提交书面申请书时间不允许为空。");
            }
            memberApply.setApplyTime(applyTime);
            memberApply.setRemark(remark);
            memberApply.setFillTime(new Date());
            memberApply.setCreateTime(new Date());
            memberApply.setStage(OwConstants.OW_APPLY_STAGE_INIT);
            enterApplyService.memberApply(memberApply);

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "提交入党申请",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, "");

            logger.info(addLog(LogConstants.LOG_PARTY, "提交入党申请"));
        } else {

            StringBuffer _remark = new StringBuffer();
            MemberApply record = new MemberApply();

            record.setPartyId(partyId);
            record.setBranchId(branchId);
            if (partyId != null && partyId.intValue() != oldPartyId) {
                Map<Integer, Party> partyMap = partyService.findAll();
                _remark.append("所属分党委由" + partyMap.get(oldPartyId).getName() + "修改为"
                        + partyMap.get(partyId).getName() + ";");
            }

            if (branchId != null && (oldBranchId == null || branchId.intValue() != oldBranchId)) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                if (oldBranchId == null) {
                    _remark.append("所属党支部修改为" + branchMap.get(branchId).getName() + ";");
                } else {
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

                if (record.getApplyTime() != null && record.getActiveTime().before(record.getApplyTime())) {
                    return failed("确定为入党积极分子时间不能早于提交书面申请书时间");
                }
            }
            /*if(record.getApplyTime()==null && _memberApply.getApplyTime()!=null
                    && record.getActiveTime().before(_memberApply.getApplyTime())){
               return failed("确定为入党积极分子时间不能早于提交书面申请书时间");
            }*/

            String candidateTime = DateUtils.formatDate(_memberApply.getCandidateTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_candidateTime) && _memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_CANDIDATE
                    && !StringUtils.equalsIgnoreCase(candidateTime, _candidateTime.trim())) {

                record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
                _remark.append("确定为发展对象时间由" + candidateTime + "修改为" + _candidateTime + ";");

                if (record.getActiveTime() != null && record.getCandidateTime().before(record.getActiveTime())) {
                    return failed("确定为发展对象时间应该在确定为入党积极分子之后");
                }

                /*if(record.getActiveTime()==null && _memberApply.getActiveTime()!=null
                        && record.getCandidateTime().before(_memberApply.getActiveTime())){
                   return failed("确定为发展对象时间应该在确定为入党积极分子之后");
                }*/
            }

            String trainTime = DateUtils.formatDate(_memberApply.getTrainTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_trainTime) && _memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_CANDIDATE
                    && !StringUtils.equalsIgnoreCase(trainTime, _trainTime.trim())) {
                record.setTrainTime(DateUtils.parseDate(_trainTime, DateUtils.YYYY_MM_DD));
                _remark.append("参加培训时间由" + trainTime + "修改为" + _trainTime + ";");
            }

            String planTime = DateUtils.formatDate(_memberApply.getPlanTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_planTime) && _memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_PLAN
                    && !StringUtils.equalsIgnoreCase(planTime, _planTime.trim())) {
                record.setPlanTime(DateUtils.parseDate(_planTime, DateUtils.YYYY_MM_DD));
                _remark.append("列入发展计划时间由" + planTime + "修改为" + _planTime + ";");
            }

            String drawTime = DateUtils.formatDate(_memberApply.getDrawTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_drawTime) && _memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_DRAW
                    && !StringUtils.equalsIgnoreCase(drawTime, _drawTime.trim())) {
                record.setDrawTime(DateUtils.parseDate(_drawTime, DateUtils.YYYY_MM_DD));
                _remark.append("领取志愿书时间由" + drawTime + "修改为" + _drawTime + ";");
            }

            String growTime = DateUtils.formatDate(_memberApply.getGrowTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_growTime) && _memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_GROW
                    && !StringUtils.equalsIgnoreCase(growTime, _growTime.trim())) {
                record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
                _remark.append("入党时间由" + growTime + "修改为" + _growTime + ";");

                if (record.getCandidateTime() != null && record.getGrowTime().before(record.getCandidateTime())) {
                    return failed("入党时间应该在确定为发展对象之后");
                }
            }

            String positiveTime = DateUtils.formatDate(_memberApply.getPositiveTime(), DateUtils.YYYY_MM_DD);
            if (StringUtils.isNotBlank(_positiveTime) && _memberApply.getStage() == OwConstants.OW_APPLY_STAGE_POSITIVE
                    && !StringUtils.equalsIgnoreCase(positiveTime, _positiveTime.trim())) {
                record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
                _remark.append("转正时间由" + positiveTime + "修改为" + _positiveTime + ";");

                if (record.getGrowTime() != null && record.getPositiveTime().before(record.getGrowTime())) {
                    return failed("转正时间应该在入党时间之后");
                }
            }

            if (_remark.length() > 0) {
                MemberApplyExample example = new MemberApplyExample();
                example.createCriteria().andUserIdEqualTo(userId);
                memberApplyService.updateByExampleSelective(userId, record, example);

                applyApprovalLogService.add(userId,
                        _memberApply.getPartyId(), _memberApply.getBranchId(), userId,
                        loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "修改",
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, _remark.toString());

                logger.info(addLog(LogConstants.LOG_PARTY, "修改入党申请"));
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
    public String apply_active() {

        return "member/memberApply/apply_active";
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

            if(memberApply.getApplyTime()==null){
                return failed(memberApply.getUser().getRealname() +"的提交书面申请书时间为空，请修改或打回。");
            }

            if (activeTime.before(memberApply.getApplyTime())) {
                return failed("确定为入党积极分子时间不能早于提交书面申请书时间");
            }
        }

        memberApplyOpService.apply_active(ids, activeTime, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:candidate")
    @RequestMapping(value = "/apply_candidate")
    public String apply_candidate() {

        return "member/memberApply/apply_candidate";
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
    public String apply_plan() {

        return "member/memberApply/apply_plan";
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
    public String apply_draw() {

        return "member/memberApply/apply_draw";
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
    public String apply_grow() {

        return "member/memberApply/apply_grow";
    }

    //组织部管理员审核 预备党员 , 在领取志愿书模块
    @RequiresPermissions({"memberApply:grow_check2", SystemConstants.PERMISSION_PARTYVIEWALL})
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
    public String apply_positive() {

        return "member/memberApply/apply_positive";
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
    @RequiresPermissions({"memberApply:positive_check2", SystemConstants.PERMISSION_PARTYVIEWALL})
    @RequestMapping(value = "/apply_positive_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check2(@RequestParam(value = "ids[]") Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive_check2(ids, loginUser.getId());

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberApply:update")
    @RequestMapping("/memberApply_back")
    public String memberApply_back() {

        return "member/memberApply/memberApply_back";
    }

    @RequiresPermissions("memberApply:update")
    @RequestMapping(value = "/memberApply_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_back(@CurrentUser SysUserView loginUser,
                                   @RequestParam(value = "ids[]") Integer[] ids, byte stage,
                                   String reason) {

        memberApplyOpService.memberApply_back(ids, stage, reason, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_PARTY, "打回入党申请：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    //移除记录（只允许移除未发展的）
    @RequiresPermissions("memberApply:remove")
    @RequestMapping("/memberApply_remove")
    public String memberApply_remove() {

        return "member/memberApply/memberApply_remove";
    }

    @RequiresPermissions("memberApply:remove")
    @RequestMapping(value = "/memberApply_remove", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_remove(@RequestParam(value = "ids[]") Integer[] ids,
                                     Boolean isRemove,
                                     String reason, HttpServletRequest request) {

        memberApplyOpService.memberApply_remove(ids, BooleanUtils.isTrue(isRemove), reason);
        return success();
    }


    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApplyLog")
    public String memberApplyLog(@RequestParam(defaultValue = "1") int cls,
                                 Integer userId,
                                 String stage, Integer partyId,
                                 Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("stage", stage);
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

        return "member/memberApply/memberApplyLog_page";
    }

    public void memberApply_export(MemberApplyViewExample example, HttpServletResponse response) {

        List<MemberApplyView> memberApplys = memberApplyViewMapper.selectByExample(example);
        long rownum = memberApplyViewMapper.countByExample(example);

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

        String fileName = "申请入党人员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
