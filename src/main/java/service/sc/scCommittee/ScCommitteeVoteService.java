package service.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeTopicCadre;
import domain.sc.scCommittee.ScCommitteeTopicCadreExample;
import domain.sc.scCommittee.ScCommitteeVote;
import domain.sc.scCommittee.ScCommitteeVoteExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sc.ScBaseMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ScCommitteeVoteService extends ScBaseMapper {

    @Autowired
    private ScCommitteeTopicService scCommitteeTopicService;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScCommitteeVoteExample example = new ScCommitteeVoteExample();
        ScCommitteeVoteExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scCommitteeVoteMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScCommitteeVote record,
                                String originalPost, Date originalPostTime){

        record.setSortOrder(getNextSortOrder("sc_committee_vote", "topic_id=" + record.getTopicId()));
        scCommitteeVoteMapper.insertSelective(record);
        int topicId = record.getTopicId();
        int cadreId = record.getCadreId();

        updateTopicCadre(topicId, cadreId, originalPost, originalPostTime);
    }

    // 更新议题涉及的干部基本信息（原任职务信息等）
    private void updateTopicCadre(int topicId, int cadreId, String originalPost, Date originalPostTime){

        if(StringUtils.isNotBlank(originalPost) || originalPostTime != null){

            ScCommitteeTopicCadre _record = new ScCommitteeTopicCadre();
            _record.setCadreId(cadreId);
            _record.setTopicId(topicId);
            _record.setOriginalPost(originalPost);
            _record.setOriginalPostTime(originalPostTime);

            ScCommitteeTopicCadre topicCadre = scCommitteeTopicService.getTopicCadre(topicId, cadreId);
            if(topicCadre==null) {
                scCommitteeTopicCadreMapper.insertSelective(_record);
            }else {
                _record.setId(topicCadre.getId());
                scCommitteeTopicCadreMapper.updateByPrimaryKeySelective(_record);
            }
        }else{
            ScCommitteeTopicCadreExample example = new ScCommitteeTopicCadreExample();
            example.createCriteria().andTopicIdEqualTo(topicId).andCadreIdEqualTo(cadreId);
            scCommitteeTopicCadreMapper.deleteByExample(example);
        }
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
    public void updateByPrimaryKeySelective(ScCommitteeVote record, String originalPost, Date originalPostTime){

        scCommitteeVoteMapper.updateByPrimaryKeySelective(record);
        updateTopicCadre(record.getTopicId(), record.getCadreId(), originalPost, originalPostTime);
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

            example.createCriteria().andTopicIdEqualTo(topicId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andTopicIdEqualTo(topicId).andSortOrderLessThan(baseSortOrder);
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
