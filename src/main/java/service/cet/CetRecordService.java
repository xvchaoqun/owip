package service.cet;

import domain.cet.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CetRecordService extends CetBaseMapper {

    @Autowired
    private CetProjectObjService cetProjectObjService;

    // 获取或更新证书编码
    public String selectOrUpdateCertNo(int recordId){

        CetRecord cetRecord = cetRecordMapper.selectByPrimaryKey(recordId);
        return selectOrUpdateCertNo(cetRecord);
    }

    public String selectOrUpdateCertNo(CetRecord cetRecord){

        Integer recordId = cetRecord.getId();
        if(recordId==null) return null;
        int year = cetRecord.getYear();
        byte userType = cetRecord.getUserType();
        Byte specialType = cetRecord.getSpecialType();
        Integer projectType = cetRecord.getProjectType();
        Short certNo = cetRecord.getCertNo();

        if(specialType==null || projectType==null) return null;

        String projectTypeCode = null;
        CetProjectType cetProjectType = cetProjectTypeMapper.selectByPrimaryKey(projectType);
        if(cetProjectType!=null){
            projectTypeCode = cetProjectType.getCode();
        }
        if(projectTypeCode==null) return null;

        if(certNo==null){

            certNo = iCetMapper.getNextCertNo(year, userType, specialType, projectType);
            certNo = (certNo==null)?1:certNo;

            if(certNo>9999) return null; // 证书编号最多四位数

            int update = iCetMapper.updateCertNo(recordId, certNo, year, userType, specialType, projectType);

            if(update==0) return null; // 更新证书编号失败
        }

        return String.format("%s%s%s%s%04d",
                specialType==CetConstants.CET_PROJECT_TYPE_SPECIAL?"Z":"R",
                userType==SystemConstants.USER_TYPE_JZG?"T":"S",
                year,
                projectTypeCode,
                certNo);
    }

    // 增量同步上级调训（含党校其他培训）
    public void syncAllUpperTrain(){

        List<Integer> upperTrainIds = iCetMapper.getUnArchiveUpperTrainIds();

        for (Integer upperTrainId : upperTrainIds) {
            syncUpperTrain(upperTrainId);
        }
    }

    // 增量同步二级党委培训
    public void syncAllUnitTrian(){

        List<Integer> unitTrainIds = iCetMapper.getUnArchiveUnitTrainIds();

        for (Integer unitTrainId : unitTrainIds) {
            syncUnitTrain(unitTrainId);
        }
    }

    // 增量同步过程培训记录
    public void syncAllProjectObj(Integer projectId){

        {
            // 第一步：删除已退出参训人员的培训记录
            List<Integer> quitObjIds = new ArrayList<>();
            CetProjectObjExample example = new CetProjectObjExample();
            CetProjectObjExample.Criteria criteria = example.createCriteria().andIsQuitEqualTo(true);
            if(projectId!=null){
                criteria.andProjectIdEqualTo(projectId);
            }
            List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);
            for (CetProjectObj cetProjectObj : cetProjectObjs) {
                quitObjIds.add(cetProjectObj.getId());
            }
            if(quitObjIds.size()>0) {
                CetRecordExample example1 = new CetRecordExample();
                example1.createCriteria()
                        .andSourceTypeEqualTo(CetConstants.CET_SOURCE_TYPE_PROJECT)
                        .andSourceIdIn(quitObjIds);
                CetRecord record = new CetRecord();
                record.setIsDeleted(true);
                cetRecordMapper.updateByExampleSelective(record, example1);
            }
        }

        {
            // 第二步：同步未退出参训人员的培训记录
            List<Integer> objIds = iCetMapper.getUnArchiveProjectObjIds(projectId);
            for (Integer objId : objIds) {
                sysProjectObj(objId);
            }
        }
    }

    // 将一条上级调训（含党校其他培训）记录归档至培训记录
    @Transactional
    public void syncUpperTrain(int upperTrainId){

        CetUpperTrain t = cetUpperTrainMapper.selectByPrimaryKey(upperTrainId);
        byte type = CetConstants.CET_TYPE_UPPER;
        byte sourceType = CetConstants.CET_SOURCE_TYPE_UPPER; // 上级调训

        if(t.getType()==CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL){ // 党校其他培训

            if(t.getSpecialType()==null || t.getSpecialType()==CetConstants.CET_PROJECT_TYPE_SPECIAL){
                type = CetConstants.CET_TYPE_SPECIAL;
            }else{
                type = CetConstants.CET_TYPE_DAILY;
            }
        }

        if(t.getStatus()!=CetConstants.CET_UPPER_TRAIN_STATUS_PASS
                || t.getIsValid()==null){

            // 未通过审核不计入
            delByType(sourceType, upperTrainId);
            return;
        }

        CetRecord r = get(sourceType, upperTrainId);
        if(r==null){
            r = new CetRecord();
        }

        r.setYear(t.getYear());
        r.setUserId(t.getUserId());
        r.setTraineeTypeId(t.getTraineeTypeId());
        r.setOtherTraineeType(t.getOtherTraineeType());
        r.setTitle(t.getTitle());
        r.setStartDate(t.getStartDate());
        r.setEndDate(t.getEndDate());
        r.setName(t.getTrainName());
        r.setType(type);
        r.setSourceType(sourceType);
        r.setSourceId(upperTrainId);
        r.setOrganizer(StringUtils.defaultIfBlank(CmTag.getMetaTypeName(t.getOrganizer()), t.getOtherOrganizer()));
        r.setPeriod(t.getPeriod());
        if(BooleanUtils.isTrue(t.getIsOnline())) {
            r.setOnlinePeriod(t.getPeriod());
        }
        //r.setShouldFinishPeriod();
        r.setIsGraduate(t.getIsGraduate());
        r.setIsValid(t.getIsValid());
        r.setArchiveTime(new Date());

        SysUserView uv = t.getUser();
        r.setIsDeleted(false);
        r.setSpecialType(t.getSpecialType());
        r.setProjectType(t.getProjectTypeId());
        r.setUserType(uv.getType()== SystemConstants.USER_TYPE_JZG?
                CetConstants.CET_USER_TYPE_TEACHER:CetConstants.CET_USER_TYPE_STUDENT);
        //r.setNo();

        if(r.getId()==null){
            cetRecordMapper.insertSelective(r);
        }else{
            cetRecordMapper.updateByPrimaryKeySelective(r);
        }

        selectOrUpdateCertNo(r);
    }

    // 将一条二级党委培训记录同步至培训记录
    @Transactional
    public void syncUnitTrain(int unitTrainId){

        CetUnitTrain t = cetUnitTrainMapper.selectByPrimaryKey(unitTrainId);
        CetUnitProject p = t.getProject();

        byte type = CetConstants.CET_TYPE_PARTY_SPECIAL;
        byte sourceType = CetConstants.CET_SOURCE_TYPE_UNIT;

        if(p.getSpecialType() == CetConstants.CET_PROJECT_TYPE_DAILY){

            type = CetConstants.CET_TYPE_PARTY_DAILY;
        }

        if(t.getStatus()!=CetConstants.CET_UNITTRAIN_RERECORD_PASS
                || p.getStatus() != CetConstants.CET_UNIT_PROJECT_STATUS_PASS){

            // 未通过审核不计入
            delByType(sourceType, unitTrainId);
            return;
        }

        CetParty cetParty = p.getCetParty();
        String organizer = (cetParty==null)?"":cetParty.getName();
        CetRecord r = get(sourceType, unitTrainId);
        if(r==null){
            r = new CetRecord();
        }

        r.setYear(p.getYear());
        r.setUserId(t.getUserId());
        r.setTraineeTypeId(t.getTraineeTypeId());
        //r.setOtherTraineeType(t.getOtherTraineeType());
        r.setTitle(t.getTitle());
        r.setStartDate(p.getStartDate());
        r.setEndDate(p.getEndDate());
        r.setName(p.getProjectName());
        r.setType(type);
        r.setSourceType(sourceType);
        r.setSourceId(unitTrainId);
        r.setCetPartyId((cetParty==null)?null:cetParty.getId());
        r.setOrganizer(organizer);
        r.setPeriod(t.getPeriod());
        if(BooleanUtils.isTrue(p.getIsOnline())) {
            r.setOnlinePeriod(t.getPeriod());
        }
        //r.setShouldFinishPeriod();
        r.setIsGraduate(t.getIsGraduate());
        r.setIsValid(p.getIsValid());
        r.setArchiveTime(new Date());

        SysUserView uv = t.getUser();
        r.setIsDeleted(false);
        r.setSpecialType(p.getSpecialType());
        r.setProjectType(p.getProjectTypeId());
        r.setUserType(uv.getType()== SystemConstants.USER_TYPE_JZG?
                CetConstants.CET_USER_TYPE_TEACHER:CetConstants.CET_USER_TYPE_STUDENT);
        //r.setNo();

        if(r.getId()==null){
            cetRecordMapper.insertSelective(r);
        }else{
            cetRecordMapper.updateByPrimaryKeySelective(r);
        }

        selectOrUpdateCertNo(r);
    }

    // 同步过程培训记录至培训记录
    @Transactional
    public void sysProjectObj(int projectObjId){

        CetProjectObj o = cetProjectObjMapper.selectByPrimaryKey(projectObjId);
        int userId = o.getUserId();
        CetProject p = cetProjectMapper.selectByPrimaryKey(o.getProjectId());

        byte type = CetConstants.CET_TYPE_SPECIAL;
        byte sourceType = CetConstants.CET_SOURCE_TYPE_PROJECT;

        if(p.getType()==CetConstants.CET_PROJECT_TYPE_DAILY){

            type = CetConstants.CET_TYPE_DAILY;
        }

        if(p.getIsPartyProject()){

            type = CetConstants.CET_TYPE_PARTY_SPECIAL;

            if(p.getType()==CetConstants.CET_PROJECT_TYPE_DAILY){

                type = CetConstants.CET_TYPE_PARTY_DAILY;
            }
        }

        // 已退出培训不计入
        if(BooleanUtils.isTrue(o.getIsQuit())){

            delByType(sourceType, projectObjId);
            return;
        }

        int projectId = o.getProjectId();
        Map<Integer, BigDecimal> finishPeriodMap = cetProjectObjService.getRealObjFinishPeriodMap(projectId, projectObjId);
        BigDecimal finishPeriod = finishPeriodMap.get(0);

        // 还没有完成学时不计入
        if(BooleanUtils.isNotTrue(o.getIsGraduate())){

            delByType(sourceType, projectObjId);
            return;
        }

        CetRecord r = get(sourceType, projectObjId);
        if(r==null){
            r = new CetRecord();
        }

        r.setYear(p.getYear());
        r.setUserId(o.getUserId());
        r.setTraineeTypeId(o.getTraineeTypeId());
        //r.setOtherTraineeType(t.getOtherTraineeType());
        r.setTitle(o.getTitle());
        r.setStartDate(p.getStartDate());
        r.setEndDate(p.getEndDate());
        r.setName(p.getName());
        r.setType(type);
        r.setSourceType(sourceType);
        r.setSourceId(projectObjId);

        if(!p.getIsPartyProject()) {

            r.setOrganizer("党委组织部");

            // 党校网络培训
            BigDecimal planFinishPeriod = NumberUtils.trimToZero(iCetMapper
                    .getPlanFinishPeriod(CetConstants.CET_PROJECT_PLAN_TYPE_ONLINE, userId, null, projectId));
            BigDecimal specialFinishPeriod = NumberUtils.trimToZero(iCetMapper
                    .getSpecialFinishPeriod(CetConstants.CET_PROJECT_PLAN_TYPE_SPECIAL, userId, null, projectId));
            BigDecimal onlinePeriod = planFinishPeriod.add(specialFinishPeriod);

            r.setOnlinePeriod(onlinePeriod);

        }else{

            CetParty cetParty = p.getCetParty();
            String organizer = (cetParty==null)?"":cetParty.getName();
            r.setOrganizer(organizer);

            r.setCetPartyId(p.getCetPartyId());

            BigDecimal onlinePeriod = NumberUtils.trimToZero(iCetMapper.getOnlineFinishPeriod(userId, projectId));
            r.setOnlinePeriod(onlinePeriod);
        }

        r.setPeriod(finishPeriod);

        r.setShouldFinishPeriod(o.getShouldFinishPeriod());
        r.setIsGraduate(o.getIsGraduate());

        r.setIsValid(p.getIsValid());
        r.setArchiveTime(new Date());

        SysUserView uv = o.getUser();
        r.setIsDeleted(false);
        r.setSpecialType(p.getType());
        r.setProjectType(p.getProjectTypeId());
        r.setUserType(uv.getType()== SystemConstants.USER_TYPE_JZG?
                CetConstants.CET_USER_TYPE_TEACHER:CetConstants.CET_USER_TYPE_STUDENT);
        //r.setNo();

        if(r.getId()==null){
            cetRecordMapper.insertSelective(r);
        }else{
            cetRecordMapper.updateByPrimaryKeySelective(r);
        }

        selectOrUpdateCertNo(r);
    }

    // 根据类型 和 类型的主键 查找培训记录
    public CetRecord get(byte sourceType, int sourceId){

        CetRecordExample example = new CetRecordExample();
        example.createCriteria().andSourceTypeEqualTo(sourceType).andSourceIdEqualTo(sourceId);
        List<CetRecord> cetRecords = cetRecordMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetRecords.size()>0?cetRecords.get(0):null;
    }

    public int delByType(byte sourceType, int sourceId){

        CetRecordExample example = new CetRecordExample();
        example.createCriteria().andSourceTypeEqualTo(sourceType).andSourceIdEqualTo(sourceId);

        CetRecord record = new CetRecord();
        record.setIsDeleted(true);
        return cetRecordMapper.updateByExampleSelective(record, example);
    }

    // 按类型读取培训记录
    public List<CetRecord> getRecords(int year, int userId, Integer traineeTypeId, Byte type, boolean isValid){

        CetRecordExample example = new CetRecordExample();
        CetRecordExample.Criteria criteria = example.createCriteria().andYearEqualTo(year).andUserIdEqualTo(userId)
                .andIsValidEqualTo(isValid);

        if(traineeTypeId!=null){
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }

        example.setOrderByClause("start_date asc, type asc");

        return cetRecordMapper.selectByExample(example);
    }
}
