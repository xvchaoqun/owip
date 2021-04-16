package service.member;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberExample;
import domain.member.MemberHistory;
import domain.member.MemberHistoryExample;
import domain.sys.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.MemberService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.PasswordHelper;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.SaltPassword;
import sys.tags.CmTag;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class MemberHistoryService extends MemberBaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private ApplyApprovalLogService applyApprovalLogService;

    public boolean isDuplicate(String realname, String code, String idcard){

        MemberHistoryExample example = new MemberHistoryExample();
        MemberHistoryExample.Criteria criteria = example.createCriteria().andRealnameEqualTo(realname).andCodeEqualTo(code)
                .andStatusEqualTo((byte) 0);
        if (StringUtils.isNotBlank(idcard)) {
            criteria.andIdcardEqualTo(idcard);
        }

        return memberHistoryMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(MemberHistory record){

        if (isDuplicate(record.getRealname(), record.getCode(), record.getIdcard())) {
            throw new OpException("添加重复");
        }

        record.setAddUserId(ShiroHelper.getCurrentUserId());
        record.setAddDate(new Date());
        record.setStatus((byte) 0);// 0 正常
        memberHistoryMapper.insertSelective(record);

        // 更新党员状态和角色
        updateMemberStatus(record.getUserId());
    }

    @Transactional
    public void generalInsert(MemberHistory record, Integer generalUser){

        // 是否生成账号
        if (generalUser == 1) {
            String prefix = CmTag.getStringProperty("memberRegPrefix", "dy");
            String code = sysUserService.genCode(prefix);
            String passwd = RandomStringUtils.randomNumeric(6);

            SysUser sysUser = new SysUser();
            sysUser.setUsername(code);
            sysUser.setCode(code);
            sysUser.setLocked(false);
            SaltPassword encrypt = passwordHelper.encryptByRandomSalt(passwd);
            sysUser.setSalt(encrypt.getSalt());
            sysUser.setPasswd(encrypt.getPassword());
            sysUser.setCreateTime(new Date());
            sysUser.setType(record.getType());
            sysUser.setSource(SystemConstants.USER_SOURCE_REG);
            sysUser.setRoleIds(sysUserService.buildRoleIds(RoleConstants.ROLE_GUEST));
            sysUserService.insertSelective(sysUser);

            SysUserInfo sysUserInfo = new SysUserInfo();
            sysUserInfo.setUserId(sysUser.getId());
            sysUserInfo.setRealname(record.getRealname());
            sysUserInfo.setIdcard(record.getIdcard());
            sysUserInfo.setGender(record.getGender());
            sysUserInfo.setBirth(record.getBirth());
            sysUserInfo.setNation(record.getNation());
            sysUserInfo.setNativePlace(record.getNativePlace());
            sysUserInfo.setMobile(record.getMobile());
            sysUserInfo.setEmail(record.getEmail());
            sysUserService.insertOrUpdateUserInfoSelective(sysUserInfo);

            if (record.getType() == SystemConstants.USER_TYPE_JZG
                    || record.getType() == SystemConstants.USER_TYPE_RETIRE){

                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setUserId(sysUser.getId());
                teacherInfo.setProPost(record.getProPost());
                teacherInfo.setExtPhone(record.getMobile());
                teacherInfoMapper.insertSelective(teacherInfo);
            }else {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setUserId(sysUser.getId());
                studentInfo.setSyncSource((byte) 0);
                studentInfo.setCreateTime(new Date());
                studentInfoMapper.insertSelective(studentInfo);
            }

            applyApprovalLogService.add(record.getId(),
                    null, null, record.getUserId(),
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "后台添加",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);

            record.setCode(code);
        }

        insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            MemberHistory record = memberHistoryMapper.selectByPrimaryKey(id);
            boolean isAddUser = record.getAddUserId().equals(ShiroHelper.getCurrentUserId());
            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)
                    &&!(ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&isAddUser))
                continue;
            if ((ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&isAddUser)
                    ||ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)){
                memberHistoryMapper.deleteByPrimaryKey(id);
                sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                        "移除", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "移除");
            }else {
                throw new OpException("没有权限删除"+record.getRealname()+"("+record.getCode()+")");
            }
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(MemberHistory record){
        checkAuth(record.getId());
        memberHistoryMapper.updateByPrimaryKeySelective(record);

        if (record.getStatus()==0) return;// 0正常

        // 更新党员状态和角色
        updateMemberStatus(record.getUserId());

    }

    public Map<Integer, MemberHistory> findAll() {

        MemberHistoryExample example = new MemberHistoryExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<MemberHistory> records = memberHistoryMapper.selectByExample(example);
        Map<Integer, MemberHistory> map = new LinkedHashMap<>();
        for (MemberHistory record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public int bacthImport(List<MemberHistory> records) {

        int count = 0;
        if (records==null||records.size()==0) return 0;

        for (MemberHistory record : records) {

            if (isDuplicate(record.getRealname(),record.getCode(), record.getIdcard())){

                //更新时，检查权限
                boolean isAddUser = record.getAddUserId().equals(ShiroHelper.getCurrentUserId());
                if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)
                        &&!(ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&isAddUser))
                    continue;

                MemberHistoryExample example = new MemberHistoryExample();
                MemberHistoryExample.Criteria criteria = example.createCriteria().andRealnameEqualTo(record.getRealname())
                        .andCodeEqualTo(record.getCode()).andStatusEqualTo((byte) 0);
                if (record.getId() != null){
                    criteria.andIdcardEqualTo(record.getIdcard());
                }
                MemberHistory memberHistory = memberHistoryMapper.selectByExample(example).get(0);
                record.setId(memberHistory.getId());
                updateByPrimaryKeySelective(record);
                sysApprovalLogService.add(memberHistory.getId(), ShiroHelper.getCurrentUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                        "导入更新", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "导入更新");
            }else {
                insertSelective(record);
                sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                        "导入添加", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "导入添加");
                count++;
            }
        }
        return count;
    }

    @Transactional
    public void out(Integer[] ids, String outReason) {

        MemberHistoryExample example = new MemberHistoryExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<MemberHistory> memberHistoryList = memberHistoryMapper.selectByExample(example);
        for (MemberHistory record : memberHistoryList){

            boolean isAddUser = record.getAddUserId().equals(ShiroHelper.getCurrentUserId());
            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)
                    &&!(ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&isAddUser))
                continue;
            record.setOutReason(outReason);
            record.setStatus((byte) 1);
            memberHistoryMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                    "移除", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, outReason);
        }
    }

    @Transactional
    public void recover(Integer[] ids, String remark) {

        for (Integer id :ids){
            MemberHistory record = memberHistoryMapper.selectByPrimaryKey(id);
            boolean isAddUser = record.getAddUserId().equals(ShiroHelper.getCurrentUserId());
            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)
                    &&!(ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&isAddUser))
                continue;
            record.setStatus((byte) 0); // 0 正常
            updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                    "返回历史党员库", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }
    }

    public void checkAuth(Integer id){
        MemberHistory record = memberHistoryMapper.selectByPrimaryKey(id);
        boolean isAddUser = record.getAddUserId().equals(ShiroHelper.getCurrentUserId());
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)
                &&!(ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&isAddUser)){
            throw new UnauthorizedException();
        }
    }

    @Transactional
    public void recoverToMember(Integer[] ids, int partyId, Integer branchId, String remark) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        for (Integer id : ids) {

            MemberHistory memberHistory = memberHistoryMapper.selectByPrimaryKey(id);
            boolean isAddUser = memberHistory.getAddUserId().equals(ShiroHelper.getCurrentUserId());
            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)
                    &&!(ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&isAddUser))
                continue;
            memberHistory.setStatus((byte) 1);
            memberHistoryMapper.updateByPrimaryKeySelective(memberHistory);

            Integer userId = memberHistory.getUserId();

            Member record = new Member();
            record.setPartyId(partyId);
            record.setStatus(MemberConstants.MEMBER_STATUS_NORMAL);
            record.setBranchId(branchId);
            if (branchId == null) {
                commonMapper.excuteSql("update ow_member set branch_id=null where user_id=" + userId);
            }

            record.setUserId(userId);
            MemberExample example = new MemberExample();
            example.createCriteria().andUserIdEqualTo(userId);
            if (memberMapper.countByExample(example) > 0){
                memberMapper.updateByPrimaryKeySelective(record);
            }else {
                PropertyUtils.copyProperties(record, memberHistory);
                record.setUserId(userId);
                record.setSource(MemberConstants.MEMBER_SOURCE_ADMIN);
                memberService.addOrUpdate(record, "从历史党员库移至党员库");
            }

            sysUserService.changeRole(userId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);

            sysApprovalLogService.add(memberHistory.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY,
                    "返回党员库", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, remark);
        }
    }

    // 更新完历史党员库，判断是否更新党员状态
    public void updateMemberStatus(Integer userId){

        Member member = memberMapper.selectByPrimaryKey(userId);
        if (member == null) return;
        if (member.getStatus() == MemberConstants.MEMBER_STATUS_HISTORY) return;
        member.setStatus(MemberConstants.MEMBER_STATUS_HISTORY);
        memberMapper.updateByPrimaryKeySelective(member);

        sysUserService.changeRole(userId, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_GUEST);
    }
}
