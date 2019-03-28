package controller.member;

import bean.XlsUpload;
import controller.global.OpException;
import domain.member.*;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

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
                    msg += "；已进入入党申请阶段【" + OwConstants.OW_APPLY_STAGE_MAP.get(memberApply.getStage()) + "】";
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
                    MemberTeacher memberTeacher = memberTeacherService.get(userId);
                    if (memberTeacher.getIsRetire())
                        status = "已退休";
                }

                MemberStay memberStay = memberStayService.get(userId);
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
        List<Map<Integer, String>> xlsRows = XlsUpload.getXlsRows(sheet);

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
                return failed("该用户已经提交了入党申请[当前审批阶段：" + OwConstants.OW_APPLY_STAGE_MAP.get(memberApply.getStage()) + "]，不可以直接添加。");
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
            modelMap.put("op", "编辑");

            partyId = member.getPartyId();
            branchId = member.getBranchId();
            modelMap.put("sysUser", sysUserService.findById(userId));
        } else {
            SecurityUtils.getSubject().checkPermission("member:add");

            modelMap.put("op", "添加");
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

    // 后台添加预备党员，可能需要加入入党申请（预备党员阶段）
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

        memberService.modifyStatus(userId, politicalStatus, remark);

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

            memberService.batchDel(ids);

            logger.info(addLog(LogConstants.LOG_MEMBER, "批量删除党员：%s", JSONUtils.toString(members, false)));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/member")
    public String member(HttpServletResponse response,
                         Integer partyId, Integer branchId,
                         Integer cls, ModelMap modelMap) {

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
        String queryStr = "?cls=" + cls;
        if (partyId != null) {
            queryStr += "&partyId=" + partyId;
        }
        if (branchId != null) {
            queryStr += "&branchId=" + branchId;
        }
        /**
         * cls=1 学生党员 member.type=3 member.status=1
         * cls=6 已转出的学生党员
         */
        if (cls == 1 || cls == 6) {

            return "forward:/memberStudent" + queryStr;
        }
        /*
            cls=2教职工   =>  member.type=1 member.status=1
                3离退休   =>  member.type=2 member.status=1
                （弃用）4应退休   =>  member.type=2 member.status=1
                （弃用）5已退休   =>  member.type=2 memberTeacher.isRetire=1 member.status=2
                cls=7 已转出的教工党员
         */
        return "forward:/memberTeacher" + queryStr;
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
}
