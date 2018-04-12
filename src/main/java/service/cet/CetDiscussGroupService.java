package service.cet;

import domain.cet.CetDiscussGroup;
import domain.cet.CetDiscussGroupExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetDiscussGroupService extends BaseMapper {

    @Transactional
    public void insertSelective(CetDiscussGroup record){

        record.setSortOrder(getNextSortOrder("cet_discuss_group", "discuss_id="+record.getDiscussId()));
        cetDiscussGroupMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetDiscussGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetDiscussGroupExample example = new CetDiscussGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetDiscussGroupMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetDiscussGroup record){

        return cetDiscussGroupMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetDiscussGroup:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        CetDiscussGroup entity = cetDiscussGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetDiscussGroupExample example = new CetDiscussGroupExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetDiscussGroup> overEntities = cetDiscussGroupMapper.selectByExampleWithRowbounds(example,
                new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetDiscussGroup targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_discuss_group", "discuss_id="+entity.getDiscussId(),
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_discuss_group", "discuss_id="+entity.getDiscussId(),
                        baseSortOrder, targetEntity.getSortOrder());

            CetDiscussGroup record = new CetDiscussGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetDiscussGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
