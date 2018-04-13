package service.cet;

import domain.cet.CetDiscuss;
import domain.cet.CetDiscussExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetDiscussService extends BaseMapper {

    @Transactional
    public void insertSelective(CetDiscuss record){

        record.setSortOrder(getNextSortOrder("cet_discuss", "plan_id="+record.getPlanId()));
        cetDiscussMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetDiscussMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetDiscussExample example = new CetDiscussExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetDiscussMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetDiscuss record){

        return cetDiscussMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        CetDiscuss entity = cetDiscussMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetDiscussExample example = new CetDiscussExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetDiscuss> overEntities = cetDiscussMapper.selectByExampleWithRowbounds(example,
                new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetDiscuss targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_discuss", "plan_id="+entity.getPlanId(),
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_discuss", "plan_id="+entity.getPlanId(),
                        baseSortOrder, targetEntity.getSortOrder());

            CetDiscuss record = new CetDiscuss();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetDiscussMapper.updateByPrimaryKeySelective(record);
        }
    }
}
