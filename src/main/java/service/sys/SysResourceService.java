package service.sys;

import domain.sys.SysResource;
import domain.sys.SysResourceExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.sys.SysResourceMapper;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class SysResourceService extends BaseMapper{

	@Autowired
	private SysResourceMapper sysResourceMapper;

	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="Menus", allEntries=true),
			@CacheEvict(value="Permissions", allEntries=true)
	})
	public void insert(SysResource sysResource){

		sysResourceMapper.insert(sysResource);
		commonMapper.excuteSql("update sys_resource set is_leaf=0 where id=" + sysResource.getParentId());
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="Menus", allEntries=true),
			@CacheEvict(value="Permissions", allEntries=true)
	})
	public int updateByPrimaryKeySelective(SysResource record){

		if(StringUtils.isBlank(record.getCountCacheKeys())){
			commonMapper.excuteSql("update sys_resource set count_cache_keys=null where id="+ record.getId());
		}

		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(record.getId());
		if(record.getParentId() !=null &&
				sysResource.getParentId().intValue() != record.getParentId().intValue()){

			SysResourceExample example = new SysResourceExample();
			example.createCriteria().andParentIdEqualTo(sysResource.getParentId()).andAvailableEqualTo(SystemConstants.AVAILABLE);
			if(sysResourceMapper.countByExample(example)==0){
				commonMapper.excuteSql("update sys_resource set is_leaf=1 where id=" + sysResource.getParentId());
			}

			commonMapper.excuteSql("update sys_resource set is_leaf=0 where id=" + record.getParentId());
		}

		return  sysResourceMapper.updateByPrimaryKeySelective(record);
	}
	
	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="Menus", allEntries=true),
			@CacheEvict(value="Permissions", allEntries=true)
	})
	public void del(Integer id){

		// 顶级节点不可删除
		if(id==1) return ;

		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(id);
		{
			SysResourceExample example = new SysResourceExample();
			example.createCriteria().andParentIdEqualTo(sysResource.getParentId()).andAvailableEqualTo(SystemConstants.AVAILABLE);
			if (sysResourceMapper.countByExample(example) == 0) {
				commonMapper.excuteSql("update sys_resource set is_leaf=1 where id=" + sysResource.getParentId());
			}
		}

		sysResourceMapper.deleteByPrimaryKey(id);
		
		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andParentIdsLike(sysResource.getParentIds() + sysResource.getId() + "/" + "%");
		sysResourceMapper.deleteByExample(example);
	}

	// 根据拥有的权限，形成菜单栏目
	public List<SysResource> makeMenus(Set<String> ownPermissions){

		List<SysResource> menus = new ArrayList<>();
		Map<Integer, SysResource> sortedPermissions = getSortedSysResources();
		Collection<SysResource> permissions = sortedPermissions.values();
		for (SysResource res : permissions) {
			String type = res.getType();
			String permission = res.getPermission();
			if((StringUtils.equalsIgnoreCase(type, SystemConstants.RESOURCE_TYPE_MENU)
					|| StringUtils.equalsIgnoreCase(type, SystemConstants.RESOURCE_TYPE_URL))
					&& ownPermissions.contains(permission)) {

				if(res.getParentId()==null) {
					menus.add(res);
				}else{
					SysResource parent = sortedPermissions.get(res.getParentId());
					// id=1是顶级节点，此值必须固定为1
					if(parent.getId()==1 || ownPermissions.contains(parent.getPermission())) {
						menus.add(res);
					}
				}
			}
		}

		return menus;
	}

	public SysResource getByUrl(String url){

		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andUrlEqualTo(url);
		List<SysResource> sysResources = sysResourceMapper.selectByExample(example);

		SysResource sysResource = null;
		if(sysResources!=null && sysResources.size()>0){
			sysResource = sysResources.get(0);
		}

		return sysResource;
	}

	// 按层次递归读取资源
	private void menuLoop(List<SysResource> menuList, Integer parentId){

		SysResourceExample example = new SysResourceExample();
		SysResourceExample.Criteria criteria = example.createCriteria().andAvailableEqualTo(SystemConstants.AVAILABLE);
		if(parentId==null){
			criteria.andParentIdIsNull();
		}else{
			criteria.andParentIdEqualTo(parentId);
		}
		example.setOrderByClause("sort_order desc");
		List<SysResource> list = sysResourceMapper.selectByExample(example);

		for(SysResource sysResource:list) {

			menuList.add(sysResource);
			menuLoop(menuList, sysResource.getId());
		}
	}

	@Cacheable(value = "Permissions")
	public Map<Integer, SysResource> getSortedSysResources(){

		List<SysResource> menuList = new ArrayList<SysResource>();
		menuLoop(menuList, null);

		Map<Integer, SysResource> map = new LinkedHashMap<>();
		for (SysResource sysResource : menuList) {
			map.put(sysResource.getId(), sysResource);
		}

		return map;
	}
    
    /*private boolean hasPermission(Set<String> permissions, SysResource sysResource) {
        if(StringUtils.isEmpty(sysResource.getPermission())) {
            return true;
        }
        for(String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(sysResource.getPermission());
            if(p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }*/

	// 角色权限修改中读取资源树
	public TreeNode getTree(Set<Integer> selectIdSet){

		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andParentIdIsNull();
		example.setOrderByClause("sort_order desc");
		List<SysResource> sysResources = sysResourceMapper.selectByExample(example);
		SysResource rootRes = sysResources.get(0);

		TreeNode root = new TreeNode();
		root.title = rootRes.getName();
		root.expand = true;
		root.isFolder = true;
		root.hideCheckbox = true;
		root.children =  new ArrayList<TreeNode>();

		loopChildNode(rootRes.getId(), root, selectIdSet);

		return root;
	}

	public void loopChildNode(int parentId, TreeNode root, Set<Integer> selectIdSet){

		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		example.setOrderByClause("sort_order desc");
		List<SysResource> sysResources = sysResourceMapper.selectByExample(example);

		root.isFolder = (sysResources.size()>0);

		// System.out.println("==="+root.title);
		for (SysResource sysResource : sysResources) {

			TreeNode node = new TreeNode();
			node.key = sysResource.getId()+"";
			node.title = sysResource.getName();
			node.tooltip = sysResource.getPermission();
			node.expand = false;
			node.hideCheckbox = false;
			if(selectIdSet.contains(sysResource.getId().intValue())){
				node.select = true;
			}

			//if(!StringUtils.equals(sysResource.getType(), SystemConstants.RESOURCE_TYPE_FUNCTION)){
				node.children =  new ArrayList<TreeNode>();
				loopChildNode(sysResource.getId(), node, selectIdSet);
			//}

			root.children.add(node);
		}
	}
}
