package service.cet;

import domain.cadre.CadreView;
import domain.cet.CetAnnual;
import domain.cet.CetAnnualObj;
import domain.cet.CetAnnualObjExample;
import domain.cet.CetTraineeType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysApprovalLogService;
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CetAnnualObjService extends CetBaseMapper {

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

    @Transactional
    public boolean addOrUpdate(CetAnnualObj record) {
        boolean isSuccess = false;

        Integer annualId = record.getAnnualId();
        Integer userId = record.getUserId();
        Integer postType = record.getPostType();
        String title = record.getTitle();

        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        int year = cetAnnual.getYear();
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(cetAnnual.getTraineeTypeId());
        String code = cetTraineeType.getCode();

        // 已选人员
        Set<Integer> selectedAnnualObjUserIdSet = getSelectedAnnualObjUserIdSet(annualId);

        if (selectedAnnualObjUserIdSet.contains(userId)) {

            updateByPrimaryKeySelective(record);
            return isSuccess;
        }

            record.setYear(year);
            record.setAnnualId(annualId);
            record.setSortOrder(getNextSortOrder("cet_annual_obj","annual_id="+annualId));

            switch (code) {
                // 干部
                case "t_cadre": {
                    CadreView cv = CmTag.getCadreByUserId(userId);

                    if (cv != null){
                        record.setTitle(StringUtils.isBlank(title)?cv.getTitle():title);
                        record.setPostType(postType == null?cv.getPostType():postType);

                        record.setAdminLevel(cv.getAdminLevel());
                        record.setLpWorkTime(cv.getLpWorkTime());
                        record.setSortOrder(cv.getSortOrder());
                    }
                }
                break;
            }
            cetAnnualObjMapper.insertSelective(record);

            sysApprovalLogService.add(record.getId(), record.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_ANNUAL,
                    "添加培训对象", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "新建");
            isSuccess = true;

       /* // 需要删除的人员
        selectedAnnualObjUserIdSet.removeAll(toAddUserIdSet);
        for (Integer userId : selectedAnnualObjUserIdSet) {
            CetAnnualObjExample example = new CetAnnualObjExample();
            example.createCriteria().andAnnualIdEqualTo(annualId).andUserIdEqualTo(userId);
            cetAnnualObjMapper.deleteByExample(example);
        }*/

        updateObjCount(annualId);
        return isSuccess;
    }

    public int importCetProjectObj(List<CetAnnualObj> cetAnnualObjs){

        int successCount = 0;

        for (CetAnnualObj cetAnnualObj : cetAnnualObjs){
            //boolean isSuccess = addOrUpdate(cetAnnualObj);
            if (addOrUpdate(cetAnnualObj)) successCount ++;
        }

        return successCount;
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

    // 党校专题培训完成学时数、 党校日常培训完成学时数、 党校其他培训完成学时数、 二级党委培训完成学时数、 上级调训完成学时数
    public Map<String, BigDecimal> getFinishPeriodMap(CetAnnualObj cetAnnualObj) {

        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(cetAnnualObj.getAnnualId());
        int traineeTypeId = cetAnnual.getTraineeTypeId();
        int userId = cetAnnualObj.getUserId();
        int year = cetAnnualObj.getYear();

        BigDecimal specialFinishPeriod = NumberUtils.trimToZero(iCetMapper.totalFinishPeriod(year, userId, traineeTypeId, CetConstants.CET_TYPE_SPECIAL));
        BigDecimal dailyFinishPeriod = NumberUtils.trimToZero(iCetMapper.totalFinishPeriod(year, userId, traineeTypeId, CetConstants.CET_TYPE_DAILY));

        BigDecimal unitSpecialFinishPeriod = NumberUtils.trimToZero(iCetMapper.totalFinishPeriod(year, userId, traineeTypeId, CetConstants.CET_TYPE_PARTY_SPECIAL));
        BigDecimal unitDailyFinishPeriod = NumberUtils.trimToZero(iCetMapper.totalFinishPeriod(year, userId, traineeTypeId, CetConstants.CET_TYPE_PARTY_DAILY));
        BigDecimal unitFinishPeriod = unitSpecialFinishPeriod.add(unitDailyFinishPeriod);

        BigDecimal upperFinishPeriod = NumberUtils.trimToZero(iCetMapper.totalFinishPeriod(year, userId, traineeTypeId, CetConstants.CET_TYPE_UPPER));

        Map<String, BigDecimal> resultMap = new HashMap<>();
        resultMap.put("specialPeriod", specialFinishPeriod);
        resultMap.put("dailyPeriod", dailyFinishPeriod);
        resultMap.put("unitPeriod", unitFinishPeriod);
        resultMap.put("upperPeriod", upperFinishPeriod);

        // 总数
        BigDecimal finishPeriod = specialFinishPeriod.add(dailyFinishPeriod).add(unitFinishPeriod).add(upperFinishPeriod);
        resultMap.put("finishPeriod", finishPeriod);

        BigDecimal onlinePeriod = NumberUtils.trimToZero(iCetMapper.totalOnlinePeriod(year, userId, traineeTypeId, null));
        resultMap.put("offlinePeriod", finishPeriod.subtract(onlinePeriod));
        resultMap.put("onlinePeriod", onlinePeriod);

        return resultMap;
    }

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

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        CetAnnualObj entity = cetAnnualObjMapper.selectByPrimaryKey(id);
        changeOrder("cet_annual_obj", "annual_id=" + entity.getAnnualId(), ORDER_BY_DESC, id, addNum);
    }

    // 设定年度学习任务（批量设定）
    @Transactional
    public void updateRequire(BigDecimal periodOffline, BigDecimal periodOnline, Integer[] objIds) {

        for (Integer objId : objIds) {
            CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(objId);
            sysApprovalLogService.add(cetAnnualObj.getId(), cetAnnualObj.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_ANNUAL,
                    "设定年度学习任务", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "批量设定");
        }

        iCetMapper.batchRequire(periodOffline, periodOnline, StringUtils.join(objIds, ","));
    }

    // 每个类别最终的完成学时
    public BigDecimal totalFinishPeriod(CetAnnualObj cetAnnualObj, Byte type) {

        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(cetAnnualObj.getAnnualId());
        int traineeTypeId = cetAnnual.getTraineeTypeId();
        int userId = cetAnnualObj.getUserId();
        int year = cetAnnualObj.getYear();

        return NumberUtils.trimToZero(iCetMapper.totalFinishPeriod(year, userId, traineeTypeId, type));
    }

    // 最终的已完成学时（网络）
    public BigDecimal getFinishPeriodOnline(CetAnnualObj cetAnnualObj) {

        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(cetAnnualObj.getAnnualId());
        int traineeTypeId = cetAnnual.getTraineeTypeId();
        int userId = cetAnnualObj.getUserId();
        int year = cetAnnualObj.getYear();

        return NumberUtils.trimToZero(iCetMapper.totalOnlinePeriod(year, userId, traineeTypeId, null));
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

    public void archiveObjFinishPeriod(Integer[] objIds) {

        for (int objId : objIds) {
            archiveObjFinishPeriod(objId);
        }
    }
    public void archiveObjFinishPeriod(int objId) {

        CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        int userId = cetAnnualObj.getUserId();
        int year = cetAnnualObj.getYear();
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(cetAnnualObj.getAnnualId());
        int traineeTypeId = cetAnnual.getTraineeTypeId();

        BigDecimal specialFinishPeriod = NumberUtils.trimToZero(totalFinishPeriod(cetAnnualObj, CetConstants.CET_TYPE_SPECIAL));
        BigDecimal dailyFinishPeriod = NumberUtils.trimToZero(totalFinishPeriod(cetAnnualObj, CetConstants.CET_TYPE_DAILY));

        BigDecimal unitSpecialFinishPeriod = NumberUtils.trimToZero(totalFinishPeriod(cetAnnualObj, CetConstants.CET_TYPE_PARTY_SPECIAL)); // 二级党委
        BigDecimal unitDailyFinishPeriod = NumberUtils.trimToZero(totalFinishPeriod(cetAnnualObj, CetConstants.CET_TYPE_PARTY_DAILY)); // 二级党委
        BigDecimal unitFinishPeriod = unitSpecialFinishPeriod.add(unitDailyFinishPeriod);

        BigDecimal upperFinishPeriod = NumberUtils.trimToZero(totalFinishPeriod(cetAnnualObj, CetConstants.CET_TYPE_UPPER)); // 上级调训

        // 总数
        BigDecimal finishPeriod = specialFinishPeriod.add(dailyFinishPeriod).add(unitFinishPeriod).add(upperFinishPeriod);

        BigDecimal finishPeriodOnline = NumberUtils.trimToZero(iCetMapper.totalOnlinePeriod(year, userId, traineeTypeId, null));
        CetAnnualObj record = new CetAnnualObj();
        record.setId(objId);

        record.setSpecialPeriod(specialFinishPeriod);
        record.setDailyPeriod(dailyFinishPeriod);
        record.setUnitPeriod(unitFinishPeriod);
        record.setUpperPeriod(upperFinishPeriod);

        record.setFinishPeriodOffline(finishPeriod.subtract(finishPeriodOnline));
        record.setFinishPeriodOnline(finishPeriodOnline);
        record.setHasArchived(true);

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
                // 干部
                case "t_cadre": {

                    CetAnnualObj record = new CetAnnualObj();
                    record.setId(cetAnnualObj.getId());

                    Integer adminLevel = cetAnnualObj.getAdminLevel();
                    CadreView cv = CmTag.getCadreByUserId(userId);
                    Integer latestAdminLevel = cv.getAdminLevel();

                    // 排序始终同步最新的
                    record.setSortOrder(cv.getSortOrder());

                    if (!NumberUtils.intEqual(adminLevel, latestAdminLevel)) {
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
