package service.party;

import domain.party.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class BranchService extends BaseMapper {

    @Autowired
    private PartyMemberService partyMemberService;
    @Autowired
    private BranchMemberGroupService branchMemberGroupService;
    @Autowired
    private OrgAdminService orgAdminService;

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code));

        BranchExample example = new BranchExample();
        BranchExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return branchMapper.countByExample(example) > 0;
    }

    public void checkAuth(int partyId) {

        //===========权限
        Subject subject = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        Integer loginUserId = shiroUser.getId();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin) throw new UnauthorizedException();
        }
    }

    public String genCode(int partyId) {

        int num;
        BranchExample example = new BranchExample();
        example.createCriteria().andPartyIdEqualTo(partyId);
        example.setOrderByClause("code desc");
        List<Branch> branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (branchs.size() > 0) {
            String code = branchs.get(0).getCode();
            String _code = code.substring(code.length() - 3);
            num = Integer.parseInt(_code) + 1;
        } else {
            num = 1;
        }
        Party party = partyMapper.selectByPrimaryKey(partyId);
        return party.getCode() + String.format("%03d", num);
    }

    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public int insertSelective(Branch record) {

        checkAuth(record.getPartyId());

        record.setIsDeleted(false);
        record.setCode(genCode(record.getPartyId()));
        branchMapper.insertSelective(record);
        Integer id = record.getId();
        Branch _record = new Branch();
        _record.setId(id);
        _record.setSortOrder(id);
        return branchMapper.updateByPrimaryKeySelective(_record);
    }

    /*@Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public void del(Integer id) {
        Branch branch = branchMapper.selectByPrimaryKey(id);
        checkAuth(branch.getPartyId());

        branchMapper.deleteByPrimaryKey(id);
    }*/

    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public void batchDel(Integer[] ids, boolean isDeleted) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            Branch branch = branchMapper.selectByPrimaryKey(id);
            checkAuth(branch.getPartyId());

            if(!isDeleted){ // 恢复支部
                Party party = partyMapper.selectByPrimaryKey(branch.getPartyId());
                if(party.getIsDeleted())
                    throw new RuntimeException(String.format("恢复支部失败，支部所属的分党委【%s】已删除。", party.getName()));
            }

            if(isDeleted) {
                // 删除所有的支部委员会
                BranchMemberGroupExample example = new BranchMemberGroupExample();
                example.createCriteria().andBranchIdEqualTo(id);
                List<BranchMemberGroup> branchMemberGroups = branchMemberGroupMapper.selectByExample(example);
                if (branchMemberGroups.size() > 0) {
                    List<Integer> groupIds = new ArrayList<>();
                    for (BranchMemberGroup branchMemberGroup : branchMemberGroups) {
                        groupIds.add(branchMemberGroup.getId());
                    }
                    branchMemberGroupService.batchDel(groupIds.toArray(new Integer[]{}), true);
                }

                // 删除所有的支部管理员
                orgAdminService.delAllOrgAdmin(null, id);
            }
        }

        BranchExample example = new BranchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        Branch record = new Branch();
        record.setIsDeleted(isDeleted);
        branchMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Branch record) {

        Branch branch = branchMapper.selectByPrimaryKey(record.getId());
        checkAuth(branch.getPartyId());

        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        return branchMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "Branch:ALL")
    public Map<Integer, Branch> findAll() {

        BranchExample example = new BranchExample();
        example.setOrderByClause("sort_order desc");
        List<Branch> branches = branchMapper.selectByExample(example);
        Map<Integer, Branch> map = new LinkedHashMap<>();
        for (Branch branch : branches) {
            map.put(branch.getId(), branch);
        }

        return map;
    }
}
