package service.pcs;

import domain.pcs.PcsPrRecommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

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

   /* // 判断是否下发
    public boolean hasIssue(int configId, byte stage) {

        PcsIssueExample example = new PcsIssueExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage);

        return pcsIssueMapper.countByExample(example) > 0;
    }*/
}
