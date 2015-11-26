package service;

import domain.CadreTeachReward;
import domain.CadreTeachRewardExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreTeachRewardService extends BaseMapper {

    @Transactional
    public int insertSelective(CadreTeachReward record){

        cadreTeachRewardMapper.insertSelective(record);

        Integer id = record.getId();
        CadreTeachReward _record = new CadreTeachReward();
        _record.setId(id);
        _record.setSortOrder(id);
        return cadreTeachRewardMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    public void del(Integer id){

        cadreTeachRewardMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreTeachRewardExample example = new CadreTeachRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreTeachRewardMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreTeachReward record){
        return cadreTeachRewardMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreTeachReward> findAll() {

        CadreTeachRewardExample example = new CadreTeachRewardExample();
        example.setOrderByClause("sort_order desc");
        List<CadreTeachReward> cadreTeachRewardes = cadreTeachRewardMapper.selectByExample(example);
        Map<Integer, CadreTeachReward> map = new LinkedHashMap<>();
        for (CadreTeachReward cadreTeachReward : cadreTeachRewardes) {
            map.put(cadreTeachReward.getId(), cadreTeachReward);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CadreTeachReward entity = cadreTeachRewardMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreTeachRewardExample example = new CadreTeachRewardExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreTeachReward> overEntities = cadreTeachRewardMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreTeachReward targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("base_cadre_teach_reward", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("base_cadre_teach_reward", baseSortOrder, targetEntity.getSortOrder());

            CadreTeachReward record = new CadreTeachReward();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreTeachRewardMapper.updateByPrimaryKeySelective(record);
        }
    }
}
