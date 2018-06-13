package service;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.abroad.AbroadAdditionalPostMapper;
import persistence.abroad.AbroadAdditionalPostViewMapper;
import persistence.abroad.ApplicatCadreMapper;
import persistence.abroad.ApplicatTypeMapper;
import persistence.abroad.ApplySelfFileMapper;
import persistence.abroad.ApplySelfMapper;
import persistence.abroad.ApplySelfModifyMapper;
import persistence.abroad.ApprovalLogMapper;
import persistence.abroad.ApprovalOrderMapper;
import persistence.abroad.ApproverBlackListMapper;
import persistence.abroad.ApproverMapper;
import persistence.abroad.ApproverTypeMapper;
import persistence.abroad.PassportApplyMapper;
import persistence.abroad.PassportApplyViewMapper;
import persistence.abroad.PassportDrawFileMapper;
import persistence.abroad.PassportDrawMapper;
import persistence.abroad.PassportMapper;
import persistence.abroad.SafeBoxMapper;
import persistence.abroad.TaiwanRecordMapper;
import persistence.abroad.common.IAbroadMapper;
import persistence.base.ContentTplMapper;
import persistence.base.CountryMapper;
import persistence.base.LocationMapper;
import persistence.base.MetaClassMapper;
import persistence.base.MetaTypeMapper;
import persistence.base.ShortMsgMapper;
import persistence.base.ShortMsgReceiverMapper;
import persistence.base.ShortMsgTplMapper;
import persistence.base.SitemapMapper;
import persistence.base.SitemapRoleMapper;
import persistence.base.common.IBaseMapper;
import persistence.cadre.CadreAdLogMapper;
import persistence.cadre.CadreAdminLevelMapper;
import persistence.cadre.CadreBookMapper;
import persistence.cadre.CadreCompanyMapper;
import persistence.cadre.CadreCourseMapper;
import persistence.cadre.CadreEduMapper;
import persistence.cadre.CadreFamilyAbroadMapper;
import persistence.cadre.CadreFamilyMapper;
import persistence.cadre.CadreInfoCheckMapper;
import persistence.cadre.CadreInfoMapper;
import persistence.cadre.CadreLeaderMapper;
import persistence.cadre.CadreLeaderUnitMapper;
import persistence.cadre.CadreMapper;
import persistence.cadre.CadrePaperMapper;
import persistence.cadre.CadreParttimeMapper;
import persistence.cadre.CadrePartyMapper;
import persistence.cadre.CadrePostAdminMapper;
import persistence.cadre.CadrePostMapper;
import persistence.cadre.CadrePostProMapper;
import persistence.cadre.CadrePostWorkMapper;
import persistence.cadre.CadreReportMapper;
import persistence.cadre.CadreResearchMapper;
import persistence.cadre.CadreRewardMapper;
import persistence.cadre.CadreStatHistoryMapper;
import persistence.cadre.CadreTrainMapper;
import persistence.cadre.CadreTutorMapper;
import persistence.cadre.CadreUnderEduMapper;
import persistence.cadre.CadreViewMapper;
import persistence.cadre.CadreWorkMapper;
import persistence.cadre.common.ICadreMapper;
import persistence.cadre.common.StatCadreMapper;
import persistence.cadreInspect.CadreInspectMapper;
import persistence.cadreInspect.CadreInspectViewMapper;
import persistence.cadreReserve.CadreReserveMapper;
import persistence.cadreReserve.CadreReserveOriginMapper;
import persistence.cadreReserve.CadreReserveViewMapper;
import persistence.cet.CetColumnCourseMapper;
import persistence.cet.CetColumnCourseViewMapper;
import persistence.cet.CetColumnMapper;
import persistence.cet.CetColumnViewMapper;
import persistence.cet.CetCourseFileMapper;
import persistence.cet.CetCourseItemMapper;
import persistence.cet.CetCourseMapper;
import persistence.cet.CetDiscussGroupMapper;
import persistence.cet.CetDiscussGroupObjMapper;
import persistence.cet.CetDiscussMapper;
import persistence.cet.CetExpertMapper;
import persistence.cet.CetExpertViewMapper;
import persistence.cet.CetPartyMapper;
import persistence.cet.CetPartySchoolMapper;
import persistence.cet.CetPartySchoolViewMapper;
import persistence.cet.CetPartyViewMapper;
import persistence.cet.CetPlanCourseMapper;
import persistence.cet.CetPlanCourseObjMapper;
import persistence.cet.CetPlanCourseObjResultMapper;
import persistence.cet.CetProjectMapper;
import persistence.cet.CetProjectObjCadreViewMapper;
import persistence.cet.CetProjectObjMapper;
import persistence.cet.CetProjectObjViewMapper;
import persistence.cet.CetProjectPlanMapper;
import persistence.cet.CetProjectTraineeTypeMapper;
import persistence.cet.CetProjectTypeMapper;
import persistence.cet.CetProjectViewMapper;
import persistence.cet.CetShortMsgMapper;
import persistence.cet.CetTrainCourseFileMapper;
import persistence.cet.CetTrainCourseMapper;
import persistence.cet.CetTrainCourseStatViewMapper;
import persistence.cet.CetTrainCourseViewMapper;
import persistence.cet.CetTrainEvaNormMapper;
import persistence.cet.CetTrainEvaRankMapper;
import persistence.cet.CetTrainEvaResultMapper;
import persistence.cet.CetTrainEvaTableMapper;
import persistence.cet.CetTrainInspectorCourseMapper;
import persistence.cet.CetTrainInspectorMapper;
import persistence.cet.CetTrainInspectorViewMapper;
import persistence.cet.CetTrainMapper;
import persistence.cet.CetTrainViewMapper;
import persistence.cet.CetTraineeCadreViewMapper;
import persistence.cet.CetTraineeCourseCadreViewMapper;
import persistence.cet.CetTraineeCourseMapper;
import persistence.cet.CetTraineeCourseViewMapper;
import persistence.cet.CetTraineeMapper;
import persistence.cet.CetTraineeTypeMapper;
import persistence.cet.CetTraineeViewMapper;
import persistence.cet.CetUnitMapper;
import persistence.cet.CetUnitViewMapper;
import persistence.cet.common.ICetMapper;
import persistence.cis.CisEvaluateMapper;
import persistence.cis.CisInspectObjMapper;
import persistence.cis.CisInspectObjViewMapper;
import persistence.cis.CisInspectorMapper;
import persistence.cis.CisInspectorViewMapper;
import persistence.cis.CisObjInspectorMapper;
import persistence.cis.CisObjUnitMapper;
import persistence.cis.common.ICisMapper;
import persistence.cla.ClaAdditionalPostMapper;
import persistence.cla.ClaAdditionalPostViewMapper;
import persistence.cla.ClaApplicatCadreMapper;
import persistence.cla.ClaApplicatTypeMapper;
import persistence.cla.ClaApplyFileMapper;
import persistence.cla.ClaApplyMapper;
import persistence.cla.ClaApplyModifyMapper;
import persistence.cla.ClaApprovalLogMapper;
import persistence.cla.ClaApprovalOrderMapper;
import persistence.cla.ClaApproverBlackListMapper;
import persistence.cla.ClaApproverMapper;
import persistence.cla.ClaApproverTypeMapper;
import persistence.cla.common.IClaMapper;
import persistence.common.CommonMapper;
import persistence.common.IExtMapper;
import persistence.common.IPartyMapper;
import persistence.common.IPropertyMapper;
import persistence.common.ISysMapper;
import persistence.common.IUnitMapper;
import persistence.cpc.CpcAllocationMapper;
import persistence.cpc.common.ICpcMapper;
import persistence.crp.CrpRecordMapper;
import persistence.crs.CrsApplicantAdjustMapper;
import persistence.crs.CrsApplicantAdjustViewMapper;
import persistence.crs.CrsApplicantCheckMapper;
import persistence.crs.CrsApplicantMapper;
import persistence.crs.CrsApplicantStatViewMapper;
import persistence.crs.CrsApplicantViewMapper;
import persistence.crs.CrsApplyRuleMapper;
import persistence.crs.CrsApplyUserMapper;
import persistence.crs.CrsCandidateMapper;
import persistence.crs.CrsCandidateViewMapper;
import persistence.crs.CrsExpertMapper;
import persistence.crs.CrsExpertViewMapper;
import persistence.crs.CrsPostExpertMapper;
import persistence.crs.CrsPostFileMapper;
import persistence.crs.CrsPostMapper;
import persistence.crs.CrsPostRequireMapper;
import persistence.crs.CrsRequireRuleMapper;
import persistence.crs.CrsRuleItemMapper;
import persistence.crs.CrsShortMsgMapper;
import persistence.crs.CrsTemplateMapper;
import persistence.crs.common.ICrsMapper;
import persistence.dispatch.DispatchCadreMapper;
import persistence.dispatch.DispatchCadreRelateMapper;
import persistence.dispatch.DispatchCadreViewMapper;
import persistence.dispatch.DispatchMapper;
import persistence.dispatch.DispatchTypeMapper;
import persistence.dispatch.DispatchUnitMapper;
import persistence.dispatch.DispatchUnitRelateMapper;
import persistence.dispatch.DispatchViewMapper;
import persistence.dispatch.DispatchWorkFileAuthMapper;
import persistence.dispatch.DispatchWorkFileMapper;
import persistence.dispatch.common.IDispatchMapper;
import persistence.ext.ExtAbroadMapper;
import persistence.ext.ExtBksMapper;
import persistence.ext.ExtJzgMapper;
import persistence.ext.ExtJzgSalaryMapper;
import persistence.ext.ExtRetireSalaryMapper;
import persistence.ext.ExtYjsMapper;
import persistence.member.ApplyApprovalLogMapper;
import persistence.member.ApplyOpenTimeMapper;
import persistence.member.MemberAbroadMapper;
import persistence.member.MemberAbroadViewMapper;
import persistence.member.MemberApplyMapper;
import persistence.member.MemberApplyViewMapper;
import persistence.member.MemberInMapper;
import persistence.member.MemberInModifyMapper;
import persistence.member.MemberInflowMapper;
import persistence.member.MemberMapper;
import persistence.member.MemberModifyMapper;
import persistence.member.MemberOutMapper;
import persistence.member.MemberOutModifyMapper;
import persistence.member.MemberOutViewMapper;
import persistence.member.MemberOutflowMapper;
import persistence.member.MemberOutflowViewMapper;
import persistence.member.MemberQuitMapper;
import persistence.member.MemberReturnMapper;
import persistence.member.MemberStayMapper;
import persistence.member.MemberStayViewMapper;
import persistence.member.MemberStudentMapper;
import persistence.member.MemberTeacherMapper;
import persistence.member.MemberTransferMapper;
import persistence.member.common.IMemberMapper;
import persistence.member.common.StatMemberMapper;
import persistence.modify.ModifyBaseApplyMapper;
import persistence.modify.ModifyBaseItemMapper;
import persistence.modify.ModifyCadreAuthMapper;
import persistence.modify.ModifyTableApplyMapper;
import persistence.modify.common.IModifyMapper;
import persistence.oa.OaTaskFileMapper;
import persistence.oa.OaTaskMapper;
import persistence.oa.OaTaskMsgMapper;
import persistence.oa.OaTaskRemindMapper;
import persistence.oa.OaTaskUserFileMapper;
import persistence.oa.OaTaskUserMapper;
import persistence.oa.OaTaskUserViewMapper;
import persistence.oa.OaTaskViewMapper;
import persistence.party.BranchMapper;
import persistence.party.BranchMemberGroupMapper;
import persistence.party.BranchMemberGroupViewMapper;
import persistence.party.BranchMemberMapper;
import persistence.party.BranchTransferLogMapper;
import persistence.party.BranchViewMapper;
import persistence.party.EnterApplyMapper;
import persistence.party.OrgAdminMapper;
import persistence.party.PartyMapper;
import persistence.party.PartyMemberGroupMapper;
import persistence.party.PartyMemberGroupViewMapper;
import persistence.party.PartyMemberMapper;
import persistence.party.PartyMemberViewMapper;
import persistence.party.PartyViewMapper;
import persistence.party.RetireApplyMapper;
import persistence.partySchool.PartySchoolMapper;
import persistence.pcs.PcsAdminMapper;
import persistence.pcs.PcsAdminReportMapper;
import persistence.pcs.PcsCandidateChosenMapper;
import persistence.pcs.PcsCandidateMapper;
import persistence.pcs.PcsCandidateViewMapper;
import persistence.pcs.PcsConfigMapper;
import persistence.pcs.PcsExcludeBranchMapper;
import persistence.pcs.PcsIssueMapper;
import persistence.pcs.PcsPartyViewMapper;
import persistence.pcs.PcsPrAllocateMapper;
import persistence.pcs.PcsPrCandidateMapper;
import persistence.pcs.PcsPrCandidateViewMapper;
import persistence.pcs.PcsPrFileMapper;
import persistence.pcs.PcsPrFileTemplateMapper;
import persistence.pcs.PcsPrRecommendMapper;
import persistence.pcs.PcsProposalFileMapper;
import persistence.pcs.PcsProposalMapper;
import persistence.pcs.PcsProposalSeconderMapper;
import persistence.pcs.PcsProposalViewMapper;
import persistence.pcs.PcsRecommendMapper;
import persistence.pcs.PcsVoteCandidateMapper;
import persistence.pcs.PcsVoteGroupMapper;
import persistence.pcs.PcsVoteMemberMapper;
import persistence.pcs.common.IPcsMapper;
import persistence.pmd.PmdBranchAdminMapper;
import persistence.pmd.PmdBranchMapper;
import persistence.pmd.PmdBranchViewMapper;
import persistence.pmd.PmdConfigMemberMapper;
import persistence.pmd.PmdConfigMemberTypeMapper;
import persistence.pmd.PmdConfigResetMapper;
import persistence.pmd.PmdMemberMapper;
import persistence.pmd.PmdMemberPayMapper;
import persistence.pmd.PmdMemberPayViewMapper;
import persistence.pmd.PmdMonthMapper;
import persistence.pmd.PmdNormMapper;
import persistence.pmd.PmdNormValueLogMapper;
import persistence.pmd.PmdNormValueMapper;
import persistence.pmd.PmdNotifyCampuscardMapper;
import persistence.pmd.PmdNotifyWszfMapper;
import persistence.pmd.PmdOrderCampuscardMapper;
import persistence.pmd.PmdOrderItemMapper;
import persistence.pmd.PmdPartyAdminMapper;
import persistence.pmd.PmdPartyMapper;
import persistence.pmd.PmdPartyViewMapper;
import persistence.pmd.PmdPayBranchMapper;
import persistence.pmd.PmdPayBranchViewMapper;
import persistence.pmd.PmdPayPartyMapper;
import persistence.pmd.PmdPayPartyViewMapper;
import persistence.pmd.PmdSpecialUserMapper;
import persistence.pmd.common.IPmdMapper;
import persistence.sc.IScMapper;
import persistence.sc.scAd.ScAdArchiveMapper;
import persistence.sc.scAd.ScAdArchiveViewMapper;
import persistence.sc.scAd.ScAdArchiveVoteMapper;
import persistence.sc.scAd.ScAdUseMapper;
import persistence.sc.scCommittee.ScCommitteeMapper;
import persistence.sc.scCommittee.ScCommitteeMemberMapper;
import persistence.sc.scCommittee.ScCommitteeMemberViewMapper;
import persistence.sc.scCommittee.ScCommitteeOtherVoteMapper;
import persistence.sc.scCommittee.ScCommitteeOtherVoteViewMapper;
import persistence.sc.scCommittee.ScCommitteeTopicMapper;
import persistence.sc.scCommittee.ScCommitteeTopicViewMapper;
import persistence.sc.scCommittee.ScCommitteeViewMapper;
import persistence.sc.scCommittee.ScCommitteeVoteMapper;
import persistence.sc.scCommittee.ScCommitteeVoteViewMapper;
import persistence.sc.scDispatch.ScDispatchCommitteeMapper;
import persistence.sc.scDispatch.ScDispatchMapper;
import persistence.sc.scDispatch.ScDispatchUserMapper;
import persistence.sc.scDispatch.ScDispatchViewMapper;
import persistence.sc.scGroup.ScGroupFileMapper;
import persistence.sc.scGroup.ScGroupMapper;
import persistence.sc.scGroup.ScGroupMemberMapper;
import persistence.sc.scGroup.ScGroupMemberViewMapper;
import persistence.sc.scGroup.ScGroupParticipantMapper;
import persistence.sc.scGroup.ScGroupTopicMapper;
import persistence.sc.scGroup.ScGroupTopicUnitMapper;
import persistence.sc.scGroup.ScGroupTopicViewMapper;
import persistence.sc.scLetter.ScLetterItemMapper;
import persistence.sc.scLetter.ScLetterItemViewMapper;
import persistence.sc.scLetter.ScLetterMapper;
import persistence.sc.scLetter.ScLetterReplyItemMapper;
import persistence.sc.scLetter.ScLetterReplyItemViewMapper;
import persistence.sc.scLetter.ScLetterReplyMapper;
import persistence.sc.scLetter.ScLetterReplyViewMapper;
import persistence.sc.scLetter.ScLetterViewMapper;
import persistence.sc.scMatter.ScMatterAccessItemMapper;
import persistence.sc.scMatter.ScMatterAccessItemViewMapper;
import persistence.sc.scMatter.ScMatterAccessMapper;
import persistence.sc.scMatter.ScMatterCheckItemMapper;
import persistence.sc.scMatter.ScMatterCheckItemViewMapper;
import persistence.sc.scMatter.ScMatterCheckMapper;
import persistence.sc.scMatter.ScMatterCheckViewMapper;
import persistence.sc.scMatter.ScMatterItemMapper;
import persistence.sc.scMatter.ScMatterItemViewMapper;
import persistence.sc.scMatter.ScMatterMapper;
import persistence.sc.scMatter.ScMatterTransferMapper;
import persistence.sc.scMatter.ScMatterUserViewMapper;
import persistence.sc.scPublic.ScPublicMapper;
import persistence.sc.scPublic.ScPublicUserMapper;
import persistence.sc.scPublic.ScPublicViewMapper;
import persistence.sys.AttachFileMapper;
import persistence.sys.FeedbackMapper;
import persistence.sys.HtmlFragmentMapper;
import persistence.sys.StudentInfoMapper;
import persistence.sys.SysApprovalLogMapper;
import persistence.sys.SysConfigLoginMsgMapper;
import persistence.sys.SysConfigMapper;
import persistence.sys.SysLogMapper;
import persistence.sys.SysLoginLogMapper;
import persistence.sys.SysOnlineStaticMapper;
import persistence.sys.SysResourceMapper;
import persistence.sys.SysRoleMapper;
import persistence.sys.SysUserInfoMapper;
import persistence.sys.SysUserMapper;
import persistence.sys.SysUserRegMapper;
import persistence.sys.SysSyncMapper;
import persistence.sys.SysUserViewMapper;
import persistence.sys.TeacherInfoMapper;
import persistence.unit.HistoryUnitMapper;
import persistence.unit.UnitAdminGroupMapper;
import persistence.unit.UnitAdminMapper;
import persistence.unit.UnitCadreTransferGroupMapper;
import persistence.unit.UnitCadreTransferMapper;
import persistence.unit.UnitMapper;
import persistence.unit.UnitTransferMapper;
import persistence.verify.VerifyAgeMapper;
import persistence.verify.VerifyWorkTimeMapper;
import shiro.ShiroHelper;
import sys.helper.PartyHelper;

import java.io.File;

public class BaseMapper {

    /**
     * 干部教育培训
     */
    @Autowired(required = false)
    protected ICetMapper iCetMapper;
    @Autowired(required = false)
    protected CetUnitMapper cetUnitMapper;
    @Autowired(required = false)
    protected CetPartyMapper cetPartyMapper;
    @Autowired(required = false)
    protected CetPartySchoolMapper cetPartySchoolMapper;
    @Autowired(required = false)
    protected CetUnitViewMapper cetUnitViewMapper;
    @Autowired(required = false)
    protected CetPartyViewMapper cetPartyViewMapper;
    @Autowired(required = false)
    protected CetPartySchoolViewMapper cetPartySchoolViewMapper;
    @Autowired(required = false)
    protected CetProjectMapper cetProjectMapper;
    @Autowired(required = false)
    protected CetProjectViewMapper cetProjectViewMapper;
    @Autowired(required = false)
    protected CetProjectObjMapper cetProjectObjMapper;
    @Autowired(required = false)
    protected CetProjectObjViewMapper cetProjectObjViewMapper;
    @Autowired(required = false)
    protected CetProjectObjCadreViewMapper cetProjectObjCadreViewMapper;
    @Autowired(required = false)
    protected CetDiscussMapper cetDiscussMapper;
    @Autowired(required = false)
    protected CetDiscussGroupMapper cetDiscussGroupMapper;
    @Autowired(required = false)
    protected CetDiscussGroupObjMapper cetDiscussGroupObjMapper;
    @Autowired(required = false)
    protected CetProjectPlanMapper cetProjectPlanMapper;
    @Autowired(required = false)
    protected CetPlanCourseMapper cetPlanCourseMapper;
    @Autowired(required = false)
    protected CetPlanCourseObjMapper cetPlanCourseObjMapper;
    @Autowired(required = false)
    protected CetPlanCourseObjResultMapper cetPlanCourseObjResultMapper;
    @Autowired(required = false)
    protected CetCourseMapper cetCourseMapper;
    @Autowired(required = false)
    protected CetCourseFileMapper cetCourseFileMapper;
    @Autowired(required = false)
    protected CetCourseItemMapper cetCourseItemMapper;
    @Autowired(required = false)
    protected CetProjectTypeMapper cetProjectTypeMapper;
    @Autowired(required = false)
    protected CetExpertMapper cetExpertMapper;
    @Autowired(required = false)
    protected CetExpertViewMapper cetExpertViewMapper;
    @Autowired(required = false)
    protected CetColumnMapper cetColumnMapper;
    @Autowired(required = false)
    protected CetColumnViewMapper cetColumnViewMapper;
    @Autowired(required = false)
    protected CetColumnCourseMapper cetColumnCourseMapper;
    @Autowired(required = false)
    protected CetColumnCourseViewMapper cetColumnCourseViewMapper;
    @Autowired(required = false)
    protected CetTrainMapper cetTrainMapper;
    @Autowired(required = false)
    protected CetTrainViewMapper cetTrainViewMapper;
    @Autowired(required = false)
    protected CetTrainCourseMapper cetTrainCourseMapper;
    @Autowired(required = false)
    protected CetTrainCourseFileMapper cetTrainCourseFileMapper;
    @Autowired(required = false)
    protected CetTrainCourseViewMapper cetTrainCourseViewMapper;
    @Autowired(required = false)
    protected CetTrainCourseStatViewMapper cetTrainCourseStatViewMapper;
    @Autowired(required = false)
    protected CetTraineeMapper cetTraineeMapper;
    @Autowired(required = false)
    protected CetTraineeViewMapper cetTraineeViewMapper;
    @Autowired(required = false)
    protected CetTraineeCadreViewMapper cetTraineeCadreViewMapper;
    @Autowired(required = false)
    protected CetTraineeCourseViewMapper cetTraineeCourseViewMapper;
    @Autowired(required = false)
    protected CetTraineeCourseCadreViewMapper cetTraineeCourseCadreViewMapper;
    @Autowired(required = false)
    protected CetTraineeCourseMapper cetTraineeCourseMapper;
    @Autowired(required = false)
    protected CetTraineeTypeMapper cetTraineeTypeMapper;
    @Autowired(required = false)
    protected CetProjectTraineeTypeMapper cetProjectTraineeTypeMapper;
    @Autowired(required = false)
    protected CetShortMsgMapper cetShortMsgMapper;
    @Autowired(required = false)
    protected CetTrainEvaNormMapper cetTrainEvaNormMapper;
    @Autowired(required = false)
    protected CetTrainEvaRankMapper cetTrainEvaRankMapper;
    @Autowired(required = false)
    protected CetTrainEvaTableMapper cetTrainEvaTableMapper;
    @Autowired(required = false)
    protected CetTrainEvaResultMapper cetTrainEvaResultMapper;
    @Autowired(required = false)
    protected CetTrainInspectorMapper cetTrainInspectorMapper;
    @Autowired(required = false)
    protected CetTrainInspectorViewMapper cetTrainInspectorViewMapper;
    @Autowired(required = false)
    protected CetTrainInspectorCourseMapper cetTrainInspectorCourseMapper;

    /**
     * 干部选拔-干部任免审批表
     */
    @Autowired(required = false)
    protected ScAdArchiveMapper scAdArchiveMapper;
    @Autowired(required = false)
    protected ScAdArchiveViewMapper scAdArchiveViewMapper;
    @Autowired(required = false)
    protected ScAdArchiveVoteMapper scAdArchiveVoteMapper;
    @Autowired(required = false)
    protected ScAdUseMapper scAdUseMapper;

    /**
     * 干部选拔-干部起草文件
     */
    @Autowired(required = false)
    protected ScDispatchMapper scDispatchMapper;
    @Autowired(required = false)
    protected ScDispatchViewMapper scDispatchViewMapper;
    @Autowired(required = false)
    protected ScDispatchUserMapper scDispatchUserMapper;
    @Autowired(required = false)
    protected ScDispatchCommitteeMapper scDispatchCommitteeMapper;
    /**
     * 干部选拔-干部任前公示
     */
    @Autowired(required = false)
    protected ScPublicMapper scPublicMapper;
    @Autowired(required = false)
    protected ScPublicViewMapper scPublicViewMapper;
    @Autowired(required = false)
    protected ScPublicUserMapper scPublicUserMapper;

    /**
     * 干部选拔-干部小组会议题
     */
    @Autowired(required = false)
    protected ScGroupMapper scGroupMapper;
    @Autowired(required = false)
    protected ScGroupFileMapper scGroupFileMapper;
    @Autowired(required = false)
    protected ScGroupMemberMapper scGroupMemberMapper;
    @Autowired(required = false)
    protected ScGroupMemberViewMapper scGroupMemberViewMapper;
    @Autowired(required = false)
    protected ScGroupParticipantMapper scGroupParticipantMapper;
    @Autowired(required = false)
    protected ScGroupTopicMapper scGroupTopicMapper;
    @Autowired(required = false)
    protected ScGroupTopicViewMapper scGroupTopicViewMapper;
    @Autowired(required = false)
    protected ScGroupTopicUnitMapper scGroupTopicUnitMapper;

    /**
     * 干部选拔-党委常委会议题
     */
    @Autowired(required = false)
    protected ScCommitteeMapper scCommitteeMapper;
    @Autowired(required = false)
    protected ScCommitteeViewMapper scCommitteeViewMapper;
    @Autowired(required = false)
    protected ScCommitteeMemberMapper scCommitteeMemberMapper;
    @Autowired(required = false)
    protected ScCommitteeMemberViewMapper scCommitteeMemberViewMapper;
    @Autowired(required = false)
    protected ScCommitteeTopicMapper scCommitteeTopicMapper;
    @Autowired(required = false)
    protected ScCommitteeTopicViewMapper scCommitteeTopicViewMapper;
    @Autowired(required = false)
    protected ScCommitteeVoteMapper scCommitteeVoteMapper;
    @Autowired(required = false)
    protected ScCommitteeVoteViewMapper scCommitteeVoteViewMapper;
    @Autowired(required = false)
    protected ScCommitteeOtherVoteMapper scCommitteeOtherVoteMapper;
    @Autowired(required = false)
    protected ScCommitteeOtherVoteViewMapper scCommitteeOtherVoteViewMapper;

    /**
     * 干部选拔-纪委函询管理
     */
    @Autowired(required = false)
    protected ScLetterMapper scLetterMapper;
    @Autowired(required = false)
    protected ScLetterViewMapper scLetterViewMapper;
    @Autowired(required = false)
    protected ScLetterItemMapper scLetterItemMapper;
    @Autowired(required = false)
    protected ScLetterItemViewMapper scLetterItemViewMapper;
    @Autowired(required = false)
    protected ScLetterReplyMapper scLetterReplyMapper;
    @Autowired(required = false)
    protected ScLetterReplyViewMapper scLetterReplyViewMapper;
    @Autowired(required = false)
    protected ScLetterReplyItemMapper scLetterReplyItemMapper;
    @Autowired(required = false)
    protected ScLetterReplyItemViewMapper scLetterReplyItemViewMapper;

    /**
     * 干部选拔-个人有关事项
     */
    @Autowired(required = false)
    protected ScMatterMapper scMatterMapper;
    @Autowired(required = false)
    protected ScMatterItemMapper scMatterItemMapper;
    @Autowired(required = false)
    protected ScMatterItemViewMapper scMatterItemViewMapper;
    @Autowired(required = false)
    protected ScMatterUserViewMapper scMatterUserViewMapper;
    @Autowired(required = false)
    protected ScMatterAccessMapper scMatterAccessMapper;
    @Autowired(required = false)
    protected ScMatterAccessItemMapper scMatterAccessItemMapper;
    @Autowired(required = false)
    protected ScMatterAccessItemViewMapper scMatterAccessItemViewMapper;
    @Autowired(required = false)
    protected ScMatterCheckMapper scMatterCheckMapper;
    @Autowired(required = false)
    protected ScMatterCheckViewMapper scMatterCheckViewMapper;
    @Autowired(required = false)
    protected ScMatterCheckItemMapper scMatterCheckItemMapper;
    @Autowired(required = false)
    protected ScMatterCheckItemViewMapper scMatterCheckItemViewMapper;
    @Autowired(required = false)
    protected ScMatterTransferMapper scMatterTransferMapper;
    @Autowired(required = false)
    protected IScMapper iScMapper;

    /**
     * 党费
     */
    @Autowired(required = false)
    protected PmdPayPartyMapper pmdPayPartyMapper;
    @Autowired(required = false)
    protected PmdPayBranchMapper pmdPayBranchMapper;
    @Autowired(required = false)
    protected PmdPayPartyViewMapper pmdPayPartyViewMapper;
    @Autowired(required = false)
    protected PmdPayBranchViewMapper pmdPayBranchViewMapper;
    @Autowired(required = false)
    protected PmdMonthMapper pmdMonthMapper;
    @Autowired(required = false)
    protected IPmdMapper iPmdMapper;
    @Autowired(required = false)
    protected PmdMemberMapper pmdMemberMapper;
    @Autowired(required = false)
    protected PmdMemberPayMapper pmdMemberPayMapper;
    @Autowired(required = false)
    protected PmdMemberPayViewMapper pmdMemberPayViewMapper;
    @Autowired(required = false)
    protected PmdOrderCampuscardMapper pmdOrderCampuscardMapper;
    @Autowired(required = false)
    protected PmdOrderItemMapper pmdOrderItemMapper;
    @Autowired(required = false)
    protected PmdNotifyWszfMapper pmdNotifyWszfMapper;
    @Autowired(required = false)
    protected PmdNotifyCampuscardMapper pmdNotifyCampuscardMapper;
    @Autowired(required = false)
    protected PmdPartyMapper pmdPartyMapper;
    @Autowired(required = false)
    protected PmdPartyViewMapper pmdPartyViewMapper;
    @Autowired(required = false)
    protected PmdBranchMapper pmdBranchMapper;
    @Autowired(required = false)
    protected PmdBranchViewMapper pmdBranchViewMapper;
    @Autowired(required = false)
    protected PmdPartyAdminMapper pmdPartyAdminMapper;
    @Autowired(required = false)
    protected PmdBranchAdminMapper pmdBranchAdminMapper;
    @Autowired(required = false)
    protected PmdNormMapper pmdNormMapper;
    @Autowired(required = false)
    protected PmdNormValueMapper pmdNormValueMapper;
    @Autowired(required = false)
    protected PmdNormValueLogMapper pmdNormValueLogMapper;
    @Autowired(required = false)
    protected PmdSpecialUserMapper pmdSpecialUserMapper;
    @Autowired(required = false)
    protected PmdConfigMemberTypeMapper pmdConfigMemberTypeMapper;
    @Autowired(required = false)
    protected PmdConfigMemberMapper pmdConfigMemberMapper;
    @Autowired(required = false)
    protected PmdConfigResetMapper pmdConfigResetMapper;

    /**
     * 协同办公
     */
    @Autowired(required = false)
    protected OaTaskMapper oaTaskMapper;
    @Autowired(required = false)
    protected OaTaskViewMapper oaTaskViewMapper;
    @Autowired(required = false)
    protected OaTaskFileMapper oaTaskFileMapper;
    @Autowired(required = false)
    protected OaTaskMsgMapper oaTaskMsgMapper;
    @Autowired(required = false)
    protected OaTaskRemindMapper oaTaskRemindMapper;
    @Autowired(required = false)
    protected OaTaskUserMapper oaTaskUserMapper;
    @Autowired(required = false)
    protected OaTaskUserViewMapper oaTaskUserViewMapper;
    @Autowired(required = false)
    protected OaTaskUserFileMapper oaTaskUserFileMapper;

    /**
     * 干部请假审批
     */
    @Autowired(required = false)
    protected ClaApplicatCadreMapper claApplicatCadreMapper;
    @Autowired(required = false)
    protected ClaApplicatTypeMapper claApplicatTypeMapper;
    @Autowired(required = false)
    protected ClaApprovalOrderMapper claApprovalOrderMapper;
    @Autowired(required = false)
    protected ClaApproverMapper claApproverMapper;
    @Autowired(required = false)
    protected ClaApproverBlackListMapper claApproverBlackListMapper;
    @Autowired(required = false)
    protected ClaApproverTypeMapper claApproverTypeMapper;
    @Autowired(required = false)
    protected ClaApplyMapper claApplyMapper;
    @Autowired(required = false)
    protected ClaApplyModifyMapper claApplyModifyMapper;
    @Autowired(required = false)
    protected ClaApplyFileMapper claApplyFileMapper;
    @Autowired(required = false)
    protected ClaApprovalLogMapper claApprovalLogMapper;
    @Autowired(required = false)
    protected ClaAdditionalPostMapper claAdditionalPostMapper;
    @Autowired(required = false)
    protected ClaAdditionalPostViewMapper claAdditionalPostViewMapper;

    /**
     * 因私出国境
     */
    @Autowired(required = false)
    protected ApplicatCadreMapper applicatCadreMapper;
    @Autowired(required = false)
    protected ApplicatTypeMapper applicatTypeMapper;
    @Autowired(required = false)
    protected ApprovalOrderMapper approvalOrderMapper;
    @Autowired(required = false)
    protected ApproverMapper approverMapper;
    @Autowired(required = false)
    protected ApproverBlackListMapper approverBlackListMapper;
    @Autowired(required = false)
    protected ApproverTypeMapper approverTypeMapper;
    @Autowired(required = false)
    protected PassportDrawFileMapper passportDrawFileMapper;
    @Autowired(required = false)
    protected PassportDrawMapper passportDrawMapper;
    @Autowired(required = false)
    protected ApplySelfMapper applySelfMapper;
    @Autowired(required = false)
    protected ApplySelfModifyMapper applySelfModifyMapper;
    @Autowired(required = false)
    protected ApplySelfFileMapper applySelfFileMapper;
    @Autowired(required = false)
    protected ApprovalLogMapper approvalLogMapper;
    @Autowired(required = false)
    protected AbroadAdditionalPostMapper abroadAdditionalPostMapper;
    @Autowired(required = false)
    protected AbroadAdditionalPostViewMapper abroadAdditionalPostViewMapper;
    @Autowired(required = false)
    protected PassportMapper passportMapper;
    @Autowired(required = false)
    protected SafeBoxMapper safeBoxMapper;
    @Autowired(required = false)
    protected PassportApplyMapper passportApplyMapper;
    @Autowired(required = false)
    protected PassportApplyViewMapper passportApplyViewMapper;
    @Autowired(required = false)
    protected TaiwanRecordMapper taiwanRecordMapper;

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
    protected StudentInfoMapper studentInfoMapper;
    @Autowired(required = false)
    protected MemberStudentMapper memberStudentMapper;
    @Autowired(required = false)
    protected TeacherInfoMapper teacherInfoMapper;
    @Autowired(required = false)
    protected MemberTeacherMapper memberTeacherMapper;
    @Autowired(required = false)
    protected MemberMapper memberMapper;
    @Autowired(required = false)
    protected MemberModifyMapper memberModifyMapper;
    @Autowired(required = false)
    protected ApplyOpenTimeMapper applyOpenTimeMapper;
    @Autowired(required = false)
    protected MemberApplyMapper memberApplyMapper;
    @Autowired(required = false)
    protected MemberApplyViewMapper memberApplyViewMapper;

    /**
     * 组织机构
     */
    @Autowired(required = false)
    protected BranchMemberGroupMapper branchMemberGroupMapper;
    @Autowired(required = false)
    protected BranchMemberGroupViewMapper branchMemberGroupViewMapper;
    @Autowired(required = false)
    protected BranchMemberMapper branchMemberMapper;
    @Autowired(required = false)
    protected PartyMapper partyMapper;
    @Autowired(required = false)
    protected PartyViewMapper partyViewMapper;
    @Autowired(required = false)
    protected PartyMemberMapper partyMemberMapper;
    @Autowired(required = false)
    protected PartyMemberViewMapper partyMemberViewMapper;
    @Autowired(required = false)
    protected PartyMemberGroupMapper partyMemberGroupMapper;
    @Autowired(required = false)
    protected PartyMemberGroupViewMapper partyMemberGroupViewMapper;
    @Autowired(required = false)
    protected BranchMapper branchMapper;
    @Autowired(required = false)
    protected BranchTransferLogMapper branchTransferLogMapper;
    @Autowired(required = false)
    protected BranchViewMapper branchViewMapper;
    @Autowired(required = false)
    protected OrgAdminMapper orgAdminMapper;

    @Autowired(required = false)
    protected UnitAdminGroupMapper unitAdminGroupMapper;
    @Autowired(required = false)
    protected UnitAdminMapper unitAdminMapper;

    /**
     * 干部库
     */
    @Autowired(required = false)
    protected CadreInfoMapper cadreInfoMapper;
    @Autowired(required = false)
    protected CadreInfoCheckMapper cadreInfoCheckMapper;
    @Autowired(required = false)
    protected CadreFamilyAbroadMapper cadreFamilyAbroadMapper;
    @Autowired(required = false)
    protected CadreFamilyMapper cadreFamilyMapper;
    @Autowired(required = false)
    protected CadreCompanyMapper cadreCompanyMapper;
    @Autowired(required = false)
    protected CadreParttimeMapper cadreParttimeMapper;
    @Autowired(required = false)
    protected CadreTrainMapper cadreTrainMapper;
    @Autowired(required = false)
    protected CadreRewardMapper cadreRewardMapper;
    @Autowired(required = false)
    protected CadrePaperMapper cadrePaperMapper;
    @Autowired(required = false)
    protected CadreResearchMapper cadreResearchMapper;
    @Autowired(required = false)
    protected CadreBookMapper cadreBookMapper;
    @Autowired(required = false)
    protected CadreCourseMapper cadreCourseMapper;
    @Autowired(required = false)
    protected CadrePostMapper cadrePostMapper;
    @Autowired(required = false)
    protected CadrePostProMapper cadrePostProMapper;
    @Autowired(required = false)
    protected CadrePostAdminMapper cadrePostAdminMapper;
    @Autowired(required = false)
    protected CadrePostWorkMapper cadrePostWorkMapper;
    @Autowired(required = false)
    protected CadreAdminLevelMapper cadreAdminLevelMapper;
    @Autowired(required = false)
    protected CadreWorkMapper cadreWorkMapper;
    @Autowired(required = false)
    protected CadreEduMapper cadreEduMapper;
    @Autowired(required = false)
    protected CadreUnderEduMapper cadreUnderEduMapper;
    @Autowired(required = false)
    protected CadreTutorMapper cadreTutorMapper;
    @Autowired(required = false)
    protected CadreReportMapper cadreReportMapper;

    @Autowired(required = false)
    protected CadreLeaderMapper cadreLeaderMapper;
    @Autowired(required = false)
    protected CadreLeaderUnitMapper cadreLeaderUnitMapper;
    @Autowired(required = false)
    protected CadreMapper cadreMapper;
    @Autowired(required = false)
    protected CadreViewMapper cadreViewMapper;
    @Autowired(required = false)
    protected CadrePartyMapper cadrePartyMapper;
    @Autowired(required = false)
    protected CadreAdLogMapper cadreAdLogMapper;
    @Autowired(required = false)
    protected CadreInspectMapper cadreInspectMapper;
    @Autowired(required = false)
    protected CadreInspectViewMapper cadreInspectViewMapper;
    @Autowired(required = false)
    protected CadreReserveMapper cadreReserveMapper;
    @Autowired(required = false)
    protected CadreReserveViewMapper cadreReserveViewMapper;
    @Autowired(required = false)
    protected CadreReserveOriginMapper cadreReserveOriginMapper;
    @Autowired(required = false)
    protected CadreStatHistoryMapper cadreStatHistoryMapper;

    /**
     * 干部考察系统
     */
    @Autowired(required = false)
    protected CisEvaluateMapper cisEvaluateMapper;
    @Autowired(required = false)
    protected CisInspectObjMapper cisInspectObjMapper;
    @Autowired(required = false)
    protected CisInspectObjViewMapper cisInspectObjViewMapper;
    @Autowired(required = false)
    protected CisInspectorMapper cisInspectorMapper;
    @Autowired(required = false)
    protected CisInspectorViewMapper cisInspectorViewMapper;
    @Autowired(required = false)
    protected CisObjInspectorMapper cisObjInspectorMapper;
    @Autowired(required = false)
    protected CisObjUnitMapper cisObjUnitMapper;
    @Autowired(required = false)
    protected ICisMapper iCisMapper;

    @Autowired(required = false)
    protected CrpRecordMapper crpRecordMapper;

    /**
     * 党代会
     */
    @Autowired(required = false)
    protected IPcsMapper iPcsMapper;
    @Autowired(required = false)
    protected PcsExcludeBranchMapper pcsExcludeBranchMapper;
    @Autowired(required = false)
    protected PcsPartyViewMapper pcsPartyViewMapper;
    @Autowired(required = false)
    protected PcsIssueMapper pcsIssueMapper;
    @Autowired(required = false)
    protected PcsAdminMapper pcsAdminMapper;
    @Autowired(required = false)
    protected PcsAdminReportMapper pcsAdminReportMapper;
    @Autowired(required = false)
    protected PcsCandidateMapper pcsCandidateMapper;
    @Autowired(required = false)
    protected PcsCandidateViewMapper pcsCandidateViewMapper;
    @Autowired(required = false)
    protected PcsCandidateChosenMapper pcsCandidateChosenMapper;
    @Autowired(required = false)
    protected PcsConfigMapper pcsConfigMapper;
    @Autowired(required = false)
    protected PcsRecommendMapper pcsRecommendMapper;
    @Autowired(required = false)
    protected PcsPrAllocateMapper pcsPrAllocateMapper;
    @Autowired(required = false)
    protected PcsPrRecommendMapper pcsPrRecommendMapper;
    @Autowired(required = false)
    protected PcsPrCandidateMapper pcsPrCandidateMapper;
    @Autowired(required = false)
    protected PcsPrCandidateViewMapper pcsPrCandidateViewMapper;
    @Autowired(required = false)
    protected PcsPrFileTemplateMapper pcsPrFileTemplateMapper;
    @Autowired(required = false)
    protected PcsPrFileMapper pcsPrFileMapper;
    @Autowired(required = false)
    protected PcsProposalMapper pcsProposalMapper;
    @Autowired(required = false)
    protected PcsProposalViewMapper pcsProposalViewMapper;
    @Autowired(required = false)
    protected PcsProposalFileMapper pcsProposalFileMapper;
    @Autowired(required = false)
    protected PcsProposalSeconderMapper pcsProposalSeconderMapper;
    @Autowired(required = false)
    protected PcsVoteGroupMapper pcsVoteGroupMapper;
    @Autowired(required = false)
    protected PcsVoteCandidateMapper pcsVoteCandidateMapper;
    @Autowired(required = false)
    protected PcsVoteMemberMapper pcsVoteMemberMapper;

    /**
     * 干部职数
     */
    @Autowired(required = false)
    protected CpcAllocationMapper cpcAllocationMapper;

    /**
     * 干部招聘
     */
    @Autowired(required = false)
    protected CrsTemplateMapper crsTemplateMapper;
    @Autowired(required = false)
    protected CrsExpertMapper crsExpertMapper;
    @Autowired(required = false)
    protected CrsExpertViewMapper crsExpertViewMapper;
    @Autowired(required = false)
    protected CrsApplicantMapper crsApplicantMapper;
    @Autowired(required = false)
    protected CrsApplicantViewMapper crsApplicantViewMapper;
    @Autowired(required = false)
    protected CrsApplicantStatViewMapper crsApplicantStatViewMapper;
    @Autowired(required = false)
    protected CrsApplicantAdjustMapper crsApplicantAdjustMapper;
    @Autowired(required = false)
    protected CrsApplicantAdjustViewMapper crsApplicantAdjustViewMapper;
    @Autowired(required = false)
    protected CrsApplicantCheckMapper crsApplicantCheckMapper;
    @Autowired(required = false)
    protected CrsPostMapper crsPostMapper;
    @Autowired(required = false)
    protected CrsApplyUserMapper crsApplyUserMapper;
    @Autowired(required = false)
    protected CrsCandidateViewMapper crsCandidateViewMapper;
    @Autowired(required = false)
    protected CrsCandidateMapper crsCandidateMapper;
    @Autowired(required = false)
    protected CrsApplyRuleMapper crsApplyRuleMapper;
    @Autowired(required = false)
    protected CrsShortMsgMapper crsShortMsgMapper;
    @Autowired(required = false)
    protected CrsPostRequireMapper crsPostRequireMapper;
    @Autowired(required = false)
    protected CrsRequireRuleMapper crsRequireRuleMapper;
    @Autowired(required = false)
    protected CrsRuleItemMapper crsRuleItemMapper;
    @Autowired(required = false)
    protected CrsPostExpertMapper crsPostExpertMapper;
    @Autowired(required = false)
    protected CrsPostFileMapper crsPostFileMapper;
    @Autowired(required = false)
    protected ICrsMapper iCrsMapper;

    /**
     * 档案认定
     */
    @Autowired(required = false)
    protected VerifyAgeMapper verifyAgeMapper;
    @Autowired(required = false)
    protected VerifyWorkTimeMapper verifyWorkTimeMapper;

    /**
     * 发文
     */
    @Autowired(required = false)
    protected UnitTransferMapper unitTransferMapper;
    @Autowired(required = false)
    protected UnitCadreTransferGroupMapper unitCadreTransferGroupMapper;
    @Autowired(required = false)
    protected UnitCadreTransferMapper unitCadreTransferMapper;
    @Autowired(required = false)
    protected DispatchMapper dispatchMapper;
    @Autowired(required = false)
    protected DispatchViewMapper dispatchViewMapper;
    @Autowired(required = false)
    protected DispatchTypeMapper dispatchTypeMapper;
    @Autowired(required = false)
    protected DispatchCadreMapper dispatchCadreMapper;
    @Autowired(required = false)
    protected DispatchCadreViewMapper dispatchCadreViewMapper;
    @Autowired(required = false)
    protected DispatchCadreRelateMapper dispatchCadreRelateMapper;
    @Autowired(required = false)
    protected DispatchUnitMapper dispatchUnitMapper;
    @Autowired(required = false)
    protected DispatchUnitRelateMapper dispatchUnitRelateMapper;
    @Autowired(required = false)
    protected DispatchWorkFileMapper dispatchWorkFileMapper;
    @Autowired(required = false)
    protected DispatchWorkFileAuthMapper dispatchWorkFileAuthMapper;


    @Autowired(required = false)
    protected StatCadreMapper statCadreMapper;

    @Autowired(required = false)
    protected HistoryUnitMapper historyUnitMapper;
    @Autowired(required = false)
    protected UnitMapper unitMapper;
    @Autowired(required = false)
    protected PartySchoolMapper partySchoolMapper;

    /**
     * 干部信息修改申请
     */
    @Autowired(required = false)
    protected ModifyCadreAuthMapper modifyCadreAuthMapper;
    @Autowired(required = false)
    protected ModifyBaseApplyMapper modifyBaseApplyMapper;
    @Autowired(required = false)
    protected ModifyBaseItemMapper modifyBaseItemMapper;
    @Autowired(required = false)
    protected ModifyTableApplyMapper modifyTableApplyMapper;

    @Autowired(required = false)
    protected SysConfigMapper sysConfigMapper;
    @Autowired(required = false)
    protected SysConfigLoginMsgMapper sysConfigLoginMsgMapper;
    @Autowired(required = false)
    protected SysApprovalLogMapper sysApprovalLogMapper;
    @Autowired(required = false)
    protected SysSyncMapper sysSyncMapper;
    @Autowired(required = false)
    protected SysLogMapper sysLogMapper;
    @Autowired(required = false)
    protected SysLoginLogMapper sysLoginLogMapper;
    @Autowired(required = false)
    protected SysUserMapper sysUserMapper;
    @Autowired(required = false)
    protected SysUserInfoMapper sysUserInfoMapper;
    @Autowired(required = false)
    protected SysUserViewMapper sysUserViewMapper;
    @Autowired(required = false)
    protected SysUserRegMapper sysUserRegMapper;
    @Autowired(required = false)
    protected SysRoleMapper sysRoleMapper;
    @Autowired(required = false)
    protected SysResourceMapper sysResourceMapper;
    @Autowired(required = false)
    protected AttachFileMapper attachFileMapper;
    @Autowired(required = false)
    protected HtmlFragmentMapper htmlFragmentMapper;
    @Autowired(required = false)
    protected SysOnlineStaticMapper sysOnlineStaticMapper;
    @Autowired(required = false)
    protected FeedbackMapper feedbackMapper;


    @Autowired(required = false)
    protected CommonMapper commonMapper;
    @Autowired(required = false)
    protected IPropertyMapper IPropertyMapper;
    @Autowired(required = false)
    protected IAbroadMapper iAbroadMapper;
    @Autowired(required = false)
    protected IClaMapper iClaMapper;
    @Autowired(required = false)
    protected ICadreMapper iCadreMapper;
    @Autowired(required = false)
    protected ICpcMapper iCpcMapper;
    @Autowired(required = false)
    protected IDispatchMapper iDispatchMapper;
    @Autowired(required = false)
    protected IMemberMapper iMemberMapper;
    @Autowired(required = false)
    protected IModifyMapper iModifyMapper;
    @Autowired(required = false)
    protected IPartyMapper iPartyMapper;
    @Autowired(required = false)
    protected ISysMapper iSysMapper;
    @Autowired(required = false)
    protected IUnitMapper iUnitMapper;
    @Autowired(required = false)
    protected StatMemberMapper statMemberMapper;

    @Autowired(required = false)
    protected IExtMapper iExtMapper;
    @Autowired(required = false)
    protected ExtYjsMapper extYjsMapper;
    @Autowired(required = false)
    protected ExtBksMapper extBksMapper;
    @Autowired(required = false)
    protected ExtJzgMapper extJzgMapper;
    @Autowired(required = false)
    protected ExtAbroadMapper extAbroadMapper;
    @Autowired(required = false)
    protected ExtRetireSalaryMapper extRetireSalaryMapper;
    @Autowired(required = false)
    protected ExtJzgSalaryMapper extJzgSalaryMapper;

    @Autowired(required = false)
    protected LocationMapper locationMapper;
    @Autowired(required = false)
    protected MetaClassMapper metaClassMapper;
    @Autowired(required = false)
    protected MetaTypeMapper metaTypeMapper;
    @Autowired(required = false)
    protected CountryMapper countryMapper;
    @Autowired(required = false)
    protected ShortMsgMapper shortMsgMapper;
    @Autowired(required = false)
    protected ShortMsgReceiverMapper shortMsgReceiverMapper;
    @Autowired(required = false)
    protected ContentTplMapper contentTplMapper;
    @Autowired(required = false)
    protected SitemapMapper sitemapMapper;
    @Autowired(required = false)
    protected SitemapRoleMapper sitemapRoleMapper;
    @Autowired(required = false)
    protected ShortMsgTplMapper shortMsgTplMapper;
    @Autowired(required = false)
    protected IBaseMapper iBaseMapper;


    // #tomcat版本>=8.0.39 下 win10下url路径中带正斜杠的文件路径读取不了
    protected final static String FILE_SEPARATOR = File.separator;

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

    // 排序顺序
    public final static byte ORDER_BY_ASC = -1; // 正序
    public final static byte ORDER_BY_DESC = 1; // 逆序

    // 获得表中最大的排序序号
    public int getNextSortOrder(String tableName, String whereSql) {

        return  getNextSortOrder(tableName, "sort_order", whereSql);
    }

    public int getNextSortOrder(String tableName, String sortOrder, String whereSql) {

        Integer maxSortOrder = commonMapper.getMaxSortOrder(tableName, sortOrder, whereSql);
        return (maxSortOrder == null ? 1 : maxSortOrder + 1);
    }
}
