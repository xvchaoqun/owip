package service.cla;

import domain.cla.ClaApprover;
import domain.cla.ClaApproverExample;
import domain.cla.ClaApproverType;
import domain.cla.ClaApproverTypeExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.ClaConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ClaApproverTypeService extends BaseMapper {

    // 查询某类审批人身份 已设定的审批人 Set<干部ID>
    public Set<Integer> findApproverCadreIds(Integer typeId){

        Set<Integer> selectIdSet = new HashSet<Integer>();
        ClaApproverExample example = new ClaApproverExample();
        if(typeId!=null)
            example.createCriteria().andTypeIdEqualTo(typeId);
        List<ClaApprover> approvers = claApproverMapper.selectByExample(example);
        for (ClaApprover approver : approvers) {
            selectIdSet.add(approver.getCadreId());
        }

        return selectIdSet;
    }

    // 为某类审批人身份 设定审批人<干部ID>
    @Transactional
    @CacheEvict(value="UserPermissions", allEntries=true, beforeInvocation=true)
    public void updateApproverCadreIds(int typeId, Integer[] cadreIds){

        ClaApproverExample example = new ClaApproverExample();
        example.createCriteria().andTypeIdEqualTo(typeId);
        claApproverMapper.deleteByExample(example);

        if(cadreIds==null || cadreIds.length==0) return ;

        for (Integer cadreId : cadreIds) {

            ClaApprover record = new ClaApprover();
            record.setTypeId(typeId);
            record.setCadreId(cadreId);
            record.setSortOrder(getNextSortOrder("cla_approver", null));
            claApproverMapper.insertSelective(record);
        }
    }

   // 获得本单位正职身份
    public ClaApproverType getMainPostApproverType(){

        ClaApproverTypeExample example = new ClaApproverTypeExample();
        example.createCriteria().andTypeEqualTo(ClaConstants.CLA_APPROVER_TYPE_UNIT);
        List<ClaApproverType> approverTypes = claApproverTypeMapper.selectByExample(example);
        if(approverTypes.size()>0) return approverTypes.get(0);
        return null;
    }
    // 获得分管校领导身份
    public ClaApproverType getLeaderApproverType(){

        ClaApproverTypeExample example = new ClaApproverTypeExample();
        example.createCriteria().andTypeEqualTo(ClaConstants.CLA_APPROVER_TYPE_LEADER);
        List<ClaApproverType> approverTypes = claApproverTypeMapper.selectByExample(example);
        if(approverTypes.size()>0) return approverTypes.get(0);
        return null;
    }

    public boolean idDuplicate(Integer id, String name, byte type){

        Assert.isTrue(StringUtils.isNotBlank(name), "name is blank");

        ClaApproverTypeExample example = new ClaApproverTypeExample();
        ClaApproverTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name);
        if(type== ClaConstants.CLA_APPROVER_TYPE_UNIT
                ||type==ClaConstants.CLA_APPROVER_TYPE_LEADER) {
            criteria.andTypeEqualTo(type); // 1本单位正职 2分管校领导 只能有一个
        }
        if(id!=null) criteria.andIdNotEqualTo(id);

        return claApproverTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ClaApproverType:ALL", allEntries = true)
    public int insertSelective(ClaApproverType record){

        Assert.isTrue(!idDuplicate(null, record.getName(), record.getType()), "duplicate name and type");
        record.setSortOrder(getNextSortOrder("cla_approver_type", null));
        return claApproverTypeMapper.insertSelective(record);
    }
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 干部请假部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "ClaApproverType:ALL", allEntries = true)
    })
    public void del(Integer id){

        claApproverTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 干部请假部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "ClaApproverType:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ClaApproverTypeExample example = new ClaApproverTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        claApproverTypeMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 干部请假部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "ClaApproverType:ALL", allEntries = true)
    })
    public int updateByPrimaryKeySelective(ClaApproverType record){
        if(StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName(), record.getType()), "duplicate name and type");
        return claApproverTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="ClaApproverType:ALL")
    public Map<Integer, ClaApproverType> findAll() {

        ClaApproverTypeExample example = new ClaApproverTypeExample();
        example.setOrderByClause("sort_order desc");
        List<ClaApproverType> claApproverTypees = claApproverTypeMapper.selectByExample(example);
        Map<Integer, ClaApproverType> map = new LinkedHashMap<>();
        for (ClaApproverType claApproverType : claApproverTypees) {
            map.put(claApproverType.getId(), claApproverType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "ClaApproverType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ClaApproverType entity = claApproverTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ClaApproverTypeExample example = new ClaApproverTypeExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ClaApproverType> overEntities = claApproverTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ClaApproverType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cla_approver_type", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cla_approver_type", null, baseSortOrder, targetEntity.getSortOrder());

            ClaApproverType record = new ClaApproverType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            claApproverTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
