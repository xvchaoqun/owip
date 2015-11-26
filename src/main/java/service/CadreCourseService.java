package service;

import domain.CadreCourse;
import domain.CadreCourseExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreCourseService extends BaseMapper {


    @Transactional
    public int insertSelective(CadreCourse record){

        return cadreCourseMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreCourseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreCourseExample example = new CadreCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreCourseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreCourse record){
        return cadreCourseMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreCourse> findAll() {

        CadreCourseExample example = new CadreCourseExample();
        example.setOrderByClause("type desc");
        List<CadreCourse> cadreCoursees = cadreCourseMapper.selectByExample(example);
        Map<Integer, CadreCourse> map = new LinkedHashMap<>();
        for (CadreCourse cadreCourse : cadreCoursees) {
            map.put(cadreCourse.getId(), cadreCourse);
        }

        return map;
    }
}
