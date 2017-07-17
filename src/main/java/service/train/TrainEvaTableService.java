package service.train;

import domain.train.TrainEvaTable;
import domain.train.TrainEvaTableExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainEvaTableService extends BaseMapper {


    @Transactional
    @CacheEvict(value="TrainEvaTable:ALL", allEntries = true)
    public void insertSelective(TrainEvaTable record){

        record.setSortOrder(getNextSortOrder("train_eva_table", "status="+ SystemConstants.AVAILABLE));
        trainEvaTableMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="TrainEvaTable:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        TrainEvaTableExample example = new TrainEvaTableExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        TrainEvaTable record = new TrainEvaTable();
        record.setStatus(SystemConstants.UNAVAILABLE);
        trainEvaTableMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    @CacheEvict(value="TrainEvaTable:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(TrainEvaTable record){

        return trainEvaTableMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="TrainEvaTable:ALL")
    public Map<Integer, TrainEvaTable> findAll() {

        TrainEvaTableExample example = new TrainEvaTableExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");
        List<TrainEvaTable> trainEvaTablees = trainEvaTableMapper.selectByExample(example);
        Map<Integer, TrainEvaTable> map = new LinkedHashMap<>();
        for (TrainEvaTable trainEvaTable : trainEvaTablees) {
            map.put(trainEvaTable.getId(), trainEvaTable);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "TrainEvaTable:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        TrainEvaTable entity = trainEvaTableMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        TrainEvaTableExample example = new TrainEvaTableExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<TrainEvaTable> overEntities = trainEvaTableMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            TrainEvaTable targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("train_eva_table", "status="+ SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("train_eva_table", "status="+ SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());

            TrainEvaTable record = new TrainEvaTable();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            trainEvaTableMapper.updateByPrimaryKeySelective(record);
        }
    }
}
