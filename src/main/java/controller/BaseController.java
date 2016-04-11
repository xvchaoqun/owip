package controller;

import domain.ApproverType;
import domain.SysUser;
import mixin.SysUserMixin;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import service.*;
import service.abroad.*;
import service.cadre.*;
import service.dispatch.*;
import service.ext.ExtBksService;
import service.ext.ExtJzgService;
import service.ext.ExtYjsService;
import service.party.*;
import service.sys.*;
import service.unit.*;
import shiro.PasswordHelper;
import shiro.ShiroUser;
import sys.constants.DispatchConstants;
import sys.constants.SystemConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class BaseController extends BaseMapper {

    @Autowired
    protected ApplicatPostService applicatPostService;
    @Autowired
    protected ApplicatTypeService applicatTypeService;
    @Autowired
    protected ApprovalOrderService approvalOrderService;
    @Autowired
    protected ApproverService approverService;
    @Autowired
    protected ApproverTypeService approverTypeService;

    @Autowired
    protected PassportDrawService passportDrawService;
    @Autowired
    protected ApprovalLogService approvalLogService;
    @Autowired
    protected ApplySelfService applySelfService;
    @Autowired
    protected PassportApplyService passportApplyService;
    @Autowired
    protected PassportService passportService;
    @Autowired
    protected SafeBoxService safeBoxService;

    @Autowired
    protected EnterApplyService enterApplyService;

    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    @Autowired
    protected MemberStayService memberStayService;
    @Autowired
    protected MemberTransferService memberTransferService;
    @Autowired
    protected MemberOutService memberOutService;
    @Autowired
    protected MemberInService memberInService;

    @Autowired
    protected MemberInflowService memberInflowService;
    @Autowired
    protected MemberOutflowService memberOutflowService;
    @Autowired
    protected MemberReturnService memberReturnService;
    @Autowired
    protected MemberAbroadService memberAbroadService;
    @Autowired
    protected MemberQuitService memberQuitService;
    @Autowired
    protected RetireApplyService retireApplyService;
    @Autowired
    protected MemberStudentService memberStudentService;
    @Autowired
    protected MemberTeacherService memberTeacherService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected ApplyLogService applyLogService;
    @Autowired
    protected ApplyOpenTimeService applyOpenTimeService;
    @Autowired
    protected MemberApplyService memberApplyService;
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
    protected UnitAdminGroupService unitAdminGroupService;
    @Autowired
    protected UnitAdminService unitAdminService;
    @Autowired
    protected CadreSubWorkService cadreSubWorkService;
    @Autowired
    protected CadrePostService cadrePostService;
    @Autowired
    protected CadreMainWorkService cadreMainWorkService;
    @Autowired
    protected ExtJzgService extJzgService;
    @Autowired
    protected ExtYjsService extYjsService;
    @Autowired
    protected ExtBksService extBksService;
    @Autowired
    protected CadreInfoService cadreInfoService;
    @Autowired
    protected CadreFamliyAbroadService cadreFamliyAbroadService;
    @Autowired
    protected CadreFamliyService cadreFamliyService;
    @Autowired
    protected CadreCompanyService cadreCompanyService;
    @Autowired
    protected CadreRewardService cadreRewardService;
    @Autowired
    protected CadreResearchService cadreResearchService;
    @Autowired
    protected CadreParttimeService cadreParttimeService;
    @Autowired
    protected CadreCourseService cadreCourseService;
    @Autowired
    protected CadreWorkService cadreWorkService;
    @Autowired
    protected CadreEduService cadreEduService;
    @Autowired
    protected CadreTutorService cadreTutorService;
    @Autowired
    protected UnitTransferService unitTransferService;
    @Autowired
    protected UnitCadreTransferService unitCadreTransferService;
    @Autowired
    protected UnitCadreTransferGroupService unitCadreTransferGroupService;
    @Autowired
    protected DispatchService dispatchService;
    @Autowired
    protected DispatchTypeService dispatchTypeService;
    @Autowired
    protected DispatchCadreService dispatchCadreService;
    @Autowired
    protected DispatchUnitRelateService dispatchUnitRelateService;
    @Autowired
    protected DispatchUnitService dispatchUnitService;
    @Autowired
    protected LeaderService leaderService;
    @Autowired
    protected LeaderUnitService leaderUnitService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected HistoryUnitService historyUnitService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected MetaClassService metaClassService;
    @Autowired
    protected MetaTypeService metaTypeService;

    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected TeacherService teacherService;
    @Autowired
    protected StudentService studentService;
    @Autowired
    protected SysUserSyncService sysUserSyncService;
    @Autowired
    protected LocationService locationService;
    @Autowired
    protected CountryService countryService;
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
    protected SysConfigService sysConfigService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected Environment evironment;

    public Map<Class<?>, Class<?>> sourceMixins(){

        Map<Class<?>, Class<?>> sourceMixins = new HashMap<>();
        sourceMixins.put(SysUser.class, SysUserMixin.class);

        return sourceMixins;
    }

    public String addLog(String logType, String content, Object...params){

        if(params!=null && params.length>0)
            content = String.format(content, params);

        return logService.log(logType, content);
    }

	public Map<String, Object> success(){

		return success(null);
	}

	public static Map<String, Object> success(String msg){

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("msg", StringUtils.defaultIfBlank(msg, "success"));
		return resultMap;
	}

	public static Map<String, Object> failed(String msg){

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", false);
		resultMap.put("msg", StringUtils.defaultIfBlank(msg, "failed"));
		return resultMap;
	}

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
    protected VerifyAuth checkVerityAuth(Object entity, Integer partyId, Integer branchId ){

        VerifyAuth verifyAuth = new VerifyAuth();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        int loginUserId = shiroUser.getId();
        verifyAuth.entity = entity;

        verifyAuth.isBranchAdmin = branchMemberService.isPresentAdmin(loginUserId, branchId);
        verifyAuth.isPartyAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
        verifyAuth.isDirectBranch = partyService.isDirectBranch(partyId);
        if(!verifyAuth.isBranchAdmin && (!verifyAuth.isDirectBranch || !verifyAuth.isPartyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }
        return verifyAuth;
    }
    /**
     * 当前操作人员应该是应是申请人所在的分党委、党总支、直属党支部的管理员
     */
    protected VerifyAuth checkVerityAuth2(Object entity, Integer partyId){
        VerifyAuth verifyAuth = new VerifyAuth();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        int loginUserId = shiroUser.getId();
        verifyAuth.entity = entity;

        if(!partyMemberService.isPresentAdmin(loginUserId, partyId)){
            throw new UnauthorizedException();
        }
        verifyAuth.isParty = partyService.isParty(partyId);
        return verifyAuth;
    }

    public Map getMetaMap(){

        Map map = new HashMap<>();

        map.put("partyMap", partyService.findAll());
        map.put("staffTypeMap", metaTypeService.metaTypes("mc_branch_staff_type"));
        map.put("branchUnitTypeMap", metaTypeService.metaTypes("mc_branch_unit_type"));
        map.put("partyUnitTypeMap", metaTypeService.metaTypes("mc_party_unit_type"));

        map.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));

        map.put("partyMemberTypeMap", metaTypeService.metaTypes("mc_party_member_type"));

        map.put("partyTypeMap", metaTypeService.metaTypes("mc_part_type"));

        map.put("branchMap", branchService.findAll());

        map.put("jobMap", metaTypeService.metaTypes("mc_job"));
        map.put("flowDirectionMap", metaTypeService.metaTypes("mc_flow_direction"));

        map.put("eduTypeMap", metaTypeService.metaTypes("mc_edu"));
        map.put("learnStyleMap", metaTypeService.metaTypes("mc_learn_style"));
        map.put("schoolTypeMap", metaTypeService.metaTypes("mc_school"));

        map.put("eduMap", metaTypeService.metaTypes("mc_edu"));

        map.put("abroadTypeMap", metaTypeService.metaTypes("mc_abroad_type"));

        map.put("politicalStatusMap", metaTypeService.metaTypes("mc_political_status"));

        map.put("dispatchMap", dispatchService.findAll());

        map.put("dispatchCadreMap", dispatchCadreService.findAll());
        map.put("dispatchUnitMap", dispatchUnitService.findAll());
        map.put("postClassMap", metaTypeService.metaTypes("mc_post_class"));


        map.put("leaderTypeMap", metaTypeService.metaTypes("mc_leader_type"));
        map.put("leaderUnitTypeMap", metaTypeService.metaTypes("mc_leader_unit"));


        map.put("wayMap", metaTypeService.metaTypes("mc_dispatch_cadre_way"));
        map.put("cadreTypeMap", metaTypeService.metaTypes("mc_dispatch_cadre_type"));
        map.put("procedureMap", metaTypeService.metaTypes("mc_dispatch_cadre_procedure"));
        map.put("postMap", metaTypeService.metaTypes("mc_post"));
        map.put("unitMap", unitService.findAll());
        map.put("cadreMap", cadreService.findAll());
        map.put("unitTypeMap", metaTypeService.metaTypes("mc_unit_type"));

        map.put("dispatchTypeMap", dispatchTypeService.findAll());
        map.put("passportTypeMap", metaTypeService.metaTypes("mc_passport_type"));

        map.put("dispatchUnitTypeMap", metaTypeService.metaTypes("mc_dispatch_unit"));

        map.put("adminLevelMap", metaTypeService.metaTypes("mc_admin_level"));

        map.put("safeBoxMap", safeBoxService.findAll());

        map.put("DISPATCH_CADRE_TYPE_MAP", DispatchConstants.DISPATCH_CADRE_TYPE_MAP);

        map.put("approverTypeMap", approverTypeService.findAll());

        map.put("locationMap", locationService.codeMap());
        return map;
    }
}
