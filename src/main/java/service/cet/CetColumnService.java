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

        Assert.isTrue(StringUtils.isNotBlank(code));

        CetColumnExample example = new CetColumnExample();
        CetColumnExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetColumnMapper.countByExample(example) > 0;
    }

    // 更新子栏目的数量
    private void updateChildNum(Integer fid){
        if(fid!=null){
            CetColumnExample example = new CetColumnExample();
            example.createCriteria().andFidEqualTo(fid);
            int childNum = (int) cetColumnMapper.countByExample(example);
            CetColumn _main = new CetColumn();
            _main.setId(fid);
            _main.setChildNum(childNum);
            cetColumnMapper.updateByPrimaryKeySelective(_main);
        }
    }

    @Transactional
    public void insertSelective(CetColumn record){

        String whereSql = "1=1";
        if(record.getFid()!=null){
            whereSql += " and fid="+record.getFid();
        }

        record.setSortOrder(getNextSortOrder("cet_column", whereSql));
        cetColumnMapper.insertSelective(record);

        updateChildNum(record.getFid());
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        List<CetColumn> subCetColumns = null;
        {
            CetColumnExample example = new CetColumnExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNotNull();
            subCetColumns = cetColumnMapper.selectByExample(example);
        }

        {
            //删除子栏目
            CetColumnExample example = new CetColumnExample();
            example.createCriteria().andFidIn(Arrays.asList(ids));
            cetColumnMapper.deleteByExample(example);
        }

        {
            // 删除栏目
            CetColumnExample example = new CetColumnExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cetColumnMapper.deleteByExample(example);
        }

        {
            // 更新数量
            if(subCetColumns!=null){
                for (CetColumn subCetColumn : subCetColumns) {
                    updateChildNum(subCetColumn.getFid());
                }
            }
        }
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
            String whereSql = "1=1";
            if(entity.getFid()!=null){
                whereSql += " and fid="+entity.getFid();
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
