package service.pcs;

import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsPrCandidateExample;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrCandidateViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.List;

@Service
public class PcsPrCandidateService extends BaseMapper {

    public static final String TABLE_NAME = "pcs_pr_candidate";

    // 获得分党委某个类型下的被推荐人
    public List<PcsPrCandidateView> find(int configId, byte stage, byte type, int partyId){

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        example.createCriteria().andConfigIdEqualTo(configId).andStageEqualTo(stage)
                .andTypeEqualTo(type).andPartyIdEqualTo(partyId);

        example.setOrderByClause("sort_order asc");

        return pcsPrCandidateViewMapper.selectByExample(example);
    }

    // 清空一个党支部推荐的党委或纪委委员
    @Transactional
    public void clear(int recommendId){

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        example.createCriteria().andRecommendIdEqualTo(recommendId);
        pcsPrCandidateMapper.deleteByExample(example);
    }

    @Transactional
    public void insertSelective(PcsPrCandidate record){

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "recommend_id=" + record.getRecommendId()
                + " and type=" + record.getType()));
        pcsPrCandidateMapper.insertSelective(record);
    }

    // 升序排列
    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        PcsPrCandidate entity = pcsPrCandidateMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        int recommendId = entity.getRecommendId();
        byte type = entity.getType();

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        if (addNum < 0) { // 升序

            example.createCriteria().andRecommendIdEqualTo(recommendId).andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andRecommendIdEqualTo(recommendId).andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PcsPrCandidate> overEntities = pcsPrCandidateMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PcsPrCandidate targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum < 0)
                commonMapper.downOrder(TABLE_NAME,  "recommend_id=" + recommendId
                        + " and type=" + type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME,  "recommend_id=" + recommendId
                        + " and type=" + type, baseSortOrder, targetEntity.getSortOrder());

            PcsPrCandidate record = new PcsPrCandidate();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            pcsPrCandidateMapper.updateByPrimaryKeySelective(record);
        }
    }
}
