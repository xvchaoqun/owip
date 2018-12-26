package service.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeOtherVote;
import domain.sc.scCommittee.ScCommitteeOtherVoteExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;

import java.util.Arrays;

@Service
public class ScCommitteeOtherVoteService extends ScBaseMapper {

    @Transactional
    public void insertSelective(ScCommitteeOtherVote record){

        scCommitteeOtherVoteMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scCommitteeOtherVoteMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScCommitteeOtherVoteExample example = new ScCommitteeOtherVoteExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scCommitteeOtherVoteMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScCommitteeOtherVote record){
        return scCommitteeOtherVoteMapper.updateByPrimaryKeySelective(record);
    }
}
