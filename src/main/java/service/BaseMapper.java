package service;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.*;
import persistence.common.CommonMapper;
import persistence.common.CommonUnitMapper;
import persistence.common.SelectMapper;
import persistence.common.UpdateMapper;

public class BaseMapper {

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
	protected MemberTransferMapper memberTransferMapper;
	@Autowired
	protected MemberOutMapper memberOutMapper;
	@Autowired
	protected MemberInMapper memberInMapper;

	@Autowired
	protected MemberOutflowMapper memberOutflowMapper;
	@Autowired
	protected MemberInflowMapper memberInflowMapper;
	@Autowired
	protected MemberReturnMapper memberReturnMapper;
	@Autowired
	protected MemberAbroadMapper memberAbroadMapper;
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
