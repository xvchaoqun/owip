package service.abroad;

import domain.abroad.Approver;
import domain.abroad.ApproverExample;
import domain.abroad.ApproverType;
import domain.abroad.ApproverTypeExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ApproverTypeService extends BaseMapper {

    // 查询某类审批人身份 已设定的审批人 Set<干部ID>
    public Set<Integer> findApproverCadreIds(Integer typeId){

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

    // 为某类审批人身份 设定审批人<干部ID>
    @Transactional
    @CacheEvict(value="UserPermissions", allEntries=true, beforeInvocation=true)
    public void updateApproverCadreIds(int typeId, Integer[] cadreIds){

        ApproverExample example = new ApproverExample();
        example.createCriteria().andTypeIdEqualTo(typeId);
        approverMapper.deleteByExample(example);

        if(cadreIds==null || cadreIds.length==0) return ;

        for (Integer cadreId : cadreIds) {

            Approver record = new Approver();
            record.setTypeId(typeId);
            record.setCadreId(cadreId);
            record.setSortOrder(getNextSortOrder("abroad_approver", null));
            approverMapper.insertSelective(record);
        }
    }

    @Transactional
    @CacheEvict(value="ApproverType:ALL", allEntries = true)
    public int insertSelective(ApproverType record){

        record.setSortOrder(getNextSortOrder("abroad_approver_type", null));
        return approverTypeMapper.insertSelective(record);
    }
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "ApproverType:ALL", allEntries = true)
    })
    public void del(Integer id){

        approverTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "ApproverType:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApproverTypeExample example = new ApproverTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        approverTypeMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "ApproverType:ALL", allEntries = true)
    })
    public int updateByPrimaryKeySelective(ApproverType record){

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
                commonMapper.downOrder("abroad_approver_type", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("abroad_approver_type", null, baseSortOrder, targetEntity.getSortOrder());

            ApproverType record = new ApproverType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            approverTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
