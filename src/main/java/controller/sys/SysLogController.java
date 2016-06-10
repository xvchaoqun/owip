package controller.sys;


import controller.BaseController;
import domain.SysLog;
import domain.SysLogExample;
import domain.SysUser;
import mixin.SysLogMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.Escape;
import sys.utils.FormUtils;
import sys.utils.HtmlEscapeUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SysLogController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@RequiresPermissions("sysLog:list")
	@RequestMapping("/sysLog")
	public String sysLog() {

		return "index";
	}
	@RequiresPermissions("sysLog:list")
	@RequestMapping("/sysLog_page")
	public String sysLog_page( Integer userId, ModelMap modelMap) {

		if (userId != null) {
			modelMap.put("sysUser", sysUserService.findById(userId));
		}

		modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_sys_log"));
		return "sys/sysLog/sysLog_page";
	}
	@RequiresPermissions("sysLog:list")
	@RequestMapping("/sysLog_data")
	@ResponseBody
	public void sysLog_data(HttpServletRequest request, Integer pageSize, Integer pageNo,
							Integer userId, Integer typeId, String content, String ip) throws IOException {
		
		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);
		
		content = HtmlEscapeUtils.escape(Escape.unescape(content));
		
		SysLogExample example = new SysLogExample();
		SysLogExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
		if(typeId!=null) criteria.andTypeIdEqualTo(typeId);
		example.setOrderByClause(" id desc");

		if (userId != null) {
			criteria.andUserIdEqualTo(userId);
		}

		if(StringUtils.isNotBlank(content)){
			criteria.andContentLike("%" + content+"%");
		}
		if(StringUtils.isNotBlank(ip)){
			criteria.andIpLike("%"+ip+"%");
		}

		int count = sysLogMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){
			
			pageNo = Math.max(1, pageNo-1);
		}
		List<SysLog> sysLogs = sysLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

		CommonList commonList = new CommonList(count, pageNo, pageSize);

		Map resultMap = new HashMap();
		resultMap.put("rows", sysLogs);
		resultMap.put("records", count);
		resultMap.put("page", pageNo);
		resultMap.put("total", commonList.pageNum);

		Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
		sourceMixins.put(SysLog.class, SysLogMixin.class);
		JSONUtils.jsonp(resultMap, sourceMixins);
		return;
	}

	@RequiresPermissions("sysLog:del")
	@RequestMapping(value="/sysLog_del", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysLog_del(@CurrentUser SysUser user, Integer id, Integer type, HttpServletRequest request) {
		
		if(id!=null){

			SysLog record = new SysLog();
			record.setId(id);
			record.setStatus(SystemConstants.UNAVAILABLE);
			sysLogMapper.updateByPrimaryKeySelective(record);
			
			logger.info(user.getUsername() + " 删除日志:" + id + ":" + record.getStatus());
		}
		
		return success(FormUtils.SUCCESS);
	}
}
