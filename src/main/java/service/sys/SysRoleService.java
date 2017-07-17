package service.sys;


import domain.sys.SysResource;
import domain.sys.SysRole;
import domain.sys.SysRoleExample;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.sys.SysRoleMapper;
import sys.tool.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysResourceService sysResourceService;

	public boolean idDuplicate(Integer id, String role){

		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria criteria = example.createCriteria().andRoleEqualTo(role);
		if(id!=null) criteria.andIdNotEqualTo(id);

		return sysRoleMapper.countByExample(example) > 0;
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="Menus", allEntries=true),
			@CacheEvict(value="SysRole", key = "#role"),
			@CacheEvict(value="Permissions", allEntries=true),
			@CacheEvict(value = "UserRoles", allEntries=true)
	})
	public void del(Integer id, String role){

		sysRoleMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="Menus", allEntries=true),
			@CacheEvict(value="SysRoles", allEntries=true)
	})
	public int insert(SysRole sysRole){
		
		return sysRoleMapper.insert(sysRole);
	}
	
	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="Menus", allEntries=true),
			@CacheEvict(value="SysRole", key = "#role"),
			@CacheEvict(value="SysRole", key = "#oldRole"),
			@CacheEvict(value="SysRoles", allEntries=true),
			@CacheEvict(value = "UserRoles", allEntries=true)
	})
	public int updateByPrimaryKeySelective(SysRole sysRole, String role, String oldRole){

		return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
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

		List<SysRole> sysRoles = sysRoleMapper.selectByExample(new SysRoleExample());
		Map<Integer, SysRole> sysRoleMap = new LinkedHashMap<Integer, SysRole>();
		for (SysRole sysRole : sysRoles) {
			sysRoleMap.put(sysRole.getId(), sysRole);
		}
		return sysRoleMap;
	}

	// 获取某个角色下拥有的所有权限
	public Set<String> getRolePermissions(int roleId){

		Set<String> permissions = new HashSet<String>();
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
		String resourceIdsStr = sysRole.getResourceIds();
		if(resourceIdsStr!=null){
			Map<Integer, SysResource> sysResources = sysResourceService.getSortedSysResources();
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
	
	public TreeNode getTree(Set<Integer> selectIdSet, boolean checkIsSysHold){
		
		if(null == selectIdSet) selectIdSet = new HashSet<Integer>();
		
		TreeNode root = new TreeNode();
		root.title = "选择角色";
		root.expand = true;
		root.isFolder = true;
		root.hideCheckbox = true;
		root.children =  new ArrayList<TreeNode>();
		
		SysRoleExample example2 = new SysRoleExample();
		List<SysRole> sysRoles = sysRoleMapper.selectByExample(example2);
		
		for(SysRole sysRole:sysRoles){
			
			TreeNode node2 = new TreeNode();
			node2.title = sysRole.getDescription();
			node2.key = sysRole.getId()+"";
			node2.expand = false;
			node2.isFolder = false;
			node2.hideCheckbox = false;
			if(checkIsSysHold && BooleanUtils.isTrue(sysRole.getIsSysHold())) {
				node2.unselectable = true;
				node2.addClass = "unselectable";
			}
			node2.children = new ArrayList<TreeNode>();
			
			if(selectIdSet.contains(sysRole.getId().intValue())){
				node2.select = true;
			}
			
			root.children.add(node2);
		}
		
		return  root;
	}
}
