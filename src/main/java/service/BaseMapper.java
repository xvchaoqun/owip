package service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.*;
import persistence.common.*;
import shiro.ShiroUser;
import sys.tags.CmTag;

public class BaseMapper {

	protected class VerifyAuth<T>{
		public Boolean isBranchAdmin;
		public Boolean isPartyAdmin;
		public Boolean isDirectBranch; // 是否直属党支部
		public Boolean isParty; // 是否分党委
		public T entity;
	}

	/**
	 * 当前操作人员应该是申请人所在党支部或直属党支部的管理员，否则抛出异常
	 */
	protected VerifyAuth checkVerityAuth(Object entity, Integer partyId, Integer branchId ){

		VerifyAuth verifyAuth = new VerifyAuth();
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

		int loginUserId = shiroUser.getId();
		verifyAuth.entity = entity;

		verifyAuth.isBranchAdmin = CmTag.isPresentBranchAdmin(loginUserId, branchId);
		verifyAuth.isPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, partyId);
		verifyAuth.isDirectBranch = CmTag.isDirectBranch(partyId);
		if(!verifyAuth.isBranchAdmin && (!verifyAuth.isDirectBranch || !verifyAuth.isPartyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
			throw new UnauthorizedException();
		}
		return verifyAuth;
	}
	/**
	 * 当前操作人员应该是应是申请人所在的分党委、党总支、直属党支部的管理员
	 */
	protected VerifyAuth checkVerityAuth2(Object entity, Integer partyId){
		VerifyAuth verifyAuth = new VerifyAuth();
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

		int loginUserId = shiroUser.getId();
		verifyAuth.entity = entity;

		if(!CmTag.isPresentPartyAdmin(loginUserId, partyId)){
			throw new UnauthorizedException();
		}
		verifyAuth.isParty = CmTag.isParty(partyId);
		return verifyAuth;
	}

	@Autowired
	protected ApplicatPostMapper applicatPostMapper;
	@Autowired
	protected ApplicatTypeMapper applicatTypeMapper;
	@Autowired
	protected ApprovalOrderMapper approvalOrderMapper;
	@Autowired
	protected ApproverMapper approverMapper;
	@Autowired
	protected ApproverTypeMapper approverTypeMapper;
	@Autowired
	protected PassportDrawFileMapper passportDrawFileMapper;
	@Autowired
	protected PassportDrawMapper passportDrawMapper;
	@Autowired
	protected ApplySelfMapper applySelfMapper;
	@Autowired
	protected ApplySelfFileMapper applySelfFileMapper;
	@Autowired
	protected ApprovalLogMapper approvalLogMapper;
	@Autowired
	protected PassportMapper passportMapper;
	@Autowired
	protected SafeBoxMapper safeBoxMapper;
	@Autowired
	protected PassportApplyMapper passportApplyMapper;

	@Autowired
	protected EnterApplyMapper enterApplyMapper;

	@Autowired
	protected ApplyApprovalLogMapper applyApprovalLogMapper;
	@Autowired
	protected MemberStayMapper memberStayMapper;
	@Autowired
	protected MemberStayViewMapper memberStayViewMapper;

	@Autowired
	protected MemberTransferMapper memberTransferMapper;
	@Autowired
	protected MemberOutMapper memberOutMapper;
	@Autowired
	protected MemberInMapper memberInMapper;

	@Autowired
	protected MemberOutflowMapper memberOutflowMapper;
	@Autowired
	protected MemberOutflowViewMapper memberOutflowViewMapper;
	@Autowired
	protected MemberInflowMapper memberInflowMapper;
	@Autowired
	protected MemberReturnMapper memberReturnMapper;
	@Autowired
	protected MemberAbroadMapper memberAbroadMapper;
	@Autowired
	protected GraduateAbroadMapper graduateAbroadMapper;
	@Autowired
	protected GraduateAbroadViewMapper graduateAbroadViewMapper;
	@Autowired
	protected MemberQuitMapper memberQuitMapper;
	@Autowired
	protected RetireApplyMapper retireApplyMapper;
	@Autowired
	protected StudentMapper studentMapper;
	@Autowired
	protected MemberStudentMapper memberStudentMapper;
	@Autowired
	protected TeacherMapper teacherMapper;
	@Autowired
	protected MemberTeacherMapper memberTeacherMapper;
	@Autowired
	protected MemberMapper memberMapper;
	@Autowired
	protected ApplyLogMapper applyLogMapper;
	@Autowired
	protected ApplyOpenTimeMapper applyOpenTimeMapper;
	@Autowired
	protected MemberApplyMapper memberApplyMapper;
	@Autowired
	protected BranchMemberGroupMapper branchMemberGroupMapper;
	@Autowired
	protected BranchMemberGroupViewMapper branchMemberGroupViewMapper;
	@Autowired
	protected BranchMemberMapper branchMemberMapper;
	@Autowired
	protected PartyMapper partyMapper;
	@Autowired
	protected PartyMemberMapper partyMemberMapper;
	@Autowired
	protected PartyMemberGroupMapper partyMemberGroupMapper;
	@Autowired
	protected BranchMapper branchMapper;
	@Autowired
	protected OrgAdminMapper orgAdminMapper;

	@Autowired
	protected UnitAdminGroupMapper unitAdminGroupMapper;
	@Autowired
	protected UnitAdminMapper unitAdminMapper;
	@Autowired
	protected CadreInfoMapper cadreInfoMapper;
	@Autowired
	protected CadreFamliyAbroadMapper cadreFamliyAbroadMapper;
	@Autowired
	protected CadreFamliyMapper cadreFamliyMapper;
	@Autowired
	protected CadreCompanyMapper cadreCompanyMapper;
	@Autowired
	protected CadreParttimeMapper cadreParttimeMapper;
	@Autowired
	protected CadreRewardMapper cadreRewardMapper;
	@Autowired
	protected CadreResearchMapper cadreResearchMapper;
	@Autowired
	protected CadreCourseMapper cadreCourseMapper;
	@Autowired
	protected CadrePostMapper cadrePostMapper;
	@Autowired
	protected CadreMainWorkMapper cadreMainWorkMapper;
	@Autowired
	protected CadreSubWorkMapper cadreSubWorkMapper;
	@Autowired
	protected CadreWorkMapper cadreWorkMapper;
	@Autowired
	protected CadreEduMapper cadreEduMapper;
	@Autowired
	protected CadreTutorMapper cadreTutorMapper;
	@Autowired
	protected UnitTransferMapper unitTransferMapper;
	@Autowired
	protected UnitCadreTransferGroupMapper unitCadreTransferGroupMapper;
	@Autowired
	protected UnitCadreTransferMapper unitCadreTransferMapper;
	@Autowired
	protected DispatchMapper dispatchMapper;
	@Autowired
	protected DispatchTypeMapper dispatchTypeMapper;
	@Autowired
	protected DispatchCadreMapper dispatchCadreMapper;
	@Autowired
	protected DispatchUnitMapper dispatchUnitMapper;
	@Autowired
	protected DispatchUnitRelateMapper dispatchUnitRelateMapper;
	@Autowired
	protected LeaderMapper leaderMapper;
	@Autowired
	protected LeaderUnitMapper leaderUnitMapper;
	@Autowired
	protected CadreMapper cadreMapper;
	@Autowired
	protected HistoryUnitMapper historyUnitMapper;
	@Autowired
	protected UnitMapper unitMapper;

	@Autowired
	protected SysUserSyncMapper sysUserSyncMapper;
	@Autowired
	protected SysLogMapper sysLogMapper;
	@Autowired
	protected SysUserMapper sysUserMapper;
	@Autowired
	protected SysUserRegMapper sysUserRegMapper;
	@Autowired
	protected SysRoleMapper sysRoleMapper;
	@Autowired
	protected SysResourceMapper sysResourceMapper;
	@Autowired
	protected MetaClassMapper metaClassMapper;
	@Autowired
	protected MetaTypeMapper metaTypeMapper;

	@Autowired
	protected SysConfigMapper sysConfigMapper;

	@Autowired
	protected CommonUnitMapper commonUnitMapper;
	@Autowired
	protected CommonMapper commonMapper;
	@Autowired
	protected SearchMapper searchMapper;
	@Autowired
	protected SelectMapper selectMapper;
	@Autowired
	protected UpdateMapper updateMapper;
	@Autowired
	protected ExtYjsMapper extYjsMapper;
	@Autowired
	protected ExtBksMapper extBksMapper;
	@Autowired
	protected ExtJzgMapper extJzgMapper;

	@Autowired
	protected LocationMapper locationMapper;
	@Autowired
	protected CountryMapper countryMapper;
	@Autowired
	protected ShortMsgMapper shortMsgMapper;
}
