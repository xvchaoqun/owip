package controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import service.*;
import shiro.PasswordHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class BaseController extends BaseMapper {


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
    protected UnitTransferService unitTransferService;
    @Autowired
    protected UnitTransferItemService unitTransferItemService;
    @Autowired
    protected UnitCadreTransferService unitCadreTransferService;
    @Autowired
    protected UnitCadreTransferGroupService unitCadreTransferGroupService;
    @Autowired
    protected UnitCadreTransferItemService unitCadreTransferItemService;
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
