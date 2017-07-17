package controller.sys;

import controller.BaseController;
import domain.sys.SysRole;
import domain.sys.SysRoleExample;
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
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.Escape;
import sys.utils.FormUtils;
import sys.utils.HtmlEscapeUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class SysRoleController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping("/sysRole")
	public String sysRole(HttpServletRequest request, Integer pageSize, Integer pageNo, String searchStr, ModelMap modelMap) {
		
		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);
		
		searchStr = HtmlEscapeUtils.escape(Escape.unescape(searchStr), "UTF-8");
		
		SysRoleExample example = new SysRoleExample();
		//example.createCriteria().andStatusEqualTo(0);
		example.setOrderByClause(" id desc");
		if(StringUtils.isNotBlank(searchStr)){
			example.createCriteria().andRoleLike("%" + searchStr + "%");
		}
		int count = sysRoleMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){
			
			pageNo = Math.max(1, pageNo-1);
		}
		List<SysRole> sysRoles = sysRoleMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));
		modelMap.put("sysRoles", sysRoles);
		
		CommonList commonList = new CommonList(count, pageNo, pageSize);
		if(StringUtils.isBlank(searchStr))
			commonList.setSearchStr("&pageSize="+pageSize);
		else
			commonList.setSearchStr("&searchStr=" + Escape.escape(Escape.escape(Escape.escape(searchStr))) + "&pageSize="+pageSize);
		modelMap.put("commonList", commonList);
		
		return "sys/sysRole/sysRole_page";
	}

	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping(value="/sysRole_au", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysRole_au(@CurrentUser SysUserView loginUser,
			SysRole sysRole, 
			@RequestParam(value="resIds[]",required=false) Integer[] resIds,
			HttpServletRequest request) {

		String role = StringUtils.trimToNull(StringUtils.lowerCase(sysRole.getRole()));
		if(StringUtils.equals(role, SystemConstants.ROLE_ADMIN)) {
			throw new IllegalArgumentException("不允许添加admin角色");
		}
		if (role!=null && sysRoleService.idDuplicate(sysRole.getId(), role)) {
			return failed("添加重复");
		}

		if(resIds==null || resIds.length==0)
			sysRole.setResourceIds("-1");
		else
			sysRole.setResourceIds(StringUtils.join(resIds, ","));

		if(sysRole.getId() == null){
			if(role==null){
				throw new IllegalArgumentException("角色不能为空");
			}
			sysRoleService.insert(sysRole);
			logger.info(addLog(SystemConstants.LOG_ADMIN, "创建角色：%s", JSONUtils.toString(sysRole, false)));
		}else{
			SysRole oldSysRole = sysRoleMapper.selectByPrimaryKey(sysRole.getId());
			sysRoleService.updateByPrimaryKeySelective(sysRole, sysRole.getRole(), oldSysRole.getRole());
			logger.info(addLog(SystemConstants.LOG_ADMIN, "更新角色：%s", JSONUtils.toString(sysRole, false)));
		}
		
		return success(FormUtils.SUCCESS);
	}

	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping("/sysRole_au")
	public String sysRole_au(Integer id, ModelMap modelMap) throws IOException {

		Set<Integer> selectIdSet = new HashSet<Integer>();
		modelMap.addAttribute("op", "添加");
		
		if(id != null){
			
			SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
			String resourceIdsStr = sysRole.getResourceIds();
			if(resourceIdsStr!=null){
				String[] resourceIds = resourceIdsStr.split(",");
				for(String resourceId:resourceIds){
					if(StringUtils.isNotBlank(resourceId)){
						
						selectIdSet.add(Integer.parseInt(resourceId));
					}
				}
			}
			modelMap.put("sysRole", sysRole);
			modelMap.addAttribute("op", "修改");
		}
		
		TreeNode tree = sysResourceService.getTree(selectIdSet);
		modelMap.put("tree", JSONUtils.toString(tree));
		
		return "sys/sysRole/sysRole_au";
	}
	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping(value="/sysRole_del", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysRole_del(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request) {
		
		if(id!=null){
			SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
			sysRoleService.del(id, sysRole.getRole());
			logger.info(addLog(SystemConstants.LOG_ADMIN, "删除角色：%s", id));
		}
		
		return success(FormUtils.SUCCESS);
	}

	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping(value="/sysRole_updateIsSysHold", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysRole_updateIsSysHold(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request) {

		if(id!=null){
			SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
			if(sysRole.getIsSysHold()==null)
				sysRole.setIsSysHold(false);
			String role = sysRole.getRole();
			Boolean isSysHold = sysRole.getIsSysHold();

			SysRole record = new SysRole();
			record.setId(id);
			record.setIsSysHold(!isSysHold);
			sysRoleService.updateByPrimaryKeySelective(record, role, role);
			logger.info(addLog(SystemConstants.LOG_ADMIN, "更改角色是否系统自动控制：%s, %s", role, !isSysHold));
		}

		return success(FormUtils.SUCCESS);
	}
}
