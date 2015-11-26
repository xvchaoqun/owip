package service;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.*;

public class BaseMapper {

	@Autowired
	protected CadreParttimeMapper cadreParttimeMapper;
	@Autowired
	protected CadreTeachRewardMapper cadreTeachRewardMapper;
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
	protected ExtYjsMapper extYjsMapper;
	@Autowired
	protected ExtBksMapper extBksMapper;
	@Autowired
	protected ExtJzgMapper extJzgMapper;
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
	protected UnitTransferItemMapper unitTransferItemMapper;
	@Autowired
	protected UnitTransferMapper unitTransferMapper;
	@Autowired
	protected UnitCadreTransferGroupMapper unitCadreTransferGroupMapper;
	@Autowired
	protected UnitCadreTransferItemMapper unitCadreTransferItemMapper;
	@Autowired
	protected UnitCadreTransferMapper unitCadreTransferMapper;
	@Autowired
	protected DispatchMapper dispatchMapper;
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
	protected CommonMapper commonMapper;

}
