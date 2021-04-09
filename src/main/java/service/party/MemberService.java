package service.party;

import controller.global.OpException;
import domain.base.MetaType;
import domain.member.*;
import domain.party.*;
import domain.sys.SysUser;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import ext.service.SyncService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.base.MetaTypeService;
import service.member.EnterApplyService;
import service.member.MemberApplyService;
import service.member.MemberBaseMapper;
import service.member.MemberHistoryService;
import service.sys.LogService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.*;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class MemberService extends MemberBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired(required = false)
    private SyncService syncService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private LogService logService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private PartyMemberGroupService partyMemberGroupService;
    @Autowired
    private BranchMemberGroupService branchMemberGroupService;
    @Autowired
    private MemberApplyService memberApplyService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private MemberHistoryService memberHistoryService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    public Member get(int userId) {

        if (memberMapper == null) return null;
        return memberMapper.selectByPrimaryKey(userId);
    }

    /**
     * 党员出党后重新回来
     *
     * @param userId
     */
    @Transactional
    public void reback(int userId, int partyId, Integer branchId) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        if(member!=null && member.getStatus()==MemberConstants.MEMBER_STATUS_NORMAL){
            return; // 已经在党员库中无须处理
        }

        if(partyService.isDirectBranch(partyId)){
            branchId = null; // 可能已被修改为了直属党支部
        }

        commonMapper.excuteSql("update ow_member set party_id="+partyId
                + ", branch_id="+ branchId + ", status=" + MemberConstants.MEMBER_STATUS_NORMAL
                + " where user_id="+userId);

        // 更新系统角色  访客->党员
        sysUserService.changeRoleGuestToMember(userId);
    }

    // 后台数据库中导入党员数据后，需要同步信息、更新状态
    @Transactional
    public void dbUpdate(int userId) {

        EnterApplyService enterApplyService = CmTag.getBean(EnterApplyService.class);
        enterApplyService.checkCurrentApply(userId, OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY);

        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();
        if (uv.isTeacher()) {

            syncService.snycTeacherInfo(userId, uv.getCode());
        } else {
            syncService.snycStudent(userId, uv.getCode(), type);
        }

        // 更新系统角色  访客->党员
        sysUserService.changeRole(userId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);
    }

    // 批量导入
    @Transactional
    public int batchImportInSchool(List<Member> records) {

        int addCount = 0;
        for (Member record : records) {
            if (addOrUpdate(record, "批量导入")) {
                addCount++;
            }
        }

        return addCount;
    }

    // 批量导入
    @Transactional
    public int batchImportOutSchool(List<Member> records,
                                    List<TeacherInfo> teacherInfos,
                                    List<SysUser> sysUsers,
                                    List<SysUserInfo> sysUserInfos) {

        int addCount = 0;
        for (Member record : records) {
            if (addOrUpdate(record, "批量导入")) {
                addCount++;
            }
        }

        for (TeacherInfo teacherInfo : teacherInfos) {
            teacherInfoService.updateByPrimaryKeySelective(teacherInfo);
        }
        for (SysUser sysUser : sysUsers) {
            sysUserService.updateUserType(sysUser.getId(), sysUser.getType());
        }

        for (SysUserInfo sysUserInfo : sysUserInfos) {
            sysUserService.insertOrUpdateUserInfoSelective(sysUserInfo);
        }

        return addCount;
    }

    @Transactional
    public boolean addOrUpdate(Member record, String remark) {

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();
        if (uv.isTeacher()) {

            syncService.snycTeacherInfo(userId, uv.getCode());
        } else if (type == SystemConstants.USER_TYPE_BKS) {

            syncService.snycStudent(userId, uv.getCode(), type);
        } else if (type == SystemConstants.USER_TYPE_SS
                || type == SystemConstants.USER_TYPE_BS) {

            syncService.snycStudent(userId, uv.getCode(), type);
        } else {
            throw new OpException("账号不是教工或学生。" + uv.getCode() + "," + uv.getRealname());
        }

        boolean isAdd = false;
        Member _member = get(userId);
        if (_member != null) {
            // 允许转出后用原账号转入
            Assert.isTrue(memberMapper.updateByPrimaryKeySelective(record) == 1, "db update failed");
        } else if (_member == null) {
            Assert.isTrue(memberMapper.insertSelective(record) == 1, "db insert failed");
            isAdd = true;
        }

        // 如果是预备党员，则要进入申请入党预备党员阶段（直接添加预备党员时发生）
        MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
        memberApplyService.updateByMember(userId);

        // 更新系统角色  访客->党员
        sysUserService.changeRole(userId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);

        // 记录修改日志
        addModify(userId, remark);

        return isAdd;
    }


    /*@Transactional
    public void del(Integer userId){

        memberMapper.deleteByPrimaryKey(userId);
    }*/

    @Transactional
    public void changeBranch(Integer[] userIds, int partyId, int branchId) {

        if (userIds == null || userIds.length == 0) return;

        // 要求转移的用户状态正常，且都属于partyId
        MemberExample example = new MemberExample();
        example.createCriteria().andPartyIdEqualTo(partyId).andUserIdIn(Arrays.asList(userIds))
                .andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
        int count = (int) memberMapper.countByExample(example);
        if (count != userIds.length) {
            throw new OpException("数据请求有误，请重新选择");
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Branch branch = branchMap.get(branchId);
        if (branch.getPartyId().intValue() != partyId) {
            throw new OpException("数据请求有误，请重新选择");
        }

        Member record = new Member();
        record.setBranchId(branchId);
        memberMapper.updateByExampleSelective(record, example);

        for (Integer userId : userIds) {
            // 保留历史记录
            addModify(userId, "内部组织关系变动");
        }

        MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
        for (int userId : userIds) { // 更新党员发展的预备党员
            memberApplyService.updateByMember(userId);
        }
    }

    @Transactional
    public void changeParty(Integer[] userIds, int partyId, Integer branchId) {

        if (userIds == null || userIds.length == 0) return;

        // 不判断userIds中分党委和党支部是转移的情况
        MemberExample example = new MemberExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds))
                .andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
        int count = (int) memberMapper.countByExample(example);
        if (count != userIds.length) {
            throw new OpException("数据请求有误，请重新选择[0]");
        }
        if (branchId != null) {
            Map<Integer, Branch> branchMap = branchService.findAll();
            Branch branch = branchMap.get(branchId);
            if (branch.getPartyId().intValue() != partyId) {
                throw new OpException("数据请求有误，请重新选择[1]");
            }
        } else {
            // 直属党支部
            if (!partyService.isDirectBranch(partyId)) {
                throw new OpException("数据请求有误，请重新选择[2]");
            }
        }

        iMemberMapper.changeMemberParty(partyId, branchId, example);

        for (Integer userId : userIds) {
            // 保留历史记录
            addModify(userId, "校内组织关系转移");
        }

        MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
        for (int userId : userIds) { // 更新党员发展的预备党员
            memberApplyService.updateByMember(userId);
        }
    }

    @Transactional
    public void batchDel(Integer[] userIds, boolean denyApply) {

        if (userIds == null || userIds.length == 0) return;
        {
            MemberExample example = new MemberExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            memberMapper.deleteByExample(example);
        }

        if (denyApply) {
            MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
            // 删除党员发展信息（预备党员、正式党员)
            for (Integer userId : userIds) {
                memberApplyService.denyWhenDeleteMember(userId);
            }
        }

        // 删除组织关系转出、出国党员暂留、校内转接、党员流出
        {
            MemberOutExample example = new MemberOutExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<MemberOut> memberOuts = memberOutMapper.selectByExample(example);
            if (memberOuts.size() > 0) {
                logger.info(logService.log(LogConstants.LOG_MEMBER, "批量删除组织关系转出：" + JSONUtils.toString(memberOuts, false)));
                memberOutMapper.deleteByExample(example);
            }
        }
        {
            MemberStayExample example = new MemberStayExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<MemberStay> memberStays = memberStayMapper.selectByExample(example);
            if (memberStays.size() > 0) {
                logger.info(logService.log(LogConstants.LOG_MEMBER, "批量删除出国党员暂留：" + JSONUtils.toString(memberStays, false)));
                memberStayMapper.deleteByExample(example);
            }
        }
        {
            MemberTransferExample example = new MemberTransferExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExample(example);
            if (memberTransfers.size() > 0) {
                logger.info(logService.log(LogConstants.LOG_MEMBER, "批量删除校内转接：" + JSONUtils.toString(memberTransfers, false)));
                memberTransferMapper.deleteByExample(example);
            }
        }
        {
            MemberOutflowExample example = new MemberOutflowExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<MemberOutflow> memberOutflows = memberOutflowMapper.selectByExample(example);
            if (memberOutflows.size() > 0) {
                logger.info(logService.log(LogConstants.LOG_MEMBER, "批量删除党员流出：" + JSONUtils.toString(memberOutflows, false)));
                memberOutflowMapper.deleteByExample(example);
            }
        }

        for (Integer userId : userIds) {
            // 更新系统角色  党员->访客
            sysUserService.changeRole(userId, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_GUEST);
        }
    }

    // 系统内部使用，更新党员状态、党籍状态等
    @Transactional
    public void updateByPrimaryKeySelective(Member record) {

        Integer userId = record.getUserId();
        if (record.getPartyId() != null && record.getBranchId() == null) {
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member", "user_id", userId, record.getPartyId());
        }

        Byte politicalStatus = record.getPoliticalStatus();
        if (politicalStatus == null) {
            Member member = memberMapper.selectByPrimaryKey(userId);
            politicalStatus = member.getPoliticalStatus();
        }
        // 更新为预备党员时，删除转正时间
        if (politicalStatus != null && politicalStatus == MemberConstants.MEMBER_POLITICAL_STATUS_GROW) {
            record.setPositiveTime(null);
            commonMapper.excuteSql("update ow_member set positive_time=null where user_id=" + userId);
        }

        memberMapper.updateByPrimaryKeySelective(record);

        MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
        memberApplyService.updateByMember(userId);
    }

    // 修改党籍信息时使用，保留修改记录
    @Transactional
    public void updateByPrimaryKeySelective(Member record, String reason) {

        Integer userId = record.getUserId();
        {
            MemberModifyExample example = new MemberModifyExample();
            example.createCriteria().andUserIdEqualTo(record.getUserId());
            if (memberModifyMapper.countByExample(example) == 0) { // 第一次修改，需要保留原纪录
                addModify(userId, "初始记录");
            }
        }

        // 先保留历史记录
        addModify(userId, reason);

        updateByPrimaryKeySelective(record);

        if(record.getStatus()!=null && record.getStatus()==MemberConstants.MEMBER_STATUS_NORMAL){
            sysUserService.addRole(userId, RoleConstants.ROLE_MEMBER);
        }
    }

    public void addModify(int userId, String reason) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        if (member == null) return;
        MemberModify modify = new MemberModify();

        try {
            PropertyUtils.copyProperties(modify, member);
        } catch (Exception e) {
            logger.error("异常", e);
        }
        modify.setReason(reason);
        modify.setOpUserId(ShiroHelper.getCurrentUserId());
        modify.setOpTime(new Date());
        modify.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));
        memberModifyMapper.insertSelective(modify);
    }

    // 修改党籍状态
    @Transactional
    public void modifyStatus(int userId, byte politicalStatus, String remark) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        if (member != null && member.getPoliticalStatus() != politicalStatus &&
                MemberConstants.MEMBER_POLITICAL_STATUS_MAP.containsKey(politicalStatus)) {
            Member record = new Member();
            record.setUserId(userId);
            record.setPoliticalStatus(politicalStatus);
            updateByPrimaryKeySelective(record, StringUtils.defaultIfBlank(remark, "修改党籍状态为"
                    + MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(politicalStatus)));

            MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
            memberApplyService.updateByMember(userId);
        }
    }

    // 更换党员的学工号（不影响账号，仅调换党员库中的userId，需要变更角色）
    @Transactional
    public void exchangeMemberCode(int oldUserId, int newUserId, String remark) {

        if(oldUserId ==newUserId) return;

        SysUserView newUser = sysUserService.findById(newUserId);
        String newCode = newUser.getCode();
        Member checkMember = memberMapper.selectByPrimaryKey(newUserId);
        if (checkMember != null) {

            throw new OpException("{0}({1})已经在党员库中({2})，无法更换",
                    newUser.getRealname(), newCode,
                    MemberConstants.MEMBER_STATUS_MAP.get(checkMember.getStatus()));
        }

        SysUserView oldUser = sysUserService.findById(oldUserId);
        String oldCode = oldUser.getCode();

        commonMapper.excuteSql("update ow_member set user_id=" + newUserId +" where user_id=" + oldUserId);

        // 更新新的学工号的系统角色  访客->党员
        sysUserService.changeRole(newUserId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);
        sysUserService.changeRole(oldUserId, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_GUEST);

        addModify(oldUserId, "更换学工号" + oldCode + "->" + newCode + "，" + remark);
    }

    //更新党员信息完整度
    public void updateIntegrity(){

        MemberViewExample example = new MemberViewExample();
        example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
        List<MemberView> memberViews = memberViewMapper.selectByExample(example);

        for (MemberView memberView : memberViews){

            checkIntegrity(memberView);
        }
    }

    public void checkIntegrity(MemberView memberView){

        if (memberView==null) return;

        Double a = 0.00; Double b = 0.00;
        if (memberView.getGender()!=null){a++;}//性别
        b++;
        if (memberView.getBirth()!=null){a++;}//出生日期
        b++;
        if (StringUtils.isNotBlank(memberView.getNation())){a++;}//民族
        b++;
        if (memberView.getPoliticalStatus()!=null){a++;}//政治面貌
        b++;
        if (memberView.getPartyId()!=null){a++;}//所属二级单位党组织
        b++;
        if (memberView.getBranchId()!=null){a++;b++;}//所在党支部 直属党支部BranchId为空,不计入总数

        if (memberView.getGrowTime()!=null){a++;}//入党时间
        b++;
        if (memberView.getPoliticalStatus()==MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE ){//正式党员
            if (memberView.getPositiveTime()!=null){a++;}//转正时间
            b++;
        }
        if(memberView.getPoliticalStatus()==MemberConstants.MEMBER_POLITICAL_STATUS_GROW &&
        isOverTime(memberView.getGrowTime(),13)){//预备党员 入党时间与当前时间相差13个月
            if (memberView.getPositiveTime()!=null){a++;}//转正时间
            b++;
        }

        if (memberView.getUserType() == SystemConstants.USER_TYPE_BKS
        || memberView.getUserType() == SystemConstants.USER_TYPE_SS
        || memberView.getUserType() == SystemConstants.USER_TYPE_BS ){//类型为“学生”时，计算是否出国留学
            a++;b++;//是否出国留学
        }

        BigDecimal molecule = new BigDecimal(a);
        BigDecimal denominator = new BigDecimal(b);

        Member member = new Member();
        member.setUserId(memberView.getUserId());
        member.setIntegrity(molecule.divide(denominator,2,BigDecimal.ROUND_HALF_UP));

        memberMapper.updateByPrimaryKeySelective(member);
    }

    public Boolean isOverTime(Date date,Integer gapMonth){

        return DateUtils.monthOffNow(date)>gapMonth;
    }

    // 查询用户是否是支部成员
    public boolean isMember(Integer userId, Integer partyId, Integer branchId) {
        if (userId == null) return false;
        if (partyId == null && branchId == null) return false;
        MemberExample example = new MemberExample();
        MemberExample.Criteria criteria=example.createCriteria().andUserIdEqualTo(userId);

        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        List<Member> records = memberMapper.selectByExample(example);
        return records.size() == 0 ? false : true;

    }

    /*党员信息一键导入(包括党员发展过程的对象)
        导入时需要先导入正式党员和预备党员，然后在导入发展党员
        导入党员数据，如果有二级党委和党支部的编码，则导入，为空时，按规则生成。
        同时会更新ow_member_apply的数据
    */
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "Branch:ALL", allEntries = true),
            @CacheEvict(value = "Party:ALL", allEntries = true),
    })
    public Map<String, Object> importMemberAllInfo(XSSFWorkbook workbook,
                                                   XSSFSheet sheet,
                                                   List<Map<Integer, String>> xlsRows,
                                                   Map<String, Byte> politicalStatusMap,
                                                   String startCode) {

        Date now = new Date();
        List<Member> records = new ArrayList<>();
        List<MemberApply> applyRecords = new ArrayList<>();

        Map<String, Byte> stageMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : OwConstants.OW_APPLY_STAGE_MAP.entrySet()) {

            stageMap.put(entry.getValue(), entry.getKey());
        }
        int row = 1;
        Integer partyId = null;
        Integer branchId = null;
        int partyAdd = 0;
        int branchAdd = 0;
        int cellNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;

            String _stage = StringUtils.trimToNull(xlsRow.get(7));//党籍状态，如果不是正式党员或预备党员，放在党员发展中。
            if (StringUtils.isBlank(_stage)) {
                throw new OpException("第{0}行党籍状态为空", row);
            }
            //添加到党员发展中
            if (!politicalStatusMap.containsKey(_stage)){

                MemberApply memberApply = new MemberApply();
                String userCode = ContentUtils.trimAll(xlsRow.get(0));
                String idcard = ContentUtils.trimAll(xlsRow.get(2));
                Map<String, List<String>> codeMap = new HashMap<>();
                if (StringUtils.isBlank(userCode)) {
                    //通过身份证号得到userCode
                    if (idcard == null) {
                        throw new OpException("第{0}行身份证号和学工号都为空", row);
                    }
                    Byte _type = 0;
                    //提取学工号
                    codeMap = sysUserService.getCodes(_type, _type, idcard, _type, null);
                    if (codeMap.size()==0)
                        continue;
                    userCode = codeMap.keySet().iterator().next();
                    XSSFRow _row = sheet.getRow(row - 1);
                    XSSFCell cell = _row.getCell(0);
                    if(cell==null){
                        cell = _row.createCell(0);
                    }
                    cell.setCellValue(StringUtils.trimToEmpty(userCode));
                    if (codeMap.get(userCode).size() >= 1) {
                        //设置字体
                        XSSFFont font = workbook.createFont();
                        if (codeMap.get(userCode).size() > 1) {
                            font.setColor(HSSFColor.RED.index);
                        }

                        //设置样式
                        XSSFCellStyle style = workbook.createCellStyle();
                        style.setFont(font);
                        cell.setCellStyle(style);

                        cell = _row.createCell(cellNum);
                        cell.setCellValue(StringUtils.join(codeMap.get(userCode), "、"));
                    }
                }
                SysUserView uv = sysUserService.findByCode(userCode);
                if (uv == null) {
                    throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
                }
                memberApply.setUserId(uv.getId());

                //分党委
                String partyName = ContentUtils.trimAll(xlsRow.get(3));
                //String partyCode = StringUtils.trimToNull(xlsRow.get(4));
                Party party = new Party();
                if (StringUtils.isBlank(partyName)){
                    throw new OpException("第{0}行分党委名称为空", row);
                }else {
                    PartyExample example = new PartyExample();
                    example.createCriteria().andNameLike(partyName).andIsDeletedEqualTo(false);
                    List<Party> parties = partyMapper.selectByExample(example);
                    if (parties.size() >= 1){
                        party = parties.get(0);
                        partyId = party.getId();
                    }
                }

                memberApply.setPartyId(party.getId());

                if (!partyService.isDirectBranch(partyId)) {
                    String branchName = ContentUtils.trimAll(xlsRow.get(5));
                    String _branchCode = ContentUtils.trimAll(xlsRow.get(6));
                    Branch branch = new Branch();
                    if (StringUtils.isBlank(branchName)) {
                        throw new OpException("第{0}行党支部名称为空", row);
                    }
                    BranchExample example = new BranchExample();
                    example.createCriteria().andPartyIdEqualTo(partyId).andNameEqualTo(branchName).andIsDeletedEqualTo(false);
                    List<Branch> branches = branchMapper.selectByExample(example);
                    if (branches.size() >= 1) {
                        branch = branches.get(0);
                    }

                    memberApply.setBranchId(branch.getId());
                }

                int col = 8;
                Byte stage = stageMap.get(_stage);
                if (stage == null) {
                    throw new OpException("第{0}行党籍状态[{1}]不存在", row, _stage);
                }
                memberApply.setStage(stage);

                col++;
                memberApply.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
                memberApply.setFillTime(memberApply.getApplyTime());

                memberApply.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
                memberApply.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
                memberApply.setSponsorUsers(StringUtils.trimToNull(xlsRow.get(col++)));

                memberApply.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

                memberApply.setIsRemove(false);
                memberApply.setCreateTime(now);

                /*
                //是否需要判断数据的逻辑
                try{
                    extCommonService.checkMemberApplyData(memberApply);
                }catch (OpException ex){
                    throw new OpException("第{0}行数据有误:" + ex.getMessage(), row);
                }*/

                applyRecords.add(memberApply);

                continue;
            }

            Member record = new Member();
            String userCode = ContentUtils.trimAll(xlsRow.get(0));
            String idcard = ContentUtils.trimAll(xlsRow.get(2));
            Map<String, List<String>> codeMap = new HashMap<>();
            if (StringUtils.isBlank(userCode)) {
                //通过身份证号得到userCode
                if (idcard == null) {
                    throw new OpException("第{0}行身份证号和学工号都为空", row);
                }
                Byte _type = 0;
                //提取学工号
                codeMap = sysUserService.getCodes(_type, _type, idcard, _type, null);
                if (codeMap.size()==0)
                    continue;
                userCode = codeMap.keySet().iterator().next();
                XSSFRow _row = sheet.getRow(row - 1);
                XSSFCell cell = _row.getCell(0);
                if(cell==null){
                    cell = _row.createCell(0);
                }
                cell.setCellValue(StringUtils.trimToEmpty(userCode));
                if (codeMap.get(userCode).size() >= 1) {
                    //设置字体
                    XSSFFont font = workbook.createFont();
                    if (codeMap.get(userCode).size() > 1) {
                        font.setColor(HSSFColor.RED.index);
                    }

                    //设置样式
                    XSSFCellStyle style = workbook.createCellStyle();
                    style.setFont(font);
                    cell.setCellStyle(style);

                    cell = _row.createCell(cellNum);
                    cell.setCellValue(StringUtils.join(codeMap.get(userCode), "、"));
                }
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getId());

            //分党委
            String partyName = ContentUtils.trimAll(xlsRow.get(3));
            String partyCode = StringUtils.trimToNull(xlsRow.get(4));
            Party party = new Party();
            if (StringUtils.isBlank(partyName)){
                throw new OpException("第{0}行分党委名称为空", row);
            }else {
                PartyExample example = new PartyExample();
                example.createCriteria().andNameLike(partyName).andIsDeletedEqualTo(false);
                List<Party> parties = partyMapper.selectByExample(example);
                if (parties.size() >= 1){
                    party = parties.get(0);
                }
            }

            if (party.getId() == null) {
                if (StringUtils.isBlank(startCode)){
                    throw new OpException("分党委起始编码为空", row);
                }
                if (StringUtils.isNotBlank(partyCode)){
                    party.setCode(partyCode);
                }else{
                    party.setCode(partyService.genCode(ContentUtils.trimAll(startCode)));
                }
                party.setName(partyName);
                party.setShortName("");
                MetaType partyUnitType = CmTag.getMetaTypeByName("mc_party_unit_type", "事业单位");
                party.setUnitTypeId(partyUnitType.getId());
                //党总支类别
                MetaType partyClass = null;
                if (partyName.contains("直属")){
                    partyClass = CmTag.getMetaTypeByName("mc_party_class", "直属党支部");
                }else {
                    partyClass = CmTag.getMetaTypeByName("mc_party_class", "分党委");
                }
                party.setClassId(partyClass.getId());
                //组织类型
                MetaType partyType = null;
                if (partyName.contains("附属")){
                    partyType = CmTag.getMetaTypeByName("mc_party_type", "附属学校党组织");
                }else {
                    partyType = CmTag.getMetaTypeByName("mc_party_type", "院系党组织");
                }
                party.setTypeId(partyType.getId());
                party.setIsEnterpriseBig(false);
                party.setIsEnterpriseNationalized(false);
                party.setIsSeparate(false);
                party.setCreateTime(now);
                party.setIsPycj(false);
                party.setIsBg(false);
                partyService.insertSelective(party);
                partyAdd++;
            }
            partyId = party.getId();
            partyName = party.getName();
            record.setPartyId(partyId);

            //领导班子
            PartyMemberGroup _pmg = partyMemberGroupService.getPresentGroup(partyId);
            if (_pmg == null) {
                PartyMemberGroup pmg = new PartyMemberGroup();
                pmg.setPartyId(party.getId());
                pmg.setName(partyName);
                pmg.setIsDeleted(false);
                partyMemberGroupMapper.insertSelective(pmg);
            }

            //党支部
            if (!partyService.isDirectBranch(partyId)) {
                String branchName = ContentUtils.trimAll(xlsRow.get(5));
                String _branchCode = ContentUtils.trimAll(xlsRow.get(6));
                Branch branch = new Branch();
                if (StringUtils.isBlank(branchName)){
                    throw new OpException("第{0}行党支部名称为空", row);
                }
                BranchExample example = new BranchExample();
                example.createCriteria().andPartyIdEqualTo(partyId).andNameEqualTo(branchName).andIsDeletedEqualTo(false);
                List<Branch> branches = branchMapper.selectByExample(example);
                if (branches.size() >= 1){
                    branch = branches.get(0);
                }

                if (branch.getId() == null) {//添加没有的党支部
                    branch = new Branch();
                    if (StringUtils.isNotBlank(_branchCode)){
                        branch.setCode(_branchCode);
                    }else {
                        branch.setCode(branchService.genCode(partyId));
                    }
                    branch.setPartyId(partyId);
                    branch.setName(branchName.replaceAll("\\s*", ""));
                    branch.setIsStaff(!StringUtils.containsAny(branchName, "博士", "硕士", "本科"));
                    MetaType branchUnitType = CmTag.getMetaTypeByName("mc_branch_unit_type", "事业单位");
                    branch.setUnitTypeId(branchUnitType.getId());
                    branch.setIsPrefessional(false);
                    branch.setIsBaseTeam(false);
                    branch.setIsEnterpriseBig(false);
                    branch.setIsEnterpriseNationalized(false);
                    branch.setIsUnion(false);
                    branch.setIsDeleted(false);
                    branch.setCreateTime(now);
                    branch.setSortOrder(getNextSortOrder("ow_branch",
                            "is_deleted=0 and party_id=" + partyId));
                    branchMapper.insert(branch);
                    branchAdd++;
                }
                branchId = branch.getId();
                branchName = branch.getName();
                record.setBranchId(branchId);

                //支部委员会
                BranchMemberGroup _bmg = branchMemberGroupService.getPresentGroup(branchId);
                if (_bmg == null){
                    BranchMemberGroup bmg = new BranchMemberGroup();
                    bmg.setBranchId(branch.getId());
                    bmg.setName(branchName);
                    bmg.setIsDeleted(false);
                    branchMemberGroupMapper.insertSelective(bmg);
                }
            }
            if (!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), partyId, branchId)) {
                throw new OpException("第{0}行没有权限导入（您不是该支部的管理员）", row);
            }

            int col = 7;
            String _politicalStatus = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_politicalStatus)) {
                throw new OpException("第{0}行党籍状态为空", row);
            }
            Byte politicalStatus = politicalStatusMap.get(_politicalStatus);
            if (politicalStatus == null) {
                throw new OpException("第{0}行党籍状态[{1}]有误", row, _politicalStatus);
            }
            record.setPoliticalStatus(politicalStatus);

            record.setTransferTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setSponsor(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setGrowBranch(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPositiveBranch(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartyPost(StringUtils.trimToNull(xlsRow.get(col++)));//党内职务
            record.setPartyReward(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setOtherReward(StringUtils.trimToNull(xlsRow.get(col++)));
            String _source = StringUtils.trimToNull(xlsRow.get(col++));
            Byte source = MemberConstants.MEMBER_SOURCE_IMPORT;
            if (StringUtils.isNotBlank(_source)) {
                for (Map.Entry<Byte, String> entry : MemberConstants.MEMBER_SOURCE_MAP.entrySet()) {
                    if (StringUtils.equals(entry.getValue(), _source)){
                        source = entry.getKey();
                        break;
                    }
                }
            }
            record.setSource(source);
            record.setRemark1(xlsRow.get(col++));
            record.setRemark2(xlsRow.get(col++));
            record.setRemark3(xlsRow.get(col++));
            record.setCreateTime(now);
            record.setStatus(MemberConstants.MEMBER_STATUS_NORMAL);

            String _addtYpe = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isNotBlank(_addtYpe)){
                MetaType metaType = metaTypeService.findOrCreate("mc_member_add_type", _addtYpe);
                record.setAddType(metaType.getId());
            }

            records.add(record);
        }

        int successCount = batchImportUpdate(records);
        int applyCount = memberApplyService.batchImport(applyRecords);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        resultMap.put("applyCount", applyCount);
        resultMap.put("msg", StringUtils.defaultIfBlank("success", "success"));
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size() + applyRecords.size());
        resultMap.put("partyAdd", partyAdd);
        resultMap.put("branchAdd", branchAdd);

        return resultMap;
    }

    private int batchImportUpdate(List<Member> records) {
        int addCount = 0;
        for (Member record : records) {
            if (adjust(record)) {
                addCount++;
            }
        }

        return addCount;
    }

    private Boolean adjust(Member record) {
        Integer userId = record.getUserId();
        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        boolean isAdd = false;
        Member _member = get(userId);
        if (_member != null) {
            memberMapper.updateByPrimaryKeySelective(record);
        } else {
            memberMapper.insertSelective(record);
            isAdd = true;
        }

        if (record.getPoliticalStatus() == MemberConstants.MEMBER_POLITICAL_STATUS_GROW) {
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            Date now = new Date();
            if (memberApply == null) {
                memberApply = new MemberApply();
                memberApply.setUserId(userId);
                memberApply.setPartyId(partyId);
                memberApply.setBranchId(branchId);
                memberApply.setApplyTime(record.getApplyTime() == null ? now : record.getApplyTime());
                memberApply.setActiveTime(record.getActiveTime());
                memberApply.setCandidateTime(record.getCandidateTime());
                memberApply.setGrowTime(record.getGrowTime());
                memberApply.setGrowStatus(OwConstants.OW_APPLY_STATUS_UNCHECKED);
                memberApply.setStage(OwConstants.OW_APPLY_STAGE_GROW);

                memberApply.setRemark("预备党员信息添加后同步");
                memberApply.setFillTime(now);
                memberApply.setCreateTime(now);
                memberApply.setIsRemove(false);
                memberApply.setPartyId(partyId);
                memberApply.setBranchId(branchId);

                memberApplyMapper.insertSelective(memberApply);
            } else {
                memberApply.setPartyId(partyId);
                memberApply.setBranchId(branchId);
                memberApplyMapper.updateByPrimaryKeySelective(memberApply);
            }
        }

        // 更新系统角色  访客->党员
        sysUserService.changeRole(userId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);

        return isAdd;

    }

    //用于组织关系调整
    @Transactional
    public void batchUpdate(List<Member> records){

        for (Member member : records){
            //记录调整日志
            addModify(member.getUserId(),"组织关系批量调整");
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }

    @Transactional
    public void transferToHistory(Integer[] ids, String lable, String detailReason) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        for (Integer id : ids) {

            Member member = get(id);
            member.setStatus(MemberConstants.MEMBER_STATUS_HISTORY);
            memberMapper.updateByPrimaryKeySelective(member);

            SysUserView uv = sysUserService.findById(id);
            Party party = partyMapper.selectByPrimaryKey(member.getPartyId());
            String partyName = party.getName();
            String branchName = "";
            if (member.getBranchId() != null) {
                Branch branch = branchMapper.selectByPrimaryKey(member.getBranchId());
                branchName = branch.getName();
            }

            MemberHistory record = new MemberHistory();

            PropertyUtils.copyProperties(record, uv);
            PropertyUtils.copyProperties(record, member);
            record.setLable(lable);
            record.setPartyName(partyName);
            record.setBranchName(branchName);
            record.setDetailReason(detailReason);
            memberHistoryService.insertSelective(record);

            sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                    "将"+record.getRealname()+"转移至历史党员库", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, detailReason);
        }
    }
}
