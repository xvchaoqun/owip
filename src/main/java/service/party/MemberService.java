package service.party;

import controller.global.OpException;
import domain.member.*;
import domain.party.Branch;
import domain.party.EnterApply;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.ext.SyncService;
import service.member.EnterApplyService;
import service.member.MemberApplyService;
import service.member.MemberBaseMapper;
import service.sys.LogService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public void reback(int userId) {

        Member record = new Member();
        record.setUserId(userId);
        record.setStatus(MemberConstants.MEMBER_STATUS_NORMAL);
        //record.setBranchId(member.getBranchId());
        int ret = updateByPrimaryKeySelective(record);
        if (ret > 0) {
            // 更新系统角色  访客->党员
            sysUserService.changeRoleGuestToMember(userId);
        }
    }

    // 后台数据库中导入党员数据后，需要同步信息、更新状态
    @Transactional
    public void dbUpdate(int userId) {

        EnterApplyService enterApplyService = CmTag.getBean(EnterApplyService.class);
        EnterApply _enterApply = enterApplyService.checkCurrentApply(userId, OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY);
        if (_enterApply != null) {
            EnterApply enterApply = new EnterApply();
            enterApply.setId(_enterApply.getId());
            enterApply.setStatus(OwConstants.OW_ENTER_APPLY_STATUS_PASS);
            enterApplyMapper.updateByPrimaryKeySelective(enterApply);
        }

        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();
        if (type == SystemConstants.USER_TYPE_JZG) {

            // 同步教职工信息到ow_member_teacher表
            syncService.snycTeacherInfo(userId, uv);
        } else if (type == SystemConstants.USER_TYPE_BKS
                || type == SystemConstants.USER_TYPE_YJS) {

            // 同步研究生信息到 ow_member_student表
            syncService.snycStudent(userId, uv);
        } else {
            throw new OpException("添加失败，该账号不是教工或学生。" + uv.getCode() + "," + uv.getRealname());
        }

        // 更新系统角色  访客->党员
        sysUserService.changeRole(userId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);
    }

    // 批量导入
    @Transactional
    public int batchImportInSchool(List<Member> records){

        int addCount = 0;
        for (Member record : records) {
            if(add(record)){
                addCount++;
            }

            addModify(record.getUserId(), "批量导入党员信息");
        }

        return addCount;
    }
    // 批量导入
    @Transactional
    public int batchImportOutSchool(List<Member> records,
                                    List<TeacherInfo> teacherInfos,
                                    List<SysUserInfo> sysUserInfos){

        int addCount = 0;
        for (Member record : records) {
            if(add(record)){
                addCount++;
            }
            addModify(record.getUserId(), "批量导入党员信息");
        }

        for (TeacherInfo teacherInfo : teacherInfos) {
            teacherInfoService.updateByPrimaryKeySelective(teacherInfo);
        }

        for (SysUserInfo sysUserInfo : sysUserInfos) {
            sysUserService.insertOrUpdateUserInfoSelective(sysUserInfo);
        }

        return addCount;
    }

    @Transactional
    public boolean add(Member record) {

        EnterApplyService enterApplyService = CmTag.getBean(EnterApplyService.class);

        EnterApply _enterApply = enterApplyService.getCurrentApply(record.getUserId());
        if (_enterApply != null) {
            EnterApply enterApply = new EnterApply();
            enterApply.setId(_enterApply.getId());
            enterApply.setStatus(OwConstants.OW_ENTER_APPLY_STATUS_PASS);
            enterApplyMapper.updateByPrimaryKeySelective(enterApply);
        }

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();
        if (type == SystemConstants.USER_TYPE_JZG) {

            // 同步教职工信息到ow_member_teacher表
            record.setType(MemberConstants.MEMBER_TYPE_TEACHER); // 教职工党员
            syncService.snycTeacherInfo(userId, uv);
        } else if (type == SystemConstants.USER_TYPE_BKS) {

            // 同步本科生信息到 ow_member_student表
            record.setType(MemberConstants.MEMBER_TYPE_STUDENT); // 学生党员
            syncService.snycStudent(userId, uv);
        } else if (type == SystemConstants.USER_TYPE_YJS) {

            // 同步研究生信息到 ow_member_student表
            record.setType(MemberConstants.MEMBER_TYPE_STUDENT); // 学生党员
            syncService.snycStudent(userId, uv);
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
        memberApplyService.addOrChangeToGrowApply(userId);

        // 更新系统角色  访客->党员
        sysUserService.changeRole(userId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);

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
            throw new OpException("数据异常，请重新选择");
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Branch branch = branchMap.get(branchId);
        if (branch.getPartyId().intValue() != partyId) {
            throw new OpException("数据异常，请重新选择");
        }

        Member record = new Member();
        record.setBranchId(branchId);
        memberMapper.updateByExampleSelective(record, example);

        MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
        for (int userId : userIds) { // 更新入党申请的预备党员
            memberApplyService.updateWhenModifyMember(userId, record.getPartyId(), record.getBranchId());
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
            throw new OpException("数据异常，请重新选择[0]");
        }
        if (branchId != null) {
            Map<Integer, Branch> branchMap = branchService.findAll();
            Branch branch = branchMap.get(branchId);
            if (branch.getPartyId().intValue() != partyId) {
                throw new OpException("数据异常，请重新选择[1]");
            }
        } else {
            // 直属党支部
            if (!partyService.isDirectBranch(partyId)) {
                throw new OpException("数据异常，请重新选择[2]");
            }
        }

        iMemberMapper.changeMemberParty(partyId, branchId, example);

        MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
        for (int userId : userIds) { // 更新入党申请的预备党员
            memberApplyService.updateWhenModifyMember(userId, partyId, branchId);
        }
    }

    @Transactional
    public void batchDel(Integer[] userIds) {

        if (userIds == null || userIds.length == 0) return;
        {
            MemberExample example = new MemberExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            memberMapper.deleteByExample(example);
        }

        MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
        // 删除入党申请（预备党员、正式党员)
        for (Integer userId : userIds) {
            memberApplyService.denyWhenDeleteMember(userId);
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
    public int updateByPrimaryKeySelective(Member record) {

        Integer userId = record.getUserId();
        if (record.getPartyId() != null && record.getBranchId() == null) {
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member", "user_id", userId, record.getPartyId());
        }

        if (record.getPartyId() != null) {
            MemberApplyService memberApplyService = CmTag.getBean(MemberApplyService.class);
            memberApplyService.updateWhenModifyMember(userId, record.getPartyId(), record.getBranchId());
        }

        Byte politicalStatus = record.getPoliticalStatus();
        if(politicalStatus==null) {
            Member member = memberMapper.selectByPrimaryKey(userId);
            politicalStatus = member.getPoliticalStatus();
        }
        // 更新为预备党员时，删除转正时间
        if(politicalStatus!=null &&  politicalStatus== MemberConstants.MEMBER_POLITICAL_STATUS_GROW){
            record.setPositiveTime(null);
            commonMapper.excuteSql("update ow_member set positive_time=null where user_id=" + userId);
        }

        return memberMapper.updateByPrimaryKeySelective(record);
    }

    // 修改党籍信息时使用，保留修改记录
    @Transactional
    public int updateByPrimaryKeySelective(Member record, String reason) {

        Integer userId = record.getUserId();
        {
            MemberModifyExample example = new MemberModifyExample();
            example.createCriteria().andUserIdEqualTo(record.getUserId());
            if (memberModifyMapper.countByExample(example) == 0) { // 第一次修改，需要保留原纪录
                addModify(userId, "初始记录");
            }
        }

        int count = updateByPrimaryKeySelective(record);

        addModify(userId, reason);

        return count;
    }

    public void addModify(int userId, String reason) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        if (member == null) return;
        MemberModify modify = new MemberModify();
        try {
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(modify, member);
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
            if (politicalStatus == MemberConstants.MEMBER_POLITICAL_STATUS_GROW) {
                // 正式->预备，需要同时修改或添加入党申请【6.预备党员】阶段
                memberApplyService.addOrChangeToGrowApply(userId);
            } else {
                // 预备->正式，需要同时修改入党申请【6.预备党员】阶段->【7.正式党员】阶段
                memberApplyService.modifyMemberToPositiveStatus(userId);
            }
        }
    }
}
