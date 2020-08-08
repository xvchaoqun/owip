package service.cet;

import controller.global.OpException;
import domain.cet.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.CetConstants;

import java.util.*;

@Service
public class CetTrainCourseService extends CetBaseMapper {

    @Autowired
    private CetCourseService cetCourseService;

    @Cacheable(value = "CetTrainCourseSignToken", key = "#trainCourseId")
    public String getSignToken(int trainCourseId){

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        String signToken = cetTrainCourse.getSignToken();
        Long signTokenExpire = cetTrainCourse.getSignTokenExpire();
        if(StringUtils.isBlank(signToken) || signTokenExpire==null){
            return null;
        }

        return  signToken + "_"+ signTokenExpire;
    }

    @CacheEvict(value = "CetTrainCourseSignToken", key = "#trainCourseId")
    public void updateSignToken(int trainCourseId, String signToken, long signTokenExpire){

        CetTrainCourse record = new CetTrainCourse();
        record.setId(trainCourseId);
        record.setSignToken(signToken);
        record.setSignTokenExpire(signTokenExpire);

        cetTrainCourseMapper.updateByPrimaryKeySelective(record);
    }


    public CetTrainCourse get(int trainId, int courseId) {

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andCourseIdEqualTo(courseId);
        List<CetTrainCourse> cetTrainCourses = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (cetTrainCourses.size() > 0) ? cetTrainCourses.get(0) : null;
    }

    @Transactional
    @CacheEvict(value = "CetTrainCourses", key = "#record.trainId")
    public void insertSelective(CetTrainCourse record) {

        Integer trainId = record.getTrainId();
        Integer projectId = record.getProjectId();
        String whereSql = null;
        if(trainId==null){
            whereSql = "project_id=" + projectId;
        }else{
            whereSql = "train_id=" + trainId;
        }

        record.setSortOrder(getNextSortOrder("cet_train_course", whereSql));
        cetTrainCourseMapper.insertSelective(record);

        if(trainId!=null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            if (cetTrain.getPlanId() != null) {
                iCetMapper.updateTrainCourseTotalPeriod(cetTrain.getPlanId());
            }

            iCetMapper.updateTrainCourseNum(trainId);

        }else if(projectId!=null){
            iCetMapper.updateProjectTotalPeriodByCourse(projectId);
        }
    }


    @Transactional
    @CacheEvict(value = "CetTrainCourses", allEntries = true)
    public void batchDel(Integer[] ids, Integer projectId, Integer trainId) {

        if (ids == null || ids.length == 0) return;

        CetTrainCourseExample example = new CetTrainCourseExample();
        CetTrainCourseExample.Criteria criteria = example.createCriteria().andIdIn(Arrays.asList(ids));
        if(trainId!=null){
            criteria.andTrainIdEqualTo(trainId);
        }else if(projectId!=null){
            criteria.andProjectIdEqualTo(projectId);
        }
        cetTrainCourseMapper.deleteByExample(example);

        if(trainId!=null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            if (cetTrain.getPlanId() != null) {
                iCetMapper.updateTrainCourseTotalPeriod(cetTrain.getPlanId());
            }

            iCetMapper.updateTrainCourseNum(trainId);

        }else if(projectId!=null){
            iCetMapper.updateProjectTotalPeriodByCourse(projectId);
        }
    }

    @Transactional
    @CacheEvict(value = "CetTrainCourses", allEntries = true)
    public void updateByPrimaryKeySelective(CetTrainCourse record) {

        cetTrainCourseMapper.updateByPrimaryKeySelective(record);
        if(record.getApplyLimit()==null){
            commonMapper.excuteSql("update cet_train_course set apply_limit=null where id=" + record.getId());
        }

        Integer trainId = record.getTrainId();
        Integer projectId = record.getProjectId();
        if(trainId!=null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            if (cetTrain.getPlanId() != null) {
                iCetMapper.updateTrainCourseTotalPeriod(cetTrain.getPlanId());
            }
        }else if(projectId!=null){
            iCetMapper.updateProjectTotalPeriodByCourse(projectId);
        }
    }

    @Cacheable(value = "CetTrainCourses", key = "#trainId")
    public Map<Integer, CetTrainCourse> findAll(int trainId) {

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId);
        example.setOrderByClause("sort_order asc");
        List<CetTrainCourse> trainCoursees = cetTrainCourseMapper.selectByExample(example);
        Map<Integer, CetTrainCourse> map = new LinkedHashMap<>();
        for (CetTrainCourse trainCourse : trainCoursees) {
            map.put(trainCourse.getId(), trainCourse);
        }

        return map;
    }

    @Transactional
    public void batchAddFile(List<CetTrainCourseFile> records) {

        for (CetTrainCourseFile record : records) {
            //record.setSortOrder(getNextSortOrder("sc_group_file", null));
            cetTrainCourseFileMapper.insertSelective(record);
        }
    }

    @Transactional
    public void delFile(Integer fileId) {

        cetTrainCourseFileMapper.deleteByPrimaryKey(fileId);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetTrainCourses", allEntries = true)
    public void changeOrder(int id, int addNum) {

        CetTrainCourse entity = cetTrainCourseMapper.selectByPrimaryKey(id);

        Integer trainId = entity.getTrainId();
        Integer projectId = entity.getProjectId();
        String whereSql = null;
        if(trainId==null){
            whereSql = "project_id=" + projectId;
        }else{
            whereSql = "train_id=" + trainId;
        }

        changeOrder("cet_train_course", whereSql, ORDER_BY_ASC, id, addNum);
    }

    // 添加课程
    @Transactional
    @CacheEvict(value = "CetTrainCourses", allEntries = true)
    public void selectCourses(int trainId, Integer[] courseIds) {

        if (courseIds == null || courseIds.length == 0) return;

        CetProject cetProject = iCetMapper.getCetProject(trainId);
        Integer projectId = null;
        if(cetProject!=null){
            projectId = cetProject.getId();
        }

        for (int courseId : courseIds) {

            CetCourse cetCourse = cetCourseService.get(courseId);

            CetTrainCourse record = new CetTrainCourse();
            record.setProjectId(projectId);
            record.setTrainId(trainId);
            record.setCourseId(courseId);
            // 以下信息不再随课程变化
            record.setName(cetCourse.getName());
            CetExpert cetExpert = cetCourse.getCetExpert();
            if(cetExpert!=null) {
                record.setTeacher(cetExpert.getRealname());
            }
            record.setPeriod(cetCourse.getPeriod());
            record.setAddress(cetCourse.getAddress());
            record.setSummary(cetCourse.getSummary());
            record.setIsOnline(cetCourse.getType()==CetConstants.CET_COURSE_TYPE_ONLINE);

            CetTrainCourse cetTrainCourse = get(trainId, courseId);
            if (cetTrainCourse != null){

                record.setId(cetTrainCourse.getId());
                cetTrainCourseMapper.updateByPrimaryKeySelective(record);

            }else {

                record.setSortOrder(getNextSortOrder("cet_train_course", "train_id=" + trainId));
                cetTrainCourseMapper.insertSelective(record);
            }
        }

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        if(cetTrain.getPlanId() !=null) {
            iCetMapper.updateTrainCourseTotalPeriod(cetTrain.getPlanId());
        }

        iCetMapper.updateTrainCourseNum(trainId);
    }

    // 关联评估表
    @Transactional
    @CacheEvict(value = "CetTrainCourses", key = "#trainId")
    public void evaTable(int trainId, Integer[] ids, int evaTableId) {

        if (ids.length == 0) return;
        for (Integer id : ids) {
            CetTrainCourse trainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            if (trainCourse.getTrainId().intValue() == trainId
                    && (trainCourse.getEvaTableId() == null || trainCourse.getEvaTableId().intValue() != evaTableId)) {

                {
                    CetTrainEvaResultExample example = new CetTrainEvaResultExample();
                    example.createCriteria().andTrainCourseIdEqualTo(id);
                    if (cetTrainEvaResultMapper.countByExample(example) > 0) {
                        throw new OpException(String.format("课程[%s]已经产生了评估结果，不可以修改评估表", trainCourse.getName()));
                    }
                }

                CetTrainCourse record = new CetTrainCourse();
                record.setId(id);
                record.setEvaTableId(evaTableId);
                cetTrainCourseMapper.updateByPrimaryKeySelective(record);
            }
        }
    }

    // 0：评课进行中 1:已关闭评课 2：评课未开始（未上课） 3：评课已结束
    public int evaIsClosed(int courseId) {

        CetTrainCourse trainCourse = cetTrainCourseMapper.selectByPrimaryKey(courseId);
        CetTrain train = cetTrainMapper.selectByPrimaryKey(trainCourse.getTrainId());
        if (BooleanUtils.isTrue(train.getEvaClosed())) {
            return 1;
        }

        Date now = new Date();
        Date openTime = trainCourse.getStartTime();
        Date closeTime = train.getEvaCloseTime();

        if (openTime != null && now.before(openTime)) {
            return 2;
        }
        if (closeTime != null && now.after(closeTime)) {
            return 3;
        }

        return 0;
    }

    // 对外培训中导入课程
    @Transactional
    @CacheEvict(value = "CetTrainCourses", key = "#trainId")
    public int offTrainCourseBatchImport(int trainId, List<CetTrainCourse> records) {

        int addCount = 0;
        for (CetTrainCourse record : records) {

            record.setSortOrder(getNextSortOrder("cet_train_course", "train_id=" + record.getTrainId()));
            cetTrainCourseMapper.insertSelective(record);

            addCount++;
        }

        iCetMapper.updateTrainCourseNum(trainId);

        return addCount;
    }

    // 获取课程参训人（已选课学员） <userId, CetTrainObjView>
    public Map<Integer, CetTrainObjView> findTrainees(int trainCourseId) {

        CetTrainObjViewExample example = new CetTrainObjViewExample();
        example.createCriteria().andTrainCourseIdEqualTo(trainCourseId);

        Map<Integer, CetTrainObjView> resultMap = new HashMap<>();
        List<CetTrainObjView> cetTrainObjViews = cetTrainObjViewMapper.selectByExample(example);
        for (CetTrainObjView cetTrainObjView : cetTrainObjViews) {

            resultMap.put(cetTrainObjView.getUserId(), cetTrainObjView);
        }

        return resultMap;
    }
}
