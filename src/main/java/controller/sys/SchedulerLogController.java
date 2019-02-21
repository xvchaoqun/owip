package controller.sys;


import controller.BaseController;
import domain.sys.SchedulerLog;
import domain.sys.SchedulerLogExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.sys.SchedulerJobMapper;
import persistence.sys.SchedulerLogMapper;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SchedulerLogController extends BaseController {

	@Autowired
	private SchedulerLogMapper schedulerLogMapper;
	@Autowired
	private SchedulerJobMapper schedulerJobMapper;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("schedulerJob:list")
	@RequestMapping("/schedulerLog")
	public String schedulerLog(Integer jobId,
							   @RequestParam(required = false, defaultValue = "1")Byte cls,
							   ModelMap modelMap) {

		modelMap.put("cls", cls);

		if (jobId != null) {
			modelMap.put("job", schedulerJobMapper.selectByPrimaryKey(jobId));
		}

		return "sys/schedulerLog/schedulerLog_page";
	}
	@RequiresPermissions("schedulerJob:list")
	@RequestMapping("/schedulerLog_data")
	@ResponseBody
	public void schedulerLog_data(HttpServletRequest request, Integer pageSize, Integer pageNo,
								  Integer jobId,
								  @RequestDateRange DateRange triggerTime) throws IOException {
		
		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);
		
		SchedulerLogExample example = new SchedulerLogExample();
		SchedulerLogExample.Criteria criteria = example.createCriteria()
				.andStatusEqualTo(SystemConstants.SCHEDULER_JOB_WASEXECUTED);
		example.setOrderByClause(" id desc");

		if(jobId!=null){
			criteria.andJobIdEqualTo(jobId);
		}
		if(triggerTime.getStart()!=null){
			criteria.andTriggerTimeGreaterThanOrEqualTo(triggerTime.getStart());
		}
		if(triggerTime.getEnd()!=null){
			criteria.andTriggerTimeLessThanOrEqualTo(triggerTime.getEnd());
		}

		int count = (int) schedulerLogMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){
			
			pageNo = Math.max(1, pageNo-1);
		}
		List<SchedulerLog> schedulerLogs = schedulerLogMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

		CommonList commonList = new CommonList(count, pageNo, pageSize);

		Map resultMap = new HashMap();
		resultMap.put("rows", schedulerLogs);
		resultMap.put("records", count);
		resultMap.put("page", pageNo);
		resultMap.put("total", commonList.pageNum);

		Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
		JSONUtils.jsonp(resultMap, baseMixins);
		return;
	}
}
