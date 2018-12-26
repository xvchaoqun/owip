package service.cet;

import domain.cet.CetTrainEvaNorm;
import domain.cet.CetTrainEvaNormExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CetTrainEvaNormService extends CetBaseMapper {

    // 更新评估指标的数量
    private void updateNormNum(Integer fid){
        if(fid!=null){
            CetTrainEvaNormExample example = new CetTrainEvaNormExample();
            example.createCriteria().andFidEqualTo(fid);
            int normNum = (int) cetTrainEvaNormMapper.countByExample(example);
            CetTrainEvaNorm _main = new CetTrainEvaNorm();
            _main.setId(fid);
            _main.setNormNum(normNum);
            cetTrainEvaNormMapper.updateByPrimaryKeySelective(_main);
        }
    }

    @Transactional
    @CacheEvict(value="CetTrainEvaNorms", allEntries = true)
    public void insertSelective(CetTrainEvaNorm record){

        String whereSql = "eva_table_id=" + record.getEvaTableId();
        if(record.getFid()!=null){
            whereSql += " and fid="+record.getFid();
        }
        record.setSortOrder(getNextSortOrder("cet_train_eva_norm", whereSql));

        record.setNormNum(0);
        cetTrainEvaNormMapper.insertSelective(record);

        updateNormNum(record.getFid());
    }

    @Transactional
    @CacheEvict(value="CetTrainEvaNorms", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        List<CetTrainEvaNorm> subCetTrainEvaNorms = null;
        {
            CetTrainEvaNormExample example = new CetTrainEvaNormExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNotNull();
            subCetTrainEvaNorms = cetTrainEvaNormMapper.selectByExample(example);
        }

        {
            //删除评估指标
            CetTrainEvaNormExample example = new CetTrainEvaNormExample();
            example.createCriteria().andFidIn(Arrays.asList(ids));
            cetTrainEvaNormMapper.deleteByExample(example);
        }

        {
            // 删除评估内容
            CetTrainEvaNormExample example = new CetTrainEvaNormExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cetTrainEvaNormMapper.deleteByExample(example);
        }

        {
            // 更新数量
            if(subCetTrainEvaNorms!=null){
                for (CetTrainEvaNorm subCetTrainEvaNorm : subCetTrainEvaNorms) {
                    updateNormNum(subCetTrainEvaNorm.getFid());
                }
            }
        }

    }

    @Transactional
    @CacheEvict(value="CetTrainEvaNorms", allEntries = true)
    public int updateByPrimaryKeySelective(CetTrainEvaNorm record){

        record.setEvaTableId(null);
        record.setFid(null);
        record.setNormNum(null);
        return cetTrainEvaNormMapper.updateByPrimaryKeySelective(record);
    }

    // <评估内容ID， 评估内容> ，其中评估内容下可能有二级指标
    @Cacheable(value="CetTrainEvaNorms")
    public Map<Integer, CetTrainEvaNorm> findAll(int evaTableId) {

        List<CetTrainEvaNorm> cetTrainEvaNormes = new ArrayList<>();
        {
            CetTrainEvaNormExample example = new CetTrainEvaNormExample();
            example.createCriteria().andEvaTableIdEqualTo(evaTableId).andFidIsNull();
            example.setOrderByClause("sort_order desc");
            cetTrainEvaNormes = cetTrainEvaNormMapper.selectByExample(example);
        }

        Map<Integer, CetTrainEvaNorm> map = new LinkedHashMap<>();
        int topIndex=0;
        for (CetTrainEvaNorm cetTrainEvaNorm : cetTrainEvaNormes) {

            CetTrainEvaNormExample example = new CetTrainEvaNormExample();
            example.createCriteria().andEvaTableIdEqualTo(evaTableId).andFidEqualTo(cetTrainEvaNorm.getId());
            example.setOrderByClause("sort_order desc");
            List<CetTrainEvaNorm> subNorms = cetTrainEvaNormMapper.selectByExample(example);
            int subIndex = 0;
            for (CetTrainEvaNorm subNorm : subNorms) {
                subNorm.setTopNorm(cetTrainEvaNorm); // 上级指标，即评估内容
                subNorm.setTopIndex(topIndex);
                subNorm.setSubIndex(subIndex++);
            }
            topIndex++;
            cetTrainEvaNorm.setSubNorms(subNorms);  // 评估内容包含的二级指标

            map.put(cetTrainEvaNorm.getId(), cetTrainEvaNorm);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetTrainEvaNorms", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetTrainEvaNorm entity = cetTrainEvaNormMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer evaTableId = entity.getEvaTableId();
        Integer fid = entity.getFid();

        CetTrainEvaNormExample example = new CetTrainEvaNormExample();
        if (addNum > 0) {

            CetTrainEvaNormExample.Criteria criteria = example.createCriteria().andEvaTableIdEqualTo(evaTableId);
            if(fid!=null) criteria.andFidEqualTo(fid);
            criteria.andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            CetTrainEvaNormExample.Criteria criteria = example.createCriteria().andEvaTableIdEqualTo(evaTableId);
            if(fid!=null) criteria.andFidEqualTo(fid);
            criteria.andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetTrainEvaNorm> overEntities = cetTrainEvaNormMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetTrainEvaNorm targetEntity = overEntities.get(overEntities.size()-1);

            String whereSql = "eva_table_id=" + evaTableId;
            if(fid!=null){
                whereSql += " and fid="+fid;
            }

            if (addNum > 0)
                commonMapper.downOrder("cet_train_eva_norm", whereSql, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_train_eva_norm", whereSql, baseSortOrder, targetEntity.getSortOrder());

            CetTrainEvaNorm record = new CetTrainEvaNorm();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetTrainEvaNormMapper.updateByPrimaryKeySelective(record);
        }
    }
}
