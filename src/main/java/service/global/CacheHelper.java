package service.global;

import domain.member.MemberReg;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * Created by fafa on 2017/4/11.
 */
@Service
public class CacheHelper {

    // 清除指定缓存
    public void clearCache(String name, String key) {

        if (StringUtils.isNotBlank(name)) {
            CacheManager manager = CacheManager.getInstance();
            Cache cache = manager.getCache(name);
            if (StringUtils.isNotBlank(key)) {
                cache.remove(key);
            } else {
                cache.removeAll();
            }
        } else {
            CacheManager.create().clearAll();
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#u.username"),
            @CacheEvict(value = "SysUserView", key = "#u.username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#u.code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#u.id"),
            @CacheEvict(value = "UserPermissions", key = "#u.username+':0'"),
            @CacheEvict(value = "UserPermissions", key = "#u.username+':1'")
    })
    public void clearUserCache(SysUser u) {
    }

    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#u.username"),
            @CacheEvict(value = "SysUserView", key = "#u.username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#u.code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#u.id"),
            @CacheEvict(value = "UserPermissions", key = "#u.username+':0'"),
            @CacheEvict(value = "UserPermissions", key = "#u.username+':1'")
    })
    public void clearUserCache(SysUserView u) {
    }

    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#u.username"),
            @CacheEvict(value = "SysUserView", key = "#u.username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#u.code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#u.id"),
            @CacheEvict(value = "UserPermissions", key = "#u.username+':0'"),
            @CacheEvict(value = "UserPermissions", key = "#u.username+':1'")
    })
    public void clearUserCache(MemberReg u) {
    }

    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "SysResources", allEntries = true),
            @CacheEvict(value = "UserRoles", allEntries = true),
            @CacheEvict(value = "SysRoles", allEntries = true)
    })
    public void clearRoleCache() {
    }

    // 清除系统基础数据缓存（用于后台数据库手动更新后）
    @Caching(evict = {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "SysResources", allEntries = true),
            @CacheEvict(value = "UserRoles", allEntries = true),
            @CacheEvict(value = "SysRoles", allEntries = true),
            @CacheEvict(value = "LayerTypes:ALL", allEntries = true),
            @CacheEvict(value = "MetaType:ALL", allEntries = true),
            @CacheEvict(value = "MetaType:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true),
            @CacheEvict(value = "MetaClass:ALL", allEntries = true),
            @CacheEvict(value = "MetaClass:Code:ALL", allEntries = true),
            @CacheEvict(value = "AnnualTypes", allEntries = true),
            @CacheEvict(value = "schoolUnits", allEntries = true),
    })
    public void clearSysBaseCache() {
    }

    @CacheEvict(value = "SysMsgCount", key = "#userId")
    public void clearSysMsgCount(int userId) {}

    @CacheEvict(value = "OaTaskUserCount", key = "#userId")
    public void clearOaTaskUserCount(int userId){}

    @CacheEvict(value = "Cadre", key="#cadreId", condition = "#cadreId != null")
    public void clearCadreCache(int cadreId){}

    @CacheEvict(value = "Cadre", allEntries = true)
    public void clearAllCadreCache(){}
}
