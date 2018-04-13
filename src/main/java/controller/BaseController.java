package controller;

import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.ext.ExtJzg;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import service.BaseMapper;
import service.LoginUserService;
import service.SpringProps;
import service.analysis.StatCadreService;
import service.analysis.StatPartyMemberService;
import service.analysis.StatService;
import service.base.ContentTplService;
import service.base.CountryService;
import service.base.LocationService;
import service.base.MetaClassService;
import service.base.MetaTypeService;
import service.base.ShortMsgService;
import service.base.ShortMsgTplService;
import service.base.SitemapService;
import service.cadre.CadreAdditionalPostService;
import service.cadre.CadreAdminLevelService;
import service.cadre.CadreBookService;
import service.cadre.CadreCommonService;
import service.cadre.CadreCompanyService;
import service.cadre.CadreCourseService;
import service.cadre.CadreEduService;
import service.cadre.CadreExportService;
import service.cadre.CadreFamliyAbroadService;
import service.cadre.CadreFamliyService;
import service.cadre.CadreInfoCheckService;
import service.cadre.CadreInfoService;
import service.cadre.CadreLeaderService;
import service.cadre.CadreLeaderUnitService;
import service.cadre.CadrePaperService;
import service.cadre.CadreParttimeService;
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
import service.cadreReserve.CadreReserveService;
import service.cpc.CpcAllocationService;
import service.crp.CrpRecordService;
import service.dispatch.DispatchService;
import service.dispatch.DispatchTypeService;
import service.ext.ExtBksService;
import service.ext.ExtJzgService;
import service.ext.ExtYjsService;
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
import service.source.SyncService;
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
import service.unit.UnitService;
import service.unit.UnitTransferService;
import shiro.PasswordHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class BaseController extends BaseMapper {

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
    protected UnitAdminGroupService unitAdminGroupService;
    @Autowired
    protected UnitAdminService unitAdminService;
    @Autowired
    protected CadrePostService cadrePostService;
    @Autowired
    protected CadreAdminLevelService cadreAdminLevelService;
    @Autowired
    protected CadreInfoService cadreInfoService;
    @Autowired
    protected CadreInfoCheckService cadreInfoCheckService;
    @Autowired
    protected CadreFamliyAbroadService cadreFamliyAbroadService;
    @Autowired
    protected CadreFamliyService cadreFamliyService;
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
    protected CadreReserveExportService cadreReserveExportService;
    @Autowired
    protected CadreAdditionalPostService cadreAdditionalPostService;

    @Autowired
    protected CrpRecordService crpRecordService;

    @Autowired
    protected CpcAllocationService cpcAllocationService;

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
    protected PartySchoolService partySchoolService;
    @Autowired
    protected MetaClassService metaClassService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected ShortMsgTplService shortMsgTplService;

    @Autowired
    protected AvatarService avatarService;

    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected TeacherInfoService teacherService;
    @Autowired
    protected StudentInfoService studentService;

    @Autowired
    protected ExtJzgService extJzgService;
    @Autowired
    protected ExtYjsService extYjsService;
    @Autowired
    protected ExtBksService extBksService;
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

    protected void pdf2Swf(String filePath, String swfPath) throws IOException, InterruptedException {

        FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + filePath,
                springProps.uploadPath + swfPath, springProps.swfToolsLanguagedir);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param saveFolder
     * @param type       = pdf pic
     * @param sImgWidth
     * @param sImgHeight
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String upload(MultipartFile file, String saveFolder,
                         String type,
                         int sImgWidth,
                         int sImgHeight) throws IOException, InterruptedException {

        if (file == null || file.isEmpty()) return null;

        String realPath = FILE_SEPARATOR + saveFolder +
                FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMMdd") +
                FILE_SEPARATOR + UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String savePath = realPath + FileUtils.getExtention(originalFilename);
        FileUtils.copyFile(file, new File(springProps.uploadPath + savePath));

        if (StringUtils.equalsIgnoreCase(type, "doc")) {

            String pdfPath = realPath + ".pdf";
            FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath + pdfPath);
            String swfPath = realPath + ".swf";
            pdf2Swf(pdfPath, swfPath);

        }else if (StringUtils.equalsIgnoreCase(type, "pdf")) {

            String swfPath = realPath + ".swf";
            pdf2Swf(savePath, swfPath);

        } else if (StringUtils.equalsIgnoreCase(type, "pic")) {
            // 需要缩略图的情况
            String shortImgPath = realPath + "_s.jpg";
            Thumbnails.of(file.getInputStream())
                    .size(sImgWidth, sImgHeight)
                    .outputFormat("jpg")
                    .outputQuality(1.0f)
                    .toFile(springProps.uploadPath + shortImgPath);
        }

        return savePath;
    }

    public String upload(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        return upload(file, saveFolder, null, 0, 0);
    }

    public String uploadDocOrPdf(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        if (StringUtils.contains(file.getContentType(), "pdf")) {

            return uploadPdf(file, saveFolder);
        } else {
            return uploadDoc(file, saveFolder);
        }
    }

    public String uploadDoc(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        return upload(file, saveFolder, "doc", 0, 0);
    }

    public String uploadPdf(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        return upload(file, saveFolder, "pdf", 0, 0);
    }

    public String uploadPic(MultipartFile file, String saveFolder, int sImgWidth, int sImgHeight) throws IOException, InterruptedException {

        return upload(file, saveFolder, "pic", sImgWidth, sImgHeight);
    }

    public String savePdfOrImage(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        if (StringUtils.indexOfAny(file.getContentType(), "pdf", "image")==-1) {
            throw new FileFormatException("文件格式错误，请上传pdf或图片文件");
        }

        if (StringUtils.contains(file.getContentType(), "pdf")) {

            return uploadPdf(file, saveFolder);
        }else{

            return uploadPic(file, saveFolder, 400, 300);
        }
    }

    // 未登录操作日志
    public String addLog(Integer userId, String username, String logType, String content, Object... params) {

        if (params != null && params.length > 0)
            content = String.format(content, params);

        return logService.log(userId, username, logType, content);
    }

    // 登录后操作日志
    public String addLog(Integer logType, String content, Object... params) {

        if (params != null && params.length > 0)
            content = String.format(content, params);

        return logService.log(logType, content);
    }

    public Map<String, Object> success() {

        return success(null);
    }

    public static Map<String, Object> success(String msg) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        resultMap.put("msg", StringUtils.defaultIfBlank(msg, "success"));
        return resultMap;
    }

    public static Map<String, Object> formValidError(String fieldName, String msg) {

        Map<String, Object> resultMap = success(msg);
        resultMap.put("success", true);
        resultMap.put("error", true);
        resultMap.put("field", fieldName);

        return resultMap;
    }

    public static Map<String, Object> failed(String msg) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        resultMap.put("msg", StringUtils.defaultIfBlank(msg, "failed"));
        return resultMap;
    }

    public static Map<String, Object> ret(int ret, String msg) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("ret", ret);
        resultMap.put("msg", StringUtils.defaultIfBlank(msg, "failed"));
        return resultMap;
    }

    public Map getMetaMap() {

        Map map = new HashMap<>();

        map.put("sysConfig", CmTag.getSysConfig());

        map.put("partyMap", partyService.findAll());
        map.put("staffTypeMap", metaTypeService.metaTypes("mc_branch_staff_type"));
        map.put("partyUnitTypeMap", metaTypeService.metaTypes("mc_party_unit_type"));
        map.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));
        map.put("partyMemberPostMap", metaTypeService.metaTypes("mc_party_member_post"));
        map.put("partyMemberTypeMap", metaTypeService.metaTypes("mc_party_member_type"));
        map.put("partyTypeMap", metaTypeService.metaTypes("mc_part_type"));

        map.put("branchMap", branchService.findAll());
        map.put("branchTypeMap", metaTypeService.metaTypes("mc_branch_type"));
        map.put("branchUnitTypeMap", metaTypeService.metaTypes("mc_branch_unit_type"));

        map.put("jobMap", metaTypeService.metaTypes("mc_job"));
        map.put("flowDirectionMap", metaTypeService.metaTypes("mc_flow_direction"));

        map.put("eduTypeMap", metaTypeService.metaTypes("mc_edu"));
        map.put("learnStyleMap", metaTypeService.metaTypes("mc_learn_style"));
        map.put("schoolTypeMap", metaTypeService.metaTypes("mc_school"));

        map.put("abroadUserTypeMap", metaTypeService.metaTypes("mc_abroad_user_type"));

        map.put("eduMap", metaTypeService.metaTypes("mc_edu"));

        map.put("abroadTypeMap", metaTypeService.metaTypes("mc_abroad_type"));

        map.put("politicalStatusMap", metaTypeService.metaTypes("mc_political_status"));

        DispatchService dispatchService = CmTag.getBean(DispatchService.class);
        if(dispatchService!=null) map.put("dispatchMap", dispatchService.findAll());
        DispatchTypeService dispatchTypeService = CmTag.getBean(DispatchTypeService.class);
        if(dispatchTypeService!=null) map.put("dispatchTypeMap", dispatchTypeService.findAll());

        map.put("postClassMap", metaTypeService.metaTypes("mc_post_class"));
        map.put("leaderTypeMap", metaTypeService.metaTypes("mc_leader_type"));
        map.put("leaderUnitTypeMap", metaTypeService.metaTypes("mc_leader_unit"));


        map.put("wayMap", metaTypeService.metaTypes("mc_dispatch_cadre_way"));
        map.put("cadreTypeMap", metaTypeService.metaTypes("mc_dispatch_cadre_type"));
        map.put("procedureMap", metaTypeService.metaTypes("mc_dispatch_cadre_procedure"));
        map.put("postMap", metaTypeService.metaTypes("mc_post"));
        map.put("unitMap", unitService.findAll());

        map.put("democraticPartyMap", metaTypeService.metaTypes("mc_democratic_party"));
        map.put("unitTypeMap", metaTypeService.metaTypes("mc_unit_type"));

        map.put("passportTypeMap", metaTypeService.metaTypes("mc_passport_type"));

        map.put("dispatchUnitTypeMap", metaTypeService.metaTypes("mc_dispatch_unit"));

        map.put("adminLevelMap", metaTypeService.metaTypes("mc_admin_level"));

        map.put("locationMap", locationService.codeMap());
        map.put("countryMap", countryService.findAll());

        map.put("roleMap", sysRoleService.findAll());
        return map;
    }

    public void cadreBase(Integer cadreId, ModelMap modelMap){

        if(cadreId==null) return;

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        modelMap.put("cadre", cadre);

        SysUserView uv = sysUserService.findById(cadre.getUserId());
        modelMap.put("uv", uv);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);
        modelMap.put("member", memberService.get(uv.getId()));

        TeacherInfo teacherInfo = teacherService.get(uv.getUserId());
        modelMap.put("teacherInfo", teacherInfo);

        // 人事信息
        ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
        modelMap.put("extJzg", extJzg);

        CadrePost mainCadrePost = cadrePostService.getCadreMainCadrePost(cadreId);
        // 主职,现任职务
        modelMap.put("mainCadrePost", mainCadrePost);

        // 任现职级
        modelMap.put("cadreAdminLevel", cadreAdminLevelService.getPresentByCadreId(cadreId,
                mainCadrePost != null ? mainCadrePost.getAdminLevelId() : null));

        // 兼职单位
        List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(cadreId);
        if (subCadrePosts.size() >= 1) {
            modelMap.put("subCadrePost1", subCadrePosts.get(0));
        }
        if (subCadrePosts.size() >= 2) {
            modelMap.put("subCadrePost2", subCadrePosts.get(1));
        }

        // 最高学历
        modelMap.put("highEdu", cadreEduService.getHighEdu(cadreId));
        //最高学位
        modelMap.put("highDegree", cadreEduService.getHighDegree(cadreId));

        // 是否已认定了参加工作时间，没认定前可修改
        modelMap.put("hasVerifyWorkTime", cadre.getVerifyWorkTime()!=null);
    }
}
