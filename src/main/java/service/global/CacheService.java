package service.global;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.abroad.ApproverType;
import domain.base.LayerType;
import domain.base.Location;
import domain.base.MetaType;
import domain.cadre.CadreViewExample;
import domain.cet.CetTrainEvaTable;
import domain.dispatch.DispatchType;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysRole;
import domain.sys.SysUserView;
import domain.unit.Unit;
import mixin.MetaTypeOptionMixin;
import mixin.OptionMixin;
import mixin.PartyOptionMixin;
import mixin.UnitOptionMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import persistence.common.CountMapper;
import service.BaseMapper;
import service.SpringProps;
import service.base.LayerTypeService;
import service.base.LocationService;
import service.base.MetaTypeService;
import service.cadre.CadreAdminLevelService;
import service.cadre.CadrePostService;
import service.dispatch.DispatchTypeService;
import service.party.BranchService;
import service.party.PartyService;
import service.sys.SysPropertyService;
import service.sys.SysRoleService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import service.unit.UnitService;
import sys.HttpResponseMethod;
import sys.constants.AbroadConstants;
import sys.constants.CacheConstants;
import sys.constants.MemberConstants;
import sys.constants.ModifyConstants;
import sys.tags.CmTag;
import sys.utils.ClassUtils;
import sys.utils.ConfigUtil;
import sys.utils.FileUtils;
import sys.utils.JSONUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fafa on 2017/4/11.
 */
@Service(value = "cacheService")
public class CacheService extends BaseMapper implements HttpResponseMethod {

    @Autowired
    protected CountMapper countMapper;
    @Autowired
    protected LocationService locationService;
    @Autowired
    protected CacheManager cacheManager;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private LayerTypeService layerTypeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPropertyService sysPropertyService;
    @Autowired(required = false)
    private UnitService unitService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected CadrePostService cadrePostService;
    @Autowired
    protected CadreAdminLevelService cadreAdminLevelService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected SpringProps springProps;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public final static String MENU_COUNT_CACHE_NAME = "menu_count_cache";

    public final static String LIMIT_CACHE_KEY_NAME = "limit_cache";

    private Cache<String, AtomicInteger> limitCache;

    // 缓存数量限制
    public void limitCache(String cacheKey, int maxCount) {

        limitCache = cacheManager.getCache(LIMIT_CACHE_KEY_NAME);

        AtomicInteger retryCount = limitCache.get(cacheKey);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            limitCache.put(cacheKey, retryCount);
        }
        if (retryCount.incrementAndGet() > maxCount) {
            throw new ExcessiveAttemptsException();
        }
    }
    public void clearLimitCache(String cacheKey) {

        limitCache = cacheManager.getCache(LIMIT_CACHE_KEY_NAME);
        limitCache.remove(cacheKey);
    }

    // 异步Pdf转图片
    @Async
    public void asyncPdf2jpg(String pdfFilePath, Integer pageNo) {

        toPdfImage(pdfFilePath, pageNo);
    }

    // 判断某个角色是否拥有某个权限
    public boolean roleIsPermitted(String role, String permission) {

        SysRole sysRole = sysRoleService.getByRole(role);
        if (sysRole == null) return false;

        Set<String> rolePermissions = new HashSet<>();
        rolePermissions.addAll(sysRoleService.getRolePermissions(sysRole.getId(), false));
        rolePermissions.addAll(sysRoleService.getRolePermissions(sysRole.getId(), true));

        return rolePermissions.contains(permission);
    }

    // 判断某个用户是否拥有某个权限
    public boolean userIsPermitted(Integer userId, String permission) {

        SysUserView uv = sysUserService.findById(userId);

        Set<String> userPermissions = new HashSet<>();
        userPermissions.addAll(sysUserService.findPermissions(uv.getUsername(), false));
        userPermissions.addAll(sysUserService.findPermissions(uv.getUsername(), true));

        return userPermissions.contains(permission);
    }

    // 判断某个用户是否拥有某个角色
    public boolean userHasRole(Integer userId, String role) {

        SysUserView uv = sysUserService.findById(userId);
        Set<String> roles = sysUserService.findRoles(uv.getUsername());

        return roles.contains(role);
    }

    // 菜单缓存数量
    public Integer getCacheCount(String countCacheKeys) {

        if (StringUtils.isBlank(countCacheKeys)) return null;

        Cache<Object, Object> countCache = cacheManager.getCache(MENU_COUNT_CACHE_NAME);
        if (countCache == null || countCache.size() == 0) {
            refreshCacheCounts();
        }

        if (countCache.size() == 0) return null;

        int count = 0;
        String[] keys = countCacheKeys.split(",");
        for (String key : keys) {
            if (!NumberUtils.isCreatable(key)) continue;

            Integer _count = (Integer) countCache.get(Byte.valueOf(key));
            if (_count != null && _count > 0)
                count += _count;
        }
        return count;
    }

    // 刷新各类统计数量缓存
    public void refreshCacheCounts() {

        Cache<Object, Object> countCache = cacheManager.getCache(MENU_COUNT_CACHE_NAME);

        countCache.clear();

        countCache.put(CacheConstants.CACHE_KEY_MODIFY_BASE_APPLY, countMapper.modifyBaseApply());
        countCache.put(CacheConstants.CACHE_KEY_ABROAD_PASSPORT_APPLY, countMapper.abroadPassportApply());
        countCache.put(CacheConstants.CACHE_KEY_ABROAD_APPLY_SELF, countMapper.abroadApplySelf());
        countCache.put(CacheConstants.CACHE_KEY_TAIWAN_RECORD_HANDLE_TYPE, countMapper.taiwanRecordHandleType());

        countCache.put(CacheConstants.CACHE_KEY_CLA_APPLY, countMapper.claApply());

        {
            // 特殊党员干部库-已存在于党员信息库（用于提醒管理员删除）
            CadreViewExample example = new CadreViewExample();
            CadreViewExample.Criteria criteria = example.createCriteria();
            criteria.andOwIdIsNotNull().andMemberStatusIn(Arrays.asList(MemberConstants.MEMBER_STATUS_NORMAL,
                    MemberConstants.MEMBER_STATUS_TRANSFER));

            countCache.put(CacheConstants.CACHE_KEY_CADRE_PARTY_TO_REMOVE, (int) cadreViewMapper.countByExample(example));
        }

        List<Map> modifyTableApplyCounts = countMapper.modifyTableApply();
        for (Map entity : modifyTableApplyCounts) {
            byte module = ((Integer) entity.get("module")).byteValue();
            int num = ((Long) entity.get("num")).intValue();
            Byte cacheKey = null;
            switch (module) {
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_EDU;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_WORK;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT;
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN;
                    break;

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO;
                    break;

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN;
                    break;

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK;
                    break;

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY;
                    break;

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD:
                    cacheKey = CacheConstants.CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD;
                    break;
            }
            countCache.put(cacheKey, num);
        }

        List<Map> abroadPassportDrawCounts = countMapper.abroadPassportDraw();
        for (Map entity : abroadPassportDrawCounts) {
            byte type = ((Integer) entity.get("type")).byteValue();
            int num = ((Long) entity.get("num")).intValue();
            Byte cacheKey = null;
            switch (type) {
                case AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF:
                    cacheKey = CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_SELF;
                    break;
                case AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW:
                    cacheKey = CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_TW;
                    break;
                case AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER:
                    cacheKey = CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER;
                    break;
                case AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF:
                    cacheKey = CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF;
                    break;
                case AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF:
                    cacheKey = CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF;
                    break;
            }
            countCache.put(cacheKey, num);
        }
    }

    // 刷新地区js数据
    public void flushLocation() throws FileNotFoundException {

        String locationJs = ConfigUtil.defaultHomePath() + File.separator
                + "extend" + File.separator + "js" + File.separator + "location.js";
        String function = FileUtils.getData(new FileInputStream(locationJs));
        String clazz = "function ChinaLocation() {this.items=" + locationService.toJSON() + ";}\r\n";

        FileUtils.writerText(CmTag.getJsFolder(), clazz + function, "location.js", false);

        logger.info("==============刷新location.js成功=================");
    }

    // 刷新基础js数据
    public Map flushMetadata() {

        Map cMap = new HashMap();
        for (Class<?> aClass : ClassUtils.getClasses("sys.constants", true)) {

            Map constantMap = new HashMap();
            Field[] fields = aClass.getFields();
            for (Field field : fields) {
                if (StringUtils.equals(field.getType().getName(), "java.util.Map")) {
                    try {
                        constantMap.put(field.getName(), field.get(null));
                    } catch (IllegalAccessException e) {
                        logger.error("异常", e);
                    }
                }
            }

            cMap.putAll(constantMap);
        }

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        cMap.put("metaTypeMap", metaTypeMap);

        Map metaMap = getMetaMap();
        cMap.putAll(metaMap);

        Map<Integer, SysRole> roleMap = sysRoleService.findAll();
        Map<String, SysRole> roleCodeMap = new HashMap<>();
        for (SysRole sysRole : roleMap.values()) {
            roleCodeMap.put(sysRole.getCode(), sysRole);
        }
        cMap.put("roleCodeMap", roleCodeMap);

        ObjectMapper mapper = JSONUtils.buildObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);

        Map<Class<?>, Class<?>> baseMixins = new HashMap<>();
        baseMixins.put(MetaType.class, MetaTypeOptionMixin.class);
        baseMixins.put(Party.class, PartyOptionMixin.class);
        baseMixins.put(Branch.class, PartyOptionMixin.class);
        baseMixins.put(Unit.class, UnitOptionMixin.class);
        baseMixins.put(DispatchType.class, OptionMixin.class);
        baseMixins.put(ApproverType.class, OptionMixin.class);
        baseMixins.put(Location.class, OptionMixin.class);
        baseMixins.put(CetTrainEvaTable.class, OptionMixin.class);
        baseMixins.put(SysRole.class, OptionMixin.class);
        baseMixins.put(LayerType.class, OptionMixin.class);

        mapper.setMixIns(baseMixins);

        // 删除目前不需要的
        cMap.remove("countryMap");

        try {
            FileUtils.writerText(CmTag.getJsFolder(), "var _cMap=" + mapper.writeValueAsString(cMap), "metadata.js", false);
            logger.info("==============刷新metadata.js成功=================");
        } catch (JsonProcessingException e) {
            logger.error("异常", e);
        }

        return cMap;
    }

    public Map getMetaMap() {

        Map map = new HashMap<>();

        map.put("partyMap", partyService.findAll());
        map.put("branchMap", branchService.findAll());

        DispatchTypeService dispatchTypeService = CmTag.getBean(DispatchTypeService.class);
        if (dispatchTypeService != null) map.put("dispatchTypeMap", dispatchTypeService.findAll());

        map.put("unitMap", unitService.findAll());

        map.put("locationMap", locationService.codeMap());

        map.put("roleMap", sysRoleService.findAll());

        map.put("_pMap", sysPropertyService.findAll());

        map.put("layerTypeMap", layerTypeService.findAll());

        return map;
    }
}
