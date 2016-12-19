package service.cadre;

import domain.cadre.CadreTrain;
import domain.cadre.CadreTrainExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreTrainService extends BaseMapper {

    @Transactional
    public int insertSelective(CadreTrain record){

        cadreTrainMapper.insertSelective(record);

        Integer id = record.getId();
        CadreTrain _record = new CadreTrain();
        _record.setId(id);
        _record.setSortOrder(id);
        return cadreTrainMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    public void del(Integer id){

        cadreTrainMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreTrainExample example = new CadreTrainExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreTrainMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadreTrainExample example = new CadreTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreTrainMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreTrain record){
        return cadreTrainMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreTrain> findAll() {

        CadreTrainExample example = new CadreTrainExample();
        example.setOrderByClause("sort_order desc");
        List<CadreTrain> cadreTraines = cadreTrainMapper.selectByExample(example);
        Map<Integer, CadreTrain> map = new LinkedHashMap<>();
        for (CadreTrain cadreTrain : cadreTraines) {
            map.put(cadreTrain.getId(), cadreTrain);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    /*@Transactional
    public void changeOrder(int id, int cadreId, int addNum) {

        if(addNum == 0) return ;

        CadreTrain entity = cadreTrainMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreTrainExample example = new CadreTrainExample();
        if (addNum > 0) {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreTrain> overEntities = cadreTrainMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreTrain targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_cadreTrain(cadreId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_cadreTrain(cadreId, baseSortOrder, targetEntity.getSortOrder());

            CadreTrain record = new CadreTrain();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreTrainMapper.updateByPrimaryKeySelective(record);
        }
    }*/
}
