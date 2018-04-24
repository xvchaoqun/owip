package service.cet;

import domain.cet.CetColumn;
import domain.cet.CetColumnExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetColumnService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        CetColumnExample example = new CetColumnExample();
        CetColumnExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetColumnMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetColumn record){

        String whereSql = null;
        if(record.getFid()!=null){
            whereSql = "fid="+record.getFid();
        }

        record.setSortOrder(getNextSortOrder("cet_column", whereSql));
        cetColumnMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        // 删除栏目
        CetColumnExample example = new CetColumnExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetColumnMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetColumn record){
        return cetColumnMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetColumn entity = cetColumnMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer fid = entity.getFid();

        CetColumnExample example = new CetColumnExample();
        if (addNum > 0) {

            CetColumnExample.Criteria criteria = example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            if(fid!=null) criteria.andFidEqualTo(fid);
            example.setOrderByClause("sort_order asc");
        }else {

            CetColumnExample.Criteria criteria = example.createCriteria().andSortOrderLessThan(baseSortOrder);
            if(fid!=null) criteria.andFidEqualTo(fid);
            example.setOrderByClause("sort_order desc");
        }

        List<CetColumn> overEntities = cetColumnMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetColumn targetEntity = overEntities.get(overEntities.size()-1);
            String whereSql = null;
            if(entity.getFid()!=null){
                whereSql = "fid="+entity.getFid();
            }
            if (addNum > 0)
                commonMapper.downOrder("cet_column", whereSql, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_column", whereSql, baseSortOrder, targetEntity.getSortOrder());

            CetColumn record = new CetColumn();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetColumnMapper.updateByPrimaryKeySelective(record);
        }
    }
}
