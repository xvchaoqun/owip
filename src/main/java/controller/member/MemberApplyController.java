package controller.member;

import controller.global.OpException;
import domain.member.*;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import persistence.member.common.SponsorBean;
import service.member.MemberApplyOpService;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
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

    // 党员发展流程 总体布局
    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApply_layout")
    public String memberApply_layout(@RequestParam(defaultValue = "1") int cls, ModelMap modelMap) {

        if (cls == 1)
            return "forward:/memberApply";
        //if (cls == 2)
        if (cls == 3)
            return "forward:/memberApplyLog";
        if (cls == 4)
            return "forward:/memberApplyExport";
        if (cls == 5)
            return "forward:/partyPublic";
        if (cls == 6)
            return "forward:/applySnRange";
        if (cls == 7 || cls == 8)
            return "forward:/applySn";

        return null;
    }

    @RequiresPermissions("memberApply:import")
    @RequestMapping("/memberApply_import")
    public String memberApply_import(ModelMap modelMap) {

        return "member/memberApply/memberApply_import";
    }

    // 导入
    @RequiresPermissions("memberApply:import")
    @RequestMapping(value = "/memberApply_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<String, Party> runPartyMap = new HashMap<>();
        for (Party party : partyMap.values()) {
            if (BooleanUtils.isNotTrue(party.getIsDeleted())) {
                runPartyMap.put(party.getCode(), party);
            }
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<String, Branch> runBranchMap = new HashMap<>();
        for (Branch branch : branchMap.values()) {
            if (BooleanUtils.isNotTrue(branch.getIsDeleted())) {
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
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        boolean needCandidateTrain = CmTag.getBoolProperty("memberApply_needCandidateTrain");
        List<MemberApply> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            MemberApply record = new MemberApply();

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getId());

            String partyCode = StringUtils.trim(xlsRow.get(2));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行联系分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null) {
                throw new OpException("第{0}行联系分党委编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(party.getId());
            if (!partyService.isDirectBranch(party.getId())) {

                String branchCode = StringUtils.trim(xlsRow.get(4));
                if (StringUtils.isBlank(branchCode)) {
                    throw new OpException("第{0}行联系党支部编码为空", row);
                }
                Branch branch = runBranchMap.get(branchCode);
                if (branch == null) {
                    throw new OpException("第{0}行联系党支部编码[{1}]不存在", row, branchCode);
                }
                record.setBranchId(branch.getId());
            }

            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            if (!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), partyId, branchId)) {
                throw new OpException("第{0}行没有权限导入（您不是该支部的管理员）", row);
            }

            int rowNum = 6;
            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));
            record.setFillTime(record.getApplyTime());

            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));
            record.setActiveTrainStartTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));

            if(needCandidateTrain) {
                record.setActiveTrainEndTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));
                record.setActiveGrade(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            }

            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));
            record.setCandidateTrainStartTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));

            if(needCandidateTrain) {
                record.setCandidateTrainEndTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));
                record.setCandidateGrade(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            }

            if (!CmTag.getBoolProperty("ignore_plan_and_draw")) {
                record.setPlanTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));
                record.setDrawTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));
                String applySnStr = StringUtils.trimToNull(xlsRow.get(rowNum++));
                if (applySnStr != null) {

                    ApplySn applySn = iMemberMapper.getApplySnByDisplaySn(DateUtils.getCurrentYear(), applySnStr);
                    if (applySn == null) {
                        throw new OpException("第{0}行志愿书编码不存在[{1}]", row, applySnStr);
                    }

                    record.setApplySnId(applySn.getRangeId());
                    record.setApplySn(applySn.getDisplaySn());
                }
            }

            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));

            String _stage = StringUtils.trimToNull(xlsRow.get(rowNum++));
            if (StringUtils.isBlank(_stage)) {
                throw new OpException("第{0}行当前状态为空", row);
            }
            Byte stage = stageMap.get(_stage);
            if (stage == null) {
                throw new OpException("第{0}行当前状态[{1}]不存在", row, _stage);
            }
            record.setStage(stage);

            record.setIsRemove(false);
            record.setCreateTime(now);
            record.setRemark(StringUtils.trimToNull(xlsRow.get(rowNum++)));

            try{
                extCommonService.checkMemberApplyData(record);
            }catch (OpException ex){
                throw new OpException("第{0}行数据有误:" + ex.getMessage(), row);
            }

            records.add(record);
        }

        int addCount = memberApplyService.batchImport(records);
        int totalCount = records.size();

        applyApprovalLogService.add(null,
                null, null, null,
                ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "批量导入",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                MessageFormat.format("操作成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                        totalCount, addCount, totalCount - addCount));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入党员发展记录成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("memberApply:admin")
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
                modelMap.put("isAdmin", PartyHelper.hasBranchAuth(loginUser.getId(), partyId, branchId));
                break;
            case OwConstants.OW_APPLY_STAGE_ACTIVE:
            case OwConstants.OW_APPLY_STAGE_CANDIDATE:
                if (status == -1)
                    modelMap.put("isAdmin", PartyHelper.hasBranchAuth(loginUser.getId(), partyId, branchId));
                else
                    modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), partyId));
                break;
            case OwConstants.OW_APPLY_STAGE_PLAN:
                modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), partyId));
                break;
            case OwConstants.OW_APPLY_STAGE_DRAW:
                if (status == -1)
                    modelMap.put("isAdmin", ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL));
                else if (status == 2) // 组织部审核之后，党支部才提交
                    modelMap.put("isAdmin", PartyHelper.hasBranchAuth(loginUser.getId(), partyId, branchId));
                else if (status == 0) // 党支部提交后，分党委审核
                    modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), partyId));
                break;
            case OwConstants.OW_APPLY_STAGE_GROW:
                if (status == -1)
                    modelMap.put("isAdmin", PartyHelper.hasBranchAuth(loginUser.getId(), partyId, branchId));
                else if (status == 0)
                    modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), partyId));
                else
                    modelMap.put("isAdmin", ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL));
                break;
        }
        // 组织部可以审批所有
        if (ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
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

    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApply")
    public String memberApply(@RequestParam(defaultValue = "1") int cls,
                              Integer userId,
                              Integer partyId,
                              Integer branchId,
                              @RequestParam(defaultValue = MemberConstants.MEMBER_TYPE_STUDENT + "") Byte type,
                              @RequestParam(defaultValue = "0") Byte stage,
                              @RequestParam(required = false, defaultValue = "1") Boolean isApply,
                              ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);
        modelMap.put("stage", stage);
        modelMap.put("isApply",isApply);
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

        boolean addPermits = !ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
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

    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApply_data")
    public void memberApply_data(HttpServletResponse response,
                                 Integer userId,
                                 Integer partyId,
                                 Integer branchId,
                                 @RequestParam(defaultValue = MemberConstants.MEMBER_TYPE_STUDENT + "") Byte type,
                                 @RequestParam(defaultValue = "0") Byte stage,
                                 @RequestParam(required = false, defaultValue = "1") Boolean isApply,
                                 Byte growStatus, // 领取志愿书阶段查询
                                 Byte positiveStatus, // 预备党员阶段查询
                                 String applySn, // 志愿书编码
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

        if(type==MemberConstants.MEMBER_TYPE_TEACHER){
            criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_JZG,
                    SystemConstants.USER_TYPE_RETIRE));
        }else{
            criteria.andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_BKS,
                    SystemConstants.USER_TYPE_SS, SystemConstants.USER_TYPE_BS));
        }
        if (stage != null && stage != -4) {
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
            if (stage == -3) {
                criteria.andIsRemoveEqualTo(true);
            } else {
                criteria.andIsRemoveEqualTo(false);
            }

            if (stage == OwConstants.OW_APPLY_STAGE_INIT){
                // 入党申请人阶段，区分新申请or继续培养
                if (isApply) {
                    criteria.andApplyStageEqualTo(OwConstants.OW_APPLY_STAGE_INIT);
                }else {
                    criteria.andApplyStageNotEqualTo(OwConstants.OW_APPLY_STAGE_INIT);
                }
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
        if (StringUtils.isNotBlank(applySn)) {
            criteria.andApplySnLike("%" + applySn.trim() + "%");
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

    // 后台管理党员发展信息
    @RequiresPermissions("memberApply:admin")
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

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/memberApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_au(@CurrentUser SysUserView loginUser,
                                 MemberApply record,
                                 @RequestParam(defaultValue = "add", required = false) String op,
                                 String remark, HttpServletRequest request) {

        int userId = record.getUserId();
        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        Byte stage = record.getStage();

        record.setContactUsers(StringUtils.trimToEmpty(record.getContactUsers()));
        record.setContactUserIds(StringUtils.trimToEmpty(record.getContactUserIds()));
        record.setSponsorUsers(StringUtils.trimToEmpty(record.getSponsorUsers()));
        record.setSponsorUserIds(StringUtils.trimToEmpty(record.getSponsorUserIds()));
        record.setGrowContactUsers(StringUtils.trimToEmpty(record.getGrowContactUsers()));
        record.setGrowContactUserIds(StringUtils.trimToEmpty(record.getGrowContactUserIds()));

        Integer oldPartyId = null;
        Integer oldBranchId = null;
        MemberApply _memberApply = memberApplyService.get(userId);
        if (_memberApply != null) {
            oldPartyId = _memberApply.getPartyId();
            oldBranchId = _memberApply.getBranchId();
        } else {
            oldPartyId = partyId;
            oldBranchId = branchId;
        }
        //===========权限
        Integer loginUserId = loginUser.getId();
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, oldPartyId);
            if (isAdmin) {
                // 分党委管理员不能修改所在党组织
                if (_memberApply != null) partyId = null;
            } else {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, oldPartyId, oldBranchId);
                // 支部管理员不能修改所在党组织及所属党支部
                if (isAdmin && _memberApply != null) {
                    partyId = null;
                    branchId = null;
                }
            }
            if (!isAdmin) throw new UnauthorizedException();
        }

        if (StringUtils.equals(op, "add") && _memberApply != null) {

            Party party = partyMapper.selectByPrimaryKey(_memberApply.getPartyId());

            // 如果存在党员发展记录，且对应的分党委是现运行分党委时，不允许直接添加发展党员记录
            if (party != null && BooleanUtils.isNotTrue(party.getIsDeleted())) {

                 // 当前节点不为未通过或者申请状态不可直接添加
                if (_memberApply.getStage() != OwConstants.OW_APPLY_STAGE_DENY &&
                        _memberApply.getStage() != OwConstants.OW_APPLY_STAGE_INIT){

                    return failed("{0}已经添加，当前在{1}阶段，所在二级党委：{2}。请联系所在二级党委完成党员发展流程。",
                        _memberApply.getUser().getRealname(),
                        OwConstants.OW_APPLY_ALLSTAGE_MAP.get(_memberApply.getStage()), party.getName());
                }
            }
        }

        if (_memberApply == null) {

            if (stage == null || !OwConstants.OW_APPLY_STAGE_MAP.containsKey(stage)) {
                return failed("数据有误，请刷新重试。");
            }

            enterApplyService.checkMemberApplyAuth(userId);

            record.setUserId(userId);
            record.setStage(stage);

            if(stage==OwConstants.OW_APPLY_STAGE_DRAW){
                record.setDrawStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
                if(!CmTag.getBoolProperty("draw_od_check")) {
                    // 领取志愿书不需要组织部审批
                    record.setGrowStatus(OwConstants.OW_APPLY_STATUS_OD_CHECKED);
                }
            }

            record.setPartyId(partyId);
            record.setBranchId(branchId);

            record.setRemark(remark);
            record.setFillTime(new Date());
            record.setCreateTime(new Date());

            extCommonService.checkMemberApplyData(record);

            enterApplyService.memberApply(record);

            applyApprovalLogService.add(userId,
                    record.getPartyId(), record.getBranchId(), userId,
                    loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "后台添加",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, "");

            logger.info(addLog(LogConstants.LOG_MEMBER, "添加党员发展信息"));
        } else {

            StringBuffer _remark = new StringBuffer();

            record.setPartyId(partyId);
            record.setBranchId(branchId);
            if (partyId != null && partyId.intValue() != oldPartyId) {
                Map<Integer, Party> partyMap = partyService.findAll();
                _remark.append("所在党组织由" + partyMap.get(oldPartyId).getName() + "修改为"
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
            String _applyTime = DateUtils.formatDate(record.getApplyTime(), DateUtils.YYYY_MM_DD);
            if (!StringUtils.equalsIgnoreCase(applyTime, StringUtils.trimToNull(_applyTime))) {
                _remark.append("提交书面申请书时间由" + applyTime + "修改为" + _applyTime + ";");
            }

            String activeTime = DateUtils.formatDate(_memberApply.getActiveTime(), DateUtils.YYYY_MM_DD);
            String _activeTime = DateUtils.formatDate(record.getActiveTime(), DateUtils.YYYY_MM_DD);
            if (!StringUtils.equalsIgnoreCase(activeTime, StringUtils.trimToNull(_activeTime))) {
                _remark.append("确定为入党积极分子时间由" + activeTime + "修改为" + _activeTime + ";");
            }
            String activeTrainStartTime = DateUtils.formatDate(_memberApply.getActiveTrainStartTime(), DateUtils.YYYY_MM_DD);
            String _activeTrainStartTime = DateUtils.formatDate(record.getActiveTrainStartTime(), DateUtils.YYYY_MM_DD);
            if (!StringUtils.equalsIgnoreCase(activeTrainStartTime, StringUtils.trimToNull(_activeTrainStartTime))) {
                _remark.append("积极分子培训起始时间由" + activeTrainStartTime + "修改为" + _activeTrainStartTime + ";");
            }

            String activeTrainEndTime = DateUtils.formatDate(_memberApply.getActiveTrainEndTime(), DateUtils.YYYY_MM_DD);
            String _activeTrainEndTime = DateUtils.formatDate(record.getActiveTrainEndTime(), DateUtils.YYYY_MM_DD);
            if (!StringUtils.equalsIgnoreCase(activeTrainEndTime, StringUtils.trimToNull(_activeTrainEndTime))) {
                _remark.append("积极分子培训结束时间由" + activeTrainEndTime + "修改为" + _activeTrainEndTime + ";");
            }

            String activeGrade = record.getActiveGrade();
            if (!StringUtils.equalsIgnoreCase(activeGrade, _memberApply.getActiveGrade())) {
                record.setActiveGrade(activeGrade);
                _remark.append("积极分子结业考试成绩由" + StringUtils.trimToEmpty(_memberApply.getActiveGrade()) + "修改为" + activeGrade + ";");
            }

            String candidateTime = DateUtils.formatDate(_memberApply.getCandidateTime(), DateUtils.YYYY_MM_DD);
            String _candidateTime = DateUtils.formatDate(record.getCandidateTime(), DateUtils.YYYY_MM_DD);
            if (_memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_CANDIDATE
                    && !StringUtils.equalsIgnoreCase(candidateTime, StringUtils.trimToNull(_candidateTime))) {

                _remark.append("确定为发展对象时间由" + candidateTime + "修改为" + _candidateTime + ";");
            }

            String candidateTrainStartTime = DateUtils.formatDate(_memberApply.getCandidateTrainStartTime(), DateUtils.YYYY_MM_DD);
            String _candidateTrainStartTime = DateUtils.formatDate(record.getCandidateTrainStartTime(), DateUtils.YYYY_MM_DD);
            if (_memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_CANDIDATE
                    && !StringUtils.equalsIgnoreCase(candidateTrainStartTime, StringUtils.trimToNull(_candidateTrainStartTime))) {
                _remark.append("发展对象培训起始时间由" + candidateTrainStartTime + "修改为" + _candidateTrainStartTime + ";");
            }

            String candidateTrainEndTime = DateUtils.formatDate(_memberApply.getCandidateTrainEndTime(), DateUtils.YYYY_MM_DD);
            String _candidateTrainEndTime = DateUtils.formatDate(record.getCandidateTrainEndTime(), DateUtils.YYYY_MM_DD);
            if (_memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_CANDIDATE
                    && !StringUtils.equalsIgnoreCase(candidateTrainEndTime, StringUtils.trimToNull(_candidateTrainEndTime))) {
                _remark.append("发展对象培训结束时间由" + candidateTrainEndTime + "修改为" + _candidateTrainEndTime + ";");
            }

            String candidateGrade = record.getCandidateGrade();
            if (!StringUtils.equalsIgnoreCase(candidateGrade, _memberApply.getCandidateGrade())) {

                record.setCandidateGrade(candidateGrade);
                _remark.append("发展对象结业考试成绩由" + StringUtils.trimToEmpty(_memberApply.getCandidateGrade()) + "修改为" + candidateGrade + ";");
            }

            String planTime = DateUtils.formatDate(_memberApply.getPlanTime(), DateUtils.YYYY_MM_DD);
            String _planTime = DateUtils.formatDate(record.getPlanTime(), DateUtils.YYYY_MM_DD);
            if (_memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_PLAN
                    && !StringUtils.equalsIgnoreCase(planTime, StringUtils.trimToNull(_planTime))) {
                _remark.append("列入发展计划时间由" + planTime + "修改为" + _planTime + ";");
            }

            String drawTime = DateUtils.formatDate(_memberApply.getDrawTime(), DateUtils.YYYY_MM_DD);
            String _drawTime = DateUtils.formatDate(record.getDrawTime(), DateUtils.YYYY_MM_DD);
            if (_memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_DRAW
                    && !StringUtils.equalsIgnoreCase(drawTime, StringUtils.trimToNull(_drawTime))) {
                _remark.append("领取志愿书时间由" + drawTime + "修改为" + _drawTime + ";");
            }

            String growTime = DateUtils.formatDate(_memberApply.getGrowTime(), DateUtils.YYYY_MM_DD);
            String _growTime = DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD);
            if (_memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_GROW
                    && !StringUtils.equalsIgnoreCase(growTime, StringUtils.trimToNull(_growTime))) {
                _remark.append("入党时间由" + growTime + "修改为" + _growTime + ";");

            }

            String positiveTime = DateUtils.formatDate(_memberApply.getPositiveTime(), DateUtils.YYYY_MM_DD);
            String _positiveTime = DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD);
            if (_memberApply.getStage() == OwConstants.OW_APPLY_STAGE_POSITIVE
                    && !StringUtils.equalsIgnoreCase(positiveTime, StringUtils.trimToNull(_positiveTime))) {
                _remark.append("转正时间由" + positiveTime + "修改为" + _positiveTime + ";");
            }

            extCommonService.checkMemberApplyData(record);

            if(StringUtils.isNotBlank(record.getRemark())){
                _remark.append(record.getRemark());
            }

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId);
            memberApplyService.updateByExampleSelective(userId, record, example);

            applyApprovalLogService.add(userId,
                    _memberApply.getPartyId(), _memberApply.getBranchId(), userId,
                    loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "修改",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, _remark.toString());

            logger.info(addLog(LogConstants.LOG_MEMBER, "修改党员发展信息"));
        }

        return success(FormUtils.SUCCESS);
    }

    // 申请不通过
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_deny(Integer[] ids, String remark, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        for (Integer userId : ids) {
            checkVerityAuth(userId);
        }

        memberApplyOpService.apply_deny(ids, remark);

        return success(FormUtils.SUCCESS);
    }

    // 申请通过
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_pass(Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        for (Integer userId : ids) {
            checkVerityAuth(userId);
        }

        memberApplyOpService.apply_pass(ids, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_active")
    public String apply_active() {

        return "member/memberApply/apply_active";
    }

    // 申请通过 成为积极分子
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_active", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_active(Integer[] ids, String _activeTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        Date activeTime = DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD);
        for (Integer userId : ids) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth(userId);
            MemberApply memberApply = verifyAuth.entity;

            if (memberApply.getApplyTime() == null) {
                return failed(memberApply.getUser().getRealname() + "的提交书面申请书时间为空，请修改或退回。");
            }

            memberApply.setActiveTime(activeTime);
            extCommonService.checkMemberApplyData(memberApply);

            /*if (activeTime.before(memberApply.getApplyTime())) {
                return failed("确定为入党积极分子时间不能早于提交书面申请书时间");
            }*/
        }

        memberApplyOpService.apply_active(ids, activeTime, loginUser.getId());

        return success();
    }

    //@RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_active_contact")
    public String apply_active_contact(Integer[] ids, ModelMap modelMap) {

        byte inSchool = 1; // 默认校内
        if(ids.length==1) {
            int userId = ids[0];
            if(!ShiroHelper.isPermitted("memberApply:admin")
                && userId!=ShiroHelper.getCurrentUserId()){
                throw new UnauthorizedException();
            }
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);

            if(memberApply!=null) {
                String _userIds = memberApply.getContactUserIds();
                String _users = memberApply.getContactUsers();
                if (StringUtils.isBlank(_userIds)
                        && StringUtils.isNotBlank(_users)) {
                    inSchool = 0;
                    if (StringUtils.isNotBlank(_users)) {
                        String[] users = _users.split(",");
                        modelMap.put("users", users);
                    }
                } else {

                    Set<Integer> userIds = NumberUtils.toIntSet(_userIds, ",");
                    modelMap.put("userIds", userIds.toArray());
                }
            }
        }else{
            ShiroHelper.checkPermission("memberApply:admin");
        }
        modelMap.put("inSchool", inSchool);

        return "member/memberApply/apply_active_contact";
    }

    // 提交 确定培养联系人
    //@RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_active_contact", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_active_contact(Integer[] ids, Integer[] userIds,
                                       String[] users, HttpServletRequest request) {

        if(ids.length==1) {
            int userId = ids[0];
            if(!ShiroHelper.isPermitted("memberApply:admin")
                && userId!=ShiroHelper.getCurrentUserId()){
                throw new UnauthorizedException();
            }
        }else{
            ShiroHelper.checkPermission("memberApply:admin");
        }

        String contactUsers = memberApplyOpService.apply_active_contact(ids, userIds, users);
        Map<String, Object> resultMap = success();
        resultMap.put("contactUsers", contactUsers);

        // 添加时赋值
        if(ArrayUtils.getLength(userIds)>0 && userIds[0]!=null) {
            if(userIds.length==2 && userIds[1]!=null){
                if(userIds[0].intValue()==userIds[1]){
                    return failed("不可选择相同的培养联系人");
                }
            }
            resultMap.put("contactUserIds", StringUtils.join(userIds, ","));
        }
        return resultMap;
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_candidate")
    public String apply_candidate() {

        return "member/memberApply/apply_candidate";
    }

    // 提交 确定为发展对象
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_candidate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_candidate(Integer[] ids, String _candidateTime,
                                  String _candidateTrainStartTime, String _candidateTrainEndTime,
                                  @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_candidate(ids, _candidateTime,
                _candidateTrainStartTime, _candidateTrainEndTime, loginUser.getId());

        return success();
    }

    // 审核 确定为发展对象
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_candidate_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_candidate_check(Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_candidate_check(ids, loginUser.getId());

        return success();
    }


    //@RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_candidate_sponsor")
    public String apply_candidate_sponsor(Integer[] ids, ModelMap modelMap) {

        if(ids.length==1) {

            int userId = ids[0];
            if(!ShiroHelper.isPermitted("memberApply:admin")
                && userId!=ShiroHelper.getCurrentUserId()){
                throw new UnauthorizedException();
            }
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if(memberApply!=null) {
                SponsorBean bean = SponsorBean.toBean(memberApply.getSponsorUserIds());
                modelMap.put("bean", bean);
            }
        }else{
            ShiroHelper.checkPermission("memberApply:admin");
        }

        return "member/memberApply/apply_candidate_sponsor";
    }

    // 提交 确定入党介绍人
    //@RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_candidate_sponsor", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_candidate_sponsor(Integer[] ids, String sponsorUserIds, HttpServletRequest request) {

        if(ids.length==1) {
            int userId = ids[0];
            if(!ShiroHelper.isPermitted("memberApply:admin")
                && userId!=ShiroHelper.getCurrentUserId()){
                throw new UnauthorizedException();
            }
        }else{
            ShiroHelper.checkPermission("memberApply:admin");
        }

        SponsorBean bean = SponsorBean.toBean(sponsorUserIds);
        if(bean!=null){
            if(bean.getUserId1()!=null && bean.getUserId2()!=null &&
            bean.getUserId1().intValue()==bean.getUserId2()){
              return failed("不可选择相同的入党介绍人");
            }
        }

        String sponsorUsers = memberApplyOpService.apply_candidate_sponsor(ids, sponsorUserIds);
        Map<String, Object> resultMap = success();
        resultMap.put("sponsorUsers", sponsorUsers);
        resultMap.put("sponsorUserIds", sponsorUserIds);

        return resultMap;
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_plan")
    public String apply_plan() {

        return "member/memberApply/apply_plan";
    }

    //提交 列入发展计划
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_plan", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_plan(Integer[] ids, String _planTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_plan(ids, _planTime, loginUser.getId());

        return success();
    }

    //审核 列入发展计划
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_plan_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_plan_check(Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_plan_check(ids, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_draw")
    public String apply_draw() {

        return "member/memberApply/apply_draw";
    }

    //提交 领取志愿书
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_draw(Integer[] ids, String _drawTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_draw(ids, _drawTime, loginUser.getId());

        return success();
    }

    //组织部管理员审核 预备党员 , 在领取志愿书模块， 显示将分配的志愿书编号段
    @RequiresPermissions(RoleConstants.PERMISSION_PARTYVIEWALL)
    @RequestMapping(value = "/apply_grow_od_check")
    public String apply_grow_od_check(Integer[] ids, ModelMap modelMap) {


        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdIn(Arrays.asList(ids))
                .andStageEqualTo(OwConstants.OW_APPLY_STAGE_DRAW)
                .andGrowStatusIsNull();
        // 实际需要分配的对象
        List<MemberApply> memberApplys = memberApplyMapper.selectByExample(example);
        int count = memberApplys.size();
        if (count == 1) {
            modelMap.put("memberApply", memberApplys.get(0));
        }

        List<Integer> idList = new ArrayList<>();
        for (MemberApply memberApply : memberApplys) {
            idList.add(memberApply.getUserId());
        }
        modelMap.put("ids", StringUtils.join(idList, ","));  // 实际需要分配的对象ID

        List<ApplySn> assignApplySnList = applySnService.getAssignApplySnList(count);
        int size = assignApplySnList.size();
        if (size > 0) {
            modelMap.put("startSn", assignApplySnList.get(0));
        }
        if (size > 1) {
            modelMap.put("endSn", assignApplySnList.get(size - 1));
        }

        modelMap.put("assignCount", size);
        modelMap.put("totalCount", count);

        return "member/memberApply/apply_grow_od_check";
    }

    //组织部管理员审核 预备党员 , 在领取志愿书模块
    @RequiresPermissions(RoleConstants.PERMISSION_PARTYVIEWALL)
    @RequestMapping(value = "/apply_grow_od_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_grow_od_check(Integer[] ids,
                                      Integer startSnId,
                                      Integer endSnId,
                                      @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow_od_check(ids, startSnId, endSnId, loginUser.getId());

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_grow")
    public String apply_grow() {

        return "member/memberApply/apply_grow";
    }

    //党支部提交 预备党员， 在组织部审核之后
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_grow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_grow(Integer[] ids, String _growTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow(ids, _growTime, loginUser.getId());

        return success();
    }

    //分党委审核 预备党员
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_grow_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_grow_check(Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow_check(ids, loginUser.getId());

        return success();
    }

    //@RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_grow_contact")
    public String apply_grow_contact(Integer[] ids, ModelMap modelMap) {

        if(ids.length==1) {

            int userId = ids[0];
            if(!ShiroHelper.isPermitted("memberApply:admin")
                && userId!=ShiroHelper.getCurrentUserId()){
                throw new UnauthorizedException();
            }
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if(memberApply!=null) {
                SponsorBean bean = SponsorBean.toBean(memberApply.getGrowContactUserIds());
                modelMap.put("bean", bean);
            }
        }else{
            ShiroHelper.checkPermission("memberApply:admin");
        }

        return "member/memberApply/apply_grow_contact";
    }

    // 提交 确定培养联系人
    //@RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_grow_contact", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_grow_contact(Integer[] ids, String growContactUserIds, HttpServletRequest request) {

        if(ids.length==1) {
            int userId = ids[0];
            if(!ShiroHelper.isPermitted("memberApply:admin")
                && userId!=ShiroHelper.getCurrentUserId()){
                throw new UnauthorizedException();
            }
        }else{
            ShiroHelper.checkPermission("memberApply:admin");
        }

        SponsorBean bean = SponsorBean.toBean(growContactUserIds);
        if(bean!=null){
            if(bean.getUserId1()!=null && bean.getUserId2()!=null &&
            bean.getUserId1().intValue()==bean.getUserId2()){
              return failed("不可选择相同的培养联系人");
            }
        }

        String growContactUsers = memberApplyOpService.apply_grow_contact(ids, growContactUserIds);
        Map<String, Object> resultMap = success();
        resultMap.put("growContactUsers", growContactUsers);
        resultMap.put("growContactUserIds", growContactUserIds);

        return resultMap;
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_positive")
    public String apply_positive() {

        return "member/memberApply/apply_positive";
    }

    //提交 正式党员
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_positive", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_positive(Integer[] ids, String _positiveTime, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive(ids, _positiveTime, loginUser.getId());

        return success();
    }

    //审核 正式党员
    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/apply_positive_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check(Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive_check(ids, loginUser.getId());

        return success();
    }

    //组织部管理员审核 正式党员， 在预备党员模块
    @RequiresPermissions(RoleConstants.PERMISSION_PARTYVIEWALL)
    @RequestMapping(value = "/apply_positive_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check2(Integer[] ids, @CurrentUser SysUserView loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive_check2(ids, loginUser.getId());

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApply_back")
    public String memberApply_back() {

        return "member/memberApply/memberApply_back";
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/memberApply_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_back(@CurrentUser SysUserView loginUser,
                                   Integer[] ids, byte stage,
                                   @RequestParam(required = false, defaultValue = "1") boolean applySnReuse,
                                   String reason) {

        memberApplyOpService.memberApply_back(ids, stage, reason, loginUser.getId(), applySnReuse);

        logger.info(addLog(LogConstants.LOG_MEMBER, "退回党员发展申请：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    //移除记录（只允许移除未发展的）
    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApply_remove")
    public String memberApply_remove() {

        return "member/memberApply/memberApply_remove";
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/memberApply_remove", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_remove(Integer[] ids,
                                     Boolean isRemove,
                                     @RequestParam(required = false, defaultValue = "1") boolean applySnReuse,
                                     String reason, HttpServletRequest request) {

        memberApplyOpService.memberApply_remove(ids, BooleanUtils.isTrue(isRemove), reason, applySnReuse);
        return success();
    }


    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/memberApply_batchApply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_batchApply(Integer[] ids) {

        memberApplyOpService.batchApply(ids);
        return success();
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/memberApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_batchDel(Integer[] ids) {

        memberApplyOpService.batchDel(ids);
        return success();
    }

    // 更换学工号
    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApply_changeCode")
    public String memberApply_changeCode(int userId, ModelMap modelMap) {

        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        modelMap.put("memberApply", memberApply);

        return "member/memberApply/memberApply_changeCode";
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/memberApply_changeCode", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_changeCode(int userId, int newUserId, String remark) {

        memberApplyService.changeCode(userId, newUserId, remark);

        return success(FormUtils.SUCCESS);
    }

    // 更换联系党组织
    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApply_changeParty")
    public String memberApply_changeParty(Integer[] ids, ModelMap modelMap) {
        if (ids.length == 1) {
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(ids[0]);
            modelMap.put("memberApply", memberApply);
        }
        modelMap.put("count", ids.length);
        return "member/memberApply/memberApply_changeParty";
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/memberApply_changeParty", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_changeParty(Integer[] ids, int partyId, Integer branchId, String remark) {

        memberApplyService.changeParty(ids, partyId, branchId, remark);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApply_search")
    public String memberApply_search() {

        return "member/memberApply/memberApply_search";
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping(value = "/memberApply_search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_search(int userId) {

        String realname = "";
        String party = "";
        String msg = "";
        Byte userType = null;
        String code = "";
        String status = "";
        SysUserView sysUser = sysUserService.findById(userId);
        if (sysUser == null) {
            msg = "该账号不存在";
        } else {
            code = sysUser.getCode();
            userType = sysUser.getType();
            realname = sysUser.getRealname();
            MemberApply memberApply = memberApplyService.get(userId);
            if (memberApply == null) {
                msg = "该账号不在党员发展库中";
            } else {
                party = PartyHelper.displayParty(memberApply.getPartyId(), memberApply.getBranchId());
                msg += "所在党员发展阶段【" + OwConstants.OW_APPLY_ALLSTAGE_MAP.get(memberApply.getStage()) + "】";
                if (BooleanUtils.isTrue(memberApply.getIsRemove())) {
                    msg += "（已移除）";
                }
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("msg", msg);
        resultMap.put("userType", userType);
        resultMap.put("code", code);
        resultMap.put("realname", realname);
        resultMap.put("party", party);
        resultMap.put("status", status);
        return resultMap;
    }

    @RequiresPermissions("memberApply:admin")
    @RequestMapping("/memberApplyLog")
    public String memberApplyLog(@RequestParam(defaultValue = "1") int cls,
                                 Integer userId,
                                 Integer applyUserId,
                                 String stage,
                                 Integer partyId,
                                 Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("stage", stage);
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (applyUserId != null) {
            modelMap.put("applyUser", sysUserService.findById(applyUserId));
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

    //审核继续培养申请
    @RequestMapping(value = "/memberApply_continue_check", method = RequestMethod.POST)
    @ResponseBody
    public Map memberApply_continue_check(Integer[] ids,
                                          Boolean isPass, @CurrentUser SysUserView loginUser) {

        if (ids != null && ids.length>0) {
            memberApplyOpService.continue_check(isPass,ids,loginUser.getUserId());
        }
        return success(FormUtils.SUCCESS);
    }

    public void memberApply_export(MemberApplyViewExample example, HttpServletResponse response) {

        List<MemberApplyView> memberApplys = memberApplyViewMapper.selectByExample(example);
        long rownum = memberApplyViewMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"账号|100", "所在党组织|300|left", "所属党支部|300|left", "类型|150"};
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
                    memberApply.getUserType() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = String.format("申请入党人员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

    //导出发展中的学生党员
    @RequestMapping("/memberDevelope_export")
    public void memberDevelope_export(HttpServletResponse response) {

        Byte[] a = {0, 2, 3, 4, 5};

        List<Byte> stages = new ArrayList<>();
        for (Byte stage : a) {
            stages.add(stage);
        }
        MemberApplyViewExample example = new MemberApplyViewExample();
        example.createCriteria().andStageIn(stages).andUserTypeIn(Arrays.asList(SystemConstants.USER_TYPE_BKS,
                    SystemConstants.USER_TYPE_SS, SystemConstants.USER_TYPE_BS));
        List<MemberApplyView> records = memberApplyViewMapper.selectByExample(example);
        int rownum = records.size();

        String[] titles = {"学号|100", "所在党组织|100", "所属党支部|100", "提交书面申请时间|100", "信息填报时间|100", "备注|100",
                "阶段|100", "不通过原因|100", "通过时间|100","确定为入党积极分子时间|100", "参加培训开始时间|100", "参加培训结束时间|100","积极分子结业考试成绩|100",
                "确定为发展对象时间|100", "参加培训开始时间|100","参加培训结束时间|100", "发展对象结业考试成绩|100", "发展对象审核状态|100",
                "列入发展计划时间|100", "列入计划审核状态|100", "领取志愿书时间|100", "志愿书领取审核状态|100", "关联志愿书编码|100", "志愿书编码|100",
                "发展公示|100", "入党时间|100", "发展审核状态|100", "转正公示|100", "转正时间|100", "转正审核状态|100","是否移除|100", "创建时间|100",
                "身份证号|150", "专业|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberApplyView record = records.get(i);

            SysUserView uv = CmTag.getUserById(record.getUserId());

            String[] values = {
                    uv.getCode(),//学号
                    String.valueOf(record.getPartyId()),//所在党组织
                    String.valueOf(record.getBranchId() == null ? "" : record.getBranchId()),//所属党支部
                    DateUtils.formatDate(record.getApplyTime(), DateUtils.YYYYMMDD_DOT),//提交书面申请时间
                    DateUtils.formatDate(record.getFillTime(), DateUtils.YYYYMMDD_DOT),//信息填报时间
                    record.getRemark(),//备注
                    String.valueOf(record.getStage()),//阶段
                    record.getReason(),//不通过原因
                    DateUtils.formatDate(record.getPassTime(), DateUtils.YYYYMMDD_DOT),//通过时间
                    DateUtils.formatDate(record.getActiveTime(), DateUtils.YYYYMMDD_DOT),//确定为入党积极分子时间
                    DateUtils.formatDate(record.getActiveTrainStartTime(), DateUtils.YYYYMMDD_DOT),//参加培训开始时间，成为入党积极分子之后参加的培训
                    DateUtils.formatDate(record.getActiveTrainEndTime(), DateUtils.YYYYMMDD_DOT),//参加培训结束时间，成为入党积极分子之后参加的培训
                    record.getActiveGrade(),//积极分子结业考试成绩
                    DateUtils.formatDate(record.getCandidateTime(), DateUtils.YYYYMMDD_DOT),//确定为发展对象时间
                    DateUtils.formatDate(record.getCandidateTrainStartTime(), DateUtils.YYYYMMDD_DOT),//参加培训开始时间
                    DateUtils.formatDate(record.getCandidateTrainEndTime(), DateUtils.YYYYMMDD_DOT),//参加培训结束时间
                    record.getCandidateGrade(),//发展对象结业考试成绩
                    record.getCandidateStatus() == null ? "" : String.valueOf(record.getCandidateStatus()),//发展对象审核状态
                    DateUtils.formatDate(record.getPlanTime(), DateUtils.YYYYMMDD_DOT),//列入发展计划时间，党支部填写，分党委审批，有固定开放时间；组织部也可给分党委单独设定开放时间
                    record.getPlanStatus() == null ? "" : String.valueOf(record.getPlanStatus()),//列入计划审核状态，0未审核，1已审核
                    DateUtils.formatDate(record.getDrawTime(), DateUtils.YYYYMMDD_DOT),//领取志愿书时间，由党支部填写、分党委审核
                    record.getDrawStatus() == null ? "" : String.valueOf(record.getDrawStatus()),//志愿书领取审核状态， 0未审核 1已审核
                    record.getApplySnId() == null ? "" : String.valueOf(record.getApplySnId()),//关联志愿书编码
                    record.getApplySn(),//志愿书编码
                    record.getGrowPublicId() == null ? "" : String.valueOf(record.getGrowPublicId()),//发展公示
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYYMMDD_DOT),//入党时间，由党支部填写、分党委审核，党总支、直属党支部需增加组织部审核
                    record.getGrowStatus() == null ? "" : String.valueOf(record.getGrowStatus()),//发展审核状态， 0未审核 1分党委审核 2 组织部审核
                    record.getPositivePublicId() == null ? "" : String.valueOf(record.getPositivePublicId()),//转正公示
                    record.getPositiveTime() == null ? "" : String.valueOf(record.getPositiveTime()),//转正时间
                    record.getPositiveStatus() == null ? "" : String.valueOf(record.getPositiveStatus()),//转正审核状态， 0未审核 1分党委审核 2组织部审核
                    String.valueOf(record.getIsRemove()? '是' : '否'),//是否移除，（针对未发展的申请，可以移除）
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYYMMDD_DOT),//创建时间
                    uv.getIdcard() == null ? "" : uv.getIdcard(),//身份证
                    uv.getSpecialty() == null ? "" : uv.getSpecialty(),//专业
            };
            valuesList.add(values);
        }
        String fileName = String.format("学生发展对象(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
