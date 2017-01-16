package service.cadre;

import domain.cadre.CadreLeader;
import domain.cadre.CadreLeaderExample;
import domain.cadre.CadreLeaderUnit;
import domain.cadre.CadreLeaderUnitExample;
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
public class CadreLeaderService extends BaseMapper {

    // 根据校领导类别查询校领导
    public List<CadreLeader> findLeaderByType(int type){

        CadreLeaderExample example = new CadreLeaderExample();
        example.createCriteria().andTypeIdEqualTo(type);
        example.setOrderByClause("sort_order desc");
        return cadreLeaderMapper.selectByExample(example);
    }
    // 根据校领导id和关联单位类别获取所有关联单位IDs
    public List<CadreLeaderUnit> findLeaderUnitByType(int leaderId, int type){

        CadreLeaderUnitExample example = new CadreLeaderUnitExample();
        example.createCriteria().andLeaderIdEqualTo(leaderId).andTypeIdEqualTo(type);
        return cadreLeaderUnitMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int cadreId, int typeId) {

        CadreLeaderExample example = new CadreLeaderExample();
        CadreLeaderExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId).andTypeIdEqualTo(typeId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreLeaderMapper.countByExample(example) > 0;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public int insertSelective(CadreLeader record) {

        record.setSortOrder(getNextSortOrder("cadre_leader", "1=1"));
        return cadreLeaderMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public void del(Integer id) {

        cadreLeaderMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreLeaderExample example = new CadreLeaderExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreLeaderMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Leader:ALL", allEntries = true)
    })
    public int updateByPrimaryKeySelective(CadreLeader record) {
        return cadreLeaderMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "Leader:ALL")
    public Map<Integer, CadreLeader> findAll() {

        CadreLeaderExample example = new CadreLeaderExample();
        example.setOrderByClause("sort_order desc");
        List<CadreLeader> cadreLeaderes = cadreLeaderMapper.selectByExample(example);
        Map<Integer, CadreLeader> map = new LinkedHashMap<>();
        for (CadreLeader cadreLeader : cadreLeaderes) {
            map.put(cadreLeader.getId(), cadreLeader);
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

        CadreLeader entity = cadreLeaderMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreLeaderExample example = new CadreLeaderExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreLeader> overEntities = cadreLeaderMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CadreLeader targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("cadre_leader", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_leader", null, baseSortOrder, targetEntity.getSortOrder());

            CadreLeader record = new CadreLeader();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreLeaderMapper.updateByPrimaryKeySelective(record);
        }
    }
}
