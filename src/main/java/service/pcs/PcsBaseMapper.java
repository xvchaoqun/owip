package service.pcs;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.pcs.*;
import persistence.pcs.common.IPcsMapper;
import service.CoreBaseMapper;

public class PcsBaseMapper extends CoreBaseMapper {

    /**
     * 党代会
     */
    @Autowired(required = false)
    protected IPcsMapper iPcsMapper;
    @Autowired(required = false)
    protected PcsPartyMapper pcsPartyMapper;
    @Autowired(required = false)
    protected PcsBranchMapper pcsBranchMapper;
    @Autowired(required = false)
    protected PcsIssueMapper pcsIssueMapper;
    @Autowired(required = false)
    protected PcsAdminMapper pcsAdminMapper;
    @Autowired(required = false)
    protected PcsAdminReportMapper pcsAdminReportMapper;
    @Autowired(required = false)
    protected PcsCandidateMapper pcsCandidateMapper;
    @Autowired(required = false)
    protected PcsCandidateChosenMapper pcsCandidateChosenMapper;
    @Autowired(required = false)
    protected PcsConfigMapper pcsConfigMapper;
    @Autowired(required = false)
    protected PcsRecommendMapper pcsRecommendMapper;
    @Autowired(required = false)
    protected PcsPrAllocateMapper pcsPrAllocateMapper;
    @Autowired(required = false)
    protected PcsPrRecommendMapper pcsPrRecommendMapper;
    @Autowired(required = false)
    protected PcsPrCandidateMapper pcsPrCandidateMapper;
    @Autowired(required = false)
    protected PcsPrFileTemplateMapper pcsPrFileTemplateMapper;
    @Autowired(required = false)
    protected PcsPrFileMapper pcsPrFileMapper;
    @Autowired(required = false)
    protected PcsProposalMapper pcsProposalMapper;
    @Autowired(required = false)
    protected PcsProposalViewMapper pcsProposalViewMapper;
    @Autowired(required = false)
    protected PcsProposalFileMapper pcsProposalFileMapper;
    @Autowired(required = false)
    protected PcsProposalSeconderMapper pcsProposalSeconderMapper;
    @Autowired(required = false)
    protected PcsVoteGroupMapper pcsVoteGroupMapper;
    @Autowired(required = false)
    protected PcsVoteCandidateMapper pcsVoteCandidateMapper;
    @Autowired(required = false)
    protected PcsVoteMemberMapper pcsVoteMemberMapper;
    @Autowired(required = false)
    protected PcsPollMapper pcsPollMapper;
    @Autowired(required = false)
    protected PcsPollCandidateMapper pcsPollCandidateMapper;
    @Autowired(required = false)
    protected PcsPollInspectorMapper pcsPollInspectorMapper;
    @Autowired(required = false)
    protected PcsPollResultMapper pcsPollResultMapper;
}
