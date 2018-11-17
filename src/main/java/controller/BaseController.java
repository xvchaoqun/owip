package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import service.BaseMapper;
import service.LoginUserService;
import service.SpringProps;
import service.analysis.StatCadreService;
import service.analysis.StatPartyMemberService;
import service.analysis.StatService;
import service.base.AnnualTypeService;
import service.base.ContentTplService;
import service.base.CountryService;
import service.base.LocationService;
import service.base.MetaClassService;
import service.base.MetaTypeService;
import service.base.ShortMsgService;
import service.base.ShortMsgTplService;
import service.base.SitemapService;
import service.cadre.CadreAdminLevelService;
import service.cadre.CadreBookService;
import service.cadre.CadreCommonService;
import service.cadre.CadreCompanyService;
import service.cadre.CadreCourseService;
import service.cadre.CadreEduService;
import service.cadre.CadreEvaService;
import service.cadre.CadreExportService;
import service.cadre.CadreFamilyAbroadService;
import service.cadre.CadreFamilyService;
import service.cadre.CadreInfoCheckService;
import service.cadre.CadreInfoService;
import service.cadre.CadreLeaderService;
import service.cadre.CadreLeaderUnitService;
import service.cadre.CadrePaperService;
import service.cadre.CadreParttimeService;
import service.cadre.CadrePartyService;
import service.cadre.CadrePostAdminService;
import service.cadre.CadrePostProService;
import service.cadre.CadrePostService;
import service.cadre.CadrePostWorkService;
import service.cadre.CadreReportService;
import service.cadre.CadreResearchService;
import service.cadre.CadreRewardService;
import service.cadre.CadreService;
import service.cadre.CadreStatHistoryService;
import service.cadre.CadreTrainService;
import service.cadre.CadreTutorService;
import service.cadre.CadreUnderEduService;
import service.cadre.CadreWorkService;
import service.cadreInspect.CadreInspectExportService;
import service.cadreInspect.CadreInspectService;
import service.cadreReserve.CadreReserveExportService;
import service.cadreReserve.CadreReserveOriginService;
import service.cadreReserve.CadreReserveService;
import service.crp.CrpRecordService;
import service.ext.ExtService;
import service.ext.SyncService;
import service.global.CacheService;
import service.party.BranchMemberAdminService;
import service.party.BranchMemberGroupService;
import service.party.BranchMemberService;
import service.party.BranchService;
import service.party.MemberService;
import service.party.MemberStudentService;
import service.party.MemberTeacherService;
import service.party.OrgAdminService;
import service.party.PartyMemberAdminService;
import service.party.PartyMemberGroupService;
import service.party.PartyMemberService;
import service.party.PartyService;
import service.partySchool.PartySchoolService;
import service.sys.AttachFileService;
import service.sys.AvatarService;
import service.sys.FeedbackService;
import service.sys.HtmlFragmentService;
import service.sys.LogService;
import service.sys.StudentInfoService;
import service.sys.SysApprovalLogService;
import service.sys.SysConfigService;
import service.sys.SysLoginLogService;
import service.sys.SysResourceService;
import service.sys.SysRoleService;
import service.sys.SysUserRegService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import service.sys.UserBeanService;
import service.unit.HistoryUnitService;
import service.unit.UnitAdminGroupService;
import service.unit.UnitAdminService;
import service.unit.UnitCadreTransferGroupService;
import service.unit.UnitCadreTransferService;
import service.unit.UnitPostAllocationService;
import service.unit.UnitPostService;
import service.unit.UnitService;
import service.unit.UnitTransferService;
import shiro.PasswordHelper;
import sys.HttpResponseMethod;

@SuppressWarnings("unchecked")
public class BaseController extends BaseMapper implements HttpResponseMethod {

    @Autowired
    protected BranchMemberGroupService branchMemberGroupService;
    @Autowired
    protected BranchMemberAdminService branchMemberAdminService;
    @Autowired
    protected BranchMemberService branchMemberService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected PartyMemberGroupService partyMemberGroupService;
    @Autowired
    protected PartyMemberAdminService partyMemberAdminService;
    @Autowired
    protected PartyMemberService partyMemberService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected OrgAdminService orgAdminService;
    @Autowired
    protected MemberStudentService memberStudentService;
    @Autowired
    protected MemberTeacherService memberTeacherService;
    @Autowired
    protected MemberService memberService;

    @Autowired
    protected CadrePostService cadrePostService;
    @Autowired
    protected CadreAdminLevelService cadreAdminLevelService;
    @Autowired
    protected CadreInfoService cadreInfoService;
    @Autowired
    protected CadreInfoCheckService cadreInfoCheckService;
    @Autowired
    protected CadreFamilyAbroadService cadreFamilyAbroadService;
    @Autowired
    protected CadreFamilyService cadreFamilyService;
    @Autowired
    protected CadreCompanyService cadreCompanyService;
    @Autowired
    protected CadreRewardService cadreRewardService;
    @Autowired
    protected CadreBookService cadreBookService;
    @Autowired
    protected CadreResearchService cadreResearchService;
    @Autowired
    protected CadrePaperService cadrePaperService;
    @Autowired
    protected CadreParttimeService cadreParttimeService;
    @Autowired
    protected CadrePostProService cadrePostProService;
    @Autowired
    protected CadrePostAdminService cadrePostAdminService;
    @Autowired
    protected CadrePostWorkService cadrePostWorkService;
    @Autowired
    protected CadreTrainService cadreTrainService;
    @Autowired
    protected CadreCourseService cadreCourseService;
    @Autowired
    protected CadreWorkService cadreWorkService;
    @Autowired
    protected CadreEduService cadreEduService;
    @Autowired
    protected CadreEvaService cadreEvaService;
    @Autowired
    protected CadreUnderEduService cadreUnderEduService;
    @Autowired
    protected CadreTutorService cadreTutorService;
    @Autowired
    protected CadreReportService cadreReportService;

    @Autowired
    protected CadreLeaderService cadreLeaderService;
    @Autowired
    protected CadreLeaderUnitService cadreLeaderUnitService;
    @Autowired
    protected CadreStatHistoryService cadreStatHistoryService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected CadreCommonService cadreCommonService;
    @Autowired
    protected CadreInspectService cadreInspectService;
    @Autowired
    protected CadreInspectExportService cadreInspectExportService;
    @Autowired
    protected CadreReserveService cadreReserveService;
    @Autowired
    protected CadreReserveOriginService cadreReserveOriginService;
    @Autowired
    protected CadreReserveExportService cadreReserveExportService;
    @Autowired
    protected CadrePartyService cadrePartyService;

    @Autowired
    protected CrpRecordService crpRecordService;

    @Autowired
    protected UnitPostAllocationService unitPostAllocationService;

    @Autowired
    protected UnitTransferService unitTransferService;
    @Autowired
    protected UnitCadreTransferService unitCadreTransferService;
    @Autowired
    protected UnitCadreTransferGroupService unitCadreTransferGroupService;
    @Autowired
    protected HistoryUnitService historyUnitService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected UnitPostService unitPostService;
    @Autowired
    protected UnitAdminGroupService unitAdminGroupService;
    @Autowired
    protected UnitAdminService unitAdminService;

    @Autowired
    protected PartySchoolService partySchoolService;
    @Autowired
    protected MetaClassService metaClassService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected AnnualTypeService annualTypeService;
    @Autowired
    protected ShortMsgTplService shortMsgTplService;

    @Autowired
    protected AvatarService avatarService;

    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected StudentInfoService studentService;

    @Autowired
    protected ExtService extService;
    @Autowired
    protected SyncService syncService;

    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected SysUserRegService sysUserRegService;
    @Autowired
    protected SysRoleService sysRoleService;
    @Autowired
    protected SysResourceService sysResourceService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected LogService logService;
    @Autowired
    protected AttachFileService attachFileService;
    @Autowired
    protected HtmlFragmentService htmlFragmentService;
    @Autowired
    protected SysLoginLogService sysLoginLogService;
    @Autowired
    protected FeedbackService feedbackService;

    @Autowired
    protected ContentTplService contentTplService;
    @Autowired
    protected LocationService locationService;
    @Autowired
    protected CountryService countryService;
    @Autowired
    protected SitemapService sitemapService;

    @Autowired
    protected CadreExportService cadreExportService;
    @Autowired
    protected StatCadreService statCadreService;
    @Autowired
    protected StatPartyMemberService statPartyMemberService;
    @Autowired
    protected StatService statService;

    @Autowired
    protected CacheService cacheService;

    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected Environment evironment;
}
