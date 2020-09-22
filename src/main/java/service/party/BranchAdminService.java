package service.party;

import domain.party.BranchMember;
import domain.party.BranchMemberGroup;
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
    @CacheEvict(value="AdminBranchIdList", key = "#result")
    public int setBranchAdmin(int branchMemberId, boolean isAdmin) {

        BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(branchMemberId);
        int userId = branchMember.getUserId();
        boolean isHistory = BooleanUtils.isTrue(branchMember.getIsHistory());
        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());
        boolean isDeleted = BooleanUtils.isTrue(branchMemberGroup.getIsDeleted());

        if(isHistory||isDeleted){ // 已离任或非现任支委，则删除管理员权限
            isAdmin = false;
        }

        BranchMember record = new BranchMember();
        record.setId(branchMemberId);
        record.setIsAdmin(isAdmin);
        branchMemberMapper.updateByPrimaryKeySelective(record);

        if (isAdmin) {
            sysUserService.addRole(userId, RoleConstants.ROLE_BRANCHADMIN);
        }

        if (adminBranchIdCount(userId) == 0) {
            sysUserService.delRole(userId, RoleConstants.ROLE_BRANCHADMIN);
        }

        return userId;
    }
}
