package service.cadre;

import domain.cadre.CadreReward;
import domain.cadre.CadreRewardExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreRewardService extends BaseMapper {

    @Transactional
    public int insertSelective(CadreReward record){

        record.setSortOrder(getNextSortOrder("cadre_teach_reward", "1=1"));
        return cadreRewardMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreRewardMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreRewardExample example = new CadreRewardExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreRewardMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }
        CadreRewardExample example = new CadreRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreRewardMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreReward record){
        return cadreRewardMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreReward> findAll() {

        CadreRewardExample example = new CadreRewardExample();
        example.setOrderByClause("sort_order desc");
        List<CadreReward> cadreRewardes = cadreRewardMapper.selectByExample(example);
        Map<Integer, CadreReward> map = new LinkedHashMap<>();
        for (CadreReward cadreReward : cadreRewardes) {
            map.put(cadreReward.getId(), cadreReward);
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

        CadreReward entity = cadreRewardMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreRewardExample example = new CadreRewardExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreReward> overEntities = cadreRewardMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreReward targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cadre_teach_reward", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_teach_reward", null, baseSortOrder, targetEntity.getSortOrder());

            CadreReward record = new CadreReward();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreRewardMapper.updateByPrimaryKeySelective(record);
        }
    }
}
