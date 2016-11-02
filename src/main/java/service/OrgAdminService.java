package service;

import domain.party.OrgAdmin;
import domain.party.OrgAdminExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OrgAdminService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, int userId, Integer partyId, Integer branchId){

        Assert.isTrue(partyId!=null || branchId!=null);

        OrgAdminExample example = new OrgAdminExample();
        OrgAdminExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        if(id!=null) criteria.andIdNotEqualTo(id);

        return orgAdminMapper.countByExample(example) > 0;
    }
    @Transactional
    @CacheEvict(value="AdminPartyIdList", key = "#userId")
    public int addPartyAdmin(int userId, int partyId){

        Assert.isTrue(!idDuplicate(null, userId, partyId, null));

        SysUserView sysUser = sysUserService.findById(userId);

        // 见PartyMemberAdminService.toggleAdmin
        // 添加账号的"分党委管理员"角色
        // 如果账号是现任班子的管理员， 且没有"分党委管理员"角色，则添加
        Set<String> roleStrSet = sysUserService.findRoles(sysUser.getUsername());
        if (!roleStrSet.contains(SystemConstants.ROLE_PARTYADMIN)) {
            sysUserService.addRole(userId, SystemConstants.ROLE_PARTYADMIN, sysUser.getUsername(), sysUser.getCode());
        }

        OrgAdmin record = new OrgAdmin();
        record.setUserId(userId);
        record.setPartyId(partyId);
        record.setCreateTime(new Date());

        return orgAdminMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="AdminBranchIdList", key = "#userId")
    public int addBranchAdmin(int userId, int branchId){

        Assert.isTrue(!idDuplicate(null, userId, null, branchId));

        SysUserView sysUser = sysUserService.findById(userId);

        // 见 BranchMemberAdminService.toggleAdmin
        // 添加账号的"党支部管理员"角色
        // 如果账号是现任班子的管理员， 且没有"党支部管理员"角色，则添加
        Set<String> roleStrSet = sysUserService.findRoles(sysUser.getUsername());
        if (!roleStrSet.contains(SystemConstants.ROLE_BRANCHADMIN)) {
            sysUserService.addRole(userId, SystemConstants.ROLE_BRANCHADMIN, sysUser.getUsername(), sysUser.getCode());
        }

        OrgAdmin record = new OrgAdmin();
        record.setUserId(userId);
        record.setBranchId(branchId);
        record.setCreateTime(new Date());

        return orgAdminMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "AdminPartyIdList", key = "#userId"),
            @CacheEvict(value = "AdminBranchIdList", key = "#userId")
    })
    public void del(Integer id, int userId){

        OrgAdmin orgAdmin = orgAdminMapper.selectByPrimaryKey(id);
        Assert.isTrue(orgAdmin.getUserId().intValue() == userId);

        SysUserView sysUser = sysUserService.findById(userId);

        // 先删除
        orgAdminMapper.deleteByPrimaryKey(id);

        if(orgAdmin.getPartyId()!=null) {
            // 见PartyMemberAdminService.toggleAdmin
            // 删除账号的"分党委管理员"角色
            // 如果他只是该分党委的管理员，则删除账号所属的"分党委管理员"角色； 否则不处理
            List<Integer> partyIdList = commonMapper.adminPartyIdList(userId);
            if (partyIdList.size() == 0) {
                sysUserService.delRole(userId, SystemConstants.ROLE_PARTYADMIN, sysUser.getUsername(), sysUser.getCode());
            }
        }

        if(orgAdmin.getBranchId()!=null) {
            // BranchMemberAdminService.toggleAdmin
            // 删除账号的"党支部管理员"角色
            // 如果他只是该党支部的管理员，则删除账号所属的"党支部管理员"角色； 否则不处理
            List<Integer> branchIdList = commonMapper.adminBranchIdList(userId);
            if (branchIdList.size() == 0) {
                sysUserService.delRole(userId, SystemConstants.ROLE_BRANCHADMIN, sysUser.getUsername(), sysUser.getCode());
            }
        }
    }
}
