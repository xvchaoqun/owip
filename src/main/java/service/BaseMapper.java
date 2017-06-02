package service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.abroad.*;
import persistence.base.*;
import persistence.cadre.*;
import persistence.cadreInspect.CadreInspectMapper;
import persistence.cadreInspect.CadreInspectViewMapper;
import persistence.cadreReserve.CadreReserveMapper;
import persistence.cadreReserve.CadreReserveViewMapper;
import persistence.cis.*;
import persistence.common.*;
import persistence.cpc.CpcAllocationMapper;
import persistence.crp.CrpRecordMapper;
import persistence.dispatch.*;
import persistence.ext.ExtAbroadMapper;
import persistence.ext.ExtBksMapper;
import persistence.ext.ExtJzgMapper;
import persistence.ext.ExtYjsMapper;
import persistence.member.*;
import persistence.modify.ModifyBaseApplyMapper;
import persistence.modify.ModifyBaseItemMapper;
import persistence.modify.ModifyCadreAuthMapper;
import persistence.modify.ModifyTableApplyMapper;
import persistence.party.*;
import persistence.recruit.RecruitPostMapper;
import persistence.recruit.RecruitTemplateMapper;
import persistence.sys.*;
import persistence.train.*;
import persistence.unit.*;
import persistence.verify.VerifyAgeMapper;
import persistence.verify.VerifyWorkTimeMapper;
import shiro.ShiroUser;
import sys.tags.CmTag;

import java.io.File;

public class BaseMapper {

	// #tomcat版本>=8.0.39 下 win10下url路径中带正斜杠的文件路径读取不了
	protected final static String FILE_SEPARATOR= File.separator;

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
	protected <T> VerifyAuth<T> checkVerityAuth(T entity, Integer partyId, Integer branchId ){

		VerifyAuth<T> verifyAuth = new VerifyAuth<T>();
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
	protected <T> VerifyAuth<T> checkVerityAuth2(T entity, Integer partyId){
		VerifyAuth<T> verifyAuth = new VerifyAuth<T>();
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

	// 获得表中最大的排序序号
	public int getNextSortOrder(String tableName, String whereSql){

		Integer maxSortOrder = commonMapper.getMaxSortOrder(tableName, whereSql);
		return (maxSortOrder == null ? 1 : maxSortOrder + 1);
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
	protected MemberApplyViewMapper memberApplyViewMapper;
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
	protected PartyMemberViewMapper partyMemberViewMapper;
	@Autowired
	protected PartyMemberGroupMapper partyMemberGroupMapper;
	@Autowired
	protected PartyMemberGroupViewMapper partyMemberGroupViewMapper;
	@Autowired
	protected BranchMapper branchMapper;
	@Autowired
	protected BranchTransferLogMapper branchTransferLogMapper;
	@Autowired
	protected BranchViewMapper branchViewMapper;
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
	protected CadreReportMapper cadreReportMapper;

	@Autowired
	protected CadreLeaderMapper cadreLeaderMapper;
	@Autowired
	protected CadreLeaderUnitMapper cadreLeaderUnitMapper;
	@Autowired
	protected CadreAdditionalPostMapper cadreAdditionalPostMapper;
	@Autowired
	protected CadreMapper cadreMapper;
	@Autowired
	protected CadreViewMapper cadreViewMapper;
	@Autowired
	protected CadreAdLogMapper cadreAdLogMapper;
	@Autowired
	protected CadreInspectMapper cadreInspectMapper;
	@Autowired
	protected CadreInspectViewMapper cadreInspectViewMapper;
	@Autowired
	protected CadreReserveMapper cadreReserveMapper;
	@Autowired
	protected CadreReserveViewMapper cadreReserveViewMapper;
	@Autowired
	protected CadreStatHistoryMapper cadreStatHistoryMapper;

	@Autowired
	protected CisEvaluateMapper cisEvaluateMapper;
	@Autowired
	protected CisInspectObjMapper cisInspectObjMapper;
	@Autowired
	protected CisInspectObjViewMapper cisInspectObjViewMapper;
	@Autowired
	protected CisInspectorMapper cisInspectorMapper;
	@Autowired
	protected CisInspectorViewMapper cisInspectorViewMapper;
	@Autowired
	protected CisObjInspectorMapper cisObjInspectorMapper;
	@Autowired
	protected CisObjUnitMapper cisObjUnitMapper;

	@Autowired
	protected CrpRecordMapper crpRecordMapper;

	@Autowired
	protected CpcAllocationMapper cpcAllocationMapper;

	@Autowired
	protected TrainMapper trainMapper;
	@Autowired
	protected TrainCourseMapper trainCourseMapper;
	@Autowired
	protected TrainEvaNormMapper trainEvaNormMapper;
	@Autowired
	protected TrainEvaRankMapper trainEvaRankMapper;
	@Autowired
	protected TrainEvaTableMapper trainEvaTableMapper;
	@Autowired
	protected TrainEvaResultMapper trainEvaResultMapper;
	@Autowired
	protected TrainInspectorMapper trainInspectorMapper;
	@Autowired
	protected TrainInspectorCourseMapper trainInspectorCourseMapper;


	@Autowired
	protected RecruitPostMapper recruitPostMapper;
	@Autowired
	protected RecruitTemplateMapper recruitTemplateMapper;

	@Autowired
	protected VerifyAgeMapper verifyAgeMapper;
	@Autowired
	protected VerifyWorkTimeMapper verifyWorkTimeMapper;

	@Autowired
	protected UnitTransferMapper unitTransferMapper;
	@Autowired
	protected UnitCadreTransferGroupMapper unitCadreTransferGroupMapper;
	@Autowired
	protected UnitCadreTransferMapper unitCadreTransferMapper;
	@Autowired
	protected DispatchMapper dispatchMapper;
	@Autowired
	protected DispatchViewMapper dispatchViewMapper;
	@Autowired
	protected DispatchTypeMapper dispatchTypeMapper;
	@Autowired
	protected DispatchCadreMapper dispatchCadreMapper;
	@Autowired
	protected DispatchCadreViewMapper dispatchCadreViewMapper;
	@Autowired
	protected DispatchCadreRelateMapper dispatchCadreRelateMapper;
	@Autowired
	protected DispatchUnitMapper dispatchUnitMapper;
	@Autowired
	protected DispatchUnitRelateMapper dispatchUnitRelateMapper;
	@Autowired
	protected DispatchWorkFileMapper dispatchWorkFileMapper;
	@Autowired
	protected DispatchWorkFileAuthMapper dispatchWorkFileAuthMapper;


	@Autowired
	protected StatCadreMapper statCadreMapper;
	@Autowired
	protected StatTrainMapper statTrainMapper;

	@Autowired
	protected HistoryUnitMapper historyUnitMapper;
	@Autowired
	protected UnitMapper unitMapper;

	@Autowired
	protected ModifyCadreAuthMapper modifyCadreAuthMapper;
	@Autowired
	protected ModifyBaseApplyMapper modifyBaseApplyMapper;
	@Autowired
	protected ModifyBaseItemMapper modifyBaseItemMapper;
	@Autowired
	protected ModifyTableApplyMapper modifyTableApplyMapper;

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
	protected FeedbackMapper feedbackMapper;

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
	protected ShortMsgReceiverMapper shortMsgReceiverMapper;
	@Autowired
	protected ContentTplMapper contentTplMapper;
	@Autowired
	protected SitemapMapper sitemapMapper;
	@Autowired
	protected SitemapRoleMapper sitemapRoleMapper;
	@Autowired
	protected ShortMsgTplMapper shortMsgTplMapper;
}
