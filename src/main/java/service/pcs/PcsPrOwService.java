package service.pcs;

import domain.pcs.*;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;
import sys.constants.PcsConstants;
import sys.constants.RoleConstants;

import java.util.List;

@Service
public class PcsPrOwService extends PcsBaseMapper {

    @Autowired
    private PcsPrPartyService pcsPrPartyService;
    @Autowired
    private SysUserService sysUserService;

    // 组织部管理员审核分党委推荐
    @Transactional
    public void checkPartyRecommend(int configId, byte stage,
                                    Integer[] partyIds, byte status, String remark) {

        if(!PcsConstants.PCS_PR_RECOMMEND_STATUS_MAP.containsKey(status)){
            return ;
        }
        for (int partyId : partyIds) {

            PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, stage, partyId);
            // 必须是已上报、未审核的才能审核
            if (pcsPrRecommend == null
                    || !pcsPrRecommend.getHasReport()
                    || pcsPrRecommend.getStatus() != PcsConstants.PCS_PR_RECOMMEND_STATUS_INIT) continue;


            PcsPrRecommend record = new PcsPrRecommend();
            record.setId(pcsPrRecommend.getId());
            record.setStatus(status);
            record.setCheckRemark(remark);

            if (status == PcsConstants.PCS_PR_RECOMMEND_STATUS_DENY) {
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
                .andStageEqualTo(stage).andStatusNotEqualTo(PcsConstants.PCS_PR_RECOMMEND_STATUS_PASS);

        return pcsPrRecommendMapper.selectByExample(example);
    }
    // 组织部未审核或未审核通过的推荐列表 （用于三上所有的分党委提交推荐之后）
    public long notPassPCSPrRecommendsCount(int configId, byte stage){

        PcsPrRecommendExample example = new PcsPrRecommendExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage).andStatusNotEqualTo(PcsConstants.PCS_PR_RECOMMEND_STATUS_PASS);

        return pcsPrRecommendMapper.countByExample(example);
    }

    //同步全校党代表名单至提案党代表名单
    @Transactional
    public void sync(int configId) {

        // 清空之前的名单（如果存在)
        commonMapper.excuteSql("update pcs_pr_candidate pc,pcs_pr_recommend pr set pc.is_proposal=null, " +
                "pc.proposal_sort_order=null " + "where pc.recommend_id=pr.id and pr.config_id=" + configId + " and pr.stage="
                + PcsConstants.PCS_STAGE_SECOND + " and pc.is_chosen = 1");

        List<SysUserView> prUsers = sysUserService.findByRole(RoleConstants.ROLE_PCS_PR);
        for (SysUserView prUser : prUsers) {
            // 清除角色
            sysUserService.delRole(prUser.getId(), RoleConstants.ROLE_PCS_PR);
        }

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(PcsConstants.PCS_STAGE_SECOND).andIsChosenEqualTo(true);
        //example.setOrderByClause("party_sort_order desc, type asc, realname_sort_order asc");
        example.setOrderByClause("realname_sort_order asc, branch_vote desc, vote desc, positive_vote desc");
        List<PcsPrCandidate> records = pcsPrCandidateMapper.selectByExample(example);

        int size = records.size();
        for (int i = 0; i < size; i++) {

            PcsPrCandidate candidate = records.get(i);

            PcsPrCandidate record = new PcsPrCandidate();
            record.setId(candidate.getId());
            record.setIsProposal(true);
            record.setProposalSortOrder(i+1); // 升序排列

            pcsPrCandidateMapper.updateByPrimaryKeySelective(record);

            Integer userId = candidate.getUserId();
            sysUserService.addRole(userId, RoleConstants.ROLE_PCS_PR);
        }
    }
}
