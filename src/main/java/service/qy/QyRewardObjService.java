package service.qy;

import domain.qy.QyRewardObj;
import domain.qy.QyRewardObjExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QyRewardObjService extends QyBaseMapper {

 /*   public boolean idDuplicate(Integer id, Integer objId,Byte type){

        if(objId==null||type==null) return false;

        QyRewardObjExample example = new QyRewardObjExample();
        QyRewardObjExample.Criteria criteria = example.createCriteria();
        if(id!=null)
            criteria.andIdNotEqualTo(id);
        if(type==1)
            criteria.andPartyIdNotEqualTo(objId);
        if(type==2)
            criteria.andBranchIdNotEqualTo(objId);
        if(type==3)
            criteria.andUserIdNotEqualTo(objId);
        return qyRewardObjMapper.countByExample(example) > 0;
    }*/
    @Transactional
    public void insertSelective(QyRewardObj record){

        record.setSortOrder(getNextSortOrder("qy_reward_obj", null));
        qyRewardObjMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        qyRewardObjMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        QyRewardObjExample example = new QyRewardObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        qyRewardObjMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(QyRewardObj record){
        qyRewardObjMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, QyRewardObj> findAll() {

        QyRewardObjExample example = new QyRewardObjExample();
        example.setOrderByClause("sort_order desc");
        List<QyRewardObj> records = qyRewardObjMapper.selectByExample(example);
        Map<Integer, QyRewardObj> map = new LinkedHashMap<>();
        for (QyRewardObj record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {
      QyRewardObj record = qyRewardObjMapper.selectByPrimaryKey(id);
        changeOrder("qy_reward_obj", "record_id=" + record.getRecordId(), ORDER_BY_DESC, id, addNum);
    }
}
