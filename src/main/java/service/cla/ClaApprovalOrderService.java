package service.cla;

import domain.cla.ClaApprovalOrder;
import domain.cla.ClaApprovalOrderExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@Service
public class ClaApprovalOrderService extends ClaBaseMapper {

    public boolean idDuplicate(Integer id, int applicatTypeId, int approverTypeId){

        ClaApprovalOrderExample example = new ClaApprovalOrderExample();
        ClaApprovalOrderExample.Criteria criteria = example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId)
                .andApproverTypeIdEqualTo(approverTypeId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return claApprovalOrderMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ClaApprovalOrder:ALL", allEntries = true)
    public int insertSelective(ClaApprovalOrder record){

        Assert.isTrue(!idDuplicate(null, record.getApplicatTypeId(), record.getApproverTypeId()), "duplicate");

        record.setSortOrder(getNextSortOrder("cla_approval_order", "applicat_type_id=" + record.getApplicatTypeId()));
        return claApprovalOrderMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="ClaApprovalOrder:ALL", allEntries = true)
    public void del(Integer id){

        claApprovalOrderMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="ClaApprovalOrder:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ClaApprovalOrderExample example = new ClaApprovalOrderExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        claApprovalOrderMapper.deleteByExample(example);
    }

    /*@Transactional
    @CacheEvict(value="ClaApprovalOrder:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ClaApprovalOrder record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        return claApprovalOrderMapper.updateByPrimaryKeySelective(record);
    }*/

    /*@Cacheable(value="ClaApprovalOrder:ALL")
    public Map<Integer, ClaApprovalOrder> findAll() {

        ClaApprovalOrderExample example = new ClaApprovalOrderExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<ClaApprovalOrder> approvalOrderes = claApprovalOrderMapper.selectByExample(example);
        Map<Integer, ClaApprovalOrder> map = new LinkedHashMap<>();
        for (ClaApprovalOrder approvalOrder : approvalOrderes) {
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
    @CacheEvict(value = "ClaApprovalOrder:ALL", allEntries = true)
    public void changeOrder(int applicatTypeId, int id, int addNum) {

        if(addNum == 0) return ;

        ClaApprovalOrder entity = claApprovalOrderMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ClaApprovalOrderExample example = new ClaApprovalOrderExample();
        if (addNum > 0) {

            example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ClaApprovalOrder> overEntities = claApprovalOrderMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ClaApprovalOrder targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cla_approval_order", "applicat_type_id=" + applicatTypeId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cla_approval_order", "applicat_type_id=" + applicatTypeId, baseSortOrder, targetEntity.getSortOrder());

            ClaApprovalOrder record = new ClaApprovalOrder();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            claApprovalOrderMapper.updateByPrimaryKeySelective(record);
        }
    }
}
