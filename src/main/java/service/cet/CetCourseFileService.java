package service.cet;

import domain.cet.CetCourseFile;
import domain.cet.CetCourseFileExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetCourseFileService extends CetBaseMapper {

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

        CetCourseFile entity = cetCourseFileMapper.selectByPrimaryKey(id);
        changeOrder("cet_course_file", "course_id="+ entity.getCourseId(), ORDER_BY_ASC, id, addNum);
    }
}
