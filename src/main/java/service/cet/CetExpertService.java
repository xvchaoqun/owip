package service.cet;

import domain.cet.CetExpert;
import domain.cet.CetExpertExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetExpertService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        CetExpertExample example = new CetExpertExample();
        CetExpertExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetExpertMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetExpert record){

        record.setSortOrder(getNextSortOrder("cet_expert", "1=1"));
        cetExpertMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetExpertMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetExpertExample example = new CetExpertExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetExpertMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetExpert record){
        return cetExpertMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetExpert entity = cetExpertMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetExpertExample example = new CetExpertExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetExpert> overEntities = cetExpertMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetExpert targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cet_expert", "1=1", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_expert", "1=1", baseSortOrder, targetEntity.getSortOrder());

            CetExpert record = new CetExpert();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetExpertMapper.updateByPrimaryKeySelective(record);
        }
    }
}
