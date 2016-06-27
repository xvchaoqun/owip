package service.cadre;

import domain.CadreUnderEdu;
import domain.CadreUnderEduExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CadreUnderEduService extends BaseMapper {

    @Transactional
    public int insertSelective(CadreUnderEdu record){

        return cadreUnderEduMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreUnderEduMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreUnderEduExample example = new CadreUnderEduExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreUnderEduMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreUnderEdu record){
        return cadreUnderEduMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int cadreId, int addNum) {

        if(addNum == 0) return ;

        CadreUnderEdu entity = cadreUnderEduMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreUnderEduExample example = new CadreUnderEduExample();
        if (addNum > 0) {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreUnderEdu> overEntities = cadreUnderEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreUnderEdu targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_cadreUnderEdu(cadreId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_cadreUnderEdu(cadreId, baseSortOrder, targetEntity.getSortOrder());

            CadreUnderEdu record = new CadreUnderEdu();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreUnderEduMapper.updateByPrimaryKeySelective(record);
        }
    }
}
