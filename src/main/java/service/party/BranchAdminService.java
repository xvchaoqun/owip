package service.party;

import domain.party.BranchMember;
import domain.party.BranchMemberGroup;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fafa on 2016/1/19.
 */
@Service
public class BranchAdminService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    @Cacheable(value="AdminBranchIdList", key="#userId")
    public List<Integer> adminBranchIdList(int userId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setUserId(userId);
        List<OwAdmin> owAdmins = iPartyMapper.selectBranchAdminList(owAdmin, new RowBounds());

        return owAdmins.stream().map(OwAdmin::getBranchId).collect(Collectors.toList());
    }

    // 管理支部数量
    public int adminBranchIdCount(int userId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setUserId(userId);

        return iPartyMapper.countBranchAdminList(owAdmin);
    }
    // 是否管理支部
    public boolean adminBranch(int userId, int branchId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setUserId(userId);
        owAdmin.setBranchId(branchId);

        return iPartyMapper.countBranchAdminList(owAdmin)>0;
    }

    // 支部管理员ID列表
    public List<Integer> adminBranchUserIdList(int branchId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setBranchId(branchId);
        List<OwAdmin> owAdmins = iPartyMapper.selectBranchAdminList(owAdmin, new RowBounds());

        return owAdmins.stream().map(OwAdmin::getUserId).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value="AdminBranchIdList", key = "#branchMember.userId")
    public void toggleAdmin(BranchMember branchMember) {

        boolean isAdmin = BooleanUtils.isTrue(branchMember.getIsAdmin());
        BranchMember record = new BranchMember();
        record.setId(branchMember.getId());
        record.setIsAdmin(!isAdmin);
        branchMemberMapper.updateByPrimaryKeySelective(record); // 必须先更新，保证下面的判断正确

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());
        if (branchMemberGroup.getIsPresent()) { // 只有当前班子是现任班子才操作

            Integer userId = branchMember.getUserId();

            if (isAdmin) {
                // 删除账号的"党支部管理员"角色
                // 如果他只是该党支部的管理员，则删除账号所属的"党支部管理员"角色； 否则不处理
                if (adminBranchIdCount(userId) == 0) {
                    sysUserService.delRole(userId, RoleConstants.ROLE_BRANCHADMIN);
                }
            } else {
                // 添加账号的"党支部管理员"角色
                // 如果账号是现任班子的管理员， 且没有"党支部管理员"角色，则添加
                SysUserView sysUser = sysUserService.findById(userId);
                if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_BRANCHADMIN)) {
                    sysUserService.addRole(userId, RoleConstants.ROLE_BRANCHADMIN);
                }
            }
        }
    }
}
