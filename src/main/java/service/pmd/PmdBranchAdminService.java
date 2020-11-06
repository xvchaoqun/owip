package service.pmd;

import domain.pmd.PmdBranchAdmin;
import domain.pmd.PmdBranchAdminExample;
import domain.pmd.PmdPayBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.BranchAdminService;
import service.party.BranchMemberService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class PmdBranchAdminService extends PmdBaseMapper {

    @Autowired
    private PmdPayBranchService pmdPayBranchService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BranchMemberService branchMemberService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private BranchAdminService branchAdminService;

    /**
     * 判断是管理支部
     *
     * @param userId
     * @param partyId
     * @param branchId
     * @return
     */
    public boolean adminBranch(int userId, Integer partyId, Integer branchId) {

        if (ShiroHelper.isPermitted(SystemConstants.PERMISSION_PMDVIEWALL))
            return true;

        List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
        Set<Integer> adminPartyIdSet = new HashSet<>();
        adminPartyIdSet.addAll(adminPartyIds);
        if (adminPartyIdSet.contains(partyId)) {
            return true;
        }

        if (branchId != null) {
            return isBranchAdmin(userId, branchId);
        }

        return false;
    }

    // 判断是否是支部管理员
    public boolean isBranchAdmin(int userId, int branchId) {

        List<Integer> adminBranchIds = getAdminBranchIds(userId);
        Set<Integer> adminBranchIdSet = new HashSet<>();
        adminBranchIdSet.addAll(adminBranchIds);
        if (adminBranchIdSet.contains(branchId)) {
            return true;
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

                List<Integer> branchAdminIds = branchAdminService.adminBranchUserIdList(branchId);

                for (Integer branchAdminId : branchAdminIds) {

                    PmdBranchAdmin record = new PmdBranchAdmin();
                    record.setPartyId(partyId);
                    record.setBranchId(branchId);
                    record.setUserId(branchAdminId);
                    record.setType(PmdConstants.PMD_ADMIN_TYPE_OW);

                    if (!isBranchAdmin(branchAdminId, branchId)) {
                        pmdBranchAdminMapper.insertSelective(record);
                        sysUserService.addRole(record.getUserId(), RoleConstants.ROLE_PMD_BRANCH);
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

        Byte type = PmdConstants.PMD_ADMIN_TYPE_ADD;

        if (branchMemberService.isPresentAdmin(userId, partyId, branchId)) {
            type = PmdConstants.PMD_ADMIN_TYPE_OW;
        }
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
