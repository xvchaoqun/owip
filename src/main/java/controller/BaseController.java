package controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import service.*;
import service.cadre.*;
import service.dispatch.DispatchCadreService;
import service.dispatch.DispatchService;
import service.dispatch.DispatchUnitRelateService;
import service.dispatch.DispatchUnitService;
import service.ext.ExtBksService;
import service.ext.ExtJzgService;
import service.ext.ExtYjsService;
import service.party.*;
import service.sys.*;
import service.unit.*;
import shiro.PasswordHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class BaseController extends BaseMapper {

    @Autowired
    protected SyncUserService syncUserService;

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
    protected BranchMemberService branchMemberService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected PartyMemberGroupService partyMemberGroupService;
    @Autowired
    protected PartyMemberService partyMemberService;
    @Autowired
    protected BranchService branchService;

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
    protected UnitTransferService unitTransferService;
    @Autowired
    protected UnitCadreTransferService unitCadreTransferService;
    @Autowired
    protected UnitCadreTransferGroupService unitCadreTransferGroupService;
    @Autowired
    protected DispatchService dispatchService;
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
    protected LocationService locationService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected SysRoleService sysRoleService;
    @Autowired
    protected SysResourceService sysResourceService;
    @Autowired
    protected LogService logService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected Environment environment;

    public String addLog(HttpServletRequest request, String logType, String content, Object...params){

        if(params!=null && params.length>0)
            content = String.format(content, params);

        return logService.log(logType, content, request);
    }

	public Map<String, Object> success(){

		return success(null);
	}

	public Map<String, Object> success(String msg){

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("msg", StringUtils.defaultIfBlank(msg, "success"));
		return resultMap;
	}

	public Map<String, Object> failed(String msg){

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", false);
		resultMap.put("msg", StringUtils.defaultIfBlank(msg, "failed"));
		return resultMap;
	}
}
