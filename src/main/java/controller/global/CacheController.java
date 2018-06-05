package controller.global;

import controller.BaseController;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by fafa on 2017/4/11.
 */
@Controller
@RequestMapping("/cache")
public class CacheController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 清除ehcache所有的缓存
    @RequiresPermissions("sysConfig:cache")
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

        logger.info("==============清空缓存成功=================");

        return success();
    }

    // 刷新location缓存
    @RequiresPermissions("sysConfig:cache")
    @RequestMapping("/flush_location_JSON")
    @ResponseBody
    public Map flush_location_JSON() {

        cacheService.flushLocation();
        return success();
    }

    // 刷新元数据缓存
    @RequiresPermissions("sysConfig:cache")
    @RequestMapping("/flush_metadata_JSON")
    @ResponseBody
    public Map flush_metadata_JSON() {

        Map metadata = cacheService.flushMetadata();
        Map<String, Object> resultMap = success();
        resultMap.put("metadata", metadata);

        return resultMap;
    }
}
