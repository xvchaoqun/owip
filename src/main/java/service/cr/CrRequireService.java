package service.cr;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.cr.CrRequire;
import domain.cr.CrRequireExample;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import sys.constants.CrConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.*;

@Service
public class CrRequireService extends CrBaseMapper {

    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected CadreService cadreService;

    public Map<Byte, String> getRealValMap(int userId) {

        CadreView cv = cadreService.dbFindByUserId(userId);

        Map<Byte, String> resultMap = new HashMap<>();
        if (cv == null) return resultMap;

        Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();

        resultMap.put(CrConstants.CR_POST_RULE_TYPE_XL, metaTypeService.getName(cv.getEduId()));
        resultMap.put(CrConstants.CR_POST_RULE_TYPE_XLXW,
                StringUtils.defaultIfBlank(metaTypeService.getName(cv.getEduId()), "--")
                + "," + StringUtils.defaultIfBlank(cv.getDegree(), "--"));
        resultMap.put(CrConstants.CR_POST_RULE_TYPE_RZNL, cv.getBirth() == null ? "" : DateUtils.calAge(cv.getBirth()));

        Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(), "中共",
                cv.getDpTypeId(), cv.getDpGrowTime(), true);
        String partyName = cadreParty.get("partyName");
        String partyAddYear = null;
        String partyAddTime = cadreParty.get("growTime");
        if (StringUtils.isNotBlank(partyAddTime)) {
            String[] addTimes = partyAddTime.split(",");
            if (addTimes.length == 1) {
                partyAddYear = StringUtils.equals(addTimes[0], "-") ? "-" : DateUtils.yearOffNow_cn(DateUtils.parseDate(addTimes[0], DateUtils.YYYYMM));
            } else if (addTimes.length == 2) {
                partyAddYear = (StringUtils.equals(addTimes[0], "-") ? "-" : DateUtils.yearOffNow_cn(DateUtils.parseDate(addTimes[0], DateUtils.YYYYMM)))
                        + "," +
                        (StringUtils.equals(addTimes[1], "-") ? "-" : DateUtils.yearOffNow_cn(DateUtils.parseDate(addTimes[1], DateUtils.YYYYMM)));
            }
        }

        resultMap.put(CrConstants.CR_POST_RULE_TYPE_ZZMM, combineTowString(partyName, partyAddYear));

        String proPostLevel = cv.getProPostLevel();
        String proPostLevelTime = cv.getProPostLevelTime() == null ? null : DateUtils.yearOffNow_cn(cv.getProPostLevelTime());
        String proPost = cv.getProPost();
        String proPostTime = cv.getProPostTime() == null ? null : DateUtils.yearOffNow_cn(cv.getProPostTime());
        resultMap.put(CrConstants.CR_POST_RULE_TYPE_ZZJS, combineTowString(proPost, proPostTime)
                + "<br/>" + combineTowString(proPostLevel, proPostLevelTime));

        String manageLevel = cv.getManageLevel();
        String manageLevelTime = cv.getManageLevelTime() == null ? null : DateUtils.yearOffNow_cn(cv.getManageLevelTime());
        resultMap.put(CrConstants.CR_POST_RULE_TYPE_GLGW, combineTowString(manageLevel, manageLevelTime));

        Integer adminLevel = cv.getAdminLevel();
        MetaType mainPost = codeKeyMap.get("mt_admin_level_main");
        MetaType vicePost = codeKeyMap.get("mt_admin_level_vice");
        String sWorkTime = DateUtils.formatDate(cv.getsWorkTime(), DateUtils.YYYY_MM_DD);
        String sWorkTimeOfYear = cv.getLpWorkTime() == null ? null : DateUtils.yearOffNow_cn(cv.getsWorkTime());

        resultMap.put(CrConstants.CR_POST_RULE_TYPE_ZCJ, "--");
        resultMap.put(CrConstants.CR_POST_RULE_TYPE_FCJ, "--");
        if (adminLevel != null) {
            if (adminLevel.intValue() == mainPost.getId()) {
                String name = mainPost.getName();
                resultMap.put(CrConstants.CR_POST_RULE_TYPE_ZCJ,
                        combineTowString(name, sWorkTime) + "，" + (sWorkTimeOfYear == null ? "--" : sWorkTimeOfYear));
            } else if (adminLevel.intValue() == vicePost.getId()) {
                String name = vicePost.getName();
                resultMap.put(CrConstants.CR_POST_RULE_TYPE_FCJ,
                        combineTowString(name, sWorkTime) + "，" + (sWorkTimeOfYear == null ? "--" : sWorkTimeOfYear));
            }
        }

        String workTime = cv.getWorkTime() == null ? null : DateUtils.yearOffNow_cn(cv.getWorkTime());
        resultMap.put(CrConstants.CR_POST_RULE_TYPE_GZ, workTime);

        String arriveTime = cv.getArriveTime() == null ? null : DateUtils.yearOffNow_cn(cv.getArriveTime());
        resultMap.put(CrConstants.CR_POST_RULE_TYPE_BXGZ, arriveTime);

        TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(userId);
        if(teacherInfo!=null) {
            resultMap.put(CrConstants.CR_POST_RULE_TYPE_BZLB, teacherInfo.getAuthorizedType());
            resultMap.put(CrConstants.CR_POST_RULE_TYPE_GRSF, teacherInfo.getStaffType());
        }

        return resultMap;
    }

    public String combineTowString(String frtStr, String sndStr) {

        frtStr = StringUtils.defaultString(frtStr, "--");
        sndStr = StringUtils.defaultString(sndStr, "--");

        return frtStr + "，" + sndStr;
    }

    public CrRequire get(String name) {

        if(StringUtils.isBlank(name)) return null;

        CrRequireExample example = new CrRequireExample();
        example.createCriteria().andNameEqualTo(name.trim()).andStatusEqualTo(SystemConstants.AVAILABLE);
        List<CrRequire> crRequires = crRequireMapper.selectByExampleWithRowbounds(example,
                new RowBounds(0, 1));
        return crRequires.size() == 1 ? crRequires.get(0) : null;
    }

    @Transactional
    @CacheEvict(value = "CrRequire:ALL", allEntries = true)
    public void insertSelective(CrRequire record) {

        if(get(record.getName())!=null){
            throw new OpException("资格要求模板名称重复。");
        }

        record.setSortOrder(getNextSortOrder("cr_require", "status=" + SystemConstants.AVAILABLE));
        crRequireMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "CrRequire:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrRequireExample example = new CrRequireExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CrRequire record = new CrRequire();
        record.setStatus(SystemConstants.UNAVAILABLE);
        crRequireMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    @CacheEvict(value = "CrRequire:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CrRequire record) {

        if(record.getName()!=null) {
            CrRequire crRequire = get(record.getName());
            if (crRequire!=null && crRequire.getId().intValue()!=record.getId()){
                throw new OpException("资格要求模板名称重复。");
            }
        }
        return crRequireMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "CrRequire:ALL")
    public Map<Integer, CrRequire> findAll() {

        CrRequireExample example = new CrRequireExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");
        List<CrRequire> crRequirees = crRequireMapper.selectByExample(example);
        Map<Integer, CrRequire> map = new LinkedHashMap<>();
        for (CrRequire crRequire : crRequirees) {
            map.put(crRequire.getId(), crRequire);
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
    @CacheEvict(value = "CrRequire:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CrRequire entity = crRequireMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CrRequireExample example = new CrRequireExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CrRequire> overEntities = crRequireMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CrRequire targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("cr_require", "status=" + SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cr_require", "status=" + SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());

            CrRequire record = new CrRequire();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            crRequireMapper.updateByPrimaryKeySelective(record);
        }
    }
}
