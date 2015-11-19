package service;

import domain.UnitCadreTransferItem;
import domain.UnitCadreTransferItemExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitCadreTransferItemService extends BaseMapper {

    public boolean idDuplicate(Integer id, int transferId, int dispatchCadreId){

        UnitCadreTransferItemExample example = new UnitCadreTransferItemExample();
        UnitCadreTransferItemExample.Criteria criteria = example.createCriteria()
                .andTransferIdEqualTo(transferId).andDispatchCadreIdEqualTo(dispatchCadreId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitCadreTransferItemMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransferItem:ALL", allEntries = true)
    public int insertSelective(UnitCadreTransferItem record){

        return unitCadreTransferItemMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="UnitCadreTransferItem:ALL", allEntries = true)
    public void del(Integer id){

        unitCadreTransferItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransferItem:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitCadreTransferItemExample example = new UnitCadreTransferItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitCadreTransferItemMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransferItem:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(UnitCadreTransferItem record){
        return unitCadreTransferItemMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="UnitCadreTransferItem:ALL")
    public Map<Integer, UnitCadreTransferItem> findAll() {

        UnitCadreTransferItemExample example = new UnitCadreTransferItemExample();
        example.setOrderByClause("id desc");
        List<UnitCadreTransferItem> unitCadreTransferItemes = unitCadreTransferItemMapper.selectByExample(example);
        Map<Integer, UnitCadreTransferItem> map = new LinkedHashMap<>();
        for (UnitCadreTransferItem unitCadreTransferItem : unitCadreTransferItemes) {
            map.put(unitCadreTransferItem.getId(), unitCadreTransferItem);
        }

        return map;
    }
}
