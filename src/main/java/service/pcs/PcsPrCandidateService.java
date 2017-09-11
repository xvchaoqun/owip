package service.pcs;

import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsPrCandidateExample;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrCandidateViewExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PcsPrCandidateService extends BaseMapper {

    public static final String TABLE_NAME = "pcs_pr_candidate";

    // 获得某个分党委入选名单列表
    public Map<Integer, PcsPrCandidateView> findSelectedMap(int configId, byte stage, int partyId){

        Map<Integer, PcsPrCandidateView> resultMap = new LinkedHashMap<>();

        PcsPrCandidateViewExample example = createExample(configId, stage, partyId, null);
        List<PcsPrCandidateView> pcsPrCandidateViews = pcsPrCandidateViewMapper.selectByExample(example);
        for (PcsPrCandidateView ca : pcsPrCandidateViews) {

            resultMap.put(ca.getUserId(), ca);
        }

        return resultMap;
    }

    /**
     *
     * 先按“专业技术人员和干部”、“学生代表”、“离退休代表”；
     其中“学生代表”和“离退休代表”只按票数排序；
     其中“专业技术人员和干部”排序规则为：

     校领导在最前，顺序与校领导干部库顺序一致；
     其他所有的按票数来排。
     */
    // 用于查询入选名单
    public PcsPrCandidateViewExample createExample(int configId, byte stage, Integer partyId, Integer userId){

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        PcsPrCandidateViewExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).
                andStageEqualTo(stage);
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        example.setOrderByClause("type asc, leader_sort_order desc, vote desc, sort_order asc");
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        return example;
    }

    // 获得分党委某个类型下的被推荐人
    public List<PcsPrCandidateView> find(int configId, byte stage, byte type, int partyId){

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        example.createCriteria().andConfigIdEqualTo(configId).andStageEqualTo(stage)
                .andTypeEqualTo(type).andPartyIdEqualTo(partyId);

        example.setOrderByClause("sort_order asc");

        return pcsPrCandidateViewMapper.selectByExample(example);
    }

    // 同一阶段，同一用户只能选一次（即不同分党委不能同时选一个人，因为要保证本分党委推荐本单位的人）
    public PcsPrCandidateView find(int userId, int configId, byte stage){

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andConfigIdEqualTo(configId).andStageEqualTo(stage);

        List<PcsPrCandidateView> pcsPrCandidateViews = pcsPrCandidateViewMapper.selectByExample(example);
        return pcsPrCandidateViews.size()>0?pcsPrCandidateViews.get(0):null;
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
   /* @Transactional
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
    }*/
}
