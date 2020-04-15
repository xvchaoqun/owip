package controller.sys;


import controller.BaseController;
import domain.sys.SysLoginLog;
import domain.sys.SysLoginLogExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
public class SysLoginLogController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 切换账号登录
	@RequiresPermissions(value={"sysLogin:switch", "sysLogin:switchParty"}, logical = Logical.OR)
	@RequestMapping("/sysLogin_switch")
	public String sysLogin_switch(ModelMap modelMap) {

		return "sys/sysLoginLog/sysLogin_switch";
	}

	@RequiresPermissions(value={"sysLogin:switch", "sysLogin:switchParty"}, logical = Logical.OR)
	@RequestMapping("/sysLogin_switch_status")
	@ResponseBody
	public Map sysLogin_switch_status(String username) {

		Map<String, Object> resultMap = success(FormUtils.SUCCESS);
		resultMap.put("onlineSession", sysLoginLogService.getOnlineSession(username));

		boolean canSwitch = true;
		boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
		if(!superAccount) {
			Set<String> roles = sysUserService.findRoles(username);
			if (roles.contains(RoleConstants.ROLE_ADMIN)) {
				canSwitch = false;
			}else if(!ShiroHelper.isPermitted("sysLogin:switch")){
				// 仅允许切换党组织管理员权限的账号，不允许切换无限制(超管除外)切换权限的人
				Set<String> permissions = sysUserService.findPermissions(username, false);
				if(permissions.contains("sysLogin:switch")){
					canSwitch = false;
				}
			}
		}
		resultMap.put("canSwitch", canSwitch);

		return resultMap;
	}

	@RequiresPermissions("sysLoginLog:list")
	@RequestMapping("/sysLoginLog")
	public String sysLoginLog( Integer userId, ModelMap modelMap) {

		if (userId != null) {
			modelMap.put("sysUser", sysUserService.findById(userId));
		}

		return "sys/sysLoginLog/sysLoginLog_page";
	}
	@RequiresPermissions("sysLoginLog:list")
	@RequestMapping("/sysLoginLog_data")
	@ResponseBody
	public void sysLoginLog_data(HttpServletRequest request, String username, Integer pageSize, Integer pageNo,
							Integer userId, Byte type, String ip) throws IOException {
		
		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);
		
		SysLoginLogExample example = new SysLoginLogExample();
		SysLoginLogExample.Criteria criteria = example.createCriteria();
		if(type!=null) criteria.andTypeEqualTo(type);
		example.setOrderByClause(" id desc");

		boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
		if(!superAccount){
			criteria.andUsernameNotIn(new ArrayList<>(CmTag.getSuperAccounts()));
		}

		if (userId != null) {
			criteria.andUserIdEqualTo(userId);
		}
		if(StringUtils.isNotBlank(username)){
			criteria.andUsernameLike(SqlUtils.like(username));
		}
		if(StringUtils.isNotBlank(ip)){
			criteria.andLoginIpLike(SqlUtils.like(ip));
		}

		long count = sysLoginLogMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){
			
			pageNo = Math.max(1, pageNo-1);
		}
		List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

		CommonList commonList = new CommonList(count, pageNo, pageSize);

		Map resultMap = new HashMap();
		resultMap.put("rows", sysLoginLogs);
		resultMap.put("records", count);
		resultMap.put("page", pageNo);
		resultMap.put("total", commonList.pageNum);

		Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
		//baseMixins.put(SysLoginLog.class, SysLoginLogMixin.class);
		JSONUtils.jsonp(resultMap, baseMixins);
		return;
	}
}
