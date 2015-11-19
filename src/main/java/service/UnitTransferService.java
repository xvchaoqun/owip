package service;

import domain.UnitTransfer;
import domain.UnitTransferExample;
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
public class UnitTransferService extends BaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        UnitTransferExample example = new UnitTransferExample();
        UnitTransferExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitTransferMapper.countByExample(example) > 0;
    }*/

    @Transactional
    @CacheEvict(value="UnitTransfer:ALL", allEntries = true)
    public int insertSelective(UnitTransfer record){

        unitTransferMapper.insertSelective(record);

        Integer id = record.getId();
        UnitTransfer _record = new UnitTransfer();
        _record.setId(id);
        _record.setSortOrder(id);
        return unitTransferMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="UnitTransfer:ALL", allEntries = true)
    public void del(Integer id){

        unitTransferMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="UnitTransfer:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitTransferExample example = new UnitTransferExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitTransferMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="UnitTransfer:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(UnitTransfer record){
        return unitTransferMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="UnitTransfer:ALL")
    public Map<Integer, UnitTransfer> findAll() {

        UnitTransferExample example = new UnitTransferExample();
        example.setOrderByClause("sort_order desc");
        List<UnitTransfer> unitTransferes = unitTransferMapper.selectByExample(example);
        Map<Integer, UnitTransfer> map = new LinkedHashMap<>();
        for (UnitTransfer unitTransfer : unitTransferes) {
            map.put(unitTransfer.getId(), unitTransfer);
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
    @CacheEvict(value = "UnitTransfer:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        UnitTransfer entity = unitTransferMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitTransferExample example = new UnitTransferExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitTransfer> overEntities = unitTransferMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            UnitTransfer targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("base_unitTransfer", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("base_unitTransfer", baseSortOrder, targetEntity.getSortOrder());

            UnitTransfer record = new UnitTransfer();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitTransferMapper.updateByPrimaryKeySelective(record);
        }
    }
}
