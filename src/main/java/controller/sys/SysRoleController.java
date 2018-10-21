package controller.sys;

import controller.BaseController;
import domain.sys.SysRole;
import domain.sys.SysRoleExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.Escape;
import sys.utils.FormUtils;
import sys.utils.HtmlEscapeUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class SysRoleController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("sysRole:list")
	@RequestMapping("/sysRole")
	public String sysRole() {

		return "sys/sysRole/sysRole_page";
	}

	@RequiresPermissions("sysRole:list")
	@RequestMapping("/sysRole_data")
	@ResponseBody
	public void sysRole_data(HttpServletRequest request, Integer pageSize, Integer pageNo, String searchStr) throws IOException {
		
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
		example.setOrderByClause(" sort_order desc");
		if(StringUtils.isNotBlank(searchStr)){
			example.createCriteria().andRoleLike("%" + searchStr + "%");
		}
		long count = sysRoleMapper.countByExample(example);
		if((pageNo-1)*pageSize >= count){
			
			pageNo = Math.max(1, pageNo-1);
		}
		List<SysRole> sysRoles = sysRoleMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		Map resultMap = new HashMap();
		resultMap.put("rows", sysRoles);
		resultMap.put("records", count);
		resultMap.put("page", pageNo);
		resultMap.put("total", commonList.pageNum);

		Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
		JSONUtils.jsonp(resultMap, baseMixins);
		return;
	}

	@RequiresPermissions("sysRole:edit")
	@RequestMapping(value="/sysRole_au", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysRole_au(@CurrentUser SysUserView loginUser,
			SysRole sysRole, 
			@RequestParam(value="resIds[]",required=false) Integer[] resIds,
			@RequestParam(value="m_resIds[]",required=false) Integer[] m_resIds,
			HttpServletRequest request) {

		String role = StringUtils.trimToNull(StringUtils.lowerCase(sysRole.getRole()));
		if(StringUtils.equals(role, RoleConstants.ROLE_ADMIN)) {
			throw new IllegalArgumentException("不允许添加admin角色");
		}
		if (role!=null && sysRoleService.idDuplicate(sysRole.getId(), role)) {
			return failed("添加重复");
		}

		if(resIds==null || resIds.length==0)
			sysRole.setResourceIds("-1");
		else
			sysRole.setResourceIds(StringUtils.join(resIds, ","));

		if(m_resIds==null || m_resIds.length==0)
			sysRole.setmResourceIds("-1");
		else
			sysRole.setmResourceIds(StringUtils.join(m_resIds, ","));

		if(sysRole.getId() == null){
			if(role==null){
				throw new IllegalArgumentException("角色不能为空");
			}
			sysRoleService.insertSelective(sysRole);
			logger.info(addLog(LogConstants.LOG_ADMIN, "创建角色：%s", JSONUtils.toString(sysRole, MixinUtils.baseMixins(), false)));
		}else{
			sysRoleService.updateByPrimaryKeySelective(sysRole);
			logger.info(addLog(LogConstants.LOG_ADMIN, "更新角色：%s", JSONUtils.toString(sysRole, MixinUtils.baseMixins(), false)));
		}
		
		return success(FormUtils.SUCCESS);
	}

	@RequiresPermissions("sysRole:edit")
	@RequestMapping("/sysRole_au")
	public String sysRole_au(Integer id, ModelMap modelMap) throws IOException {


		modelMap.addAttribute("op", "添加");

		Set<Integer> selectIdSet = new HashSet<Integer>();
		Set<Integer> mSelectIdSet = new HashSet<Integer>();
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
			String mResourceIdsStr = sysRole.getmResourceIds();
			if(mResourceIdsStr!=null){
				String[] resourceIds = mResourceIdsStr.split(",");
				for(String resourceId:resourceIds){
					if(StringUtils.isNotBlank(resourceId)){
						mSelectIdSet.add(Integer.parseInt(resourceId));
					}
				}
			}
			modelMap.put("sysRole", sysRole);
			modelMap.addAttribute("op", "修改");
		}
		
		TreeNode tree = sysResourceService.getTree(selectIdSet, false);
		modelMap.put("tree", JSONUtils.toString(tree));

		TreeNode mTree = sysResourceService.getTree(mSelectIdSet, true);
		modelMap.put("mTree", JSONUtils.toString(mTree));
		
		return "sys/sysRole/sysRole_au";
	}
	@RequiresPermissions("sysRole:del")
	@RequestMapping(value="/sysRole_del", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysRole_del(@CurrentUser SysUserView loginUser,
							  @RequestParam(value = "ids[]") Integer[] ids,
							  HttpServletRequest request) {
		
		sysRoleService.batchDel(ids);
		logger.info(addLog(LogConstants.LOG_ADMIN, "删除角色：%s", StringUtils.join(ids, ",")));

		return success(FormUtils.SUCCESS);
	}

	@RequiresPermissions("sysRole:edit")
	@RequestMapping(value = "/sysRole_changeOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map do_sysRole_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

		sysRoleService.changeOrder(id, addNum);
		logger.info(addLog(LogConstants.LOG_ADMIN, "角色调序：%s,%s", id, addNum));
		return success(FormUtils.SUCCESS);
	}

	@RequiresPermissions("sysRole:edit")
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
			sysRoleService.updateByPrimaryKeySelective(record);
			logger.info(addLog(LogConstants.LOG_ADMIN, "更改角色是否系统自动控制：%s, %s", role, !isSysHold));
		}

		return success(FormUtils.SUCCESS);
	}
}
