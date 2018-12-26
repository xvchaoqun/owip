package controller.sc;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.dispatch.DispatchCadreMapper;
import service.abroad.PassportService;
import service.base.AnnualTypeService;
import service.base.ShortMsgService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.sc.ScBaseMapper;
import service.sc.scAd.ScAdArchiveService;
import service.sc.scAd.ScAdArchiveVoteService;
import service.sc.scAd.ScAdUseService;
import service.sc.scBorder.ScBorderItemService;
import service.sc.scBorder.ScBorderService;
import service.sc.scCommittee.*;
import service.sc.scDispatch.ScDispatchCommitteeService;
import service.sc.scDispatch.ScDispatchService;
import service.sc.scDispatch.ScDispatchUserService;
import service.sc.scGroup.*;
import service.sc.scLetter.ScLetterItemService;
import service.sc.scLetter.ScLetterReplyService;
import service.sc.scLetter.ScLetterService;
import service.sc.scMatter.*;
import service.sc.scMotion.ScMotionPostService;
import service.sc.scMotion.ScMotionService;
import service.sc.scPassport.ScPassportHandService;
import service.sc.scPassport.ScPassportMsgService;
import service.sc.scPassport.ScPassportService;
import service.sc.scPublic.ScPublicService;
import service.sc.scPublic.ScPublicUserService;
import service.sc.scSubsidy.ScSubsidyCadreService;
import service.sc.scSubsidy.ScSubsidyDcService;
import service.sc.scSubsidy.ScSubsidyDispatchService;
import service.sc.scSubsidy.ScSubsidyService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import service.unit.UnitService;
import sys.HttpResponseMethod;

public class ScBaseController extends ScBaseMapper implements HttpResponseMethod {
    
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected CadreCommonService cadreCommonService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected AnnualTypeService annualTypeService;
    @Autowired(required = false)
    protected PassportService passportService;
    
    @Autowired
    protected ScAdArchiveService scAdArchiveService;
    @Autowired
    protected ScAdArchiveVoteService scAdArchiveVoteService;
    @Autowired
    protected ScAdUseService scAdUseService;
    
    @Autowired(required = false)
    protected ScBorderService scBorderService;
    @Autowired(required = false)
    protected ScBorderItemService scBorderItemService;
    @Autowired(required = false)
    protected DispatchCadreMapper dispatchCadreMapper;
    
     @Autowired
    protected ScCommitteeService scCommitteeService;
    @Autowired
    protected ScCommitteeMemberService scCommitteeMemberService;
    @Autowired
    protected ScCommitteeTopicService scCommitteeTopicService;
    @Autowired
    protected ScCommitteeVoteService scCommitteeVoteService;
    @Autowired
    protected ScCommitteeOtherVoteService scCommitteeOtherVoteService;
    
    @Autowired
    protected ScDispatchService scDispatchService;
    @Autowired
    protected ScDispatchUserService scDispatchUserService;
    @Autowired
    protected ScDispatchCommitteeService scDispatchCommitteeService;
    
    @Autowired
    protected ScGroupService scGroupService;
    @Autowired
    protected ScGroupFileService scGroupFileService;
    @Autowired
    protected ScGroupMemberService scGroupMemberService;
    @Autowired
    protected ScGroupParticipantService scGroupParticipantService;
    @Autowired
    protected ScGroupTopicService scGroupTopicService;
    @Autowired
    protected ScGroupTopicUnitService scGroupTopicUnitService;
    
    @Autowired
    protected ScLetterService scLetterService;
    @Autowired
    protected ScLetterItemService scLetterItemService;
    @Autowired
    protected ScLetterReplyService scLetterReplyService;
    
    @Autowired
    protected ScMatterService scMatterService;
    @Autowired
    protected ScMatterItemService scMatterItemService;
    @Autowired
    protected ScMatterAccessService scMatterAccessService;
    @Autowired
    protected ScMatterAccessItemService scMatterAccessItemService;
    @Autowired
    protected ScMatterCheckService scMatterCheckService;
    @Autowired
    protected ScMatterCheckItemService scMatterCheckItemService;
    @Autowired
    protected ScMatterTransferService scMatterTransferService;
    
    @Autowired
    protected ScMotionService scMotionService;
    @Autowired
    protected ScMotionPostService scMotionPostService;
    
    @Autowired
    protected ScPassportService scPassportService;
    @Autowired
    protected ScPassportHandService scPassportHandService;
    @Autowired
    protected ScPassportMsgService scPassportMsgService;
    
    @Autowired
    protected ScPublicService scPublicService;
    @Autowired
    protected ScPublicUserService scPublicUserService;
    
    @Autowired
    protected ScSubsidyService scSubsidyService;
    @Autowired
    protected ScSubsidyCadreService scSubsidyCadreService;
    @Autowired
    protected ScSubsidyDcService scSubsidyDcService;
    @Autowired
    protected ScSubsidyDispatchService scSubsidyDispatchService;
}
