package service.cet;

import domain.cet.CetTrainEvaTable;
import domain.cet.CetTrainEvaTableExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetTrainEvaTableService extends CetBaseMapper {


    @Transactional
    @CacheEvict(value="CetTrainEvaTable:ALL", allEntries = true)
    public void insertSelective(CetTrainEvaTable record){

        record.setSortOrder(getNextSortOrder("cet_train_eva_table", "status="+ SystemConstants.AVAILABLE));
        cetTrainEvaTableMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetTrainEvaTable:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainEvaTableExample example = new CetTrainEvaTableExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CetTrainEvaTable record = new CetTrainEvaTable();
        record.setStatus(SystemConstants.UNAVAILABLE);
        cetTrainEvaTableMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    @CacheEvict(value="CetTrainEvaTable:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CetTrainEvaTable record){

        return cetTrainEvaTableMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetTrainEvaTable:ALL")
    public Map<Integer, CetTrainEvaTable> findAll() {

        CetTrainEvaTableExample example = new CetTrainEvaTableExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");
        List<CetTrainEvaTable> cetTrainEvaTablees = cetTrainEvaTableMapper.selectByExample(example);
        Map<Integer, CetTrainEvaTable> map = new LinkedHashMap<>();
        for (CetTrainEvaTable cetTrainEvaTable : cetTrainEvaTablees) {
            map.put(cetTrainEvaTable.getId(), cetTrainEvaTable);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetTrainEvaTable:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetTrainEvaTable entity = cetTrainEvaTableMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetTrainEvaTableExample example = new CetTrainEvaTableExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetTrainEvaTable> overEntities = cetTrainEvaTableMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetTrainEvaTable targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cet_train_eva_table", "status="+ SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_train_eva_table", "status="+ SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());

            CetTrainEvaTable record = new CetTrainEvaTable();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetTrainEvaTableMapper.updateByPrimaryKeySelective(record);
        }
    }
}
