package service.cadre;

import domain.cadre.CadreLeaderUnit;
import domain.cadre.CadreLeaderUnitExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CadreLeaderUnitService extends BaseMapper {

    public boolean idDuplicate(Integer id, int leaderId, int unitId){

        CadreLeaderUnitExample example = new CadreLeaderUnitExample();
        CadreLeaderUnitExample.Criteria criteria = example.createCriteria()
                .andLeaderIdEqualTo(leaderId).andUnitIdEqualTo(unitId);
        if(id!=null){
            criteria.andIdNotEqualTo(id);
        }

        return cadreLeaderUnitMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="LeaderUnit:ALL", allEntries = true)
    public int insertSelective(CadreLeaderUnit record){

        record.setSortOrder(getNextSortOrder("cadre_leader_unit", "leader_id="+record.getLeaderId()));
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
/*
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
        CadreLeaderUnit entity = cadreLeaderUnitMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer leaderId = entity.getLeaderId();

        CadreLeaderUnitExample example = new CadreLeaderUnitExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andLeaderIdEqualTo(leaderId)
                    .andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andLeaderIdEqualTo(leaderId)
                    .andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreLeaderUnit> overEntities = cadreLeaderUnitMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CadreLeaderUnit targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cadre_leader_unit", "leader_id="+leaderId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_leader_unit", "leader_id="+leaderId, baseSortOrder, targetEntity.getSortOrder());

            CadreLeaderUnit record = new CadreLeaderUnit();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreLeaderUnitMapper.updateByPrimaryKeySelective(record);
        }
    }
}
