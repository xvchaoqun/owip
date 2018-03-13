package service.cet;

import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeCourseExample;
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
public class CetTraineeCourseService extends BaseMapper {
    @Transactional
    public void insertSelective(CetTraineeCourse record){

        cetTraineeCourseMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetTraineeCourseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTraineeCourseExample example = new CetTraineeCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTraineeCourseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTraineeCourse record){
        return cetTraineeCourseMapper.updateByPrimaryKeySelective(record);
    }
}
