package service;

import domain.UnitCadreTransfer;
import domain.UnitCadreTransferExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitCadreTransferService extends BaseMapper {

    public boolean idDuplicate(Integer id, int groupId, int cadreId){

        UnitCadreTransferExample example = new UnitCadreTransferExample();
        UnitCadreTransferExample.Criteria criteria = example.createCriteria()
                .andGroupIdEqualTo(groupId).andCadreIdEqualTo(cadreId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitCadreTransferMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransfer:ALL", allEntries = true)
    public int insertSelective(UnitCadreTransfer record){

        unitCadreTransferMapper.insertSelective(record);

        Integer id = record.getId();
        UnitCadreTransfer _record = new UnitCadreTransfer();
        _record.setId(id);
        _record.setSortOrder(id);
        return unitCadreTransferMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="UnitCadreTransfer:ALL", allEntries = true)
    public void del(Integer id){

        unitCadreTransferMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransfer:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitCadreTransferExample example = new UnitCadreTransferExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitCadreTransferMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransfer:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(UnitCadreTransfer record){
        return unitCadreTransferMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="UnitCadreTransfer:ALL")
    public Map<Integer, UnitCadreTransfer> findAll() {

        UnitCadreTransferExample example = new UnitCadreTransferExample();
        example.setOrderByClause("sort_order desc");
        List<UnitCadreTransfer> unitCadreTransferes = unitCadreTransferMapper.selectByExample(example);
        Map<Integer, UnitCadreTransfer> map = new LinkedHashMap<>();
        for (UnitCadreTransfer unitCadreTransfer : unitCadreTransferes) {
            map.put(unitCadreTransfer.getId(), unitCadreTransfer);
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
    @CacheEvict(value = "UnitCadreTransfer:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        UnitCadreTransfer entity = unitCadreTransferMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitCadreTransferExample example = new UnitCadreTransferExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitCadreTransfer> overEntities = unitCadreTransferMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            UnitCadreTransfer targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("base_unit_cadre_transfer",baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("base_unit_cadre_transfer",baseSortOrder, targetEntity.getSortOrder());

            UnitCadreTransfer record = new UnitCadreTransfer();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitCadreTransferMapper.updateByPrimaryKeySelective(record);
        }
    }
}
