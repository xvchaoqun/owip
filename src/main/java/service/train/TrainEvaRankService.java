package service.train;

import domain.train.TrainEvaRank;
import domain.train.TrainEvaRankExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainEvaRankService extends BaseMapper {

    @Transactional
    @CacheEvict(value="TrainEvaRanks", allEntries = true)
    public void insertSelective(TrainEvaRank record){

        record.setSortOrder(getNextSortOrder("train_eva_rank", "1=1"));
        trainEvaRankMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="TrainEvaRanks", allEntries = true)
    public void del(Integer id){

        trainEvaRankMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="TrainEvaRanks", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        TrainEvaRankExample example = new TrainEvaRankExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        trainEvaRankMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="TrainEvaRanks", allEntries = true)
    public int updateByPrimaryKeySelective(TrainEvaRank record){
        return trainEvaRankMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="TrainEvaRanks")
    public Map<Integer, TrainEvaRank> findAll(int evaTableId) {

        TrainEvaRankExample example = new TrainEvaRankExample();
        example.createCriteria().andEvaTableIdEqualTo(evaTableId);
        example.setOrderByClause("sort_order desc");
        List<TrainEvaRank> trainEvaRankes = trainEvaRankMapper.selectByExample(example);
        Map<Integer, TrainEvaRank> map = new LinkedHashMap<>();
        for (TrainEvaRank trainEvaRank : trainEvaRankes) {
            map.put(trainEvaRank.getId(), trainEvaRank);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "TrainEvaRanks", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        TrainEvaRank entity = trainEvaRankMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer evaTableId = entity.getEvaTableId();

        TrainEvaRankExample example = new TrainEvaRankExample();
        if (addNum > 0) {

            example.createCriteria().andEvaTableIdEqualTo(evaTableId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andEvaTableIdEqualTo(evaTableId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<TrainEvaRank> overEntities = trainEvaRankMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            TrainEvaRank targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("train_eva_rank", "eva_table_id="+evaTableId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("train_eva_rank", "eva_table_id="+evaTableId, baseSortOrder, targetEntity.getSortOrder());

            TrainEvaRank record = new TrainEvaRank();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            trainEvaRankMapper.updateByPrimaryKeySelective(record);
        }
    }
}
