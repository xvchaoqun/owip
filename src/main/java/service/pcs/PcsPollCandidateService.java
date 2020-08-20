package service.pcs;

import domain.pcs.PcsPollCandidate;
import domain.pcs.PcsPollCandidateExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class PcsPollCandidateService extends PcsBaseMapper {

    @Transactional
    public void insertSelective(PcsPollCandidate record){
        record.setSortOrder(getNextSortOrder("pcs_poll_candidate", null));
        pcsPollCandidateMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsPollCandidateExample example = new PcsPollCandidateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollCandidateMapper.deleteByExample(example);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        PcsPollCandidate candidate = pcsPollCandidateMapper.selectByPrimaryKey(id);
        changeOrder("pcs_poll_candidate", "poll_id=" + candidate.getPollId(), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public int bacthImport(List<PcsPollCandidate> records, Integer pollId, Byte type) {
        int addCount = 0;

        PcsPollCandidateExample example = new PcsPollCandidateExample();
        example.createCriteria().andPollIdEqualTo(pollId).andTypeEqualTo(type);
        pcsPollCandidateMapper.deleteByExample(example);
        for (PcsPollCandidate record : records) {

            insertSelective(record);
            addCount++;
        }

        return addCount;
    }

    //List<PcsPollCandidate> -- 二下/三下推荐人
    public List<PcsPollCandidate> findAll(Integer pollId, Byte type){

        PcsPollCandidateExample example = new PcsPollCandidateExample();
        example.createCriteria().andPollIdEqualTo(pollId).andTypeEqualTo(type);

        example.setOrderByClause("sort_order desc");
        List<PcsPollCandidate> pcsPollCandidates = pcsPollCandidateMapper.selectByExample(example);

        return pcsPollCandidates;
    }
}
