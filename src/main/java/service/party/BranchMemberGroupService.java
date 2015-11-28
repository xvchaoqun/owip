package service.party;

import domain.BranchMemberGroup;
import domain.BranchMemberGroupExample;
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
public class BranchMemberGroupService extends BaseMapper {

    @Transactional
    @CacheEvict(value="BranchMemberGroup:ALL", allEntries = true)
    public int insertSelective(BranchMemberGroup record){

        branchMemberGroupMapper.insertSelective(record);

        Integer id = record.getId();
        BranchMemberGroup _record = new BranchMemberGroup();
        _record.setId(id);
        _record.setSortOrder(id);
        return branchMemberGroupMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="BranchMemberGroup:ALL", allEntries = true)
    public void del(Integer id){

        branchMemberGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="BranchMemberGroup:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchMemberGroupMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="BranchMemberGroup:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(BranchMemberGroup record){
        return branchMemberGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="BranchMemberGroup:ALL")
    public Map<Integer, BranchMemberGroup> findAll() {

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.setOrderByClause("sort_order desc");
        List<BranchMemberGroup> branchMemberGroupes = branchMemberGroupMapper.selectByExample(example);
        Map<Integer, BranchMemberGroup> map = new LinkedHashMap<>();
        for (BranchMemberGroup branchMemberGroup : branchMemberGroupes) {
            map.put(branchMemberGroup.getId(), branchMemberGroup);
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
    @CacheEvict(value = "BranchMemberGroup:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        BranchMemberGroup entity = branchMemberGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<BranchMemberGroup> overEntities = branchMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            BranchMemberGroup targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("ow_branch_member_group", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch_member_group", baseSortOrder, targetEntity.getSortOrder());

            BranchMemberGroup record = new BranchMemberGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMemberGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
