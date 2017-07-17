package controller.sys;


import controller.BaseController;
import domain.sys.SysLoginLog;
import domain.sys.SysLoginLogExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.tool.paging.CommonList;
import sys.utils.Escape;
import sys.utils.HtmlEscapeUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SysLoginLogController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("sysLoginLog:list")
	@RequestMapping("/sysLoginLog")
	public String sysLoginLog( Integer userId, ModelMap modelMap) {

		if (userId != null) {
			modelMap.put("sysUser", sysUserService.findById(userId));
		}

		modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_sys_log"));
		return "sys/sysLoginLog/sysLoginLog_page";
	}
	@RequiresPermissions("sysLoginLog:list")
	@RequestMapping("/sysLoginLog_data")
	@ResponseBody
	public void sysLoginLog_data(HttpServletRequest request, String username, Integer pageSize, Integer pageNo,
							Integer userId, Byte type, String content, String ip) throws IOException {
		
		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);
		
		content = HtmlEscapeUtils.escape(Escape.unescape(content), "UTF-8");
		
		SysLoginLogExample example = new SysLoginLogExample();
		SysLoginLogExample.Criteria criteria = example.createCriteria();
		if(type!=null) criteria.andTypeEqualTo(type);
		example.setOrderByClause(" id desc");

		if (userId != null) {
			criteria.andUserIdEqualTo(userId);
		}
		if(StringUtils.isNotBlank(username)){
			criteria.andUsernameLike("%" + username + "%");
		}
		if(StringUtils.isNotBlank(ip)){
			criteria.andLoginIpLike("%" + ip + "%");
		}

		int count = sysLoginLogMapper.countByExample(example);
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
