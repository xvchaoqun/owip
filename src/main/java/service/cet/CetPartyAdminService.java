package service.cet;

import domain.base.MetaType;
import domain.cet.CetParty;
import domain.cet.CetPartyAdmin;
import domain.cet.CetPartyAdminExample;
import domain.party.PartyMemberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.PartyMemberService;
import service.sys.SysUserService;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.List;

@Service
public class CetPartyAdminService extends CetBaseMapper{

    @Autowired
    private PartyMemberService partyMemberService;
    @Autowired
    private SysUserService sysUserService;

    //根据partyId得到管理员
    public List<CetPartyAdmin> findByPartyId(Integer cetPartyId) {

        CetPartyAdminExample example = new CetPartyAdminExample();
        example.createCriteria().andCetPartyIdEqualTo(cetPartyId);
        example.setOrderByClause(" type asc");
        return cetPartyAdminMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, Integer partyId, Integer userId) {

        CetPartyAdminExample example = new CetPartyAdminExample();
        CetPartyAdminExample.Criteria criteria = example.createCriteria().andCetPartyIdEqualTo(partyId).andUserIdEqualTo(userId);
        if (null != id) criteria.andIdEqualTo(id);

        return cetPartyAdminMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insert(int cetPartyId, int userId) {

        CetPartyAdmin record = new CetPartyAdmin();
        record.setCetPartyId(cetPartyId);
        record.setUserId(userId);
        byte type = CetConstants.CET_PARTY_ADMIN_NORMAL;
        CetParty cetparty = cetPartyMapper.selectByPrimaryKey(cetPartyId);
        if (null != cetparty.getPartyId()) {
            MetaType sMetaType = CmTag.getMetaTypeByCode("mt_party_secretary");
            MetaType vsMetaType = CmTag.getMetaTypeByCode("mt_party_vice_secretary");
            MetaType mMetaType = CmTag.getMetaTypeByCode("mt_party_member");
            PartyMemberView pmv = partyMemberService.getPartyMemberView(cetparty.getPartyId(), userId);
            if (null != pmv){
                if(pmv.getPostId() == sMetaType.getId()){
                    type = CetConstants.CET_PARTY_ADMIN_SECRETARY;
                }else if(pmv.getPostId() == vsMetaType.getId()){
                    type = CetConstants.CET_PARTY_ADMIN_VICE_SECRETARY;
                }else if(pmv.getPostId() == mMetaType.getId().intValue()){
                    type = CetConstants.CET_PARTY_ADMIN_COMMITTEE_MEMBER;
                }
            }
        }
        record.setType(type);
        cetPartyAdminMapper.insert(record);

        // 变更权限
        if (!cetparty.getIsDeleted())
            updateRoleCetAdminParty(userId);
    }

    @Transactional
    public void del(Integer id) {

        CetPartyAdmin cetPartyAdmin = cetPartyAdminMapper.selectByPrimaryKey(id);
        CetParty cetParty = cetPartyMapper.selectByPrimaryKey(cetPartyAdmin.getCetPartyId());
        int userId = cetPartyAdmin.getUserId();
        cetPartyAdminMapper.deleteByPrimaryKey(id);

        // 变更权限
        if (!cetParty.getIsDeleted())
            updateRoleCetAdminParty(userId);
    }

    // 更新或删除二级党委管理员权限
    public void updateRoleCetAdminParty(int userId){

        List<CetParty> adminParties = iCetMapper.getAdminParties(userId);
        if(adminParties.size()>0){
            sysUserService.addRole(userId, RoleConstants.ROLE_CET_ADMIN_PARTY);
        }else{
            sysUserService.delRole(userId, RoleConstants.ROLE_CET_ADMIN_PARTY);
        }
    }
}
