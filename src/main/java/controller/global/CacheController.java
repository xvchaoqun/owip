package controller.global;

import controller.BaseController;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;

import java.util.Map;

/**
 * Created by fafa on 2017/4/11.
 */
@Controller
@RequestMapping("/cache")
public class CacheController extends BaseController{

    // 清除ehcache所有的缓存
    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/clear")
    @ResponseBody
    public Map clearCache() {
        /*CacheManager manager = CacheManager.getInstance();
        String[] names = manager.getCacheNames();
        for (String name : names)
        {
            Cache cache = manager.getCache(name);
            cache.removeAll();
        }*/
        CacheManager.create().clearAll();
        /*CacheManager cacheManager = CacheManager.create();
        Ehcache cache = cacheManager.getEhcache(cacheConfiguration.getName());
        cache.removeAll();*/

        return success();
    }

    // 刷新location缓存
    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/flush_location_JSON")
    @ResponseBody
    public Map flush_location_JSON() {

        cacheService.flushLocation();
        return success();
    }

    // 刷新元数据缓存
    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/flush_metadata_JSON")
    @ResponseBody
    public Map flush_metadata_JSON(){

        cacheService.flushMetadata();
        return success();
    }
}
