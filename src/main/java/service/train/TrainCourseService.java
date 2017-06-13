package service.train;

import bean.XlsTrainCourse;
import domain.train.Train;
import domain.train.TrainCourse;
import domain.train.TrainCourseExample;
import domain.train.TrainEvaResultExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class TrainCourseService extends BaseMapper {

    @Transactional
    @CacheEvict(value="TrainCourses", key = "#record.trainId")
    public void insertSelective(TrainCourse record){

        record.setFinishCount(0);
        record.setStatus(SystemConstants.AVAILABLE);
        record.setSortOrder(getNextSortOrder("train_course",
                "train_id="+record.getTrainId()+ " and status="+SystemConstants.AVAILABLE));
        trainCourseMapper.insertSelective(record);
        // 更新班次的课程数量
        iTrainMapper.update_train_courseNum();
    }

    @Transactional
    @CacheEvict(value="TrainCourses", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        TrainCourseExample example = new TrainCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        TrainCourse record = new TrainCourse();
        record.setStatus(SystemConstants.UNAVAILABLE);
        trainCourseMapper.updateByExampleSelective(record, example);

        // 更新班次的课程数量
        iTrainMapper.update_train_courseNum();
    }

    @Transactional
    @CacheEvict(value="TrainCourses", allEntries = true)
    public int updateByPrimaryKeySelective(TrainCourse record){

        record.setTrainId(null);
        return trainCourseMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="TrainCourses", key = "#trainId")
    public Map<Integer, TrainCourse> findAll(int trainId) {

        TrainCourseExample example = new TrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order asc");
        List<TrainCourse> trainCoursees = trainCourseMapper.selectByExample(example);
        Map<Integer, TrainCourse> map = new LinkedHashMap<>();
        for (TrainCourse trainCourse : trainCoursees) {
            map.put(trainCourse.getId(), trainCourse);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "TrainCourses", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        TrainCourse entity = trainCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer trainId = entity.getTrainId();

        TrainCourseExample example = new TrainCourseExample();
        if (addNum < 0) { // 正序

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder)
            .andTrainIdEqualTo(trainId).andStatusEqualTo(SystemConstants.AVAILABLE);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder)
                    .andTrainIdEqualTo(trainId).andStatusEqualTo(SystemConstants.AVAILABLE);
            example.setOrderByClause("sort_order desc");
        }

        List<TrainCourse> overEntities = trainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            TrainCourse targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum < 0)
                commonMapper.downOrder("train_course", "train_id="+trainId + " and status="+SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("train_course", "train_id="+trainId + " and status="+SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());

            TrainCourse record = new TrainCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            trainCourseMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 关联评估表
    @Transactional
    @CacheEvict(value="TrainCourses", key = "#trainId")
    public void evaTable(int trainId, Integer[] ids, int evaTableId) {

        if(ids.length==0) return;
        for (Integer id : ids) {
            TrainCourse trainCourse = trainCourseMapper.selectByPrimaryKey(id);
            if(trainCourse.getTrainId().intValue()==trainId
            && (trainCourse.getEvaTableId()==null || trainCourse.getEvaTableId().intValue()!=evaTableId)) {

                {
                    TrainEvaResultExample example = new TrainEvaResultExample();
                    example.createCriteria().andCourseIdEqualTo(id);
                    if (trainEvaResultMapper.countByExample(example) > 0) {
                        throw new RuntimeException(String.format("课程[%s]已经产生了评估结果，不可以修改评估表", trainCourse.getName()));
                    }
                }

                TrainCourse record = new TrainCourse();
                record.setId(id);
                record.setEvaTableId(evaTableId);
                trainCourseMapper.updateByPrimaryKeySelective(record);
            }
        }
    }

    // 0：评课进行中 1:已关闭评课 2：评课未开始（未上课） 3：评课已结束
    public int evaIsClosed(int courseId){

        TrainCourse trainCourse = trainCourseMapper.selectByPrimaryKey(courseId);
        Train train = trainMapper.selectByPrimaryKey(trainCourse.getTrainId());
        if(BooleanUtils.isTrue(train.getIsClosed())){
            return 1;
        }

        Date now = new Date();
        Date openTime = trainCourse.getStartTime();
        Date closeTime = train.getCloseTime();

        if(openTime!=null && now.before(openTime)){
            return 2;
        }
        if(closeTime!=null && now.after(closeTime)){
            return 3;
        }

        return 0;
    }


    @Transactional
    @CacheEvict(value="TrainCourses", key = "#trainId")
    public int imports(final List<XlsTrainCourse> beans, int trainId) {

        int success = 0;
        for (XlsTrainCourse uRow : beans) {

            TrainCourse record = new TrainCourse();
            record.setName(uRow.getName());
            record.setTeacher(uRow.getTeacher());
            record.setStartTime(uRow.getStartTime());
            record.setEndTime(uRow.getEndTime());
            record.setTrainId(trainId);

            insertSelective(record);
            success++;
        }

        return success;
    }
}
