package service.dp;

import domain.dp.DpPartyMember;
import domain.dp.DpPartyMemberGroup;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.jfree.util.BooleanUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.dp.DpPartyMemberMapper;
import service.sys.SysUserService;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import javax.management.relation.Role;
import java.util.List;

/**
 * Created by fafa on 2016/1/19.
 */
@Service
public class DpPartyMemberAdminService extends DpBaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public List<Integer> adminDpPartyIdList(int userId){

        return iDpPartyMapper.adminDpPartyIdList(userId);
    }

    @Transactional
    public void setPartyAdmin(Integer partyMemberId, boolean autoAdmin){
        DpPartyMember old = dpPartyMemberMapper.selectByPrimaryKey(partyMemberId);
        int userId = old.getUserId();
        boolean presentMember = BooleanUtils.isTrue(old.getIsAdmin());
        DpPartyMemberGroup group = dpPartyMemberGroupMapper.selectByPrimaryKey(old.getGroupId());
        boolean isDeleted = group.getIsDeleted();

        if (isDeleted || presentMember){    //已离任或者非现任委员会委员会，取消管理员权限
            autoAdmin = false;
        }

        DpPartyMember member = new DpPartyMember();
        member.setId(old.getId());
        member.setIsAdmin(autoAdmin);
        dpPartyMemberMapper.updateByPrimaryKeySelective(member);


        if (autoAdmin){
            sysUserService.addRole(userId, RoleConstants.ROLE_DP_PARTY);
        }

        if (iDpPartyMapper.adminDpPartyIdList(userId).size() == 0){
            sysUserService.delRole(userId, RoleConstants.ROLE_DP_PARTY);
        }
    }

    @Transactional
    public void toggleAdmin(DpPartyMember dpPartyMember){

        DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(dpPartyMember.getGroupId());
        //if(!dpPartyMemberGroup.getIsPresent())  return;// 只有当前委员会是现任委员会才操作

        DpPartyMember record = new DpPartyMember();
        record.setId(dpPartyMember.getId());
        record.setIsAdmin(!dpPartyMember.getIsAdmin());
        dpPartyMemberMapper.updateByPrimaryKeySelective(record); // 必须先更新，保证下面的判断正确

        Integer userId = dpPartyMember.getUserId();

        if(dpPartyMember.getIsAdmin()){
            // 删除账号的"民主党派管理员"角色
            // 如果他只是该民主党派的管理员，则删除账号所属的"民主党派管理员"角色； 否则不处理
            List<Integer> partyIdList = iDpPartyMapper.adminDpPartyIdList(userId);
            if(partyIdList.size()==0) {
                sysUserService.delRole(userId,RoleConstants.ROLE_DP_PARTY);
            }
        }else{
            // 添加账号的"民主党派管理员"角色
            // 如果账号是现任委员会的管理员， 且没有"民主党派管理员"角色，则添加
            SysUserView sysUser = sysUserService.findById(userId);
            if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_DP_PARTY)) {
                sysUserService.addRole(userId, RoleConstants.ROLE_DP_PARTY);
            }
        }

    }
}
