package service.unit;

import domain.unit.Leader;
import domain.unit.LeaderExample;
import domain.unit.LeaderUnit;
import domain.unit.LeaderUnitExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaderService extends BaseMapper {

    // 根据校领导类别查询校领导
    public List<Leader> findLeaderByType(int type){

        LeaderExample example = new LeaderExample();
        example.createCriteria().andTypeIdEqualTo(type);
        example.setOrderByClause("sort_order desc");
        return leaderMapper.selectByExample(example);
    }
    // 根据校领导id和关联单位类别获取所有关联单位IDs
    public List<LeaderUnit> findLeaderUnitByType(int leaderId, int type){

        LeaderUnitExample example = new LeaderUnitExample();
        example.createCriteria().andLeaderIdEqualTo(leaderId).andTypeIdEqualTo(type);
        return leaderUnitMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int cadreId, int typeId) {

        LeaderExample example = new LeaderExample();
        LeaderExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId).andTypeIdEqualTo(typeId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return leaderMapper.countByExample(example) > 0;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public int insertSelective(Leader record) {

        leaderMapper.insertSelective(record);

        Integer id = record.getId();
        Leader _record = new Leader();
        _record.setId(id);
        _record.setSortOrder(id);
        return leaderMapper.updateByPrimaryKeySelective(_record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public void del(Integer id) {

        leaderMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        LeaderExample example = new LeaderExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        leaderMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public int updateByPrimaryKeySelective(Leader record) {
        return leaderMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "Leader:ALL")
    public Map<Integer, Leader> findAll() {

        LeaderExample example = new LeaderExample();
        example.setOrderByClause("sort_order desc");
        List<Leader> leaderes = leaderMapper.selectByExample(example);
        Map<Integer, Leader> map = new LinkedHashMap<>();
        for (Leader leader : leaderes) {
            map.put(leader.getId(), leader);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Leader:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        Leader entity = leaderMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        LeaderExample example = new LeaderExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Leader> overEntities = leaderMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            Leader targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("unit_leader", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("unit_leader", baseSortOrder, targetEntity.getSortOrder());

            Leader record = new Leader();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            leaderMapper.updateByPrimaryKeySelective(record);
        }
    }
}
