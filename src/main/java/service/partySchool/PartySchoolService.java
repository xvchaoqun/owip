package service.partySchool;

import domain.partySchool.PartySchool;
import domain.partySchool.PartySchoolExample;
import org.apache.ibatis.session.RowBounds;
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
public class PartySchoolService extends BaseMapper {

    @Transactional
    @CacheEvict(value="PartySchool:ALL", allEntries = true)
    public void insertSelective(PartySchool record){

        record.setSortOrder(getNextSortOrder("party_school", "status="+ record.getStatus()));
        partySchoolMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PartySchool:ALL", allEntries = true)
    public void del(Integer id){

        partySchoolMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PartySchool:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartySchoolExample example = new PartySchoolExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partySchoolMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PartySchool:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(PartySchool record){

        return partySchoolMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PartySchool:ALL")
    public Map<Integer, PartySchool> findAll() {

        PartySchoolExample example = new PartySchoolExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<PartySchool> partySchooles = partySchoolMapper.selectByExample(example);
        Map<Integer, PartySchool> map = new LinkedHashMap<>();
        for (PartySchool partySchool : partySchooles) {
            map.put(partySchool.getId(), partySchool);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "PartySchool:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        PartySchool entity = partySchoolMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        PartySchoolExample example = new PartySchoolExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PartySchool> overEntities = partySchoolMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            PartySchool targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("party_school", "status="+entity.getStatus(), baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("party_school", "status="+entity.getStatus(), baseSortOrder, targetEntity.getSortOrder());

            PartySchool record = new PartySchool();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partySchoolMapper.updateByPrimaryKeySelective(record);
        }
    }
}
