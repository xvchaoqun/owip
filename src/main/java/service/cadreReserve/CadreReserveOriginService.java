package service.cadreReserve;

import domain.cadreReserve.CadreReserveOrigin;
import domain.cadreReserve.CadreReserveOriginExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.Date;

@Service
public class CadreReserveOriginService extends BaseMapper {

    @Transactional
    public void insertSelective(CadreReserveOrigin record){

        record.setAddTime(new Date());
        cadreReserveOriginMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cadreReserveOriginMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreReserveOriginExample example = new CadreReserveOriginExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreReserveOriginMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreReserveOrigin record){

        return cadreReserveOriginMapper.updateByPrimaryKeySelective(record);
    }

   /* *//**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     *//*
    @Transactional
    @CacheEvict(value = "CadreReserveOrigin:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        CadreReserveOrigin entity = cadreReserveOriginMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreReserveOriginExample example = new CadreReserveOriginExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CadreReserveOrigin> overEntities = cadreReserveOriginMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadreReserveOrigin targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cadre_reserve_origin", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_reserve_origin", null, baseSortOrder, targetEntity.getSortOrder());

            CadreReserveOrigin record = new CadreReserveOrigin();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreReserveOriginMapper.updateByPrimaryKeySelective(record);
        }
    }*/
}
