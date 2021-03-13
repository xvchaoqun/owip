package service.party;

import controller.global.OpException;
import domain.party.*;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;

import java.util.Date;
import java.util.List;

@Service
public class OrgAdminService extends BaseMapper {

    @Autowired
    private BranchService branchService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PartyAdminService partyAdminService;
    @Autowired
    private BranchAdminService branchAdminService;

    @Transactional
    @CacheEvict(value = "AdminPartyIdList", key = "#userId")
    public int addPartyAdmin(int userId, int partyId) {

        if(partyAdminService.adminParty(userId, partyId)){
            throw new OpException("添加重复，已经是管理员（班子成员或其他管理员）。");
        }

        SysUserView sysUser = sysUserService.findById(userId);

        // 见PartyMemberAdminService.toggleAdmin
        // 添加账号的"分党委管理员"角色
        // 如果账号是现任班子的管理员， 且没有"分党委管理员"角色，则添加
        if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_PARTYADMIN)) {
            sysUserService.addRole(userId, RoleConstants.ROLE_PARTYADMIN);
        }

        OrgAdmin record = new OrgAdmin();
        record.setUserId(userId);
        record.setPartyId(partyId);
        record.setCreateTime(new Date());
        record.setType(OwConstants.OW_ORG_ADMIN_PARTY);

        return orgAdminMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "AdminBranchIdList", key = "#userId")
    public int addBranchAdmin(int userId, int branchId) {

        if(branchAdminService.adminBranch(userId, branchId)){
            throw new OpException("添加重复，已经是管理员（支部委员会成员或其他管理员）。");
        }

        SysUserView sysUser = sysUserService.findById(userId);

        // 见 BranchMemberAdminService.toggleAdmin
        // 添加账号的"党支部管理员"角色
        // 如果账号是现任班子的管理员， 且没有"党支部管理员"角色，则添加
        if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_BRANCHADMIN)) {
            sysUserService.addRole(userId, RoleConstants.ROLE_BRANCHADMIN);
        }

        OrgAdmin record = new OrgAdmin();
        record.setUserId(userId);
        record.setBranchId(branchId);
        record.setCreateTime(new Date());
        record.setType(OwConstants.OW_ORG_ADMIN_BRANCH);

        return orgAdminMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "AdminPartyIdList", key = "#userId"),
            @CacheEvict(value = "AdminBranchIdList", key = "#userId")
    })
    public void del(Integer id, int userId) {

        OrgAdmin orgAdmin = orgAdminMapper.selectByPrimaryKey(id);
        Assert.isTrue(orgAdmin.getUserId().intValue() == userId, "wrong userId");

        // 先删除
        orgAdminMapper.deleteByPrimaryKey(id);

        if (orgAdmin.getPartyId() != null) {
            // 见PartyMemberAdminService.toggleAdmin
            // 删除账号的"分党委管理员"角色
            // 如果他只是该分党委的管理员，则删除账号所属的"分党委管理员"角色； 否则不处理
            if (partyAdminService.adminPartyIdCount(userId) == 0) {
                sysUserService.delRole(userId, RoleConstants.ROLE_PARTYADMIN);
            }
        }

        if (orgAdmin.getBranchId() != null) {
            // BranchMemberAdminService.toggleAdmin
            // 删除账号的"党支部管理员"角色
            // 如果他只是该党支部的管理员，则删除账号所属的"党支部管理员"角色； 否则不处理
            if (branchAdminService.adminBranchIdCount(userId) == 0) {
                sysUserService.delRole(userId, RoleConstants.ROLE_BRANCHADMIN);
            }
        }
    }

    // 删除管理员
    public void delAllOrgAdmin(Integer partyId, Integer branchId) {

        if (partyId == null && branchId == null) return; // 不能删除全部的管理员

        OrgAdminExample example = new OrgAdminExample();
        OrgAdminExample.Criteria criteria = example.createCriteria();
        if (partyId != null) criteria.andPartyIdEqualTo(partyId);
        if (branchId != null) criteria.andBranchIdEqualTo(branchId);
        List<OrgAdmin> orgAdmins = orgAdminMapper.selectByExample(example);

        for (OrgAdmin orgAdmin : orgAdmins) {

            del(orgAdmin.getId(), orgAdmin.getUserId());
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "AdminPartyIdList"),
            @CacheEvict(value = "AdminBranchIdList")
    })
    public int batchImport(List<OrgAdmin> records) {

        int count = 0;

        for (OrgAdmin record : records) {
            Byte type = record.getType();
            Integer userId = record.getUserId();
            SysUserView sysUser = sysUserService.findById(userId);

            if (type == OwConstants.OW_ORG_ADMIN_PARTY) {
                if (partyAdminService.adminParty(userId, record.getPartyId())) {
                    continue;
                }
                if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_PARTYADMIN)) {
                    sysUserService.addRole(userId, RoleConstants.ROLE_PARTYADMIN);
                }
            }else if (type == OwConstants.OW_ORG_ADMIN_BRANCH){
                if(branchAdminService.adminBranch(userId, record.getBranchId())){
                    continue;
                }
                if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_BRANCHADMIN)) {
                    sysUserService.addRole(userId, RoleConstants.ROLE_BRANCHADMIN);
                }
            }

            orgAdminMapper.insertSelective(record);
            count++;

        }

        return count;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "AdminPartyIdList"),
            @CacheEvict(value = "AdminBranchIdList")
    })
    public void batchDel(String[] ids, boolean isPartyAdmin, SysUserView loginUser) {

        for (String id : ids) {

            String[] str = id.split("_");
            int userId = Integer.parseInt(str[1]);
            int type = Integer.parseInt(str[2]);//type=1表示委员会成员 type=2表示普通管理员

            if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
                if (userId == loginUser.getId()) {
                    throw new OpException("不能删除自己");
                }
            }

            if (type == 2) {
                OrgAdmin orgAdmin = (OrgAdmin) getAdmin(id, isPartyAdmin);

                if (isPartyAdmin){
                    PartyHelper.checkAuth(orgAdmin.getPartyId());
                }else {
                    int branchId = orgAdmin.getBranchId();
                    Branch branch =  branchService.findAll().get(branchId);
                    PartyHelper.checkAuth(branch.getPartyId(), branchId);
                }
                del(orgAdmin.getId(), orgAdmin.getUserId());
            }else {
                if (isPartyAdmin){
                    PartyMemberView pmv = (PartyMemberView) getAdmin(id, isPartyAdmin);
                    partyAdminService.setPartyAdmin(pmv.getId(), false);
                }else {
                    BranchMemberView bmv = (BranchMemberView) getAdmin(id,isPartyAdmin);
                    branchAdminService.setBranchAdmin(bmv.getId(), false);
                }
            }
        }

    }

    public Object getAdmin(String owAdminId, boolean isPartyAdmin){

        String[] str = owAdminId.split("_");

        int owId = Integer.parseInt(str[0]);//分党委id或者党支部id（根据isPartyAdmin判断）
        int userId = Integer.parseInt(str[1]);
        int type = Integer.parseInt(str[2]);//1委员会成员 2管理员

        if (type == 1){
            if (isPartyAdmin) {
                PartyMemberViewExample example = new PartyMemberViewExample();
                example.createCriteria().andUserIdEqualTo(userId).andGroupPartyIdEqualTo(owId);
                List<PartyMemberView> pmv = partyMemberViewMapper.selectByExample(example);
                if (pmv != null && pmv.size() > 0) {
                    return pmv.get(0);
                }
            } else {
                BranchMemberViewExample example = new BranchMemberViewExample();
                example.createCriteria().andUserIdEqualTo(userId).andBranchIdEqualTo(owId);
                List<BranchMemberView> bmv = branchMemberViewMapper.selectByExample(example);
                if (bmv != null && bmv.size() > 0) {
                    return bmv.get(0);
                }
            }
        }else if (type == 2){
            OrgAdminExample example = new OrgAdminExample();
            OrgAdminExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
            if (isPartyAdmin){
                criteria.andPartyIdEqualTo(owId);
            }else {
                criteria.andBranchIdEqualTo(owId);
            }
            List<OrgAdmin> orgAdmins = orgAdminMapper.selectByExample(example);
            if (orgAdmins != null && orgAdmins.size() > 0) {

                return orgAdmins.get(0);
            }
        }

        return null;
    }
}
