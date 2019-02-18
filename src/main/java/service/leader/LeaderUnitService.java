package service.leader;

import domain.leader.LeaderUnit;
import domain.leader.LeaderUnitExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

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

        if (addNum == 0) return;
        byte orderBy = ORDER_BY_ASC;
        LeaderUnit entity = leaderUnitMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer userId = entity.getUserId();

        LeaderUnitExample example = new LeaderUnitExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andUserIdEqualTo(userId)
                    .andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andUserIdEqualTo(userId)
                    .andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<LeaderUnit> overEntities = leaderUnitMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            LeaderUnit targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("leader_unit", "user_id="+userId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("leader_unit", "user_id="+userId, baseSortOrder, targetEntity.getSortOrder());

            LeaderUnit record = new LeaderUnit();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            leaderUnitMapper.updateByPrimaryKeySelective(record);
        }
    }
}
