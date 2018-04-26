package service.cet;

import domain.cet.CetCourseFile;
import domain.cet.CetCourseFileExample;
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
public class CetCourseFileService extends BaseMapper {

    @Transactional
    @CacheEvict(value="CetCourseFiles", allEntries = true)
    public void insertSelective(CetCourseFile record){

        record.setSortOrder(getNextSortOrder("cet_course_file", "course_id="+ record.getCourseId()));
        cetCourseFileMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetCourseFiles", allEntries = true)
    public void del(Integer id){

        cetCourseFileMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="CetCourseFiles", allEntries = true)
    public int updateByPrimaryKeySelective(CetCourseFile record){
        record.setCourseId(null);
        return cetCourseFileMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetCourseFiles", key = "#courseId")
    public Map<Integer, CetCourseFile> findAll(int courseId) {

        CetCourseFileExample example = new CetCourseFileExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        example.setOrderByClause("sort_order asc");
        List<CetCourseFile> cetCourseFilees = cetCourseFileMapper.selectByExample(example);
        Map<Integer, CetCourseFile> map = new LinkedHashMap<>();
        for (CetCourseFile cetCourseFile : cetCourseFilees) {
            map.put(cetCourseFile.getId(), cetCourseFile);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetCourseFiles", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        CetCourseFile entity = cetCourseFileMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer courseId = entity.getCourseId();

        CetCourseFileExample example = new CetCourseFileExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andCourseIdEqualTo(courseId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCourseIdEqualTo(courseId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetCourseFile> overEntities = cetCourseFileMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetCourseFile targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_course_file", "course_id="+ courseId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_course_file", "course_id="+ courseId, baseSortOrder, targetEntity.getSortOrder());

            CetCourseFile record = new CetCourseFile();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetCourseFileMapper.updateByPrimaryKeySelective(record);
        }
    }
}
