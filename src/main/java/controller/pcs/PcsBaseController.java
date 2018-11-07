package controller.pcs;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.pcs.PcsAdminService;
import service.pcs.PcsCandidateService;
import service.pcs.PcsCommitteeMemberService;
import service.pcs.PcsConfigService;
import service.pcs.PcsExportService;
import service.pcs.PcsOwService;
import service.pcs.PcsPartyService;
import service.pcs.PcsPartyViewService;
import service.pcs.PcsPrAlocateService;
import service.pcs.PcsPrCandidateService;
import service.pcs.PcsPrExportService;
import service.pcs.PcsPrFileService;
import service.pcs.PcsPrFileTemplateService;
import service.pcs.PcsPrListService;
import service.pcs.PcsPrOwService;
import service.pcs.PcsPrPartyService;
import service.pcs.PcsProposalOwService;
import service.pcs.PcsProposalService;
import service.pcs.PcsRecommendService;
import service.pcs.PcsVoteCandidateService;
import service.pcs.PcsVoteExportService;
import service.pcs.PcsVoteGroupService;
import service.pcs.PcsVoteMemberService;
import service.pcs.PcsVoteService;

public class PcsBaseController extends BaseController {

    @Autowired(required = false)
    protected PcsPartyViewService pcsPartyViewService;
    @Autowired(required = false)
    protected PcsAdminService pcsAdminService;
    @Autowired(required = false)
    protected PcsExportService pcsExportService;
    @Autowired(required = false)
    protected PcsCandidateService pcsCandidateService;
    @Autowired(required = false)
    protected PcsConfigService pcsConfigService;
    @Autowired(required = false)
    protected PcsCommitteeMemberService pcsCommitteeMemberService;
    @Autowired(required = false)
    protected PcsRecommendService pcsRecommendService;
    @Autowired(required = false)
    protected PcsOwService pcsOwService;
    @Autowired(required = false)
    protected PcsPartyService pcsPartyService;
    @Autowired(required = false)
    protected PcsProposalService pcsProposalService;

    @Autowired(required = false)
    protected PcsPrAlocateService pcsPrAlocateService;
    @Autowired(required = false)
    protected PcsPrPartyService pcsPrPartyService;
    @Autowired(required = false)
    protected PcsPrCandidateService pcsPrCandidateService;
    @Autowired(required = false)
    protected PcsPrExportService pcsPrExportService;
    @Autowired(required = false)
    protected PcsPrOwService pcsPrOwService;
    @Autowired(required = false)
    protected PcsPrFileTemplateService pcsPrFileTemplateService;
    @Autowired(required = false)
    protected PcsPrFileService pcsPrFileService;
    @Autowired(required = false)
    protected PcsPrListService pcsPrListService;
    @Autowired(required = false)
    protected PcsProposalOwService pcsProposalOwService;

    @Autowired(required = false)
    protected PcsVoteGroupService pcsVoteGroupService;
    @Autowired(required = false)
    protected PcsVoteMemberService pcsVoteMemberService;
    @Autowired(required = false)
    protected PcsVoteCandidateService pcsVoteCandidateService;
    @Autowired(required = false)
    protected PcsVoteExportService pcsVoteExportService;
    @Autowired(required = false)
    protected PcsVoteService pcsVoteService;
}
