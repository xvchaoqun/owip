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
import service.global.CacheService;
import sys.tool.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SysRoleService extends BaseMapper {

	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private CacheService cacheService;

	public boolean idDuplicate(Integer id, String role){

		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria criteria = example.createCriteria().andRoleEqualTo(role);
		if(id!=null) criteria.andIdNotEqualTo(id);

		return sysRoleMapper.countByExample(example) > 0;
	}

	@Transactional
	public void del(Integer id){

		sysRoleMapper.deleteByPrimaryKey(id);

		cacheService.clearRoleCache();
	}

	@Transactional
	public void insertSelective(SysRole record){

		record.setSortOrder(getNextSortOrder("sys_role", "1=1"));
		sysRoleMapper.insertSelective(record);

		cacheService.clearRoleCache();
	}
	
	@Transactional
	public void updateByPrimaryKeySelective(SysRole sysRole){

		sysRoleMapper.updateByPrimaryKeySelective(sysRole);

		cacheService.clearRoleCache();
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
				commonMapper.downOrder("sys_role", "1=1", baseSortOrder, targetEntity.getSortOrder());
			else
				commonMapper.upOrder("sys_role", "1=1", baseSortOrder, targetEntity.getSortOrder());

			SysRole record = new SysRole();
			record.setId(id);
			record.setSortOrder(targetEntity.getSortOrder());
			sysRoleMapper.updateByPrimaryKeySelective(record);
		}

		cacheService.clearRoleCache();
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
