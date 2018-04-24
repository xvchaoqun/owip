package service.cet;

import domain.cet.CetColumnCourse;
import domain.cet.CetColumnCourseExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.List;

@Service
public class CetColumnCourseService extends BaseMapper {

    public boolean idDuplicate(Integer columnId, Integer courseId){

        CetColumnCourseExample example = new CetColumnCourseExample();
        CetColumnCourseExample.Criteria criteria = example.createCriteria().andColumnIdEqualTo(columnId)
                .andCourseIdEqualTo(courseId);

        return cetColumnCourseMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetColumnCourse record){

        //Assert.isTrue(!idDuplicate(null, record.getCode()));
        record.setSortOrder(getNextSortOrder("cet_column_course", null));
        cetColumnCourseMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        CetColumnCourse cetColumnCourse = cetColumnCourseMapper.selectByPrimaryKey(id);
        cetColumnCourseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {

            del(id);
        }
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetColumnCourse entity = cetColumnCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetColumnCourseExample example = new CetColumnCourseExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetColumnCourse> overEntities = cetColumnCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetColumnCourse targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cet_column_course", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_column_course", null, baseSortOrder, targetEntity.getSortOrder());

            CetColumnCourse record = new CetColumnCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetColumnCourseMapper.updateByPrimaryKeySelective(record);
        }
    }
}
