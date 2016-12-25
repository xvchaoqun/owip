package service.cadre;

import domain.cadre.Cadre;
import domain.cadre.CadreCourse;
import domain.cadre.CadreCourseExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreCourseService extends BaseMapper {

    public List<CadreCourse> find(int cadreId, byte type){

        CadreCourseExample example = new CadreCourseExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type);
        example.setOrderByClause("sort_order asc");
        return cadreCourseMapper.selectByExample(example);
    }

    @Transactional
    public void batchAdd(CadreCourse record, String names){
        if(StringUtils.isNotBlank(names)){
            String[] nameArray = names.split("#");
            for (String name : nameArray) {
                if(StringUtils.isNotBlank(name)){
                    record.setId(null);
                    record.setName(name);
                    record.setSortOrder(getNextSortOrder("cadre_course",
                            "cadre_id=" + record.getCadreId() + "and type="+record.getType()));
                    cadreCourseMapper.insertSelective(record);
                }
            }
        }
    }
    @Transactional
    public void del(Integer id){

        cadreCourseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreCourseExample example = new CadreCourseExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreCourseMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }
        CadreCourseExample example = new CadreCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreCourseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreCourse record){
        return cadreCourseMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CadreCourse entity = cadreCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer cadreId = entity.getCadreId();
        Byte type = entity.getType();

        CadreCourseExample example = new CadreCourseExample();
        if (addNum > 0) { // 下降

            example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreCourse> overEntities = cadreCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreCourse targetEntity = overEntities.get(overEntities.size()-1);
            if (addNum > 0)
                commonMapper.downOrder("cadre_course", "cadre_id=" + cadreId + "and type="+type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_course", "cadre_id=" + cadreId + "and type="+type, baseSortOrder, targetEntity.getSortOrder());

            CadreCourse record = new CadreCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreCourseMapper.updateByPrimaryKeySelective(record);
        }
    }
}
