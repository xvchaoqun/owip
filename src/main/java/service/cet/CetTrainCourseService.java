package service.cet;

import controller.global.OpException;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainCourseExample;
import domain.train.Train;
import domain.train.TrainEvaResultExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetTrainCourseService extends BaseMapper {

    @Transactional
    @CacheEvict(value="CetTrainCourses", key = "#record.trainId")
    public void insertSelective(CetTrainCourse record){

        record.setFinishCount(0);
        record.setStatus(SystemConstants.AVAILABLE);
        record.setSortOrder(getNextSortOrder("train_course",
                "train_id="+record.getTrainId()+ " and status="+SystemConstants.AVAILABLE));
        cetTrainCourseMapper.insertSelective(record);
        // 更新班次的课程数量
        iTrainMapper.update_train_courseNum();
    }

    @Transactional
    @CacheEvict(value="CetTrainCourses", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CetTrainCourse record = new CetTrainCourse();
        record.setStatus(SystemConstants.UNAVAILABLE);
        cetTrainCourseMapper.updateByExampleSelective(record, example);

        // 更新班次的课程数量
        iTrainMapper.update_train_courseNum();
    }

    @Transactional
    @CacheEvict(value="CetTrainCourses", allEntries = true)
    public int updateByPrimaryKeySelective(CetTrainCourse record){

        record.setTrainId(null);
        return cetTrainCourseMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetTrainCourses", key = "#trainId")
    public Map<Integer, CetTrainCourse> findAll(int trainId) {

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order asc");
        List<CetTrainCourse> trainCoursees = cetTrainCourseMapper.selectByExample(example);
        Map<Integer, CetTrainCourse> map = new LinkedHashMap<>();
        for (CetTrainCourse trainCourse : trainCoursees) {
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
    @CacheEvict(value = "CetTrainCourses", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;
        byte orderBy = ORDER_BY_ASC;
        CetTrainCourse entity = cetTrainCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer trainId = entity.getTrainId();

        CetTrainCourseExample example = new CetTrainCourseExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder)
            .andTrainIdEqualTo(trainId).andStatusEqualTo(SystemConstants.AVAILABLE);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder)
                    .andTrainIdEqualTo(trainId).andStatusEqualTo(SystemConstants.AVAILABLE);
            example.setOrderByClause("sort_order desc");
        }

        List<CetTrainCourse> overEntities = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetTrainCourse targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("train_course", "train_id="+trainId + " and status="+SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("train_course", "train_id="+trainId + " and status="+SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());

            CetTrainCourse record = new CetTrainCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetTrainCourseMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 关联评估表
    @Transactional
    @CacheEvict(value="CetTrainCourses", key = "#trainId")
    public void evaTable(int trainId, Integer[] ids, int evaTableId) {

        if(ids.length==0) return;
        for (Integer id : ids) {
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            if(cetTrainCourse.getTrainId().intValue()==trainId
            && (cetTrainCourse.getEvaTableId()==null || cetTrainCourse.getEvaTableId().intValue()!=evaTableId)) {

                {
                    TrainEvaResultExample example = new TrainEvaResultExample();
                    example.createCriteria().andCourseIdEqualTo(id);
                    if (trainEvaResultMapper.countByExample(example) > 0) {
                        throw new OpException(String.format("课程[%s]已经产生了评估结果，不可以修改评估表", cetTrainCourse.getCourseId()));
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
    public int evaIsClosed(int courseId){

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(courseId);
        Train train = trainMapper.selectByPrimaryKey(cetTrainCourse.getTrainId());
        if(BooleanUtils.isTrue(train.getIsClosed())){
            return 1;
        }

        Date now = new Date();
        Date openTime = cetTrainCourse.getStartTime();
        Date closeTime = train.getCloseTime();

        if(openTime!=null && now.before(openTime)){
            return 2;
        }
        if(closeTime!=null && now.after(closeTime)){
            return 3;
        }

        return 0;
    }
}
