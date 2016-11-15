package service.party;

import domain.member.*;
import domain.party.Branch;
import domain.party.EnterApply;
import domain.party.GraduateAbroad;
import domain.party.GraduateAbroadExample;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.ext.ExtService;
import service.helper.ContextHelper;
import service.helper.ShiroSecurityHelper;
import service.sys.LogService;
import service.sys.SysUserService;
import service.sys.SysUserSyncService;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MemberService extends BaseMapper {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserSyncService sysUserSyncService;
    @Autowired
    private EnterApplyService enterApplyService;
    @Autowired
    private MemberApplyService memberApplyService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private LogService logService;

    public Member get(int userId){

         return memberMapper.selectByPrimaryKey(userId);
    }
    /**
     * 党员出党
     * @param userId
     * @param status SystemConstants.MEMBER_STATUS_QUIT
     *               SystemConstants.MEMBER_STATUS_RETIRE
     */
    @Transactional
    public void quit(int userId, byte status){

        //Member member = memberMapper.selectByPrimaryKey(userId);
        Member record = new Member();
        record.setUserId(userId);
        record.setStatus(status);
        //record.setBranchId(member.getBranchId());
        updateByPrimaryKeySelective(record);

        // 更新系统角色  党员->访客
        SysUserView uv = sysUserService.findById(userId);
        sysUserService.changeRoleMemberToGuest(userId, uv.getUsername(), uv.getCode());
    }

    /**
     * 党员出党后重新回来
     * @param userId
     */
    @Transactional
    public void reback(int userId){

        Member record = new Member();
        record.setUserId(userId);
        record.setStatus(SystemConstants.MEMBER_STATUS_NORMAL);
        //record.setBranchId(member.getBranchId());
        int ret = updateByPrimaryKeySelective(record);
        if(ret>0) {
            // 更新系统角色  访客->党员
            SysUserView uv = sysUserService.findById(userId);
            sysUserService.changeRoleGuestToMember(userId, uv.getUsername(), uv.getCode());
        }
    }

    // 后台数据库中导入党员数据后，需要同步信息、更新状态
    @Transactional
    public void dbUpdate(int userId){

        EnterApply _enterApply = enterApplyService.getCurrentApply(userId);
        if(_enterApply!=null && _enterApply.getType()!=SystemConstants.ENTER_APPLY_TYPE_MEMBERINFLOW) {
            EnterApply enterApply = new EnterApply();
            enterApply.setId(_enterApply.getId());
            enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_PASS);
            enterApplyMapper.updateByPrimaryKeySelective(enterApply);
        }

        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();
        if(type== SystemConstants.USER_TYPE_JZG){

            // 同步教职工信息到ow_member_teacher表
            sysUserSyncService.snycTeacherInfo(userId, uv);
        }else if(type==SystemConstants.USER_TYPE_BKS){

            // 同步本科生信息到 ow_member_student表
            sysUserSyncService.snycStudent(userId, uv);
        }else if(type==SystemConstants.USER_TYPE_YJS){

            // 同步研究生信息到 ow_member_student表
            sysUserSyncService.snycStudent(userId, uv);
        }else{
            throw new DBErrorException("添加失败，该账号不是教工或学生。" + uv.getCode() + "," + uv.getRealname());
        }

        // 更新系统角色  访客->党员
        sysUserService.changeRole(userId, SystemConstants.ROLE_GUEST,
                SystemConstants.ROLE_MEMBER, uv.getUsername(), uv.getCode());
    }

    @Transactional
    public void add(Member record){

        EnterApply _enterApply = enterApplyService.getCurrentApply(record.getUserId());
        if(_enterApply!=null && _enterApply.getType()!=SystemConstants.ENTER_APPLY_TYPE_MEMBERINFLOW) {
            EnterApply enterApply = new EnterApply();
            enterApply.setId(_enterApply.getId());
            enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_PASS);
            enterApplyMapper.updateByPrimaryKeySelective(enterApply);
        }

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();
        if(type== SystemConstants.USER_TYPE_JZG){

            // 同步教职工信息到ow_member_teacher表
            record.setType(SystemConstants.MEMBER_TYPE_TEACHER); // 教职工党员
            sysUserSyncService.snycTeacherInfo(userId, uv);
        }else if(type==SystemConstants.USER_TYPE_BKS){

            // 同步本科生信息到 ow_member_student表
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT); // 学生党员
            sysUserSyncService.snycStudent(userId, uv);
        }else if(type==SystemConstants.USER_TYPE_YJS){

            // 同步研究生信息到 ow_member_student表
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT); // 学生党员
            sysUserSyncService.snycStudent(userId, uv);
        }else{
            throw new DBErrorException("添加失败，该账号不是教工或学生。" + uv.getCode() + "," + uv.getRealname());
        }

        Member _member = get(userId);
        if(_member!=null && _member.getStatus()==SystemConstants.MEMBER_STATUS_TRANSFER){
            // 允许挂职干部转出后用原账号转入
            Assert.isTrue(memberMapper.updateByPrimaryKeySelective(record) == 1);
        }else if(_member==null) {
            Assert.isTrue(memberMapper.insertSelective(record) == 1);
        }else throw new RuntimeException("数据异常，入党失败。"+ uv.getCode() + "," + uv.getRealname());

        // 如果是预备党员，则要进入申请入党预备党员阶段
        memberApplyService.addGrowApply(userId);

        // 更新系统角色  访客->党员
        sysUserService.changeRole(userId, SystemConstants.ROLE_GUEST,
                SystemConstants.ROLE_MEMBER, uv.getUsername(), uv.getCode());
    }


    /*@Transactional
    public void del(Integer userId){

        memberMapper.deleteByPrimaryKey(userId);
    }*/

    @Transactional
    public void changeBranch(Integer[] userIds, int partyId, int branchId){

        if(userIds==null || userIds.length==0) return;

        // 要求转移的用户状态正常，且都属于partyId
        MemberExample example = new MemberExample();
        example.createCriteria().andPartyIdEqualTo(partyId).andUserIdIn(Arrays.asList(userIds))
                .andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL);
        int count = memberMapper.countByExample(example);
        if(count!=userIds.length){
            throw new RuntimeException("数据异常，请重新选择");
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Branch branch = branchMap.get(branchId);
        if(branch.getPartyId().intValue()!=partyId){
            throw new RuntimeException("数据异常，请重新选择");
        }

        Member record = new Member();
        record.setBranchId(branchId);
        memberMapper.updateByExampleSelective(record,example);
    }

    @Transactional
    public void changeParty(Integer[] userIds, int partyId, Integer branchId){

        if(userIds==null || userIds.length==0) return;

        // 不判断userIds中分党委和党支部是转移的情况
        MemberExample example = new MemberExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds))
                .andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL);
        int count = memberMapper.countByExample(example);
        if(count!=userIds.length){
            throw new RuntimeException("数据异常，请重新选择[0]");
        }
        if(branchId!=null) {
            Map<Integer, Branch> branchMap = branchService.findAll();
            Branch branch = branchMap.get(branchId);
            if (branch.getPartyId().intValue() != partyId) {
                throw new RuntimeException("数据异常，请重新选择[1]");
            }
        }else{
            // 直属党支部
            if(!partyService.isDirectBranch(partyId)){
                throw new RuntimeException("数据异常，请重新选择[2]");
            }
        }

        updateMapper.changeMemberParty(partyId, branchId, example);
    }

    @Transactional
    public void batchDel(Integer[] userIds) {

        if (userIds == null || userIds.length == 0) return;
        {
            MemberExample example = new MemberExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            memberMapper.deleteByExample(example);
        }

        // 删除组织关系转出、出国党员暂留、校内转接、党员流出
        {
            MemberOutExample example = new MemberOutExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<MemberOut> memberOuts = memberOutMapper.selectByExample(example);
            if(memberOuts.size()>0) {
                logger.info(logService.log(SystemConstants.LOG_MEMBER, "批量删除组织关系转出：" + JSONUtils.toString(memberOuts)));
                memberOutMapper.deleteByExample(example);
            }
        }
        {
            GraduateAbroadExample example = new GraduateAbroadExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<GraduateAbroad> graduateAbroads = graduateAbroadMapper.selectByExample(example);
            if(graduateAbroads.size()>0) {
                logger.info(logService.log(SystemConstants.LOG_MEMBER, "批量删除出国党员暂留：" + JSONUtils.toString(graduateAbroads)));
                graduateAbroadMapper.deleteByExample(example);
            }
        }
        {
            MemberTransferExample example = new MemberTransferExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExample(example);
            if(memberTransfers.size()>0) {
                logger.info(logService.log(SystemConstants.LOG_MEMBER, "批量删除校内转接：" + JSONUtils.toString(memberTransfers)));
                memberTransferMapper.deleteByExample(example);
            }
        }
        {
            MemberOutflowExample example = new MemberOutflowExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            List<MemberOutflow> memberOutflows = memberOutflowMapper.selectByExample(example);
            if(memberOutflows.size()>0) {
                logger.info(logService.log(SystemConstants.LOG_MEMBER, "批量删除党员流出：" + JSONUtils.toString(memberOutflows)));
                memberOutflowMapper.deleteByExample(example);
            }
        }

        for (Integer userId : userIds) {
            SysUserView uv = sysUserService.findById(userId);
            // 更新系统角色  党员->访客
            sysUserService.changeRole(userId, SystemConstants.ROLE_MEMBER,
                    SystemConstants.ROLE_GUEST, uv.getUsername(), uv.getCode());
        }
    }
    // 系统内部使用，更新党员状态、党籍状态等
    @Transactional
    public int updateByPrimaryKeySelective(Member record){

        Integer userId = record.getUserId();
        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member", "user_id", userId, record.getPartyId());
        }
        return memberMapper.updateByPrimaryKeySelective(record);
    }

    // 修改党籍信息时使用，保留修改记录
    @Transactional
    public int updateByPrimaryKeySelective(Member record, String reason){

        Integer userId = record.getUserId();
        {
            MemberModifyExample example = new MemberModifyExample();
            example.createCriteria().andUserIdEqualTo(record.getUserId());
            if(memberModifyMapper.countByExample(example)==0){ // 第一次修改，需要保留原纪录
                addModify(userId, "初始记录");
            }
        }

        int count = updateByPrimaryKeySelective(record);

        addModify(userId, reason);

        return count;
    }

    public void addModify(int userId, String reason){

        MemberModify modify = new MemberModify();
        try {
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(modify, memberMapper.selectByPrimaryKey(userId));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        modify.setReason(reason);
        modify.setOpUserId(ShiroSecurityHelper.getCurrentUserId());
        modify.setOpTime(new Date());
        modify.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));
        memberModifyMapper.insertSelective(modify);
    }
}
