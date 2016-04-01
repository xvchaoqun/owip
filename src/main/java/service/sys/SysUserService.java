package service.sys;

import domain.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.SysUserMapper;
import service.BaseMapper;
import service.party.EnterApplyService;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class SysUserService extends BaseMapper {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private EnterApplyService enterApplyService;

	/*@Transactional
	@Caching(evict={
			@CacheEvict(value="UserRoles", key="#username"),
			@CacheEvict(value="Menus", key="#username"),
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId"),
			@CacheEvict(value="UserPermissions", key="#username")
	})
	public void addRolePartyAdmin(int userId, String username){

		Set<String> roleStrSet = findRoles(username);
		if(!roleStrSet.contains(SystemConstants.ROLE_PARTYADMIN)) {
			addRole(userId, SystemConstants.ROLE_PARTYADMIN, username);
		}
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserRoles", key="#username"),
			@CacheEvict(value="Menus", key="#username"),
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId"),
			@CacheEvict(value="UserPermissions", key="#username")
	})
	public void removeRolePartyAdmin(int userId, int partyId, String username){

		// 如果他只是该分党委的管理员，则删除分党委管理员的角色； 否则不处理
		List<PartyMember> userParyAdmin = commonMapper.findUserParyAdmin(userId);

		delRole(userId, SystemConstants.ROLE_PARTYADMIN, username);
	}*/

	/*public void addRoleBranchAdmin(int userId){

		SysUser sysUser = findById(userId);
		addRole(userId, SystemConstants.ROLE_BRANCHADMIN, sysUser.getUsername());
	}

	public void removeRoleBranchAdmin(int userId){

		SysUser sysUser = findById(userId);
		delRole(userId, SystemConstants.ROLE_BRANCHADMIN, sysUser.getUsername());
	}*/
	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserRoles", key="#username"),
			@CacheEvict(value="Menus", key="#username"),
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId"),
			@CacheEvict(value="UserPermissions", key="#username")
	})
	public void changeRoleGuestToMember(int userId, String username){

		// 更新系统角色  访客->党员
		changeRole(userId, SystemConstants.ROLE_GUEST,
				SystemConstants.ROLE_MEMBER, username);
	}
	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserRoles", key="#username"),
			@CacheEvict(value="Menus", key="#username"),
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId"),
			@CacheEvict(value="UserPermissions", key="#username")
	})
	public void changeRoleMemberToGuest(int userId, String username){

		// 更新系统角色  党员->访客
		changeRole(userId, SystemConstants.ROLE_MEMBER,
				SystemConstants.ROLE_GUEST, username);
		// 撤回原申请
		EnterApply _enterApply = enterApplyService.getCurrentApply(userId);
		if(_enterApply!=null) {
			enterApplyService.applyBack(userId, null, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT);
		}
	}


	public boolean idDuplicate(Integer id, String username, String code){

		SysUserExample example = new SysUserExample();
		SysUserExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username);
		if(id!=null) criteria.andIdNotEqualTo(id);
		if(StringUtils.isNotBlank(code)) criteria.andCodeEqualTo(code);

		return sysUserMapper.countByExample(example) > 0;
	}

	public String getDefaultRoleIds(){

		SysRole sysRole = sysRoleService.getByRole(SystemConstants.ROLE_GUEST);
		return SystemConstants.USER_ROLEIDS_SEPARTOR + sysRole.getId() + SystemConstants.USER_ROLEIDS_SEPARTOR;
	}

	@Transactional
	public void insertSelective(SysUser record){

		record.setRoleIds(getDefaultRoleIds());
		sysUserMapper.insertSelective(record);
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
			@CacheEvict(value="UserRoles", key="#oldUsername"),
			@CacheEvict(value="Menus", key="#oldUsername"),
			@CacheEvict(value="SysUser", key="#oldUsername"),
			@CacheEvict(value="SysUser:ID", key="#id"),
			@CacheEvict(value="UserPermissions", key="#oldUsername")
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
			@CacheEvict(value="UserRoles", key="#username"),
			@CacheEvict(value="Menus", key="#username"),
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId"),
			@CacheEvict(value="UserPermissions", key="#username")
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
	// 添加一个角色（重复则替换）
	@Transactional
	@Caching(evict={
			@CacheEvict(value="UserRoles", key="#username"),
			@CacheEvict(value="Menus", key="#username"),
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId"),
			@CacheEvict(value="UserPermissions", key="#username")
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
			@CacheEvict(value="UserRoles", key="#username"),
			@CacheEvict(value="Menus", key="#username"),
			@CacheEvict(value="SysUser", key="#username"),
			@CacheEvict(value="SysUser:ID", key="#userId"),
			@CacheEvict(value="UserPermissions", key="#username")
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

		Set<SysRole> roles = new HashSet<>();

		//SysUser user = findByUsername(username); 此处不可以调用缓存，否则清理了UserPermissions缓存，还要清理用户缓存
		SysUserExample example = new SysUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<SysUser> users = sysUserMapper.selectByExampleWithRowbounds(example , new RowBounds(0, 1));
		if(users.size()==0) return roles;

		SysUser user = users.get(0);

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
