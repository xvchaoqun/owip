package service;

import domain.Cadre;
import domain.CadreExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreService extends BaseMapper {

    public boolean idDuplicate(Integer id, int userId){

        CadreExample example = new CadreExample();
        CadreExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public int insertSelective(Cadre record){

        cadreMapper.insertSelective(record);
        Integer id = record.getId();
        Cadre _record = new Cadre();
        _record.setId(id);
        _record.setSortOrder(id);
        return cadreMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public void del(Integer id){

        cadreMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Leader:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreExample example = new CadreExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreMapper.deleteByExample(example);
    }
    
    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Cadre record){
        return cadreMapper.updateByPrimaryKeySelective(record);
    }
    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public int updateByExampleSelective(Cadre record, CadreExample example){

        return cadreMapper.updateByExampleSelective(record, example);
    }

    @Cacheable(value="Cadre:ALL")
    public Map<Integer, Cadre> findAll() {

        CadreExample example = new CadreExample();
        example.setOrderByClause("sort_order desc");
        List<Cadre> cadrees = cadreMapper.selectByExample(example);
        Map<Integer, Cadre> map = new LinkedHashMap<>();
        for (Cadre cadre : cadrees) {
            map.put(cadre.getId(), cadre);
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
    @CacheEvict(value = "Cadre:ALL", allEntries = true)
    public void changeOrder(int id, byte status, int addNum) {

        if(addNum == 0) return ;

        Cadre entity = cadreMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreExample example = new CadreExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(status).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andStatusEqualTo(status).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Cadre> overEntities = cadreMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Cadre targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_cadre(status, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_cadre(status, baseSortOrder, targetEntity.getSortOrder());

            Cadre record = new Cadre();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreMapper.updateByPrimaryKeySelective(record);
        }
    }
}
