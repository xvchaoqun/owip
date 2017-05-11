package service.abroad;

import domain.abroad.ApprovalOrder;
import domain.abroad.ApprovalOrderExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ApprovalOrderService extends BaseMapper {

    public boolean idDuplicate(Integer id, int applicatTypeId, int approverTypeId){

        ApprovalOrderExample example = new ApprovalOrderExample();
        ApprovalOrderExample.Criteria criteria = example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId)
                .andApproverTypeIdEqualTo(approverTypeId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return approvalOrderMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ApprovalOrder:ALL", allEntries = true)
    public int insertSelective(ApprovalOrder record){

        Assert.isTrue(!idDuplicate(null, record.getApplicatTypeId(), record.getApproverTypeId()), "duplicate");

        record.setSortOrder(getNextSortOrder("abroad_approval_order", "applicat_type_id=" + record.getApplicatTypeId()));
        return approvalOrderMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="ApprovalOrder:ALL", allEntries = true)
    public void del(Integer id){

        approvalOrderMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="ApprovalOrder:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApprovalOrderExample example = new ApprovalOrderExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        approvalOrderMapper.deleteByExample(example);
    }

    /*@Transactional
    @CacheEvict(value="ApprovalOrder:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ApprovalOrder record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        return approvalOrderMapper.updateByPrimaryKeySelective(record);
    }*/

    /*@Cacheable(value="ApprovalOrder:ALL")
    public Map<Integer, ApprovalOrder> findAll() {

        ApprovalOrderExample example = new ApprovalOrderExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<ApprovalOrder> approvalOrderes = approvalOrderMapper.selectByExample(example);
        Map<Integer, ApprovalOrder> map = new LinkedHashMap<>();
        for (ApprovalOrder approvalOrder : approvalOrderes) {
            map.put(approvalOrder.getId(), approvalOrder);
        }

        return map;
    }*/

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "ApprovalOrder:ALL", allEntries = true)
    public void changeOrder(int applicatTypeId, int id, int addNum) {

        if(addNum == 0) return ;

        ApprovalOrder entity = approvalOrderMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ApprovalOrderExample example = new ApprovalOrderExample();
        if (addNum > 0) {

            example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ApprovalOrder> overEntities = approvalOrderMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ApprovalOrder targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("abroad_approval_order", "applicat_type_id=" + applicatTypeId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("abroad_approval_order", "applicat_type_id=" + applicatTypeId, baseSortOrder, targetEntity.getSortOrder());

            ApprovalOrder record = new ApprovalOrder();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            approvalOrderMapper.updateByPrimaryKeySelective(record);
        }
    }
}
