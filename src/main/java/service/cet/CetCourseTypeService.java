package service.cet;

import domain.cet.CetCourseType;
import domain.cet.CetCourseTypeExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetCourseTypeService extends BaseMapper {

    public boolean idDuplicate(Integer id, String name){

        Assert.isTrue(StringUtils.isNotBlank(name), "类型为空");

        CetCourseTypeExample example = new CetCourseTypeExample();
        CetCourseTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name.trim());
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetCourseTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="CetCourseType:ALL", allEntries = true)
    public void insertSelective(CetCourseType record){

        Assert.isTrue(!idDuplicate(null, record.getName()), "类型重复");
        record.setSortOrder(getNextSortOrder("cet_course_type", null));
        cetCourseTypeMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetCourseType:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetCourseTypeExample example = new CetCourseTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetCourseTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="CetCourseType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CetCourseType record){

        if(StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "类型重复");

        return cetCourseTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetCourseType:ALL")
    public Map<Integer, CetCourseType> findAll() {

        CetCourseTypeExample example = new CetCourseTypeExample();
        example.createCriteria();
        example.setOrderByClause("sort_order asc");
        List<CetCourseType> cetCourseTypees = cetCourseTypeMapper.selectByExample(example);
        Map<Integer, CetCourseType> map = new LinkedHashMap<>();
        for (CetCourseType cetCourseType : cetCourseTypees) {
            map.put(cetCourseType.getId(), cetCourseType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetCourseType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;
        byte orderBy = ORDER_BY_ASC;
        CetCourseType entity = cetCourseTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetCourseTypeExample example = new CetCourseTypeExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetCourseType> overEntities = cetCourseTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetCourseType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_course_type", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_course_type", null, baseSortOrder, targetEntity.getSortOrder());

            CetCourseType record = new CetCourseType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetCourseTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
