package controller.pm;

import ext.service.ShortMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import service.LoginUserService;
import service.party.*;
import service.pm.*;
import service.pmd.*;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

public class PmBaseController extends PmBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected BranchMemberService branchMemberService;
    @Autowired
    protected PmMeetingService pmMeetingService;
    @Autowired
    protected PmQuarterBranchService pmQuarterBranchService;
    @Autowired
    protected PmQuarterService pmQuarterService;
    @Autowired
    protected PmQuarterPartyService pmQuarterPartyService;
    @Autowired
    protected PartyMemberService partyMemberService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected PmMeetingFileService pmMeetingFileService;
}
