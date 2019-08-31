package controller;

import ext.service.ExtService;
import ext.service.ShortMsgService;
import ext.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import service.BaseMapper;
import service.LoginUserService;
import service.SpringProps;
import service.analysis.StatCadreService;
import service.analysis.StatPartyMemberService;
import service.analysis.StatService;
import service.base.*;
import service.cadre.*;
import service.cadreInspect.CadreInspectExportService;
import service.cadreInspect.CadreInspectService;
import service.cadreReserve.CadreReserveExportService;
import service.cadreReserve.CadreReserveOriginService;
import service.cadreReserve.CadreReserveService;
import service.crp.CrpRecordService;
import service.global.CacheService;
import service.leader.LeaderService;
import service.leader.LeaderUnitService;
import service.party.*;
import service.ps.PsInfoService;
import service.sys.*;
import service.unit.*;
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
    protected OrganizerService organizerService;
    @Autowired
    protected OrganizerGroupService organizerGroupService;
    @Autowired
    protected PartyPublicService partyPublicService;
    @Autowired
    protected PartyPublicUserService partyPublicUserService;
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
    protected CadreCompanyFileService cadreCompanyFileService;
    @Autowired
    protected CadreRewardService cadreRewardService;
    @Autowired
    protected CadrePunishService cadrePunishService;
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
    protected LeaderService leaderService;
    @Autowired
    protected LeaderUnitService leaderUnitService;

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
    protected UnitFunctionService unitFunctionService;
    @Autowired
    protected UnitPostService unitPostService;
    @Autowired
    protected UnitTeamService unitTeamService;
    @Autowired
    protected UnitTeamPlanService unitTeamPlanService;

    @Autowired
    protected PsInfoService partySchoolService;
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
    protected SysPropertyService sysPropertyService;
    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
    @Autowired
    protected SysUserService sysUserService;
    
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
