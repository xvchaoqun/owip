package service.pmd;

import domain.base.MetaType;
import domain.party.PartyMemberView;
import domain.pmd.PmdPartyAdmin;
import domain.pmd.PmdPartyAdminExample;
import domain.pmd.PmdPayParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.party.PartyMemberService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PmdPartyAdminService extends BaseMapper {

    @Autowired
    private PmdPayPartyService pmdPayPartyService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PartyMemberService partyMemberService;

    public boolean idDuplicate(Integer id, int partyId, int userId) {

        PmdPartyAdminExample example = new PmdPartyAdminExample();
        PmdPartyAdminExample.Criteria criteria = example.createCriteria()
                .andPartyIdEqualTo(partyId).andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pmdPartyAdminMapper.countByExample(example) > 0;
    }

    /**
     * 判断是否是分党委管理员
     *
     * @param userId
     * @param partyId
     * @return
     */
    public boolean isPartyAdmin(int userId, Integer partyId) {

        List<Integer> adminPartyIds = getAdminPartyIds(userId);
        Set<Integer> adminPartyIdSet = new HashSet<>();
        adminPartyIdSet.addAll(adminPartyIds);

        return adminPartyIdSet.contains(partyId);
    }

    // 得到一个分党委的所有管理员
    public List<PmdPartyAdmin> getAllPmdPartyAdmins(int partyId) {

        PmdPartyAdminExample example = new PmdPartyAdminExample();
        example.createCriteria().andPartyIdEqualTo(partyId);
        example.setOrderByClause("type asc");

        return pmdPartyAdminMapper.selectByExample(example);
    }

    // 得到一个管理员管理的所有分党委ID
    public List<Integer> getAdminPartyIds(int userId) {

        PmdPartyAdminExample example = new PmdPartyAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<PmdPartyAdmin> pmdPartyAdmins = pmdPartyAdminMapper.selectByExample(example);

        List<Integer> adminPartyIds = new ArrayList<>();
        for (PmdPartyAdmin pmdPartyAdmin : pmdPartyAdmins) {
            adminPartyIds.add(pmdPartyAdmin.getPartyId());
        }

        return adminPartyIds;
    }

    // 同步书记、组织委员为党费收缴分党委管理员
    @Transactional
    public void syncPartyAdmins() {

        {
            // 先删除所有书记、组织委员
            PmdPartyAdminExample example = new PmdPartyAdminExample();
            example.createCriteria().andTypeIn(Arrays.asList(SystemConstants.PMD_ADMIN_TYPE_SECRETARY,
                    SystemConstants.PMD_ADMIN_TYPE_COMMISSARY));
            pmdPartyAdminMapper.deleteByExample(example);
        }

        Map<Integer, PmdPayParty> allPayPartyIdSet = pmdPayPartyService.getAllPayPartyIdSet();
        for (Integer partyId : allPayPartyIdSet.keySet()) {

            {
                // 书记
                PartyMemberView secretary = partyMemberService.getPartySecretary(partyId);
                if (secretary != null) {
                    PmdPartyAdmin record = new PmdPartyAdmin();
                    record.setPartyId(partyId);
                    record.setUserId(secretary.getUserId());
                    record.setType(SystemConstants.PMD_ADMIN_TYPE_SECRETARY);

                    pmdPartyAdminMapper.insertSelective(record);
                    sysUserService.addRole(record.getUserId(), SystemConstants.ROLE_PMD_PARTY);
                }
            }
            {
                // 组织委员
                MetaType zzwyType = CmTag.getMetaTypeByCode("mt_party_member_type_zzwy");
                List<PartyMemberView> zzwyList = partyMemberService.getPartyMemberViews(partyId, zzwyType.getId());
                for (PartyMemberView zzwy : zzwyList) {

                    PmdPartyAdmin record = new PmdPartyAdmin();
                    record.setPartyId(partyId);
                    record.setUserId(zzwy.getUserId());
                    record.setType(SystemConstants.PMD_ADMIN_TYPE_COMMISSARY);

                    pmdPartyAdminMapper.insertSelective(record);
                    sysUserService.addRole(record.getUserId(), SystemConstants.ROLE_PMD_PARTY);
                }
            }
        }
    }

    @Transactional
    public void add(int partyId, int userId) {

        PmdPartyAdmin record = new PmdPartyAdmin();
        record.setPartyId(partyId);
        record.setUserId(userId);

        Byte type = SystemConstants.PMD_ADMIN_TYPE_NORMAL;
        // 书记
        MetaType partySecretaryType = CmTag.getMetaTypeByCode("mt_party_secretary");
        PartyMemberView pmv = partyMemberService.getPartyMemberView(partyId, userId);
        if (pmv != null && pmv.getPostId() == partySecretaryType.getId()) {
            type = SystemConstants.PMD_ADMIN_TYPE_SECRETARY;
        } else {
            // 组织委员
            MetaType zzwyType = CmTag.getMetaTypeByCode("mt_party_member_type_zzwy");
            PartyMemberView pmv2 = partyMemberService.getPartyMemberView(partyId, userId, zzwyType.getId());
            if (pmv2 != null && pmv2.getPostId() == partySecretaryType.getId()) {
                type = SystemConstants.PMD_ADMIN_TYPE_COMMISSARY;
            }
        }

        record.setType(type);

        pmdPartyAdminMapper.insertSelective(record);
        sysUserService.addRole(record.getUserId(), SystemConstants.ROLE_PMD_PARTY);
    }

    @Transactional
    public void del(Integer id) {

        PmdPartyAdmin pmdPartyAdmin = pmdPartyAdminMapper.selectByPrimaryKey(id);
        int userId = pmdPartyAdmin.getUserId();
        pmdPartyAdminMapper.deleteByPrimaryKey(id);

        PmdPartyAdminExample example = new PmdPartyAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        if (pmdPartyAdminMapper.countByExample(example) == 0) {
            sysUserService.delRole(userId, SystemConstants.ROLE_PMD_PARTY);
        }
    }
}
