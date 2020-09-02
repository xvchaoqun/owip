package service.party;

import domain.party.PartyMember;
import domain.party.PartyMemberGroup;
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
public class PartyAdminService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    // 管理分党委ID列表
    @Cacheable(value="AdminPartyIdList", key="#userId")
    public List<Integer> adminPartyIdList(int userId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setUserId(userId);
        List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(owAdmin, new RowBounds());

        return owAdmins.stream().map(OwAdmin::getPartyId).collect(Collectors.toList());
    }

    // 管理分党委数量
    public int adminPartyIdCount(int userId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setUserId(userId);

        return iPartyMapper.countPartyAdminList(owAdmin);
    }
    // 是否管理分党委
    public boolean adminParty(int userId, int partyId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setUserId(userId);
        owAdmin.setPartyId(partyId);

        return iPartyMapper.countPartyAdminList(owAdmin)>0;
    }

    // 读取某个用户的分党委管理员列表
    public List<OwAdmin> getOwAdmins(int userId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setUserId(userId);

        return iPartyMapper.selectPartyAdminList(owAdmin, new RowBounds());
    }

    // 分党委管理员ID列表
    public List<Integer> adminPartyUserIdList(int partyId){

        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setPartyId(partyId);
        List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(owAdmin, new RowBounds());

        return owAdmins.stream().map(OwAdmin::getUserId).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value="AdminPartyIdList", key = "#partyMember.userId")
    public void toggleAdmin(PartyMember partyMember){

        boolean isAdmin = BooleanUtils.isTrue(partyMember.getIsAdmin());

        PartyMember record = new PartyMember();
        record.setId(partyMember.getId());
        record.setIsAdmin(!isAdmin);
        partyMemberMapper.updateByPrimaryKeySelective(record); // 必须先更新，保证下面的判断正确

        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(partyMember.getGroupId());
        if(partyMemberGroup.getIsPresent()) { // 只有当前班子是现任班子才操作

            Integer userId = partyMember.getUserId();

            if(isAdmin){
                // 删除账号的"分党委管理员"角色
                // 如果他只是该分党委的管理员，则删除账号所属的"分党委管理员"角色； 否则不处理
                if(adminPartyIdCount(userId)==0) {
                    sysUserService.delRole(userId, RoleConstants.ROLE_PARTYADMIN);
                }
            }else{
                // 添加账号的"分党委管理员"角色
                // 如果账号是现任班子的管理员， 且没有"分党委管理员"角色，则添加
                SysUserView sysUser = sysUserService.findById(userId);
                if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_PARTYADMIN)) {
                    sysUserService.addRole(userId, RoleConstants.ROLE_PARTYADMIN);
                }
            }
        }
    }
}
