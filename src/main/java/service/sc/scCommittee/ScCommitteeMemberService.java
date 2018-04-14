package service.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeMember;
import domain.sc.scCommittee.ScCommitteeMemberExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScCommitteeMemberService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScCommitteeMemberExample example = new ScCommitteeMemberExample();
        ScCommitteeMemberExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scCommitteeMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScCommitteeMember record){

        record.setSortOrder(getNextSortOrder("sc_committee_member", "1=1"));
        scCommitteeMemberMapper.insertSelective(record);
    }


    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScCommitteeMemberExample example = new ScCommitteeMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scCommitteeMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScCommitteeMember record){
        return scCommitteeMemberMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ScCommitteeMember entity = scCommitteeMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ScCommitteeMemberExample example = new ScCommitteeMemberExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ScCommitteeMember> overEntities = scCommitteeMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ScCommitteeMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("sc_committee_member", "1=1", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("sc_committee_member", "1=1", baseSortOrder, targetEntity.getSortOrder());

            ScCommitteeMember record = new ScCommitteeMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            scCommitteeMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
