package service;

import domain.Party;
import domain.PartyExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartyService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        PartyExample example = new PartyExample();
        PartyExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="Party:ALL", allEntries = true)
    public int insertSelective(Party record){

        Assert.isTrue(!idDuplicate(null, record.getCode()));
        partyMapper.insertSelective(record);
        Integer id = record.getId();
        Party _record = new Party();
        _record.setId(id);
        _record.setSortOrder(id);
        return partyMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="Party:ALL", allEntries = true)
    public void del(Integer id){

        partyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Party:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyExample example = new PartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="Party:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Party record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        return partyMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="Party:ALL")
    public Map<Integer, Party> findAll() {

        PartyExample example = new PartyExample();
        example.setOrderByClause("sort_order desc");
        List<Party> partyes = partyMapper.selectByExample(example);
        Map<Integer, Party> map = new LinkedHashMap<>();
        for (Party party : partyes) {
            map.put(party.getId(), party);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Party:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        Party entity = partyMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        PartyExample example = new PartyExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Party> overEntities = partyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Party targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("ow_party", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_party", baseSortOrder, targetEntity.getSortOrder());

            Party record = new Party();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMapper.updateByPrimaryKeySelective(record);
        }
    }
}
