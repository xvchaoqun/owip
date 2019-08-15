package service.dp;

import domain.dp.DpOrgAdmin;
import domain.dp.DpOrgAdminExample;
import domain.dp.DpPartyMember;
import domain.dp.DpPartyMemberGroup;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sys.SysUserService;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.List;

/**
 * Created by fafa on 2016/1/19.
 */
@Service
public class DpPartyMemberAdminService extends DpBaseMapper {

    @Autowired
    private DpOrgAdminService dpOrgAdminService;
    @Autowired
    private SysUserService sysUserService;

    @Cacheable(value="AdminDpPartyIdList", key="#userId")
    public List<Integer> adminDpPartyIdList(int userId){

        return iDpPartyMapper.adminDpPartyIdList(userId);
    }

    @Transactional
    public void toggleAdmin(DpPartyMember dpPartyMember){

        Assert.isTrue(dpPartyMember != null
                && dpPartyMember.getIsAdmin() != null && dpPartyMember.getGroupId() != null);

        DpPartyMember record = new DpPartyMember();
        record.setId(dpPartyMember.getId());
        record.setIsAdmin(!dpPartyMember.getIsAdmin());
        dpPartyMemberMapper.updateByPrimaryKeySelective(record); // 必须先更新，保证下面的判断正确

        DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(dpPartyMember.getGroupId());
        if(dpPartyMemberGroup.getIsPresent()) { // 只有当前委员会是现任委员会才操作

            Integer userId = dpPartyMember.getUserId();
            DpOrgAdminExample dpOrgAdminExample = new DpOrgAdminExample();
            dpOrgAdminExample.createCriteria().andUserIdEqualTo(userId);
            List<DpOrgAdmin> dpOrgAdmins = dpOrgAdminMapper.selectByExample(dpOrgAdminExample);

            if(dpPartyMember.getIsAdmin()){
                // 删除账号的"民主党派管理员"角色
                // 如果他只是该民主党派的管理员，则删除账号所属的"民主党派管理员"角色； 否则不处理
                List<Integer> partyIdList = iDpPartyMapper.adminDpPartyIdList(userId);
                if(partyIdList.size()==1) {
                    sysUserService.delRole(userId, RoleConstants.ROLE_DPPARTYADMIN);
                    for (DpOrgAdmin dpOrgAdmin : dpOrgAdmins){
                        dpOrgAdminService.del(dpOrgAdmin.getId(),userId);
                    }
                }
            }else{
                // 添加账号的"民主党派管理员"角色
                // 如果账号是现任委员会的管理员， 且没有"民主党派管理员"角色，则添加
                SysUserView sysUser = sysUserService.findById(userId);
                if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_DPPARTYADMIN)) {
                    sysUserService.addRole(userId, RoleConstants.ROLE_DPPARTYADMIN);
                    dpOrgAdminService.addDpPartyAdmin(userId, dpPartyMemberGroup.getPartyId());
                }
            }

        }
    }
}
