package service.cadre;

import domain.cadre.CadreLeaderUnit;
import domain.cadre.CadreLeaderUnitExample;
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
public class CadreLeaderUnitService extends BaseMapper {

    public boolean idDuplicate(int leaderId, int unitId, int typeId){

        CadreLeaderUnitExample example = new CadreLeaderUnitExample();
        example.createCriteria().andLeaderIdEqualTo(leaderId).andUnitIdEqualTo(unitId).andTypeIdEqualTo(typeId);

        return cadreLeaderUnitMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public int insertSelective(CadreLeaderUnit record){

        return cadreLeaderUnitMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public void del(Integer id){

        cadreLeaderUnitMapper.deleteByPrimaryKey(id);
    }
    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreLeaderUnitExample example = new CadreLeaderUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreLeaderUnitMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CadreLeaderUnit record){
        return cadreLeaderUnitMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="LeaderUnit:ALL")
    public Map<Integer, CadreLeaderUnit> findAll() {

        CadreLeaderUnitExample example = new CadreLeaderUnitExample();
        example.setOrderByClause("sort_order desc");
        List<CadreLeaderUnit> cadreLeaderUnites = cadreLeaderUnitMapper.selectByExample(example);
        Map<Integer, CadreLeaderUnit> map = new LinkedHashMap<>();
        for (CadreLeaderUnit cadreLeaderUnit : cadreLeaderUnites) {
            map.put(cadreLeaderUnit.getId(), cadreLeaderUnit);
        }

        return map;
    }
}
