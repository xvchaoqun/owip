package service.party;

import domain.Branch;
import domain.BranchExample;
import domain.Party;
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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchService extends BaseMapper {

    @Autowired
    private  PartyMemberService partyMemberService;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        BranchExample example = new BranchExample();
        BranchExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return branchMapper.countByExample(example) > 0;
    }
    public void checkAuth(int partyId){

        //===========权限
        Subject subject = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        Integer loginUserId = shiroUser.getId();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin) throw new UnauthorizedException();
        }
    }

    public String genCode(int partyId){

        int num ;
        BranchExample example = new BranchExample();
        example.createCriteria().andPartyIdEqualTo(partyId);
        example.setOrderByClause("code desc");
        List<Branch> branchs = branchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(branchs.size()>0){
            String code = branchs.get(0).getCode();
            String _code = code.substring(code.length() - 3);
            num = Integer.parseInt(_code) + 1;
        }else{
            num = 1;
        }
        Party party = partyMapper.selectByPrimaryKey(partyId);
        return party.getCode() + String.format("%03d", num);
    }
    @Transactional
    @CacheEvict(value="Branch:ALL", allEntries = true)
    public int insertSelective(Branch record){

        checkAuth(record.getPartyId());

        record.setCode(genCode(record.getPartyId()));
        branchMapper.insertSelective(record);
        Integer id = record.getId();
        Branch _record = new Branch();
        _record.setId(id);
        _record.setSortOrder(id);
        return branchMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="Branch:ALL", allEntries = true)
    public void del(Integer id){
        Branch branch = branchMapper.selectByPrimaryKey(id);
        checkAuth(branch.getPartyId());

        branchMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Branch:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            Branch branch = branchMapper.selectByPrimaryKey(id);
            checkAuth(branch.getPartyId());
        }

        BranchExample example = new BranchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="Branch:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Branch record){

        Branch branch = branchMapper.selectByPrimaryKey(record.getId());
        checkAuth(branch.getPartyId());

        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        return branchMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="Branch:ALL")
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

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Branch:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        Branch entity = branchMapper.selectByPrimaryKey(id);

        checkAuth(entity.getPartyId());

        Integer baseSortOrder = entity.getSortOrder();

        BranchExample example = new BranchExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Branch> overEntities = branchMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Branch targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("ow_branch", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch", baseSortOrder, targetEntity.getSortOrder());

            Branch record = new Branch();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMapper.updateByPrimaryKeySelective(record);
        }
    }
}
