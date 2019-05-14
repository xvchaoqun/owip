package service.sys;


import domain.sys.SysResource;
import domain.sys.SysRole;
import domain.sys.SysRoleExample;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.global.CacheHelper;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class SysRoleService extends BaseMapper {

	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private CacheHelper cacheHelper;

	public boolean idDuplicate(Integer id, String role){

		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria criteria = example.createCriteria().andRoleEqualTo(role);
		if(id!=null) criteria.andIdNotEqualTo(id);

		return sysRoleMapper.countByExample(example) > 0;
	}

	@Transactional
	public void batchDel(Integer[] ids){

		if(ids==null || ids.length==0) return ;

		SysRoleExample example = new SysRoleExample();
		example.createCriteria().andIdIn(Arrays.asList(ids));
		sysRoleMapper.deleteByExample(example);

		cacheHelper.clearRoleCache();
	}

	@Transactional
	public void insertSelective(SysRole record){

		record.setSortOrder(getNextSortOrder("sys_role", null));
		sysRoleMapper.insertSelective(record);

		cacheHelper.clearRoleCache();
	}
	
	@Transactional
	public void updateByPrimaryKeySelective(SysRole sysRole){

		sysRoleMapper.updateByPrimaryKeySelective(sysRole);

		cacheHelper.clearRoleCache();
	}

	// 给角色添加或删除某个资源
	public void updateRole(int roleId, int resourceId, boolean addOrDel) {

		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(resourceId);
		boolean isMobile = sysResource.getIsMobile();
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);

		SysRole record = new SysRole();
		record.setId(roleId);
		Set<Integer> resIdSet = new HashSet<>();
		if(isMobile){
			String mResIds = sysRole.getmResourceIds();
			String[] _mResIds = mResIds.split(",");
			for (String mResId : _mResIds) {
				resIdSet.add(Integer.valueOf(mResId));
			}
			if(addOrDel){
				resIdSet.add(resourceId);
			}else{
				resIdSet.remove(resourceId);
			}
			record.setmResourceIds(org.apache.commons.lang3.StringUtils.join(resIdSet, ","));
		}else {
			String resIds = sysRole.getResourceIds();
			String[] _resIds = resIds.split(",");
			for (String resId : _resIds) {
				resIdSet.add(Integer.valueOf(resId));
			}
			if(addOrDel){
				resIdSet.add(resourceId);
			}else{
				resIdSet.remove(resourceId);
			}
			record.setResourceIds(org.apache.commons.lang3.StringUtils.join(resIdSet, ","));
		}

		sysRoleMapper.updateByPrimaryKeySelective(record);

		cacheHelper.clearRoleCache();
	}

	@Cacheable(value = "SysRoles", key = "#role")
	public SysRole getByRole(String role){

		SysRoleExample example = new SysRoleExample();
		example.createCriteria().andRoleEqualTo(role);
		List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
		if(sysRoles.size()>0) return sysRoles.get(0);
		return null;
	}

	@Cacheable(value = "SysRoles")
	public Map<Integer, SysRole> findAll(){

		SysRoleExample example = new SysRoleExample();
		example.setOrderByClause("sort_order desc");
		List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
		Map<Integer, SysRole> sysRoleMap = new LinkedHashMap<Integer, SysRole>();
		for (SysRole sysRole : sysRoles) {
			sysRoleMap.put(sysRole.getId(), sysRole);
		}
		return sysRoleMap;
	}

	/**
	 * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
	 * @param id
	 * @param addNum
	 */
	@Transactional
	public void changeOrder(int id, int addNum) {

		if(addNum == 0) return ;
		byte orderBy = ORDER_BY_DESC;
		SysRole entity = sysRoleMapper.selectByPrimaryKey(id);
		Integer baseSortOrder = entity.getSortOrder();

		SysRoleExample example = new SysRoleExample();
		if (addNum*orderBy > 0) {

			example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
			example.setOrderByClause("sort_order asc");
		}else {

			example.createCriteria().andSortOrderLessThan(baseSortOrder);
			example.setOrderByClause("sort_order desc");
		}

		List<SysRole> overEntities = sysRoleMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
		if(overEntities.size()>0) {

			SysRole targetEntity = overEntities.get(overEntities.size()-1);

			if (addNum*orderBy > 0)
				commonMapper.downOrder("sys_role", null, baseSortOrder, targetEntity.getSortOrder());
			else
				commonMapper.upOrder("sys_role", null, baseSortOrder, targetEntity.getSortOrder());

			SysRole record = new SysRole();
			record.setId(id);
			record.setSortOrder(targetEntity.getSortOrder());
			sysRoleMapper.updateByPrimaryKeySelective(record);
		}

		cacheHelper.clearRoleCache();
	}

	// 获取某个角色下拥有的所有权限
	public Set<String> getRolePermissions(int roleId, boolean isMobile){

		Set<String> permissions = new HashSet<String>();
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
		String resourceIdsStr = isMobile?sysRole.getmResourceIds():sysRole.getResourceIds();

		if(resourceIdsStr!=null){
			Map<Integer, SysResource> sysResources = sysResourceService.getSortedSysResources(isMobile);
			String[] resourceIds = resourceIdsStr.split(",");
			for(String resourceId:resourceIds){
				if(StringUtils.isNotBlank(resourceId)){

					SysResource sysResource = sysResources.get(Integer.parseInt(resourceId));
					if(sysResource!=null && StringUtils.isNotBlank(sysResource.getPermission())){
						permissions.add(sysResource.getPermission().trim());
					}
				}
			}
		}

		return permissions;
	}

	// checkIsSysHold: 是否考虑系统自动赋予角色，如果考虑，则不允许手动更改。
	public TreeNode getTree(Set<Integer> selectIdSet, boolean checkIsSysHold){
		
		if(null == selectIdSet) selectIdSet = new HashSet<Integer>();
		
		TreeNode root = new TreeNode();
		root.title = "选择角色";
		root.expand = true;
		root.isFolder = true;
		root.hideCheckbox = true;
		root.children =  new ArrayList<TreeNode>();

		SysRoleExample example = new SysRoleExample();
		example.setOrderByClause("sort_order desc");
		List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
		boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());

		for(SysRole sysRole:sysRoles){

			// 只有超级管理员允许修改为超级管理员（如果不是则不显示超级管理员的选项）
			if(!superAccount &&  StringUtils.equals(sysRole.getRole(), RoleConstants.ROLE_SUPER)) {
				continue;
			}

			TreeNode node = new TreeNode();
			node.title = sysRole.getDescription();
			node.key = sysRole.getId() + "";
			node.expand = false;
			node.isFolder = false;
			node.hideCheckbox = false;
			if (checkIsSysHold && BooleanUtils.isTrue(sysRole.getIsSysHold())) {

				if (!superAccount) node.unselectable = true;
				node.addClass = "unselectable";
			}
			node.children = new ArrayList<TreeNode>();

			if (selectIdSet.contains(sysRole.getId().intValue())) {
				node.select = true;
			}

			root.children.add(node);
		}
		
		return  root;
	}
}
