package service.dr;

import domain.dr.DrVoterTypeTpl;
import domain.dr.DrVoterTypeTplExample;
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
public class DrVoterTypeTplService extends DrBaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrVoterTypeTplExample example = new DrVoterTypeTplExample();
        DrVoterTypeTplExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drVoterTypeTplMapper.countByExample(example) > 0;
    }*/

    @Transactional
    @CacheEvict(value="DrVoterTypeTpl:ALL", allEntries = true)
    public void insertSelective(DrVoterTypeTpl record){

        record.setSortOrder(getNextSortOrder("dr_voter_type_tpl", null));
        drVoterTypeTplMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="DrVoterTypeTpl:ALL", allEntries = true)
    public void del(Integer id){

        drVoterTypeTplMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="DrVoterTypeTpl:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DrVoterTypeTplExample example = new DrVoterTypeTplExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drVoterTypeTplMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="DrVoterTypeTpl:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(DrVoterTypeTpl record){
        return drVoterTypeTplMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DrVoterTypeTpl:ALL")
    public Map<Integer, DrVoterTypeTpl> findAll() {

        DrVoterTypeTplExample example = new DrVoterTypeTplExample();
        example.setOrderByClause("sort_order desc");
        List<DrVoterTypeTpl> drVoterTypeTpls = drVoterTypeTplMapper.selectByExample(example);
        Map<Integer, DrVoterTypeTpl> map = new LinkedHashMap<>();
        for (DrVoterTypeTpl drVoterTypeTpl : drVoterTypeTpls) {
            map.put(drVoterTypeTpl.getId(), drVoterTypeTpl);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "DrVoterTypeTpl:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        DrVoterTypeTpl entity = drVoterTypeTplMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        DrVoterTypeTplExample example = new DrVoterTypeTplExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DrVoterTypeTpl> overEntities = drVoterTypeTplMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            DrVoterTypeTpl targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("dr_voter_type_tpl", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dr_voter_type_tpl", null, baseSortOrder, targetEntity.getSortOrder());

            DrVoterTypeTpl record = new DrVoterTypeTpl();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            drVoterTypeTplMapper.updateByPrimaryKeySelective(record);
        }
    }
}
