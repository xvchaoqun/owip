package service.unit;

import domain.LeaderUnit;
import domain.LeaderUnitExample;
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
public class LeaderUnitService extends BaseMapper {

    public boolean idDuplicate(int leaderId, int unitId, int typeId){

        LeaderUnitExample example = new LeaderUnitExample();
        example.createCriteria().andLeaderIdEqualTo(leaderId).andUnitIdEqualTo(unitId).andTypeIdEqualTo(typeId);

        return leaderUnitMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public int insertSelective(LeaderUnit record){

        return leaderUnitMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public void del(Integer id){

        leaderUnitMapper.deleteByPrimaryKey(id);
    }
    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        LeaderUnitExample example = new LeaderUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        leaderUnitMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(LeaderUnit record){
        return leaderUnitMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="LeaderUnit:ALL")
    public Map<Integer, LeaderUnit> findAll() {

        LeaderUnitExample example = new LeaderUnitExample();
        example.setOrderByClause("sort_order desc");
        List<LeaderUnit> leaderUnites = leaderUnitMapper.selectByExample(example);
        Map<Integer, LeaderUnit> map = new LinkedHashMap<>();
        for (LeaderUnit leaderUnit : leaderUnites) {
            map.put(leaderUnit.getId(), leaderUnit);
        }

        return map;
    }
}
