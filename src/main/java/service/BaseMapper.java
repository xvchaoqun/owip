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
import persistence.common.IMemberMapper;
import persistence.common.IModifyMapper;
import persistence.common.IPartyMapper;
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
import persistence.crs.CrsApplicantViewMapper;
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
    protected TaiwanRecordMapper taiwanRecordMapper;

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
    protected CrsApplicantAdjustMapper crsApplicantAdjustMapper;
    @Autowired
    protected CrsApplicantAdjustViewMapper crsApplicantAdjustViewMapper;
    @Autowired
    protected CrsApplicantCheckMapper crsApplicantCheckMapper;
    @Autowired
    protected CrsPostMapper crsPostMapper;
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
