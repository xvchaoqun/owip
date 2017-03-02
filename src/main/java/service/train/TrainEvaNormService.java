package service.train;

import domain.train.TrainEvaNorm;
import domain.train.TrainEvaNormExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class TrainEvaNormService extends BaseMapper {

    // 更新评估指标的数量
    private void updateNormNum(Integer fid){
        if(fid!=null){
            TrainEvaNormExample example = new TrainEvaNormExample();
            example.createCriteria().andFidEqualTo(fid);
            int normNum = trainEvaNormMapper.countByExample(example);
            TrainEvaNorm _main = new TrainEvaNorm();
            _main.setId(fid);
            _main.setNormNum(normNum);
            trainEvaNormMapper.updateByPrimaryKeySelective(_main);
        }
    }

    @Transactional
    @CacheEvict(value="TrainEvaNorms", allEntries = true)
    public void insertSelective(TrainEvaNorm record){

        String whereSql = "eva_table_id=" + record.getEvaTableId();
        if(record.getFid()!=null){
            whereSql += " and fid="+record.getFid();
        }
        record.setSortOrder(getNextSortOrder("train_eva_norm", whereSql));

        record.setNormNum(0);
        trainEvaNormMapper.insertSelective(record);

        updateNormNum(record.getFid());
    }

    @Transactional
    @CacheEvict(value="TrainEvaNorms", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        List<TrainEvaNorm> subTrainEvaNorms = null;
        {
            TrainEvaNormExample example = new TrainEvaNormExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNotNull();
            subTrainEvaNorms = trainEvaNormMapper.selectByExample(example);
        }

        {
            //删除评估指标
            TrainEvaNormExample example = new TrainEvaNormExample();
            example.createCriteria().andFidIn(Arrays.asList(ids));
            trainEvaNormMapper.deleteByExample(example);
        }

        {
            // 删除评估内容
            TrainEvaNormExample example = new TrainEvaNormExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            trainEvaNormMapper.deleteByExample(example);
        }

        {
            // 更新数量
            if(subTrainEvaNorms!=null){
                for (TrainEvaNorm subTrainEvaNorm : subTrainEvaNorms) {
                    updateNormNum(subTrainEvaNorm.getFid());
                }
            }
        }

    }

    @Transactional
    @CacheEvict(value="TrainEvaNorms", allEntries = true)
    public int updateByPrimaryKeySelective(TrainEvaNorm record){

        record.setEvaTableId(null);
        record.setFid(null);
        record.setNormNum(null);
        return trainEvaNormMapper.updateByPrimaryKeySelective(record);
    }

    // <评估内容ID， 评估内容> ，其中评估内容下可能有二级指标
    @Cacheable(value="TrainEvaNorms")
    public Map<Integer, TrainEvaNorm> findAll(int evaTableId) {

        List<TrainEvaNorm> trainEvaNormes = new ArrayList<>();
        {
            TrainEvaNormExample example = new TrainEvaNormExample();
            example.createCriteria().andEvaTableIdEqualTo(evaTableId).andFidIsNull();
            example.setOrderByClause("sort_order desc");
            trainEvaNormes = trainEvaNormMapper.selectByExample(example);
        }

        Map<Integer, TrainEvaNorm> map = new LinkedHashMap<>();
        int topIndex=0;
        for (TrainEvaNorm trainEvaNorm : trainEvaNormes) {

            TrainEvaNormExample example = new TrainEvaNormExample();
            example.createCriteria().andEvaTableIdEqualTo(evaTableId).andFidEqualTo(trainEvaNorm.getId());
            example.setOrderByClause("sort_order desc");
            List<TrainEvaNorm> subNorms = trainEvaNormMapper.selectByExample(example);
            int subIndex = 0;
            for (TrainEvaNorm subNorm : subNorms) {
                subNorm.setTopNorm(trainEvaNorm); // 上级指标，即评估内容
                subNorm.setTopIndex(topIndex);
                subNorm.setSubIndex(subIndex++);
            }
            topIndex++;
            trainEvaNorm.setSubNorms(subNorms);  // 评估内容包含的二级指标

            map.put(trainEvaNorm.getId(), trainEvaNorm);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "TrainEvaNorms", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        TrainEvaNorm entity = trainEvaNormMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer evaTableId = entity.getEvaTableId();
        Integer fid = entity.getFid();

        TrainEvaNormExample example = new TrainEvaNormExample();
        if (addNum > 0) {

            TrainEvaNormExample.Criteria criteria = example.createCriteria().andEvaTableIdEqualTo(evaTableId);
            if(fid!=null) criteria.andFidEqualTo(fid);
            criteria.andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            TrainEvaNormExample.Criteria criteria = example.createCriteria().andEvaTableIdEqualTo(evaTableId);
            if(fid!=null) criteria.andFidEqualTo(fid);
            criteria.andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<TrainEvaNorm> overEntities = trainEvaNormMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            TrainEvaNorm targetEntity = overEntities.get(overEntities.size()-1);

            String whereSql = "eva_table_id=" + evaTableId;
            if(fid!=null){
                whereSql += " and fid="+fid;
            }

            if (addNum > 0)
                commonMapper.downOrder("train_eva_norm", whereSql, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("train_eva_norm", whereSql, baseSortOrder, targetEntity.getSortOrder());

            TrainEvaNorm record = new TrainEvaNorm();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            trainEvaNormMapper.updateByPrimaryKeySelective(record);
        }
    }
}
