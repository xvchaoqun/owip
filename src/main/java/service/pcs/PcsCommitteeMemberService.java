package service.pcs;

import domain.pcs.PcsCommitteeMember;
import domain.pcs.PcsCommitteeMemberExample;
import domain.pcs.PcsCommitteeMemberView;
import domain.pcs.PcsCommitteeMemberViewExample;
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
public class PcsCommitteeMemberService extends PcsBaseMapper {

    public boolean idDuplicate(Integer id, Boolean type, Integer userId, Boolean isQuit){

        if(type==null || userId==null || isQuit==null) return false;
        if(isQuit) return false;

        PcsCommitteeMemberExample example = new PcsCommitteeMemberExample();
        PcsCommitteeMemberExample.Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type).andIsQuitEqualTo(isQuit)
                .andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pcsCommitteeMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="PcsCommitteeMembers", allEntries = true)
    public void insertSelective(PcsCommitteeMember record){

        Assert.isTrue(!idDuplicate(null, record.getType(), record.getUserId(), record.getIsQuit()), "duplicate");
        record.setSortOrder(getNextSortOrder("pcs_committee_member", "type="+record.getType()
                + " and is_quit="+ record.getIsQuit()));
        pcsCommitteeMemberMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PcsCommitteeMembers", allEntries = true)
    public void del(Integer id){

        pcsCommitteeMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PcsCommitteeMembers", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsCommitteeMemberExample example = new PcsCommitteeMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsCommitteeMemberMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PcsCommitteeMembers", allEntries = true)
    public int updateByPrimaryKeySelective(PcsCommitteeMember record){
        if(record.getType()!=null && record.getUserId()!=null && record.getIsQuit()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getType(), record.getUserId(), record.getIsQuit()), "duplicate");
        return pcsCommitteeMemberMapper.updateByPrimaryKeySelective(record);
    }

    // 读取现任委员
    @Cacheable(value="PcsCommitteeMembers")
    public Map<Integer, PcsCommitteeMemberView> committeeMemberMap() {

        PcsCommitteeMemberViewExample example = new PcsCommitteeMemberViewExample();
        example.createCriteria()
                .andIsCommmitteeMemberEqualTo(true)
                .andIsQuitEqualTo(false);
        example.setOrderByClause("sort_order desc");
        List<PcsCommitteeMemberView> pcsCommitteeMemberes = pcsCommitteeMemberViewMapper.selectByExample(example);
        Map<Integer, PcsCommitteeMemberView> map = new LinkedHashMap<>();
        for (PcsCommitteeMemberView pcsCommitteeMemberView : pcsCommitteeMemberes) {
            map.put(pcsCommitteeMemberView.getId(), pcsCommitteeMemberView);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "PcsCommitteeMembers", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        PcsCommitteeMember entity = pcsCommitteeMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        boolean type = entity.getType();
        Boolean isQuit = entity.getIsQuit();

        PcsCommitteeMemberExample example = new PcsCommitteeMemberExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andTypeEqualTo(type)
                    .andIsQuitEqualTo(isQuit).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andTypeEqualTo(type)
                    .andIsQuitEqualTo(isQuit).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PcsCommitteeMember> overEntities = pcsCommitteeMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            PcsCommitteeMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("pcs_committee_member", "type="+type + " and is_quit="+ isQuit,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("pcs_committee_member", "type="+type + " and is_quit="+ isQuit,
                        baseSortOrder, targetEntity.getSortOrder());

            PcsCommitteeMember record = new PcsCommitteeMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            pcsCommitteeMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
