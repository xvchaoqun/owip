package service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
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
import persistence.cadre.CadreAdLogMapper;
import persistence.cadre.CadreAdditionalPostMapper;
import persistence.cadre.CadreAdminLevelMapper;
import persistence.cadre.CadreBookMapper;
import persistence.cadre.CadreCompanyMapper;
import persistence.cadre.CadreCourseMapper;
import persistence.cadre.CadreEduMapper;
import persistence.cadre.CadreFamliyAbroadMapper;
import persistence.cadre.CadreFamliyMapper;
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
import persistence.cadreInspect.CadreInspectMapper;
import persistence.cadreInspect.CadreInspectViewMapper;
import persistence.cadreReserve.CadreReserveMapper;
import persistence.cadreReserve.CadreReserveViewMapper;
import persistence.cis.CisEvaluateMapper;
import persistence.cis.CisInspectObjMapper;
import persistence.cis.CisInspectObjViewMapper;
import persistence.cis.CisInspectorMapper;
import persistence.cis.CisInspectorViewMapper;
import persistence.cis.CisObjInspectorMapper;
import persistence.cis.CisObjUnitMapper;
import persistence.common.CommonMapper;
import persistence.common.IAbroadMapper;
import persistence.common.ICadreMapper;
import persistence.common.ICpcMapper;
import persistence.common.ICrsMapper;
import persistence.common.IDispatchMapper;
import persistence.common.IExtMapper;
import persistence.common.IMemberMapper;
import persistence.common.IModifyMapper;
import persistence.common.IPartyMapper;
import persistence.common.IPcsMapper;
import persistence.common.IPmdMapper;
import persistence.common.IPropertyMapper;
import persistence.common.ISysMapper;
import persistence.common.ITrainMapper;
import persistence.common.IUnitMapper;
import persistence.common.StatCadreMapper;
import persistence.common.StatMemberMapper;
import persistence.cpc.CpcAllocationMapper;
import persistence.crp.CrpRecordMapper;
import persistence.crs.CrsApplicantAdjustMapper;
import persistence.crs.CrsApplicantAdjustViewMapper;
import persistence.crs.CrsApplicantCheckMapper;
import persistence.crs.CrsApplicantMapper;
import persistence.crs.CrsApplicantStatViewMapper;
import persistence.crs.CrsApplicantViewMapper;
import persistence.crs.CrsApplyRuleMapper;
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
import persistence.ext.ExtAbroadMapper;
import persistence.ext.ExtBksMapper;
import persistence.ext.ExtJzgMapper;
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
import persistence.modify.ModifyBaseApplyMapper;
import persistence.modify.ModifyBaseItemMapper;
import persistence.modify.ModifyCadreAuthMapper;
import persistence.modify.ModifyTableApplyMapper;
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
import persistence.pmd.PmdBranchAdminMapper;
import persistence.pmd.PmdBranchMapper;
import persistence.pmd.PmdBranchViewMapper;
import persistence.pmd.PmdConfigMemberMapper;
import persistence.pmd.PmdConfigMemberTypeMapper;
import persistence.pmd.PmdMemberMapper;
import persistence.pmd.PmdMemberPayMapper;
import persistence.pmd.PmdMemberPayViewMapper;
import persistence.pmd.PmdMonthMapper;
import persistence.pmd.PmdNormMapper;
import persistence.pmd.PmdNormValueLogMapper;
import persistence.pmd.PmdNormValueMapper;
import persistence.pmd.PmdNotifyCampusCardLogMapper;
import persistence.pmd.PmdNotifyWszfLogMapper;
import persistence.pmd.PmdPartyAdminMapper;
import persistence.pmd.PmdPartyMapper;
import persistence.pmd.PmdPartyViewMapper;
import persistence.pmd.PmdPayBranchMapper;
import persistence.pmd.PmdPayBranchViewMapper;
import persistence.pmd.PmdPayPartyMapper;
import persistence.pmd.PmdPayPartyViewMapper;
import persistence.pmd.PmdSpecialUserMapper;
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
import persistence.sys.SysUserSyncMapper;
import persistence.sys.SysUserViewMapper;
import persistence.sys.TeacherInfoMapper;
import persistence.train.TrainCourseMapper;
import persistence.train.TrainEvaNormMapper;
import persistence.train.TrainEvaRankMapper;
import persistence.train.TrainEvaResultMapper;
import persistence.train.TrainEvaTableMapper;
import persistence.train.TrainInspectorCourseMapper;
import persistence.train.TrainInspectorMapper;
import persistence.train.TrainMapper;
import persistence.unit.HistoryUnitMapper;
import persistence.unit.UnitAdminGroupMapper;
import persistence.unit.UnitAdminMapper;
import persistence.unit.UnitCadreTransferGroupMapper;
import persistence.unit.UnitCadreTransferMapper;
import persistence.unit.UnitMapper;
import persistence.unit.UnitTransferMapper;
import persistence.verify.VerifyAgeMapper;
import persistence.verify.VerifyWorkTimeMapper;
import shiro.ShiroUser;
import sys.tags.CmTag;

import java.io.File;

public class BaseMapper {

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
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        int loginUserId = shiroUser.getId();
        verifyAuth.entity = entity;

        verifyAuth.isBranchAdmin = CmTag.isPresentBranchAdmin(loginUserId, partyId, branchId);
        verifyAuth.isPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, partyId);
        verifyAuth.isDirectBranch = CmTag.isDirectBranch(partyId);
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
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        int loginUserId = shiroUser.getId();
        verifyAuth.entity = entity;

        if (!CmTag.isPresentPartyAdmin(loginUserId, partyId)) {
            throw new UnauthorizedException();
        }
        verifyAuth.isParty = CmTag.isParty(partyId);
        verifyAuth.isPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, partyId);
        verifyAuth.isDirectBranch = CmTag.isDirectBranch(partyId);
        return verifyAuth;
    }

    // 获得表中最大的排序序号
    public int getNextSortOrder(String tableName, String whereSql) {

        return  getNextSortOrder(tableName, "sort_order", whereSql);
    }

    public int getNextSortOrder(String tableName, String sortOrder, String whereSql) {

        Integer maxSortOrder = commonMapper.getMaxSortOrder(tableName, sortOrder, whereSql);
        return (maxSortOrder == null ? 1 : maxSortOrder + 1);
    }

    /**
     * 党费
     */
    @Autowired
    protected PmdPayPartyMapper pmdPayPartyMapper;
    @Autowired
    protected PmdPayBranchMapper pmdPayBranchMapper;
    @Autowired
    protected PmdPayPartyViewMapper pmdPayPartyViewMapper;
    @Autowired
    protected PmdPayBranchViewMapper pmdPayBranchViewMapper;
    @Autowired
    protected PmdMonthMapper pmdMonthMapper;
    @Autowired
    protected IPmdMapper iPmdMapper;
    @Autowired
    protected PmdMemberMapper pmdMemberMapper;
    @Autowired
    protected PmdMemberPayMapper pmdMemberPayMapper;
    @Autowired
    protected PmdMemberPayViewMapper pmdMemberPayViewMapper;
    @Autowired
    protected PmdNotifyWszfLogMapper pmdNotifyWszfLogMapper;
    @Autowired
    protected PmdNotifyCampusCardLogMapper pmdNotifyCampusCardLogMapper;
    @Autowired
    protected PmdPartyMapper pmdPartyMapper;
    @Autowired
    protected PmdPartyViewMapper pmdPartyViewMapper;
    @Autowired
    protected PmdBranchMapper pmdBranchMapper;
    @Autowired
    protected PmdBranchViewMapper pmdBranchViewMapper;
    @Autowired
    protected PmdPartyAdminMapper pmdPartyAdminMapper;
    @Autowired
    protected PmdBranchAdminMapper pmdBranchAdminMapper;
    @Autowired
    protected PmdNormMapper pmdNormMapper;
    @Autowired
    protected PmdNormValueMapper pmdNormValueMapper;
    @Autowired
    protected PmdNormValueLogMapper pmdNormValueLogMapper;
    @Autowired
    protected PmdSpecialUserMapper pmdSpecialUserMapper;
    @Autowired
    protected PmdConfigMemberTypeMapper pmdConfigMemberTypeMapper;
    @Autowired
    protected PmdConfigMemberMapper pmdConfigMemberMapper;

    /**
     * 协同办公
     */
    @Autowired
    protected OaTaskMapper oaTaskMapper;
    @Autowired
    protected OaTaskViewMapper oaTaskViewMapper;
    @Autowired
    protected OaTaskFileMapper oaTaskFileMapper;
    @Autowired
    protected OaTaskMsgMapper oaTaskMsgMapper;
    @Autowired
    protected OaTaskRemindMapper oaTaskRemindMapper;
    @Autowired
    protected OaTaskUserMapper oaTaskUserMapper;
    @Autowired
    protected OaTaskUserViewMapper oaTaskUserViewMapper;
    @Autowired
    protected OaTaskUserFileMapper oaTaskUserFileMapper;

    /**
     * 因私出国境
     */
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
    protected TaiwanRecordMapper taiwanRecordMapper;

    /**
     * 党建
     */
    @Autowired
    protected EnterApplyMapper enterApplyMapper;
    @Autowired
    protected ApplyApprovalLogMapper applyApprovalLogMapper;
    @Autowired
    protected MemberTransferMapper memberTransferMapper;
    @Autowired
    protected MemberOutMapper memberOutMapper;
    @Autowired
    protected MemberOutViewMapper memberOutViewMapper;
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
    protected MemberStayMapper memberStayMapper;
    @Autowired
    protected MemberStayViewMapper memberStayViewMapper;
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
    protected ApplyOpenTimeMapper applyOpenTimeMapper;
    @Autowired
    protected MemberApplyMapper memberApplyMapper;
    @Autowired
    protected MemberApplyViewMapper memberApplyViewMapper;

    /**
     * 组织机构
     */
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

    /**
     * 干部库
     */
    @Autowired
    protected CadreInfoMapper cadreInfoMapper;
    @Autowired
    protected CadreInfoCheckMapper cadreInfoCheckMapper;
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
    protected CadrePartyMapper cadrePartyMapper;
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

    /**
     * 干部考察系统
     */
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

    /**
     * 党代会
     */
    @Autowired
    protected IPcsMapper iPcsMapper;
    @Autowired
    protected PcsExcludeBranchMapper pcsExcludeBranchMapper;
    @Autowired
    protected PcsPartyViewMapper pcsPartyViewMapper;
    @Autowired
    protected PcsIssueMapper pcsIssueMapper;
    @Autowired
    protected PcsAdminMapper pcsAdminMapper;
    @Autowired
    protected PcsAdminReportMapper pcsAdminReportMapper;
    @Autowired
    protected PcsCandidateMapper pcsCandidateMapper;
    @Autowired
    protected PcsCandidateViewMapper pcsCandidateViewMapper;
    @Autowired
    protected PcsCandidateChosenMapper pcsCandidateChosenMapper;
    @Autowired
    protected PcsConfigMapper pcsConfigMapper;
    @Autowired
    protected PcsRecommendMapper pcsRecommendMapper;
    @Autowired
    protected PcsPrAllocateMapper pcsPrAllocateMapper;
    @Autowired
    protected PcsPrRecommendMapper pcsPrRecommendMapper;
    @Autowired
    protected PcsPrCandidateMapper pcsPrCandidateMapper;
    @Autowired
    protected PcsPrCandidateViewMapper pcsPrCandidateViewMapper;
    @Autowired
    protected PcsPrFileTemplateMapper pcsPrFileTemplateMapper;
    @Autowired
    protected PcsPrFileMapper pcsPrFileMapper;
    @Autowired
    protected PcsProposalMapper pcsProposalMapper;
    @Autowired
    protected PcsProposalViewMapper pcsProposalViewMapper;
    @Autowired
    protected PcsProposalFileMapper pcsProposalFileMapper;
    @Autowired
    protected PcsProposalSeconderMapper pcsProposalSeconderMapper;
    @Autowired
    protected PcsVoteGroupMapper pcsVoteGroupMapper;
    @Autowired
    protected PcsVoteCandidateMapper pcsVoteCandidateMapper;
    @Autowired
    protected PcsVoteMemberMapper pcsVoteMemberMapper;

    /**
     * 干部职数
     */
    @Autowired
    protected CpcAllocationMapper cpcAllocationMapper;

    /**
     * 培训
     */
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

    /**
     * 干部招聘
     */
    @Autowired
    protected CrsTemplateMapper crsTemplateMapper;
    @Autowired
    protected CrsExpertMapper crsExpertMapper;
    @Autowired
    protected CrsExpertViewMapper crsExpertViewMapper;
    @Autowired
    protected CrsApplicantMapper crsApplicantMapper;
    @Autowired
    protected CrsApplicantViewMapper crsApplicantViewMapper;
    @Autowired
    protected CrsApplicantStatViewMapper crsApplicantStatViewMapper;
    @Autowired
    protected CrsApplicantAdjustMapper crsApplicantAdjustMapper;
    @Autowired
    protected CrsApplicantAdjustViewMapper crsApplicantAdjustViewMapper;
    @Autowired
    protected CrsApplicantCheckMapper crsApplicantCheckMapper;
    @Autowired
    protected CrsPostMapper crsPostMapper;
    @Autowired
    protected CrsCandidateViewMapper crsCandidateViewMapper;
    @Autowired
    protected CrsCandidateMapper crsCandidateMapper;
    @Autowired
    protected CrsApplyRuleMapper crsApplyRuleMapper;
    @Autowired
    protected CrsShortMsgMapper crsShortMsgMapper;
    @Autowired
    protected CrsPostRequireMapper crsPostRequireMapper;
    @Autowired
    protected CrsRequireRuleMapper crsRequireRuleMapper;
    @Autowired
    protected CrsRuleItemMapper crsRuleItemMapper;
    @Autowired
    protected CrsPostExpertMapper crsPostExpertMapper;
    @Autowired
    protected CrsPostFileMapper crsPostFileMapper;
    @Autowired
    protected ICrsMapper iCrsMapper;

    /**
     * 档案认定
     */
    @Autowired
    protected VerifyAgeMapper verifyAgeMapper;
    @Autowired
    protected VerifyWorkTimeMapper verifyWorkTimeMapper;

    /**
     * 发文
     */
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
    protected HistoryUnitMapper historyUnitMapper;
    @Autowired
    protected UnitMapper unitMapper;

    /**
     * 干部信息修改申请
     */
    @Autowired
    protected ModifyCadreAuthMapper modifyCadreAuthMapper;
    @Autowired
    protected ModifyBaseApplyMapper modifyBaseApplyMapper;
    @Autowired
    protected ModifyBaseItemMapper modifyBaseItemMapper;
    @Autowired
    protected ModifyTableApplyMapper modifyTableApplyMapper;

    @Autowired
    protected SysConfigMapper sysConfigMapper;
    @Autowired
    protected SysConfigLoginMsgMapper sysConfigLoginMsgMapper;
    @Autowired
    protected SysApprovalLogMapper sysApprovalLogMapper;
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
    protected AttachFileMapper attachFileMapper;
    @Autowired
    protected HtmlFragmentMapper htmlFragmentMapper;
    @Autowired
    protected SysOnlineStaticMapper sysOnlineStaticMapper;
    @Autowired
    protected FeedbackMapper feedbackMapper;


    @Autowired
    protected CommonMapper commonMapper;
    @Autowired
    protected IPropertyMapper IPropertyMapper;
    @Autowired
    protected IAbroadMapper iAbroadMapper;
    @Autowired
    protected ICadreMapper iCadreMapper;
    @Autowired
    protected ICpcMapper iCpcMapper;
    @Autowired
    protected IDispatchMapper iDispatchMapper;
    @Autowired
    protected IMemberMapper iMemberMapper;
    @Autowired
    protected IModifyMapper iModifyMapper;
    @Autowired
    protected IPartyMapper iPartyMapper;
    @Autowired
    protected ISysMapper iSysMapper;
    @Autowired
    protected ITrainMapper iTrainMapper;
    @Autowired
    protected IUnitMapper iUnitMapper;
    @Autowired
    protected StatMemberMapper statMemberMapper;

    @Autowired
    protected IExtMapper iExtMapper;
    @Autowired
    protected ExtYjsMapper extYjsMapper;
    @Autowired
    protected ExtBksMapper extBksMapper;
    @Autowired
    protected ExtJzgMapper extJzgMapper;
    @Autowired
    protected ExtAbroadMapper extAbroadMapper;
    @Autowired
    protected ExtRetireSalaryMapper extRetireSalaryMapper;

    @Autowired
    protected LocationMapper locationMapper;
    @Autowired
    protected MetaClassMapper metaClassMapper;
    @Autowired
    protected MetaTypeMapper metaTypeMapper;
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
