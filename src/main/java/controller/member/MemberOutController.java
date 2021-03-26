package controller.member;

import controller.global.OpException;
import domain.base.MetaType;
import domain.member.*;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.util.*;

@Controller
public class MemberOutController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberOutImport:*")
    @RequestMapping("/memberOut_import")
    public String memberOut_import(ModelMap modelMap) {

        return "member/memberOut/memberOut_import";
    }

    // 导入
    @RequiresPermissions("memberOutImport:*")
    @RequestMapping(value = "/memberOut_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        Set<Integer> adminPartyIdSet = new HashSet<>(loginUserService.adminPartyIdList());
        Set<Integer> adminBranchIdSet = new HashSet<>(loginUserService.adminBranchIdList());

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<MemberOut> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            MemberOut record = new MemberOut();

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getId());

            Member member = memberService.get(uv.getId());
            if (member == null) {
                throw new OpException("第{0}行学工号[{1}]不在党员库中", row, userCode);
            }
            record.setPartyId(member.getPartyId());
            record.setBranchId(member.getBranchId());

            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
                if (!adminPartyIdSet.contains(member.getPartyId())
                        && (member.getBranchId() == null || !adminBranchIdSet.contains(member.getBranchId()))) {

                    throw new OpException("第{0}行学工号[{1}]没有权限导入", row, userCode);
                }
            }

            String _type = StringUtils.trimToNull(xlsRow.get(2));
            MetaType type = CmTag.getMetaTypeByName("mc_member_in_out_type", _type);
            if (type == null) throw new OpException("第{0}行组织关系转出类别[{1}]不存在", row, _type);
            record.setType(type.getId());

            int rowNum = 3;
            record.setPhone(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            record.setToTitle(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            record.setToUnit(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            record.setFromUnit(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            record.setFromAddress(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            record.setFromPhone(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            record.setFromFax(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            record.setFromPostCode(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            record.setPayTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++))));

            Integer days = null;
            String _days = StringUtils.trimToNull(xlsRow.get(rowNum++));
            if (_days != null) {
                try {
                    days = Integer.valueOf(_days);
                } catch (Exception e) {
                    throw new OpException("第{0}行介绍信有效期天数[{1}]有误", row, _type);
                }
            }
            record.setValidDays(days);
            Date handleTime = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(rowNum++)));
            if(handleTime==null){
                throw new OpException("第{0}行办理时间[{1}]为空", row, _type);
            }
            record.setHandleTime(handleTime);
            record.setHasReceipt(StringUtils.equals(StringUtils.trimToNull(xlsRow.get(rowNum++)), "是"));

            //record.setStatus(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY);

            records.add(record);
        }

        int addCount = memberOutService.batchImport(records);
        int totalCount = records.size();

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入组织关系转出记录成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_view")
    public String memberOut_view(int userId, ModelMap modelMap) {

        modelMap.put("userBean", userBeanService.get(userId));

        MemberOut memberOut = memberOutService.getLatest(userId);
        modelMap.put("memberOut", memberOut);

        return "member/memberOut/memberOut_view";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut")
    public String memberOut(@RequestParam(defaultValue = "1") byte cls, // 1 待审核 2未通过 3 已审核
                            Integer userId,
                            Integer partyId,
                            Integer branchId, ModelMap modelMap) {

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

        if (cls == 1) {
            // 分党委待审核总数（新申请）
            modelMap.put("approvalCountNew", memberOutService.count(null, null, (byte) 1, (byte) 1));
            // 分党委待审核总数（返回修改）
            modelMap.put("approvalCountBack", memberOutService.count(null, null, (byte) 1, (byte) 4));
            // 分党委待审核总数
            modelMap.put("approvalCount", memberOutService.count(null, null, (byte) 1, cls));
        }
        if (cls == 6) {
            // 组织部待审核总数（新申请）
            modelMap.put("approvalCountNew", memberOutService.count(null, null, (byte) 2, (byte) 6));
            // 组织部待审核总数（返回修改）
            modelMap.put("approvalCountBack", memberOutService.count(null, null, (byte) 2, (byte) 7));

            modelMap.put("approvalCount", memberOutService.count(null, null, (byte) 2, cls));
        }

        if (cls == 3) {
            List<MetaType> printTypeList = new ArrayList<>(); // 打印类别
            List<MetaType> fillPrintTypeList = new ArrayList<>(); // 套打类别
            Map<Integer, MetaType> typeMap = CmTag.getMetaTypes("mc_member_in_out_type");
            for (MetaType type : typeMap.values()) {
                if (BooleanUtils.isTrue(type.getBoolAttr())) {
                    fillPrintTypeList.add(type);
                } else {
                    printTypeList.add(type);
                }
            }
            modelMap.put("printTypeList", printTypeList);
            modelMap.put("fillPrintTypeList", fillPrintTypeList);
        }

        return "member/memberOut/memberOut_page";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_data")
    public void memberOut_data(@RequestParam(defaultValue = "1") byte cls, HttpServletResponse response,
                               /*@SortParam(required = false, defaultValue = "id", tableName = "ow_member_out") String sort,
                               @OrderParam(required = false, defaultValue = "desc") String order,*/
                               Integer userId,
                               Byte status,
                               Boolean hasReceipt,
                               Boolean isBack,
                               Boolean isModify,
                               Boolean isSelfPrint,
                               Byte userType,
                               Integer type,
                               Integer partyId,
                               Integer branchId,
                               String toUnit,
                               String toTitle,
                               String fromUnit,
                               @RequestDateRange DateRange _handleTime,
                               @RequestDateRange DateRange _acceptReceiptTime,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberOutViewExample example = new MemberOutViewExample();
        MemberOutViewExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        if (cls == 3) {
            //example.setOrderByClause("print_count asc, id desc");
            example.setOrderByClause("check_time desc, apply_time desc");
        } else {
            example.setOrderByClause("apply_time desc");
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (userType != null) {
            criteria.andUserTypeEqualTo(userType);
        }
        if (hasReceipt != null) {
            criteria.andHasReceiptEqualTo(hasReceipt);
        }
        if (_acceptReceiptTime.getStart() != null) {
            criteria.andAcceptReceiptTimeGreaterThanOrEqualTo(_acceptReceiptTime.getStart());
        }
        if (_acceptReceiptTime.getEnd() != null) {
            criteria.andAcceptReceiptTimeLessThanOrEqualTo(_acceptReceiptTime.getEnd());
        }
        if (isBack != null) {
            criteria.andIsBackEqualTo(isBack);
        }
        if (isModify != null) {
            criteria.andIsModifyEqualTo(isModify);
        }
        if (isSelfPrint != null) {
            if (isSelfPrint)
                criteria.andIsSelfPrintCountGreaterThan(0);
            else
                criteria.andIsSelfPrintCountEqualTo(0);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (StringUtils.isNotBlank(toUnit)) {
            criteria.andToUnitLike(SqlUtils.like(toUnit));
        }
        if (StringUtils.isNotBlank(toTitle)) {
            criteria.andToTitleLike(SqlUtils.like(toTitle));
        }
        if (StringUtils.isNotBlank(fromUnit)) {
            criteria.andFromUnitLike(SqlUtils.like(fromUnit));
        }
        if (_handleTime.getStart() != null) {
            criteria.andHandleTimeGreaterThanOrEqualTo(_handleTime.getStart());
        }

        if (_handleTime.getEnd() != null) {
            criteria.andHandleTimeLessThanOrEqualTo(_handleTime.getEnd());
        }

        if (cls == 1) { // 分党委审核（申请记录）
            criteria.andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_APPLY);
        } else if (cls == 5) { // 分党委已审核
            criteria.andStatusGreaterThanOrEqualTo(MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        } else if (cls == 6) { // 组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        } else if (cls == 2) {
            List<Byte> statusList = new ArrayList<>();
            statusList.add(MemberConstants.MEMBER_OUT_STATUS_ABOLISH);
            statusList.add(MemberConstants.MEMBER_OUT_STATUS_SELF_BACK);
            statusList.add(MemberConstants.MEMBER_OUT_STATUS_BACK);
            criteria.andStatusIn(statusList);
        } else {
            criteria.andStatusIn(Arrays.asList(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY,
                    MemberConstants.MEMBER_OUT_STATUS_ARCHIVE));
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            memberOut_export(example, response);
            return;
        }

        long count = memberOutViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberOutView> memberOuts = memberOutViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberOuts);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }


    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_approval")
    public String memberOut_approval(@RequestParam(defaultValue = "1") byte cls, @CurrentUser SysUserView loginUser, Integer id,
                                     byte type, // 1:分党委审核 2：组织部审核
                                     ModelMap modelMap) {

        MemberOut currentMemberOut = null;
        if (id != null) {
            currentMemberOut = memberOutMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberOut.getStatus() != MemberConstants.MEMBER_OUT_STATUS_APPLY)
                    currentMemberOut = null;
            }
            if (type == 2) {
                if (currentMemberOut.getStatus() != MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY)
                    currentMemberOut = null;
            }
        } else {
            currentMemberOut = memberOutService.next(null, type, cls);
        }
        if (currentMemberOut == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberOut", currentMemberOut);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), currentMemberOut.getPartyId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL));
        }

        // 读取总数
        modelMap.put("count", memberOutService.count(null, null, type, cls));
        // 下一条记录
        modelMap.put("next", memberOutService.next(currentMemberOut, type, cls));
        // 上一条记录
        modelMap.put("last", memberOutService.last(currentMemberOut, type, cls));

        return "member/memberOut/memberOut_approval";
    }

    @RequiresPermissions("memberOut:edit")
    @RequestMapping("/memberOut_deny")
    public String memberOut_deny(Integer id, ModelMap modelMap) {

        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        modelMap.put("memberOut", memberOut);
        Integer userId = memberOut.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberOut/memberOut_deny";
    }

    @RequiresPermissions("memberOut:edit")
    @RequestMapping(value = "/memberOut_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                  byte type, // 1:分党委审核 2：组织部审核
                                  Integer[] ids) {


        memberOutService.memberOut_check(ids, type, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "组织关系转出申请-审核：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOut:edit")
    @RequestMapping("/memberOut_back")
    public String memberOut_back() {

        return "member/memberOut/memberOut_back";
    }

    @RequiresPermissions("memberOut:edit")
    @RequestMapping(value = "/memberOut_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_back(@CurrentUser SysUserView loginUser,
                                 Integer[] ids,
                                 byte status,
                                 String reason) {


        memberOutService.memberOut_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "分党委退回组织关系转出申请：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOut:edit")
    @RequestMapping(value = "/memberOut_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_au(@CurrentUser SysUserView loginUser,
                               MemberOut record,
                               String _payTime,
                               String _acceptReceiptTime,
                               Boolean reapply, //  添加或 重新申请 时传入 true
                               String _handleTime, HttpServletRequest request) {

        Integer userId = record.getUserId();

        if (StringUtils.isNotBlank(_acceptReceiptTime)){
            record.setAcceptReceiptTime(DateUtils.parseDate(_acceptReceiptTime, DateUtils.YYYY_MM_DD));
        }
        if(record.getId()==null) {
            Member member = memberService.get(userId);
            record.setPartyId(member.getPartyId());
            record.setBranchId(member.getBranchId());
        }

        if (!IdcardValidator.valid(record.getIdcard())) {
            return failed("身份证号码有误。");
        }

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();

        //===========权限
        Integer loginUserId = loginUser.getId();
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {

            if (!PartyHelper.hasBranchAuth(loginUserId, partyId, branchId))
                throw new UnauthorizedException();

            if (record.getId() != null) {
                // 分党委只能修改还未提交组织部审核的记录
                MemberOut before = memberOutMapper.selectByPrimaryKey(record.getId());
                if (before.getStatus() == MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY) {
                    return failed("该申请已经提交组织部审核，不可以进行修改。");
                }
            }
        }

        Integer id = record.getId();
        if (id == null) {
            MemberOut memberOut = memberOutService.getLatest(record.getUserId());
            if (memberOut != null) {
                if (memberOut.getStatus() < MemberConstants.MEMBER_OUT_STATUS_APPLY) {
                    // 保证退回或撤销的，可以重新提交
                    id = memberOut.getId();
                    record.setId(id);
                } else if (memberOut.getStatus() >= MemberConstants.MEMBER_OUT_STATUS_APPLY
                        && memberOut.getStatus() < MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY) {
                    return failed(String.format("存在未完成审批的记录（状态：%s）",
                            MemberConstants.MEMBER_OUT_STATUS_MAP.get(memberOut.getStatus())));
                }
            }
        }

        if (StringUtils.isNotBlank(_payTime)) {
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }
        if (StringUtils.isNotBlank(_handleTime)) {
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            record.setApplyTime(new Date());
            memberOutService.insertOrUpdateSelective(record);

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT,
                    "后台操作",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交");

            logger.info(addLog(LogConstants.LOG_MEMBER, "添加组织关系转出：%s", record.getId()));
        } else {
            MemberOut before = memberOutMapper.selectByPrimaryKey(record.getId());
            // 重新提交未通过的申请
            if (BooleanUtils.isTrue(reapply) && before.getStatus() < MemberConstants.MEMBER_OUT_STATUS_APPLY) {

                record.setApplyTime(new Date());
                record.setStatus(MemberConstants.MEMBER_OUT_STATUS_APPLY);
                record.setIsBack(false);
                memberOutService.insertOrUpdateSelective(record);

                applyApprovalLogService.add(record.getId(),
                        record.getPartyId(), record.getBranchId(), record.getUserId(),
                        loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT,
                        "后台操作",
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "重新提交");
            } else if (hasModified(before, record)) {

                memberOutService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_MEMBER, "更新组织关系转出：%s", record.getId()));

                MemberOut _memberOut = memberOutMapper.selectByPrimaryKey(record.getId());
                if (_memberOut.getStatus() == MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY) { // 转出之后，如果还有修改，则需要保存记录

                    if (BooleanUtils.isNotTrue(before.getIsModify())) { // 第一次修改
                        MemberOut _record = new MemberOut();
                        _record.setId(before.getId());
                        _record.setIsModify(true);
                        memberOutMapper.updateByPrimaryKeySelective(_record);

                        MemberOutModify _modifyRecord = new MemberOutModify();
                        try {
                            PropertyUtils.copyProperties(_modifyRecord, before);
                        } catch (Exception e) {
                            logger.error("异常", e);
                        }
                        _modifyRecord.setId(null);
                        _modifyRecord.setOutId(_memberOut.getId());
                        _modifyRecord.setApplyUserId(_memberOut.getUserId());
                        _modifyRecord.setUserId(_memberOut.getUserId()); // 原纪录
                        _modifyRecord.setCreateTime(new Date());
                        _modifyRecord.setIp(IpUtils.getRealIp(request));
                        memberOutModifyMapper.insertSelective(_modifyRecord);
                    }

                    {
                        MemberOutModify _modifyRecord = new MemberOutModify();
                        try {
                            PropertyUtils.copyProperties(_modifyRecord, _memberOut);
                        } catch (Exception e) {
                            logger.error("异常", e);
                        }

                        _modifyRecord.setId(null);
                        _modifyRecord.setOutId(_memberOut.getId());
                        _modifyRecord.setApplyUserId(_memberOut.getUserId());
                        _modifyRecord.setUserId(loginUserId);
                        _modifyRecord.setCreateTime(new Date());
                        _modifyRecord.setIp(IpUtils.getRealIp(request));
                        memberOutModifyMapper.insertSelective(_modifyRecord);
                    }
                }
            } else {
                return failed("没有修改项。如不需要修改，请直接返回。");
            }
        }

        return success(FormUtils.SUCCESS);
    }

    private boolean hasModified(MemberOut before, MemberOut record) {

        return ( (record.getAge() != null && !StringUtils.equals(before.getAge() + "", record.getAge() + ""))
                        || (record.getNation() != null && !StringUtils.equals(before.getNation() + "", record.getNation() + ""))
                        || (record.getPoliticalStatus() != null && !StringUtils.equals(before.getPoliticalStatus() + "", record.getPoliticalStatus() + ""))
                        || (record.getIdcard() != null && !StringUtils.equals(before.getIdcard() + "", record.getIdcard() + ""))
                        || (record.getGender() != null && !StringUtils.equals(before.getGender() + "", record.getGender() + ""))
                        || (record.getPhone() != null && !StringUtils.equals(before.getPhone() + "", record.getPhone() + ""))
                        || (record.getPartyId() != null && !StringUtils.equals(before.getPartyId() + "", record.getPartyId() + ""))
                        || (record.getBranchId() != null && !StringUtils.equals(before.getBranchId() + "", record.getBranchId() + ""))
                        || (record.getType() != null && !StringUtils.equals(before.getType() + "", record.getType() + ""))
                        || (record.getToTitle() != null && !StringUtils.equals(before.getToTitle(), record.getToTitle()))
                        || (record.getToUnit() != null && !StringUtils.equals(before.getToUnit(), record.getToUnit()))
                        || (record.getFromUnit() != null && !StringUtils.equals(before.getFromUnit(), record.getFromUnit()))
                        || (record.getFromAddress() != null && !StringUtils.equals(before.getFromAddress(), record.getFromAddress()))
                        || (record.getFromPhone() != null && !StringUtils.equals(before.getFromPhone(), record.getFromPhone()))
                        || (record.getFromFax() != null && !StringUtils.equals(before.getFromFax(), record.getFromFax()))
                        || (record.getFromPostCode() != null && !StringUtils.equals(before.getFromPostCode(), record.getFromPostCode()))
                        || (record.getPayTime() != null && !StringUtils.equals(DateUtils.formatDate(before.getPayTime(), "yyyyMM"), DateUtils.formatDate(record.getPayTime(), "yyyyMM")))
                        || (record.getValidDays() != null && !StringUtils.equals(before.getValidDays() + "", record.getValidDays() + ""))
                        || (record.getHandleTime() != null && !StringUtils.equals(DateUtils.formatDate(before.getHandleTime(), "yyyyMMdd"), DateUtils.formatDate(record.getHandleTime(), "yyyyMMdd")))
                        || (record.getHasReceipt() != null && !StringUtils.equals(before.getHasReceipt() + "", record.getHasReceipt() + ""))
                        || (record.getYear() != null && !StringUtils.equals(before.getYear() + "", record.getYear() + ""))
                        || (!CmTag.getBoolProperty("use_code_as_identify") && ((record.getCode() == null && before.getCode() == null)|| !StringUtils.equals(before.getYear() + "", record.getYear() + "")))
        );
    }

    @RequiresPermissions("memberOut:edit")
    @RequestMapping("/memberOut_au")
    public String memberOut_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
            modelMap.put("memberOut", memberOut);

            //modelMap.put("userBean", userBeanService.get(memberOut.getUserId()));
        }
        return "member/memberOut/memberOut_au";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_selects")
    @ResponseBody
    public Map memberOut_selects(Integer pageSize, Integer pageNo,
                                 Boolean noAuth, // 默认需要读取权限
                                 String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberOutExample example = new MemberOutExample();
        // 查询状态为“组织部审批通过”的记录（不包含已归档记录）
        MemberOutExample.Criteria criteria = example.createCriteria()
                .andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY);

        boolean addPermits = false;
        List<Integer> adminPartyIdList = null;
        List<Integer> adminBranchIdList = null;
        if (BooleanUtils.isNotTrue(noAuth)) {
            addPermits = !ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);

            if(addPermits) {
                adminPartyIdList = loginUserService.adminPartyIdList();
                adminBranchIdList = loginUserService.adminBranchIdList();
                criteria.addPermits(adminPartyIdList, adminBranchIdList);
            }
        }


        if(StringUtils.isNotBlank(searchStr)) {
            criteria.andUserLike(searchStr);
        }

        long count = memberOutMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberOut> records = memberOutMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List options = new ArrayList<>();
        if (null != records && records.size() > 0) {

            for (MemberOut record : records) {
                int userId = record.getUserId();
                Map<String, Object> option = new HashMap<>();
                option.put("id", userId + "");
                option.put("text", record.getRealname());
                option.put("code", record.getCode());
                option.put("unit", extService.getUnit(userId));

                MemberView mv = iMemberMapper.getMemberView(userId);
                if(mv!=null) {
                    option.put("politicalStatus", mv.getPoliticalStatus());
                    option.put("applyTime", mv.getApplyTime());
                    option.put("activeTime", mv.getActiveTime());
                    option.put("candidateTime", mv.getCandidateTime());
                    option.put("growTime", mv.getGrowTime());
                    option.put("positiveTime", mv.getPositiveTime());
                }
                option.put("record", record);
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("memberOut:abolish")
    @RequestMapping("/memberOut_abolish")
    public String memberOut_abolish(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
            modelMap.put("memberOut", memberOut);
        }
        return "member/memberOut/memberOut_abolish";
    }

    @RequiresPermissions("memberOut:abolish")
    @RequestMapping(value = "/memberOut_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_abolish(HttpServletRequest request, Integer id, String remark) {

        if (id != null) {
            memberOutService.abolish(id, remark, (byte) 3); // 默认组织部撤销
            logger.info(addLog(LogConstants.LOG_MEMBER, "撤销已完成的组织关系转出：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutSelfPrint:edit")
    @RequestMapping("/memberOut/memberOut_selfPrint")
    public String memberOut_selfPrint() {

        return "member/memberOut/memberOut_selfPrint";
    }

    @RequiresPermissions("memberOutSelfPrint:edit")
    @RequestMapping(value = "/memberOut/memberOut_selfPrint", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_selfPrint(Integer[] ids, Boolean isSelfPrint) {

        if (ids != null) {

            memberOutService.updateSelfPrint(ids, BooleanUtils.isTrue(isSelfPrint));
            logger.info(addLog(LogConstants.LOG_MEMBER, "变更已完成的组织关系转出党员(%s)的自助打印状态",
                    StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("memberOut:del")
    @RequestMapping(value = "/memberOut_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberOutService.del(id);
            logger.info(addLog(LogConstants.LOG_MEMBER, "删除组织关系转出：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOut:del")
    @RequestMapping(value = "/memberOut_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            memberOutService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量删除组织关系转出：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
*/
    public void memberOut_export(MemberOutViewExample example, HttpServletResponse response) {

        List<MemberOutView> records = memberOutViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号|100", "姓名|50", "身份证号码|180", "性别|50", "年龄|50", "民族|80", "人员类别|80", "联系电话|100",
                "类别|50", "党籍状态|100", "所在基层党组织|300|left", "所在党支部|300|left", "转入单位抬头|280|left",
                "转入单位|200|left", "转出单位|200|left", "介绍信有效期天数|120", "办理时间|80","是否有回执|80","回执接收时间|100", "状态|120"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            MemberOutView record = records.get(i);
            int userId = record.getUserId();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Member member = memberService.get(userId);
            String memberTypeName = SystemConstants.USER_TYPE_MAP.get(record.getUserType());

            String[] values = {
                    record.getUserCode(),
                    record.getRealname(),
                    record.getIdcard(),
                    record.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(record.getGender()),
                    record.getAge()==null?"":record.getAge()+"",
                    record.getNation(),
                    memberTypeName,
                    record.getPhone(),
                    record.getType() == null ? "" : metaTypeService.getName(record.getType()),
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(member.getPoliticalStatus()),
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    record.getToTitle(),
                    record.getToUnit(),
                    record.getFromUnit(),
                    record.getValidDays() + "",
                    DateUtils.formatDate(record.getHandleTime(), DateUtils.YYYY_MM_DD),
                    BooleanUtils.isTrue(record.getHasReceipt())?"是":"否",
                    DateUtils.formatDate(record.getAcceptReceiptTime(), DateUtils.YYYY_MM_DD),
                    record.getStatus() == null ? "" : MemberConstants.MEMBER_OUT_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = "组织关系转出(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
