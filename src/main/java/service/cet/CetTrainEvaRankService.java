package service.cet;

import domain.cet.CetTrainEvaRank;
import domain.cet.CetTrainEvaRankExample;
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
public class CetTrainEvaRankService extends BaseMapper {

    @Transactional
    @CacheEvict(value="CetTrainEvaRanks", allEntries = true)
    public void insertSelective(CetTrainEvaRank record){

        record.setSortOrder(getNextSortOrder("cet_train_eva_rank", "1=1"));
        cetTrainEvaRankMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetTrainEvaRanks", allEntries = true)
    public void del(Integer id){

        cetTrainEvaRankMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="CetTrainEvaRanks", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainEvaRankExample example = new CetTrainEvaRankExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTrainEvaRankMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="CetTrainEvaRanks", allEntries = true)
    public int updateByPrimaryKeySelective(CetTrainEvaRank record){
        return cetTrainEvaRankMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetTrainEvaRanks")
    public Map<Integer, CetTrainEvaRank> findAll(int evaTableId) {

        CetTrainEvaRankExample example = new CetTrainEvaRankExample();
        example.createCriteria().andEvaTableIdEqualTo(evaTableId);
        example.setOrderByClause("sort_order desc");
        List<CetTrainEvaRank> cetTrainEvaRankes = cetTrainEvaRankMapper.selectByExample(example);
        Map<Integer, CetTrainEvaRank> map = new LinkedHashMap<>();
        for (CetTrainEvaRank cetTrainEvaRank : cetTrainEvaRankes) {
            map.put(cetTrainEvaRank.getId(), cetTrainEvaRank);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetTrainEvaRanks", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetTrainEvaRank entity = cetTrainEvaRankMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer evaTableId = entity.getEvaTableId();

        CetTrainEvaRankExample example = new CetTrainEvaRankExample();
        if (addNum > 0) {

            example.createCriteria().andEvaTableIdEqualTo(evaTableId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andEvaTableIdEqualTo(evaTableId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetTrainEvaRank> overEntities = cetTrainEvaRankMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetTrainEvaRank targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cet_train_eva_rank", "eva_table_id="+evaTableId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_train_eva_rank", "eva_table_id="+evaTableId, baseSortOrder, targetEntity.getSortOrder());

            CetTrainEvaRank record = new CetTrainEvaRank();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetTrainEvaRankMapper.updateByPrimaryKeySelective(record);
        }
    }
}
