package service.cet;

import domain.cet.CetCourse;
import domain.cet.CetCourseItem;
import domain.cet.CetCourseItemExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetCourseItemService extends CetBaseMapper {

    // 更新课程总学时，针对上级网上专题
    public void refreshCoursePeriod(int courseId){

        BigDecimal period = iCetMapper.getCoursePeriod(courseId);

        CetCourse record = new CetCourse();
        record.setId(courseId);
        record.setPeriod(NumberUtils.trimToZero(period));

        cetCourseMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @CacheEvict(value="CetCourseItems", allEntries = true)
    public void insertSelective(CetCourseItem record){

        record.setSortOrder(getNextSortOrder("cet_course_item", "course_id="+ record.getCourseId()));
        cetCourseItemMapper.insertSelective(record);

        refreshCoursePeriod(record.getCourseId());
    }

    @Transactional
    @CacheEvict(value="CetCourseItems", allEntries = true)
    public void del(Integer id){

        CetCourseItem cetCourseItem = cetCourseItemMapper.selectByPrimaryKey(id);
        int courseId = cetCourseItem.getCourseId();

        cetCourseItemMapper.deleteByPrimaryKey(id);

        refreshCoursePeriod(courseId);
    }

    @Transactional
    @CacheEvict(value="CetCourseItems", allEntries = true)
    public int updateByPrimaryKeySelective(CetCourseItem record){
        record.setCourseId(null);
        return cetCourseItemMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetCourseItems", key = "#courseId")
    public Map<Integer, CetCourseItem> findAll(int courseId) {

        CetCourseItemExample example = new CetCourseItemExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        example.setOrderByClause("sort_order desc");
        List<CetCourseItem> cetCourseItemes = cetCourseItemMapper.selectByExample(example);
        Map<Integer, CetCourseItem> map = new LinkedHashMap<>();
        for (CetCourseItem cetCourseItem : cetCourseItemes) {
            map.put(cetCourseItem.getId(), cetCourseItem);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetCourseItems", allEntries = true)
    public void changeOrder(int id, int addNum) {

        CetCourseItem entity = cetCourseItemMapper.selectByPrimaryKey(id);
        changeOrder("cet_course_item", "course_id="+ entity.getCourseId(), ORDER_BY_ASC, id, addNum);
    }
}
