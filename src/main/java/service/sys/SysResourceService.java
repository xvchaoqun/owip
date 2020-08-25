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

	public boolean idDuplicate(Integer id, String permission, String url){

		if(StringUtils.isNotBlank(permission)){
			SysResourceExample example = new SysResourceExample();
			SysResourceExample.Criteria criteria = example.createCriteria().andNameEqualTo(permission);
			if (id != null) {
				criteria.andIdNotEqualTo(id);
			}
			if (sysResourceMapper.countByExample(example) > 0) {
				return true;
			}
		}

		if(StringUtils.isNotBlank(url)){
			SysResourceExample example = new SysResourceExample();
			SysResourceExample.Criteria criteria = example.createCriteria().andUrlEqualTo(url.trim());
			if (id != null) {
				criteria.andIdNotEqualTo(id);
			}
			if (sysResourceMapper.countByExample(example) > 0) {
				return true;
			}
		}

		return false;
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="SysResources", allEntries=true)
	})
	public void insert(SysResource record){

		record.setUrl(StringUtils.trimToNull(record.getUrl()));
		record.setPermission(StringUtils.trimToNull(record.getPermission()));
		sysResourceMapper.insert(record);
		commonMapper.excuteSql("update sys_resource set is_leaf=0 where id=" + record.getParentId());
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="SysResources", allEntries=true)
	})
	public void updateByPrimaryKeySelective(SysResource record){

		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(record.getId());
		// 改变父节点
		if(record.getParentId() !=null && sysResource.getParentId().intValue() != record.getParentId().intValue()){

			// 判断原父节点是否成为了叶子节点
			SysResourceExample example = new SysResourceExample();
			example.createCriteria().andParentIdEqualTo(sysResource.getParentId()).andAvailableEqualTo(SystemConstants.AVAILABLE);
			if(sysResourceMapper.countByExample(example)==0){
				commonMapper.excuteSql("update sys_resource set is_leaf=1 where id=" + sysResource.getParentId());
			}
			// 把新父节点修改为非叶子节点
			commonMapper.excuteSql("update sys_resource set is_leaf=0 where id=" + record.getParentId());
		}

		// 确认一下是否是叶子节点
		List<SysResource> subSysResourses = getSubSysResourses(record.getId());
		record.setIsLeaf(subSysResourses.size()==0);

		sysResourceMapper.updateByPrimaryKeySelective(record);

		commonMapper.excuteSql(String.format("update sys_resource set id=%s %s %s %s where id=%s",
				record.getId(),
				StringUtils.isBlank(record.getCountCacheKeys()) ? ", count_cache_keys=null" : "",
				StringUtils.isBlank(record.getUrl()) ? ", url=null" : "",
				StringUtils.isBlank(record.getPermission()) ? ", permission=null" : "",
				record.getId()));

		updateAllChildParentIds(record.getId());
	}

	// 递归更新所有子节点的所属父节点字符串
	private void updateAllChildParentIds(int id){

		SysResource parent = sysResourceMapper.selectByPrimaryKey(id);

		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andParentIdEqualTo(id);
		List<SysResource> childs = sysResourceMapper.selectByExample(example);
		if(childs==null || childs.size()==0) return;

		for (SysResource child : childs) {
			SysResource record = new SysResource();
			record.setId(child.getId());
			record.setParentIds(parent.getParentIds() + id + "/");
			sysResourceMapper.updateByPrimaryKeySelective(record);

			updateAllChildParentIds(child.getId());
		}
	}
	
	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserPermissions", allEntries=true),
			@CacheEvict(value="SysResources", allEntries=true)
	})
	public void del(Integer id){

		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(id);
		// 顶级节点不可删除
		if(sysResource.getParentId()==null) return ;

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

	@Cacheable(value = "SysResources", key = "'url:'+ #url")
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

	// 按层次递归读取资源(非顶级节点只读取permission不为空的)
	private void menuLoop(List<SysResource> menuList, Integer parentId, boolean isMobile){

		SysResourceExample example = new SysResourceExample();
		SysResourceExample.Criteria criteria = example.createCriteria().andIsMobileEqualTo(isMobile)
				.andAvailableEqualTo(SystemConstants.AVAILABLE);
		if(parentId==null){
			criteria.andParentIdIsNull();
		}else{
			criteria.andParentIdEqualTo(parentId);
		}
		example.setOrderByClause("sort_order desc");
		List<SysResource> list = sysResourceMapper.selectByExample(example);

		for(SysResource sysResource:list) {

			if(sysResource.getParentId()!=null
					&& StringUtils.isBlank(sysResource.getPermission())) continue;

			menuList.add(sysResource);
			menuLoop(menuList, sysResource.getId(), isMobile);
		}
	}

	@Cacheable(value = "SysResources", key = "#isMobile")
	public Map<Integer, SysResource> getSortedSysResources(boolean isMobile){

		List<SysResource> menuList = new ArrayList<SysResource>();
		menuLoop(menuList, null, isMobile);

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

	// 读取完整的资源树[用于角色权限修改]
	public TreeNode getTree(Set<Integer> selectIdSet, boolean isMobile){

		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andParentIdIsNull().andIsMobileEqualTo(isMobile);
		example.setOrderByClause("sort_order desc");
		List<SysResource> sysResources = sysResourceMapper.selectByExample(example);
		SysResource rootRes = sysResources.get(0);

		TreeNode root = new TreeNode();
		root.title = rootRes.getName();
		root.expand = true;
		root.isFolder = true;
		root.hideCheckbox = true;
		root.children =  new ArrayList<>();

		loopChildNode(rootRes.getId(), root, selectIdSet);

		return root;
	}

	// 节点下的子资源列表
	public List<SysResource> getSubSysResourses(int parentId){

		SysResourceExample example = new SysResourceExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		example.setOrderByClause("sort_order desc");
		return sysResourceMapper.selectByExample(example);
	}

	// 节点下的资源目录树 root
	public void loopChildNode(int parentId, TreeNode root, Set<Integer> selectIdSet){

		List<SysResource> sysResources = getSubSysResourses(parentId);

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
