package controller.member;

import bean.UserBean;
import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberTransfer;
import domain.member.MemberTransferExample;
import domain.member.MemberTransferExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Controller
public class MemberTransferController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_view")
    public String memberTransfer_view(Integer id, Integer userId, Boolean popup, ModelMap modelMap) {

        MemberTransfer memberTransfer = null;
        if(id!=null){
            memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
            userId = memberTransfer.getUserId();
        }else{
           memberTransfer = memberTransferService.get(userId);
        }

        modelMap.put("memberTransfer", memberTransfer);

        UserBean userBean = userBeanService.get(userId);
        modelMap.put("userBean", userBean);

        if(BooleanUtils.isTrue(popup)){
            return "member/memberTransfer/memberTransfer_view_popup";
        }else {

            MemberTransferExample example = new MemberTransferExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStatusEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
            List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExample(example);
            modelMap.put("memberTransfers", memberTransfers); // 已完成的记录

            return "member/memberTransfer/memberTransfer_view";
        }
    }

    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer")
    public String memberTransfer(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核
                                  Integer userId,
                                  Integer partyId,
                                  Integer branchId,
                                  Integer toPartyId,
                                  Integer toBranchId,
                                  ModelMap modelMap) {

        modelMap.put("cls", cls);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }
        if (toPartyId != null) {
            modelMap.put("toParty", partyMap.get(toPartyId));
        }
        if (toBranchId != null) {
            modelMap.put("toBranch", branchMap.get(toBranchId));
        }

        // 转出分党委待审核总数
        modelMap.put("partyApprovalCount", memberTransferService.count(null, null, (byte)1));
        // 转入分党委待审核数目
        modelMap.put("toPartyApprovalCount", memberTransferService.count(null, null, (byte)2));

        return "member/memberTransfer/memberTransfer_page";
    }
    
    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_data")
    public void memberTransfer_data(@RequestParam(defaultValue = "1")Integer cls,HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_transfer") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 Byte status,
                                 Boolean isBack,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                    Integer toPartyId,
                                    Integer toBranchId,
                                    @RequestDateRange DateRange _fromHandleTime,
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

        MemberTransferExample example = new MemberTransferExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if(status!=null){
            criteria.andStatusEqualTo(status);
        }
        if(isBack!=null){
            criteria.andIsBackEqualTo(isBack);
        }

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (toPartyId!=null) {
            criteria.andToPartyIdEqualTo(toPartyId);
        }
        if (toBranchId!=null) {
            criteria.andToBranchIdEqualTo(toBranchId);
        }
        if (_fromHandleTime.getStart()!=null) {
            criteria.andFromHandleTimeGreaterThanOrEqualTo(_fromHandleTime.getStart());
        }

        if (_fromHandleTime.getEnd()!=null) {
            criteria.andFromHandleTimeLessThanOrEqualTo(_fromHandleTime.getEnd());
        }

        if(cls==1){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(MemberConstants.MEMBER_TRANSFER_STATUS_APPLY);
            statusList.add(MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
            criteria.andStatusIn(statusList);
        }else if(cls==2){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(MemberConstants.MEMBER_TRANSFER_STATUS_SELF_BACK);
            statusList.add(MemberConstants.MEMBER_TRANSFER_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            memberTransfer_export(example, response);
            return;
        }

        int count = (int) memberTransferMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberTransfers);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberTransfer:list")
    @RequestMapping("/memberTransfer_approval")
    public String memberTransfer_approval(@CurrentUser SysUserView loginUser, Integer id,
                                      byte type, // 1:转出分党委审核 2：转入分党委审核
                                      ModelMap modelMap) {

        MemberTransfer currentMemberTransfer = null;
        if (id != null) {
            currentMemberTransfer = memberTransferMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberTransfer.getStatus() != MemberConstants.MEMBER_TRANSFER_STATUS_APPLY)
                    currentMemberTransfer = null;
            }
            if (type == 2) {
                if (currentMemberTransfer.getStatus() != MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY)
                    currentMemberTransfer = null;
            }
        } else {
            currentMemberTransfer = memberTransferService.next(type, null);
        }
        if (currentMemberTransfer == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberTransfer", currentMemberTransfer);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), currentMemberTransfer.getPartyId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), currentMemberTransfer.getToPartyId()));
        }

        // 读取总数
        modelMap.put("count", memberTransferService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberTransferService.next(type, currentMemberTransfer));
        // 上一条记录
        modelMap.put("last", memberTransferService.last(type, currentMemberTransfer));

        return "member/memberTransfer/memberTransfer_approval";
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping("/memberTransfer_deny")
    public String memberTransfer_deny(Integer id, ModelMap modelMap) {

        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        modelMap.put("memberTransfer", memberTransfer);
        Integer userId = memberTransfer.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberTransfer/memberTransfer_deny";
    }
    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                   byte type, // 1:转出分党委审核 2：转入分党委审核
                                   Integer[] ids) {


        memberTransferService.memberTransfer_check(ids, type, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "暂留申请-审核：%s", StringUtils.join( ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping("/memberTransfer_back")
    public String memberTransfer_back() {

        return "member/memberTransfer/memberTransfer_back";
    }

    @RequiresPermissions("memberTransfer:update")
    @RequestMapping(value = "/memberTransfer_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_back(@CurrentUser SysUserView loginUser,
                                  Integer[] ids,
                                  byte status,
                                  String reason) {


        memberTransferService.memberTransfer_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "暂留申请：%s", StringUtils.join( ids, ",")));
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping(value = "/memberTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_au(@CurrentUser SysUserView loginUser,MemberTransfer record,
                                    String _payTime, String _fromHandleTime,
                                    Boolean reapply,
                                    HttpServletRequest request) {

        Integer partyId = null;
        Integer branchId = null;
        if(record.getId()==null) {

            Integer userId = record.getUserId();
            Member member = memberService.get(userId);
            partyId = member.getPartyId();
            branchId = member.getBranchId();
            record.setPartyId(partyId);
            record.setBranchId(branchId);
        }else{

            MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(record.getId());
            partyId = memberTransfer.getPartyId();
            branchId = memberTransfer.getBranchId();
        }

        //===========权限
        Integer loginUserId = loginUser.getId();
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin && branchId!=null) {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }

            boolean isToAdmin = partyMemberService.isPresentAdmin(loginUserId, record.getToPartyId());
            if(!isToAdmin && record.getToBranchId()!=null) {
                isToAdmin = branchMemberService.isPresentAdmin(loginUserId, record.getToPartyId(), record.getToBranchId());
            }

            if(!isAdmin && !isToAdmin) throw new OpException("您不是转出或转入党委的管理员，没有权限操作。");
        }

        Integer id = record.getId();

        if (memberTransferService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }
        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }


        if (id == null) {
            record.setApplyTime(new Date());
            record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_APPLY);
            memberTransferService.insertSelective(record);

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER,
                    "后台操作",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交申请");

            logger.info(addLog(LogConstants.LOG_MEMBER, "提交校内组织关系转接申请：%s", record.getId()));
        } else {
            MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
            if (BooleanUtils.isTrue(reapply) && memberTransfer.getStatus() < MemberConstants.MEMBER_TRANSFER_STATUS_APPLY) {

                record.setApplyTime(new Date());
                record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_APPLY);
                record.setIsBack(false);
                memberTransferService.updateByPrimaryKeySelective(record);

                applyApprovalLogService.add(record.getId(),
                        record.getPartyId(), record.getBranchId(), record.getUserId(),
                        loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER,
                        "后台操作",
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "重新提交申请");
            } else {
                record.setStatus(null); // 不改状态
                memberTransferService.updateByPrimaryKeySelective(record);

                applyApprovalLogService.add(record.getId(),
                        record.getPartyId(), record.getBranchId(), record.getUserId(),
                        loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER,
                        "后台操作",
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "修改申请");

                logger.info(addLog(LogConstants.LOG_MEMBER, "更新校内组织关系转接申请：%s", record.getId()));
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping("/memberTransfer_au")
    public String memberTransfer_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
            modelMap.put("memberTransfer", memberTransfer);

            modelMap.put("userBean", userBeanService.get(memberTransfer.getUserId()));

            Integer userId = memberTransfer.getUserId();
            UserBean userBean = userBeanService.get(userId);
            modelMap.put("userBean", userBean);

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);

            modelMap.put("fromParty", partyMap.get(userBean.getPartyId()));
            modelMap.put("fromBranch", branchMap.get(userBean.getBranchId()));

            if (memberTransfer.getToPartyId() != null) {
                modelMap.put("toParty", partyMap.get(memberTransfer.getToPartyId()));
            }
            if (memberTransfer.getToBranchId() != null) {
                modelMap.put("toBranch", branchMap.get(memberTransfer.getToBranchId()));
            }

        }
        return "member/memberTransfer/memberTransfer_au";
    }

    /*@RequiresPermissions("memberTransfer:del")
    @RequestMapping(value = "/memberTransfer_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberTransferService.del(id);
            logger.info(addLog(LogConstants.LOG_MEMBER, "删除校内组织关系转接：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("memberTransfer:del")
    @RequestMapping(value = "/memberTransfer_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberTransferService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量删除校内组织关系转接：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberTransfer_export(MemberTransferExample example, HttpServletResponse response) {

        List<MemberTransfer> records = memberTransferMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号|100","姓名|100","所在党组织|300|left","所在党支部|300|left", "转入党组织|300|left",
                "转入党支部|300|left", "转出单位联系电话|100","转出单位传真|100",
                "党费缴纳至年月|100", "介绍信有效期天数|100","转出办理时间|100","状态|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberTransfer record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Integer toPartyId = record.getToPartyId();
            Integer toBranchId = record.getToBranchId();
            Branch branch = null;
            if (branchId != null){
                branch = branchService.findAll().get(branchId);
            }
            Branch toBranch = null;
            if (toBranchId != null){
                toBranch = branchService.findAll().get(toBranchId);
            }
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branch==null?"":branch.getName(),
                    toPartyId==null?"":partyService.findAll().get(toPartyId).getName(),
                    toBranch==null?"":toBranch.getName(),
                    record.getFromPhone(),
                    record.getFromFax(),
                    DateUtils.formatDate(record.getPayTime(), DateUtils.YYYYMM),
                    record.getValidDays()+"",
                    DateUtils.formatDate(record.getFromHandleTime(), DateUtils.YYYYMMDD_DOT),
                    record.getStatus()==null?"":MemberConstants.MEMBER_TRANSFER_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = "校内组织关系转接_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping("/memberTransfer_import")
    public String memberTransfer_import(ModelMap modelMap) {

        return "member/memberTransfer/memberTransfer_import";
    }

    // 导入
    @RequiresPermissions("memberTransfer:edit")
    @RequestMapping(value = "/memberTransfer_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        Set<Integer> adminPartyIdSet = new HashSet<>(loginUserService.adminPartyIdList());
        Set<Integer> adminBranchIdSet = new HashSet<>(loginUserService.adminBranchIdList());

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<MemberTransfer> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            MemberTransfer record = new MemberTransfer();

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
                if (!adminPartyIdSet.contains(member.getPartyId())) {
                    if (member.getBranchId() == null || !adminBranchIdSet.contains(member.getBranchId())) {
                        throw new OpException("第{0}行学工号[{1}]没有权限导入", row, userCode);
                    }
                }
            }

            String _toParty = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isBlank(_toParty)){
                throw new OpException("第{0}行转入二级党委为空", row);
            }
            Party toParty = partyService.getByName(_toParty);
            if (toParty == null) {
                throw new OpException("第{0}行转入二级党委[{1}]不存在", row, _toParty);
            }
            Integer toPartyId = toParty.getId();
            record.setToPartyId(toPartyId);

            //非直属党支部
            if (!PartyHelper.isDirectBranch(toPartyId)) {
                String _toBranch = StringUtils.trimToNull(xlsRow.get(3));
                if (StringUtils.isBlank(_toBranch)) {
                    throw new OpException("第{0}行转入党支部为空", row);
                }
                Branch toBranch = branchService.getByName(_toBranch);
                if (toBranch == null) {
                    throw new OpException("第{0}行转入党支部[{1}]不存在", row, _toBranch);
                }
                record.setToBranchId(toBranch.getId());
            }

            int col = 4;
            record.setFromPhone(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setFromFax(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPayTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

            String validDays = StringUtils.trimToNull(xlsRow.get(col++));
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            if (!pattern.matcher(validDays).matches()){
                throw new OpException("第{0}行介绍信有效期天数请填写阿拉伯数字", row);
            }
            record.setValidDays(Integer.valueOf(validDays));
            record.setFromHandleTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            records.add(record);
        }

        int addCount = memberTransferService.batchImport(records);
        int totalCount = records.size();

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入组织关系转接记录成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
