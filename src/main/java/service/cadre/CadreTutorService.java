package service.cadre;

import domain.cadre.CadreTutor;
import domain.cadre.CadreTutorExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreTutorService extends BaseMapper {

    @Transactional
    public int insertSelective(CadreTutor record){

        record.setSortOrder(getNextSortOrder("cadre_tutor", "cadre_id=" + record.getCadreId()));
        return cadreTutorMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreTutorMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreTutorExample example = new CadreTutorExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreTutorMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreTutor record){

        return cadreTutorMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreTutor> findAll(int cadreId) {

        CadreTutorExample example = new CadreTutorExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        example.setOrderByClause("sort_order desc");
        List<CadreTutor> cadreTutores = cadreTutorMapper.selectByExample(example);
        Map<Integer, CadreTutor> map = new LinkedHashMap<>();
        for (CadreTutor cadreTutor : cadreTutores) {
            map.put(cadreTutor.getId(), cadreTutor);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int cadreId, int addNum) {

        if(addNum == 0) return ;

        CadreTutor entity = cadreTutorMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreTutorExample example = new CadreTutorExample();
        if (addNum > 0) {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreTutor> overEntities = cadreTutorMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreTutor targetEntity = overEntities.get(overEntities.size()-1);
            if (addNum > 0)
                commonMapper.downOrder("cadre_tutor", "cadre_id=" + cadreId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_tutor", "cadre_id=" + cadreId, baseSortOrder, targetEntity.getSortOrder());

            CadreTutor record = new CadreTutor();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreTutorMapper.updateByPrimaryKeySelective(record);
        }
    }
}
