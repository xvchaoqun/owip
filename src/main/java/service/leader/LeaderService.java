package service.leader;

import domain.cadre.Cadre;
import domain.cm.CmMember;
import domain.leader.Leader;
import domain.leader.LeaderExample;
import domain.leader.LeaderUnit;
import domain.leader.LeaderUnitExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.cm.CmMemberMapper;
import service.BaseMapper;
import sys.constants.CmConstants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaderService extends BaseMapper {

    @Autowired
    private CmMemberMapper cmMemberMapper;

    // 根据校领导类别查询校领导
    public List<Leader> findLeaderByType(int type){

        LeaderExample example = new LeaderExample();
        example.createCriteria().andTypeIdEqualTo(type);
        example.setOrderByClause("sort_order desc");
        return leaderMapper.selectByExample(example);
    }
    // 根据校领导userId和关联单位类别获取所有关联单位IDs
    public List<LeaderUnit> findLeaderUnitByType(int userId, int type){

        LeaderUnitExample example = new LeaderUnitExample();
        example.createCriteria().andUserIdEqualTo(userId).andTypeIdEqualTo(type);
        return leaderUnitMapper.selectByExample(example);
    }

    public Leader get(int userId) {

        LeaderExample example = new LeaderExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<Leader> leaders = leaderMapper.selectByExampleWithRowbounds(example,
                new RowBounds(0, 1));

        return leaders.size()>0?leaders.get(0):null;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public void fromLeader(Integer[] cadreIds) {

        if(cadreIds ==null || cadreIds.length==0) return;
        for (Integer cadreId : cadreIds) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
            int userId = cadre.getUserId();
            Leader record = new Leader();
            record.setUserId(userId);
            Leader leader = get(userId);
            if(leader==null) {
                insertSelective(record);
            }
        }
    }
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public void fromCm(Integer[] memberIds) {

        if(memberIds ==null || memberIds.length==0) return;

        for (Integer memberId : memberIds) {

            CmMember cmMember = cmMemberMapper.selectByPrimaryKey(memberId);
            if(cmMember==null || cmMember.getType()!= CmConstants.CM_MEMBER_TYPE_CW
                    || cmMember.getIsQuit()) continue;

            int userId = cmMember.getUserId();
            Leader record = new Leader();
            record.setUserId(userId);
            Leader leader = get(userId);
            if(leader==null) {
                insertSelective(record);
            }
        }
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public int insertSelective(Leader record) {

        record.setSortOrder(getNextSortOrder("leader", null));
        return leaderMapper.insertSelective(record);
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
            @CacheEvict(value = "Leader:ALL", allEntries = true),
            @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {

            Leader leader = leaderMapper.selectByPrimaryKey(id);
            int userId = leader.getUserId();

            LeaderUnitExample example = new LeaderUnitExample();
            example.createCriteria().andUserIdEqualTo(userId);
            leaderUnitMapper.deleteByExample(example);

            leaderMapper.deleteByPrimaryKey(id);
        }
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
                commonMapper.downOrder("leader", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("leader", null, baseSortOrder, targetEntity.getSortOrder());

            Leader record = new Leader();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            leaderMapper.updateByPrimaryKeySelective(record);
        }
    }
}
