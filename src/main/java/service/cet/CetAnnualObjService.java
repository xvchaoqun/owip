package service.cet;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cet.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.cet.common.TrainRecord;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CetAnnualObjService extends CetBaseMapper {
    
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    
    @Transactional
    public void insertSelective(CetAnnualObj record) {
        
        cetAnnualObjMapper.insertSelective(record);
    }
    
    @Transactional
    public void batchDel(Integer[] ids) {
        
        if (ids == null || ids.length == 0) return;
        
        CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(ids[0]);
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetAnnualObjMapper.deleteByExample(example);
        
        updateObjCount(cetAnnualObj.getAnnualId());
    }
    
    @Transactional
    public int updateByPrimaryKeySelective(CetAnnualObj record) {
        return cetAnnualObjMapper.updateByPrimaryKeySelective(record);
    }
    
    public Set<Integer> getSelectedAnnualObjUserIdSet(int annualId) {
        
        Set<Integer> userIdSet = new HashSet<>();
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        example.createCriteria().andAnnualIdEqualTo(annualId);
        List<CetAnnualObj> cetAnnualObjs = cetAnnualObjMapper.selectByExample(example);
        
        for (CetAnnualObj cetAnnualObj : cetAnnualObjs) {
            userIdSet.add(cetAnnualObj.getUserId());
        }
        
        return userIdSet;
    }
    
    // 培训明细记录
    public List<TrainRecord> getTrainRecords(int userId, int year, boolean isValid) {
        
        
        List<TrainRecord> trainRecords = new ArrayList<>();
        
        List<TrainRecord> specialRecords = iCetMapper.getProjectRecords(userId, year, (byte) 1, isValid);
        if (specialRecords != null) trainRecords.addAll(specialRecords);
        List<TrainRecord> dailyRecords = iCetMapper.getProjectRecords(userId, year, (byte) 2, isValid);
        if (dailyRecords != null) trainRecords.addAll(dailyRecords);
        List<TrainRecord> unitRecords = iCetMapper.getUnitRecords(userId, year, isValid);
        if (unitRecords != null) trainRecords.addAll(unitRecords);
        List<TrainRecord> upperRecords = iCetMapper.getUpperRecords(userId, year, isValid);
        if (upperRecords != null) trainRecords.addAll(upperRecords);
        
        return trainRecords;
    }
    
    @Transactional
    public void addOrUpdate(int annualId, Integer[] userIds) {
        if (userIds == null || userIds.length == 0) return;
        
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        int year = cetAnnual.getYear();
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(cetAnnual.getTraineeTypeId());
        String code = cetTraineeType.getCode();
        
        // 已选人员
        Set<Integer> selectedAnnualObjUserIdSet = getSelectedAnnualObjUserIdSet(annualId);
        // 提交更新人员
        Set<Integer> toAddUserIdSet = new HashSet<>();
        toAddUserIdSet.addAll(Arrays.asList(userIds));
        
        for (Integer userId : userIds) {
            
            if (selectedAnnualObjUserIdSet.contains(userId)) continue;
            
            CetAnnualObj record = new CetAnnualObj();
            record.setYear(year);
            record.setAnnualId(annualId);
            record.setUserId(userId);
            switch (code) {
                // 中层干部
                case "t_cadre": {
                    CadreView cv = CmTag.getCadreByUserId(userId);
                    record.setTitle(cv.getTitle());
                    record.setAdminLevel(cv.getAdminLevel());
                    record.setPostType(cv.getPostType());
                    record.setLpWorkTime(cv.getLpWorkTime());
                    record.setSortOrder(cv.getSortOrder());
                }
                break;
            }
            cetAnnualObjMapper.insertSelective(record);
            
            sysUserService.addRole(userId, RoleConstants.ROLE_CET_TRAINEE);
            
            sysApprovalLogService.add(record.getId(), record.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_ANNUAL,
                    "添加培训对象", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "新建");
        }
        
        // 需要删除的人员
        selectedAnnualObjUserIdSet.removeAll(toAddUserIdSet);
        for (Integer userId : selectedAnnualObjUserIdSet) {
            CetAnnualObjExample example = new CetAnnualObjExample();
            example.createCriteria().andAnnualIdEqualTo(annualId).andUserIdEqualTo(userId);
            cetAnnualObjMapper.deleteByExample(example);
        }
        
        updateObjCount(annualId);
    }
    
    private void updateObjCount(int annualId) {
        //更新培训人数
        CetAnnualObjExample exmaple = new CetAnnualObjExample();
        exmaple.createCriteria().andAnnualIdEqualTo(annualId);
        int count = (int) cetAnnualObjMapper.countByExample(exmaple);
        CetAnnual record = new CetAnnual();
        record.setId(annualId);
        record.setObjCount(count);
        cetAnnualMapper.updateByPrimaryKeySelective(record);
    }
    
    // 党校专题培训完成学时数、 党校日常培训完成学时数、 二级党校/党委培训完成学时数、 二级单位培训完成学时数、 上级调训完成学时数
    public Map<String, BigDecimal> getFinishPeriodMap(int userId, int year) {
        
        BigDecimal specialFinishPeriod = iCetMapper.getProjectFinishPeriod(userId, year, (byte) 1);
        BigDecimal dailyFinishPeriod = iCetMapper.getProjectFinishPeriod(userId, year, (byte) 2);
        BigDecimal upperFinishPeriod = iCetMapper.getUpperFinishPeriod(userId, year);
        BigDecimal unitFinishPeriod = iCetMapper.getUnitFinishPeriod(userId, year);
        
        Map<String, BigDecimal> resultMap = new HashMap<>();
        resultMap.put("specialPeriod", specialFinishPeriod);
        resultMap.put("dailyPeriod", dailyFinishPeriod);
        resultMap.put("partyPeriod", BigDecimal.ZERO); // 待定
        resultMap.put("upperPeriod", upperFinishPeriod);
        resultMap.put("unitPeriod", unitFinishPeriod);
        
        return resultMap;
    }
    
    /*// 正式归档培训对象
    public void archiveOne(CetAnnualObj cetAnnualObj) {
        
        int userId = cetAnnualObj.getUserId();
        int year = cetAnnualObj.getYear();
        Map<String, BigDecimal> finishPeriodMap = getFinishPeriodMap(userId, year);
        
        CetAnnualObj record = new CetAnnualObj();
        record.setId(cetAnnualObj.getId());
        record.setSpecialPeriod(finishPeriodMap.get("specialPeriod"));
        record.setDailyPeriod(finishPeriodMap.get("dailyPeriod"));
        record.setPartyPeriod(finishPeriodMap.get("partyPeriod"));
        record.setUpperPeriod(finishPeriodMap.get("upperPeriod"));
        record.setUnitPeriod(finishPeriodMap.get("unitPeriod"));
        record.setHasArchived(true);
        
        cetAnnualObjMapper.updateByPrimaryKeySelective(record);
    }
    
    // 正式归档某一类培训对象
    @Transactional
    public void archive(int annualId) {
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        example.createCriteria().andAnnualIdEqualTo(annualId).andIsQuitEqualTo(false);
        
        List<CetAnnualObj> cetAnnualObjs = cetAnnualObjMapper.selectByExample(example);
        for (CetAnnualObj cetAnnualObj : cetAnnualObjs) {
            archiveOne(cetAnnualObj);
        }
    }
    
    // 取消归档某一类培训对象
    @Transactional
    public void unarchive(int annualId) {
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        example.createCriteria().andAnnualIdEqualTo(annualId).andIsQuitEqualTo(false);
        
        CetAnnualObj record = new CetAnnualObj();
        record.setHasArchived(false);
        cetAnnualObjMapper.updateByExampleSelective(record, example);
    }*/
    
    // 退出
    @Transactional
    public void quit(boolean isQuit, Integer[] ids) {
        
        CetAnnualObj record = new CetAnnualObj();
        record.setIsQuit(isQuit);
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsQuitEqualTo(!isQuit);
        cetAnnualObjMapper.updateByExampleSelective(record, example);
        
        for (Integer id : ids) {
            CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(id);
            sysApprovalLogService.add(cetAnnualObj.getId(), cetAnnualObj.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_ANNUAL,
                    isQuit ? "退出" : "返回", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    null);
        }
    }
    
    /*public Map<Integer, CetAnnualObj> findAll() {

        CetAnnualObjExample example = new CetAnnualObjExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<CetAnnualObj> cetAnnualObjes = cetAnnualObjMapper.selectByExample(example);
        Map<Integer, CetAnnualObj> map = new LinkedHashMap<>();
        for (CetAnnualObj cetAnnualObj : cetAnnualObjes) {
            map.put(cetAnnualObj.getId(), cetAnnualObj);
        }

        return map;
    }*/
    
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {
        
        if (addNum == 0) return;
        
        byte orderBy = ORDER_BY_DESC;
        
        CetAnnualObj entity = cetAnnualObjMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        int annualId = entity.getAnnualId();
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        if (addNum * orderBy > 0) {
            
            example.createCriteria().andSortOrderGreaterThan(baseSortOrder).andAnnualIdEqualTo(annualId);
            example.setOrderByClause("sort_order asc");
        } else {
            
            example.createCriteria().andSortOrderLessThan(baseSortOrder).andAnnualIdEqualTo(annualId);
            example.setOrderByClause("sort_order desc");
        }
        
        List<CetAnnualObj> overEntities = cetAnnualObjMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {
            
            CetAnnualObj targetEntity = overEntities.get(overEntities.size() - 1);
            
            if (addNum * orderBy > 0)
                commonMapper.downOrder("cet_annual_obj", "annual_id=" + annualId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_annual_obj", "annual_id=" + annualId, baseSortOrder, targetEntity.getSortOrder());
            
            CetAnnualObj record = new CetAnnualObj();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetAnnualObjMapper.updateByPrimaryKeySelective(record);
        }
    }
    
    // 设定年度学习任务（批量设定）
    @Transactional
    public void batchRequire(CetAnnualRequire record) {
        
        checkReuire(record);
        
        int annualId = record.getAnnualId();
        int adminLevel = record.getAdminLevel();
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        example.createCriteria().andAnnualIdEqualTo(annualId).andAdminLevelEqualTo(adminLevel);
        List<CetAnnualObj> cetAnnualObjs = cetAnnualObjMapper.selectByExample(example);
        record.setRelateCount(cetAnnualObjs.size());
        
        List<Integer> objIds = new ArrayList<>();
        for (CetAnnualObj obj : cetAnnualObjs) {
            
            objIds.add(obj.getId());
            sysApprovalLogService.add(obj.getId(), obj.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_ANNUAL,
                    "设定年度学习任务", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "批量设定");
        }
        iCetMapper.batchRequire(record, StringUtils.join(objIds, ","));
        
        record.setOpUserId(ShiroHelper.getCurrentUserId());
        record.setOpTime(new Date());
        record.setIp(ContextHelper.getRealIp());
        cetAnnualRequireMapper.insertSelective(record);
    }
    
    // 设定年度学习任务（个别设定）
    @Transactional
    public void singleRequire(int objId, CetAnnualRequire record) {
        
        checkReuire(record);
        
        CetAnnualObj obj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        
        sysApprovalLogService.add(obj.getId(), obj.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_ANNUAL,
                "设定年度学习任务", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                "个别设定");
        
        if (obj.getNeedUpdateRequire()) {
            CetAnnualObj update = new CetAnnualObj();
            update.setId(objId);
            
            CadreView cv = CmTag.getCadreByUserId(obj.getUserId());
            update.setTitle(cv.getTitle());
            update.setAdminLevel(cv.getAdminLevel());
            update.setPostType(cv.getPostType());
            update.setLpWorkTime(cv.getLpWorkTime());
            // 重置状态
            update.setNeedUpdateRequire(false);
            
            cetAnnualObjMapper.updateByPrimaryKeySelective(update);
        }
        
        iCetMapper.batchRequire(record, objId + "");
    }
    
    private void checkReuire(CetAnnualRequire record) {
        
        BigDecimal period = NumberUtils.trimToZero(record.getPeriod());
        BigDecimal maxSpecialPeriod = NumberUtils.trimToZero(record.getMaxSpecialPeriod());
        BigDecimal maxDailyPeriod = NumberUtils.trimToZero(record.getMaxDailyPeriod());
        BigDecimal maxPartyPeriod = NumberUtils.trimToZero(record.getMaxPartyPeriod());
        BigDecimal maxUnitPeriod = NumberUtils.trimToZero(record.getMaxUnitPeriod());
        BigDecimal maxUpperPeriod = NumberUtils.trimToZero(record.getMaxUpperPeriod());
        
        if (period.compareTo(maxSpecialPeriod.add(maxDailyPeriod)
                .add(maxPartyPeriod).add(maxUnitPeriod).add(maxUpperPeriod)) < 0) {
            
            throw new OpException("上限之和不允许大于年度学习任务。");
        }
    }
    
    
    // 最终的党校专题学时
    public BigDecimal getSpecialPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r) {
        
        BigDecimal period = null;
        if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
            period = NumberUtils.trimToZero(cetAnnualObj.getSpecialPeriod());
        } else {
            period = NumberUtils.trimToZero(r.get("specialPeriod"));
        }
        
        BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxSpecialPeriod());
        return (max.compareTo(BigDecimal.ZERO) > 0 && period.compareTo(max) > 0) ? max : period;
    }
    
    // 最终的党校日常学时
    public BigDecimal getDailyPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r) {
        
        BigDecimal period = null;
        if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
            period = NumberUtils.trimToZero(cetAnnualObj.getDailyPeriod());
        } else {
            period = NumberUtils.trimToZero(r.get("dailyPeriod"));
        }
        
        BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxDailyPeriod());
        return (max.compareTo(BigDecimal.ZERO) > 0 && period.compareTo(max) > 0) ? max : period;
    }
    
    // 最终的二级党委学时
    public BigDecimal getPartyPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r) {
        
        BigDecimal period = null;
        if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
            period = NumberUtils.trimToZero(cetAnnualObj.getPartyPeriod());
        } else {
            period = NumberUtils.trimToZero(r.get("partyPeriod"));
        }
        
        BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxPartyPeriod());
        return (max.compareTo(BigDecimal.ZERO) > 0 && period.compareTo(max) > 0) ? max : period;
    }
    
    // 最终的二级单位学时
    public BigDecimal getUnitPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r) {
        
        BigDecimal period = null;
        if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
            period = NumberUtils.trimToZero(cetAnnualObj.getUnitPeriod());
        } else {
            period = NumberUtils.trimToZero(r.get("unitPeriod"));
        }
        
        BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxUnitPeriod());
        return (max.compareTo(BigDecimal.ZERO) > 0 && period.compareTo(max) > 0) ? max : period;
    }
    
    // 最终的上级调训学时
    public BigDecimal getUpperPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r) {
        
        BigDecimal period = null;
        if (BooleanUtils.isTrue(cetAnnualObj.getHasArchived())) {
            period = NumberUtils.trimToZero(cetAnnualObj.getUpperPeriod());
        } else {
            period = NumberUtils.trimToZero(r.get("upperPeriod"));
        }
        
        BigDecimal max = NumberUtils.trimToZero(cetAnnualObj.getMaxUpperPeriod());
        return (max.compareTo(BigDecimal.ZERO) > 0 && period.compareTo(max) > 0) ? max : period;
    }
    
    // 最终的已完成学时
    public BigDecimal getFinishPeriod(CetAnnualObj cetAnnualObj, Map<String, BigDecimal> r) {
        
        BigDecimal finishPeriod = BigDecimal.ZERO;
        
        finishPeriod = finishPeriod.add(getSpecialPeriod(cetAnnualObj, r));
        finishPeriod = finishPeriod.add(getDailyPeriod(cetAnnualObj, r));
        finishPeriod = finishPeriod.add(getPartyPeriod(cetAnnualObj, r));
        finishPeriod = finishPeriod.add(getUnitPeriod(cetAnnualObj, r));
        finishPeriod = finishPeriod.add(getUpperPeriod(cetAnnualObj, r));
        
        return finishPeriod;
    }
    
    // 最终的已完成学时
    public BigDecimal getFinishPeriod(int objId) {
        
        CetAnnualObj obj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        
        return getFinishPeriod(obj, obj.getR());
    }
    
    // 归档已完成学时
    public void archiveFinishPeriod(int annualId) {
        
        CetAnnualObjExample exmaple = new CetAnnualObjExample();
        exmaple.createCriteria().andAnnualIdEqualTo(annualId)/*.andIsQuitEqualTo(isQuit)*/;
        List<CetAnnualObj> cetAnnualObjs = cetAnnualObjMapper.selectByExample(exmaple);
        
        for (CetAnnualObj cetAnnualObj : cetAnnualObjs) {
            
            archiveObjFinishPeriod(cetAnnualObj.getId());
        }
    }
    
    public void archiveObjFinishPeriod(int objId) {
        
        CetAnnualObj record = new CetAnnualObj();
        record.setId(objId);
        record.setFinishPeriod(getFinishPeriod(objId));
        
        cetAnnualObjMapper.updateByPrimaryKeySelective(record);
    }
    
    // 同步培训对象信息
    @Transactional
    public int sync(int annualId) {
        
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        //int year = cetAnnual.getYear();
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(cetAnnual.getTraineeTypeId());
        String code = cetTraineeType.getCode();
        
        CetAnnualObjExample exmaple = new CetAnnualObjExample();
        exmaple.createCriteria().andAnnualIdEqualTo(annualId);
        List<CetAnnualObj> cetAnnualObjs = cetAnnualObjMapper.selectByExample(exmaple);
        
        int adminLevelChangedCount = 0;
        
        for (CetAnnualObj cetAnnualObj : cetAnnualObjs) {
            
            Integer userId = cetAnnualObj.getUserId();
            
            switch (code) {
                // 中层干部
                case "t_cadre": {
                    
                    CetAnnualObj record = new CetAnnualObj();
                    record.setId(cetAnnualObj.getId());
                    
                    int adminLevel = cetAnnualObj.getAdminLevel();
                    CadreView cv = CmTag.getCadreByUserId(userId);
                    int latestAdminLevel = cv.getAdminLevel();
                    
                    // 排序始终同步最新的
                    record.setSortOrder(cv.getSortOrder());
                    
                    if (adminLevel != latestAdminLevel) {
                        // 行政级别变更了，不允许直接修改信息，必须修改年度学习任务
                        record.setNeedUpdateRequire(true);
                        adminLevelChangedCount++;
                    } else {
                        record.setTitle(cv.getTitle());
                        record.setAdminLevel(cv.getAdminLevel());
                        record.setPostType(cv.getPostType());
                        record.setLpWorkTime(cv.getLpWorkTime());
                    }
                    
                    cetAnnualObjMapper.updateByPrimaryKeySelective(record);
                }
                break;
            }
        }
        
        return adminLevelChangedCount;
    }
}
