package service.member;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.member.*;
import persistence.party.EnterApplyMapper;
import persistence.party.RetireApplyMapper;
import service.CoreBaseMapper;
import shiro.ShiroHelper;
import sys.helper.PartyHelper;

public class MemberBaseMapper extends CoreBaseMapper {

   /**
     * 党建
     */
    @Autowired(required = false)
    protected MemberRegMapper memberRegMapper;
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
    protected ApplySnMapper applySnMapper;
    @Autowired(required = false)
    protected ApplySnRangeMapper applySnRangeMapper;
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
    
    protected class VerifyAuth<T> {
        public Boolean isBranchAdmin;
        public Boolean isPartyAdmin;
        public Boolean isDirectBranch; // 是否直属党支部
        public Boolean isParty; // 是否分党委
        public T entity;
    }
    
    /**
     * 当前操作人员应该是申请人所在党支部或直属党支部的管理员，否则抛出异常
     */
    protected <T> VerifyAuth<T> checkVerityAuth(T entity, Integer partyId, Integer branchId) {
        
        VerifyAuth<T> verifyAuth = new VerifyAuth<T>();
        
        int loginUserId = ShiroHelper.getCurrentUserId();
        verifyAuth.entity = entity;
        
        verifyAuth.isBranchAdmin = PartyHelper.isPresentBranchAdmin(loginUserId, partyId, branchId);
        verifyAuth.isPartyAdmin = PartyHelper.isPresentPartyAdmin(loginUserId, partyId);
        verifyAuth.isDirectBranch = PartyHelper.isDirectBranch(partyId);
        if (!verifyAuth.isBranchAdmin && (!verifyAuth.isDirectBranch || !verifyAuth.isPartyAdmin)) { // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }
        return verifyAuth;
    }
    
    /**
     * 当前操作人员应该是应是申请人所在的分党委、党总支、直属党支部的管理员
     */
    protected <T> VerifyAuth<T> checkVerityAuth2(T entity, Integer partyId) {
        VerifyAuth<T> verifyAuth = new VerifyAuth<T>();
        
        int loginUserId = ShiroHelper.getCurrentUserId();
        verifyAuth.entity = entity;
        
        if (!PartyHelper.isPresentPartyAdmin(loginUserId, partyId)) {
            throw new UnauthorizedException();
        }
        verifyAuth.isParty = PartyHelper.isParty(partyId);
        verifyAuth.isPartyAdmin = PartyHelper.isPresentPartyAdmin(loginUserId, partyId);
        verifyAuth.isDirectBranch = PartyHelper.isDirectBranch(partyId);
        return verifyAuth;
    }
}
