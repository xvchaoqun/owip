package service;

import domain.PartyMember;
import domain.PartyMemberExample;
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
public class PartyMemberService extends BaseMapper {

    // 查询用户是否是现任分党委班子的管理员
    public boolean isAdmin(Integer userId, Integer partyId){
        if(userId==null || partyId == null) return false;
        return commonMapper.isPartyAdmin(userId, partyId)>0;
    }

    public boolean idDuplicate(Integer id, int groupId, int userId){

        PartyMemberExample example = new PartyMemberExample();
        PartyMemberExample.Criteria criteria = example.createCriteria()
                .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="PartyMember:ALL", allEntries = true)
    public int insertSelective(PartyMember record){

        partyMemberMapper.insertSelective(record);

        Integer id = record.getId();
        PartyMember _record = new PartyMember();
        _record.setId(id);
        _record.setSortOrder(id);
        return partyMemberMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="PartyMember:ALL", allEntries = true)
    public void del(Integer id){

        partyMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PartyMember:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyMemberExample example = new PartyMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyMemberMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PartyMember:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(PartyMember record){
        return partyMemberMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PartyMember:ALL")
    public Map<Integer, PartyMember> findAll() {

        PartyMemberExample example = new PartyMemberExample();
        example.setOrderByClause("sort_order desc");
        List<PartyMember> partyMemberes = partyMemberMapper.selectByExample(example);
        Map<Integer, PartyMember> map = new LinkedHashMap<>();
        for (PartyMember partyMember : partyMemberes) {
            map.put(partyMember.getId(), partyMember);
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
    @CacheEvict(value = "PartyMember:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        PartyMember entity = partyMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        PartyMemberExample example = new PartyMemberExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PartyMember> overEntities = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            PartyMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("ow_party_member", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_party_member", baseSortOrder, targetEntity.getSortOrder());

            PartyMember record = new PartyMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            partyMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
