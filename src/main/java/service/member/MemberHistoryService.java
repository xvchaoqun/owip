package service.member;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberExample;
import domain.member.MemberHistory;
import domain.member.MemberHistoryExample;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.MemberService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

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

        record.setAddUserId(ShiroHelper.getCurrentUserId());
        record.setAddDate(new Date());
        record.setStatus((byte) 0);// 0 正常
        memberHistoryMapper.insertSelective(record);

        // 更新党员状态和角色
        updateMemberStatus(record.getUserId());
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
    public void recover(Integer[] ids) {

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
                    "恢复", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "恢复");
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
    public void recoverToMember(Integer[] ids, Integer partyId, Integer branchId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        for (Integer id : ids) {

            MemberHistory memberHistory = memberHistoryMapper.selectByPrimaryKey(id);
            boolean isAddUser = memberHistory.getAddUserId().equals(ShiroHelper.getCurrentUserId());
            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)
                    &&!(ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN)&&isAddUser))
                continue;
            memberHistory.setStatus((byte) 1);
            memberHistoryMapper.updateByPrimaryKeySelective(memberHistory);

            Member record = new Member();
            record.setPartyId(partyId);
            record.setStatus(MemberConstants.MEMBER_STATUS_NORMAL);
            if (branchId != null) {
                record.setBranchId(branchId);
            }

            Integer userId = memberHistory.getUserId();
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
                    "恢复党员身份", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, "恢复党员身份");
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
