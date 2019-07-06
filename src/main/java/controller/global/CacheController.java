package controller.global;

import controller.BaseController;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.global.CacheHelper;

import java.util.Map;

/**
 * Created by fafa on 2017/4/11.
 */
@Controller
@RequestMapping("/cache")
public class CacheController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CacheHelper cacheHelper;

    // 清除ehcache所有的缓存
    @RequiresPermissions("sysConfig:cache")
    @RequestMapping("/clear")
    @ResponseBody
    public Map clearCache(Boolean clearBase, String name, String key) {

        if(BooleanUtils.isTrue(clearBase)){

            cacheHelper.clearSysBaseCache();
        }else {

            if (StringUtils.isNotBlank(name)) {
                CacheManager manager = CacheManager.getInstance();
                Cache cache = manager.getCache(name);
                if (cache == null) {
                    return failed("缓存{0}不存在", name);
                }
                if (StringUtils.isNotBlank(key)) {
                    cache.remove(key);
                } else {
                    cache.removeAll();
                }
                logger.info("==============清理缓存name={}, key={}成功=================", name, key);

            } else {
                CacheManager.create().clearAll();
                logger.info("==============清空缓存成功=================");
            }
        }

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

    // 刷新元数据缓存 所有登录的账号均有权限操作
    //@RequiresPermissions("sysConfig:cache")
    @RequestMapping("/flush_metadata_JSON")
    @ResponseBody
    public Map flush_metadata_JSON() {

        Map metadata = cacheService.flushMetadata();
        Map<String, Object> resultMap = success();
        resultMap.put("metadata", metadata);

        return resultMap;
    }

    // 刷新数量缓存
    @RequiresPermissions("sysConfig:cache")
    @RequestMapping("/flush_count")
    @ResponseBody
    public Map flush_count() {

        cacheService.refreshCacheCounts();

        return success();
    }
}
