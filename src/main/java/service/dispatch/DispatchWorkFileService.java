package service.dispatch;

import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DispatchWorkFileService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "发文号重复");

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        DispatchWorkFileExample.Criteria criteria = example.createCriteria()
                .andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dispatchWorkFileMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DispatchWorkFile record){

        Assert.isTrue(!idDuplicate(null, record.getCode()), "发文号重复");
        record.setSortOrder(getNextSortOrder("dispatch_work_file", "status=1 and type="+record.getType()));
        dispatchWorkFileMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchWorkFileMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(DispatchWorkFile record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "发文号重复");
        return dispatchWorkFileMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        DispatchWorkFile entity = dispatchWorkFileMapper.selectByPrimaryKey(id);
        Assert.isTrue(entity.getStatus(), "状态异常");
        Integer baseSortOrder = entity.getSortOrder();
        Byte type = entity.getType();

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(true).andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andStatusEqualTo(true).andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DispatchWorkFile> overEntities = dispatchWorkFileMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            DispatchWorkFile targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("dispatch_work_file", "status=1 and type="+type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dispatch_work_file", "status=1 and type="+type, baseSortOrder, targetEntity.getSortOrder());

            DispatchWorkFile record = new DispatchWorkFile();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dispatchWorkFileMapper.updateByPrimaryKeySelective(record);
        }
    }
}
