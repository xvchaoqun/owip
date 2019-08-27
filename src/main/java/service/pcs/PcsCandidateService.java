package service.pcs;

import domain.pcs.PcsCandidate;
import domain.pcs.PcsCandidateExample;
import domain.pcs.PcsCandidateView;
import domain.pcs.PcsCandidateViewExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class PcsCandidateService extends PcsBaseMapper {

    public static final String TABLE_NAME = "pcs_candidate";

    // 读取党支部下的党委委员、纪委委员
    public List<PcsCandidateView> find(int partyId, Integer branchId, int configId, byte stage, byte type){

        PcsCandidateViewExample example = new PcsCandidateViewExample();
        PcsCandidateViewExample.Criteria criteria =
                example.createCriteria().andPartyIdEqualTo(partyId)
                        .andConfigIdEqualTo(configId).andStageEqualTo(stage)
                        .andTypeEqualTo(type);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);
        example.setOrderByClause("sort_order asc");
        return pcsCandidateViewMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int recommendId, int userId, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        PcsCandidateExample.Criteria criteria = example.createCriteria()
                .andRecommendIdEqualTo(recommendId).andUserIdEqualTo(userId).andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pcsCandidateMapper.countByExample(example) > 0;
    }

    // 清空一个党支部推荐的党委或纪委委员
    @Transactional
    public void clear(int recommendId, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        example.createCriteria().andRecommendIdEqualTo(recommendId).andTypeEqualTo(type);
        pcsCandidateMapper.deleteByExample(example);
    }

    @Transactional
    public void insertSelective(PcsCandidate record){

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "recommend_id=" + record.getRecommendId()
                + " and type=" + record.getType()));
        pcsCandidateMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsCandidateExample example = new PcsCandidateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsCandidateMapper.deleteByExample(example);
    }

    // 升序排列
    @Transactional
    public void changeOrder(int id, int addNum) {

        PcsCandidate entity = pcsCandidateMapper.selectByPrimaryKey(id);
        changeOrder(TABLE_NAME, "recommend_id=" + entity.getRecommendId()
                + " and type=" + entity.getType(), ORDER_BY_ASC, id, addNum);
    }
}
