package service.party;

import domain.party.PartyMember;
import domain.party.PartyMemberGroup;
import domain.sys.SysUserView;
import org.eclipse.jdt.internal.core.Assert;
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
public class PartyMemberAdminService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    @Cacheable(value="AdminPartyIdList", key="#userId")
    public List<Integer> adminPartyIdList(int userId){

        return iPartyMapper.adminPartyIdList(userId);
    }

    @Transactional
    @CacheEvict(value="AdminPartyIdList", key = "#partyMember.userId")
    public void toggleAdmin(PartyMember partyMember){

        Assert.isTrue(partyMember != null && partyMember.getId() != null
                && partyMember.getIsAdmin() != null && partyMember.getUserId() != null && partyMember.getGroupId() != null);

        PartyMember record = new PartyMember();
        record.setId(partyMember.getId());
        record.setIsAdmin(!partyMember.getIsAdmin());
        partyMemberMapper.updateByPrimaryKeySelective(record); // 必须先更新，保证下面的判断正确

        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(partyMember.getGroupId());
        if(partyMemberGroup.getIsPresent()) { // 只有当前班子是现任班子才操作

            Integer userId = partyMember.getUserId();
            SysUserView sysUser = sysUserService.findById(userId);
            if(partyMember.getIsAdmin()){
                // 删除账号的"分党委管理员"角色
                // 如果他只是该分党委的管理员，则删除账号所属的"分党委管理员"角色； 否则不处理
                List<Integer> partyIdList = iPartyMapper.adminPartyIdList(userId);
                if(partyIdList.size()==0) {
                    sysUserService.delRole(userId, SystemConstants.ROLE_PARTYADMIN, sysUser.getUsername(), sysUser.getCode());
                }
            }else{
                // 添加账号的"分党委管理员"角色
                // 如果账号是现任班子的管理员， 且没有"分党委管理员"角色，则添加
                Set<String> roleStrSet = sysUserService.findRoles(sysUser.getUsername());
                if (!roleStrSet.contains(SystemConstants.ROLE_PARTYADMIN)) {
                    sysUserService.addRole(userId, SystemConstants.ROLE_PARTYADMIN, sysUser.getUsername(), sysUser.getCode());
                }
            }
        }
    }
}
