package service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.abroad.*;
import persistence.base.ContentTplMapper;
import persistence.base.CountryMapper;
import persistence.base.LocationMapper;
import persistence.cadre.*;
import persistence.common.*;
import persistence.dispatch.*;
import persistence.ext.ExtAbroadMapper;
import persistence.ext.ExtBksMapper;
import persistence.ext.ExtJzgMapper;
import persistence.ext.ExtYjsMapper;
import persistence.member.*;
import persistence.party.*;
import persistence.sys.*;
import persistence.unit.*;
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

		verifyAuth.isBranchAdmin = CmTag.isPresentBranchAdmin(loginUserId, partyId, branchId);
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
		verifyAuth.isPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, partyId);
		verifyAuth.isDirectBranch = CmTag.isDirectBranch(partyId);
		return verifyAuth;
	}

	@Autowired
	protected ApplicatCadreMapper applicatCadreMapper;
	@Autowired
	protected ApplicatTypeMapper applicatTypeMapper;
	@Autowired
	protected ApprovalOrderMapper approvalOrderMapper;
	@Autowired
	protected ApproverMapper approverMapper;
	@Autowired
	protected ApproverBlackListMapper approverBlackListMapper;
	@Autowired
	protected ApproverTypeMapper approverTypeMapper;
	@Autowired
	protected PassportDrawFileMapper passportDrawFileMapper;
	@Autowired
	protected PassportDrawMapper passportDrawMapper;
	@Autowired
	protected ApplySelfMapper applySelfMapper;
	@Autowired
	protected ApplySelfModifyMapper applySelfModifyMapper;
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
	protected PassportApplyViewMapper passportApplyViewMapper;

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
	protected MemberOutModifyMapper memberOutModifyMapper;
	@Autowired
	protected MemberInMapper memberInMapper;
	@Autowired
	protected MemberInModifyMapper memberInModifyMapper;
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
	protected MemberAbroadViewMapper memberAbroadViewMapper;
	@Autowired
	protected GraduateAbroadMapper graduateAbroadMapper;
	@Autowired
	protected GraduateAbroadViewMapper graduateAbroadViewMapper;
	@Autowired
	protected MemberQuitMapper memberQuitMapper;
	@Autowired
	protected RetireApplyMapper retireApplyMapper;
	@Autowired
	protected StudentInfoMapper studentInfoMapper;
	@Autowired
	protected MemberStudentMapper memberStudentMapper;
	@Autowired
	protected TeacherInfoMapper teacherInfoMapper;
	@Autowired
	protected MemberTeacherMapper memberTeacherMapper;
	@Autowired
	protected MemberMapper memberMapper;
	@Autowired
	protected MemberModifyMapper memberModifyMapper;
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
	protected PartyViewMapper partyViewMapper;
	@Autowired
	protected PartyMemberMapper partyMemberMapper;
	@Autowired
	protected PartyMemberGroupMapper partyMemberGroupMapper;
	@Autowired
	protected BranchMapper branchMapper;
	@Autowired
	protected BranchViewMapper branchViewMapper;
	@Autowired
	protected OrgAdminMapper orgAdminMapper;

	@Autowired
	protected UnitAdminGroupMapper unitAdminGroupMapper;
	@Autowired
	protected UnitAdminMapper unitAdminMapper;
	@Autowired
	protected CadreConcatMapper cadreConcatMapper;
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
	protected CadreTrainMapper cadreTrainMapper;
	@Autowired
	protected CadreRewardMapper cadreRewardMapper;
	@Autowired
	protected CadrePaperMapper cadrePaperMapper;
	@Autowired
	protected CadreResearchMapper cadreResearchMapper;
	@Autowired
	protected CadreBookMapper cadreBookMapper;
	@Autowired
	protected CadreCourseMapper cadreCourseMapper;
	@Autowired
	protected CadrePostMapper cadrePostMapper;
	@Autowired
	protected CadrePostProMapper cadrePostProMapper;
	@Autowired
	protected CadrePostAdminMapper cadrePostAdminMapper;
	@Autowired
	protected CadrePostWorkMapper cadrePostWorkMapper;
	@Autowired
	protected CadreAdminLevelMapper cadreAdminLevelMapper;
	@Autowired
	protected CadreWorkMapper cadreWorkMapper;
	@Autowired
	protected CadreEduMapper cadreEduMapper;
	@Autowired
	protected CadreUnderEduMapper cadreUnderEduMapper;
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
	protected DispatchCadreRelateMapper dispatchCadreRelateMapper;
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
	protected CadreAdditionalPostMapper cadreAdditionalPostMapper;
	@Autowired
	protected CadreViewMapper cadreViewMapper;
	@Autowired
	protected HistoryUnitMapper historyUnitMapper;
	@Autowired
	protected UnitMapper unitMapper;

	@Autowired
	protected SysUserSyncMapper sysUserSyncMapper;
	@Autowired
	protected SysLogMapper sysLogMapper;
	@Autowired
	protected SysLoginLogMapper sysLoginLogMapper;
	@Autowired
	protected SysUserMapper sysUserMapper;
	@Autowired
	protected SysUserInfoMapper sysUserInfoMapper;
	@Autowired
	protected SysUserViewMapper sysUserViewMapper;
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
	protected AttachFileMapper attachFileMapper;
	@Autowired
	protected HtmlFragmentMapper htmlFragmentMapper;
	@Autowired
	protected SysOnlineStaticMapper sysOnlineStaticMapper;

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
	protected StatMapper statMapper;
	@Autowired
	protected ExtYjsMapper extYjsMapper;
	@Autowired
	protected ExtBksMapper extBksMapper;
	@Autowired
	protected ExtJzgMapper extJzgMapper;
	@Autowired
	protected ExtAbroadMapper extAbroadMapper;

	@Autowired
	protected LocationMapper locationMapper;
	@Autowired
	protected CountryMapper countryMapper;
	@Autowired
	protected ShortMsgMapper shortMsgMapper;
	@Autowired
	protected ContentTplMapper contentTplMapper;
}
