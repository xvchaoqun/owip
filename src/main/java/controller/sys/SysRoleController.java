package controller.sys;

import controller.BaseController;
import controller.global.OpException;
import domain.sys.SysResource;
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
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
public class SysRoleController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("sysRole:list")
	@RequestMapping("/sysRole_users")
	public String sysRole_users(int roleId, ModelMap modelMap) {

		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
		modelMap.put("sysRole", sysRole);

		return "sys/sysRole/sysRole_users";
	}

	@RequiresPermissions("sysRole:list")
	@RequestMapping("/sysRole")
	public String sysRole(@RequestParam(required = false, defaultValue = SystemConstants.SYS_ROLE_TYPE_ADD+"")Byte type, ModelMap modelMap) {
		modelMap.put("type", type);
		return "sys/sysRole/sysRole_page";
	}

	@RequiresPermissions("sysRole:list")
	@RequestMapping("/sysRole_data")
	@ResponseBody
	public void sysRole_data(HttpServletRequest request,
							 Integer resourceId,
							 String code,
							 String name,
							 Byte type,// 1 加权限  2 减权限
							 Integer pageSize,
							 Integer pageNo) throws IOException {
		
		if (null == pageSize) {
			pageSize = springProps.pageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);
		
		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" sort_order desc");

		if(resourceId!=null){
			SysResource sysResource = sysResourceMapper.selectByPrimaryKey(resourceId);
			if(sysResource.getIsMobile()) {
				criteria.andMResourceIdsContain(resourceId);
			}else{
				criteria.andResourceIdsContain(resourceId);
			}
		}

		if(StringUtils.isNotBlank(code)){
			criteria.andCodeLike(SqlUtils.like(code));
		}
		if(StringUtils.isNotBlank(name)){
			criteria.andNameLike(SqlUtils.like(name));
		}
		if(type!=null){
			criteria.andTypeEqualTo(type);
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

		String code = StringUtils.trimToNull(StringUtils.lowerCase(sysRole.getCode()));
		if(!CmTag.isSuperAccount(loginUser.getUsername())
				&& StringUtils.equals(code, RoleConstants.ROLE_ADMIN)) {
			throw new OpException("不允许添加admin角色");
		}
		if (code!=null && sysRoleService.idDuplicate(sysRole.getId(), code)) {
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
			if(code==null){
				throw new OpException("角色不能为空");
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
	public String sysRole_au(Integer id,Byte type, ModelMap modelMap) throws IOException {

		modelMap.put("type", type);
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

	@RequiresPermissions("sysRole:edit")
	@RequestMapping(value="/sysRole_copy", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysRole_copy(@CurrentUser SysUserView loginUser,
							 SysRole sysRole,
							 HttpServletRequest request) {

		String code = StringUtils.trimToNull(StringUtils.lowerCase(sysRole.getCode()));
		if(!CmTag.isSuperAccount(loginUser.getUsername())
				&& StringUtils.equals(code, RoleConstants.ROLE_ADMIN)) {
			throw new OpException("不允许复制admin角色");
		}
		if (code!=null && sysRoleService.idDuplicate(sysRole.getId(), code)) {
			return failed("添加重复");
		}
		SysRole old_sysRole = sysRoleMapper.selectByPrimaryKey(sysRole.getId());
		SysRole record=new SysRole();
		record.setCode(sysRole.getCode());
		record.setName(sysRole.getName());
		record.setType(sysRole.getType());
		record.setResourceIds(old_sysRole.getResourceIds());
		record.setmResourceIds(old_sysRole.getmResourceIds());

		sysRoleService.insertSelective(record);
		logger.info(addLog(LogConstants.LOG_ADMIN, "复制角色：%s-%s", JSONUtils.toString(old_sysRole, MixinUtils.baseMixins(), false),JSONUtils.toString(record, MixinUtils.baseMixins(), false)));

		return success(FormUtils.SUCCESS);
	}

	@RequiresPermissions("sysRole:edit")
	@RequestMapping("/sysRole_copy")
	public String sysRole_copy(Integer id,Byte type, ModelMap modelMap) throws IOException {

		modelMap.put("id", id);
		modelMap.put("type", type);

		return "sys/sysRole/sysRole_copy";
	}
	@RequiresPermissions("sysRole:list")
	@RequestMapping("/sysRole_permissions")
	public String sysRole_permissions(Integer id, boolean isMobile,ModelMap modelMap) {

		if(id==null){
			return "sys/sysRole/sysRole_permissions";
		}

		// Map<资源Id,Map<父节点Id,父节点是否被选中>>
		Map<Integer,Map<Integer,Boolean>> permissions= new HashMap<>();
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
		String resourceIdsStr = isMobile?sysRole.getmResourceIds():sysRole.getResourceIds();
		Map<Integer, SysResource> sysResourceMap = sysResourceService.getSortedSysResources(isMobile);
		if(resourceIdsStr!=null){
			String[] resourceIds = resourceIdsStr.split(",");
			for(String resourceId:resourceIds){
				if(StringUtils.isNotBlank(resourceId)){
					SysResource sysResource = sysResourceMap.get(Integer.parseInt(resourceId));
					if(sysResource!=null && StringUtils.isNotBlank(sysResource.getPermission())){

						String parentIds = sysResource.getParentIds();
						String[] strings = parentIds.split("/");
						Map<Integer,Boolean> _parentIds=new LinkedHashMap<>();
					/*	for (String _parentId : strings) {*/
						for (int i=2;i<strings.length;i++) {  //不包括顶级节点
							Integer parentId = Integer.valueOf(strings[i]);
							Boolean isSelectd=false;

							for(String _resourceId:resourceIds){
								if(Integer.parseInt(_resourceId)==parentId){
									isSelectd=true;
									break;
								}
							}
							_parentIds.put(parentId,isSelectd);

						}
						permissions.put(Integer.parseInt(resourceId),_parentIds);
					}
				}
			}
		}
		modelMap.put("permissions",permissions);
		modelMap.put("sysResourceMap",sysResourceMap);

		return "sys/sysRole/sysRole_permissions";
	}

	@RequiresPermissions("sysRole:del")
	@RequestMapping(value="/sysRole_del", method=RequestMethod.POST)
	@ResponseBody
	public Map do_sysRole_del(@RequestParam(value = "ids[]") Integer[] ids,
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
	public Map do_sysRole_updateIsSysHold(Integer id, HttpServletRequest request) {

		if(id!=null){
			SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
			if(sysRole.getIsSysHold()==null)
				sysRole.setIsSysHold(false);
			String code = sysRole.getCode();
			Boolean isSysHold = sysRole.getIsSysHold();

			SysRole record = new SysRole();
			record.setId(id);
			record.setIsSysHold(!isSysHold);
			sysRoleService.updateByPrimaryKeySelective(record);
			logger.info(addLog(LogConstants.LOG_ADMIN, "更改角色是否系统自动控制：%s, %s", code, !isSysHold));
		}

		return success(FormUtils.SUCCESS);
	}
}
