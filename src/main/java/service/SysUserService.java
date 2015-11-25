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
import org.springframework.util.Assert;
import persistence.SysUserMapper;
import sys.constants.SystemConstants;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysResourceService sysResourceService;

	public boolean idDuplicate(Integer id, String username, String code){

		SysUserExample example = new SysUserExample();
		SysUserExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username);
		if(id!=null) criteria.andIdNotEqualTo(id);
		if(StringUtils.isNotBlank(code)) criteria.andCodeEqualTo(code);

		return sysUserMapper.countByExample(example) > 0;
	}

	@Transactional
	public void insertSelective(SysUser record){

		sysUserMapper.insertSelective(record);
		// 默认角色：访客
		addRole(record.getId(), SystemConstants.ROLE_GUEST, record.getUsername());
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

	public Set<Integer> getUserRoleIdSet(String roleIdsStr){

		Set<Integer> roleIds = new HashSet<>();
		if(StringUtils.isBlank(roleIdsStr)) return roleIds;
		String[] roleIdStrs = roleIdsStr.split(SystemConstants.USER_ROLEIDS_SEPARTOR);
		for(String roleIdStr : roleIdStrs) {
			if(StringUtils.isEmpty(roleIdStr)) {
				continue;
			}
			roleIds.add(Integer.valueOf(roleIdStr));
		}

		return roleIds;
	}
	// 删除一个角色
	@Transactional
	@Caching(evict={
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId")
	})
	public void delRole(int userId, String role, String username){

		SysUser sysUser = findById(userId);
		Assert.isTrue(StringUtils.equalsIgnoreCase(sysUser.getUsername(), username));

		SysRole sysRole = sysRoleService.getByRole(role);
		Set<Integer> roleIdSet = getUserRoleIdSet(sysUser.getRoleIds());
		roleIdSet.remove(sysRole.getId());

		SysUser record = new SysUser();
		record.setId(userId);
		record.setRoleIds(SystemConstants.USER_ROLEIDS_SEPARTOR +
				StringUtils.join(roleIdSet, SystemConstants.USER_ROLEIDS_SEPARTOR)
				+ SystemConstants.USER_ROLEIDS_SEPARTOR);
		updateByPrimaryKeySelective(record, sysUser.getUsername());
	}
	// 添加一个角色
	@Transactional
	@Caching(evict={
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId")
	})
	public void addRole(int userId, String role, String username){

		SysUser sysUser = findById(userId);
		Assert.isTrue(StringUtils.equalsIgnoreCase(sysUser.getUsername(), username));

		SysRole sysRole = sysRoleService.getByRole(role);
		Set<Integer> roleIdSet = getUserRoleIdSet(sysUser.getRoleIds());
		roleIdSet.add(sysRole.getId());

		SysUser record = new SysUser();
		record.setId(userId);
		record.setRoleIds(SystemConstants.USER_ROLEIDS_SEPARTOR +
				StringUtils.join(roleIdSet, SystemConstants.USER_ROLEIDS_SEPARTOR)
				+ SystemConstants.USER_ROLEIDS_SEPARTOR);
		updateByPrimaryKeySelective(record, sysUser.getUsername());

	}

	// 改变角色
	@Transactional
	@Caching(evict={
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId")
	})
	public void changeRole(int userId, String role, String toRole, String username){

		SysUser sysUser = findById(userId);
		Assert.isTrue(StringUtils.equalsIgnoreCase(sysUser.getUsername(), username));

		SysRole sysRole = sysRoleService.getByRole(role);
		SysRole toSysRole = sysRoleService.getByRole(toRole);
		Set<Integer> roleIdSet = getUserRoleIdSet(sysUser.getRoleIds());
		roleIdSet.remove(sysRole.getId());
		roleIdSet.add(toSysRole.getId());

		SysUser record = new SysUser();
		record.setId(userId);
		record.setRoleIds(SystemConstants.USER_ROLEIDS_SEPARTOR +
				StringUtils.join(roleIdSet, SystemConstants.USER_ROLEIDS_SEPARTOR)
				+ SystemConstants.USER_ROLEIDS_SEPARTOR);
		updateByPrimaryKeySelective(record, sysUser.getUsername());

	}

	// 根据账号查找所有的角色（对象）
	private Set<SysRole> _findRoles(String username){

		SysUser user = findByUsername(username);
		Set<SysRole> roles = new HashSet<>();
		Set<Integer> roleIds = getUserRoleIdSet(user.getRoleIds());
		Map<Integer, SysRole> roleMap = sysRoleService.findAll();
		for (Integer roleId : roleIds) {
			SysRole role = roleMap.get(roleId);
			if(role != null)
				roles.add(role);
		}
        return roles;
	}

	// 根据账号查找所拥有的全部资源
	private List<SysResource> findResources(String username){

		List<SysResource> resources = new ArrayList<>();
		Set<SysRole> roles = _findRoles(username);
		List<Integer> resourceIds = new ArrayList<Integer>();
		
		for (SysRole role : roles) {
			
			String resourceIdsStr = role.getResourceIds();
			
			if(StringUtils.isBlank(resourceIdsStr)) continue;
			
			String[] resourceIdStrs = resourceIdsStr.split(SystemConstants.USER_ROLEIDS_SEPARTOR);
			for(String resourceIdStr : resourceIdStrs) {
				if(StringUtils.isEmpty(resourceIdStr)) {
					continue;
				}
				resourceIds.add(Integer.valueOf(resourceIdStr));
			}
		}
		
		if(resourceIds.size()==0) return resources;

		Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources();
		for (Integer resourceId : resourceIds) {
			SysResource resource = resourceMap.get(resourceId);
			if(resource != null)
				resources.add(resource);
		}
		return resources;
	}

	// 根据账号查找所有的角色（角色字符串集合）
	@Cacheable(value="UserRoles", key="#username")
	public Set<String> findRoles(String username) {

		Set<SysRole> _roles = _findRoles(username);
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
