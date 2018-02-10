package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.member.ApplyApprovalLogService;
import service.member.ApplyOpenTimeService;
import service.member.MemberAbroadService;
import service.member.MemberApplyService;
import service.member.MemberInService;
import service.member.MemberInflowOutService;
import service.member.MemberInflowService;
import service.member.MemberOutService;
import service.member.MemberOutflowService;
import service.member.MemberQuitService;
import service.member.MemberReturnService;
import service.member.MemberStayService;
import service.member.MemberTransferService;
import service.member.RetireApplyService;
import service.party.EnterApplyService;

public class MemberBaseController extends BaseController {

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
