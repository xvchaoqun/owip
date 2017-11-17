package service.pmd;

import domain.base.MetaType;
import domain.party.BranchMember;
import domain.pmd.PmdBranchAdmin;
import domain.pmd.PmdBranchAdminExample;
import domain.pmd.PmdPayBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PmdBranchAdminService extends BaseMapper {

    @Autowired
    private PmdPayBranchService pmdPayBranchService;
    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, int branchId, int userId) {

        PmdBranchAdminExample example = new PmdBranchAdminExample();
        PmdBranchAdminExample.Criteria criteria = example.createCriteria()
                .andBranchIdEqualTo(branchId)
                .andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pmdBranchAdminMapper.countByExample(example) > 0;
    }

    // 得到一个党支部的所有管理员
    public List<PmdBranchAdmin> getAllPmdBranchAdmins(int branchId) {

        PmdBranchAdminExample example = new PmdBranchAdminExample();
        example.createCriteria().andBranchIdEqualTo(branchId);
        example.setOrderByClause("type asc");

        return pmdBranchAdminMapper.selectByExample(example);
    }

    // 得到一个管理员管理的所有党支部ID
    public List<Integer> getAdminBranchIds(int userId) {

        PmdBranchAdminExample example = new PmdBranchAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<PmdBranchAdmin> pmdBranchAdmins = pmdBranchAdminMapper.selectByExample(example);

        List<Integer> adminBranchIds = new ArrayList<>();
        for (PmdBranchAdmin pmdBranchAdmin : pmdBranchAdmins) {
            adminBranchIds.add(pmdBranchAdmin.getBranchId());
        }

        return adminBranchIds;
    }

    // 同步支部书记、组织委员为党费收缴党支部管理员
    @Transactional
    public void syncBranchAdmins(List<Integer> partyIds) {

        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        MetaType commissaryType = CmTag.getMetaTypeByCode("mt_branch_commissary");

        for (Integer partyId : partyIds) {
            {
                // 先删除所有书记、组织委员
                PmdBranchAdminExample example = new PmdBranchAdminExample();
                example.createCriteria().andPartyIdEqualTo(partyId)
                        .andTypeIn(Arrays.asList(SystemConstants.PMD_ADMIN_TYPE_SECRETARY,
                                SystemConstants.PMD_ADMIN_TYPE_COMMISSARY));
                pmdBranchAdminMapper.deleteByExample(example);
            }

            Map<Integer, PmdPayBranch> allPayBranchIdSet = pmdPayBranchService.getAllPayBranchIdSet(partyId);
            for (Integer branchId : allPayBranchIdSet.keySet()) {

                {
                    // 书记
                    List<BranchMember> secretarys = iPartyMapper.findBranchMembers(secretaryType.getId(), branchId);
                    for (BranchMember secretary : secretarys) {
                        PmdBranchAdmin record = new PmdBranchAdmin();
                        record.setPartyId(partyId);
                        record.setBranchId(branchId);
                        record.setUserId(secretary.getUserId());
                        record.setType(SystemConstants.PMD_ADMIN_TYPE_SECRETARY);

                        pmdBranchAdminMapper.insertSelective(record);
                        sysUserService.addRole(record.getUserId(), SystemConstants.ROLE_PMD_BRANCH);
                    }
                }
                {
                    // 组织委员
                    List<BranchMember> commissarys = iPartyMapper.findBranchMembers(commissaryType.getId(), branchId);
                    for (BranchMember commissary : commissarys) {
                        PmdBranchAdmin record = new PmdBranchAdmin();
                        record.setPartyId(partyId);
                        record.setBranchId(branchId);
                        record.setUserId(commissary.getUserId());
                        record.setType(SystemConstants.PMD_ADMIN_TYPE_COMMISSARY);

                        pmdBranchAdminMapper.insertSelective(record);
                        sysUserService.addRole(record.getUserId(), SystemConstants.ROLE_PMD_BRANCH);
                    }
                }
            }
        }
    }

    @Transactional
    public void add(int partyId, int branchId, int userId) {

        PmdBranchAdmin record = new PmdBranchAdmin();
        record.setPartyId(partyId);
        record.setBranchId(branchId);
        record.setUserId(userId);

        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        MetaType commissaryType = CmTag.getMetaTypeByCode("mt_branch_commissary");

        Byte type = SystemConstants.PMD_ADMIN_TYPE_NORMAL;

        // 书记
        BranchMember secretary = iPartyMapper.findBranchMember(secretaryType.getId(), branchId, userId);
        if (secretary != null) {
            type = SystemConstants.PMD_ADMIN_TYPE_SECRETARY;
        }else {
            // 组织委员
            BranchMember commissary = iPartyMapper.findBranchMember(commissaryType.getId(), branchId, userId);
            if (commissary != null) {
                type = SystemConstants.PMD_ADMIN_TYPE_COMMISSARY;
            }
        }

        record.setType(type);

        pmdBranchAdminMapper.insertSelective(record);
        sysUserService.addRole(record.getUserId(), SystemConstants.ROLE_PMD_BRANCH);
    }

    @Transactional
    public void del(Integer id) {

        PmdBranchAdmin pmdBranchAdmin = pmdBranchAdminMapper.selectByPrimaryKey(id);
        int userId = pmdBranchAdmin.getUserId();
        pmdBranchAdminMapper.deleteByPrimaryKey(id);

        PmdBranchAdminExample example = new PmdBranchAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        if (pmdBranchAdminMapper.countByExample(example) == 0) {
            sysUserService.delRole(userId, SystemConstants.ROLE_PMD_BRANCH);
        }
    }
}
