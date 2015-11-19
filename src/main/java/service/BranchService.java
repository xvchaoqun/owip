package service;

import domain.Branch;
import domain.BranchExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        BranchExample example = new BranchExample();
        BranchExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return branchMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="Branch:ALL", allEntries = true)
    public int insertSelective(Branch record){

        Assert.isTrue(!idDuplicate(null, record.getCode()));
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

        branchMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Branch:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        BranchExample example = new BranchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="Branch:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Branch record){
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
