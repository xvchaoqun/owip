package service.cet;

import domain.cet.CetDiscuss;
import domain.cet.CetDiscussExample;
import domain.cet.CetDiscussGroupObj;
import domain.cet.CetDiscussGroupObjExample;
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

        iCetMapper.updateDiscussTotalPeriod(record.getPlanId());
    }

    @Transactional
    public void batchDel(int planId, Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetDiscussExample example = new CetDiscussExample();
        example.createCriteria().andPlanIdEqualTo(planId).andIdIn(Arrays.asList(ids));
        cetDiscussMapper.deleteByExample(example);

        iCetMapper.updateDiscussTotalPeriod(planId);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CetDiscuss record){

        cetDiscussMapper.updateByPrimaryKeySelective(record);

        iCetMapper.updateDiscussTotalPeriod(record.getPlanId());
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        CetDiscuss entity = cetDiscussMapper.selectByPrimaryKey(id);
        changeOrder("cet_discuss", "plan_id="+entity.getPlanId(), ORDER_BY_ASC, id, addNum);
    }
}
