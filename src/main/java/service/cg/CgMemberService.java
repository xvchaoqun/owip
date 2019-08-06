package service.cg;

import domain.cg.CgMember;
import domain.cg.CgMemberExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;

@Service
public class CgMemberService extends CgBaseMapper {

    public boolean idDuplicate(Integer id, int teamId, int userId, boolean isCurrent){

        if(!isCurrent) return false;

        CgMemberExample example = new CgMemberExample();
        CgMemberExample.Criteria criteria = example.createCriteria().
                andTeamIdEqualTo(teamId).andUserIdEqualTo(userId)
                .andIsCurrentEqualTo(isCurrent);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cgMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CgMember record){

        Assert.isTrue(!idDuplicate(null, record.getTeamId(), record.getUserId(), record.getIsCurrent()), "duplicate");
        record.setSortOrder(getNextSortOrder("cg_member", null));
        cgMemberMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cgMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CgMemberExample example = new CgMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cgMemberMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CgMember record){

        Assert.isTrue(!idDuplicate(record.getId(), record.getTeamId(), record.getUserId(), record.getIsCurrent()), "duplicate");
        cgMemberMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("cg_member", null, ORDER_BY_ASC, id, addNum);
    }
}
