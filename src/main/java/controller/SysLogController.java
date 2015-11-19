package controller;


import domain.SysLog;
import domain.SysLogExample;
import domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class SysLogController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/sysLog")
	public String sysLog() {

		return "index";
	}

	@RequestMapping("/sysLog_page")
	public String sysLog_page(HttpServletRequest request, Integer pageSize, Integer pageNo,
							  Integer typeId, String content, ModelMap modelMap) {
		
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

		if(StringUtils.isNotBlank(content)){
			criteria.andContentLike("%"+content+"%");
		}
		int count = sysLogMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){
			
			pageNo = Math.max(1, pageNo-1);
		}
		List<SysLog> sysLogs = sysLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));
		modelMap.put("sysLogs", sysLogs);
		
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		String searchStr = "&pageSize="+pageSize;
		if(StringUtils.isNotBlank(content)){
			searchStr += "&content" + content;
		}
		if(typeId!=null){
			searchStr += "&typeId" + typeId;
		}
		commonList.setSearchStr(searchStr);
		modelMap.put("commonList", commonList);
		modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_sys_log"));
		return "sysLog/sysLog_page";
	}

	@RequiresRoles("admin")
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
