package service.abroad;

import domain.Approver;
import domain.ApproverExample;
import domain.ApproverType;
import domain.ApproverTypeExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.*;

@Service
public class ApproverTypeService extends BaseMapper {

    public Set<Integer> getCadreIds (Integer typeId){

        Set<Integer> selectIdSet = new HashSet<Integer>();
        ApproverExample example = new ApproverExample();
        if(typeId!=null)
            example.createCriteria().andTypeIdEqualTo(typeId);
        List<Approver> approvers = approverMapper.selectByExample(example);
        for (Approver approver : approvers) {
            selectIdSet.add(approver.getCadreId());
        }

        return selectIdSet;
    }

    @Transactional
    public void updateCadreIds(int typeId, Integer[] cadreIds){

        ApproverExample example = new ApproverExample();
        example.createCriteria().andTypeIdEqualTo(typeId);
        approverMapper.deleteByExample(example);

        if(cadreIds==null || cadreIds.length==0) return ;

        for (Integer cadreId : cadreIds) {

            Approver record = new Approver();
            record.setTypeId(typeId);
            record.setCadreId(cadreId);
            approverMapper.insert(record);

            Approver _record = new Approver();
            _record.setId(record.getId());
            _record.setSortOrder(record.getId());
            approverMapper.updateByPrimaryKeySelective(_record);
        }
    }

    public boolean idDuplicate(Integer id, String name, byte type){

        Assert.isTrue(StringUtils.isNotBlank(name));

        ApproverTypeExample example = new ApproverTypeExample();
        ApproverTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name);
        if(type==1||type==2) criteria.andTypeEqualTo(type); // 1本单位正职 2分管校领导 只能有一个
        if(id!=null) criteria.andIdNotEqualTo(id);

        return approverTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ApproverType:ALL", allEntries = true)
    public int insertSelective(ApproverType record){

        Assert.isTrue(!idDuplicate(null, record.getName(), record.getType()));
        approverTypeMapper.insertSelective(record);

        ApproverType _record = new ApproverType();
        _record.setId(record.getId());
        _record.setSortOrder(record.getId());
        return approverTypeMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="ApproverType:ALL", allEntries = true)
    public void del(Integer id){

        approverTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="ApproverType:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApproverTypeExample example = new ApproverTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        approverTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="ApproverType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ApproverType record){
        if(StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName(), record.getType()));
        return approverTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="ApproverType:ALL")
    public Map<Integer, ApproverType> findAll() {

        ApproverTypeExample example = new ApproverTypeExample();
        example.setOrderByClause("sort_order desc");
        List<ApproverType> approverTypees = approverTypeMapper.selectByExample(example);
        Map<Integer, ApproverType> map = new LinkedHashMap<>();
        for (ApproverType approverType : approverTypees) {
            map.put(approverType.getId(), approverType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "ApproverType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ApproverType entity = approverTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ApproverTypeExample example = new ApproverTypeExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ApproverType> overEntities = approverTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ApproverType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("abroad_approver_type", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("abroad_approver_type", baseSortOrder, targetEntity.getSortOrder());

            ApproverType record = new ApproverType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            approverTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
