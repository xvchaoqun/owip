package service.pcs;

import domain.pcs.PcsPrRecommend;
import domain.pcs.PcsPrRecommendExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.List;

@Service
public class PcsPrOwService extends BaseMapper {

    @Autowired
    private PcsPrPartyService pcsPrPartyService;

    // 组织部管理员审核分党委推荐
    @Transactional
    public void checkPartyRecommend(int configId, byte stage,
                                    int[] partyIds, byte status, String remark) {

        if(!SystemConstants.PCS_PR_RECOMMEND_STATUS_MAP.containsKey(status)){
            return ;
        }
        for (int partyId : partyIds) {

            PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, stage, partyId);
            // 必须是已上报、未审核的才能审核
            if (pcsPrRecommend == null
                    || !pcsPrRecommend.getHasReport()
                    || pcsPrRecommend.getStatus() != SystemConstants.PCS_PR_RECOMMEND_STATUS_INIT) continue;


            PcsPrRecommend record = new PcsPrRecommend();
            record.setId(pcsPrRecommend.getId());
            record.setStatus(status);
            record.setCheckRemark(remark);

            if (status == SystemConstants.PCS_PR_RECOMMEND_STATUS_DENY) {
                // 审核不通过，分党委可以重新进行当前stage的程序，不可以进行下一stage的程序
                record.setHasReport(false);
            }

            pcsPrRecommendMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 组织部未审核或未审核通过的推荐列表 （用于三上所有的分党委提交推荐之后）
    public List<PcsPrRecommend> notPassPCSPrRecommends(int configId, byte stage){

        PcsPrRecommendExample example = new PcsPrRecommendExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage).andStatusNotEqualTo(SystemConstants.PCS_PR_RECOMMEND_STATUS_PASS);

        return pcsPrRecommendMapper.selectByExample(example);
    }
    // 组织部未审核或未审核通过的推荐列表 （用于三上所有的分党委提交推荐之后）
    public long notPassPCSPrRecommendsCount(int configId, byte stage){

        PcsPrRecommendExample example = new PcsPrRecommendExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage).andStatusNotEqualTo(SystemConstants.PCS_PR_RECOMMEND_STATUS_PASS);

        return pcsPrRecommendMapper.countByExample(example);
    }
}
