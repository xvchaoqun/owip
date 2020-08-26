package service.sys;


import domain.sys.SysResource;
import domain.sys.SysRole;
import domain.sys.SysRoleExample;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
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

import static sys.constants.SystemConstants.SYS_ROLE_TYPE_ADD;

@Service
public class SysRoleService extends BaseMapper {

	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private CacheHelper cacheHelper;

	public boolean idDuplicate(Integer id, String code){

		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
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
	public void updateRole(Integer[] roleIds, int resourceId, boolean addOrDel) {

		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(resourceId);
		boolean isMobile = sysResource.getIsMobile();
        for (int roleId : roleIds) {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);

            SysRole record = new SysRole();
            record.setId(roleId);
            Set<Integer> resIdSet = new HashSet<>();
            if (isMobile) {
                String mResIds = sysRole.getmResourceIds();
                String[] _mResIds = mResIds.split(",");
                for (String mResId : _mResIds) {
                    resIdSet.add(Integer.valueOf(mResId));
                }
                if (addOrDel) {
                    resIdSet.add(resourceId);
                } else {
                    resIdSet.remove(resourceId);
                }
                record.setmResourceIds(org.apache.commons.lang3.StringUtils.join(resIdSet, ","));
            } else {
                String resIds = sysRole.getResourceIds();
                String[] _resIds = resIds.split(",");
                for (String resId : _resIds) {
                    resIdSet.add(Integer.valueOf(resId));
                }
                if (addOrDel) {
                    resIdSet.add(resourceId);
                } else {
                    resIdSet.remove(resourceId);
                }
                record.setResourceIds(org.apache.commons.lang3.StringUtils.join(resIdSet, ","));
            }

            sysRoleMapper.updateByPrimaryKeySelective(record);
        }
		cacheHelper.clearRoleCache();
	}

	@Cacheable(value = "SysRoles", key = "#code")
	public SysRole getByRole(String code){

		SysRoleExample example = new SysRoleExample();
		example.createCriteria().andCodeEqualTo(code);
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

		changeOrder("sys_role", null, ORDER_BY_DESC, id, addNum);
		cacheHelper.clearRoleCache();
	}

	// 获取某个角色下拥有的所有权限
	public Set<String> getRolePermissions(int roleId, boolean isMobile){

		Set<String> permissions = new HashSet<String>();
		SysRole sysRole = CmTag.getRole(roleId);
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
		List<TreeNode> rootChildren = new ArrayList<TreeNode>();
		root.children = rootChildren;

		List<TreeNode> roleAdd = new ArrayList<TreeNode>();
		List<TreeNode> roleMinus = new ArrayList<TreeNode>();

		TreeNode node = new TreeNode();
		node.title = "角色（加权限）";
		node.isFolder = true;
		node.expand = true;
		node.children = roleAdd;
		rootChildren.add(node);

		node = new TreeNode();
		node.title = "角色（减权限）";
		node.isFolder = true;
		node.expand = true;
		node.children = roleMinus;
		rootChildren.add(node);


		SysRoleExample example = new SysRoleExample();
		example.setOrderByClause("sort_order desc");
		List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
		boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());

		for(SysRole sysRole:sysRoles){

			// 只有超级管理员允许修改为超级管理员（如果不是则不显示超级管理员的选项）
			if(!superAccount &&  StringUtils.equals(sysRole.getCode(), RoleConstants.ROLE_SUPER)) {
				continue;
			}

			node = new TreeNode();
			node.title = sysRole.getName();
			node.key = sysRole.getId() + "";
			node.expand = false;
			node.isFolder = false;
			node.hideCheckbox = false;

			node.children = new ArrayList<TreeNode>();
			if (selectIdSet.contains(sysRole.getId().intValue())) {
				node.select = true;
			}

			if(BooleanUtils.isTrue(sysRole.getIsSysHold())) {
				if (checkIsSysHold) {
					node.addClass = "unselectable";
					if (superAccount){
						// 系统自动维护角色，仅允许超级管理员修改
						if(sysRole.getType()==SYS_ROLE_TYPE_ADD){  //加权限
							roleAdd.add(node);
						}else{
							roleMinus.add(node);
						}
					}
				}
			}else{
				// 手动维护角色
				if(sysRole.getType()==SYS_ROLE_TYPE_ADD){  //加权限
					roleAdd.add(node);
				}else{
					roleMinus.add(node);
				}
			}
		}
		
		return  root;
	}
}
