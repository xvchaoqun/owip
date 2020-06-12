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
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.ArrayList;
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
        return cetPartyAdminMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, Integer partyId, Integer userId) {

        CetPartyAdminExample example = new CetPartyAdminExample();
        CetPartyAdminExample.Criteria criteria = example.createCriteria().andCetPartyIdEqualTo(partyId).andUserIdEqualTo(userId);
        if (null != id) criteria.andIdEqualTo(id);

        return cetPartyAdminMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insert(Integer cetPartyId, Integer userId) {

        CetPartyAdmin record = new CetPartyAdmin();
        record.setCetPartyId(cetPartyId);
        record.setUserId(userId);

        Byte type = CetConstants.CET_PARTY_ADMIN_NORMAL;

        CetParty cetparty = cetPartyMapper.selectByPrimaryKey(cetPartyId);
        if (null != cetparty.getPartyId()) {
            MetaType sMetaType = CmTag.getMetaTypeByCode("mt_party_secretary");
            MetaType vsMetaType = CmTag.getMetaTypeByCode("mt_party_vice_secretary");
            MetaType mMetaType = CmTag.getMetaTypeByCode("mt_party_member");
            PartyMemberView pmv = partyMemberService.getPartyMemberView(cetparty.getPartyId(), userId);
            if (null != pmv)
                type = pmv.getPostId() == sMetaType.getId() ? CetConstants.CET_PARTY_ADMIN_SECRETARY : pmv.getPostId() == vsMetaType.getId() ? CetConstants.CET_PARTY_ADMIN_VICE_SECRETARY : pmv.getPostId() == mMetaType.getId() ? CetConstants.CET_PARTY_ADMIN_COMMITTEE_MEMBER : type;
        }
        record.setType(type);

        cetPartyAdminMapper.insert(record);
        sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_CET_ADMIN_PARTY);
    }

    @Transactional
    public void del(Integer id) {

        CetPartyAdmin cetPartyAdmin = cetPartyAdminMapper.selectByPrimaryKey(id);
        Integer userId = cetPartyAdmin.getUserId();

        List<CetPartyAdmin> cetPartyAdmins = getByUserId(userId);
        if (cetPartyAdmins.size() == 1)
            sysUserService.delRole(cetPartyAdmin.getUserId(), RoleConstants.ROLE_CET_ADMIN_PARTY);
        cetPartyAdminMapper.deleteByPrimaryKey(id);
    }

    public List<CetPartyAdmin> getByUserId(Integer userId){

        CetPartyAdminExample example = new CetPartyAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);

        return cetPartyAdminMapper.selectByExample(example);
    }

    //得到二级党委管理员管理的分党委id
    public List<Integer> getPartyIds(){
        List<Integer> partyIds = new ArrayList<>();
        List<CetPartyAdmin> cetPartyAdmins = getByUserId(ShiroHelper.getCurrentUserId());
        for (CetPartyAdmin cetPartyAdmin : cetPartyAdmins) {
            partyIds.add(cetPartyAdmin.getCetParty().getPartyId());
        }
        return partyIds;
    }
}
