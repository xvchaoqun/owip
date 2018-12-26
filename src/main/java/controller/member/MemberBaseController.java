package controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import service.LoginUserService;
import service.base.LocationService;
import service.ext.ExtService;
import service.member.*;
import service.party.*;
import service.sys.StudentInfoService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import service.sys.UserBeanService;
import sys.HttpResponseMethod;

public class MemberBaseController extends MemberBaseMapper  implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected LocationService locationService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected MemberStudentService memberStudentService;
    @Autowired
    protected StudentInfoService studentInfoService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected MemberTeacherService memberTeacherService;
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
    protected ApplyOpenTimeService applyOpenTimeService;
    @Autowired
    protected MemberApplyService memberApplyService;
}
