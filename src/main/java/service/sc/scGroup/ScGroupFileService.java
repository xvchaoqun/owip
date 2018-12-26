package service.sc.scGroup;

import domain.sc.scGroup.ScGroupFile;
import domain.sc.scGroup.ScGroupFileExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sc.ScBaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScGroupFileService extends ScBaseMapper {

    private Integer current;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScGroupFileExample example = new ScGroupFileExample();
        ScGroupFileExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scGroupFileMapper.countByExample(example) > 0;
    }

    public ScGroupFile getCurrent(){

        ScGroupFileExample example = new ScGroupFileExample();
        example.createCriteria().andIsCurrentEqualTo(true);
        List<ScGroupFile> scGroupFiles = scGroupFileMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return scGroupFiles.size()==0?null:scGroupFiles.get(0);
    }

    @Transactional
    public void batchAdd(List<ScGroupFile> records) {
        for (ScGroupFile record : records) {
            record.setSortOrder(getNextSortOrder("sc_group_file", null));
            scGroupFileMapper.insertSelective(record);
        }
    }

    @Transactional
    public void del(Integer id){

        scGroupFileMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScGroupFileExample example = new ScGroupFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scGroupFileMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScGroupFile record){
        return scGroupFileMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ScGroupFile entity = scGroupFileMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ScGroupFileExample example = new ScGroupFileExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ScGroupFile> overEntities = scGroupFileMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ScGroupFile targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("sc_group_file", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("sc_group_file", null, baseSortOrder, targetEntity.getSortOrder());

            ScGroupFile record = new ScGroupFile();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            scGroupFileMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void setCurrent(Integer id) {

        {
            ScGroupFile record = new ScGroupFile();
            record.setIsCurrent(false);
            ScGroupFileExample example = new ScGroupFileExample();
            scGroupFileMapper.updateByExampleSelective(record, example);
        }

        ScGroupFile record = new ScGroupFile();
        record.setId(id);
        record.setIsCurrent(true);
        scGroupFileMapper.updateByPrimaryKeySelective(record);
    }
}
