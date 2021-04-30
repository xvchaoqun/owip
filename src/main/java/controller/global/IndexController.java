package controller.global;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.sys.HtmlFragment;
import domain.sys.SysUserView;
import ext.utils.CasUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.member.MemberRegService;
import service.oa.OaTaskUserService;
import service.sys.SysMsgService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseController {

	@Autowired(required = false)
	private MemberRegService memberRegService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = false)
	private SysMsgService sysMsgService;
	@Autowired(required = false)
	private OaTaskUserService oaTaskUserService;

	// 权限、系统设置等初始化js
	@RequestMapping("/js_init")
	public String js_init() {

		return "common/js_init";
	}

	// 系统消息数量
	@RequestMapping("/info")
	@ResponseBody
	public Map info(HttpServletRequest request) {

		Map<String, Integer> menuCountMap = new HashMap<>();
		int sysMsgCount = sysMsgService.getSysMsgCount(ShiroHelper.getCurrentUserId());
		if(sysMsgCount>0){
			menuCountMap.put("sysMsg:list", sysMsgCount);
		}

		if(oaTaskUserService!=null){

			int oaTaskUserCount = oaTaskUserService.getTaskUserCount(ShiroHelper.getCurrentUserId());
			if (oaTaskUserCount>0){
				menuCountMap.put("userOaTask:*",oaTaskUserCount);
			}
		}

		Map<String, Map> infoMap = new HashMap<>();
		infoMap.put("menuCountMap", menuCountMap);

		return infoMap;
	}

	@RequestMapping("/monitor")
	@ResponseBody
	public void monitor(HttpServletRequest request, String type) {

		String userAgent = RequestUtils.getUserAgent(request);
		String ip = IpUtils.getRealIp(request);

		logger.warn(String.format("monitor type=%s, userAgent=%s, ip=%s, username=%s, cas=%s", type,
				userAgent, ip, ShiroHelper.getCurrentUsername(), CasUtils.getName(request)));
	}

	@RequestMapping("/faq")
	public String faq() {
		return "page";
	}

	@RequestMapping("/faq_page")
	public String faq_page() {

		return "faq";
	}

	@RequestMapping("/help")
	public String help(ModelMap modelMap) {

		List<HtmlFragment> hfDocs = htmlFragmentService.twoLevelTree(SystemConstants.HTML_FRAGMENT_HELP_DOC);
		modelMap.put("hfDocs", hfDocs);

		return "help";
	}

	@RequiresPermissions("index:home")
	@RequestMapping("/")
	public String home_page() {

		return "index";
	}
	@RequiresPermissions("index:home")
	@RequestMapping("/index")
	public String index(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

		if(ShiroHelper.hasRole(RoleConstants.ROLE_REG)){
			modelMap.put("memberReg", memberRegService.findByUserId(loginUser.getId()));
			return "member/user/memberReg/memberReg";
		}

		String toPage = "user_base"; // 默认进入个人基本信息页
		if(ShiroHelper.isPermitted("stat:cadre")){
			toPage = "stat_cadre_index_page"; // 干部信息统计页
		}else if(ShiroHelper.isPermitted("stat:ow")){
			toPage = "stat_ow_page"; // 党建信息统计页
		}else if(ShiroHelper.isPermitted("stat:party")){
			toPage = "stat_party_page"; // 党建信息统计页
		}else if(ShiroHelper.isPermitted("stat:branch")){
			toPage = "stat_branch_page"; // 党建信息统计页
		}
		modelMap.put("to", toPage);
		int userId = ShiroHelper.getCurrentUserId();
		modelMap.put("sysMsgCount", sysMsgService.count(SystemConstants.SYS_MSG_STATUS_UNCONFIRM));

		//是所在支部的管理员才能看到支部信息统计
		List<Integer> branchIdList=loginUserService.adminBranchIdList();
		Member member = memberMapper.selectByPrimaryKey(userId);
		boolean showBranch = false;
		if (member != null&&member.getBranchId()!=null) {
			if (branchIdList.contains(member.getBranchId())){
				showBranch = true;
			}
		}
		modelMap.put("showBranch", showBranch);

		return "index_page";
	}

	@RequiresPermissions("index:self")
	@RequestMapping("/user_base")
	public String user_base() {

		int userId = ShiroHelper.getCurrentUserId();
		if(ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CADRE_CJ, RoleConstants.ROLE_CADRE_KJ)
				&& ShiroHelper.isPermitted("userCadre:menu")){
			// 是干部且有干部个人信息查看的权限 （可能校领导去除了这个权限）
			CadreView cadre = cadreService.dbFindByUserId(userId);
			return "forward:/cadre_base?_auth=self&cadreId=" + cadre.getId();

		}else if(ShiroHelper.hasRole(RoleConstants.ROLE_MEMBER)
			&& ShiroHelper.isPermitted("userMember:menu")){
			// 是党员且有干部个人信息查看的权限 （可能校领导去除了这个权限）
			return "forward:/user/member";
		}else{
			return "forward:/sysUser_base?userId="+userId;
		}
	}
}
