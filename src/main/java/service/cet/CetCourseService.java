package service.cet;

import domain.cet.CetCourse;
import domain.cet.CetCourseExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetCourseService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        CetCourseExample example = new CetCourseExample();
        CetCourseExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetCourseMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetCourse record){

        record.setSortOrder(getNextSortOrder("cet_course", "1=1"));
        cetCourseMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetCourseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetCourseExample example = new CetCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetCourseMapper.deleteByExample(example);
    }

    @Transactional

    public int updateByPrimaryKeySelective(CetCourse record){
        /*if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));*/
        return cetCourseMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetCourse entity = cetCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetCourseExample example = new CetCourseExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetCourse> overEntities = cetCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetCourse targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cet_course", "1=1", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_course", "1=1", baseSortOrder, targetEntity.getSortOrder());

            CetCourse record = new CetCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetCourseMapper.updateByPrimaryKeySelective(record);
        }
    }
}
