package service.global;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.BaseController;
import domain.abroad.ApproverType;
import domain.base.Location;
import domain.base.MetaType;
import domain.dispatch.DispatchType;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysRole;
import domain.train.TrainEvaTable;
import domain.unit.Unit;
import mixin.MetaTypeOptionMixin;
import mixin.OptionMixin;
import mixin.PartyOptionMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.common.CountMapper;
import sys.constants.SystemConstants;
import sys.utils.ConfigUtil;
import sys.utils.FileUtils;
import sys.utils.JSONUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2017/4/11.
 */
@Service
public class CacheService extends BaseController{

    @Autowired
    protected CountMapper countMapper;
    @Autowired
    protected CacheManager cacheManager;

    public final static String MENU_COUNT_CACHE_NAME = "menu_count_cache";

    // 菜单缓存数量
    public Integer getCacheCount(String countCacheKeys){

        if(StringUtils.isBlank(countCacheKeys)) return null;

        Cache<Object, Object> countCache = cacheManager.getCache(MENU_COUNT_CACHE_NAME);
        if(countCache==null || countCache.size()==0){
            refreshCacheCounts();
        }

        if(countCache.size()==0) return null;

        int count = 0;
        String[] keys = countCacheKeys.split(",");
        for (String key : keys) {
            if(!NumberUtils.isNumber(key)) continue;

            Integer _count = (Integer)countCache.get(Byte.valueOf(key));
            if(_count!=null && _count>0)
                count += _count;
        }
        return count;
    }

    // 刷新各类统计数量缓存
    public void refreshCacheCounts(){

        Cache<Object, Object> countCache = cacheManager.getCache(MENU_COUNT_CACHE_NAME);

        countCache.put(SystemConstants.CACHEKEY_MODIFY_BASE_APPLY, countMapper.modifyBaseApply());
        countCache.put(SystemConstants.CACHEKEY_ABROAD_PASSPORT_APPLY, countMapper.abroadPassportApply());
        countCache.put(SystemConstants.CACHEKEY_ABROAD_APPLY_SELF, countMapper.abroadApplySelf());

        List<Map> modifyTableApplyCounts = countMapper.modifyTableApply();
        for (Map entity : modifyTableApplyCounts) {
            byte module = ((Integer) entity.get("module")).byteValue();
            int num = ((Long) entity.get("num")).intValue();
            Byte cacheKey = null;
            switch (module){
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_EDU; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_WORK; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT; break;
                case SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN:
                    cacheKey = SystemConstants.CACHEKEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN; break;
            }
            countCache.put(cacheKey, num);
        }

        List<Map> abroadPassportDrawCounts = countMapper.abroadPassportDraw();
        for (Map entity : abroadPassportDrawCounts) {
            byte type = ((Integer) entity.get("type")).byteValue();
            int num = ((Long) entity.get("num")).intValue();
            Byte cacheKey = null;
            switch (type){
                case SystemConstants.PASSPORT_DRAW_TYPE_SELF:
                    cacheKey = SystemConstants.CACHEKEY_PASSPORT_DRAW_TYPE_SELF; break;
                case SystemConstants.PASSPORT_DRAW_TYPE_TW:
                    cacheKey = SystemConstants.CACHEKEY_PASSPORT_DRAW_TYPE_TW; break;
                case SystemConstants.PASSPORT_DRAW_TYPE_OTHER:
                    cacheKey = SystemConstants.CACHEKEY_PASSPORT_DRAW_TYPE_OTHER; break;
                case SystemConstants.PASSPORT_DRAW_TYPE_LONG_SELF:
                    cacheKey = SystemConstants.CACHEKEY_PASSPORT_DRAW_TYPE_LONG_SELF; break;
            }
            countCache.put(cacheKey, num);
        }
    }


    private static String getJsFolder(){

        return ConfigUtil.defaultHomePath() + File.separator + "js";
    }

    // 刷新地区js数据
    public void flushLocation(){

        String content = "function Location() {this.items=" + locationService.toJSON() + ";}";
        FileUtils.writerText(getJsFolder(), content, "location.js", false);
    }

    // 刷新基础js数据
    public void flushMetadata() {

        /*Map map = new HashMap();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        for (MetaType metaType : metaTypeMap.values()) {
            map.put(metaType.getId(), metaType.getName());
        }
        modelMap.put("metaTypeMap", JSONUtils.toString(map));*/

        Map cMap = new HashMap();

        Map constantMap = new HashMap();
        Field[] fields = SystemConstants.class.getFields();
        for (Field field : fields) {
            if (StringUtils.equals(field.getType().getName(), "java.util.Map")) {
                try {
                    constantMap.put(field.getName(), field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        cMap.putAll(constantMap);

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        cMap.put("metaTypeMap", metaTypeMap);

        Map metaMap = getMetaMap();
        cMap.putAll(metaMap);


        ObjectMapper mapper = JSONUtils.buildObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);

        Map<Class<?>, Class<?>> sourceMixins = new HashMap<>();
        sourceMixins.put(MetaType.class, MetaTypeOptionMixin.class);
        sourceMixins.put(Party.class, PartyOptionMixin.class);
        sourceMixins.put(Branch.class, PartyOptionMixin.class);
        //sourceMixins.put(Dispatch.class, OptionMixin.class);
        //sourceMixins.put(DispatchUnit.class, OptionMixin.class);
        sourceMixins.put(Unit.class, OptionMixin.class);
        //sourceMixins.put(Cadre.class, OptionMixin.class);
        sourceMixins.put(DispatchType.class, OptionMixin.class);
        //sourceMixins.put(SafeBox.class, OptionMixin.class);
        sourceMixins.put(ApproverType.class, OptionMixin.class);
        sourceMixins.put(Location.class, OptionMixin.class);
        //sourceMixins.put(Country.class, OptionMixin.class);

        sourceMixins.put(TrainEvaTable.class, OptionMixin.class);

        sourceMixins.put(SysRole.class, OptionMixin.class);

        mapper.setMixIns(sourceMixins);

        // 删除目前不需要的
        cMap.remove("dispatchMap");
        cMap.remove("cadreMap");
        cMap.remove("countryMap");
        cMap.remove("dispatchCadreMap");
        cMap.remove("safeBoxMap");

        try {
            FileUtils.writerText(getJsFolder(), "var _cMap=" + mapper.writeValueAsString(cMap), "metadata.js", false);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
