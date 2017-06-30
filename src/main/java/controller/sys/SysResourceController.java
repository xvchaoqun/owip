package controller.sys;

import controller.BaseController;
import domain.sys.SysResource;
import domain.sys.SysResourceExample;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class SysResourceController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping("/sysResource")
	public String sysResource(ModelMap modelMap) {

		modelMap.put("sysResources", sysResourceService.getSortedSysResources().values());
		return "sys/sysResource/sysResource_page";
	}
	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping(value="/sysResource_au", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysResource_au(@CurrentUser SysUserView loginUser,
								 @RequestParam(required = false, value = "countCacheKeys")Byte[] countCacheKeys,
								 SysResource record, HttpServletRequest request) {

		if(countCacheKeys!=null && countCacheKeys.length>0){
			record.setCountCacheKeys(StringUtils.join(countCacheKeys, ","));
		}

		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andNameEqualTo(record.getPermission());
		List<SysResource> byExample = sysResourceMapper.selectByExample(example);
		if(byExample!=null && byExample.size()>0){
			
			if(record.getId() == null ||
					byExample.get(0).getId().intValue() != record.getId())
				return failed(FormUtils.DUPLICATE);
		}

		Integer parentId = record.getParentId();
		SysResource parent = sysResourceMapper.selectByPrimaryKey(parentId);
		record.setParentIds(parent.getParentIds() + parentId + "/");

		if(record.getId() == null){
			
			record.setAvailable(SystemConstants.AVAILABLE);
			sysResourceService.insert(record);
			logger.info(addLog(SystemConstants.LOG_ADMIN, "添加资源：%s", JSONUtils.toString(record, false)));
			
		}else{
			
			sysResourceService.updateByPrimaryKeySelective(record);
			logger.info(addLog(SystemConstants.LOG_ADMIN, "更新资源：%s", JSONUtils.toString(record, false)));
		}
		
		return success(FormUtils.SUCCESS);
	}
	@RequiresRoles(SystemConstants.ROLE_ADMIN)
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
	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping(value="/sysResource_del", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysResource_del(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request) {

		if(id!=null){

			sysResourceService.del(id);
			logger.info(addLog(SystemConstants.LOG_ADMIN, "删除资源：%s", id));
		}
		
		return success(FormUtils.SUCCESS);
	}

	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping("/sysResource_selects")
	@ResponseBody
	public Map sysResource_selects(Integer pageSize, String[] type, Integer pageNo,String searchStr) throws IOException {

		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		SysResourceExample example = new SysResourceExample();
		SysResourceExample.Criteria criteria = example.createCriteria().andAvailableEqualTo(SystemConstants.AVAILABLE);
		//criteria.andTypeNotEqualTo(SystemConstants.RESOURCE_TYPE_FUNCTION);
		if(type!=null && type.length>0)
			criteria.andTypeIn(Arrays.asList(type));
		example.setOrderByClause("parent_id asc, sort_order desc");

		if(StringUtils.isNotBlank(searchStr)){
			criteria.andNameLike("%" + searchStr + "%");
		}

		int count = sysResourceMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){

			pageNo = Math.max(1, pageNo-1);
		}
		List<SysResource> sysResources = sysResourceMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

		Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources();
		List<Select2Option> options = new ArrayList<Select2Option>();
		for(SysResource sysResource:sysResources){
			String text = "";
			String parentIds = sysResource.getParentIds();

			if(sysResource.getId()==1) continue;// 不包含顶级节点

			for (String _parentId : parentIds.split("/")) {
				Integer parentId = Integer.valueOf(_parentId);
				if(parentId>1) { // 不包含顶级节点
					SysResource _sysResource = resourceMap.get(parentId);
					if(_sysResource!=null) text += _sysResource.getName() + "-";
				}
			}

			Select2Option option = new Select2Option();
			option.setText(text + sysResource.getName() + "(" + sysResource.getPermission() + ")");
			option.setId(sysResource.getId() + "");

			options.add(option);
		}

		Map resultMap = success();
		resultMap.put("totalCount", count);
		resultMap.put("options", options);
		return resultMap;
	}
}
