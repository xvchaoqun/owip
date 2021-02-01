package controller.member;

import ext.service.ExtCommonService;
import ext.service.ExtService;
import org.springframework.beans.factory.annotation.Autowired;
import service.LoginUserService;
import service.base.CountryService;
import service.base.LocationService;
import service.base.MetaTypeService;
import service.global.CacheService;
import service.member.*;
import service.party.*;
import service.sys.StudentInfoService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import service.sys.UserBeanService;
import sys.HttpResponseMethod;

public class MemberBaseController extends MemberBaseMapper  implements HttpResponseMethod {
    @Autowired
    protected MemberHistoryService memberHistoryService;
    @Autowired
    protected MemberCertifyService memberCertifyService;
    @Autowired
    protected MemberReportService memberReportService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected CacheService cacheService;
    @Autowired
    protected CountryService countryService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected LocationService locationService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected StudentInfoService studentInfoService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected ExtService extService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected PartyMemberService partyMemberService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected BranchMemberService branchMemberService;
    
    @Autowired
    protected MemberCheckService memberCheckService;
    @Autowired
    protected MemberRegService memberRegService;
    @Autowired
    protected EnterApplyService enterApplyService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    @Autowired
    protected MemberTransferService memberTransferService;
    @Autowired
    protected MemberOutService memberOutService;
    @Autowired
    protected MemberInService memberInService;

    @Autowired
    protected MemberInflowService memberInflowService;
    @Autowired
    protected MemberInflowOutService memberInflowOutService;
    @Autowired
    protected MemberOutflowService memberOutflowService;
    @Autowired
    protected MemberReturnService memberReturnService;
    @Autowired
    protected MemberAbroadService memberAbroadService;
    @Autowired
    protected MemberStayService memberStayService;
    @Autowired
    protected MemberQuitService memberQuitService;
    @Autowired
    protected RetireApplyService retireApplyService;

    @Autowired
    protected MemberApplyService memberApplyService;
    @Autowired
    protected ApplySnService applySnService;
    @Autowired
    protected ApplySnRangeService applySnRangeService;

    @Autowired
    protected ExtCommonService extCommonService;
}
