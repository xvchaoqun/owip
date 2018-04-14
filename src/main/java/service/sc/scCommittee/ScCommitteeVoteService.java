package service.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeVote;
import domain.sc.scCommittee.ScCommitteeVoteExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScCommitteeVoteService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScCommitteeVoteExample example = new ScCommitteeVoteExample();
        ScCommitteeVoteExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scCommitteeVoteMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScCommitteeVote record){

        record.setSortOrder(getNextSortOrder("sc_committee_vote", "topic_id=" + record.getTopicId()));
        scCommitteeVoteMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scCommitteeVoteMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScCommitteeVoteExample example = new ScCommitteeVoteExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scCommitteeVoteMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScCommitteeVote record){
        return scCommitteeVoteMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ScCommitteeVote entity = scCommitteeVoteMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer topicId = entity.getTopicId();

        ScCommitteeVoteExample example = new ScCommitteeVoteExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ScCommitteeVote> overEntities = scCommitteeVoteMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ScCommitteeVote targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("sc_committee_vote", "topic_id=" + topicId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("sc_committee_vote", "topic_id=" + topicId, baseSortOrder, targetEntity.getSortOrder());

            ScCommitteeVote record = new ScCommitteeVote();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            scCommitteeVoteMapper.updateByPrimaryKeySelective(record);
        }
    }
}
