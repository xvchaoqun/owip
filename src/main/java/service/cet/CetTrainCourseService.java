package service.cet;

import domain.cet.CetTrainCourse;
import domain.cet.CetTrainCourseExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CetTrainCourseService extends BaseMapper {


    public CetTrainCourse get(int trainId, int courseId){

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId).andCourseIdEqualTo(courseId);
        List<CetTrainCourse> cetTrainCourses = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (cetTrainCourses.size()>0)?cetTrainCourses.get(0):null;
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        cetTrainCourseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTrainCourse record){

        record.setTrainId(null);
        return cetTrainCourseMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
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
                commonMapper.downOrder("cet_train_course", "train_id="+trainId + " and status="+SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_train_course", "train_id="+trainId + " and status="+SystemConstants.AVAILABLE, baseSortOrder, targetEntity.getSortOrder());

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
            record.setTraineeCount(0);
            record.setSortOrder(getNextSortOrder("cet_train_course", "train_id="+record.getTrainId()));
            cetTrainCourseMapper.insertSelective(record);
        }
    }
}
