package service.dp;

import domain.dp.DpOrgAdmin;
import domain.dp.DpOrgAdminExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sys.SysUserService;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.Date;
import java.util.List;

@Service
public class DpOrgAdminService extends DpBaseMapper {

    @Autowired
    private SysUserService sysUserService;

    @Transactional
    @CacheEvict(value="AdminDpPartyIdList", key = "#userId")
    public int addDpPartyAdmin(int userId, int partyId){

        Assert.isTrue(!idDuplicate(null, userId, partyId), "duplicate");


        SysUserView sysUser = sysUserService.findById(userId);

        // 见PartyMemberAdminService.toggleAdmin
        // 添加账号的"党派管理员"角色
        // 如果账号是委员会的管理员， 且没有"分党委管理员"角色，则添加
        if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_DP_PARTY)) {
            sysUserService.addRole(userId, RoleConstants.ROLE_DP_PARTY);
        }

        DpOrgAdmin record = new DpOrgAdmin();
        record.setUserId(userId);
        record.setPartyId(partyId);
        record.setCreateTime(new Date());
        record.setType(OwConstants.OW_ORG_ADMIN_DPPARTY);

        return dpOrgAdminMapper.insertSelective(record);
    }

    public boolean idDuplicate(Integer id, int userId, Integer partyId){

        //Assert.isTrue(partyId!=null , "null");

        DpOrgAdminExample example = new DpOrgAdminExample();
        DpOrgAdminExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);

        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpOrgAdminMapper.countByExample(example) > 0;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "AdminDpPartyIdList", key = "#userId"),
            @CacheEvict(value = "AdminDpPartyIdList", key = "#userId")
    })
    public void del(Integer id, int userId){

        DpOrgAdmin dpOrgAdmin = dpOrgAdminMapper.selectByPrimaryKey(id);
        Assert.isTrue(dpOrgAdmin.getUserId().intValue() == userId, "wrong userId");

        // 先删除
        dpOrgAdminMapper.deleteByPrimaryKey(id);

        if(dpOrgAdmin.getPartyId()!=null) {
            // 见PartyMemberAdminService.toggleAdmin
            // 删除账号的"党派管理员"角色
            // 如果他只是该党派的管理员，则删除账号所属的"党派管理员"角色； 否则不处理
            List<Integer> partyIdList = iDpPartyMapper.adminDpPartyIdList(userId);
            if (partyIdList.size() == 0) {
                sysUserService.delRole(userId, RoleConstants.ROLE_DP_PARTY);
            }
        }
    }

    public void delAllOrgAdmin(Integer partyId){
        if (partyId == null) return;

        DpOrgAdminExample example = new DpOrgAdminExample();
        DpOrgAdminExample.Criteria criteria = example.createCriteria();
        if (partyId != null)criteria.andPartyIdEqualTo(partyId);
        List<DpOrgAdmin> dpOrgAdmins = dpOrgAdminMapper.selectByExample(example);

        for (DpOrgAdmin dpOrgAdmin : dpOrgAdmins){
            del(dpOrgAdmin.getId(),dpOrgAdmin.getUserId());
        }
    }

    public DpOrgAdmin findByUserId(Integer userId){

        DpOrgAdminExample example = new DpOrgAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<DpOrgAdmin> dpOrgAdmins = dpOrgAdminMapper.selectByExample(example);

        return dpOrgAdmins.size() > 0 ?dpOrgAdmins.get(0) : null;
    }
}
