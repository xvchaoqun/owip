package service.pmd;

import domain.pmd.PmdBranchAdmin;
import domain.pmd.PmdBranchAdminExample;
import domain.pmd.PmdPayBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.BranchMemberService;
import service.party.PartyService;
import service.sys.SysUserService;
import sys.constants.PmdConstants;
import sys.constants.RoleConstants;

import java.util.*;

@Service
public class PmdBranchAdminService extends PmdBaseMapper {

    @Autowired
    private PmdPayBranchService pmdPayBranchService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchMemberService branchMemberService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;

    /**
     * 判断是否是支部管理员
     *
     * @param userId
     * @param partyId
     * @param branchId
     * @return
     */
    public boolean isBranchAdmin(int userId, Integer partyId, Integer branchId){

        if(partyService.isDirectBranch(partyId)){

            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
            Set<Integer> adminPartyIdSet = new HashSet<>();
            adminPartyIdSet.addAll(adminPartyIds);

            if (adminPartyIdSet.contains(partyId)) {
                return true;
            }
        }else{
            List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(userId);
            Set<Integer> adminBranchIdSet = new HashSet<>();
            adminBranchIdSet.addAll(adminBranchIds);
            if (adminBranchIdSet.contains(branchId)) {
                return true;
            }
        }

        return false;
    }

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

    // 同步党建 - 支部管理员
    @Transactional
    public void syncBranchAdmins(List<Integer> partyIds) {

        // 同步支部管理员
        for (Integer partyId : partyIds) {

            // 先删除
            PmdBranchAdminExample example = new PmdBranchAdminExample();
            example.createCriteria().andPartyIdEqualTo(partyId)
                    .andTypeNotEqualTo(PmdConstants.PMD_ADMIN_TYPE_ADD);
            List<PmdBranchAdmin> pmdBranchAdmins = pmdBranchAdminMapper.selectByExample(example);
            for (PmdBranchAdmin pmdBranchAdmin : pmdBranchAdmins) {
                del(pmdBranchAdmin.getId());
            }

            Map<Integer, PmdPayBranch> allPayBranchIdSet = pmdPayBranchService.getAllPayBranchIdSet(partyId);
            for (Integer branchId : allPayBranchIdSet.keySet()) {

                List<Integer> branchAdminIds = iPartyMapper.findBranchAdmin(branchId);

                for (Integer branchAdminId : branchAdminIds) {

                    PmdBranchAdmin record = new PmdBranchAdmin();
                    record.setPartyId(partyId);
                    record.setBranchId(branchId);
                    record.setUserId(branchAdminId);
                    record.setType(PmdConstants.PMD_ADMIN_TYPE_OW);

                    if(!isBranchAdmin(branchAdminId, partyId, branchId)) {
                        pmdBranchAdminMapper.insertSelective(record);
                        sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_PMD_BRANCH);
                    }
                }
            }
        }

        /*
        // 同步支部书记、组织委员为党费收缴党支部管理员
        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        MetaType commissaryType = CmTag.getMetaTypeByCode("mt_branch_commissary");

        for (Integer partyId : partyIds) {
            {
                // 先删除所有书记、组织委员
                PmdBranchAdminExample example = new PmdBranchAdminExample();
                example.createCriteria().andPartyIdEqualTo(partyId)
                        .andTypeIn(Arrays.asList(PmdConstants.PMD_ADMIN_TYPE_SECRETARY,
                                PmdConstants.PMD_ADMIN_TYPE_COMMISSARY));
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
                        record.setType(PmdConstants.PMD_ADMIN_TYPE_SECRETARY);

                        pmdBranchAdminMapper.insertSelective(record);
                        sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_PMD_BRANCH);
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
                        record.setType(PmdConstants.PMD_ADMIN_TYPE_COMMISSARY);

                        pmdBranchAdminMapper.insertSelective(record);
                        sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_PMD_BRANCH);
                    }
                }
            }
        }*/
    }

    @Transactional
    public void add(int partyId, int branchId, int userId) {

        PmdBranchAdmin record = new PmdBranchAdmin();
        record.setPartyId(partyId);
        record.setBranchId(branchId);
        record.setUserId(userId);

        Byte type = PmdConstants.PMD_ADMIN_TYPE_ADD;

        if(branchMemberService.isPresentAdmin(userId, partyId, branchId)){
            type = PmdConstants.PMD_ADMIN_TYPE_OW;
        }
        /*
        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        MetaType commissaryType = CmTag.getMetaTypeByCode("mt_branch_commissary");
        // 书记
        BranchMember secretary = iPartyMapper.findBranchMember(secretaryType.getId(), branchId, userId);
        if (secretary != null) {
            type = PmdConstants.PMD_ADMIN_TYPE_SECRETARY;
        }else {
            // 组织委员
            BranchMember commissary = iPartyMapper.findBranchMember(commissaryType.getId(), branchId, userId);
            if (commissary != null) {
                type = PmdConstants.PMD_ADMIN_TYPE_COMMISSARY;
            }
        }*/

        record.setType(type);

        pmdBranchAdminMapper.insertSelective(record);
        sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_PMD_BRANCH);
    }

    @Transactional
    public void del(Integer id) {

        PmdBranchAdmin pmdBranchAdmin = pmdBranchAdminMapper.selectByPrimaryKey(id);
        int userId = pmdBranchAdmin.getUserId();
        pmdBranchAdminMapper.deleteByPrimaryKey(id);

        PmdBranchAdminExample example = new PmdBranchAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        if (pmdBranchAdminMapper.countByExample(example) == 0) {
            sysUserService.delRole(userId, RoleConstants.ROLE_PMD_BRANCH);
        }
    }
}
