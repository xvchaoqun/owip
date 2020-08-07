package service.sys;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.pcs.PcsAdmin;
import domain.sys.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import persistence.abroad.common.ApproverTypeBean;
import service.BaseMapper;
import service.abroad.ApplySelfService;
import service.abroad.ApproverService;
import service.global.CacheHelper;
import service.party.PartyAdminService;
import service.pcs.PcsAdminService;
import service.pmd.PmdPartyAdminService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.PcsConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FileUtils;

import java.io.IOException;
import java.util.*;

@Service
public class SysUserService extends BaseMapper {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private CacheHelper cacheHelper;

    public String uploadSign(int userId, MultipartFile sign) throws IOException {

        if (sign == null || sign.isEmpty()) {
            return null;
        }

        String savePath = FILE_SEPARATOR + "sign" + FILE_SEPARATOR + userId + ".png";

        FileUtils.mkdirs(springProps.uploadPath + savePath);
        Thumbnails.of(sign.getInputStream())
                .size(750, 500)
                //.outputFormat("png")
                .outputQuality(1.0f)
                .toFile(springProps.uploadPath + savePath);

        return savePath;
    }

    @Transactional
    public void changeRoleGuestToMember(int userId) {

        // 更新系统角色  访客->党员
        changeRole(userId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);
    }

    public boolean idDuplicate(Integer userId, String username, String code) {

        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username);
        if (userId != null) criteria.andIdNotEqualTo(userId);
        if (StringUtils.isNotBlank(code)) criteria.andCodeEqualTo(code);

        return sysUserMapper.countByExample(example) > 0;
    }

    // 自动生成学工号, prefix开头+6位数字
    public String genCode(String prefix) {

        String code = prefix + RandomStringUtils.randomNumeric(6);
        SysUserExample example = new SysUserExample();
        example.or().andCodeEqualTo(code);
        example.or().andUsernameEqualTo(code);
        long count = sysUserMapper.countByExample(example);

        return (count == 0) ? code : genCode(prefix);
    }

    public String buildRoleIds(String roleStr) {

        SysRole sysRole = sysRoleService.getByRole(roleStr);
        if (sysRole == null) {
            throw new OpException("角色[{0}]不存在，请联系管理员。", roleStr);
        }
        return SystemConstants.USER_ROLEIDS_SEPARTOR + sysRole.getId() + SystemConstants.USER_ROLEIDS_SEPARTOR;
    }

    @Transactional
    public void batchImport(Map<SysUserView, TeacherInfo> records) throws ReflectiveOperationException {

        for (Map.Entry<SysUserView, TeacherInfo> entry: records.entrySet()){

            SysUserView record = entry.getKey();
            TeacherInfo teacherInfo = entry.getValue();

            SysUser user = new SysUser();
            SysUserInfo userInfo = new SysUserInfo();
            PropertyUtils.copyProperties(user, record);
            PropertyUtils.copyProperties(userInfo, record);

            Integer userId = record.getUserId();
            if (userId == null) {
                user.setRoleIds(buildRoleIds(RoleConstants.ROLE_GUEST));
                sysUserMapper.insertSelective(user);
                userId = user.getId();

                userInfo.setUserId(userId);
                sysUserInfoMapper.insertSelective(userInfo);
            }else {
                sysUserMapper.updateByPrimaryKeySelective(user);
                sysUserInfoMapper.updateByPrimaryKeySelective(userInfo);
            }

            if (record.getType() == SystemConstants.USER_TYPE_JZG) {

                addRole(userId, RoleConstants.ROLE_TEACHER);
                if (teacherInfo.getUserId() == null) {
                    teacherInfo.setUserId(userId);
                    teacherInfo.setIsRetire(false);
                    teacherInfoMapper.insertSelective(teacherInfo);
                } else {
                    TeacherInfoExample example = new TeacherInfoExample();
                    example.createCriteria().andUserIdEqualTo(userId);
                    teacherInfoMapper.updateByExampleSelective(teacherInfo, example);
                }
            }else {
                if (findRoles(user.getUsername()).contains(RoleConstants.ROLE_TEACHER)){
                    delRole(userId, RoleConstants.ROLE_TEACHER);
                }
            }

            cacheHelper.clearUserCache(user);
        }
    }

    @Transactional
    public void insertSelective(SysUser record) {

        if (StringUtils.isBlank(record.getRoleIds()))
            record.setRoleIds(buildRoleIds(RoleConstants.ROLE_GUEST));
        sysUserMapper.insertSelective(record);

        int userId = record.getId();
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(userId);
        if (sysUserInfo == null) {
            sysUserInfo = new SysUserInfo();
            sysUserInfo.setUserId(userId);
            sysUserInfoMapper.insertSelective(sysUserInfo);
        }
        if (record.getType() == SystemConstants.USER_TYPE_JZG) {
            addRole(userId, RoleConstants.ROLE_TEACHER);
        }
        // 如果没添加前使用了账号登录或其他原因，可能导致缓存存在且为NULL
        cacheHelper.clearUserCache(record);
    }

    @Transactional
    public void insertOrUpdateUserInfoSelective(SysUserInfo record) {
        insertOrUpdateUserInfoSelective(record, null);
    }

    @Transactional
    public void insertOrUpdateUserInfoSelective(SysUserInfo record, TeacherInfo teacherInfo) {

        SysUser _sysUser = dbFindById(record.getUserId());
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(record.getUserId());
        if (sysUserInfo == null) {
            sysUserInfoMapper.insertSelective(record);
        } else {
            if (record.getRealname() == null) { // 防止出现 update sys_user_info set user_id=xxxx;报错
                record.setRealname(StringUtils.trimToEmpty(sysUserInfo.getRealname()));
            }
            sysUserInfoMapper.updateByPrimaryKeySelective(record);
        }

        if (teacherInfo != null) {
            teacherInfoMapper.updateByPrimaryKeySelective(teacherInfo);
            cacheHelper.clearUserCache(_sysUser);
        }

        cacheHelper.clearUserCache(_sysUser);
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

    public SysUserView dbFindByCode(String code) {

        SysUserViewExample example = new SysUserViewExample();
        example.createCriteria().andCodeEqualTo(code);
        List<SysUserView> users = sysUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (users.size() > 0) ? users.get(0) : null;
    }

    @Cacheable(value = "SysUserView:CODE_", key = "#code")
    public SysUserView findByCode(String code) {

        return dbFindByCode(code);
    }

    // 根据角色标识查找用户
    public List<SysUserView> findByRole(String role) {

        SysRole sysRole = sysRoleService.getByRole(role);
        if (sysRole == null) {
            throw new OpException("角色{0}不存在。", role);
        }
        SysUserViewExample example = new SysUserViewExample();
        SysUserViewExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdsLike("%," + sysRole.getId() + ",%");
        criteria.andLockedEqualTo(false);

        return sysUserViewMapper.selectByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SysUser user) {

        int userId = user.getId();
        SysUser _sysUser = sysUserMapper.selectByPrimaryKey(userId);
        sysUserMapper.updateByPrimaryKeySelective(user);

        if (user.getType() != null) {
            if (user.getType() == SystemConstants.USER_TYPE_JZG) {
                addRole(userId, RoleConstants.ROLE_TEACHER);
            } else {
                delRole(userId, RoleConstants.ROLE_TEACHER);
            }
        }

        cacheHelper.clearUserCache(_sysUser);
    }

    @Transactional
    public void lockUser(int id, boolean locked) {

        SysUser _sysUser = sysUserMapper.selectByPrimaryKey(id);

        SysUser record = new SysUser();
        record.setId(id);
        record.setLocked(locked);

        sysUserMapper.updateByPrimaryKeySelective(record);

        cacheHelper.clearUserCache(_sysUser);
    }

    @Transactional
    public void deleteByPrimaryKey(Integer id) {

        SysUser _sysUser = sysUserMapper.selectByPrimaryKey(id);
        sysUserMapper.deleteByPrimaryKey(id);
        cacheHelper.clearUserCache(_sysUser);
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

    public Set<Integer> getUserResIdSet(String resIdsStr) {

        Set<Integer> resIds = new HashSet<>();
        if (StringUtils.isBlank(resIdsStr)) return resIds;
        String[] resIdStrs = resIdsStr.split(SystemConstants.USER_ROLEIDS_SEPARTOR);
        for (String resIdStr : resIdStrs) {
            if (StringUtils.isEmpty(resIdStr)) {
                continue;
            }
            resIds.add(Integer.valueOf(resIdStr));
        }

        return resIds;
    }
    // 删除一个角色
    @Transactional
    public void delRole(int userId, String role) {

        SysUser _sysUser = dbFindById(userId);

        SysRole sysRole = sysRoleService.getByRole(role);
        if (sysRole == null) {
            throw new OpException("角色{0}不存在。", role);
        }
        Set<Integer> roleIdSet = getUserRoleIdSet(_sysUser.getRoleIds());
        roleIdSet.remove(sysRole.getId());

        SysUser record = new SysUser();
        record.setId(userId);
        record.setRoleIds(SystemConstants.USER_ROLEIDS_SEPARTOR +
                StringUtils.join(roleIdSet, SystemConstants.USER_ROLEIDS_SEPARTOR)
                + SystemConstants.USER_ROLEIDS_SEPARTOR);
        updateByPrimaryKeySelective(record);

        cacheHelper.clearUserCache(_sysUser);
    }

    // 添加一个角色（重复则替换）
    @Transactional
    public void addRole(int userId, String role) {

        SysUser _sysUser = dbFindById(userId);

        SysRole sysRole = sysRoleService.getByRole(role);
        if (sysRole == null) throw new OpException("系统角色" + role + "不存在");
        Set<Integer> roleIdSet = getUserRoleIdSet(_sysUser.getRoleIds());
        roleIdSet.add(sysRole.getId());

        SysUser record = new SysUser();
        record.setId(userId);
        record.setRoleIds(SystemConstants.USER_ROLEIDS_SEPARTOR +
                StringUtils.join(roleIdSet, SystemConstants.USER_ROLEIDS_SEPARTOR)
                + SystemConstants.USER_ROLEIDS_SEPARTOR);
        updateByPrimaryKeySelective(record);

        cacheHelper.clearUserCache(_sysUser);
    }


    // 改变角色
    @Transactional
    public void changeRole(int userId, String role, String toRole) {

        SysUser _sysUser = dbFindById(userId);

        SysRole sysRole = sysRoleService.getByRole(role);
        SysRole toSysRole = sysRoleService.getByRole(toRole);
        Set<Integer> roleIdSet = getUserRoleIdSet(_sysUser.getRoleIds());
        if (sysRole != null)
            roleIdSet.remove(sysRole.getId());
        if (toSysRole != null)
            roleIdSet.add(toSysRole.getId());

        SysUser record = new SysUser();
        record.setId(userId);
        record.setRoleIds(SystemConstants.USER_ROLEIDS_SEPARTOR +
                StringUtils.join(roleIdSet, SystemConstants.USER_ROLEIDS_SEPARTOR)
                + SystemConstants.USER_ROLEIDS_SEPARTOR);
        updateByPrimaryKeySelective(record);

        cacheHelper.clearUserCache(_sysUser);

        //踢下线（如果登入的话）
        ShiroHelper.kickOutUser(_sysUser.getUsername());
    }

    // 根据账号查找所有的角色（对象）
    public Set<SysRole> findAllRoles(String username) {

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

    // 根据账号查找所有的角色（对象）,type=1 加权限  2 减权限
    public Set<SysRole> findTypeRoles(String username,byte type) {

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
            if (role != null&&role.getType()==type)
                roles.add(role);
        }

        return roles;
    }

    // 根据账号查找账号加减权限
    public Set<String> findUserPermission(int userId, boolean isMobile) {
        Set<String> permissions = new HashSet<String>();
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        String[] usersAdd = findUserResId(sysUser.getUsername(), isMobile,SystemConstants.SYS_ROLE_TYPE_ADD);
        String[] usersMinus = findUserResId(sysUser.getUsername(), isMobile,SystemConstants.SYS_ROLE_TYPE_MINUS);

        Map<Integer, SysResource> sysResources = sysResourceService.getSortedSysResources(isMobile);
        if(usersAdd!=null){
            for(String userAdd:usersAdd){
                if(StringUtils.isNotBlank(userAdd)){
                    SysResource sysResource = sysResources.get(Integer.parseInt(userAdd));
                    if(sysResource!=null && StringUtils.isNotBlank(sysResource.getPermission())){
                        permissions.add(sysResource.getPermission().trim());
                    }
                }
            }
        }
        if(usersMinus!=null){
            for(String userMinus:usersMinus){
                if(StringUtils.isNotBlank(userMinus)){
                    SysResource sysResource = sysResources.get(Integer.parseInt(userMinus));
                    if(sysResource!=null && StringUtils.isNotBlank(sysResource.getPermission())){
                        permissions.remove(sysResource.getPermission().trim());
                    }
                }
            }
        }
       return permissions;
    }

    // 根据账号及类别查找权限
    public String[] findUserResId(String username, boolean isMobile,byte type) {

        String[] resIds=null;

        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<SysUser> users = sysUserMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (users.size() == 0) return resIds;

        SysUser user = users.get(0);
        SysUserInfo sysUserInfo  = sysUserInfoMapper.selectByPrimaryKey(user.getId());
        if(sysUserInfo!=null) {
            if (!isMobile && type == SystemConstants.SYS_ROLE_TYPE_ADD) {
                if (StringUtils.isBlank(sysUserInfo.getResIdsAdd())) return resIds;
                resIds = sysUserInfo.getResIdsAdd().split(SystemConstants.USER_ROLEIDS_SEPARTOR);
            } else if (isMobile && type == SystemConstants.SYS_ROLE_TYPE_ADD) {
                if (StringUtils.isBlank(sysUserInfo.getmResIdsAdd())) return resIds;
                resIds = sysUserInfo.getmResIdsAdd().split(SystemConstants.USER_ROLEIDS_SEPARTOR);
            } else if (!isMobile && type == SystemConstants.SYS_ROLE_TYPE_MINUS) {
                if (StringUtils.isBlank(sysUserInfo.getResIdsMinus())) return resIds;
                resIds = sysUserInfo.getResIdsMinus().split(SystemConstants.USER_ROLEIDS_SEPARTOR);
            } else if (isMobile && type == SystemConstants.SYS_ROLE_TYPE_MINUS) {
                if (StringUtils.isBlank(sysUserInfo.getmResIdsMinus())) return resIds;
                resIds = sysUserInfo.getmResIdsMinus().split(SystemConstants.USER_ROLEIDS_SEPARTOR);
            }
        }

        return resIds;
    }

    // 根据账号查找所拥有的全部资源
    private List<SysResource> findResources(String username, boolean isMobile) {

        List<SysResource> resources = new ArrayList<>();
        Set<SysRole> rolesAdd = findTypeRoles(username, SystemConstants.SYS_ROLE_TYPE_ADD); // 账号加权限角色Set
        Set<SysRole> rolesMinus = findTypeRoles(username,SystemConstants.SYS_ROLE_TYPE_MINUS); // 账号减权限角色Set

        String[] usersAdd = findUserResId(username, isMobile,SystemConstants.SYS_ROLE_TYPE_ADD); // 账号加权限Set
        String[] usersMinus = findUserResId(username, isMobile,SystemConstants.SYS_ROLE_TYPE_MINUS);// 账号减权限Set

        Set<Integer> resourceIds = new LinkedHashSet<>();

        for (SysRole roleAdd : rolesAdd) {

            String resourceIdsStr = isMobile ? roleAdd.getmResourceIds() : roleAdd.getResourceIds();

            if (StringUtils.isBlank(resourceIdsStr)) continue;

            String[] resourceIdStrs = resourceIdsStr.split(SystemConstants.USER_ROLEIDS_SEPARTOR);
            for (String resourceIdStr : resourceIdStrs) {
                if (StringUtils.isEmpty(resourceIdStr)) {
                    continue;
                }
                resourceIds.add(Integer.valueOf(resourceIdStr)); //账号角色加资源
            }
        }

        if (usersAdd!=null){
            for (String userAdd : usersAdd) {
                if (StringUtils.isEmpty(userAdd)) {
                    continue;
                }
                resourceIds.add(Integer.valueOf(userAdd)); //账号加资源
            }
        }

        for (SysRole roleMinus : rolesMinus) {

            String resourceIdsStr = isMobile ? roleMinus.getmResourceIds() : roleMinus.getResourceIds();

            if (StringUtils.isBlank(resourceIdsStr)) continue;

            String[] resourceIdStrs = resourceIdsStr.split(SystemConstants.USER_ROLEIDS_SEPARTOR);
            for (String resourceIdStr : resourceIdStrs) {
                if (StringUtils.isEmpty(resourceIdStr)) {
                    continue;
                }
                resourceIds.remove(Integer.valueOf(resourceIdStr)); //账号角色减资源
            }
        }

        if (usersMinus!=null) {
            for (String userMinus : usersMinus) {
                if (StringUtils.isEmpty(userMinus)) {
                    continue;
                }
                resourceIds.remove(Integer.valueOf(userMinus)); //账号减资源
            }
        }

        if (resourceIds.size() == 0) return resources;

        Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources(isMobile);
        for (Integer resourceId : resourceIds) {
            SysResource resource = resourceMap.get(resourceId);
            if (resource != null)
                resources.add(resource);
        }
        return resources;
    }

    // 根据拥有的权限，形成菜单栏目
    public List<SysResource> makeMenus(Set<String> ownPermissions, boolean isMobile) {

        List<SysResource> menus = new ArrayList<>();
        Map<Integer, SysResource> sortedPermissions = sysResourceService.getSortedSysResources(isMobile);
        for (SysResource res : sortedPermissions.values()) {
            String type = res.getType();
            String permission = res.getPermission();
            if ((StringUtils.equalsIgnoreCase(type, SystemConstants.RESOURCE_TYPE_MENU)
                    || StringUtils.equalsIgnoreCase(type, SystemConstants.RESOURCE_TYPE_URL))
                    && ownPermissions.contains(permission)) {

                if (res.getParentId() == null) {
                    menus.add(res);
                } else {
                    SysResource parent = sortedPermissions.get(res.getParentId());

                    // id=1是顶级节点，此值必须固定为1
                    if (parent.getParentId() == null) {
                        menus.add(res);
                        continue;
                    }

                    // 必须拥有全部层级的父目录，才显示
                    List<String> parents = new ArrayList<>();
                    while (parent != null && parent.getParentId() != null) {
                        parents.add(parent.getPermission());
                        if (parent.getParentId() != null)
                            parent = sortedPermissions.get(parent.getParentId());
                        else
                            parent = null;
                    }

                    if (ownPermissions.containsAll(parents)) {
                        menus.add(res);
                        continue;
                    }
                }
            }
        }

        return menus;
    }

    // 根据账号查找所有的角色（角色字符串集合）
    @Cacheable(value = "UserRoles", key = "#username")
    public Set<String> findRoles(String username) {

        Set<SysRole> _roles = findAllRoles(username);
        Set<String> roleCodes = new HashSet<String>();
        for (SysRole role : _roles) {
            roleCodes.add(role.getCode());
        }
        return roleCodes;
    }

    @Cacheable(value = "UserPermissions", key = "#username + ':' + (#isMobile?1:0)")
    public Set<String> findPermissions(String username, boolean isMobile) {

        if(StringUtils.isBlank(username)) return null;

        SysUserView sysUser = findByUsername(username);
        List<SysResource> resources = findResources(username, isMobile);

        Set<String> permissions = new HashSet<String>();
        for (SysResource resource : resources) {
            if (StringUtils.isNotBlank(resource.getPermission()))
                permissions.add(resource.getPermission().trim());
        }

        return isMobile ? mFilterMenus(sysUser.getId(), findRoles(username), permissions)
                : filterMenus(sysUser.getId(), findRoles(username), permissions);
    }

    @Transactional
    public void updateUserRoles(int userId, String roleIds) {

        SysUser _sysUser = sysUserMapper.selectByPrimaryKey(userId);

        SysUser user = new SysUser();
        user.setId(userId);
        user.setRoleIds(roleIds);

        sysUserMapper.updateByPrimaryKeySelective(user);

        cacheHelper.clearUserCache(_sysUser);
    }

    /**
     * 特殊的用户权限过滤（网页端）
     */
    public Set<String> filterMenus(int userId, Set<String> userRoles, Set<String> userPermissions) {

        ApproverTypeBean approverTypeBean = null;
        ApplySelfService applySelfService = CmTag.getBean(ApplySelfService.class);
        if (applySelfService != null)
            approverTypeBean = applySelfService.getApproverTypeBean(userId);

        // 直属党支部管理员不需要“党支部管理、支部委员会”
        if (userRoles.contains(RoleConstants.ROLE_PARTYADMIN)) {

            PartyAdminService partyAdminService = CmTag.getBean(PartyAdminService.class);
            if (partyAdminService != null) {
                List<Integer> adminPartyIdList = partyAdminService.adminPartyIdList(userId);
                boolean hasAdminParty = false;
                for (Integer adminPartyId : adminPartyIdList) {
                    if (!PartyHelper.isDirectBranch(adminPartyId)) {
                        hasAdminParty = true;
                        break;
                    }
                }
                if (!hasAdminParty) {
                    userPermissions.remove("branch:list");
                    userPermissions.remove("branchMemberGroup:list");
                }
            }
        }

        // 党费收缴，直属党支部不具有设置支部管理员的权限
        if (userRoles.contains(RoleConstants.ROLE_PMD_PARTY)) {
            PmdPartyAdminService pmdPartyAdminService = CmTag.getBean(PmdPartyAdminService.class);
            if (pmdPartyAdminService != null) {
                List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
                boolean hasAdminParty = false;
                for (Integer adminPartyId : adminPartyIds) {
                    if (!PartyHelper.isDirectBranch(adminPartyId)) {
                        hasAdminParty = true;
                        break;
                    }
                }
                if (!hasAdminParty) {
                    userPermissions.remove("pmdPayBranch:*");
                }
            }
        }

        // 党代会分党委管理员，只有书记才拥有添加分党委管理员的权限
        if (userRoles.contains(RoleConstants.ROLE_PCS_ADMIN)) {

            PcsAdminService pcsAdminService = CmTag.getBean(PcsAdminService.class);
            if (pcsAdminService != null) {
                PcsAdmin pcsAdmin = pcsAdminService.getAdmin(userId);
                if (pcsAdmin == null || pcsAdmin.getType() != PcsConstants.PCS_ADMIN_TYPE_SECRETARY) {
                    userPermissions.remove("pcsPartyAdmin:*");
                }
            }
        }

        // 干部
        if (userRoles.contains(RoleConstants.ROLE_CADRE_CJ)) {
            CadreView cadre = CmTag.getCadreByUserId(userId);

            //科级干部、考察对象和离任处级干部不可以看到因私出国申请，现任处级干部和离任校领导可以
            if (cadre == null || cadre.getStatus() != CadreConstants.CADRE_STATUS_CJ
                    && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER
                    && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER_LEAVE) {
                userPermissions.remove("abroad:user"); // 因私出国境申请（干部目录）
                userPermissions.remove("userApplySelf:*"); // 申请因私出国境（干部）
                userPermissions.remove("userPassportDraw:*"); // 申请使用证件（干部）
                userPermissions.remove("userPassportApply:*"); // 因私出国境证件（干部）
            }

            if (cadre != null && CmTag.getLeader(cadre.getUserId()) != null) {

                // 校领导没有(userApplySelf:*， userPassportDraw:*)
                userPermissions.remove("userApplySelf:*");
                userPermissions.remove("userPassportDraw:*");
            }

            // 没有审批权限的干部，没有（abroad:menu（目录）, applySelf:approvalList)
            if (cadre == null || (cadre.getStatus() != CadreConstants.CADRE_STATUS_CJ
                    && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER) || approverTypeBean == null ||
                    !approverTypeBean.isApprover()) {

                userPermissions.remove("applySelf:approvalList"); // 因私出国境审批
                if (!userRoles.contains(RoleConstants.ROLE_CADREADMIN)) {
                    // 干部管理员 需要目录，普通干部不需要
                    userPermissions.remove("abroad:menu"); // 因私出国境审批（目录）
                }
            }

            // 非干部管理员账号如果有直接修改本人干部信息的权限，则不能看到“干部信息修改申请”菜单
            boolean hasDirectModifyCadreAuth = CmTag.hasDirectModifyCadreAuth(cadre.getId());
            /*if (!userRoles.contains(RoleConstants.ROLE_CADREADMIN) && hasDirectModifyCadreAuth) {
                userPermissions.remove("modifyCadreInfo:menu");
            }*/
            if (hasDirectModifyCadreAuth) {
                userPermissions.remove("userModifyCadre:menu");
            }
        }

        return userPermissions;
    }

    /**
     * 特殊的用户权限过滤（手机端）
     */
    public Set<String> mFilterMenus(int userId, Set<String> userRoles, Set<String> userPermissions) {

        ApproverService approverService = CmTag.getBean(ApproverService.class);
        if (approverService != null) {
            // 是干部
            if (userRoles.contains(RoleConstants.ROLE_CADRE_CJ)) {

                // 是干部管理员 或 没有因私审批权限
                if (userRoles.contains(RoleConstants.ROLE_CADREADMIN) ||
                        !approverService.hasApproveAuth(userId)) {

                    userPermissions.remove("m:applySelfList:*");
                }
            }
        }

        return userPermissions;
    }

    // 批量更新账号信息
    @Transactional
    public void batchUpdate(List<SysUserInfo> records, List<TeacherInfo> teacherInfos) {

        for (TeacherInfo record : teacherInfos) {

            SysUser _sysUser = sysUserMapper.selectByPrimaryKey(record.getUserId());

            teacherInfoMapper.updateByPrimaryKeySelective(record);

            cacheHelper.clearUserCache(_sysUser);
        }

        for (SysUserInfo record : records) {

            insertOrUpdateUserInfoSelective(record, null);
        }

        cacheHelper.clearCadreCache();
    }

    //根据身份证号或姓名找到对应的学工号
    public Map<String, List<String>> getCodes(Byte roleType, //0：混合 1：干部
                                              Byte colType, //0：身份证 1：姓名
                                              String searchKey, //colType=0:idcard colType=1：realname
                                              @RequestParam(required = false, defaultValue = "0")Byte type, //类别 教职工、本科生、研究生  0： 混合
                                              String birthKey) {

        List<String> codeList = new ArrayList<>();
        Map<String, List<String>> codeMap = new HashMap<>();
        if (roleType == 1) {

            CadreViewExample example = new CadreViewExample();
            CadreViewExample.Criteria criteria = example.createCriteria();
            if (colType == 0) {
                // 身份证
                criteria.andIdcardEqualTo(searchKey);
            } else {
                // 姓名
                criteria.andRealnameEqualTo(searchKey);
            }
            example.setOrderByClause("code desc");
            List<CadreView> cvs = cadreViewMapper.selectByExample(example);
            if (cvs.size() >= 1) {
                for (CadreView cv : cvs) {
                    if (null != birthKey) {
                        if (birthKey.equals(DateUtils.formatDate(cv.getBirth(), "yyyyMM"))) {
                            codeList.add(cv.getCode());
                        }
                    }else {
                        codeList.add(cv.getCode());
                    }
                }
            }
        } else {
            SysUserViewExample example = new SysUserViewExample();
            SysUserViewExample.Criteria criteria = example.createCriteria();
            if (colType == 0) {
                // 身份证
                criteria.andIdcardEqualTo(searchKey);
            } else {
                // 姓名
                criteria.andRealnameEqualTo(searchKey);
            }
            if (type != 0) {
                criteria.andTypeEqualTo(type);
            }

            // 按账号类别 教职工、研究生、本科生的排序
            example.setOrderByClause("field(type, 1,3,2) asc");

            List<SysUserView> uvs = sysUserViewMapper.selectByExample(example);

            if (uvs.size() == 1) {
                codeList.add(uvs.get(0).getCode());
            } else if (uvs.size() > 1) {

                SysUserView firstUv = uvs.get(0);
                byte _type = firstUv.getType();
                if (_type == SystemConstants.USER_TYPE_YJS) {
                    boolean flag = false;//当账号类型为博士时，flag=true，保证code不会再被硕士类型的账号赋值
                    for (SysUserView uv : uvs) {

                        String code = uv.getCode();
                        StudentInfo studentInfo = studentInfoMapper.selectByPrimaryKey(uv.getId());
                        if (!flag && studentInfo != null) {
                            String _stuType = studentInfo.getType();
                            if (_stuType.contains("硕士")) {
                               code = uv.getCode();
                            } else if (_stuType.contains("博士")) {
                                flag = true;
                                code = uv.getCode();
                            }
                        }
                        if (null != birthKey) {
                            if (birthKey.equals(DateUtils.formatDate(uv.getBirth(), "yyyyMM"))) {
                                codeList.add(0, code);
                            }
                        }else {
                            codeList.add(code);
                        }
                    }
                }else {
                    //本科生和教职工没有细分，所以直接读取
                    for (SysUserView uv : uvs) {
                        if (null != birthKey) {
                            if (birthKey.equals(DateUtils.formatDate(uv.getBirth(), "yyyyMM"))) {
                                codeList.add(0, uv.getCode());
                            }
                        } else {
                            codeList.add(uv.getCode());
                        }
                    }
                }
            }
        }

        if(codeList.size() > 0){

            codeMap.put(codeList.get(0), codeList);
        }

        return codeMap;
    }
}
