package service.cet;

import domain.cet.CetDiscuss;
import domain.cet.CetDiscussExample;
import domain.cet.CetDiscussGroupObj;
import domain.cet.CetDiscussGroupObjExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetDiscussService extends CetBaseMapper {

    // 查看已分组学员情况 <projectObjId, CetDiscussGroupObj>
    public Map<Integer, CetDiscussGroupObj> getObjMap(int discussId){

        Map<Integer, CetDiscussGroupObj> resultMap = new HashMap<>();

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        example.createCriteria().andDiscussIdEqualTo(discussId);
        List<CetDiscussGroupObj> cetDiscussGroupObjs = cetDiscussGroupObjMapper.selectByExample(example);
        for (CetDiscussGroupObj cetDiscussGroupObj : cetDiscussGroupObjs) {

            resultMap.put(cetDiscussGroupObj.getObjId(), cetDiscussGroupObj);
        }

        return resultMap;
    }

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
        Integer planId = entity.getPlanId();

        CetDiscussExample example = new CetDiscussExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andPlanIdEqualTo(planId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andPlanIdEqualTo(planId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetDiscuss> overEntities = cetDiscussMapper.selectByExampleWithRowbounds(example,
                new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetDiscuss targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_discuss", "plan_id="+planId,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_discuss", "plan_id="+planId,
                        baseSortOrder, targetEntity.getSortOrder());

            CetDiscuss record = new CetDiscuss();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetDiscussMapper.updateByPrimaryKeySelective(record);
        }
    }
}
