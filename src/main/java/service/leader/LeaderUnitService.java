package service.leader;

import domain.leader.LeaderUnit;
import domain.leader.LeaderUnitExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class LeaderUnitService extends BaseMapper {

    public boolean idDuplicate(Integer id, int userId, int unitId){

        LeaderUnitExample example = new LeaderUnitExample();
        LeaderUnitExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId).andUnitIdEqualTo(unitId);
        if(id!=null){
            criteria.andIdNotEqualTo(id);
        }

        return leaderUnitMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public int insertSelective(LeaderUnit record){

        record.setSortOrder(getNextSortOrder("leader_unit", "user_id="+record.getUserId()));
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
/*
    @Cacheable(value="LeaderUnit:ALL")
    public Map<Integer, LeaderUnit> findAll() {

        LeaderUnitExample example = new LeaderUnitExample();
        example.setOrderByClause("sort_order desc");
        List<LeaderUnit> cadreLeaderUnites = leaderUnitMapper.selectByExample(example);
        Map<Integer, LeaderUnit> map = new LinkedHashMap<>();
        for (LeaderUnit cadreLeaderUnit : cadreLeaderUnites) {
            map.put(cadreLeaderUnit.getId(), cadreLeaderUnit);
        }

        return map;
    }*/

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 升序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "LeaderUnit:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        LeaderUnit entity = leaderUnitMapper.selectByPrimaryKey(id);
        changeOrder("leader_unit", "user_id="+entity.getUserId(), ORDER_BY_ASC, id, addNum);
    }
}
