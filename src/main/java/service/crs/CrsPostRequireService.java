package service.crs;

import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.crs.CrsPostRequire;
import domain.crs.CrsPostRequireExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import sys.constants.CrsConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrsPostRequireService extends BaseMapper {

    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected CadreService cadreService;

    public Map<Byte, String> getRealValMap(int userId) {

        CadreView cv = cadreService.dbFindByUserId(userId);

        Map<Byte, String> resultMap = new HashMap<>();
        if (cv == null) return resultMap;

        Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();

        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_XL, metaTypeService.getName(cv.getEduId()));
        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_RZNL, cv.getBirth() == null ? "" : DateUtils.calAge(cv.getBirth()));

        Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(), "中共",
                cv.getDpTypeId(), cv.getDpGrowTime(), true);
        String partyName = cadreParty.get("partyName");
        String partyAddYear = null;
        String partyAddTime = cadreParty.get("growTime");
        if(StringUtils.isNotBlank(partyAddTime)){
            String[] addTimes = partyAddTime.split(",");
            if(addTimes.length==1){
                partyAddYear = StringUtils.equals(addTimes[0], "-")?"-":DateUtils.yearOffNow_cn(DateUtils.parseDate(addTimes[0], DateUtils.YYYYMM));
            }else if(addTimes.length==2){
                partyAddYear = (StringUtils.equals(addTimes[0], "-")?"-":DateUtils.yearOffNow_cn(DateUtils.parseDate(addTimes[0], DateUtils.YYYYMM)))
                        + "," +
                        (StringUtils.equals(addTimes[1], "-")?"-":DateUtils.yearOffNow_cn(DateUtils.parseDate(addTimes[1], DateUtils.YYYYMM)));
            }
        }

        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_ZZMM, combineTowString(partyName, partyAddYear));

        String proPostLevel = cv.getProPostLevel();
        String proPostLevelTime = cv.getProPostLevelTime() == null ? null : DateUtils.yearOffNow_cn(cv.getProPostLevelTime());
        String proPost = cv.getProPost();
        String proPostTime = cv.getProPostTime() == null ? null : DateUtils.yearOffNow_cn(cv.getProPostTime());
        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_ZZJS, combineTowString(proPost, proPostTime)
                +"<br/>" + combineTowString(proPostLevel, proPostLevelTime));

        String manageLevel = cv.getManageLevel();
        String manageLevelTime = cv.getManageLevelTime() == null ? null : DateUtils.yearOffNow_cn(cv.getManageLevelTime());
        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_GLGW,  combineTowString(manageLevel, manageLevelTime));

        Integer adminLevelId = cv.getAdminLevelId();
        MetaType mainPost = codeKeyMap.get("mt_admin_level_main");
        MetaType vicePost = codeKeyMap.get("mt_admin_level_vice");
        String lpWorkTime = DateUtils.formatDate(cv.getLpWorkTime(), DateUtils.YYYY_MM_DD);
        String lpWorkTimeOfYear = cv.getLpWorkTime()==null?null:DateUtils.yearOffNow_cn(cv.getLpWorkTime());

        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_ZCJ, "--");
        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_FCJ, "--");
        if(adminLevelId != null) {
            if (adminLevelId.intValue() == mainPost.getId()) {
                String name = mainPost.getName();
                resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_ZCJ,
                        combineTowString(name, lpWorkTime) + "，" +(lpWorkTimeOfYear==null?"--":lpWorkTimeOfYear));
            } else if (adminLevelId.intValue() == vicePost.getId()) {
                String name = vicePost.getName();
                resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_FCJ,
                        combineTowString(name, lpWorkTime) + "，" +(lpWorkTimeOfYear==null?"--":lpWorkTimeOfYear));
            }
        }

        String workTime = cv.getWorkTime()==null?null:DateUtils.yearOffNow_cn(cv.getWorkTime());
        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_GZ, workTime);

        String arriveTime = cv.getArriveTime()==null?null:DateUtils.yearOffNow_cn(cv.getArriveTime());
        resultMap.put(CrsConstants.CRS_POST_RULE_TYPE_BXGZ, arriveTime);

        return resultMap;
    }

    public String combineTowString(String frtStr, String sndStr) {

        frtStr = StringUtils.defaultString(frtStr, "--");
        sndStr = StringUtils.defaultString(sndStr, "--");

        return frtStr + "，" + sndStr;
    }

    @Transactional
    @CacheEvict(value = "CrsPostRequire:ALL", allEntries = true)
    public void insertSelective(CrsPostRequire record) {

        record.setSortOrder(getNextSortOrder("crs_post_require", "status=" + SystemConstants.AVAILABLE));
        crsPostRequireMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "CrsPostRequire:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsPostRequireExample example = new CrsPostRequireExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CrsPostRequire record = new CrsPostRequire();
        record.setStatus(SystemConstants.UNAVAILABLE);
        crsPostRequireMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    @CacheEvict(value = "CrsPostRequire:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CrsPostRequire record) {

        return crsPostRequireMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "CrsPostRequire:ALL")
    public Map<Integer, CrsPostRequire> findAll() {

        CrsPostRequireExample example = new CrsPostRequireExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");
        List<CrsPostRequire> crsPostRequirees = crsPostRequireMapper.selectByExample(example);
        Map<Integer, CrsPostRequire> map = new LinkedHashMap<>();
        for (CrsPostRequire crsPostRequire : crsPostRequirees) {
            map.put(crsPostRequire.getId(), crsPostRequire);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CrsPostRequire:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CrsPostRequire entity = crsPostRequireMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CrsPostRequireExample example = new CrsPostRequireExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CrsPostRequire> overEntities = crsPostRequireMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CrsPostRequire targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("crs_post_require", "status=" + SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("crs_post_require", "status=" + SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());

            CrsPostRequire record = new CrsPostRequire();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            crsPostRequireMapper.updateByPrimaryKeySelective(record);
        }
    }
}
