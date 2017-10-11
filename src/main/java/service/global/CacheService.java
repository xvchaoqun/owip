package service.global;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.BaseController;
import domain.abroad.ApproverType;
import domain.base.Location;
import domain.base.MetaType;
import domain.cadre.CadreViewExample;
import domain.dispatch.DispatchType;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysRole;
import domain.sys.SysUser;
import domain.sys.SysUserReg;
import domain.sys.SysUserView;
import domain.train.TrainEvaTable;
import domain.unit.Unit;
import mixin.MetaTypeOptionMixin;
import mixin.OptionMixin;
import mixin.PartyOptionMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import persistence.common.CountMapper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.FileUtils;
import sys.utils.JSONUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

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

        countCache.clear();

        countCache.put(SystemConstants.CACHEKEY_MODIFY_BASE_APPLY, countMapper.modifyBaseApply());
        countCache.put(SystemConstants.CACHEKEY_ABROAD_PASSPORT_APPLY, countMapper.abroadPassportApply());
        countCache.put(SystemConstants.CACHEKEY_ABROAD_APPLY_SELF, countMapper.abroadApplySelf());
        countCache.put(SystemConstants.CACHEKEY_TAIWAN_RECORD_HANDLE_TYPE, countMapper.taiwanRecordHandleType());

        {
            // 特殊党员干部库-已存在于党员信息库（用于提醒管理员删除）
            CadreViewExample example = new CadreViewExample();
            CadreViewExample.Criteria criteria = example.createCriteria();
            criteria.andOwIdIsNotNull().andMemberStatusIn(Arrays.asList(SystemConstants.MEMBER_STATUS_NORMAL,
                    SystemConstants.MEMBER_STATUS_TRANSFER));

            countCache.put(SystemConstants.CACHEKEY_CADRE_PARTY_TO_REMOVE, (int)cadreViewMapper.countByExample(example));
        }

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



    // 刷新地区js数据
    public void flushLocation(){

        String content = "function Location() {this.items=" + locationService.toJSON() + ";}";
        FileUtils.writerText(CmTag.getJsFolder(), content, "location.js", false);

        logger.info("==============刷新location.js成功=================");
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

        Map<Class<?>, Class<?>> baseMixins = new HashMap<>();
        baseMixins.put(MetaType.class, MetaTypeOptionMixin.class);
        baseMixins.put(Party.class, PartyOptionMixin.class);
        baseMixins.put(Branch.class, PartyOptionMixin.class);
        //baseMixins.put(Dispatch.class, OptionMixin.class);
        //baseMixins.put(DispatchUnit.class, OptionMixin.class);
        baseMixins.put(Unit.class, OptionMixin.class);
        //baseMixins.put(Cadre.class, OptionMixin.class);
        baseMixins.put(DispatchType.class, OptionMixin.class);
        //baseMixins.put(SafeBox.class, OptionMixin.class);
        baseMixins.put(ApproverType.class, OptionMixin.class);
        baseMixins.put(Location.class, OptionMixin.class);
        //baseMixins.put(Country.class, OptionMixin.class);

        baseMixins.put(TrainEvaTable.class, OptionMixin.class);

        baseMixins.put(SysRole.class, OptionMixin.class);

        mapper.setMixIns(baseMixins);

        // 删除目前不需要的
        cMap.remove("dispatchMap");
        cMap.remove("countryMap");
        cMap.remove("dispatchCadreMap");
        cMap.remove("safeBoxMap");

        try {
            FileUtils.writerText(CmTag.getJsFolder(), "var _cMap=" + mapper.writeValueAsString(cMap), "metadata.js", false);
            logger.info("==============刷新metadata.js成功=================");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#u.username"),
            @CacheEvict(value = "Menus", key = "#u.username"),
            @CacheEvict(value = "SysUserView", key = "#u.username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#u.code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#u.id"),
            @CacheEvict(value = "UserPermissions", key = "#u.username")
    })
    public void clearUserCache(SysUser u) {}

    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#u.username"),
            @CacheEvict(value = "Menus", key = "#u.username"),
            @CacheEvict(value = "SysUserView", key = "#u.username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#u.code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#u.id"),
            @CacheEvict(value = "UserPermissions", key = "#u.username")
    })
    public void clearUserCache(SysUserView u) {}

    @Caching(evict = {
            @CacheEvict(value = "UserRoles", key = "#u.username"),
            @CacheEvict(value = "Menus", key = "#u.username"),
            @CacheEvict(value = "SysUserView", key = "#u.username"),
            @CacheEvict(value = "SysUserView:CODE_", key = "#u.code"),
            @CacheEvict(value = "SysUserView:ID_", key = "#u.id"),
            @CacheEvict(value = "UserPermissions", key = "#u.username")
    })
    public void clearUserCache(SysUserReg u) {}
}
