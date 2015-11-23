package service;

import domain.PartyMemberGroup;
import domain.PartyMemberGroupExample;
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
public class PartyMemberGroupService extends BaseMapper {

    private void resetPresentParty(int partyId) {

        // 去掉以前设置的现任班子状态
        PartyMemberGroup _record = new PartyMemberGroup();
        _record.setIsPresent(false);
        PartyMemberGroupExample _example = new PartyMemberGroupExample();
        _example.createCriteria().andPartyIdEqualTo(partyId);
        partyMemberGroupMapper.updateByExampleSelective(_record, _example);
    }

    @Transactional
    @CacheEvict(value = "PartyMemberGroup:ALL", allEntries = true)
    public int insertSelective(PartyMemberGroup record) {

        if (record.getIsPresent()) {
            resetPresentParty(record.getPartyId());
        }
        partyMemberGroupMapper.insertSelective(record);

        Integer id = record.getId();
        PartyMemberGroup _record = new PartyMemberGroup();
        _record.setId(id);
        _record.setSortOrder(id);
        return partyMemberGroupMapper.updateByPrimaryKeySelective(_record);
    }

    @Transactional
    @CacheEvict(value = "PartyMemberGroup:ALL", allEntries = true)
    public void del(Integer id) {

        partyMemberGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value = "PartyMemberGroup:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyMemberGroupMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value = "PartyMemberGroup:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(PartyMemberGroup record) {

        if (record.getIsPresent()) {
            resetPresentParty(record.getPartyId());
        }
        return partyMemberGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "PartyMemberGroup:ALL")
    public Map<Integer, PartyMemberGroup> findAll() {

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        example.setOrderByClause("sort_order desc");
        List<PartyMemberGroup> partyMemberGroupes = partyMemberGroupMapper.selectByExample(example);
        Map<Integer, PartyMemberGroup> map = new LinkedHashMap<>();
        for (PartyMemberGroup partyMemberGroup : partyMemberGroupes) {
            map.put(partyMemberGroup.getId(), partyMemberGroup);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "PartyMemberGroup:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        PartyMemberGroup entity = partyMemberGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        PartyMemberGroupExample example = new PartyMemberGroupExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PartyMemberGroup> overEntities = partyMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PartyMemberGroup targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("ow_party_member_group", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_party_member_group", baseSortOrder, targetEntity.getSortOrder());

            PartyMemberGroup record = new PartyMemberGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMemberGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
