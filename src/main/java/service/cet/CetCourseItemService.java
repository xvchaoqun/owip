package service.cet;

import domain.cet.CetCourseItem;
import domain.cet.CetCourseItemExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetCourseItemService extends BaseMapper {

    @Transactional
    @CacheEvict(value="CetCourseItems", allEntries = true)
    public void insertSelective(CetCourseItem record){

        record.setSortOrder(getNextSortOrder("cet_course_item", "course_id="+ record.getCourseId()));
        cetCourseItemMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetCourseItems", allEntries = true)
    public void del(Integer id){

        cetCourseItemMapper.deleteByPrimaryKey(id);
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

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        CetCourseItem entity = cetCourseItemMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer courseId = entity.getCourseId();

        CetCourseItemExample example = new CetCourseItemExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andCourseIdEqualTo(courseId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCourseIdEqualTo(courseId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetCourseItem> overEntities = cetCourseItemMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetCourseItem targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_course_item", "course_id="+ courseId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_course_item", "course_id="+ courseId, baseSortOrder, targetEntity.getSortOrder());

            CetCourseItem record = new CetCourseItem();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetCourseItemMapper.updateByPrimaryKeySelective(record);
        }
    }
}
