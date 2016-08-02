package service.sys;


import domain.sys.SysRole;
import domain.sys.SysRoleExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.sys.SysRoleMapper;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

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
			@CacheEvict(value="Permissions", allEntries=true)
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
			@CacheEvict(value="SysRoles", allEntries=true)
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
		Map<Integer, SysRole> sysRoleMap = new LinkedHashMap();
		for (SysRole sysRole : sysRoles) {
			sysRoleMap.put(sysRole.getId(), sysRole);
		}
		return sysRoleMap;
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
			if(BooleanUtils.isTrue(sysRole.getIsSysHold())) {
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
