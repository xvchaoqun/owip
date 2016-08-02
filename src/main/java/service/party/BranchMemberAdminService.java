package service.party;

import domain.party.BranchMember;
import domain.party.BranchMemberGroup;

import domain.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.List;
import java.util.Set;

/**
 * Created by fafa on 2016/1/19.
 */
@Service
public class BranchMemberAdminService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    @Cacheable(value="AdminBranchIdList", key="#userId")
    public List<Integer> adminBranchIdList(int userId){

        return commonMapper.adminBranchIdList(userId);
    }

    @Transactional
    @CacheEvict(value="AdminBranchIdList", key = "#branchMember.userId")
    public void toggleAdmin(BranchMember branchMember) {

        org.eclipse.jdt.internal.core.Assert.isTrue(branchMember != null && branchMember.getId() != null
                && branchMember.getIsAdmin() != null && branchMember.getUserId() != null && branchMember.getGroupId() != null);

        BranchMember record = new BranchMember();
        record.setId(branchMember.getId());
        record.setIsAdmin(!branchMember.getIsAdmin());
        branchMemberMapper.updateByPrimaryKeySelective(record); // 必须先更新，保证下面的判断正确

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());
        if (branchMemberGroup.getIsPresent()) { // 只有当前班子是现任班子才操作

            Integer userId = branchMember.getUserId();
            SysUser sysUser = sysUserService.findById(userId);
            if (branchMember.getIsAdmin()) {
                // 删除账号的"党支部管理员"角色
                // 如果他只是该党支部的管理员，则删除账号所属的"党支部管理员"角色； 否则不处理
                List<Integer> branchIdList = commonMapper.adminBranchIdList(userId);
                if (branchIdList.size() == 0) {
                    sysUserService.delRole(userId, SystemConstants.ROLE_BRANCHADMIN, sysUser.getUsername(), sysUser.getCode());
                }
            } else {
                // 添加账号的"党支部管理员"角色
                // 如果账号是现任班子的管理员， 且没有"党支部管理员"角色，则添加
                Set<String> roleStrSet = sysUserService.findRoles(sysUser.getUsername());
                if (!roleStrSet.contains(SystemConstants.ROLE_BRANCHADMIN)) {
                    sysUserService.addRole(userId, SystemConstants.ROLE_BRANCHADMIN, sysUser.getUsername(), sysUser.getCode());
                }
            }
        }
    }
}
