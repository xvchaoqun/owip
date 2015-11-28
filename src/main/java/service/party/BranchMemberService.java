package service.party;

import domain.BranchMember;
import domain.BranchMemberExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchMemberService extends BaseMapper {


    // 查询用户是否是支部管理员
    public boolean isAdmin(Integer userId, Integer branchId){
        if(userId==null || branchId == null) return false;
        return commonMapper.isBranchAdmin(userId, branchId)>0;
    }

    public boolean idDuplicate(Integer id, int groupId, int userId){

        BranchMemberExample example = new BranchMemberExample();
        BranchMemberExample.Criteria criteria = example.createCriteria()
                .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return branchMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="BranchMember:ALL", allEntries = true)
    public int insertSelective(BranchMember record){

        branchMemberMapper.insertSelective(record);

        Integer id = record.getId();
        BranchMember _record = new BranchMember();
        _record.setId(id);
        _record.setSortOrder(id);
        return branchMemberMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="BranchMember:ALL", allEntries = true)
    public void del(Integer id){

        branchMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="BranchMember:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        BranchMemberExample example = new BranchMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchMemberMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="BranchMember:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(BranchMember record){
        return branchMemberMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="BranchMember:ALL")
    public Map<Integer, BranchMember> findAll() {

        BranchMemberExample example = new BranchMemberExample();
        example.setOrderByClause("sort_order desc");
        List<BranchMember> branchMemberes = branchMemberMapper.selectByExample(example);
        Map<Integer, BranchMember> map = new LinkedHashMap<>();
        for (BranchMember branchMember : branchMemberes) {
            map.put(branchMember.getId(), branchMember);
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
    @CacheEvict(value = "BranchMember:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        BranchMember entity = branchMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        BranchMemberExample example = new BranchMemberExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<BranchMember> overEntities = branchMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            BranchMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("ow_branch_member", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch_member", baseSortOrder, targetEntity.getSortOrder());

            BranchMember record = new BranchMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
