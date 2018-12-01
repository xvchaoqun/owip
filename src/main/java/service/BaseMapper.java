package service;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.abroad.*;
import persistence.abroad.common.IAbroadMapper;
import persistence.base.*;
import persistence.base.common.IBaseMapper;
import persistence.cadre.*;
import persistence.cadre.common.ICadreMapper;
import persistence.cadre.common.StatCadreMapper;
import persistence.cadreInspect.CadreInspectMapper;
import persistence.cadreInspect.CadreInspectViewMapper;
import persistence.cadreReserve.CadreReserveMapper;
import persistence.cadreReserve.CadreReserveOriginMapper;
import persistence.cadreReserve.CadreReserveViewMapper;
import persistence.cet.*;
import persistence.cet.common.ICetMapper;
import persistence.cis.*;
import persistence.cis.common.ICisMapper;
import persistence.cla.*;
import persistence.cla.common.IClaMapper;
import persistence.common.CommonMapper;
import persistence.common.IPartyMapper;
import persistence.common.IPropertyMapper;
import persistence.common.ISysMapper;
import persistence.cpc.common.ICpcMapper;
import persistence.crp.CrpRecordMapper;
import persistence.crs.*;
import persistence.crs.common.ICrsMapper;
import persistence.dispatch.*;
import persistence.dispatch.common.IDispatchMapper;
import persistence.ext.*;
import persistence.member.*;
import persistence.member.common.IMemberMapper;
import persistence.member.common.StatMemberMapper;
import persistence.modify.ModifyBaseApplyMapper;
import persistence.modify.ModifyBaseItemMapper;
import persistence.modify.ModifyCadreAuthMapper;
import persistence.modify.ModifyTableApplyMapper;
import persistence.modify.common.IModifyMapper;
import persistence.oa.*;
import persistence.party.*;
import persistence.partySchool.PartySchoolMapper;
import persistence.pcs.*;
import persistence.pcs.common.IPcsMapper;
import persistence.pmd.*;
import persistence.pmd.common.IPmdMapper;
import persistence.sc.IScMapper;
import persistence.sc.scAd.ScAdArchiveMapper;
import persistence.sc.scAd.ScAdArchiveViewMapper;
import persistence.sc.scAd.ScAdArchiveVoteMapper;
import persistence.sc.scAd.ScAdUseMapper;
import persistence.sc.scBorder.ScBorderItemMapper;
import persistence.sc.scBorder.ScBorderItemViewMapper;
import persistence.sc.scBorder.ScBorderMapper;
import persistence.sc.scBorder.ScBorderViewMapper;
import persistence.sc.scCommittee.*;
import persistence.sc.scDispatch.ScDispatchCommitteeMapper;
import persistence.sc.scDispatch.ScDispatchMapper;
import persistence.sc.scDispatch.ScDispatchUserMapper;
import persistence.sc.scDispatch.ScDispatchViewMapper;
import persistence.sc.scGroup.*;
import persistence.sc.scLetter.*;
import persistence.sc.scMatter.*;
import persistence.sc.scMotion.ScMotionMapper;
import persistence.sc.scMotion.ScMotionPostMapper;
import persistence.sc.scPassport.ScPassportHandMapper;
import persistence.sc.scPassport.ScPassportMapper;
import persistence.sc.scPassport.ScPassportMsgMapper;
import persistence.sc.scPublic.ScPublicMapper;
import persistence.sc.scPublic.ScPublicUserMapper;
import persistence.sc.scPublic.ScPublicViewMapper;
import persistence.sc.scSubsidy.*;
import persistence.sys.*;
import persistence.unit.*;
import persistence.unit.common.IUnitMapper;
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
    @Autowired(required = false)
    protected CetUpperTrainMapper cetUpperTrainMapper;
    @Autowired(required = false)
    protected CetUpperTrainAdminMapper cetUpperTrainAdminMapper;

    /**
     * 干部选拔-动议
     */
    @Autowired(required = false)
    protected ScMotionMapper scMotionMapper;
    @Autowired(required = false)
    protected ScMotionPostMapper scMotionPostMapper;

    /**
     * 干部选拔-干部津贴变动
     */
    @Autowired(required = false)
    protected ScSubsidyMapper scSubsidyMapper;
    @Autowired(required = false)
    protected ScSubsidyDispatchMapper scSubsidyDispatchMapper;
    @Autowired(required = false)
    protected ScSubsidyDispatchViewMapper scSubsidyDispatchViewMapper;
    @Autowired(required = false)
    protected ScSubsidyCadreMapper scSubsidyCadreMapper;
    @Autowired(required = false)
    protected ScSubsidyCadreViewMapper scSubsidyCadreViewMapper;
    @Autowired(required = false)
    protected ScSubsidyDcMapper scSubsidyDcMapper;

    /**
     * 干部选拔-新提任干部交证件
     */
    @Autowired(required = false)
    protected ScPassportHandMapper scPassportHandMapper;
    @Autowired(required = false)
    protected ScPassportMapper scPassportMapper;

    @Autowired(required = false)
    protected ScPassportMsgMapper scPassportMsgMapper;

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
    protected ScCommitteeTopicCadreMapper scCommitteeTopicCadreMapper;
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
     * 干部选拔-出入境备案
     */
    @Autowired(required = false)
    protected ScBorderMapper scBorderMapper;
    @Autowired(required = false)
    protected ScBorderViewMapper scBorderViewMapper;
    @Autowired(required = false)
    protected ScBorderItemMapper scBorderItemMapper;
    @Autowired(required = false)
    protected ScBorderItemViewMapper scBorderItemViewMapper;

    /**
     * 干部选拔-个人有关事项
     */
    @Autowired(required = false)
    protected ScMatterMapper scMatterMapper;
    @Autowired(required = false)
    protected ScMatterViewMapper scMatterViewMapper;
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
    protected CadreCompanyViewMapper cadreCompanyViewMapper;
    @Autowired(required = false)
    protected CadreCompanyFileMapper cadreCompanyFileMapper;
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
    protected CadreEvaMapper cadreEvaMapper;
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
    protected CadreLeaderUnitViewMapper cadreLeaderUnitViewMapper;
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
    protected PcsCommitteeMemberMapper pcsCommitteeMemberMapper;
    @Autowired(required = false)
    protected PcsCommitteeMemberViewMapper pcsCommitteeMemberViewMapper;
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
    protected DispatchUnitViewMapper dispatchUnitViewMapper;
    @Autowired(required = false)
    protected DispatchUnitRelateMapper dispatchUnitRelateMapper;
    @Autowired(required = false)
    protected DispatchWorkFileMapper dispatchWorkFileMapper;
    @Autowired(required = false)
    protected DispatchWorkFileAuthMapper dispatchWorkFileAuthMapper;


    @Autowired(required = false)
    protected StatCadreMapper statCadreMapper;

    @Autowired(required = false)
    protected PartySchoolMapper partySchoolMapper;

    @Autowired(required = false)
    protected HistoryUnitMapper historyUnitMapper;
    @Autowired(required = false)
    protected UnitMapper unitMapper;
    @Autowired(required = false)
    protected UnitViewMapper unitViewMapper;
    @Autowired(required = false)
    protected UnitPostMapper unitPostMapper;
    @Autowired(required = false)
    protected UnitPostViewMapper unitPostViewMapper;
    @Autowired(required = false)
    protected UnitPostCountViewMapper unitPostCountViewMapper;
    @Autowired(required = false)
    protected UnitTransferMapper unitTransferMapper;
    @Autowired(required = false)
    protected UnitCadreTransferGroupMapper unitCadreTransferGroupMapper;
    @Autowired(required = false)
    protected UnitCadreTransferMapper unitCadreTransferMapper;
    @Autowired(required = false)
    protected UnitTeamMapper unitTeamMapper;
    @Autowired(required = false)
    protected UnitTeamPlanMapper unitTeamPlanMapper;

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
    protected AnnualTypeMapper annualTypeMapper;
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
