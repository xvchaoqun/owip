package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.pcs.PcsAdminService;
import service.pcs.PcsCandidateService;
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

public class PcsBaseController extends BaseController {

    @Autowired
    protected PcsPartyViewService pcsPartyViewService;
    @Autowired
    protected PcsAdminService pcsAdminService;
    @Autowired
    protected PcsExportService pcsExportService;
    @Autowired
    protected PcsCandidateService pcsCandidateService;
    @Autowired
    protected PcsConfigService pcsConfigService;
    @Autowired
    protected PcsRecommendService pcsRecommendService;
    @Autowired
    protected PcsOwService pcsOwService;
    @Autowired
    protected PcsPartyService pcsPartyService;
    @Autowired
    protected PcsProposalService pcsProposalService;

    @Autowired
    protected PcsPrAlocateService pcsPrAlocateService;
    @Autowired
    protected PcsPrPartyService pcsPrPartyService;
    @Autowired
    protected PcsPrCandidateService pcsPrCandidateService;
    @Autowired
    protected PcsPrExportService pcsPrExportService;
    @Autowired
    protected PcsPrOwService pcsPrOwService;
    @Autowired
    protected PcsPrFileTemplateService pcsPrFileTemplateService;
    @Autowired
    protected PcsPrFileService pcsPrFileService;
    @Autowired
    protected PcsPrListService pcsPrListService;
    @Autowired
    protected PcsProposalOwService pcsProposalOwService;

    @Autowired
    protected PcsVoteGroupService pcsVoteGroupService;
    @Autowired
    protected PcsVoteMemberService pcsVoteMemberService;
    @Autowired
    protected PcsVoteCandidateService pcsVoteCandidateService;
    @Autowired
    protected PcsVoteExportService pcsVoteExportService;
}
