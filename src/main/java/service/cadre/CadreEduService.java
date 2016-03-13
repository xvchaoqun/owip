package service.cadre;

import domain.CadreEdu;
import domain.CadreEduExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CadreEduService extends BaseMapper {


    public CadreEdu getHighEdu(int cadreId){

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsHighEduEqualTo(true);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(cadreEdus.size()>0) return  cadreEdus.get(0);
        return null;
    }
    public CadreEdu getHighDegree(int cadreId){

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsHighDegreeEqualTo(true);
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(cadreEdus.size()>0) return  cadreEdus.get(0);
        return null;
    }

    @Transactional
    public int insertSelective(CadreEdu record){

        return cadreEduMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        cadreEduMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreEduExample example = new CadreEduExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreEduMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreEdu record){
        return cadreEduMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int cadreId, int addNum) {

        if(addNum == 0) return ;

        CadreEdu entity = cadreEduMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreEduExample example = new CadreEduExample();
        if (addNum > 0) {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreEdu> overEntities = cadreEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreEdu targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_cadreEdu(cadreId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_cadreEdu(cadreId, baseSortOrder, targetEntity.getSortOrder());

            CadreEdu record = new CadreEdu();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreEduMapper.updateByPrimaryKeySelective(record);
        }
    }
}
