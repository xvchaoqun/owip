package service.cet;

import domain.cet.CetSpecialPlan;
import domain.cet.CetSpecialPlanExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetSpecialPlanService extends BaseMapper {

    public boolean idDuplicate(Integer id, int specialId, byte type){

        CetSpecialPlanExample example = new CetSpecialPlanExample();
        CetSpecialPlanExample.Criteria criteria = example.createCriteria().andSpecialIdEqualTo(specialId).andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetSpecialPlanMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetSpecialPlan record){

        Assert.isTrue(!idDuplicate(null, record.getSpecialId(), record.getType()), "duplicate");
        record.setSortOrder(getNextSortOrder("cet_special_plan", "special_id=" + record.getSpecialId()));
        cetSpecialPlanMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetSpecialPlanMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetSpecialPlanExample example = new CetSpecialPlanExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetSpecialPlanMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetSpecialPlan record){
        if(record.getSpecialId()!=null && record.getType()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getSpecialId(), record.getType()), "duplicate");
        return cetSpecialPlanMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        CetSpecialPlan entity = cetSpecialPlanMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetSpecialPlanExample example = new CetSpecialPlanExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetSpecialPlan> overEntities = cetSpecialPlanMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetSpecialPlan targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_special_plan", "special_id=" + entity.getSpecialId(), baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_special_plan", "special_id=" + entity.getSpecialId(), baseSortOrder, targetEntity.getSortOrder());

            CetSpecialPlan record = new CetSpecialPlan();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetSpecialPlanMapper.updateByPrimaryKeySelective(record);
        }
    }
}
