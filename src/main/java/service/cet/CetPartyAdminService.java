package service.cet;

import domain.base.MetaType;
import domain.cet.CetParty;
import domain.cet.CetPartyAdmin;
import domain.cet.CetPartyAdminExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import service.sys.SysUserService;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.List;

@Service
public class CetPartyAdminService extends CetBaseMapper {

    @Autowired
    private SysUserService sysUserService;

    //根据partyId得到管理员
    public List<CetPartyAdmin> findByPartyId(Integer cetPartyId) {

        CetPartyAdminExample example = new CetPartyAdminExample();
        example.createCriteria().andCetPartyIdEqualTo(cetPartyId);
        example.setOrderByClause(" type asc");
        return cetPartyAdminMapper.selectByExample(example);
    }

    public CetPartyAdmin get(Integer cetPartyId, Integer userId) {

        CetPartyAdminExample example = new CetPartyAdminExample();
        CetPartyAdminExample.Criteria criteria =
                example.createCriteria().andCetPartyIdEqualTo(cetPartyId).andUserIdEqualTo(userId);

        List<CetPartyAdmin> cetPartyAdmins = cetPartyAdminMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return cetPartyAdmins.size() > 0 ? cetPartyAdmins.get(0) : null;
    }

    @SuppressWarnings("checkstyle:WhitespaceAround")
    @Transactional
    public void insertOrUpdate(int cetPartyId, int userId) {

        CetPartyAdmin record = new CetPartyAdmin();
        record.setCetPartyId(cetPartyId);
        record.setUserId(userId);
        byte type = CetConstants.CET_PARTY_ADMIN_NORMAL;
        CetParty cetparty = cetPartyMapper.selectByPrimaryKey(cetPartyId);
        Integer partyId = cetparty.getPartyId();
        if (null != partyId) { // 如果关联了基层党组织，则同步此人的职务

            OwAdmin search = new OwAdmin();
            search.setPartyId(partyId);
            search.setUserId(userId);
            List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(search, new RowBounds(0, 1));

            if (owAdmins.size() > 0) {
                OwAdmin owAdmin = owAdmins.get(0);
                Integer postId = owAdmin.getPostId();

                if (postId != null) {
                    MetaType sMetaType = CmTag.getMetaTypeByCode("mt_party_secretary");
                    MetaType vsMetaType = CmTag.getMetaTypeByCode("mt_party_vice_secretary");
                    MetaType mMetaType = CmTag.getMetaTypeByCode("mt_party_member");

                    if (postId.intValue() == sMetaType.getId()) {
                        type = CetConstants.CET_PARTY_ADMIN_SECRETARY;
                    } else if (postId.intValue() == vsMetaType.getId()) {
                        type = CetConstants.CET_PARTY_ADMIN_VICE_SECRETARY;
                    } else if (postId.intValue() == mMetaType.getId()) {
                        type = CetConstants.CET_PARTY_ADMIN_COMMITTEE_MEMBER;
                    }
                }
            }
        }
        record.setType(type);

        CetPartyAdmin cetPartyAdmin = get(cetPartyId, userId);
        if (cetPartyAdmin == null) {
            cetPartyAdminMapper.insert(record);
            updateAdminCount(cetPartyId);
        } else {
            record.setId(cetPartyAdmin.getId());
            cetPartyAdminMapper.updateByPrimaryKeySelective(record);
        }

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
        updateAdminCount(cetParty.getId());

        // 变更权限
        if (!cetParty.getIsDeleted())
            updateRoleCetAdminParty(userId);
    }

    // 更新或删除二级党委管理员权限
    public void updateRoleCetAdminParty(int userId) {

        List<CetParty> adminParties = iCetMapper.getAdminParties(userId);
        if (adminParties.size() > 0) {
            sysUserService.addRole(userId, RoleConstants.ROLE_CET_ADMIN_PARTY);
        } else {
            sysUserService.delRole(userId, RoleConstants.ROLE_CET_ADMIN_PARTY);
        }
    }

    //更新管理员数量
    @Transactional
    public void updateAdminCount(Integer cetPartyId){
        CetPartyAdminExample example = new CetPartyAdminExample();
        example.createCriteria().andCetPartyIdEqualTo(cetPartyId);
        long adminCount = cetPartyAdminMapper.countByExample(example);
        CetParty record = new CetParty();
        record.setId(cetPartyId);
        record.setAdminCount((int) adminCount);
        cetPartyMapper.updateByPrimaryKeySelective(record);
    }
}
