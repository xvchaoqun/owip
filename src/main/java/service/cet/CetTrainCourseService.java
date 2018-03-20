package service.cet;

import bean.XlsTrainCourse;
import controller.global.OpException;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainCourseExample;
import domain.cet.CetTrainEvaResultExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetTrainCourseService extends BaseMapper {


    public CetTrainCourse get(int trainId, int courseId){

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andCourseIdEqualTo(courseId);
        List<CetTrainCourse> cetTrainCourses = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (cetTrainCourses.size()>0)?cetTrainCourses.get(0):null;
    }

    @Transactional
    @CacheEvict(value="CetTrainCourses", key = "#record.trainId")
    public void insertSelective(CetTrainCourse record){

        record.setSortOrder(getNextSortOrder("cet_train_course", "train_id="+record.getTrainId()));
        cetTrainCourseMapper.insertSelective(record);
    }


    @Transactional
    @CacheEvict(value="CetTrainCourses", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        cetTrainCourseMapper.deleteByExample(example);
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
        example.createCriteria().andTrainIdEqualTo(trainId);
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
    @CacheEvict(value="CetTrainCourses", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;
        byte orderBy = ORDER_BY_ASC;
        CetTrainCourse entity = cetTrainCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer trainId = entity.getTrainId();

        CetTrainCourseExample example = new CetTrainCourseExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder)
            .andTrainIdEqualTo(trainId);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder)
                    .andTrainIdEqualTo(trainId);
            example.setOrderByClause("sort_order desc");
        }

        List<CetTrainCourse> overEntities = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetTrainCourse targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_train_course", "train_id="+trainId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_train_course", "train_id="+trainId, baseSortOrder, targetEntity.getSortOrder());

            CetTrainCourse record = new CetTrainCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetTrainCourseMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 添加课程
    @Transactional
    public void selectCourses(int trainId, Integer[] courseIds) {

        if(courseIds==null || courseIds.length==0) return;

        for (Integer courseId : courseIds) {

            if(get(trainId, courseId)!=null) continue;

            CetTrainCourse record = new CetTrainCourse();
            record.setTrainId(trainId);
            record.setCourseId(courseId);
            record.setSortOrder(getNextSortOrder("cet_train_course", "train_id="+record.getTrainId()));
            cetTrainCourseMapper.insertSelective(record);
        }
    }

    // 关联评估表
    @Transactional
    @CacheEvict(value="CetTrainCourses", key = "#trainId")
    public void evaTable(int trainId, Integer[] ids, int evaTableId) {

        if(ids.length==0) return;
        for (Integer id : ids) {
            CetTrainCourse trainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            if(trainCourse.getTrainId().intValue()==trainId
                    && (trainCourse.getEvaTableId()==null || trainCourse.getEvaTableId().intValue()!=evaTableId)) {

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
    public int evaIsClosed(int courseId){

        CetTrainCourse trainCourse = cetTrainCourseMapper.selectByPrimaryKey(courseId);
        CetTrain train = cetTrainMapper.selectByPrimaryKey(trainCourse.getTrainId());
        if(BooleanUtils.isTrue(train.getEvaClosed())){
            return 1;
        }

        Date now = new Date();
        Date openTime = trainCourse.getStartTime();
        Date closeTime = train.getEvaCloseTime();

        if(openTime!=null && now.before(openTime)){
            return 2;
        }
        if(closeTime!=null && now.after(closeTime)){
            return 3;
        }

        return 0;
    }


    @Transactional
    @CacheEvict(value="CetTrainCourses", key = "#trainId")
    public int imports(final List<XlsTrainCourse> beans, int trainId) {

        int success = 0;
        for (XlsTrainCourse uRow : beans) {

            CetTrainCourse record = new CetTrainCourse();
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
