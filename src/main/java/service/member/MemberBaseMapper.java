package service.member;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.member.*;
import persistence.party.EnterApplyMapper;
import persistence.party.RetireApplyMapper;
import service.CoreBaseMapper;

public class MemberBaseMapper extends CoreBaseMapper {

   /**
     * 党建
     */
    @Autowired(required = false)
    protected EnterApplyMapper enterApplyMapper;
    @Autowired(required = false)
    protected ApplyApprovalLogMapper applyApprovalLogMapper;
    @Autowired(required = false)
    protected MemberTransferMapper memberTransferMapper;
    @Autowired(required = false)
    protected MemberOutMapper memberOutMapper;
    @Autowired(required = false)
    protected MemberOutViewMapper memberOutViewMapper;
    @Autowired(required = false)
    protected MemberOutModifyMapper memberOutModifyMapper;
    @Autowired(required = false)
    protected MemberInMapper memberInMapper;
    @Autowired(required = false)
    protected MemberInModifyMapper memberInModifyMapper;
    @Autowired(required = false)
    protected MemberOutflowMapper memberOutflowMapper;
    @Autowired(required = false)
    protected MemberOutflowViewMapper memberOutflowViewMapper;
    @Autowired(required = false)
    protected MemberInflowMapper memberInflowMapper;
    @Autowired(required = false)
    protected MemberReturnMapper memberReturnMapper;
    @Autowired(required = false)
    protected MemberAbroadMapper memberAbroadMapper;
    @Autowired(required = false)
    protected MemberAbroadViewMapper memberAbroadViewMapper;
    @Autowired(required = false)
    protected MemberStayMapper memberStayMapper;
    @Autowired(required = false)
    protected MemberStayViewMapper memberStayViewMapper;
    @Autowired(required = false)
    protected MemberQuitMapper memberQuitMapper;
    @Autowired(required = false)
    protected RetireApplyMapper retireApplyMapper;
    @Autowired(required = false)
    protected MemberStudentMapper memberStudentMapper;
    @Autowired(required = false)
    protected MemberTeacherMapper memberTeacherMapper;
   
    @Autowired(required = false)
    protected MemberModifyMapper memberModifyMapper;
    @Autowired(required = false)
    protected ApplyOpenTimeMapper applyOpenTimeMapper;
    @Autowired(required = false)
    protected MemberApplyMapper memberApplyMapper;
    @Autowired(required = false)
    protected MemberApplyViewMapper memberApplyViewMapper;
}
