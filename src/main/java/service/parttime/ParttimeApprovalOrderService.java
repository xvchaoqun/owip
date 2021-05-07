package service.parttime;

import domain.parttime.ParttimeApprovalOrder;
import domain.parttime.ParttimeApprovalOrderExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.parttime.ParttimeApprovalOrderMapper;
import service.BaseMapper;

import java.util.List;

@Service
public class ParttimeApprovalOrderService extends BaseMapper {
    @Autowired
    private ParttimeApprovalOrderMapper parttimeApprovalOrderMapper;

    @Transactional
    @CacheEvict(value="ParttimeApprovalOrder:ALL", allEntries = true)
    public void del(Integer id){

        parttimeApprovalOrderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "ParttimeApprovalOrder:ALL", allEntries = true)
    public void changeOrder(int applicatTypeId, int id, int addNum) {

        if(addNum == 0) return ;

        ParttimeApprovalOrder entity = parttimeApprovalOrderMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ParttimeApprovalOrderExample example = new ParttimeApprovalOrderExample();
        if (addNum > 0) {

            example.createCriteria().andApplicateTypeIdEqualTo(applicatTypeId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andApplicateTypeIdEqualTo(applicatTypeId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ParttimeApprovalOrder> overEntities = parttimeApprovalOrderMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ParttimeApprovalOrder targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("parttime_approval_order", "applicat_type_id=" + applicatTypeId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("parttime_approval_order", "applicat_type_id=" + applicatTypeId, baseSortOrder, targetEntity.getSortOrder());

            ParttimeApprovalOrder record = new ParttimeApprovalOrder();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            parttimeApprovalOrderMapper.updateByPrimaryKeySelective(record);
        }
    }

    public boolean idDuplicate(Integer id, int applicatTypeId, int approverTypeId){

        ParttimeApprovalOrderExample example = new ParttimeApprovalOrderExample();
        ParttimeApprovalOrderExample.Criteria criteria = example.createCriteria().andApplicateTypeIdEqualTo(applicatTypeId)
                .andApproverTypeIdEqualTo(approverTypeId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return parttimeApprovalOrderMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ParttimeApprovalOrder:ALL", allEntries = true)
    public int insertSelective(ParttimeApprovalOrder record){

        Assert.isTrue(!idDuplicate(null, record.getApplicateTypeId(), record.getApproverTypeId()), "duplicate");

        record.setSortOrder(getNextSortOrder("parttime_approval_order", "applicat_type_id=" + record.getApplicateTypeId()));
        return parttimeApprovalOrderMapper.insertSelective(record);
    }
}
