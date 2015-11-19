package service;

import domain.SysResource;
import domain.SysRole;
import domain.SysUser;
import domain.SysUserExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import persistence.SysUserMapper;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysResourceService resourceService;

	public boolean idDuplicate(Integer id, String username, String code){

		SysUserExample example = new SysUserExample();
		if(id!=null) example.or().andIdNotEqualTo(id);
		if(StringUtils.isNotBlank(code))
			example.or().andCodeEqualTo(code);
		example.or().andUsernameEqualTo(username);

		return sysUserMapper.countByExample(example) > 0;
	}

	@Cacheable(value="SysUser:ID", key="#id")
	public SysUser findById(int id) {

		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Cacheable(value="SysUser", key="#username")
	public SysUser findByUsername(String username) {

		SysUserExample example = new SysUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<SysUser> users = sysUserMapper.selectByExampleWithRowbounds(example , new RowBounds(0, 1));
		
		return (users.size()>0)?users.get(0):null;
	}
	@Transactional
	@Caching(evict={
			@CacheEvict(value="SysUser", key="#oldUsername"),
			@CacheEvict(value="SysUser:ID", key="#user.id")
	})
	public int updateByPrimaryKeySelective(SysUser user, String oldUsername){

		return sysUserMapper.updateByPrimaryKeySelective(user);
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#id")
	})
	public int lockUser(int id, String username, boolean locked){

		SysUserExample example = new SysUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		SysUser record = new SysUser();
		record.setLocked(locked);

		return sysUserMapper.updateByExampleSelective(record, example);
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="SysUser", key="#oldUsername"),
			@CacheEvict(value="SysUser:ID", key="#id")
	})
	public int deleteByPrimaryKey(Integer id, String oldUsername){

		return sysUserMapper.deleteByPrimaryKey(id);
	}

	public List<Integer> getUserRoleIds(String roleIdsStr){

		List<Integer> roleIds = new ArrayList<Integer>();
		if(StringUtils.isBlank(roleIdsStr)) return roleIds;
		String[] roleIdStrs = roleIdsStr.split(",");
		for(String roleIdStr : roleIdStrs) {
			if(StringUtils.isEmpty(roleIdStr)) {
				continue;
			}
			roleIds.add(Integer.valueOf(roleIdStr));
		}

		return roleIds;
	}
	// 添加一个角色
	public String buildRoleIdsStr(String roleIdsStr, int roleId){

		Set<Integer> roleIdSet = new HashSet<>();
		roleIdSet.addAll(getUserRoleIds(roleIdsStr));
		if(roleIdSet.contains(roleId))
			return roleIdsStr;
		roleIdSet.add(roleId);

		return "," + StringUtils.join(roleIdSet, ",") + ",";
	}

	private List<SysRole> _findRoles(String username){

		SysUser user = findByUsername(username);

		List<SysRole> roles = new ArrayList<>();
		List<Integer> roleIds = getUserRoleIds(user.getRoleIds());

		Map<Integer, SysRole> roleMap = roleService.findAll();
		for (Integer roleId : roleIds) {
			SysRole role = roleMap.get(roleId);
			if(role != null)
				roles.add(role);
		}

        return roles;
	}
	
	private List<SysResource> findResources(String username){

		List<SysResource> resources = new ArrayList<>();
		List<SysRole> roles = _findRoles(username);
		List<Integer> resourceIds = new ArrayList<Integer>();
		
		for (SysRole role : roles) {
			
			String resourceIdsStr = role.getResourceIds();
			
			if(StringUtils.isBlank(resourceIdsStr)) continue;
			
			String[] resourceIdStrs = resourceIdsStr.split(",");
			for(String resourceIdStr : resourceIdStrs) {
				if(StringUtils.isEmpty(resourceIdStr)) {
					continue;
				}
				resourceIds.add(Integer.valueOf(resourceIdStr));
			}
		}
		
		if(resourceIds.size()==0) return resources;

		Map<Integer, SysResource> resourceMap = resourceService.getSortedSysResources();
		for (Integer resourceId : resourceIds) {
			SysResource resource = resourceMap.get(resourceId);
			if(resource != null)
				resources.add(resource);
		}
		return resources;
	}

	@Cacheable(value="UserRoles", key="#username")
	public Set<String> findRoles(String username) {

		List<SysRole> _roles = _findRoles(username);

		Set<String> roles = new HashSet<String>();
        for(SysRole role : _roles) {
           
        	roles.add(role.getRole());
        }
        
		return roles;
	}
	
	@Cacheable(value="UserPermissions", key="#username")
	public Set<String> findPermissions(String username) {
		
		List<SysResource> resources = findResources(username);
		
		Set<String> permissions = new HashSet<String>();
        for(SysResource resource : resources) {
        	if(StringUtils.isNotBlank(resource.getPermission()))
            	permissions.add(resource.getPermission().trim());
        }
        
        return permissions;
	}
	
	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserRoles", key="#username"),
			@CacheEvict(value="Menus", key="#username"),
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId"),
			@CacheEvict(value="UserPermissions", key="#username")
	})
	public void updateUserRoles(int userId, String username, String roleIds){

		SysUser user = new SysUser();
		user.setId(userId);
		user.setRoleIds(roleIds);
		
		sysUserMapper.updateByPrimaryKeySelective(user );
	}

}
