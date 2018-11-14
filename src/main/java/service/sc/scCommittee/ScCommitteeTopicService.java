package service.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeTopic;
import domain.sc.scCommittee.ScCommitteeTopicCadre;
import domain.sc.scCommittee.ScCommitteeTopicCadreExample;
import domain.sc.scCommittee.ScCommitteeTopicExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScCommitteeTopicService extends BaseMapper {

    // 获得议题涉及的某个干部
    public ScCommitteeTopicCadre getTopicCadre(int topicId, int cadreId){

        ScCommitteeTopicCadreExample example = new ScCommitteeTopicCadreExample();
        example.createCriteria().andTopicIdEqualTo(topicId).andCadreIdEqualTo(cadreId);
        List<ScCommitteeTopicCadre> scCommitteeTopicCadres = scCommitteeTopicCadreMapper.selectByExample(example);

        return scCommitteeTopicCadres.size()>0?scCommitteeTopicCadres.get(0):null;
    }

    public boolean idDuplicate(Integer id, int committeeId, int seq){

        ScCommitteeTopicExample example = new ScCommitteeTopicExample();
        ScCommitteeTopicExample.Criteria criteria = example.createCriteria().andCommitteeIdEqualTo(committeeId)
                .andSeqEqualTo(seq);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scCommitteeTopicMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScCommitteeTopic record){

        record.setIsDeleted(false);
        scCommitteeTopicMapper.insertSelective(record);
    }


    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScCommitteeTopicExample example = new ScCommitteeTopicExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScCommitteeTopic record = new ScCommitteeTopic();
        record.setIsDeleted(true);
        scCommitteeTopicMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScCommitteeTopic record){
        return scCommitteeTopicMapper.updateByPrimaryKeySelective(record);
    }

    // 自动生成议题序号
    public Integer genSeq(Integer committeeId) {

        int num ;
        ScCommitteeTopicExample example = new ScCommitteeTopicExample();
        example.createCriteria().andCommitteeIdEqualTo(committeeId);
        example.setOrderByClause("seq desc");
        List<ScCommitteeTopic> records = scCommitteeTopicMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(records.size()>0){
            int seq = records.get(0).getSeq();
            num = seq + 1;
        }else{
            num = 1;
        }
        return num;
    }
}
