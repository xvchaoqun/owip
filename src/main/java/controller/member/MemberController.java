package controller.member;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.member.*;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import interceptor.OrderParam;
import mixin.MemberMixin;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class MemberController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("member:list")
    @RequestMapping("/member/search")
    public String member_search() {

        return "member/member/member_search";
    }

    @RequiresPermissions("member:list")
    @RequestMapping(value = "/member/search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_search(int userId) {

        String realname = "";
        String unit = "";
        String msg = "";
        Byte userType = null;
        String code = "";
        String status = "";
        SysUserView sysUser = sysUserService.findById(userId);
        if (sysUser == null) {
            msg = "该用户不存在";
        } else {
            code = sysUser.getCode();
            userType = sysUser.getType();
            realname = sysUser.getRealname();
            unit = extService.getUnit(userId);
            Member member = memberService.get(userId);
            if (member == null) {
                msg = "该用户不是党员";
                MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
                if (memberApply != null && memberApply.getStage() > OwConstants.OW_APPLY_STAGE_DENY) {

                    msg += "；已进入党员发展阶段【" + OwConstants.OW_APPLY_STAGE_MAP.get(memberApply.getStage()) + "】";
                    if (BooleanUtils.isTrue(memberApply.getIsRemove())) {
                        msg += "（已移除）";
                    }
                }
            } else {
                Integer partyId = member.getPartyId();
                Integer branchId = member.getBranchId();
                Party party = partyService.findAll().get(partyId);
                msg = party.getName();
                if (branchId != null) {
                    Branch branch = branchService.findAll().get(branchId);
                    if (branch != null) msg += "-" + branch.getName();
                }

                // 查询状态
                if (member.getStatus() == MemberConstants.MEMBER_STATUS_NORMAL) {
                    status = "正常";
                } else if (member.getStatus() == MemberConstants.MEMBER_STATUS_TRANSFER) {
                    status = "已转出";
                } else if (member.getStatus() == MemberConstants.MEMBER_STATUS_QUIT) {
                    status = "已出党";
                }

                if (member.getType() == MemberConstants.MEMBER_TYPE_TEACHER) {

                    MemberView memberView = iMemberMapper.getMemberView(userId);
                    if (memberView.getIsRetire())
                        status = "已退休";
                }

                MemberStay memberStay = memberStayService.get(userId, MemberConstants.MEMBER_STAY_TYPE_ABROAD);
                if (memberStay != null) {
                    if (memberStay.getStatus() == MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY) {
                        status = "出国暂留申请已完成审批";
                    } else if (memberStay.getStatus() >= MemberConstants.MEMBER_STAY_STATUS_APPLY)
                        status = "已申请出国暂留，但还未审核通过";
                }
            }
        }


        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("msg", msg);
        resultMap.put("userType", userType);
        resultMap.put("code", code);
        resultMap.put("realname", realname);
        resultMap.put("unit", unit);
        resultMap.put("status", status);
        return resultMap;
    }

    // for test 后台数据库中导入党员数据后，需要同步信息、更新状态
    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping(value = "/member_dbUpdate")
    @ResponseBody
    public Map dbUpdate() {

        List<Member> members = memberMapper.selectByExample(new MemberExample());
        for (Member member : members) {
            memberService.dbUpdate(member.getUserId());
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(SystemConstants.PERMISSION_PARTYVIEWALL)
    @RequestMapping("/member_import")
    public String member_import(boolean inSchool,
                                ModelMap modelMap) {

        modelMap.put("inSchool", inSchool);
        return "member/member/member_import";
    }

    // 导入校内账号的党员信息
    @RequiresPermissions(SystemConstants.PERMISSION_PARTYVIEWALL)
    @RequestMapping(value = "/member_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_import(boolean inSchool, HttpServletRequest request) throws InvalidFormatException, IOException {

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

        Map<String, Byte> politicalStatusMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : MemberConstants.MEMBER_POLITICAL_STATUS_MAP.entrySet()) {

            politicalStatusMap.put(entry.getValue(), entry.getKey());
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<String, Object> resultMap = null;

        if (inSchool) {
            resultMap = importInSchoolMember(xlsRows, runPartyMap, runBranchMap, politicalStatusMap);
        } else {
            resultMap = importOutSchoolMember(xlsRows, runPartyMap, runBranchMap, politicalStatusMap);
        }

        return resultMap;
    }

    // 导入校内账号的党员信息
    private Map<String, Object> importInSchoolMember(List<Map<Integer, String>> xlsRows,
                                                     Map<String, Party> runPartyMap,
                                                     Map<String, Branch> runBranchMap,
                                                     Map<String, Byte> politicalStatusMap) {

        Date now = new Date();
        List<Member> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            Member record = new Member();

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
                throw new OpException("第{0}行分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null) {
                throw new OpException("第{0}行分党委编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(party.getId());
            if (!partyService.isDirectBranch(party.getId())) {

                String branchCode = StringUtils.trim(xlsRow.get(4));
                if (StringUtils.isBlank(branchCode)) {
                    throw new OpException("第{0}行党支部编码为空", row);
                }
                Branch branch = runBranchMap.get(branchCode);
                if (branch == null) {
                    throw new OpException("第{0}行党支部编码[{1}]不存在", row, partyCode);
                }
                record.setBranchId(branch.getId());
            }

            String _politicalStatus = StringUtils.trimToNull(xlsRow.get(6));
            if (StringUtils.isBlank(_politicalStatus)) {
                throw new OpException("第{0}行党籍状态为空", row);
            }
            Byte politicalStatus = politicalStatusMap.get(_politicalStatus);
            if (politicalStatus == null) {
                throw new OpException("第{0}行党籍状态[{1}]有误", row, _politicalStatus);
            }
            record.setPoliticalStatus(politicalStatus);

            int col = 7;
            record.setTransferTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setSponsor(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setGrowBranch(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPositiveBranch(StringUtils.trimToNull(xlsRow.get(col++)));

            record.setPartyPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartyReward(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setOtherReward(StringUtils.trimToNull(xlsRow.get(col++)));

            record.setCreateTime(now);
            records.add(record);
        }

        int successCount = memberService.batchImportInSchool(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    // 导入校外账号的党员信息
    private Map<String, Object> importOutSchoolMember(List<Map<Integer, String>> xlsRows,
                                                      Map<String, Party> runPartyMap,
                                                      Map<String, Branch> runBranchMap,
                                                      Map<String, Byte> politicalStatusMap) {

        Date now = new Date();
        List<Member> records = new ArrayList<>();
        List<TeacherInfo> teacherInfos = new ArrayList<>();
        List<SysUserInfo> sysUserInfos = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            Member record = new Member();
            TeacherInfo teacherInfo = new TeacherInfo();
            SysUserInfo sysUserInfo = new SysUserInfo();

            int col = 0;
            String userCode = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(userCode)) {
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            int userId = uv.getId();
            if (uv == null) {
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            SysUserView sysUser = sysUserService.findById(userId);
            Byte source = sysUser.getSource();
            if (source == SystemConstants.USER_SOURCE_BKS || source == SystemConstants.USER_SOURCE_YJS
                    || source == SystemConstants.USER_SOURCE_JZG) {
                throw new OpException("第{0}行学工号[{1}]是学校账号", row, userCode);
            }
            record.setUserId(userId);
            teacherInfo.setUserId(userId);
            sysUserInfo.setUserId(userId);

            String realname = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(realname)) {
                throw new OpException("第{0}行姓名为空", row);
            }
            sysUserInfo.setRealname(realname);

            String gender = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.contains(gender, "男")) {
                sysUserInfo.setGender(SystemConstants.GENDER_MALE);
            } else if (StringUtils.contains(gender, "女")) {
                sysUserInfo.setGender(SystemConstants.GENDER_FEMALE);
            } else {
                sysUserInfo.setGender(SystemConstants.GENDER_UNKNOWN);
            }

            sysUserInfo.setBirth(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            sysUserInfo.setNativePlace(StringUtils.trimToNull(xlsRow.get(col++)));

            sysUserInfo.setNation(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setIdcard(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setMobile(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setCountry(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setFromType(StringUtils.trimToNull(xlsRow.get(col++))); // 学员结构，本校、境内、京外等

            sysUserInfo.setUnit(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setEducation(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setDegree(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setDegreeTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            teacherInfo.setMajor(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setSchool(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setSchoolType(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setDegreeSchool(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setArriveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            teacherInfo.setAuthorizedType(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setStaffType(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setStaffStatus(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setPostClass(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setMainPostLevel(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setOnJob(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setProPost(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setProPostLevel(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setTitleLevel(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setManageLevel(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setOfficeLevel(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setPost(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setPostLevel(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setTalentType(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setTalentTitle(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setAddress(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setMaritalStatus(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setEmail(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setHomePhone(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setIsRetire(StringUtils.equals(xlsRow.get(col++), "是"));
            teacherInfo.setRetireTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

            teacherInfo.setIsHonorRetire(StringUtils.equals(xlsRow.get(col++), "是"));

            String _politicalStatus = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_politicalStatus)) {
                throw new OpException("第{0}行党籍状态为空", row);
            }
            Byte politicalStatus = politicalStatusMap.get(_politicalStatus);
            if (politicalStatus == null) {
                throw new OpException("第{0}行党籍状态[{1}]有误", row, _politicalStatus);
            }
            record.setPoliticalStatus(politicalStatus);

            String partyCode = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null) {
                throw new OpException("第{0}行分党委编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(party.getId());

            col++;

            if (!partyService.isDirectBranch(party.getId())) {

                String branchCode = StringUtils.trim(xlsRow.get(col++));
                if (StringUtils.isBlank(branchCode)) {
                    throw new OpException("第{0}行党支部编码为空", row);
                }
                Branch branch = runBranchMap.get(branchCode);
                if (branch == null) {
                    throw new OpException("第{0}行党支部编码[{1}]不存在", row, partyCode);
                }
                record.setBranchId(branch.getId());
            }

            col++;
            record.setTransferTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

            record.setSponsor(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setGrowBranch(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPositiveBranch(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartyPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartyReward(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setOtherReward(StringUtils.trimToNull(xlsRow.get(col++)));

            record.setCreateTime(now);

            records.add(record);
            teacherInfos.add(teacherInfo);
            sysUserInfos.add(sysUserInfo);
        }

        int successCount = memberService.batchImportOutSchool(records, teacherInfos, sysUserInfos);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    //@RequiresPermissions("member:edit")
    @RequestMapping(value = "/member_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_au(@CurrentUser SysUserView loginUser, Member record, String _applyTime, String _activeTime, String _candidateTime,
                            String _growTime, String _positiveTime, String _transferTime, String reason, HttpServletRequest request) {

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        if (partyId != null && branchId == null) {
            if (!partyService.isDirectBranch(partyId)) {
                return failed("只有直属党支部或党支部可以添加党员");
            }
        }

        //===========权限
        Integer loginUserId = loginUser.getId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin && branchId != null) { // 只有支部管理员或分党委管理员可以添加党员
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }
            if (!isAdmin) throw new UnauthorizedException();
        }

        Integer userId = record.getUserId();

        if (StringUtils.isNotBlank(_applyTime)) {
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_activeTime)) {
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_candidateTime)) {
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_growTime)) {
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_positiveTime)) {
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_transferTime)) {
            record.setTransferTime(DateUtils.parseDate(_transferTime, DateUtils.YYYY_MM_DD));
        }
        SysUserView sysUser = sysUserService.findById(record.getUserId());
        Member member = memberService.get(userId);
        if (member == null) {
            SecurityUtils.getSubject().checkPermission("member:add");

            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if (memberApply != null && memberApply.getStage() >= OwConstants.OW_APPLY_STAGE_INIT) {
                return failed("该用户已经提交了入党申请[当前审批阶段："
                        + OwConstants.OW_APPLY_STAGE_MAP.get(memberApply.getStage())
                        + ((BooleanUtils.isTrue(memberApply.getIsRemove())) ? "（已移除）" : "")
                        + "]，不可以直接添加。");
            }

            record.setStatus(MemberConstants.MEMBER_STATUS_NORMAL); // 正常
            record.setCreateTime(new Date());
            record.setSource(MemberConstants.MEMBER_SOURCE_ADMIN); // 后台添加的党员
            memberService.add(record);

            memberService.addModify(record.getUserId(), "后台添加");

            logger.info(addLog(LogConstants.LOG_MEMBER,
                    "添加党员信息表：%s %s %s %s, 添加原因：%s", sysUser.getId(), sysUser.getRealname(),
                    partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(), reason));
        } else {

            SecurityUtils.getSubject().checkPermission("member:edit");

            record.setPoliticalStatus(null); // 不能修改党籍状态
            memberService.updateByPrimaryKeySelective(record, reason);

            logger.info(addLog(LogConstants.LOG_MEMBER,
                    "更新党员信息表：%s %s %s %s, 更新原因：%s", sysUser.getId(), sysUser.getRealname(),
                    partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    reason));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("member:edit")
    @RequestMapping("/member_au")
    public String member_au(Integer userId, Integer partyId, Integer branchId, ModelMap modelMap) {

        Member member = null;
        if (userId != null) {
            SecurityUtils.getSubject().checkPermission("member:edit");

            member = memberMapper.selectByPrimaryKey(userId);
            partyId = member.getPartyId();
            branchId = member.getBranchId();
            modelMap.put("sysUser", sysUserService.findById(userId));
        } else {
            SecurityUtils.getSubject().checkPermission("member:add");
        }

        Map<Integer, Branch> branchMap = branchService.findAll();
        if (branchId != null && partyId == null) { // 给支部添加党员
            Branch branch = branchMap.get(branchId);
            partyId = branch.getPartyId();
        }

        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        if (userId == null && partyId != null && branchId == null) { // 给直属党支部添加党员
            if (!partyService.isDirectBranch(partyId)) {
                throw new OpException("只有直属党支部或党支部可以添加党员");
            }
        }

        modelMap.put("member", member);

        return "member/member/member_au";
    }

    // 后台添加预备党员，可能需要加入党员发展流程（预备党员阶段）
    @RequiresPermissions("member:edit")
    @RequestMapping(value = "/snyc_memberApply", method = RequestMethod.POST)
    @ResponseBody
    public Map snyc_memberApply(int userId) {

        Member member = memberService.get(userId);
        Integer partyId = member.getPartyId();
        Integer branchId = member.getBranchId();
        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin && branchId != null) { // 只有支部管理员或分党委管理员可以操作
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }
            if (!isAdmin) throw new UnauthorizedException();
        }

        memberApplyService.addOrChangeToGrowApply(userId);

        return success(FormUtils.SUCCESS);
    }

    // 修改党籍状态
    @RequiresPermissions("member:modifyStatus")
    @RequestMapping("/member_modify_status")
    public String member_modify_status(int userId, ModelMap modelMap) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        modelMap.put("member", member);

        return "member/member/member_modify_status";
    }

    @RequiresPermissions("member:modifyStatus")
    @RequestMapping(value = "/member_modify_status", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_modify_status(int userId, byte politicalStatus, String remark) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        if (member.getPoliticalStatus() != politicalStatus) {
            memberService.addModify(userId, "修改党籍状态");
            memberService.modifyStatus(userId, politicalStatus, remark);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/member_changeBranch")
    public String member_changeBranch(@CurrentUser SysUserView loginUser, @RequestParam(value = "ids[]") Integer[] ids,
                                      int partyId, ModelMap modelMap) {

        // 判断是分党委管理员
        if (!PartyHelper.isPresentPartyAdmin(loginUser.getId(), partyId)) {
            throw new UnauthorizedException();
        }

        modelMap.put("ids", StringUtils.join(ids, ","));

        Integer _partyId = null;
        for (Integer userId : ids) {
            Member member = memberService.get(userId);
            if (_partyId == null) _partyId = member.getPartyId();
            if (_partyId != null && _partyId.intValue() != member.getPartyId()) {
                throw new OpException("只允许在同一个分党委内部进行批量转移。");
            }
            if (partyService.isDirectBranch(member.getPartyId())) {
                throw new OpException("直属党支部不能进行内部转移。");
            }
        }

        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("party", partyMap.get(partyId));

        return "member/member/member_changeBranch";
    }

    // 批量分党委内部转移
    @RequestMapping(value = "/member_changeBranch", method = RequestMethod.POST)
    @ResponseBody
    public Map member_changeBranch(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                   @RequestParam(value = "ids[]") Integer[] ids,
                                   int partyId, // 用于校验
                                   int branchId,
                                   ModelMap modelMap) {

        // 判断是分党委管理员
        if (!PartyHelper.isPresentPartyAdmin(loginUser.getId(), partyId)) {
            throw new UnauthorizedException();
        }

        if (null != ids) {
            memberService.changeBranch(ids, partyId, branchId);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量分党委内部转移：%s, %s, %s", StringUtils.join(ids, ","), partyId, branchId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(SystemConstants.PERMISSION_PARTYVIEWALL)
    @RequestMapping("/member_changeParty")
    public String member_changeParty() {

        //modelMap.put("ids", StringUtils.join( ids, ","));

        return "member/member/member_changeParty";
    }

    // 批量校内组织关系转移
    @RequiresPermissions(SystemConstants.PERMISSION_PARTYVIEWALL)
    @RequestMapping(value = "/member_changeParty", method = RequestMethod.POST)
    @ResponseBody
    public Map member_changeParty(HttpServletRequest request,
                                  @RequestParam(value = "ids[]") Integer[] ids,
                                  int partyId,
                                  Integer branchId,
                                  ModelMap modelMap) {

        if (null != ids) {
            memberService.changeParty(ids, partyId, branchId);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量校内组织关系转移：%s, %s, %s", ids, partyId, branchId));
        }
        return success(FormUtils.SUCCESS);
    }

    /*@RequiresRoles(value = {RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_ODADMIN}, logical = Logical.OR)
    @RequiresPermissions("member:del")
    @RequestMapping(value = "/member_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberService.del(id);
            logger.info(addLog(LogConstants.LOG_MEMBER, "删除党员信息表：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions(SystemConstants.PERMISSION_PARTYVIEWALL)
    @RequestMapping(value = "/member_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {

            List<Member> members = new ArrayList<>();
            for (Integer id : ids) {
                members.add(memberService.get(id));
            }

            memberService.batchDel(ids, true);

            logger.info(addLog(LogConstants.LOG_MEMBER, "批量删除党员：%s", JSONUtils.toString(members, false)));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/member")
    public String member(HttpServletResponse response,
                         Integer userId,
                         Integer partyId,
                         Integer branchId,
                         @RequestParam(required = false, value = "nation") String[] nation,
                         @RequestParam(required = false, value = "nativePlace") String[] nativePlace,

                         // 1 学生 2教职工 3离退休 6已转出学生 7 已转出教职工 10全部
                         Integer cls,
                         ModelMap modelMap) {

        modelMap.put("cls", cls);

        boolean addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        Map memberStudentCount = iMemberMapper.selectMemberStudentCount(addPermits, adminPartyIdList, adminBranchIdList);
        if (memberStudentCount != null) {
            modelMap.putAll(memberStudentCount);
        }

        Map memberTeacherCount = iMemberMapper.selectMemberTeacherCount(addPermits, adminPartyIdList, adminBranchIdList);
        if (memberTeacherCount != null) {
            modelMap.putAll(memberTeacherCount);
        }

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));

        if (nation != null) {
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        if (nativePlace != null) {
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            modelMap.put("selectNativePlaces", selectNativePlaces);
        }

        // 确认默认显示党员人数最多的标签页
        if (cls == null) {
            int studentNormalCount = 0;
            if (memberStudentCount != null) {
                studentNormalCount = ((BigDecimal) memberStudentCount.get("student_normalCount")).intValue();
            }
            int teacherNormalCount = 0;
            int teacherRetireCount = 0;

            if (memberTeacherCount != null) {
                teacherNormalCount = ((BigDecimal) memberTeacherCount.get("teacher_normalCount")).intValue();
                teacherRetireCount = ((BigDecimal) memberTeacherCount.get("teacher_retireCount")).intValue();
            }

            if (studentNormalCount >= teacherNormalCount
                    && studentNormalCount >= teacherRetireCount) {
                cls = 1;
            } else if (teacherNormalCount > studentNormalCount
                    && teacherNormalCount >= teacherRetireCount) {
                cls = 2;
            } else {

                cls = 3;
            }
        }

        modelMap.put("cls", cls);

        // 导出的列名字
        List<String> titles = new ArrayList<>();
        if (cls == 2 || cls == 3 || cls == 7) { // 教师党员
            titles = getTeacherExportTitles();
            if (cls == 3) {
                titles.add(6, "离退休时间|80");
            }

            modelMap.put("teacherEducationTypes", iPropertyMapper.teacherEducationTypes());
            modelMap.put("teacherPostClasses", iPropertyMapper.teacherPostClasses());
            modelMap.put("nations", iPropertyMapper.teacherNations());
            modelMap.put("nativePlaces", iPropertyMapper.teacherNativePlaces());
        } else if (cls == 1 || cls == 6) { // 学生党员

            titles = getStudentExportTitles();

            modelMap.put("studentGrades", iPropertyMapper.studentGrades());
            modelMap.put("studentTypes", iPropertyMapper.studentTypes());
            modelMap.put("nations", iPropertyMapper.studentNations());
            modelMap.put("nativePlaces", iPropertyMapper.studentNativePlaces());
        }else if(cls==10){
            modelMap.put("nations", iPropertyMapper.nations());
            modelMap.put("nativePlaces", iPropertyMapper.nativePlaces());
        }

        modelMap.put("titles", titles);

        return "/member/member/member_page";
    }

    @RequiresPermissions("member:list")
    @RequestMapping("/member_data")
    public void member_data(HttpServletResponse response,
                            @RequestParam(defaultValue = "party") String sort,
                            @OrderParam(required = false, defaultValue = "desc") String order,
                            @RequestParam(defaultValue = "1") int cls,
                            Integer userId,
                            Integer unitId,
                            Integer partyId,
                            Integer branchId,
                            Byte politicalStatus,
                            Byte gender,
                            Byte age,
                            @RequestParam(required = false, value = "nation") String[] nation,
                            @RequestParam(required = false, value = "nativePlace") String[] nativePlace,
                            @RequestDateRange DateRange _growTime,
                            @RequestDateRange DateRange _positiveTime,
                            @RequestDateRange DateRange _outHandleTime,
                            Byte userSource, // 账号来源

                            /**学生党员**/
                            String grade,
                            String studentType,
                            String eduLevel,
                            String eduType,

                            /** 教职工党员**/
                            String education,
                            String postClass,
                            @RequestDateRange DateRange _retireTime,
                            Boolean isHonorRetire,

                            @RequestParam(required = false, defaultValue = "0") int export,
                            @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                            @RequestParam(required = false) Integer[] cols, // 选择导出的列
                            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberViewExample example = new MemberViewExample();
        MemberViewExample.Criteria criteria = example.createCriteria();

        if (StringUtils.equalsIgnoreCase(sort, "party")) {
            example.setOrderByClause(String.format("party_id , branch_id %s, grow_time desc", order));
        } else if (StringUtils.equalsIgnoreCase(sort, "growTime")) {
            example.setOrderByClause(String.format("grow_time %s", order));
        }

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (gender != null) {
            criteria.andGenderEqualTo(gender);
        }
        if (politicalStatus != null) {
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (nation != null) {
            List<String> selectNations = Arrays.asList(nation);
            criteria.andNationIn(selectNations);
        }
        if (nativePlace != null) {
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            criteria.andNativePlaceIn(selectNativePlaces);
        }
        if (age != null) {
            switch (age) {
                case MemberConstants.MEMBER_AGE_20: // 20及以下
                    criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                    break;
                case MemberConstants.MEMBER_AGE_21_30:
                    criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -31))
                            .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                    break;
                case MemberConstants.MEMBER_AGE_31_40:
                    criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -41))
                            .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -31));
                    break;
                case MemberConstants.MEMBER_AGE_41_50:
                    criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -51))
                            .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -41));
                    break;
                case MemberConstants.MEMBER_AGE_51: // 51及以上
                    criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -51));
                    break;
                case MemberConstants.MEMBER_AGE_0:
                    criteria.andBirthIsNull();
                    break;
            }
        }

        if (StringUtils.isNotBlank(grade)) {
            criteria.andGradeEqualTo(grade);
        }
        if (StringUtils.isNotBlank(studentType)) {
            criteria.andStudentTypeEqualTo(studentType);
        }
        if (StringUtils.isNotBlank(eduLevel)) {
            criteria.andEduLevelLike(SqlUtils.like(eduLevel));
        }
        if (StringUtils.isNotBlank(eduType)) {
            criteria.andEduTypeLike(SqlUtils.like(eduType));
        }

        if (StringUtils.isNotBlank(education)) {
            criteria.andEducationEqualTo(education);
        }
        if (StringUtils.isNotBlank(postClass)) {
            criteria.andPostClassEqualTo(postClass);
        }

        if (_retireTime.getStart() != null) {
            criteria.andRetireTimeGreaterThanOrEqualTo(_retireTime.getStart());
        }

        if (_retireTime.getEnd() != null) {
            criteria.andRetireTimeLessThanOrEqualTo(_retireTime.getEnd());
        }

        if (isHonorRetire != null) {
            criteria.andIsHonorRetireEqualTo(isHonorRetire);
        }

        if (_growTime.getStart() != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
        }

        if (_growTime.getEnd() != null) {
            criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
        }
        if (_positiveTime.getStart() != null) {
            criteria.andPositiveTimeGreaterThanOrEqualTo(_positiveTime.getStart());
        }
        if (_positiveTime.getEnd() != null) {
            criteria.andPositiveTimeLessThanOrEqualTo(_positiveTime.getEnd());
        }
        if (_outHandleTime.getStart() != null) {
            criteria.andOutHandleTimeGreaterThanOrEqualTo(_outHandleTime.getStart());
        }
        if (_outHandleTime.getEnd() != null) {
            criteria.andOutHandleTimeLessThanOrEqualTo(_outHandleTime.getEnd());
        }

        /*
           1 学生 2教职工 3离退休 6已转出学生 7 已转出教职工 10全部
         */
        switch (cls) {
            case 1:
                criteria.andTypeEqualTo(MemberConstants.MEMBER_TYPE_STUDENT)
                        .andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
                break;
            case 2:
                criteria.andTypeEqualTo(MemberConstants.MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                        .andIsRetireNotEqualTo(true);
                break;
            case 3:
                criteria.andTypeEqualTo(MemberConstants.MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                        .andIsRetireEqualTo(true);
                break;
            case 6:
                criteria.andTypeEqualTo(MemberConstants.MEMBER_TYPE_STUDENT)
                        .andStatusEqualTo(MemberConstants.MEMBER_STATUS_TRANSFER);
                break;
            case 7:
                criteria.andTypeEqualTo(MemberConstants.MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(MemberConstants.MEMBER_STATUS_TRANSFER);
                break;
            case 10:
                criteria.andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
                break;
            default:
                criteria.andUserIdIsNull();
                break;
        }

        if (userSource != null) {
            criteria.andUserSourceEqualTo(userSource);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andUserIdIn(Arrays.asList(ids));

            if(cls==1 || cls==6){
                student_export(cls, example, cols, response);
            }else if(cls==2 || cls==3 || cls==7) {
                teacher_export(cls, example, cols, response);
            }
            return;
        }

        long count = memberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberView> records = memberViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(MemberView.class, MemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 党员基本信息
    @RequiresPermissions("member:base")
    @RequestMapping("/member_base")
    public String member_base(Integer userId, ModelMap modelMap) {

        MemberView member = iMemberMapper.getMemberView(userId);
        modelMap.put("member", member);
        if (member.getType() == MemberConstants.MEMBER_TYPE_TEACHER)
            return "member/member/teacher_base";

        return "member/member/student_base";
    }

    @RequestMapping("/member_view")
    public String member_show_page(@CurrentUser SysUserView loginUser, HttpServletResponse response, int userId, ModelMap modelMap) {

        Member member = memberService.get(userId);

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            Integer partyId = member.getPartyId();
            Integer branchId = member.getBranchId();
            Integer loginUserId = loginUser.getId();
            boolean isBranchAdmin = PartyHelper.isPresentBranchAdmin(loginUserId, partyId, branchId);
            boolean isPartyAdmin = PartyHelper.isPresentPartyAdmin(loginUserId, partyId);
            if (!isBranchAdmin && !isPartyAdmin) {
                throw new UnauthorizedException();
            }
        }
        if (member.getType() == MemberConstants.MEMBER_TYPE_TEACHER)  // 这个地方的判断可能有问题，应该用党员信息里的类别++++++++++++
            return "member/member/teacher_view";
        return "member/member/student_view";
    }

    private List<String> getTeacherExportTitles() {

        return new ArrayList<>(Arrays.asList(new String[]{"工作证号|100", "姓名|80",
                "编制类别|80", "人员类别|100", "人员状态|80", "在岗情况|80", "岗位类别|80", "主岗等级|120",
                "性别|50", "出生日期|80", "年龄|50", "年龄范围|80", "民族|50", "国家/地区|80", "证件号码|150",
                "政治面貌|80", "所属" + CmTag.getStringProperty("partyName", "党委") + "|300", "所在党支部|300", "所在单位|200",
                "入党时间|100", "入党时所在党支部|200|left", "入党介绍人|100", "转正时间|100", "转正时所在党支部|200|left",
                "党内职务|100", "党内奖励|100", "其他奖励|100", "增加类型|100",
                "到校日期|80",
                "专业技术职务|120", "专技岗位等级|120", "管理岗位等级|120", "任职级别|120",
                "行政职务|180", "学历|120", "学历毕业学校|200", "学位授予学校|200",
                "学位|100", "学员结构|100", "人才类型|100", "人才称号|200", "手机号码|100"}));
    }

    public void teacher_export(int cls, MemberViewExample example, Integer[] cols, HttpServletResponse response) {

        //Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<MemberView> records = memberViewMapper.selectByExample(example);

        List<String> exportTitles = getTeacherExportTitles();
        if (cls == 3) {
            exportTitles.add(6, "离退休时间|80");
        }

        if (cols != null && cols.length > 0) {
            // 选择导出列
            List<String> _titles = new ArrayList<>();
            for (int col : cols) {
                _titles.add(exportTitles.get(col));
            }
            exportTitles.clear();
            exportTitles.addAll(_titles);
        }

        List<List<String>> valuesList = new ArrayList<>();
        for (MemberView record : records) {
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Date birth = record.getBirth();
            String ageRange = "";
            if (birth != null) {
                byte memberAgeRange = MemberConstants.getMemberAgeRange(birth);
                if (memberAgeRange > 0)
                    ageRange = MemberConstants.MEMBER_AGE_MAP.get(memberAgeRange);
            }
            CadreView cadre = CmTag.getCadreByUserId(record.getUserId());
            SysUserView uv = sysUserService.findById(record.getUserId());
            String post = record.getPost();  // 行政职务 -- 所在单位及职务
            String adminLevel = record.getPostLevel(); // 任职级别 -- 行政级别
            if (cadre != null && (cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                    || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)) {
                post = cadre.getTitle();
                if (cadre.getAdminLevel() != null) adminLevel = CmTag.getMetaType(cadre.getAdminLevel()).getName();
            }
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    record.getCode(),
                    record.getRealname(),
                    record.getAuthorizedType(),
                    record.getStaffType(),
                    record.getStaffStatus(), // 人员状态
                    record.getOnJob(), // 在岗情况
                    record.getPostClass(), // 岗位类别
                    record.getMainPostLevel(), // 主岗等级
                    gender == null ? "" : SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth != null ? DateUtils.intervalYearsUntilNow(birth) + "" : "",
                    ageRange, // 年龄范围
                    record.getNation(),
                    uv.getCountry(),// 国家/地区
                    record.getIdcard(), // 证件号码
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),
                    partyId == null ? "" : partyMap.get(partyId).getName(),
                    branchId == null ? "" : branchMap.get(branchId).getName(),
                    uv.getUnit(), // 所在单位

                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record.getGrowBranch(),
                    record.getSponsor(),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    record.getPositiveBranch(),
                    record.getPartyPost(),
                    record.getPartyReward(),
                    record.getOtherReward(),
                    metaTypeService.getName(record.getAddType()),

                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYY_MM_DD), // 到校日期
                    record.getProPost(), // 专业技术职务
                    record.getProPostLevel(), //专技岗位等级
                    record.getManageLevel(), // 管理岗位等级
                    adminLevel, // 任职级别 -- 行政级别
                    post, // 行政职务 -- 职务
                    record.getEducation(), // 学历
                    record.getSchool(), // 学历毕业学校
                    record.getDegreeSchool(),
                    record.getDegree(), // 学位
                    record.getFromType(), // 学员结构
                    record.getTalentType(), // 人才类型
                    record.getTalentTitle(),
                    record.getMobile()
            }));

            if (cls == 3) {
                values.add(6, DateUtils.formatDate(record.getRetireTime(), DateUtils.YYYY_MM_DD));
            }

            if (cols != null && cols.length > 0) {
                // 选择导出列
                List<String> _values = new ArrayList<>();
                for (int col : cols) {
                    _values.add(values.get(col));
                }
                values.clear();
                values.addAll(_values);
            }

            valuesList.add(values);
        }
        String fileName = (cls == 7 ? "已转出" : (cls == 3 ? "离退休" : "在职")) + "教职工党员信息(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";

        ExportHelper.export(exportTitles, valuesList, fileName, response);
    }

    private List<String> getStudentExportTitles() {

        return new ArrayList<>(Arrays.asList(new String[]{"学号|100", "学生类别|150", "姓名|80", "性别|50", "出生日期|100", "身份证号|150",
                "民族|100", "年级|50", "所属" + CmTag.getStringProperty("partyName", "党委") + "|350|left", "所属党支部|350|left",
                "政治面貌|100", "入党时间|100", "入党时所在党支部|200|left", "入党介绍人|100", "转正时间|100", "转正时所在党支部|200|left",
                "党内职务|100", "党内奖励|100", "其他奖励|100", "增加类型|100",
                "培养层次（研究生）|150", "培养类型（研究生）|150", "教育类别（研究生）|150",
                "培养方式（研究生）|150", "预计毕业年月|100", "学籍状态|100", "是否出国留学|100"}));
    }

    public void student_export(int cls, MemberViewExample example, Integer[] cols, HttpServletResponse response) {

        //Map<Integer, Unit> unitMap = unitService.findAll();
        List<MemberView> records = memberViewMapper.selectByExample(example);
        int rownum = records.size();

        List<String> exportTitles = getStudentExportTitles();
        if (cols != null && cols.length > 0) {
            // 选择导出列
            List<String> _titles = new ArrayList<>();
            for (int col : cols) {
                _titles.add(exportTitles.get(col));
            }
            exportTitles.clear();
            exportTitles.addAll(_titles);
        }

        List<List<String>> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberView record = records.get(i);
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();

            MemberStay memberStay = memberStayService.get(record.getUserId(), MemberConstants.MEMBER_STAY_TYPE_ABROAD);

            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    record.getCode(),
                    record.getStudentType(),
                    record.getRealname(),
                    gender == null ? "" : SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                    record.getIdcard(),
                    record.getNation(),
                    record.getGrade(), // 年级
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()), // 政治面貌
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record.getGrowBranch(),
                    record.getSponsor(),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    record.getPositiveBranch(),
                    record.getPartyPost(),
                    record.getPartyReward(),
                    record.getOtherReward(),
                    metaTypeService.getName(record.getAddType()),
                    record.getEduLevel(),
                    record.getEduType(),
                    record.getEduCategory(),
                    record.getEduWay(),
                    DateUtils.formatDate(record.getExpectGraduateTime(), DateUtils.YYYY_MM_DD),
                    record.getXjStatus(),
                    (memberStay != null && memberStay.getStatus() ==
                            MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY) ? "是" : "否"// 是否出国留学
            }));

            if (cols != null && cols.length > 0) {
                // 选择导出列
                List<String> _values = new ArrayList<>();
                for (int col : cols) {
                    _values.add(values.get(col));
                }
                values.clear();
                values.addAll(_values);
            }

            valuesList.add(values);
        }

        String fileName = (cls == 6 ? "已转出" : "") + "学生党员信息(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.export(exportTitles, valuesList, fileName, response);
    }
}
