package service.cadre;

import domain.cadre.CadreParttime;
import domain.cadre.CadreParttimeExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreParttimeService extends BaseMapper {


    @Transactional
    public int insertSelective(CadreParttime record){

        cadreParttimeMapper.insertSelective(record);

        Integer id = record.getId();
        CadreParttime _record = new CadreParttime();
        _record.setId(id);
        _record.setSortOrder(id);
        return cadreParttimeMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    public void del(Integer id){

        cadreParttimeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, int cadreId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            CadreParttimeExample example = new CadreParttimeExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andIdIn(Arrays.asList(ids));
            int count = cadreParttimeMapper.countByExample(example);
            if(count!=ids.length){
                throw new IllegalArgumentException("数据异常");
            }
        }

        CadreParttimeExample example = new CadreParttimeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreParttimeMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreParttime record){
        return cadreParttimeMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreParttime> findAll() {

        CadreParttimeExample example = new CadreParttimeExample();
        example.setOrderByClause("sort_order desc");
        List<CadreParttime> cadreParttimees = cadreParttimeMapper.selectByExample(example);
        Map<Integer, CadreParttime> map = new LinkedHashMap<>();
        for (CadreParttime cadreParttime : cadreParttimees) {
            map.put(cadreParttime.getId(), cadreParttime);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int cadreId, int addNum) {

        if(addNum == 0) return ;

        CadreParttime entity = cadreParttimeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreParttimeExample example = new CadreParttimeExample();
        if (addNum > 0) {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreParttime> overEntities = cadreParttimeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreParttime targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_cadreParttime(cadreId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_cadreParttime(cadreId, baseSortOrder, targetEntity.getSortOrder());

            CadreParttime record = new CadreParttime();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreParttimeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
