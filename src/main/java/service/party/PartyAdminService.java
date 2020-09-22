package service.party;

import domain.party.PartyMember;
import domain.party.PartyMemberGroup;
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
    @CacheEvict(value="AdminPartyIdList", key = "#result")
    public int setPartyAdmin(int partyMemberId, boolean isAdmin) {

        PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(partyMemberId);
        int userId = partyMember.getUserId();
        boolean isHistory = BooleanUtils.isTrue(partyMember.getIsHistory());
        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(partyMember.getGroupId());
        boolean isDeleted = BooleanUtils.isTrue(partyMemberGroup.getIsDeleted());

        if(isHistory||isDeleted){ // 已离任或非现任班子，则删除管理员权限
            isAdmin = false;
        }

        PartyMember record = new PartyMember();
        record.setId(partyMemberId);
        record.setIsAdmin(isAdmin);
        partyMemberMapper.updateByPrimaryKeySelective(record);

        if (isAdmin) {
            sysUserService.addRole(userId, RoleConstants.ROLE_PARTYADMIN);
        }

        if (adminPartyIdCount(userId) == 0) {
            sysUserService.delRole(userId, RoleConstants.ROLE_PARTYADMIN);
        }

        return userId;
    }
}
