package controller.pcs;

import ext.service.ExtService;
import org.springframework.beans.factory.annotation.Autowired;
import service.LoginUserService;
import service.base.MetaClassService;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.global.CacheService;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import service.pcs.*;
import service.sys.*;
import sys.HttpResponseMethod;

public class PcsBaseController extends PcsBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysPropertyService sysPropertyService;
    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected CacheService cacheService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected StudentInfoService studentInfoService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected MetaClassService metaClassService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected ExtService extService;

    @Autowired(required = false)
    protected PcsAdminService pcsAdminService;
    @Autowired(required = false)
    protected PcsExportService pcsExportService;
    @Autowired(required = false)
    protected PcsCandidateService pcsCandidateService;
    @Autowired(required = false)
    protected PcsConfigService pcsConfigService;
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

    @Autowired(required = false)
    protected PcsPollService pcsPollService;
    @Autowired(required = false)
    protected PcsPollCandidateService pcsPollCandidateService;
    @Autowired(required = false)
    protected PcsPollInspectorService pcsPollInspectorService;
    @Autowired(required = false)
    protected PcsPollResultService pcsPollResultService;
}
