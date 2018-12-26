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
    protected PcsExcludeBranchMapper pcsExcludeBranchMapper;
    @Autowired(required = false)
    protected PcsPartyViewMapper pcsPartyViewMapper;
    @Autowired(required = false)
    protected PcsIssueMapper pcsIssueMapper;
    @Autowired(required = false)
    protected PcsAdminMapper pcsAdminMapper;
    @Autowired(required = false)
    protected PcsAdminReportMapper pcsAdminReportMapper;
    @Autowired(required = false)
    protected PcsCandidateMapper pcsCandidateMapper;
    @Autowired(required = false)
    protected PcsCandidateViewMapper pcsCandidateViewMapper;
    @Autowired(required = false)
    protected PcsCandidateChosenMapper pcsCandidateChosenMapper;
    @Autowired(required = false)
    protected PcsConfigMapper pcsConfigMapper;
    @Autowired(required = false)
    protected PcsCommitteeMemberMapper pcsCommitteeMemberMapper;
    @Autowired(required = false)
    protected PcsCommitteeMemberViewMapper pcsCommitteeMemberViewMapper;
    @Autowired(required = false)
    protected PcsRecommendMapper pcsRecommendMapper;
    @Autowired(required = false)
    protected PcsPrAllocateMapper pcsPrAllocateMapper;
    @Autowired(required = false)
    protected PcsPrRecommendMapper pcsPrRecommendMapper;
    @Autowired(required = false)
    protected PcsPrCandidateMapper pcsPrCandidateMapper;
    @Autowired(required = false)
    protected PcsPrCandidateViewMapper pcsPrCandidateViewMapper;
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
}
