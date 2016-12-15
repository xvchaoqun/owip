package service.sys;

import domain.ext.ExtBks;
import domain.ext.ExtJzg;
import domain.ext.ExtYjs;
import domain.party.EnterApply;
import domain.sys.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.ext.ExtBksService;
import service.ext.ExtJzgService;
import service.ext.ExtYjsService;
import service.helper.ShiroSecurityHelper;
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

    @Autowired
    protected ExtJzgService extJzgService;
    @Autowired
    protected ExtYjsService extYjsService;
    @Autowired
    protected ExtBksService extBksService;

    // 获取用户所在的学校人事库或学生库中的单位名称
    public String getUnit(SysUserView sysUser) {

        String code = sysUser.getCode();
        Byte type = sysUser.getType();
        String unit = null;
        if (type == SystemConstants.USER_TYPE_JZG) {
            ExtJzg extJzg = extJzgService.getByCode(code);
            if (extJzg != null) {
                unit = extJzg.getDwmc();
                if (StringUtils.isNotBlank(extJzg.getYjxk())) unit += "-" + extJzg.getYjxk();
            }

        } else if (type == SystemConstants.USER_TYPE_YJS) {
            ExtYjs extYjs = extYjsService.getByCode(code);
            if (extYjs != null) {
                unit = extYjs.getYxsmc();
                if (StringUtils.isNotBlank(extYjs.getYjfxmc())) unit += "-" + extYjs.getYjfxmc();
            }
        } else if (type == SystemConstants.USER_TYPE_BKS) {
            ExtBks extBks = extBksService.getByCode(code);
            if (extBks != null) {
                unit = extBks.getYxmc();
                if (StringUtils.isNotBlank(extBks.getZymc())) unit += "-" + extBks.getZymc();
            }
        }
        return unit;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#username"),
            @CacheEvict(value = "Menus", key = "#username"),
            @CacheEvict(value = "SysUserView", key = "#username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#userId"),
            @CacheEvict(value = "UserPermissions", key = "#username")
    })
    public void changeRoleGuestToMember(int userId, String username, String code) {

        // 更新系统角色  访客->党员
        changeRole(userId, SystemConstants.ROLE_GUEST,
                SystemConstants.ROLE_MEMBER, username, code);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#username"),
            @CacheEvict(value = "Menus", key = "#username"),
            @CacheEvict(value = "SysUserView", key = "#username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#userId"),
            @CacheEvict(value = "UserPermissions", key = "#username")
    })
    public void changeRoleMemberToGuest(int userId, String username, String code) {

        // 更新系统角色  党员->访客
        changeRole(userId, SystemConstants.ROLE_MEMBER,
                SystemConstants.ROLE_GUEST, username, code);
        // 撤回原申请
        EnterApply _enterApply = enterApplyService.getCurrentApply(userId);
        if (_enterApply != null) {
            enterApplyService.applyBack(userId, null, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT);
        }
    }


    public boolean idDuplicate(Integer userId, String username, String code) {

        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username);
        if (userId != null) criteria.andIdNotEqualTo(userId);
        if (StringUtils.isNotBlank(code)) criteria.andCodeEqualTo(code);

        return sysUserMapper.countByExample(example) > 0;
    }

    public String buildRoleIds(String roleStr) {

        SysRole sysRole = sysRoleService.getByRole(roleStr);
        return SystemConstants.USER_ROLEIDS_SEPARTOR + sysRole.getId() + SystemConstants.USER_ROLEIDS_SEPARTOR;
    }

    @Transactional
    @Caching(evict = { // 如果没添加前使用了账号登录或其他原因，可能导致缓存存在且为NULL
            @CacheEvict(value = "UserRoles", key = "#record.username"),
            @CacheEvict(value = "Menus", key = "#record.username"),
            @CacheEvict(value = "SysUserView", key = "#record.username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#record.code"),
            @CacheEvict(value = "UserPermissions", key = "#record.username")
    })
    public void insertSelective(SysUser record) {

        if (StringUtils.isBlank(record.getRoleIds()))
            record.setRoleIds(buildRoleIds(SystemConstants.ROLE_GUEST));
        sysUserMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "SysUserView", key = "#result.Username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#result.code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#result.id")
    })
    public SysUser insertOrUpdateUserInfoSelective(SysUserInfo record) {

        SysUser sysUser = dbFindById(record.getUserId());
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(record.getUserId());
        if ( sysUserInfo == null) {

            if(StringUtils.isBlank(record.getNativePlace())){
                record.setNativePlace(getExtNativePlace(sysUser.getSource(), sysUser.getCode()));
            }

            sysUserInfoMapper.insertSelective(record);
        }else {

            if(StringUtils.isBlank(record.getNativePlace())){
                record.setNativePlace(getExtNativePlace(sysUser.getSource(), sysUser.getCode()));
            }

            sysUserInfoMapper.updateByPrimaryKeySelective(record);
        }

        return sysUser;
    }

    // 照片、籍贯、 出生地、户籍地： 这四个字段只从人事库同步一次， 之后就不
    // 再同步这个信息了。 然后可以对这四个字段进行编辑。

    // 籍贯
    private String getExtNativePlace(byte source, String code){

        if(source==SystemConstants.USER_SOURCE_JZG) {
            ExtJzg extJzg = extJzgService.getByCode(code);
            if(extJzg!=null){
                return extJzg.getJg();
            }
        }
        if(source==SystemConstants.USER_SOURCE_BKS) {
            ExtBks extBks = extBksService.getByCode(code);
            if(extBks!=null){
                return extBks.getSf();
            }
        }
        if(source==SystemConstants.USER_SOURCE_YJS) {
                    /*ExtYjs extYjs = extYjsService.getByCode(code);
                    if(extYjs!=null){
                        //record.setNativePlace(extYjs.get);
                        //record.getHousehold(extJzg.getc);
                    }*/
        }
        return null;
    }

    public SysUser dbFindById(int id) {

        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "SysUserView:ID_", key = "#id")
    public SysUserView findById(int id) {

        SysUserViewExample example = new SysUserViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<SysUserView> users = sysUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (users.size() > 0) ? users.get(0) : null;
    }

    @Cacheable(value = "SysUserView", key = "#username")
    public SysUserView findByUsername(String username) {

        SysUserViewExample example = new SysUserViewExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<SysUserView> users = sysUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (users.size() > 0) ? users.get(0) : null;
    }

    @Cacheable(value = "SysUserView:CODE_", key = "#code")
    public SysUserView findByCode(String code) {

        SysUserViewExample example = new SysUserViewExample();
        example.createCriteria().andCodeEqualTo(code);
        List<SysUserView> users = sysUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (users.size() > 0) ? users.get(0) : null;
    }

    // 根据角色标识查找用户
    public List<SysUserView> findByRole(String role) {

        SysRole sysRole = sysRoleService.getByRole(role);

        SysUserViewExample example = new SysUserViewExample();
        SysUserViewExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdsLike("%," + sysRole.getId() + ",%");
        criteria.andLockedEqualTo(false);

        return sysUserViewMapper.selectByExample(example);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "SysUserView", key = "#oldUsername"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#user.id")
    })
    public int updateByPrimaryKeySelective(SysUser user, String oldUsername, String code) {

        return sysUserMapper.updateByPrimaryKeySelective(user);
    }

    /*@Transactional
    @Caching(evict = {
            @CacheEvict(value = "SysUserView", key = "#result.Username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#result.code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#result.id")
    })
    public SysUser updateUserInfoByPrimaryKeySelective(SysUserInfo record) {

        SysUser sysUser = dbFindById(record.getUserId());
        sysUserInfoMapper.updateByPrimaryKeySelective(record);

        return sysUser;
    }*/

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "SysUserView", key = "#username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#id")
    })
    public int lockUser(int id, String username, String code, boolean locked) {

        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        SysUser record = new SysUser();
        record.setLocked(locked);

        return sysUserMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#oldUsername"),
            @CacheEvict(value = "Menus", key = "#oldUsername"),
            @CacheEvict(value = "SysUserView", key = "#oldUsername"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#id"),
            @CacheEvict(value = "UserPermissions", key = "#oldUsername")
    })
    public int deleteByPrimaryKey(Integer id, String oldUsername, String code) {

        return sysUserMapper.deleteByPrimaryKey(id);
    }

    public Set<Integer> getUserRoleIdSet(String roleIdsStr) {

        Set<Integer> roleIds = new HashSet<>();
        if (StringUtils.isBlank(roleIdsStr)) return roleIds;
        String[] roleIdStrs = roleIdsStr.split(SystemConstants.USER_ROLEIDS_SEPARTOR);
        for (String roleIdStr : roleIdStrs) {
            if (StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            roleIds.add(Integer.valueOf(roleIdStr));
        }

        return roleIds;
    }

    // 删除一个角色
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#username"),
            @CacheEvict(value = "Menus", key = "#username"),
            @CacheEvict(value = "SysUserView", key = "#username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#userId"),
            @CacheEvict(value = "UserPermissions", key = "#username")
    })
    public void delRole(int userId, String role, String username, String code) {

        SysUser sysUser = dbFindById(userId);
        Assert.isTrue(StringUtils.equalsIgnoreCase(sysUser.getUsername(), username));

        SysRole sysRole = sysRoleService.getByRole(role);
        Set<Integer> roleIdSet = getUserRoleIdSet(sysUser.getRoleIds());
        roleIdSet.remove(sysRole.getId());

        SysUser record = new SysUser();
        record.setId(userId);
        record.setRoleIds(SystemConstants.USER_ROLEIDS_SEPARTOR +
                StringUtils.join(roleIdSet, SystemConstants.USER_ROLEIDS_SEPARTOR)
                + SystemConstants.USER_ROLEIDS_SEPARTOR);
        updateByPrimaryKeySelective(record, sysUser.getUsername(), code);
    }

    // 添加一个角色（重复则替换）
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#username"),
            @CacheEvict(value = "Menus", key = "#username"),
            @CacheEvict(value = "SysUserView", key = "#username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#userId"),
            @CacheEvict(value = "UserPermissions", key = "#username")
    })
    public void addRole(int userId, String role, String username, String code) {

        SysUser sysUser = dbFindById(userId);
        Assert.isTrue(StringUtils.equalsIgnoreCase(sysUser.getUsername(), username));

        SysRole sysRole = sysRoleService.getByRole(role);
        Set<Integer> roleIdSet = getUserRoleIdSet(sysUser.getRoleIds());
        roleIdSet.add(sysRole.getId());

        SysUser record = new SysUser();
        record.setId(userId);
        record.setRoleIds(SystemConstants.USER_ROLEIDS_SEPARTOR +
                StringUtils.join(roleIdSet, SystemConstants.USER_ROLEIDS_SEPARTOR)
                + SystemConstants.USER_ROLEIDS_SEPARTOR);
        updateByPrimaryKeySelective(record, sysUser.getUsername(), code);

    }


    // 改变角色
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#username"),
            @CacheEvict(value = "Menus", key = "#username"),
            @CacheEvict(value = "SysUserView", key = "#username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#userId"),
            @CacheEvict(value = "UserPermissions", key = "#username")
    })
    public void changeRole(int userId, String role, String toRole, String username, String code) {

        SysUser sysUser = dbFindById(userId);
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
        updateByPrimaryKeySelective(record, sysUser.getUsername(), code);

        //踢下线（如果登入的话）
        ShiroSecurityHelper.kickOutUser(username);
    }

    // 根据账号查找所有的角色（对象）
    private Set<SysRole> _findRoles(String username) {

        Set<SysRole> roles = new HashSet<>();

        //SysUser user = findByUsername(username); 此处不可以调用缓存，否则清理了UserPermissions缓存，还要清理用户缓存
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<SysUser> users = sysUserMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (users.size() == 0) return roles;

        SysUser user = users.get(0);

        Set<Integer> roleIds = getUserRoleIdSet(user.getRoleIds());
        Map<Integer, SysRole> roleMap = sysRoleService.findAll();
        for (Integer roleId : roleIds) {
            SysRole role = roleMap.get(roleId);
            if (role != null)
                roles.add(role);
        }
        return roles;
    }

    // 根据账号查找所拥有的全部资源
    private List<SysResource> findResources(String username) {

        List<SysResource> resources = new ArrayList<>();
        Set<SysRole> roles = _findRoles(username);
        List<Integer> resourceIds = new ArrayList<Integer>();

        for (SysRole role : roles) {

            String resourceIdsStr = role.getResourceIds();

            if (StringUtils.isBlank(resourceIdsStr)) continue;

            String[] resourceIdStrs = resourceIdsStr.split(SystemConstants.USER_ROLEIDS_SEPARTOR);
            for (String resourceIdStr : resourceIdStrs) {
                if (StringUtils.isEmpty(resourceIdStr)) {
                    continue;
                }
                resourceIds.add(Integer.valueOf(resourceIdStr));
            }
        }

        if (resourceIds.size() == 0) return resources;

        Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources();
        for (Integer resourceId : resourceIds) {
            SysResource resource = resourceMap.get(resourceId);
            if (resource != null)
                resources.add(resource);
        }
        return resources;
    }

    // 根据账号查找所有的角色（角色字符串集合）
    @Cacheable(value = "UserRoles", key = "#username")
    public Set<String> findRoles(String username) {

        Set<SysRole> _roles = _findRoles(username);
        Set<String> roles = new HashSet<String>();
        for (SysRole role : _roles) {
            roles.add(role.getRole());
        }
        return roles;
    }

    @Cacheable(value = "UserPermissions", key = "#username")
    public Set<String> findPermissions(String username) {

        List<SysResource> resources = findResources(username);

        Set<String> permissions = new HashSet<String>();
        for (SysResource resource : resources) {
            if (StringUtils.isNotBlank(resource.getPermission()))
                permissions.add(resource.getPermission().trim());
        }

        return permissions;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#username"),
            @CacheEvict(value = "Menus", key = "#username"),
            @CacheEvict(value = "SysUserView", key = "#username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#userId"),
            @CacheEvict(value = "UserPermissions", key = "#username")
    })
    public void updateUserRoles(int userId, String username, String code, String roleIds) {

        SysUser user = new SysUser();
        user.setId(userId);
        user.setRoleIds(roleIds);

        sysUserMapper.updateByPrimaryKeySelective(user);
    }

}
