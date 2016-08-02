package controller.sys;

import controller.BaseController;
import domain.sys.SysResource;
import domain.sys.SysResourceExample;
import domain.sys.SysUser;
import org.apache.commons.lang.StringUtils;
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
import sys.tool.jackson.Select2Option;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SysResourceController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresRoles("admin")
	@RequestMapping("/sysResource")
	public String sysResource() {

		return "index";
	}
	@RequiresRoles("admin")
	@RequestMapping("/sysResource_page")
	public String sysResource_page(ModelMap modelMap) {

		modelMap.put("sysResources", sysResourceService.getSortedSysResources().values());
		return "sys/sysResource/sysResource_page";
	}
	@RequiresRoles("admin")
	@RequestMapping(value="/sysResource_au", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysResource_au(@CurrentUser SysUser loginUser, SysResource sysResource, HttpServletRequest request) {

		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andNameEqualTo(sysResource.getPermission());
		List<SysResource> byExample = sysResourceMapper.selectByExample(example);
		if(byExample!=null && byExample.size()>0){
			
			if(sysResource.getId() == null ||
					byExample.get(0).getId().intValue() != sysResource.getId())
				return failed(FormUtils.DUPLICATE);
		}

		Integer parentId = sysResource.getParentId();
		SysResource parent = sysResourceMapper.selectByPrimaryKey(parentId);
		sysResource.setParentIds(parent.getParentIds() + parentId + "/");

		if(sysResource.getId() == null){
			
			sysResource.setAvailable(SystemConstants.AVAILABLE);
			sysResourceService.insert(sysResource);
			logger.info(addLog(SystemConstants.LOG_ADMIN, "添加资源：%s", JSONUtils.toString(sysResource, false)));
			
		}else{
			
			sysResourceService.updateByPrimaryKeySelective(sysResource);
			logger.info(addLog(SystemConstants.LOG_ADMIN, "更新资源：%s", JSONUtils.toString(sysResource, false)));
		}
		
		return success(FormUtils.SUCCESS);
	}
	@RequiresRoles("admin")
	@RequestMapping("/sysResource_au")
	public String sysResource_au(Integer id, Integer parentId, ModelMap modelMap) {

		if(id != null){

			SysResource sysResource = sysResourceMapper.selectByPrimaryKey(id);
			modelMap.put("sysResource", sysResource);
			modelMap.addAttribute("op", "修改");

			Integer _parentId = sysResource.getParentId();
			if(_parentId!=null&&_parentId>0){
				SysResource parent = sysResourceMapper.selectByPrimaryKey(_parentId);
				modelMap.addAttribute("parent", parent);
			}
		}else if(parentId!=null){

			SysResource parent = sysResourceMapper.selectByPrimaryKey(parentId);
			modelMap.addAttribute("parent", parent);
			SysResource child = new SysResource();
	        child.setParentId(parentId);
	        
	        //child.setParentIds(parent.getParentIds() + parent.getId() + "/");
	        modelMap.addAttribute("sysResource", child);
	        modelMap.addAttribute("op", "新增子节点");
		}
		
		return "sys/sysResource/sysResource_au";
	}
	@RequiresRoles("admin")
	@RequestMapping(value="/sysResource_del", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysResource_del(@CurrentUser SysUser loginUser, Integer id, HttpServletRequest request) {
		
		if(id!=null){
			
			sysResourceService.del(id);
			logger.info(addLog(SystemConstants.LOG_ADMIN, "删除资源：%s", id));
		}
		
		return success(FormUtils.SUCCESS);
	}

	@RequiresRoles("admin")
	@RequestMapping("/sysResource_selects")
	@ResponseBody
	public Map sysResource_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		SysResourceExample example = new SysResourceExample();
		SysResourceExample.Criteria criteria = example.createCriteria().andAvailableEqualTo(SystemConstants.AVAILABLE);
		criteria.andTypeNotEqualTo(SystemConstants.RESOURCE_TYPE_FUNCTION);
		example.setOrderByClause(" sort_order desc");

		if(StringUtils.isNotBlank(searchStr)){
			criteria.andNameLike("%" + searchStr + "%");
		}

		int count = sysResourceMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){

			pageNo = Math.max(1, pageNo-1);
		}
		List<SysResource> sysResources = sysResourceMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

		List<Select2Option> options = new ArrayList<Select2Option>();
		for(SysResource sysResource:sysResources){

			Select2Option option = new Select2Option();
			option.setText(sysResource.getName());
			option.setId(sysResource.getId() + "");

			options.add(option);
		}

		Map resultMap = success();
		resultMap.put("totalCount", count);
		resultMap.put("options", options);
		return resultMap;
	}
}
