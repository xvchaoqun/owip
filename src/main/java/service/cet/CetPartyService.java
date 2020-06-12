package service.cet;

import domain.cet.CetParty;
import domain.cet.CetPartyAdmin;
import domain.cet.CetPartyExample;
import domain.party.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.PartyMemberService;
import service.sys.SysUserService;
import sys.constants.RoleConstants;

import java.util.*;

@Service
public class CetPartyService extends CetBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CetPartyAdminService cetPartyAdminService;
    @Autowired
    private PartyMemberService partyMemberService;

    public boolean idDuplicate(Integer id, int partyId) {

        CetPartyExample example = new CetPartyExample();
        CetPartyExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return cetPartyMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetParty record) {

        record.setIsDeleted(false);
        record.setSortOrder(getNextSortOrder("cet_party", "is_deleted=0"));
        cetPartyMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, boolean isDeleted) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            // 先删除或恢复二级党委
            CetParty record = new CetParty();
            record.setId(id);
            record.setIsDeleted(isDeleted);
            record.setSortOrder(getNextSortOrder("cet_party", "is_deleted="+ isDeleted));
            cetPartyMapper.updateByPrimaryKeySelective(record);

            // 后更新二级党委管理员权限
            List<CetPartyAdmin> cetPartyAdmins = cetPartyAdminService.findByPartyId(id);
            for (CetPartyAdmin cetPartyAdmin : cetPartyAdmins) {

                cetPartyAdminService.updateRoleCetAdminParty(cetPartyAdmin.getUserId());
            }
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetParty record) {

        return cetPartyMapper.updateByPrimaryKeySelective(record);
    }

    //同步管理员
    @Transactional
    public void batchSync() {

        List<Party> parties = partyMapper.selectByExample(new PartyExample());
        List<Integer> partyIds = new ArrayList<>();
        for (Party party : parties) {
            if (!party.getIsDeleted())
                partyIds.add(party.getId());
        }
        Map<Integer, CetParty> cetPartyMap = findAll();
        for (Map.Entry<Integer, CetParty> entry : cetPartyMap.entrySet()) {
            CetParty cetParty = entry.getValue();
            Integer partyId = cetParty.getPartyId();
            Integer cetPartyId = cetParty.getId();
            if (null != partyId && partyIds.contains(partyId)) {

                List<Integer> userIds = new ArrayList<>();
                List<PartyMemberView> partyMemberViews = partyMemberService.getByPartyId(partyId);
                for (PartyMemberView partyMemberView : partyMemberViews) {
                    userIds.add(partyMemberView.getUserId());
                }
                Byte type = 2;
                OrgAdminExample orgAdminExample = new OrgAdminExample();
                orgAdminExample.createCriteria().andPartyIdEqualTo(partyId).andTypeEqualTo(type);
                List<OrgAdmin> orgAdmins = orgAdminMapper.selectByExample(orgAdminExample);
                for (OrgAdmin orgAdmin : orgAdmins) {
                    userIds.add(orgAdmin.getUserId());
                }
                //先删除管理员,仅删除委员中包含的,不删除自己设置的
                List<CetPartyAdmin> cetPartyAdmins = cetPartyAdminService.findByPartyId(cetPartyId);
                for (CetPartyAdmin cetPartyAdmin : cetPartyAdmins) {
                    if (!userIds.contains(cetPartyAdmin.getUserId()))
                        continue;
                    sysUserService.delRole(cetPartyAdmin.getUserId(), RoleConstants.ROLE_CET_ADMIN_PARTY);
                    cetPartyAdminMapper.deleteByPrimaryKey(cetPartyAdmin.getId());
                }
                //同步管理员
                for (Integer userId : userIds) {
                    cetPartyAdminService.insert(cetPartyId, userId);
                }
            }
        }
    }

    private Map<Integer, CetParty> findAll() {

        CetPartyExample example = new CetPartyExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        List<CetParty> cetParties = cetPartyMapper.selectByExample(example);
        Map<Integer, CetParty> map = new HashMap<>();
        for (CetParty cetParty : cetParties) {
            map.put(cetParty.getId(), cetParty);
        }
        return map;
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        CetParty cetParty = cetPartyMapper.selectByPrimaryKey(id);
        changeOrder("cet_party", "is_deleted=" + cetParty.getIsDeleted(), ORDER_BY_DESC, id, addNum);
    }
}
